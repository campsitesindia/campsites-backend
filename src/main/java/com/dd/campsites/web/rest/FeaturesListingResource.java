package com.dd.campsites.web.rest;

import com.dd.campsites.domain.FeaturesListing;
import com.dd.campsites.repository.FeaturesListingRepository;
import com.dd.campsites.service.FeaturesListingQueryService;
import com.dd.campsites.service.FeaturesListingService;
import com.dd.campsites.service.criteria.FeaturesListingCriteria;
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
 * REST controller for managing {@link com.dd.campsites.domain.FeaturesListing}.
 */
@RestController
@RequestMapping("/api")
public class FeaturesListingResource {

    private final Logger log = LoggerFactory.getLogger(FeaturesListingResource.class);

    private static final String ENTITY_NAME = "featuresListing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeaturesListingService featuresListingService;

    private final FeaturesListingRepository featuresListingRepository;

    private final FeaturesListingQueryService featuresListingQueryService;

    public FeaturesListingResource(
        FeaturesListingService featuresListingService,
        FeaturesListingRepository featuresListingRepository,
        FeaturesListingQueryService featuresListingQueryService
    ) {
        this.featuresListingService = featuresListingService;
        this.featuresListingRepository = featuresListingRepository;
        this.featuresListingQueryService = featuresListingQueryService;
    }

    /**
     * {@code POST  /features-listings} : Create a new featuresListing.
     *
     * @param featuresListing the featuresListing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featuresListing, or with status {@code 400 (Bad Request)} if the featuresListing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features-listings")
    public ResponseEntity<FeaturesListing> createFeaturesListing(@RequestBody FeaturesListing featuresListing) throws URISyntaxException {
        log.debug("REST request to save FeaturesListing : {}", featuresListing);
        if (featuresListing.getId() != null) {
            throw new BadRequestAlertException("A new featuresListing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeaturesListing result = featuresListingService.save(featuresListing);
        return ResponseEntity
            .created(new URI("/api/features-listings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /features-listings/:id} : Updates an existing featuresListing.
     *
     * @param id the id of the featuresListing to save.
     * @param featuresListing the featuresListing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featuresListing,
     * or with status {@code 400 (Bad Request)} if the featuresListing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the featuresListing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features-listings/{id}")
    public ResponseEntity<FeaturesListing> updateFeaturesListing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeaturesListing featuresListing
    ) throws URISyntaxException {
        log.debug("REST request to update FeaturesListing : {}, {}", id, featuresListing);
        if (featuresListing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featuresListing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featuresListingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeaturesListing result = featuresListingService.save(featuresListing);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, featuresListing.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /features-listings/:id} : Partial updates given fields of an existing featuresListing, field will ignore if it is null
     *
     * @param id the id of the featuresListing to save.
     * @param featuresListing the featuresListing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featuresListing,
     * or with status {@code 400 (Bad Request)} if the featuresListing is not valid,
     * or with status {@code 404 (Not Found)} if the featuresListing is not found,
     * or with status {@code 500 (Internal Server Error)} if the featuresListing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/features-listings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FeaturesListing> partialUpdateFeaturesListing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeaturesListing featuresListing
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeaturesListing partially : {}, {}", id, featuresListing);
        if (featuresListing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featuresListing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featuresListingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeaturesListing> result = featuresListingService.partialUpdate(featuresListing);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, featuresListing.getId().toString())
        );
    }

    /**
     * {@code GET  /features-listings} : get all the featuresListings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of featuresListings in body.
     */
    @GetMapping("/features-listings")
    public ResponseEntity<List<FeaturesListing>> getAllFeaturesListings(FeaturesListingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FeaturesListings by criteria: {}", criteria);
        Page<FeaturesListing> page = featuresListingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /features-listings/count} : count all the featuresListings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/features-listings/count")
    public ResponseEntity<Long> countFeaturesListings(FeaturesListingCriteria criteria) {
        log.debug("REST request to count FeaturesListings by criteria: {}", criteria);
        return ResponseEntity.ok().body(featuresListingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /features-listings/:id} : get the "id" featuresListing.
     *
     * @param id the id of the featuresListing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the featuresListing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features-listings/{id}")
    public ResponseEntity<FeaturesListing> getFeaturesListing(@PathVariable Long id) {
        log.debug("REST request to get FeaturesListing : {}", id);
        Optional<FeaturesListing> featuresListing = featuresListingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(featuresListing);
    }

    /**
     * {@code DELETE  /features-listings/:id} : delete the "id" featuresListing.
     *
     * @param id the id of the featuresListing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features-listings/{id}")
    public ResponseEntity<Void> deleteFeaturesListing(@PathVariable Long id) {
        log.debug("REST request to delete FeaturesListing : {}", id);
        featuresListingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
