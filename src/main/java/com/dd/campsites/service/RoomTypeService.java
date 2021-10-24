package com.dd.campsites.service;

import com.dd.campsites.domain.RoomType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RoomType}.
 */
public interface RoomTypeService {
    /**
     * Save a roomType.
     *
     * @param roomType the entity to save.
     * @return the persisted entity.
     */
    RoomType save(RoomType roomType);

    /**
     * Partially updates a roomType.
     *
     * @param roomType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomType> partialUpdate(RoomType roomType);

    /**
     * Get all the roomTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoomType> findAll(Pageable pageable);

    /**
     * Get the "id" roomType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomType> findOne(Long id);

    /**
     * Delete the "id" roomType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
