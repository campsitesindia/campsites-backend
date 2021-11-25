package com.dd.campsites.service.impl;

import com.dd.campsites.domain.Photos;
import com.dd.campsites.repository.PhotosRepository;
import com.dd.campsites.service.PhotosService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Photos}.
 */
@Service
@Transactional
public class PhotosServiceImpl implements PhotosService {

    private final Logger log = LoggerFactory.getLogger(PhotosServiceImpl.class);

    private final PhotosRepository photosRepository;

    public PhotosServiceImpl(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    @Override
    public Photos save(Photos photos) {
        log.debug("Request to save Photos : {}", photos);
        return photosRepository.save(photos);
    }

    @Override
    public Optional<Photos> partialUpdate(Photos photos) {
        log.debug("Request to partially update Photos : {}", photos);

        return photosRepository
            .findById(photos.getId())
            .map(
                existingPhotos -> {
                    if (photos.getAlt() != null) {
                        existingPhotos.setAlt(photos.getAlt());
                    }
                    if (photos.getCaption() != null) {
                        existingPhotos.setCaption(photos.getCaption());
                    }
                    if (photos.getDescription() != null) {
                        existingPhotos.setDescription(photos.getDescription());
                    }
                    if (photos.getHref() != null) {
                        existingPhotos.setHref(photos.getHref());
                    }
                    if (photos.getSrc() != null) {
                        existingPhotos.setSrc(photos.getSrc());
                    }
                    if (photos.getTitle() != null) {
                        existingPhotos.setTitle(photos.getTitle());
                    }
                    if (photos.getImage() != null) {
                        existingPhotos.setImage(photos.getImage());
                    }
                    if (photos.getImageContentType() != null) {
                        existingPhotos.setImageContentType(photos.getImageContentType());
                    }
                    if (photos.getIsCoverImage() != null) {
                        existingPhotos.setIsCoverImage(photos.getIsCoverImage());
                    }
                    if (photos.getHeight() != null) {
                        existingPhotos.setHeight(photos.getHeight());
                    }
                    if (photos.getWidth() != null) {
                        existingPhotos.setWidth(photos.getWidth());
                    }
                    if (photos.getTaken() != null) {
                        existingPhotos.setTaken(photos.getTaken());
                    }
                    if (photos.getUploaded() != null) {
                        existingPhotos.setUploaded(photos.getUploaded());
                    }
                    if (photos.getCreatedBy() != null) {
                        existingPhotos.setCreatedBy(photos.getCreatedBy());
                    }
                    if (photos.getCreatedDate() != null) {
                        existingPhotos.setCreatedDate(photos.getCreatedDate());
                    }
                    if (photos.getUpdatedBy() != null) {
                        existingPhotos.setUpdatedBy(photos.getUpdatedBy());
                    }
                    if (photos.getUpdateDate() != null) {
                        existingPhotos.setUpdateDate(photos.getUpdateDate());
                    }

                    return existingPhotos;
                }
            )
            .map(photosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Photos> findAll(Pageable pageable) {
        log.debug("Request to get all Photos");
        return photosRepository.findAll(pageable);
    }

    public Page<Photos> findAllWithEagerRelationships(Pageable pageable) {
        return photosRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Photos> findOne(Long id) {
        log.debug("Request to get Photos : {}", id);
        return photosRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Photos : {}", id);
        photosRepository.deleteById(id);
    }
}
