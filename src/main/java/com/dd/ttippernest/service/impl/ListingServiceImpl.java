package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.Listing;
import com.dd.ttippernest.repository.ListingRepository;
import com.dd.ttippernest.service.ListingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Listing}.
 */
@Service
@Transactional
public class ListingServiceImpl implements ListingService {

    private final Logger log = LoggerFactory.getLogger(ListingServiceImpl.class);

    private final ListingRepository listingRepository;

    public ListingServiceImpl(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Override
    public Listing save(Listing listing) {
        log.debug("Request to save Listing : {}", listing);
        return listingRepository.save(listing);
    }

    @Override
    public Optional<Listing> partialUpdate(Listing listing) {
        log.debug("Request to partially update Listing : {}", listing);

        return listingRepository
            .findById(listing.getId())
            .map(
                existingListing -> {
                    if (listing.getAddress() != null) {
                        existingListing.setAddress(listing.getAddress());
                    }
                    if (listing.getLatitude() != null) {
                        existingListing.setLatitude(listing.getLatitude());
                    }
                    if (listing.getLongitude() != null) {
                        existingListing.setLongitude(listing.getLongitude());
                    }
                    if (listing.getUrl() != null) {
                        existingListing.setUrl(listing.getUrl());
                    }
                    if (listing.getTitle() != null) {
                        existingListing.setTitle(listing.getTitle());
                    }
                    if (listing.getContent() != null) {
                        existingListing.setContent(listing.getContent());
                    }
                    if (listing.getThumbnail() != null) {
                        existingListing.setThumbnail(listing.getThumbnail());
                    }
                    if (listing.getIsFeatured() != null) {
                        existingListing.setIsFeatured(listing.getIsFeatured());
                    }
                    if (listing.getPhone() != null) {
                        existingListing.setPhone(listing.getPhone());
                    }
                    if (listing.getEmail() != null) {
                        existingListing.setEmail(listing.getEmail());
                    }
                    if (listing.getWebsite() != null) {
                        existingListing.setWebsite(listing.getWebsite());
                    }
                    if (listing.getComment() != null) {
                        existingListing.setComment(listing.getComment());
                    }
                    if (listing.getDisableBooking() != null) {
                        existingListing.setDisableBooking(listing.getDisableBooking());
                    }
                    if (listing.getViewCount() != null) {
                        existingListing.setViewCount(listing.getViewCount());
                    }
                    if (listing.getCreatedBy() != null) {
                        existingListing.setCreatedBy(listing.getCreatedBy());
                    }
                    if (listing.getCreatedDate() != null) {
                        existingListing.setCreatedDate(listing.getCreatedDate());
                    }
                    if (listing.getUpdatedBy() != null) {
                        existingListing.setUpdatedBy(listing.getUpdatedBy());
                    }
                    if (listing.getUpdateDate() != null) {
                        existingListing.setUpdateDate(listing.getUpdateDate());
                    }

                    return existingListing;
                }
            )
            .map(listingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Listing> findAll(Pageable pageable) {
        log.debug("Request to get all Listings");
        return listingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Listing> findOne(Long id) {
        log.debug("Request to get Listing : {}", id);
        return listingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Listing : {}", id);
        listingRepository.deleteById(id);
    }
}
