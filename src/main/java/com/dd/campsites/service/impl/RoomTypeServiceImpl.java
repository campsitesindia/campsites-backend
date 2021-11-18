package com.dd.campsites.service.impl;

import com.dd.campsites.domain.RoomType;
import com.dd.campsites.repository.RoomTypeRepository;
import com.dd.campsites.service.RoomTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoomType}.
 */
@Service
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

    private final Logger log = LoggerFactory.getLogger(RoomTypeServiceImpl.class);

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public RoomType save(RoomType roomType) {
        log.debug("Request to save RoomType : {}", roomType);
        return roomTypeRepository.save(roomType);
    }

    @Override
    public Optional<RoomType> partialUpdate(RoomType roomType) {
        log.debug("Request to partially update RoomType : {}", roomType);

        return roomTypeRepository
            .findById(roomType.getId())
            .map(
                existingRoomType -> {
                    if (roomType.getType() != null) {
                        existingRoomType.setType(roomType.getType());
                    }
                    if (roomType.getDescription() != null) {
                        existingRoomType.setDescription(roomType.getDescription());
                    }
                    if (roomType.getMaxCapacity() != null) {
                        existingRoomType.setMaxCapacity(roomType.getMaxCapacity());
                    }
                    if (roomType.getNumberOfBeds() != null) {
                        existingRoomType.setNumberOfBeds(roomType.getNumberOfBeds());
                    }
                    if (roomType.getNumberOfBathrooms() != null) {
                        existingRoomType.setNumberOfBathrooms(roomType.getNumberOfBathrooms());
                    }
                    if (roomType.getRoomRatePerNight() != null) {
                        existingRoomType.setRoomRatePerNight(roomType.getRoomRatePerNight());
                    }
                    if (roomType.getRoomRateChildPerNight() != null) {
                        existingRoomType.setRoomRateChildPerNight(roomType.getRoomRateChildPerNight());
                    }
                    if (roomType.getCreatedBy() != null) {
                        existingRoomType.setCreatedBy(roomType.getCreatedBy());
                    }
                    if (roomType.getCreatedDate() != null) {
                        existingRoomType.setCreatedDate(roomType.getCreatedDate());
                    }
                    if (roomType.getUpdatedBy() != null) {
                        existingRoomType.setUpdatedBy(roomType.getUpdatedBy());
                    }
                    if (roomType.getUpdateDate() != null) {
                        existingRoomType.setUpdateDate(roomType.getUpdateDate());
                    }

                    return existingRoomType;
                }
            )
            .map(roomTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomType> findAll(Pageable pageable) {
        log.debug("Request to get all RoomTypes");
        return roomTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomType> findOne(Long id) {
        log.debug("Request to get RoomType : {}", id);
        return roomTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomType : {}", id);
        roomTypeRepository.deleteById(id);
    }
}
