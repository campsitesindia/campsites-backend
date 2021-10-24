package com.dd.campsites.service;

import com.dd.campsites.domain.RoomsForListing;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RoomsForListing}.
 */
public interface RoomsForListingService {
    /**
     * Save a roomsForListing.
     *
     * @param roomsForListing the entity to save.
     * @return the persisted entity.
     */
    RoomsForListing save(RoomsForListing roomsForListing);

    /**
     * Partially updates a roomsForListing.
     *
     * @param roomsForListing the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomsForListing> partialUpdate(RoomsForListing roomsForListing);

    /**
     * Get all the roomsForListings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoomsForListing> findAll(Pageable pageable);

    /**
     * Get the "id" roomsForListing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomsForListing> findOne(Long id);

    /**
     * Delete the "id" roomsForListing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
