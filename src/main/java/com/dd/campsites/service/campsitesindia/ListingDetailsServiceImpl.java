package com.dd.campsites.service.campsitesindia;

import com.dd.campsites.domain.Listing;
import com.dd.campsites.domain.Photos;
import com.dd.campsites.domain.Rating;
import com.dd.campsites.domain.Review;
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
import org.hibernate.Criteria;
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
    private final ListingQueryService listingQueryService;

    public ListingDetailsServiceImpl(
        ListingRepository listingRepository,
        PhotosRepository photosRepository,
        ReviewRepository reviewRepository,
        RatingRepository ratingRepository,
        ListingQueryService listingQueryService
    ) {
        this.listingRepository = listingRepository;
        this.photosRepository = photosRepository;
        this.reviewRepository = reviewRepository;

        this.ratingRepository = ratingRepository;
        this.listingQueryService = listingQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Listing> findOne(Long id) {
        log.debug("Request to get Listing : {}", id);
        return listingRepository.findById(id);
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
            //            List<Review> reviews=reviewRepository.findAllByBooking_Listing(listing.getId());
            List<Rating> ratings = ratingRepository.findAllByListingIdEquals(listing.getId());
            ListingModel listingModel = new ListingModel();
            listingModel.setListing(listing);
            listingModel.setPhotosList(photos);
            listingModel.setRatings(ratings);
            //          listingModel.setReviews(reviews);
            listingModels.add(listingModel);
        }
        // Creation
        Page<ListingModel> pages = new PageImpl<ListingModel>(listingModels, pageable, listings.getTotalElements());
        return pages;
    }
}
