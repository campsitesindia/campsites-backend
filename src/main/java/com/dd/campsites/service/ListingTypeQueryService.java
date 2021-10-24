package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.ListingType;
import com.dd.campsites.repository.ListingTypeRepository;
import com.dd.campsites.service.criteria.ListingTypeCriteria;
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
 * Service for executing complex queries for {@link ListingType} entities in the database.
 * The main input is a {@link ListingTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ListingType} or a {@link Page} of {@link ListingType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ListingTypeQueryService extends QueryService<ListingType> {

    private final Logger log = LoggerFactory.getLogger(ListingTypeQueryService.class);

    private final ListingTypeRepository listingTypeRepository;

    public ListingTypeQueryService(ListingTypeRepository listingTypeRepository) {
        this.listingTypeRepository = listingTypeRepository;
    }

    /**
     * Return a {@link List} of {@link ListingType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ListingType> findByCriteria(ListingTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ListingType> specification = createSpecification(criteria);
        return listingTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ListingType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ListingType> findByCriteria(ListingTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ListingType> specification = createSpecification(criteria);
        return listingTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ListingTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ListingType> specification = createSpecification(criteria);
        return listingTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ListingTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ListingType> createSpecification(ListingTypeCriteria criteria) {
        Specification<ListingType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ListingType_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), ListingType_.title));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), ListingType_.count));
            }
            if (criteria.getThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnail(), ListingType_.thumbnail));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), ListingType_.icon));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), ListingType_.color));
            }
            if (criteria.getImgIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIcon(), ListingType_.imgIcon));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ListingType_.description));
            }
            if (criteria.getTaxonomy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxonomy(), ListingType_.taxonomy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ListingType_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ListingType_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), ListingType_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), ListingType_.updateDate));
            }
            if (criteria.getParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentId(),
                            root -> root.join(ListingType_.parent, JoinType.LEFT).get(ListingType_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
