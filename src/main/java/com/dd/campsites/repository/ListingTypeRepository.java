package com.dd.campsites.repository;

import com.dd.campsites.domain.ListingType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListingTypeRepository extends JpaRepository<ListingType, Long>, JpaSpecificationExecutor<ListingType> {}
