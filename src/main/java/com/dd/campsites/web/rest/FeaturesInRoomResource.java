package com.dd.campsites.web.rest;

import com.dd.campsites.domain.FeaturesInRoom;
import com.dd.campsites.repository.FeaturesInRoomRepository;
import com.dd.campsites.service.FeaturesInRoomQueryService;
import com.dd.campsites.service.FeaturesInRoomService;
import com.dd.campsites.service.criteria.FeaturesInRoomCriteria;
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
 * REST controller for managing {@link com.dd.campsites.domain.FeaturesInRoom}.
 */
@RestController
@RequestMapping("/api")
public class FeaturesInRoomResource {

    private final Logger log = LoggerFactory.getLogger(FeaturesInRoomResource.class);

    private static final String ENTITY_NAME = "featuresInRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeaturesInRoomService featuresInRoomService;

    private final FeaturesInRoomRepository featuresInRoomRepository;

    private final FeaturesInRoomQueryService featuresInRoomQueryService;

    public FeaturesInRoomResource(
        FeaturesInRoomService featuresInRoomService,
        FeaturesInRoomRepository featuresInRoomRepository,
        FeaturesInRoomQueryService featuresInRoomQueryService
    ) {
        this.featuresInRoomService = featuresInRoomService;
        this.featuresInRoomRepository = featuresInRoomRepository;
        this.featuresInRoomQueryService = featuresInRoomQueryService;
    }

    /**
     * {@code POST  /features-in-rooms} : Create a new featuresInRoom.
     *
     * @param featuresInRoom the featuresInRoom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featuresInRoom, or with status {@code 400 (Bad Request)} if the featuresInRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features-in-rooms")
    public ResponseEntity<FeaturesInRoom> createFeaturesInRoom(@RequestBody FeaturesInRoom featuresInRoom) throws URISyntaxException {
        log.debug("REST request to save FeaturesInRoom : {}", featuresInRoom);
        if (featuresInRoom.getId() != null) {
            throw new BadRequestAlertException("A new featuresInRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeaturesInRoom result = featuresInRoomService.save(featuresInRoom);
        return ResponseEntity
            .created(new URI("/api/features-in-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /features-in-rooms/:id} : Updates an existing featuresInRoom.
     *
     * @param id the id of the featuresInRoom to save.
     * @param featuresInRoom the featuresInRoom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featuresInRoom,
     * or with status {@code 400 (Bad Request)} if the featuresInRoom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the featuresInRoom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features-in-rooms/{id}")
    public ResponseEntity<FeaturesInRoom> updateFeaturesInRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeaturesInRoom featuresInRoom
    ) throws URISyntaxException {
        log.debug("REST request to update FeaturesInRoom : {}, {}", id, featuresInRoom);
        if (featuresInRoom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featuresInRoom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featuresInRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeaturesInRoom result = featuresInRoomService.save(featuresInRoom);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, featuresInRoom.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /features-in-rooms/:id} : Partial updates given fields of an existing featuresInRoom, field will ignore if it is null
     *
     * @param id the id of the featuresInRoom to save.
     * @param featuresInRoom the featuresInRoom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featuresInRoom,
     * or with status {@code 400 (Bad Request)} if the featuresInRoom is not valid,
     * or with status {@code 404 (Not Found)} if the featuresInRoom is not found,
     * or with status {@code 500 (Internal Server Error)} if the featuresInRoom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/features-in-rooms/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FeaturesInRoom> partialUpdateFeaturesInRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeaturesInRoom featuresInRoom
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeaturesInRoom partially : {}, {}", id, featuresInRoom);
        if (featuresInRoom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featuresInRoom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featuresInRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeaturesInRoom> result = featuresInRoomService.partialUpdate(featuresInRoom);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, featuresInRoom.getId().toString())
        );
    }

    /**
     * {@code GET  /features-in-rooms} : get all the featuresInRooms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of featuresInRooms in body.
     */
    @GetMapping("/features-in-rooms")
    public ResponseEntity<List<FeaturesInRoom>> getAllFeaturesInRooms(FeaturesInRoomCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FeaturesInRooms by criteria: {}", criteria);
        Page<FeaturesInRoom> page = featuresInRoomQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /features-in-rooms/count} : count all the featuresInRooms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/features-in-rooms/count")
    public ResponseEntity<Long> countFeaturesInRooms(FeaturesInRoomCriteria criteria) {
        log.debug("REST request to count FeaturesInRooms by criteria: {}", criteria);
        return ResponseEntity.ok().body(featuresInRoomQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /features-in-rooms/:id} : get the "id" featuresInRoom.
     *
     * @param id the id of the featuresInRoom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the featuresInRoom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features-in-rooms/{id}")
    public ResponseEntity<FeaturesInRoom> getFeaturesInRoom(@PathVariable Long id) {
        log.debug("REST request to get FeaturesInRoom : {}", id);
        Optional<FeaturesInRoom> featuresInRoom = featuresInRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(featuresInRoom);
    }

    /**
     * {@code DELETE  /features-in-rooms/:id} : delete the "id" featuresInRoom.
     *
     * @param id the id of the featuresInRoom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features-in-rooms/{id}")
    public ResponseEntity<Void> deleteFeaturesInRoom(@PathVariable Long id) {
        log.debug("REST request to delete FeaturesInRoom : {}", id);
        featuresInRoomService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
