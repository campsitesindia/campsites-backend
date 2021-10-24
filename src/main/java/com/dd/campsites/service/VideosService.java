package com.dd.campsites.service;

import com.dd.campsites.domain.Videos;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Videos}.
 */
public interface VideosService {
    /**
     * Save a videos.
     *
     * @param videos the entity to save.
     * @return the persisted entity.
     */
    Videos save(Videos videos);

    /**
     * Partially updates a videos.
     *
     * @param videos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Videos> partialUpdate(Videos videos);

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Videos> findAll(Pageable pageable);

    /**
     * Get the "id" videos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Videos> findOne(Long id);

    /**
     * Delete the "id" videos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
