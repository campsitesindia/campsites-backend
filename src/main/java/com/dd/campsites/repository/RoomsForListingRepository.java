package com.dd.campsites.repository;

import com.dd.campsites.domain.RoomsForListing;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoomsForListing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomsForListingRepository extends JpaRepository<RoomsForListing, Long>, JpaSpecificationExecutor<RoomsForListing> {
    List<RoomsForListing> findAllByListingIdEquals(long id);
}
