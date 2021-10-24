package com.dd.campsites.service.campsitesindia;

import com.dd.campsites.domain.*;
import com.dd.campsites.repository.ListingRepository;
import com.dd.campsites.repository.PhotosRepository;
import com.dd.campsites.repository.RatingRepository;
import com.dd.campsites.repository.ReviewRepository;
import com.dd.campsites.service.ListingQueryService;
import com.dd.campsites.service.campsitesindia.model.ListingModel;
import com.dd.campsites.service.criteria.ListingCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ListingDetailsServiceImpl}.
 */
@Service
@Transactional
public class ListingDetailsServiceImpl implements ListingDetailsService {

    private final Logger log = LoggerFactory.getLogger(ListingDetailsServiceImpl.class);

    private final ListingRepository listingRepository;
    private final PhotosRepository photosRepository;
    private final ReviewRepository reviewRepository;
    private final RatingRepository ratingRepository;
    private final FeatureBusinessService featureBusinessService;
    private final ListingQueryService listingQueryService;

    public ListingDetailsServiceImpl(
        ListingRepository listingRepository,
        PhotosRepository photosRepository,
        ReviewRepository reviewRepository,
        RatingRepository ratingRepository,
        FeatureBusinessService featureBusinessService,
        ListingQueryService listingQueryService
    ) {
        this.listingRepository = listingRepository;
        this.photosRepository = photosRepository;
        this.reviewRepository = reviewRepository;

        this.ratingRepository = ratingRepository;
        this.featureBusinessService = featureBusinessService;
        this.listingQueryService = listingQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListingModel> findOne(Long id) {
        log.debug("Request to get Listing : {}", id);
        List<Photos> photos = photosRepository.findAllByListingIdEquals(id);
        List<Review> reviews = reviewRepository.findAllByListingIdEquals(id);
        Rating ratings = ratingRepository.findAllByListingIdEquals(id);
        Optional<Listing> listing = listingRepository.findById(id);
        ListingModel listingModel = new ListingModel();
        List<Features> features = featureBusinessService.findByListingId(id);
        if (listing.isEmpty()) {
            listingModel.setListing(new Listing());
        } else {
            listingModel.setListing(listing.get());
        }
        listingModel.setPhotosList(photos);
        listingModel.setReviews(reviews);
        listingModel.setRatings(ratings);
        listingModel.setFeaturesList(features);

        return Optional.ofNullable(listingModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ListingModel> findAll(ListingCriteria criteria, Pageable pageable) {
        log.debug("Request to get all Listings");

        Page<Listing> listings = listingQueryService.findByCriteria(criteria, pageable);

        List<Listing> listingList = listings.getContent();
        List<ListingModel> listingModels = new ArrayList<>();

        for (Listing listing : listingList) {
            List<Photos> photos = photosRepository.findAllByListingIdEquals(listing.getId());
            List<Review> reviews = reviewRepository.findAllByListingIdEquals(listing.getId());
            Rating ratings = ratingRepository.findAllByListingIdEquals(listing.getId());
            ListingModel listingModel = new ListingModel();
            listingModel.setListing(listing);
            listingModel.setPhotosList(photos);
            listingModel.setRatings(ratings);
            listingModel.setReviews(reviews);
            listingModels.add(listingModel);
        }
        // Creation
        Page<ListingModel> pages = new PageImpl<ListingModel>(listingModels, pageable, listings.getTotalElements());
        return pages;
    }
}
