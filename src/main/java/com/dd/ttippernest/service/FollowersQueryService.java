package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.*; // for static metamodels
import com.dd.ttippernest.domain.Followers;
import com.dd.ttippernest.repository.FollowersRepository;
import com.dd.ttippernest.service.criteria.FollowersCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Followers} entities in the database.
 * The main input is a {@link FollowersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Followers} or a {@link Page} of {@link Followers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FollowersQueryService extends QueryService<Followers> {

    private final Logger log = LoggerFactory.getLogger(FollowersQueryService.class);

    private final FollowersRepository followersRepository;

    public FollowersQueryService(FollowersRepository followersRepository) {
        this.followersRepository = followersRepository;
    }

    /**
     * Return a {@link List} of {@link Followers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Followers> findByCriteria(FollowersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Followers> specification = createSpecification(criteria);
        return followersRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Followers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Followers> findByCriteria(FollowersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Followers> specification = createSpecification(criteria);
        return followersRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FollowersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Followers> specification = createSpecification(criteria);
        return followersRepository.count(specification);
    }

    /**
     * Function to convert {@link FollowersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Followers> createSpecification(FollowersCriteria criteria) {
        Specification<Followers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Followers_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Followers_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Followers_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Followers_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Followers_.updateDate));
            }
            if (criteria.getFollowedById() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFollowedById(),
                            root -> root.join(Followers_.followedBy, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Followers_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
