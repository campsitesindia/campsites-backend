package com.dd.campsites.service.impl;

import com.dd.campsites.domain.Videos;
import com.dd.campsites.repository.VideosRepository;
import com.dd.campsites.service.VideosService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Videos}.
 */
@Service
@Transactional
public class VideosServiceImpl implements VideosService {

    private final Logger log = LoggerFactory.getLogger(VideosServiceImpl.class);

    private final VideosRepository videosRepository;

    public VideosServiceImpl(VideosRepository videosRepository) {
        this.videosRepository = videosRepository;
    }

    @Override
    public Videos save(Videos videos) {
        log.debug("Request to save Videos : {}", videos);
        return videosRepository.save(videos);
    }

    @Override
    public Optional<Videos> partialUpdate(Videos videos) {
        log.debug("Request to partially update Videos : {}", videos);

        return videosRepository
            .findById(videos.getId())
            .map(
                existingVideos -> {
                    if (videos.getName() != null) {
                        existingVideos.setName(videos.getName());
                    }
                    if (videos.getUrl() != null) {
                        existingVideos.setUrl(videos.getUrl());
                    }
                    if (videos.getCreatedBy() != null) {
                        existingVideos.setCreatedBy(videos.getCreatedBy());
                    }
                    if (videos.getCreatedDate() != null) {
                        existingVideos.setCreatedDate(videos.getCreatedDate());
                    }
                    if (videos.getUpdatedBy() != null) {
                        existingVideos.setUpdatedBy(videos.getUpdatedBy());
                    }
                    if (videos.getUpdateDate() != null) {
                        existingVideos.setUpdateDate(videos.getUpdateDate());
                    }

                    return existingVideos;
                }
            )
            .map(videosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Videos> findAll(Pageable pageable) {
        log.debug("Request to get all Videos");
        return videosRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Videos> findOne(Long id) {
        log.debug("Request to get Videos : {}", id);
        return videosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Videos : {}", id);
        videosRepository.deleteById(id);
    }
}
