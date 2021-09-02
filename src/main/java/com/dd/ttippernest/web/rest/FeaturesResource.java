package com.dd.ttippernest.web.rest;

import com.dd.ttippernest.domain.Features;
import com.dd.ttippernest.repository.FeaturesRepository;
import com.dd.ttippernest.service.FeaturesQueryService;
import com.dd.ttippernest.service.FeaturesService;
import com.dd.ttippernest.service.criteria.FeaturesCriteria;
import com.dd.ttippernest.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dd.ttippernest.domain.Features}.
 */
@RestController
@RequestMapping("/api")
public class FeaturesResource {

    private final Logger log = LoggerFactory.getLogger(FeaturesResource.class);

    private static final String ENTITY_NAME = "features";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeaturesService featuresService;

    private final FeaturesRepository featuresRepository;

    private final FeaturesQueryService featuresQueryService;

    public FeaturesResource(
        FeaturesService featuresService,
        FeaturesRepository featuresRepository,
        FeaturesQueryService featuresQueryService
    ) {
        this.featuresService = featuresService;
        this.featuresRepository = featuresRepository;
        this.featuresQueryService = featuresQueryService;
    }

    /**
     * {@code POST  /features} : Create a new features.
     *
     * @param features the features to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new features, or with status {@code 400 (Bad Request)} if the features has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features")
    public ResponseEntity<Features> createFeatures(@RequestBody Features features) throws URISyntaxException {
        log.debug("REST request to save Features : {}", features);
        if (features.getId() != null) {
            throw new BadRequestAlertException("A new features cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Features result = featuresService.save(features);
        return ResponseEntity
            .created(new URI("/api/features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /features/:id} : Updates an existing features.
     *
     * @param id the id of the features to save.
     * @param features the features to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated features,
     * or with status {@code 400 (Bad Request)} if the features is not valid,
     * or with status {@code 500 (Internal Server Error)} if the features couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features/{id}")
    public ResponseEntity<Features> updateFeatures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Features features
    ) throws URISyntaxException {
        log.debug("REST request to update Features : {}, {}", id, features);
        if (features.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, features.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featuresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Features result = featuresService.save(features);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, features.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /features/:id} : Partial updates given fields of an existing features, field will ignore if it is null
     *
     * @param id the id of the features to save.
     * @param features the features to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated features,
     * or with status {@code 400 (Bad Request)} if the features is not valid,
     * or with status {@code 404 (Not Found)} if the features is not found,
     * or with status {@code 500 (Internal Server Error)} if the features couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/features/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Features> partialUpdateFeatures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Features features
    ) throws URISyntaxException {
        log.debug("REST request to partial update Features partially : {}, {}", id, features);
        if (features.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, features.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featuresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Features> result = featuresService.partialUpdate(features);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, features.getId().toString())
        );
    }

    /**
     * {@code GET  /features} : get all the features.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/features")
    public ResponseEntity<List<Features>> getAllFeatures(FeaturesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Features by criteria: {}", criteria);
        Page<Features> page = featuresQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /features/count} : count all the features.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/features/count")
    public ResponseEntity<Long> countFeatures(FeaturesCriteria criteria) {
        log.debug("REST request to count Features by criteria: {}", criteria);
        return ResponseEntity.ok().body(featuresQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /features/:id} : get the "id" features.
     *
     * @param id the id of the features to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the features, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features/{id}")
    public ResponseEntity<Features> getFeatures(@PathVariable Long id) {
        log.debug("REST request to get Features : {}", id);
        Optional<Features> features = featuresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(features);
    }

    /**
     * {@code DELETE  /features/:id} : delete the "id" features.
     *
     * @param id the id of the features to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features/{id}")
    public ResponseEntity<Void> deleteFeatures(@PathVariable Long id) {
        log.debug("REST request to delete Features : {}", id);
        featuresService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
