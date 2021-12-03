package com.dd.campsites.campsitesindia.listing.impl;

import com.dd.campsites.campsitesindia.listing.ListingBusinessService;
import com.dd.campsites.campsitesindia.model.ListingPublish;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.service.ListingService;
import com.dd.campsites.service.campsitesindia.ListingDetailsService;
import com.dd.campsites.service.campsitesindia.model.ListingModel;
import com.dd.campsites.service.impl.ListingServiceImpl;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ListingBusinessServiceImpl implements ListingBusinessService {

    private final Logger log = LoggerFactory.getLogger(ListingServiceImpl.class);

    private final ListingDetailsService listingDetailsService;
    private final ListingService listingService;

    public ListingBusinessServiceImpl(ListingDetailsService listingDetailsService, ListingService listingService) {
        this.listingDetailsService = listingDetailsService;
        this.listingService = listingService;
    }

    @Override
    public ListingPublish publish(long listingId) {
        Optional<ListingModel> listingModel = listingDetailsService.findOne(listingId);
        long features = 0;
        long photos = 0;
        ListingPublish listingPublish = new ListingPublish();

        ListingModel listingModel1 = new ListingModel();
        if (!listingModel.isEmpty()) {
            listingModel1 = listingModel.get();
            features = listingModel1.getFeaturesList().size();
            photos = listingModel1.getPhotosList().size();
            if (features > 0) {
                listingPublish.setContainFeatures(true);
            }
            if (photos > 0) {
                listingPublish.setContainPhotos(true);
            }
        }

        if (features > 0 && photos > 0) {
            Listing listing = listingModel1.getListing();

            listing.setIsPublished(true);
            listing.setUpdateDate(Instant.now());
            listingService.partialUpdate(listing);
        }
        return listingPublish;
    }
}
