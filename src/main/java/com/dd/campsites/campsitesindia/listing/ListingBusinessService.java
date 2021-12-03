package com.dd.campsites.campsitesindia.listing;

import com.dd.campsites.campsitesindia.model.ListingPublish;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.service.campsitesindia.model.ListingModel;

public interface ListingBusinessService {
    /**
     * Publis a listing.
     *
     * @param listingId the entity to save.
     * @return the persisted entity.
     */
    ListingPublish publish(long listingId);
}
