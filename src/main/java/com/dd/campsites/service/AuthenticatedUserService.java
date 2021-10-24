package com.dd.campsites.service;

import com.dd.campsites.domain.AuthenticatedUser;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AuthenticatedUser}.
 */
public interface AuthenticatedUserService {
    /**
     * Save a authenticatedUser.
     *
     * @param authenticatedUser the entity to save.
     * @return the persisted entity.
     */
    AuthenticatedUser save(AuthenticatedUser authenticatedUser);

    /**
     * Partially updates a authenticatedUser.
     *
     * @param authenticatedUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AuthenticatedUser> partialUpdate(AuthenticatedUser authenticatedUser);

    /**
     * Get all the authenticatedUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AuthenticatedUser> findAll(Pageable pageable);

    /**
     * Get the "id" authenticatedUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuthenticatedUser> findOne(Long id);

    /**
     * Delete the "id" authenticatedUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
