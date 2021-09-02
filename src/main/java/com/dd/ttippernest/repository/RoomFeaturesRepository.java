package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.RoomFeatures;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoomFeatures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomFeaturesRepository extends JpaRepository<RoomFeatures, Long>, JpaSpecificationExecutor<RoomFeatures> {}
