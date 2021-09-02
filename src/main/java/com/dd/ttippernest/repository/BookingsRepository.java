package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.Bookings;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bookings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long>, JpaSpecificationExecutor<Bookings> {
    @Query("select bookings from Bookings bookings where bookings.user.login = ?#{principal.username}")
    List<Bookings> findByUserIsCurrentUser();
}
