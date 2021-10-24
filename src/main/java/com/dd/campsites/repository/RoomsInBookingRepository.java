package com.dd.campsites.repository;

import com.dd.campsites.domain.RoomsInBooking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoomsInBooking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomsInBookingRepository extends JpaRepository<RoomsInBooking, Long>, JpaSpecificationExecutor<RoomsInBooking> {}
