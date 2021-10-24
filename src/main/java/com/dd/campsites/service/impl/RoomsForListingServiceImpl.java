package com.dd.campsites.service.impl;

import com.dd.campsites.domain.RoomsForListing;
import com.dd.campsites.repository.RoomsForListingRepository;
import com.dd.campsites.service.RoomsForListingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoomsForListing}.
 */
@Service
@Transactional
public class RoomsForListingServiceImpl implements RoomsForListingService {

    private final Logger log = LoggerFactory.getLogger(RoomsForListingServiceImpl.class);

    private final RoomsForListingRepository roomsForListingRepository;

    public RoomsForListingServiceImpl(RoomsForListingRepository roomsForListingRepository) {
        this.roomsForListingRepository = roomsForListingRepository;
    }

    @Override
    public RoomsForListing save(RoomsForListing roomsForListing) {
        log.debug("Request to save RoomsForListing : {}", roomsForListing);
        return roomsForListingRepository.save(roomsForListing);
    }

    @Override
    public Optional<RoomsForListing> partialUpdate(RoomsForListing roomsForListing) {
        log.debug("Request to partially update RoomsForListing : {}", roomsForListing);

        return roomsForListingRepository
            .findById(roomsForListing.getId())
            .map(
                existingRoomsForListing -> {
                    return existingRoomsForListing;
                }
            )
            .map(roomsForListingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomsForListing> findAll(Pageable pageable) {
        log.debug("Request to get all RoomsForListings");
        return roomsForListingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomsForListing> findOne(Long id) {
        log.debug("Request to get RoomsForListing : {}", id);
        return roomsForListingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomsForListing : {}", id);
        roomsForListingRepository.deleteById(id);
    }
}
