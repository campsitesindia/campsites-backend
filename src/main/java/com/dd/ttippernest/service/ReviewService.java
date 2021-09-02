package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.Review;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Review}.
 */
public interface ReviewService {
    /**
     * Save a review.
     *
     * @param review the entity to save.
     * @return the persisted entity.
     */
    Review save(Review review);

    /**
     * Partially updates a review.
     *
     * @param review the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Review> partialUpdate(Review review);

    /**
     * Get all the reviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Review> findAll(Pageable pageable);

    /**
     * Get the "id" review.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Review> findOne(Long id);

    /**
     * Delete the "id" review.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
