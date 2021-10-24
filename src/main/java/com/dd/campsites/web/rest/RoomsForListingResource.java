package com.dd.campsites.web.rest;

import com.dd.campsites.domain.RoomsForListing;
import com.dd.campsites.repository.RoomsForListingRepository;
import com.dd.campsites.service.RoomsForListingQueryService;
import com.dd.campsites.service.RoomsForListingService;
import com.dd.campsites.service.criteria.RoomsForListingCriteria;
import com.dd.campsites.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dd.campsites.domain.RoomsForListing}.
 */
@RestController
@RequestMapping("/api")
public class RoomsForListingResource {

    private final Logger log = LoggerFactory.getLogger(RoomsForListingResource.class);

    private static final String ENTITY_NAME = "roomsForListing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomsForListingService roomsForListingService;

    private final RoomsForListingRepository roomsForListingRepository;

    private final RoomsForListingQueryService roomsForListingQueryService;

    public RoomsForListingResource(
        RoomsForListingService roomsForListingService,
        RoomsForListingRepository roomsForListingRepository,
        RoomsForListingQueryService roomsForListingQueryService
    ) {
        this.roomsForListingService = roomsForListingService;
        this.roomsForListingRepository = roomsForListingRepository;
        this.roomsForListingQueryService = roomsForListingQueryService;
    }

    /**
     * {@code POST  /rooms-for-listings} : Create a new roomsForListing.
     *
     * @param roomsForListing the roomsForListing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomsForListing, or with status {@code 400 (Bad Request)} if the roomsForListing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rooms-for-listings")
    public ResponseEntity<RoomsForListing> createRoomsForListing(@RequestBody RoomsForListing roomsForListing) throws URISyntaxException {
        log.debug("REST request to save RoomsForListing : {}", roomsForListing);
        if (roomsForListing.getId() != null) {
            throw new BadRequestAlertException("A new roomsForListing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomsForListing result = roomsForListingService.save(roomsForListing);
        return ResponseEntity
            .created(new URI("/api/rooms-for-listings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rooms-for-listings/:id} : Updates an existing roomsForListing.
     *
     * @param id the id of the roomsForListing to save.
     * @param roomsForListing the roomsForListing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomsForListing,
     * or with status {@code 400 (Bad Request)} if the roomsForListing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomsForListing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rooms-for-listings/{id}")
    public ResponseEntity<RoomsForListing> updateRoomsForListing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomsForListing roomsForListing
    ) throws URISyntaxException {
        log.debug("REST request to update RoomsForListing : {}, {}", id, roomsForListing);
        if (roomsForListing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomsForListing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomsForListingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomsForListing result = roomsForListingService.save(roomsForListing);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomsForListing.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rooms-for-listings/:id} : Partial updates given fields of an existing roomsForListing, field will ignore if it is null
     *
     * @param id the id of the roomsForListing to save.
     * @param roomsForListing the roomsForListing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomsForListing,
     * or with status {@code 400 (Bad Request)} if the roomsForListing is not valid,
     * or with status {@code 404 (Not Found)} if the roomsForListing is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomsForListing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rooms-for-listings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RoomsForListing> partialUpdateRoomsForListing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomsForListing roomsForListing
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoomsForListing partially : {}, {}", id, roomsForListing);
        if (roomsForListing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomsForListing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomsForListingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomsForListing> result = roomsForListingService.partialUpdate(roomsForListing);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomsForListing.getId().toString())
        );
    }

    /**
     * {@code GET  /rooms-for-listings} : get all the roomsForListings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roomsForListings in body.
     */
    @GetMapping("/rooms-for-listings")
    public ResponseEntity<List<RoomsForListing>> getAllRoomsForListings(RoomsForListingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RoomsForListings by criteria: {}", criteria);
        Page<RoomsForListing> page = roomsForListingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rooms-for-listings/count} : count all the roomsForListings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rooms-for-listings/count")
    public ResponseEntity<Long> countRoomsForListings(RoomsForListingCriteria criteria) {
        log.debug("REST request to count RoomsForListings by criteria: {}", criteria);
        return ResponseEntity.ok().body(roomsForListingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rooms-for-listings/:id} : get the "id" roomsForListing.
     *
     * @param id the id of the roomsForListing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomsForListing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rooms-for-listings/{id}")
    public ResponseEntity<RoomsForListing> getRoomsForListing(@PathVariable Long id) {
        log.debug("REST request to get RoomsForListing : {}", id);
        Optional<RoomsForListing> roomsForListing = roomsForListingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomsForListing);
    }

    /**
     * {@code DELETE  /rooms-for-listings/:id} : delete the "id" roomsForListing.
     *
     * @param id the id of the roomsForListing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rooms-for-listings/{id}")
    public ResponseEntity<Void> deleteRoomsForListing(@PathVariable Long id) {
        log.debug("REST request to delete RoomsForListing : {}", id);
        roomsForListingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
