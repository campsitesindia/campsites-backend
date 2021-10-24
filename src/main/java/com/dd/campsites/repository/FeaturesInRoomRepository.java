package com.dd.campsites.repository;

import com.dd.campsites.domain.FeaturesInRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FeaturesInRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeaturesInRoomRepository extends JpaRepository<FeaturesInRoom, Long>, JpaSpecificationExecutor<FeaturesInRoom> {}
