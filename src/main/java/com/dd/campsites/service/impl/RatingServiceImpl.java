package com.dd.campsites.service.impl;

import com.dd.campsites.domain.Rating;
import com.dd.campsites.repository.RatingRepository;
import com.dd.campsites.service.RatingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rating}.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating save(Rating rating) {
        log.debug("Request to save Rating : {}", rating);
        return ratingRepository.save(rating);
    }

    @Override
    public Optional<Rating> partialUpdate(Rating rating) {
        log.debug("Request to partially update Rating : {}", rating);

        return ratingRepository
            .findById(rating.getId())
            .map(
                existingRating -> {
                    if (rating.getValue() != null) {
                        existingRating.setValue(rating.getValue());
                    }
                    if (rating.getName() != null) {
                        existingRating.setName(rating.getName());
                    }

                    return existingRating;
                }
            )
            .map(ratingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rating> findAll(Pageable pageable) {
        log.debug("Request to get all Ratings");
        return ratingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rating> findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        return ratingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
    }
}
