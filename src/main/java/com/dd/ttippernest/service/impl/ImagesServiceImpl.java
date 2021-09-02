package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.Images;
import com.dd.ttippernest.repository.ImagesRepository;
import com.dd.ttippernest.service.ImagesService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Images}.
 */
@Service
@Transactional
public class ImagesServiceImpl implements ImagesService {

    private final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);

    private final ImagesRepository imagesRepository;

    public ImagesServiceImpl(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    @Override
    public Images save(Images images) {
        log.debug("Request to save Images : {}", images);
        return imagesRepository.save(images);
    }

    @Override
    public Optional<Images> partialUpdate(Images images) {
        log.debug("Request to partially update Images : {}", images);

        return imagesRepository
            .findById(images.getId())
            .map(
                existingImages -> {
                    if (images.getImageUrl() != null) {
                        existingImages.setImageUrl(images.getImageUrl());
                    }
                    if (images.getCreatedBy() != null) {
                        existingImages.setCreatedBy(images.getCreatedBy());
                    }
                    if (images.getCreatedDate() != null) {
                        existingImages.setCreatedDate(images.getCreatedDate());
                    }
                    if (images.getUpdatedBy() != null) {
                        existingImages.setUpdatedBy(images.getUpdatedBy());
                    }
                    if (images.getUpdateDate() != null) {
                        existingImages.setUpdateDate(images.getUpdateDate());
                    }

                    return existingImages;
                }
            )
            .map(imagesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Images> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imagesRepository.findAll(pageable);
    }

    /**
     *  Get all the images where Like is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Images> findAllWhereLikeIsNull() {
        log.debug("Request to get all images where Like is null");
        return StreamSupport
            .stream(imagesRepository.findAll().spliterator(), false)
            .filter(images -> images.getLike() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Images> findOne(Long id) {
        log.debug("Request to get Images : {}", id);
        return imagesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Images : {}", id);
        imagesRepository.deleteById(id);
    }
}
