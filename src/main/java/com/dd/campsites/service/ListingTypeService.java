package com.dd.campsites.service;

import com.dd.campsites.domain.ListingType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ListingType}.
 */
public interface ListingTypeService {
    /**
     * Save a listingType.
     *
     * @param listingType the entity to save.
     * @return the persisted entity.
     */
    ListingType save(ListingType listingType);

    /**
     * Partially updates a listingType.
     *
     * @param listingType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ListingType> partialUpdate(ListingType listingType);

    /**
     * Get all the listingTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ListingType> findAll(Pageable pageable);

    /**
     * Get the "id" listingType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ListingType> findOne(Long id);

    /**
     * Delete the "id" listingType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
