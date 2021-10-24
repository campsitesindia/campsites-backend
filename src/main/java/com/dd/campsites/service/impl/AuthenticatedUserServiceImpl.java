package com.dd.campsites.service.impl;

import com.dd.campsites.domain.AuthenticatedUser;
import com.dd.campsites.repository.AuthenticatedUserRepository;
import com.dd.campsites.service.AuthenticatedUserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AuthenticatedUser}.
 */
@Service
@Transactional
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    private final Logger log = LoggerFactory.getLogger(AuthenticatedUserServiceImpl.class);

    private final AuthenticatedUserRepository authenticatedUserRepository;

    public AuthenticatedUserServiceImpl(AuthenticatedUserRepository authenticatedUserRepository) {
        this.authenticatedUserRepository = authenticatedUserRepository;
    }

    @Override
    public AuthenticatedUser save(AuthenticatedUser authenticatedUser) {
        log.debug("Request to save AuthenticatedUser : {}", authenticatedUser);
        return authenticatedUserRepository.save(authenticatedUser);
    }

    @Override
    public Optional<AuthenticatedUser> partialUpdate(AuthenticatedUser authenticatedUser) {
        log.debug("Request to partially update AuthenticatedUser : {}", authenticatedUser);

        return authenticatedUserRepository
            .findById(authenticatedUser.getId())
            .map(
                existingAuthenticatedUser -> {
                    if (authenticatedUser.getFirstName() != null) {
                        existingAuthenticatedUser.setFirstName(authenticatedUser.getFirstName());
                    }
                    if (authenticatedUser.getLastName() != null) {
                        existingAuthenticatedUser.setLastName(authenticatedUser.getLastName());
                    }
                    if (authenticatedUser.getAuthTimestamp() != null) {
                        existingAuthenticatedUser.setAuthTimestamp(authenticatedUser.getAuthTimestamp());
                    }

                    return existingAuthenticatedUser;
                }
            )
            .map(authenticatedUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthenticatedUser> findAll(Pageable pageable) {
        log.debug("Request to get all AuthenticatedUsers");
        return authenticatedUserRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthenticatedUser> findOne(Long id) {
        log.debug("Request to get AuthenticatedUser : {}", id);
        return authenticatedUserRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuthenticatedUser : {}", id);
        authenticatedUserRepository.deleteById(id);
    }
}
