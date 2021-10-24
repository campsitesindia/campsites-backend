package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.Features;
import com.dd.campsites.repository.FeaturesRepository;
import com.dd.campsites.service.criteria.FeaturesCriteria;
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
 * Service for executing complex queries for {@link Features} entities in the database.
 * The main input is a {@link FeaturesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Features} or a {@link Page} of {@link Features} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeaturesQueryService extends QueryService<Features> {

    private final Logger log = LoggerFactory.getLogger(FeaturesQueryService.class);

    private final FeaturesRepository featuresRepository;

    public FeaturesQueryService(FeaturesRepository featuresRepository) {
        this.featuresRepository = featuresRepository;
    }

    /**
     * Return a {@link List} of {@link Features} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Features> findByCriteria(FeaturesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Features> specification = createSpecification(criteria);
        return featuresRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Features} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Features> findByCriteria(FeaturesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Features> specification = createSpecification(criteria);
        return featuresRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeaturesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Features> specification = createSpecification(criteria);
        return featuresRepository.count(specification);
    }

    /**
     * Function to convert {@link FeaturesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Features> createSpecification(FeaturesCriteria criteria) {
        Specification<Features> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Features_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Features_.title));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), Features_.count));
            }
            if (criteria.getThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnail(), Features_.thumbnail));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), Features_.icon));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), Features_.color));
            }
            if (criteria.getImgIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIcon(), Features_.imgIcon));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Features_.description));
            }
            if (criteria.getParent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParent(), Features_.parent));
            }
            if (criteria.getTaxonomy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxonomy(), Features_.taxonomy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Features_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Features_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Features_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Features_.updateDate));
            }
        }
        return specification;
    }
}
