package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.Followers;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Followers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long>, JpaSpecificationExecutor<Followers> {
    @Query("select followers from Followers followers where followers.followedBy.login = ?#{principal.username}")
    List<Followers> findByFollowedByIsCurrentUser();

    @Query("select followers from Followers followers where followers.user.login = ?#{principal.username}")
    List<Followers> findByUserIsCurrentUser();
}
