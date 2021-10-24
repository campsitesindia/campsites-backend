package com.dd.campsites.service;

import com.dd.campsites.domain.FeaturesInRoom;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FeaturesInRoom}.
 */
public interface FeaturesInRoomService {
    /**
     * Save a featuresInRoom.
     *
     * @param featuresInRoom the entity to save.
     * @return the persisted entity.
     */
    FeaturesInRoom save(FeaturesInRoom featuresInRoom);

    /**
     * Partially updates a featuresInRoom.
     *
     * @param featuresInRoom the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeaturesInRoom> partialUpdate(FeaturesInRoom featuresInRoom);

    /**
     * Get all the featuresInRooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeaturesInRoom> findAll(Pageable pageable);

    /**
     * Get the "id" featuresInRoom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeaturesInRoom> findOne(Long id);

    /**
     * Delete the "id" featuresInRoom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
