package com.dd.campsites.service.impl;

import com.dd.campsites.domain.FeaturesListing;
import com.dd.campsites.repository.FeaturesListingRepository;
import com.dd.campsites.service.FeaturesListingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeaturesListing}.
 */
@Service
@Transactional
public class FeaturesListingServiceImpl implements FeaturesListingService {

    private final Logger log = LoggerFactory.getLogger(FeaturesListingServiceImpl.class);

    private final FeaturesListingRepository featuresListingRepository;

    public FeaturesListingServiceImpl(FeaturesListingRepository featuresListingRepository) {
        this.featuresListingRepository = featuresListingRepository;
    }

    @Override
    public FeaturesListing save(FeaturesListing featuresListing) {
        log.debug("Request to save FeaturesListing : {}", featuresListing);
        return featuresListingRepository.save(featuresListing);
    }

    @Override
    public Optional<FeaturesListing> partialUpdate(FeaturesListing featuresListing) {
        log.debug("Request to partially update FeaturesListing : {}", featuresListing);

        return featuresListingRepository
            .findById(featuresListing.getId())
            .map(
                existingFeaturesListing -> {
                    return existingFeaturesListing;
                }
            )
            .map(featuresListingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeaturesListing> findAll(Pageable pageable) {
        log.debug("Request to get all FeaturesListings");
        return featuresListingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeaturesListing> findOne(Long id) {
        log.debug("Request to get FeaturesListing : {}", id);
        return featuresListingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeaturesListing : {}", id);
        featuresListingRepository.deleteById(id);
    }
}
