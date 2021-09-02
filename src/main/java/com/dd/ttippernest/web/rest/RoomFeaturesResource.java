package com.dd.ttippernest.web.rest;

import com.dd.ttippernest.domain.RoomFeatures;
import com.dd.ttippernest.repository.RoomFeaturesRepository;
import com.dd.ttippernest.service.RoomFeaturesQueryService;
import com.dd.ttippernest.service.RoomFeaturesService;
import com.dd.ttippernest.service.criteria.RoomFeaturesCriteria;
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
 * REST controller for managing {@link com.dd.ttippernest.domain.RoomFeatures}.
 */
@RestController
@RequestMapping("/api")
public class RoomFeaturesResource {

    private final Logger log = LoggerFactory.getLogger(RoomFeaturesResource.class);

    private static final String ENTITY_NAME = "roomFeatures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomFeaturesService roomFeaturesService;

    private final RoomFeaturesRepository roomFeaturesRepository;

    private final RoomFeaturesQueryService roomFeaturesQueryService;

    public RoomFeaturesResource(
        RoomFeaturesService roomFeaturesService,
        RoomFeaturesRepository roomFeaturesRepository,
        RoomFeaturesQueryService roomFeaturesQueryService
    ) {
        this.roomFeaturesService = roomFeaturesService;
        this.roomFeaturesRepository = roomFeaturesRepository;
        this.roomFeaturesQueryService = roomFeaturesQueryService;
    }

    /**
     * {@code POST  /room-features} : Create a new roomFeatures.
     *
     * @param roomFeatures the roomFeatures to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomFeatures, or with status {@code 400 (Bad Request)} if the roomFeatures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/room-features")
    public ResponseEntity<RoomFeatures> createRoomFeatures(@RequestBody RoomFeatures roomFeatures) throws URISyntaxException {
        log.debug("REST request to save RoomFeatures : {}", roomFeatures);
        if (roomFeatures.getId() != null) {
            throw new BadRequestAlertException("A new roomFeatures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomFeatures result = roomFeaturesService.save(roomFeatures);
        return ResponseEntity
            .created(new URI("/api/room-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /room-features/:id} : Updates an existing roomFeatures.
     *
     * @param id the id of the roomFeatures to save.
     * @param roomFeatures the roomFeatures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomFeatures,
     * or with status {@code 400 (Bad Request)} if the roomFeatures is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomFeatures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/room-features/{id}")
    public ResponseEntity<RoomFeatures> updateRoomFeatures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomFeatures roomFeatures
    ) throws URISyntaxException {
        log.debug("REST request to update RoomFeatures : {}, {}", id, roomFeatures);
        if (roomFeatures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomFeatures.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomFeaturesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomFeatures result = roomFeaturesService.save(roomFeatures);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomFeatures.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /room-features/:id} : Partial updates given fields of an existing roomFeatures, field will ignore if it is null
     *
     * @param id the id of the roomFeatures to save.
     * @param roomFeatures the roomFeatures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomFeatures,
     * or with status {@code 400 (Bad Request)} if the roomFeatures is not valid,
     * or with status {@code 404 (Not Found)} if the roomFeatures is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomFeatures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/room-features/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RoomFeatures> partialUpdateRoomFeatures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomFeatures roomFeatures
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoomFeatures partially : {}, {}", id, roomFeatures);
        if (roomFeatures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomFeatures.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomFeaturesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomFeatures> result = roomFeaturesService.partialUpdate(roomFeatures);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomFeatures.getId().toString())
        );
    }

    /**
     * {@code GET  /room-features} : get all the roomFeatures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roomFeatures in body.
     */
    @GetMapping("/room-features")
    public ResponseEntity<List<RoomFeatures>> getAllRoomFeatures(RoomFeaturesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RoomFeatures by criteria: {}", criteria);
        Page<RoomFeatures> page = roomFeaturesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /room-features/count} : count all the roomFeatures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/room-features/count")
    public ResponseEntity<Long> countRoomFeatures(RoomFeaturesCriteria criteria) {
        log.debug("REST request to count RoomFeatures by criteria: {}", criteria);
        return ResponseEntity.ok().body(roomFeaturesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /room-features/:id} : get the "id" roomFeatures.
     *
     * @param id the id of the roomFeatures to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomFeatures, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/room-features/{id}")
    public ResponseEntity<RoomFeatures> getRoomFeatures(@PathVariable Long id) {
        log.debug("REST request to get RoomFeatures : {}", id);
        Optional<RoomFeatures> roomFeatures = roomFeaturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomFeatures);
    }

    /**
     * {@code DELETE  /room-features/:id} : delete the "id" roomFeatures.
     *
     * @param id the id of the roomFeatures to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/room-features/{id}")
    public ResponseEntity<Void> deleteRoomFeatures(@PathVariable Long id) {
        log.debug("REST request to delete RoomFeatures : {}", id);
        roomFeaturesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
