package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.*; // for static metamodels
import com.dd.ttippernest.domain.Location;
import com.dd.ttippernest.repository.LocationRepository;
import com.dd.ttippernest.service.criteria.LocationCriteria;
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
 * Service for executing complex queries for {@link Location} entities in the database.
 * The main input is a {@link LocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Location} or a {@link Page} of {@link Location} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationQueryService extends QueryService<Location> {

    private final Logger log = LoggerFactory.getLogger(LocationQueryService.class);

    private final LocationRepository locationRepository;

    public LocationQueryService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Return a {@link List} of {@link Location} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Location> findByCriteria(LocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Location> specification = createSpecification(criteria);
        return locationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Location} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Location> findByCriteria(LocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Location> specification = createSpecification(criteria);
        return locationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Location> specification = createSpecification(criteria);
        return locationRepository.count(specification);
    }

    /**
     * Function to convert {@link LocationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Location> createSpecification(LocationCriteria criteria) {
        Specification<Location> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Location_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Location_.title));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), Location_.count));
            }
            if (criteria.getThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnail(), Location_.thumbnail));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), Location_.icon));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), Location_.color));
            }
            if (criteria.getImgIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIcon(), Location_.imgIcon));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Location_.description));
            }
            if (criteria.getParent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParent(), Location_.parent));
            }
            if (criteria.getTaxonomy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxonomy(), Location_.taxonomy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Location_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Location_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Location_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Location_.updateDate));
            }
            if (criteria.getParentLocationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentLocationId(),
                            root -> root.join(Location_.parentLocation, JoinType.LEFT).get(Location_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
