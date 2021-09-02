package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.Rating;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Rating}.
 */
public interface RatingService {
    /**
     * Save a rating.
     *
     * @param rating the entity to save.
     * @return the persisted entity.
     */
    Rating save(Rating rating);

    /**
     * Partially updates a rating.
     *
     * @param rating the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Rating> partialUpdate(Rating rating);

    /**
     * Get all the ratings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Rating> findAll(Pageable pageable);

    /**
     * Get the "id" rating.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rating> findOne(Long id);

    /**
     * Delete the "id" rating.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
