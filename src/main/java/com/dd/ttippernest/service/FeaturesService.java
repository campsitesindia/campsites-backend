package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.Features;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Features}.
 */
public interface FeaturesService {
    /**
     * Save a features.
     *
     * @param features the entity to save.
     * @return the persisted entity.
     */
    Features save(Features features);

    /**
     * Partially updates a features.
     *
     * @param features the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Features> partialUpdate(Features features);

    /**
     * Get all the features.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Features> findAll(Pageable pageable);

    /**
     * Get the "id" features.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Features> findOne(Long id);

    /**
     * Delete the "id" features.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
