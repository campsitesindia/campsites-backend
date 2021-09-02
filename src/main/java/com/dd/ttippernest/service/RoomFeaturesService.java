package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.RoomFeatures;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RoomFeatures}.
 */
public interface RoomFeaturesService {
    /**
     * Save a roomFeatures.
     *
     * @param roomFeatures the entity to save.
     * @return the persisted entity.
     */
    RoomFeatures save(RoomFeatures roomFeatures);

    /**
     * Partially updates a roomFeatures.
     *
     * @param roomFeatures the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomFeatures> partialUpdate(RoomFeatures roomFeatures);

    /**
     * Get all the roomFeatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoomFeatures> findAll(Pageable pageable);

    /**
     * Get the "id" roomFeatures.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomFeatures> findOne(Long id);

    /**
     * Delete the "id" roomFeatures.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
