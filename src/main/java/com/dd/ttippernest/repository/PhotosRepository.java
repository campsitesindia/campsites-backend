package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.Photos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Photos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotosRepository extends JpaRepository<Photos, Long>, JpaSpecificationExecutor<Photos> {}
