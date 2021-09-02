package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.Followers;
import com.dd.ttippernest.repository.FollowersRepository;
import com.dd.ttippernest.service.FollowersService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Followers}.
 */
@Service
@Transactional
public class FollowersServiceImpl implements FollowersService {

    private final Logger log = LoggerFactory.getLogger(FollowersServiceImpl.class);

    private final FollowersRepository followersRepository;

    public FollowersServiceImpl(FollowersRepository followersRepository) {
        this.followersRepository = followersRepository;
    }

    @Override
    public Followers save(Followers followers) {
        log.debug("Request to save Followers : {}", followers);
        return followersRepository.save(followers);
    }

    @Override
    public Optional<Followers> partialUpdate(Followers followers) {
        log.debug("Request to partially update Followers : {}", followers);

        return followersRepository
            .findById(followers.getId())
            .map(
                existingFollowers -> {
                    if (followers.getCreatedBy() != null) {
                        existingFollowers.setCreatedBy(followers.getCreatedBy());
                    }
                    if (followers.getCreatedDate() != null) {
                        existingFollowers.setCreatedDate(followers.getCreatedDate());
                    }
                    if (followers.getUpdatedBy() != null) {
                        existingFollowers.setUpdatedBy(followers.getUpdatedBy());
                    }
                    if (followers.getUpdateDate() != null) {
                        existingFollowers.setUpdateDate(followers.getUpdateDate());
                    }

                    return existingFollowers;
                }
            )
            .map(followersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Followers> findAll(Pageable pageable) {
        log.debug("Request to get all Followers");
        return followersRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Followers> findOne(Long id) {
        log.debug("Request to get Followers : {}", id);
        return followersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Followers : {}", id);
        followersRepository.deleteById(id);
    }
}
