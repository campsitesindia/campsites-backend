package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.Photos;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Photos}.
 */
public interface PhotosService {
    /**
     * Save a photos.
     *
     * @param photos the entity to save.
     * @return the persisted entity.
     */
    Photos save(Photos photos);

    /**
     * Partially updates a photos.
     *
     * @param photos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Photos> partialUpdate(Photos photos);

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Photos> findAll(Pageable pageable);

    /**
     * Get the "id" photos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Photos> findOne(Long id);

    /**
     * Delete the "id" photos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
