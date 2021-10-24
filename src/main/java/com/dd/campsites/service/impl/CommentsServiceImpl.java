package com.dd.campsites.service.impl;

import com.dd.campsites.domain.Comments;
import com.dd.campsites.repository.CommentsRepository;
import com.dd.campsites.service.CommentsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comments}.
 */
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final Logger log = LoggerFactory.getLogger(CommentsServiceImpl.class);

    private final CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Comments save(Comments comments) {
        log.debug("Request to save Comments : {}", comments);
        return commentsRepository.save(comments);
    }

    @Override
    public Optional<Comments> partialUpdate(Comments comments) {
        log.debug("Request to partially update Comments : {}", comments);

        return commentsRepository
            .findById(comments.getId())
            .map(
                existingComments -> {
                    if (comments.getCommentText() != null) {
                        existingComments.setCommentText(comments.getCommentText());
                    }
                    if (comments.getCreatedBy() != null) {
                        existingComments.setCreatedBy(comments.getCreatedBy());
                    }
                    if (comments.getCreatedDate() != null) {
                        existingComments.setCreatedDate(comments.getCreatedDate());
                    }
                    if (comments.getUpdatedBy() != null) {
                        existingComments.setUpdatedBy(comments.getUpdatedBy());
                    }
                    if (comments.getUpdateDate() != null) {
                        existingComments.setUpdateDate(comments.getUpdateDate());
                    }

                    return existingComments;
                }
            )
            .map(commentsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comments> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comments> findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        return commentsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comments : {}", id);
        commentsRepository.deleteById(id);
    }
}
