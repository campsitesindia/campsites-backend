package com.dd.campsites.service;

import com.dd.campsites.domain.RoomsInBooking;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RoomsInBooking}.
 */
public interface RoomsInBookingService {
    /**
     * Save a roomsInBooking.
     *
     * @param roomsInBooking the entity to save.
     * @return the persisted entity.
     */
    RoomsInBooking save(RoomsInBooking roomsInBooking);

    /**
     * Partially updates a roomsInBooking.
     *
     * @param roomsInBooking the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomsInBooking> partialUpdate(RoomsInBooking roomsInBooking);

    /**
     * Get all the roomsInBookings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoomsInBooking> findAll(Pageable pageable);

    /**
     * Get the "id" roomsInBooking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomsInBooking> findOne(Long id);

    /**
     * Delete the "id" roomsInBooking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
