package com.dd.campsites.service.impl;

import com.dd.campsites.domain.FeaturesInRoom;
import com.dd.campsites.repository.FeaturesInRoomRepository;
import com.dd.campsites.service.FeaturesInRoomService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeaturesInRoom}.
 */
@Service
@Transactional
public class FeaturesInRoomServiceImpl implements FeaturesInRoomService {

    private final Logger log = LoggerFactory.getLogger(FeaturesInRoomServiceImpl.class);

    private final FeaturesInRoomRepository featuresInRoomRepository;

    public FeaturesInRoomServiceImpl(FeaturesInRoomRepository featuresInRoomRepository) {
        this.featuresInRoomRepository = featuresInRoomRepository;
    }

    @Override
    public FeaturesInRoom save(FeaturesInRoom featuresInRoom) {
        log.debug("Request to save FeaturesInRoom : {}", featuresInRoom);
        return featuresInRoomRepository.save(featuresInRoom);
    }

    @Override
    public Optional<FeaturesInRoom> partialUpdate(FeaturesInRoom featuresInRoom) {
        log.debug("Request to partially update FeaturesInRoom : {}", featuresInRoom);

        return featuresInRoomRepository
            .findById(featuresInRoom.getId())
            .map(
                existingFeaturesInRoom -> {
                    return existingFeaturesInRoom;
                }
            )
            .map(featuresInRoomRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeaturesInRoom> findAll(Pageable pageable) {
        log.debug("Request to get all FeaturesInRooms");
        return featuresInRoomRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeaturesInRoom> findOne(Long id) {
        log.debug("Request to get FeaturesInRoom : {}", id);
        return featuresInRoomRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeaturesInRoom : {}", id);
        featuresInRoomRepository.deleteById(id);
    }
}
