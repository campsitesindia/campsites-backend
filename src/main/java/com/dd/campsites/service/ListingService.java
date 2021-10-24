package com.dd.campsites.service;

import com.dd.campsites.domain.Listing;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Listing}.
 */
public interface ListingService {
    /**
     * Save a listing.
     *
     * @param listing the entity to save.
     * @return the persisted entity.
     */
    Listing save(Listing listing);

    /**
     * Partially updates a listing.
     *
     * @param listing the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Listing> partialUpdate(Listing listing);

    /**
     * Get all the listings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Listing> findAll(Pageable pageable);

    /**
     * Get the "id" listing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Listing> findOne(Long id);

    /**
     * Delete the "id" listing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
