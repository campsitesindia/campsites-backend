package com.dd.campsites.service;

import com.dd.campsites.domain.Images;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Images}.
 */
public interface ImagesService {
    /**
     * Save a images.
     *
     * @param images the entity to save.
     * @return the persisted entity.
     */
    Images save(Images images);

    /**
     * Partially updates a images.
     *
     * @param images the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Images> partialUpdate(Images images);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Images> findAll(Pageable pageable);
    /**
     * Get all the Images where Like is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Images> findAllWhereLikeIsNull();

    /**
     * Get the "id" images.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Images> findOne(Long id);

    /**
     * Delete the "id" images.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
