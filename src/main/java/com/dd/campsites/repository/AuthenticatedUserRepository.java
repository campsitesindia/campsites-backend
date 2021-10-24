package com.dd.campsites.repository;

import com.dd.campsites.domain.AuthenticatedUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AuthenticatedUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Long>, JpaSpecificationExecutor<AuthenticatedUser> {}
