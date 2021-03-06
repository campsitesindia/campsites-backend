package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.Listing;
import com.dd.campsites.repository.ListingRepository;
import com.dd.campsites.service.criteria.ListingCriteria;
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
 * Service for executing complex queries for {@link Listing} entities in the database.
 * The main input is a {@link ListingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Listing} or a {@link Page} of {@link Listing} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ListingQueryService extends QueryService<Listing> {

    private final Logger log = LoggerFactory.getLogger(ListingQueryService.class);

    private final ListingRepository listingRepository;

    public ListingQueryService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    /**
     * Return a {@link List} of {@link Listing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Listing> findByCriteria(ListingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Listing> specification = createSpecification(criteria);
        return listingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Listing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Listing> findByCriteria(ListingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Listing> specification = createSpecification(criteria);
        return listingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ListingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Listing> specification = createSpecification(criteria);
        return listingRepository.count(specification);
    }

    /**
     * Function to convert {@link ListingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Listing> createSpecification(ListingCriteria criteria) {
        Specification<Listing> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Listing_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Listing_.address));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), Listing_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), Listing_.longitude));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Listing_.url));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Listing_.title));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), Listing_.content));
            }
            if (criteria.getThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnail(), Listing_.thumbnail));
            }
            if (criteria.getIsFeatured() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFeatured(), Listing_.isFeatured));
            }
            if (criteria.getPricePerPerson() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPricePerPerson(), Listing_.pricePerPerson));
            }
            if (criteria.getPricePerChild() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPricePerChild(), Listing_.pricePerChild));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), Listing_.discount));
            }
            if (criteria.getIsPublished() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPublished(), Listing_.isPublished));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Listing_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Listing_.email));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), Listing_.website));
            }
            if (criteria.getDisableBooking() != null) {
                specification = specification.and(buildSpecification(criteria.getDisableBooking(), Listing_.disableBooking));
            }
            if (criteria.getViewCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getViewCount(), Listing_.viewCount));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Listing_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Listing_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Listing_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Listing_.updateDate));
            }
            if (criteria.getLocationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLocationId(), root -> root.join(Listing_.location, JoinType.LEFT).get(Location_.id))
                    );
            }
            if (criteria.getListingTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getListingTypeId(),
                            root -> root.join(Listing_.listingType, JoinType.LEFT).get(ListingType_.id)
                        )
                    );
            }
            if (criteria.getOwnerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOwnerId(), root -> root.join(Listing_.owner, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
