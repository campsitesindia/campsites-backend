package com.dd.campsites.repository;

import com.dd.campsites.domain.FeaturesListing;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FeaturesListing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeaturesListingRepository extends JpaRepository<FeaturesListing, Long>, JpaSpecificationExecutor<FeaturesListing> {
    List<FeaturesListing> findAllByListingIdEquals(long id);
}
