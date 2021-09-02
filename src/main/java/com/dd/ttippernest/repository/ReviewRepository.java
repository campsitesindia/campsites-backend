package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.Review;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Review entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {}
