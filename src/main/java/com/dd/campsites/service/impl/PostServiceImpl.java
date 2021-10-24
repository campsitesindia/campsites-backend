package com.dd.campsites.service.impl;

import com.dd.campsites.domain.Post;
import com.dd.campsites.repository.PostRepository;
import com.dd.campsites.service.PostService;
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
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(Post post) {
        log.debug("Request to save Post : {}", post);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> partialUpdate(Post post) {
        log.debug("Request to partially update Post : {}", post);

        return postRepository
            .findById(post.getId())
            .map(
                existingPost -> {
                    if (post.getContent() != null) {
                        existingPost.setContent(post.getContent());
                    }
                    if (post.getCreatedBy() != null) {
                        existingPost.setCreatedBy(post.getCreatedBy());
                    }
                    if (post.getCreatedDate() != null) {
                        existingPost.setCreatedDate(post.getCreatedDate());
                    }
                    if (post.getUpdatedBy() != null) {
                        existingPost.setUpdatedBy(post.getUpdatedBy());
                    }
                    if (post.getUpdateDate() != null) {
                        existingPost.setUpdateDate(post.getUpdateDate());
                    }

                    return existingPost;
                }
            )
            .map(postRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable);
    }

    /**
     *  Get all the posts where Like is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Post> findAllWhereLikeIsNull() {
        log.debug("Request to get all posts where Like is null");
        return StreamSupport
            .stream(postRepository.findAll().spliterator(), false)
            .filter(post -> post.getLike() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }
}
