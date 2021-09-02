package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.Followers;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Followers}.
 */
public interface FollowersService {
    /**
     * Save a followers.
     *
     * @param followers the entity to save.
     * @return the persisted entity.
     */
    Followers save(Followers followers);

    /**
     * Partially updates a followers.
     *
     * @param followers the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Followers> partialUpdate(Followers followers);

    /**
     * Get all the followers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Followers> findAll(Pageable pageable);

    /**
     * Get the "id" followers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Followers> findOne(Long id);

    /**
     * Delete the "id" followers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
