package com.dd.campsites.repository;

import com.dd.campsites.domain.Features;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Features entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeaturesRepository extends JpaRepository<Features, Long>, JpaSpecificationExecutor<Features> {}
