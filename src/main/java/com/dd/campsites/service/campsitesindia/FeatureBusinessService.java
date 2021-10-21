package com.dd.campsites.service.campsitesindia;

import com.dd.campsites.domain.Features;
import java.util.List;
import java.util.Optional;

public interface FeatureBusinessService {
    /**
     * Get the "id" Features.
     *
     * @param id the id of the Listing.
     * @return the entity.
     */
    List<Features> findByListingId(Long id);
}
