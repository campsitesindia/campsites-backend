package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.AuthenticatedUser;
import com.dd.campsites.repository.AuthenticatedUserRepository;
import com.dd.campsites.service.criteria.AuthenticatedUserCriteria;
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
 * Service for executing complex queries for {@link AuthenticatedUser} entities in the database.
 * The main input is a {@link AuthenticatedUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuthenticatedUser} or a {@link Page} of {@link AuthenticatedUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuthenticatedUserQueryService extends QueryService<AuthenticatedUser> {

    private final Logger log = LoggerFactory.getLogger(AuthenticatedUserQueryService.class);

    private final AuthenticatedUserRepository authenticatedUserRepository;

    public AuthenticatedUserQueryService(AuthenticatedUserRepository authenticatedUserRepository) {
        this.authenticatedUserRepository = authenticatedUserRepository;
    }

    /**
     * Return a {@link List} of {@link AuthenticatedUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthenticatedUser> findByCriteria(AuthenticatedUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AuthenticatedUser> specification = createSpecification(criteria);
        return authenticatedUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AuthenticatedUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthenticatedUser> findByCriteria(AuthenticatedUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AuthenticatedUser> specification = createSpecification(criteria);
        return authenticatedUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuthenticatedUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AuthenticatedUser> specification = createSpecification(criteria);
        return authenticatedUserRepository.count(specification);
    }

    /**
     * Function to convert {@link AuthenticatedUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AuthenticatedUser> createSpecification(AuthenticatedUserCriteria criteria) {
        Specification<AuthenticatedUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AuthenticatedUser_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), AuthenticatedUser_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), AuthenticatedUser_.lastName));
            }
            if (criteria.getAuthTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuthTimestamp(), AuthenticatedUser_.authTimestamp));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(AuthenticatedUser_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
