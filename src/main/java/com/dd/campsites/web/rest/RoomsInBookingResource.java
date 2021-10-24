package com.dd.campsites.web.rest;

import com.dd.campsites.domain.RoomsInBooking;
import com.dd.campsites.repository.RoomsInBookingRepository;
import com.dd.campsites.service.RoomsInBookingQueryService;
import com.dd.campsites.service.RoomsInBookingService;
import com.dd.campsites.service.criteria.RoomsInBookingCriteria;
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
 * REST controller for managing {@link com.dd.campsites.domain.RoomsInBooking}.
 */
@RestController
@RequestMapping("/api")
public class RoomsInBookingResource {

    private final Logger log = LoggerFactory.getLogger(RoomsInBookingResource.class);

    private static final String ENTITY_NAME = "roomsInBooking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomsInBookingService roomsInBookingService;

    private final RoomsInBookingRepository roomsInBookingRepository;

    private final RoomsInBookingQueryService roomsInBookingQueryService;

    public RoomsInBookingResource(
        RoomsInBookingService roomsInBookingService,
        RoomsInBookingRepository roomsInBookingRepository,
        RoomsInBookingQueryService roomsInBookingQueryService
    ) {
        this.roomsInBookingService = roomsInBookingService;
        this.roomsInBookingRepository = roomsInBookingRepository;
        this.roomsInBookingQueryService = roomsInBookingQueryService;
    }

    /**
     * {@code POST  /rooms-in-bookings} : Create a new roomsInBooking.
     *
     * @param roomsInBooking the roomsInBooking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomsInBooking, or with status {@code 400 (Bad Request)} if the roomsInBooking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rooms-in-bookings")
    public ResponseEntity<RoomsInBooking> createRoomsInBooking(@RequestBody RoomsInBooking roomsInBooking) throws URISyntaxException {
        log.debug("REST request to save RoomsInBooking : {}", roomsInBooking);
        if (roomsInBooking.getId() != null) {
            throw new BadRequestAlertException("A new roomsInBooking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomsInBooking result = roomsInBookingService.save(roomsInBooking);
        return ResponseEntity
            .created(new URI("/api/rooms-in-bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rooms-in-bookings/:id} : Updates an existing roomsInBooking.
     *
     * @param id the id of the roomsInBooking to save.
     * @param roomsInBooking the roomsInBooking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomsInBooking,
     * or with status {@code 400 (Bad Request)} if the roomsInBooking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomsInBooking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rooms-in-bookings/{id}")
    public ResponseEntity<RoomsInBooking> updateRoomsInBooking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomsInBooking roomsInBooking
    ) throws URISyntaxException {
        log.debug("REST request to update RoomsInBooking : {}, {}", id, roomsInBooking);
        if (roomsInBooking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomsInBooking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomsInBookingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomsInBooking result = roomsInBookingService.save(roomsInBooking);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomsInBooking.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rooms-in-bookings/:id} : Partial updates given fields of an existing roomsInBooking, field will ignore if it is null
     *
     * @param id the id of the roomsInBooking to save.
     * @param roomsInBooking the roomsInBooking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomsInBooking,
     * or with status {@code 400 (Bad Request)} if the roomsInBooking is not valid,
     * or with status {@code 404 (Not Found)} if the roomsInBooking is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomsInBooking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rooms-in-bookings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RoomsInBooking> partialUpdateRoomsInBooking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoomsInBooking roomsInBooking
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoomsInBooking partially : {}, {}", id, roomsInBooking);
        if (roomsInBooking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomsInBooking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomsInBookingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomsInBooking> result = roomsInBookingService.partialUpdate(roomsInBooking);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roomsInBooking.getId().toString())
        );
    }

    /**
     * {@code GET  /rooms-in-bookings} : get all the roomsInBookings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roomsInBookings in body.
     */
    @GetMapping("/rooms-in-bookings")
    public ResponseEntity<List<RoomsInBooking>> getAllRoomsInBookings(RoomsInBookingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RoomsInBookings by criteria: {}", criteria);
        Page<RoomsInBooking> page = roomsInBookingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rooms-in-bookings/count} : count all the roomsInBookings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rooms-in-bookings/count")
    public ResponseEntity<Long> countRoomsInBookings(RoomsInBookingCriteria criteria) {
        log.debug("REST request to count RoomsInBookings by criteria: {}", criteria);
        return ResponseEntity.ok().body(roomsInBookingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rooms-in-bookings/:id} : get the "id" roomsInBooking.
     *
     * @param id the id of the roomsInBooking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomsInBooking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rooms-in-bookings/{id}")
    public ResponseEntity<RoomsInBooking> getRoomsInBooking(@PathVariable Long id) {
        log.debug("REST request to get RoomsInBooking : {}", id);
        Optional<RoomsInBooking> roomsInBooking = roomsInBookingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomsInBooking);
    }

    /**
     * {@code DELETE  /rooms-in-bookings/:id} : delete the "id" roomsInBooking.
     *
     * @param id the id of the roomsInBooking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rooms-in-bookings/{id}")
    public ResponseEntity<Void> deleteRoomsInBooking(@PathVariable Long id) {
        log.debug("REST request to delete RoomsInBooking : {}", id);
        roomsInBookingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
