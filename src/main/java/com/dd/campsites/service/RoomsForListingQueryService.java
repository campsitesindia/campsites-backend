package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.RoomsForListing;
import com.dd.campsites.repository.RoomsForListingRepository;
import com.dd.campsites.service.criteria.RoomsForListingCriteria;
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
 * Service for executing complex queries for {@link RoomsForListing} entities in the database.
 * The main input is a {@link RoomsForListingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoomsForListing} or a {@link Page} of {@link RoomsForListing} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoomsForListingQueryService extends QueryService<RoomsForListing> {

    private final Logger log = LoggerFactory.getLogger(RoomsForListingQueryService.class);

    private final RoomsForListingRepository roomsForListingRepository;

    public RoomsForListingQueryService(RoomsForListingRepository roomsForListingRepository) {
        this.roomsForListingRepository = roomsForListingRepository;
    }

    /**
     * Return a {@link List} of {@link RoomsForListing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoomsForListing> findByCriteria(RoomsForListingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RoomsForListing> specification = createSpecification(criteria);
        return roomsForListingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RoomsForListing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoomsForListing> findByCriteria(RoomsForListingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RoomsForListing> specification = createSpecification(criteria);
        return roomsForListingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoomsForListingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RoomsForListing> specification = createSpecification(criteria);
        return roomsForListingRepository.count(specification);
    }

    /**
     * Function to convert {@link RoomsForListingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RoomsForListing> createSpecification(RoomsForListingCriteria criteria) {
        Specification<RoomsForListing> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RoomsForListing_.id));
            }
            if (criteria.getListingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getListingId(),
                            root -> root.join(RoomsForListing_.listing, JoinType.LEFT).get(Listing_.id)
                        )
                    );
            }
            if (criteria.getRoomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoomId(), root -> root.join(RoomsForListing_.room, JoinType.LEFT).get(Room_.id))
                    );
            }
        }
        return specification;
    }
}
