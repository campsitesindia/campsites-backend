package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.Like;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like, Long>, JpaSpecificationExecutor<Like> {}
