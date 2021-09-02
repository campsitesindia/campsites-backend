package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.Location;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {}
