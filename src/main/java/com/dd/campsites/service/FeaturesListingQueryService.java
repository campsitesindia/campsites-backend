package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.FeaturesListing;
import com.dd.campsites.repository.FeaturesListingRepository;
import com.dd.campsites.service.criteria.FeaturesListingCriteria;
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
 * Service for executing complex queries for {@link FeaturesListing} entities in the database.
 * The main input is a {@link FeaturesListingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeaturesListing} or a {@link Page} of {@link FeaturesListing} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeaturesListingQueryService extends QueryService<FeaturesListing> {

    private final Logger log = LoggerFactory.getLogger(FeaturesListingQueryService.class);

    private final FeaturesListingRepository featuresListingRepository;

    public FeaturesListingQueryService(FeaturesListingRepository featuresListingRepository) {
        this.featuresListingRepository = featuresListingRepository;
    }

    /**
     * Return a {@link List} of {@link FeaturesListing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeaturesListing> findByCriteria(FeaturesListingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeaturesListing> specification = createSpecification(criteria);
        return featuresListingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FeaturesListing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeaturesListing> findByCriteria(FeaturesListingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeaturesListing> specification = createSpecification(criteria);
        return featuresListingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeaturesListingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeaturesListing> specification = createSpecification(criteria);
        return featuresListingRepository.count(specification);
    }

    /**
     * Function to convert {@link FeaturesListingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeaturesListing> createSpecification(FeaturesListingCriteria criteria) {
        Specification<FeaturesListing> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeaturesListing_.id));
            }
            if (criteria.getListingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getListingId(),
                            root -> root.join(FeaturesListing_.listing, JoinType.LEFT).get(Listing_.id)
                        )
                    );
            }
            if (criteria.getFeatureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeatureId(),
                            root -> root.join(FeaturesListing_.feature, JoinType.LEFT).get(Features_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
