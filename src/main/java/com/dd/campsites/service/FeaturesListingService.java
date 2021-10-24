package com.dd.campsites.service;

import com.dd.campsites.domain.FeaturesListing;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FeaturesListing}.
 */
public interface FeaturesListingService {
    /**
     * Save a featuresListing.
     *
     * @param featuresListing the entity to save.
     * @return the persisted entity.
     */
    FeaturesListing save(FeaturesListing featuresListing);

    /**
     * Partially updates a featuresListing.
     *
     * @param featuresListing the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeaturesListing> partialUpdate(FeaturesListing featuresListing);

    /**
     * Get all the featuresListings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeaturesListing> findAll(Pageable pageable);

    /**
     * Get the "id" featuresListing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeaturesListing> findOne(Long id);

    /**
     * Delete the "id" featuresListing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
