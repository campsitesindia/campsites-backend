package com.dd.campsites.repository;

import com.dd.campsites.domain.Listing;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Listing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    @Query("select listing from Listing listing where listing.owner.login = ?#{principal.username}")
    List<Listing> findByOwnerIsCurrentUser();
}
