package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.RoomFeatures;
import com.dd.ttippernest.repository.RoomFeaturesRepository;
import com.dd.ttippernest.service.RoomFeaturesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoomFeatures}.
 */
@Service
@Transactional
public class RoomFeaturesServiceImpl implements RoomFeaturesService {

    private final Logger log = LoggerFactory.getLogger(RoomFeaturesServiceImpl.class);

    private final RoomFeaturesRepository roomFeaturesRepository;

    public RoomFeaturesServiceImpl(RoomFeaturesRepository roomFeaturesRepository) {
        this.roomFeaturesRepository = roomFeaturesRepository;
    }

    @Override
    public RoomFeatures save(RoomFeatures roomFeatures) {
        log.debug("Request to save RoomFeatures : {}", roomFeatures);
        return roomFeaturesRepository.save(roomFeatures);
    }

    @Override
    public Optional<RoomFeatures> partialUpdate(RoomFeatures roomFeatures) {
        log.debug("Request to partially update RoomFeatures : {}", roomFeatures);

        return roomFeaturesRepository
            .findById(roomFeatures.getId())
            .map(
                existingRoomFeatures -> {
                    if (roomFeatures.getTitle() != null) {
                        existingRoomFeatures.setTitle(roomFeatures.getTitle());
                    }
                    if (roomFeatures.getCount() != null) {
                        existingRoomFeatures.setCount(roomFeatures.getCount());
                    }
                    if (roomFeatures.getThumbnail() != null) {
                        existingRoomFeatures.setThumbnail(roomFeatures.getThumbnail());
                    }
                    if (roomFeatures.getIcon() != null) {
                        existingRoomFeatures.setIcon(roomFeatures.getIcon());
                    }
                    if (roomFeatures.getColor() != null) {
                        existingRoomFeatures.setColor(roomFeatures.getColor());
                    }
                    if (roomFeatures.getImgIcon() != null) {
                        existingRoomFeatures.setImgIcon(roomFeatures.getImgIcon());
                    }
                    if (roomFeatures.getDescription() != null) {
                        existingRoomFeatures.setDescription(roomFeatures.getDescription());
                    }
                    if (roomFeatures.getParent() != null) {
                        existingRoomFeatures.setParent(roomFeatures.getParent());
                    }
                    if (roomFeatures.getTaxonomy() != null) {
                        existingRoomFeatures.setTaxonomy(roomFeatures.getTaxonomy());
                    }
                    if (roomFeatures.getCreatedBy() != null) {
                        existingRoomFeatures.setCreatedBy(roomFeatures.getCreatedBy());
                    }
                    if (roomFeatures.getCreatedDate() != null) {
                        existingRoomFeatures.setCreatedDate(roomFeatures.getCreatedDate());
                    }
                    if (roomFeatures.getUpdatedBy() != null) {
                        existingRoomFeatures.setUpdatedBy(roomFeatures.getUpdatedBy());
                    }
                    if (roomFeatures.getUpdateDate() != null) {
                        existingRoomFeatures.setUpdateDate(roomFeatures.getUpdateDate());
                    }

                    return existingRoomFeatures;
                }
            )
            .map(roomFeaturesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomFeatures> findAll(Pageable pageable) {
        log.debug("Request to get all RoomFeatures");
        return roomFeaturesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomFeatures> findOne(Long id) {
        log.debug("Request to get RoomFeatures : {}", id);
        return roomFeaturesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomFeatures : {}", id);
        roomFeaturesRepository.deleteById(id);
    }
}
