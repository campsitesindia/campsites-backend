package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.Like;
import com.dd.ttippernest.repository.LikeRepository;
import com.dd.ttippernest.service.LikeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Like}.
 */
@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    private final Logger log = LoggerFactory.getLogger(LikeServiceImpl.class);

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public Like save(Like like) {
        log.debug("Request to save Like : {}", like);
        return likeRepository.save(like);
    }

    @Override
    public Optional<Like> partialUpdate(Like like) {
        log.debug("Request to partially update Like : {}", like);

        return likeRepository
            .findById(like.getId())
            .map(
                existingLike -> {
                    if (like.getCreatedBy() != null) {
                        existingLike.setCreatedBy(like.getCreatedBy());
                    }
                    if (like.getCreatedDate() != null) {
                        existingLike.setCreatedDate(like.getCreatedDate());
                    }
                    if (like.getUpdatedBy() != null) {
                        existingLike.setUpdatedBy(like.getUpdatedBy());
                    }
                    if (like.getUpdateDate() != null) {
                        existingLike.setUpdateDate(like.getUpdateDate());
                    }

                    return existingLike;
                }
            )
            .map(likeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Like> findAll(Pageable pageable) {
        log.debug("Request to get all Likes");
        return likeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Like> findOne(Long id) {
        log.debug("Request to get Like : {}", id);
        return likeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Like : {}", id);
        likeRepository.deleteById(id);
    }
}
