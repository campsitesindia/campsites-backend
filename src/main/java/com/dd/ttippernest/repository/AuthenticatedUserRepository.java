package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.AuthenticatedUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AuthenticatedUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Long>, JpaSpecificationExecutor<AuthenticatedUser> {}
