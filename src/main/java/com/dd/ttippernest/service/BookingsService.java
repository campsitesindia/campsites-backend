package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.Bookings;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Bookings}.
 */
public interface BookingsService {
    /**
     * Save a bookings.
     *
     * @param bookings the entity to save.
     * @return the persisted entity.
     */
    Bookings save(Bookings bookings);

    /**
     * Partially updates a bookings.
     *
     * @param bookings the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bookings> partialUpdate(Bookings bookings);

    /**
     * Get all the bookings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Bookings> findAll(Pageable pageable);

    /**
     * Get the "id" bookings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bookings> findOne(Long id);

    /**
     * Delete the "id" bookings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
