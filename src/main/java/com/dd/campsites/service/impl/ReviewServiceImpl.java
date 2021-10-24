package com.dd.campsites.service.impl;

import com.dd.campsites.domain.Review;
import com.dd.campsites.repository.ReviewRepository;
import com.dd.campsites.service.ReviewService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Review}.
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review save(Review review) {
        log.debug("Request to save Review : {}", review);
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> partialUpdate(Review review) {
        log.debug("Request to partially update Review : {}", review);

        return reviewRepository
            .findById(review.getId())
            .map(
                existingReview -> {
                    if (review.getRating() != null) {
                        existingReview.setRating(review.getRating());
                    }
                    if (review.getReviewbBody() != null) {
                        existingReview.setReviewbBody(review.getReviewbBody());
                    }
                    if (review.getCreatedBy() != null) {
                        existingReview.setCreatedBy(review.getCreatedBy());
                    }
                    if (review.getCreatedDate() != null) {
                        existingReview.setCreatedDate(review.getCreatedDate());
                    }
                    if (review.getUpdatedBy() != null) {
                        existingReview.setUpdatedBy(review.getUpdatedBy());
                    }
                    if (review.getUpdateDate() != null) {
                        existingReview.setUpdateDate(review.getUpdateDate());
                    }

                    return existingReview;
                }
            )
            .map(reviewRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> findAll(Pageable pageable) {
        log.debug("Request to get all Reviews");
        return reviewRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> findOne(Long id) {
        log.debug("Request to get Review : {}", id);
        return reviewRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.deleteById(id);
    }
}
