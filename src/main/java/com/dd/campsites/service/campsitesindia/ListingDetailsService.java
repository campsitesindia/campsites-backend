package com.dd.campsites.service.campsitesindia;

import com.dd.campsites.domain.Listing;
import com.dd.campsites.service.campsitesindia.model.ListingModel;
import com.dd.campsites.service.criteria.ListingCriteria;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ListingDetailsService}.
 */
public interface ListingDetailsService {
    /**
     * Get the "id" listing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Listing> findOne(Long id);

    /**
     * Get all the listings.
     * @param criteria  .
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ListingModel> findAll(ListingCriteria criteria, Pageable pageable);
}
