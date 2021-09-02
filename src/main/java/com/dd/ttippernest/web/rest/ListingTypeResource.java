package com.dd.ttippernest.web.rest;

import com.dd.ttippernest.domain.ListingType;
import com.dd.ttippernest.repository.ListingTypeRepository;
import com.dd.ttippernest.service.ListingTypeQueryService;
import com.dd.ttippernest.service.ListingTypeService;
import com.dd.ttippernest.service.criteria.ListingTypeCriteria;
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
 * REST controller for managing {@link com.dd.ttippernest.domain.ListingType}.
 */
@RestController
@RequestMapping("/api")
public class ListingTypeResource {

    private final Logger log = LoggerFactory.getLogger(ListingTypeResource.class);

    private static final String ENTITY_NAME = "listingType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListingTypeService listingTypeService;

    private final ListingTypeRepository listingTypeRepository;

    private final ListingTypeQueryService listingTypeQueryService;

    public ListingTypeResource(
        ListingTypeService listingTypeService,
        ListingTypeRepository listingTypeRepository,
        ListingTypeQueryService listingTypeQueryService
    ) {
        this.listingTypeService = listingTypeService;
        this.listingTypeRepository = listingTypeRepository;
        this.listingTypeQueryService = listingTypeQueryService;
    }

    /**
     * {@code POST  /listing-types} : Create a new listingType.
     *
     * @param listingType the listingType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listingType, or with status {@code 400 (Bad Request)} if the listingType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/listing-types")
    public ResponseEntity<ListingType> createListingType(@RequestBody ListingType listingType) throws URISyntaxException {
        log.debug("REST request to save ListingType : {}", listingType);
        if (listingType.getId() != null) {
            throw new BadRequestAlertException("A new listingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListingType result = listingTypeService.save(listingType);
        return ResponseEntity
            .created(new URI("/api/listing-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /listing-types/:id} : Updates an existing listingType.
     *
     * @param id the id of the listingType to save.
     * @param listingType the listingType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listingType,
     * or with status {@code 400 (Bad Request)} if the listingType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listingType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/listing-types/{id}")
    public ResponseEntity<ListingType> updateListingType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListingType listingType
    ) throws URISyntaxException {
        log.debug("REST request to update ListingType : {}, {}", id, listingType);
        if (listingType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listingType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listingTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListingType result = listingTypeService.save(listingType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listingType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /listing-types/:id} : Partial updates given fields of an existing listingType, field will ignore if it is null
     *
     * @param id the id of the listingType to save.
     * @param listingType the listingType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listingType,
     * or with status {@code 400 (Bad Request)} if the listingType is not valid,
     * or with status {@code 404 (Not Found)} if the listingType is not found,
     * or with status {@code 500 (Internal Server Error)} if the listingType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/listing-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListingType> partialUpdateListingType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListingType listingType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListingType partially : {}, {}", id, listingType);
        if (listingType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listingType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listingTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListingType> result = listingTypeService.partialUpdate(listingType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listingType.getId().toString())
        );
    }

    /**
     * {@code GET  /listing-types} : get all the listingTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listingTypes in body.
     */
    @GetMapping("/listing-types")
    public ResponseEntity<List<ListingType>> getAllListingTypes(ListingTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ListingTypes by criteria: {}", criteria);
        Page<ListingType> page = listingTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /listing-types/count} : count all the listingTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/listing-types/count")
    public ResponseEntity<Long> countListingTypes(ListingTypeCriteria criteria) {
        log.debug("REST request to count ListingTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(listingTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /listing-types/:id} : get the "id" listingType.
     *
     * @param id the id of the listingType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listingType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listing-types/{id}")
    public ResponseEntity<ListingType> getListingType(@PathVariable Long id) {
        log.debug("REST request to get ListingType : {}", id);
        Optional<ListingType> listingType = listingTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listingType);
    }

    /**
     * {@code DELETE  /listing-types/:id} : delete the "id" listingType.
     *
     * @param id the id of the listingType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/listing-types/{id}")
    public ResponseEntity<Void> deleteListingType(@PathVariable Long id) {
        log.debug("REST request to delete ListingType : {}", id);
        listingTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
