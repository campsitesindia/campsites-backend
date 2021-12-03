package com.dd.campsites.web.rest;

import com.dd.campsites.campsitesindia.listing.ListingBusinessService;
import com.dd.campsites.campsitesindia.model.ListingPublish;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.domain.User;
import com.dd.campsites.repository.ListingRepository;
import com.dd.campsites.repository.UserRepository;
import com.dd.campsites.service.ListingQueryService;
import com.dd.campsites.service.ListingService;
import com.dd.campsites.service.UserService;
import com.dd.campsites.service.campsitesindia.ListingDetailsService;
import com.dd.campsites.service.campsitesindia.model.ListingModel;
import com.dd.campsites.service.criteria.ListingCriteria;
import com.dd.campsites.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Listing}.
 */
@RestController
@RequestMapping("/api/restricted")
public class ListingRestrictedResource {

    private final Logger log = LoggerFactory.getLogger(ListingRestrictedResource.class);

    private static final String ENTITY_NAME = "listing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListingService listingService;

    private final UserRepository userRepository;

    private final ListingBusinessService listingBusinessService;

    private final ListingRepository listingRepository;

    private final ListingQueryService listingQueryService;

    private final ListingDetailsService listingDetailsService;

    public ListingRestrictedResource(
        ListingService listingService,
        UserService userService,
        UserRepository userRepository,
        ListingBusinessService listingBusinessService,
        ListingRepository listingRepository,
        ListingQueryService listingQueryService,
        ListingDetailsService listingDetailsService
    ) {
        this.listingService = listingService;
        this.userRepository = userRepository;
        this.listingBusinessService = listingBusinessService;
        this.listingRepository = listingRepository;
        this.listingQueryService = listingQueryService;
        this.listingDetailsService = listingDetailsService;
    }

    /**
     * {@code PUT  /listings/:id} : Updates an existing listing.
     *
     * @param id the id of the listing to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listing,
     * or with status {@code 400 (Bad Request)} if the listing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/listings/publish/{id}")
    public ResponseEntity<ListingPublish> publishListing(@PathVariable(value = "id", required = true) final Long id)
        throws URISyntaxException {
        log.debug("REST request to publish Listing : {}, {}", id, id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!listingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListingPublish result = listingBusinessService.publish(id);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(result);
    }

    /**
     * {@code GET  /listingsWithExtraInformation} : get all the listings.
     *
     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listings in body.
     */
    @GetMapping("/listingsWithExtraInformation")
    public ResponseEntity<List<ListingModel>> getAllListingsWithExtraInformation(ListingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Listings with details by criteria: {}");
        User user = null;
        if (getUser().isEmpty()) {
            log.debug(" No user is present ................");
            List<ListingModel> emptyLM = new ArrayList<ListingModel>();
            return ResponseEntity.ok().body(emptyLM);
        } else {
            user = getUser().get();
        }

        ListingCriteria listingCriteria = new ListingCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(user.getId());
        listingCriteria.setOwnerId(longFilter);
        Page<ListingModel> page = listingDetailsService.findAll(listingCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /listings/count} : count all the listings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/listings/count")
    public ResponseEntity<Long> countListings(ListingCriteria criteria) {
        log.debug("REST request to count Listings by criteria: {}", criteria);
        return ResponseEntity.ok().body(listingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /listings/:id} : get the "id" listing.
     *
     * @param id the id of the listing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listings/{id}")
    public ResponseEntity<Listing> getListing(@PathVariable Long id) {
        log.debug("REST request to get Listing : {}", id);
        Optional<Listing> listing = listingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listing);
    }

    /**
     * {@code GET  /listings/:id} : get the "id" listing.
     *
     * @param id the id of the listing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listings/details/{id}")
    public ResponseEntity<ListingModel> getListingDetails(@PathVariable Long id) {
        if (getUser().isEmpty()) {
            log.debug(" No user is present ................");
            ListingModel emptyLM = new ListingModel();
            return ResponseEntity.ok().body(emptyLM);
        }
        log.debug("REST request to get Listing Details : {}", id);
        Optional<ListingModel> listing = listingDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listing);
    }

    /**
     * {@code DELETE  /listings/:id} : delete the "id" listing.
     *
     * @param id the id of the listing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/listings/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        log.debug("REST request to delete Listing : {}", id);
        listingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Optional<User> getUser() {
        log.info("Getting Auth user...... .....................................{}", getAuthentication().getName());
        Optional<User> user = userRepository.findOneByLogin(getAuthentication().getName());
        log.info("logged in user...... {}", user.get().getEmail());
        return user;
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("logged in authentication...... {}", authentication.isAuthenticated());
        assert (authentication.isAuthenticated());

        return authentication;
    }
}
