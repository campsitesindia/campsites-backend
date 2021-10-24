package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.RoomsInBooking;
import com.dd.campsites.repository.RoomsInBookingRepository;
import com.dd.campsites.service.criteria.RoomsInBookingCriteria;
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
 * Service for executing complex queries for {@link RoomsInBooking} entities in the database.
 * The main input is a {@link RoomsInBookingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoomsInBooking} or a {@link Page} of {@link RoomsInBooking} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoomsInBookingQueryService extends QueryService<RoomsInBooking> {

    private final Logger log = LoggerFactory.getLogger(RoomsInBookingQueryService.class);

    private final RoomsInBookingRepository roomsInBookingRepository;

    public RoomsInBookingQueryService(RoomsInBookingRepository roomsInBookingRepository) {
        this.roomsInBookingRepository = roomsInBookingRepository;
    }

    /**
     * Return a {@link List} of {@link RoomsInBooking} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoomsInBooking> findByCriteria(RoomsInBookingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RoomsInBooking> specification = createSpecification(criteria);
        return roomsInBookingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RoomsInBooking} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoomsInBooking> findByCriteria(RoomsInBookingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RoomsInBooking> specification = createSpecification(criteria);
        return roomsInBookingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoomsInBookingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RoomsInBooking> specification = createSpecification(criteria);
        return roomsInBookingRepository.count(specification);
    }

    /**
     * Function to convert {@link RoomsInBookingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RoomsInBooking> createSpecification(RoomsInBookingCriteria criteria) {
        Specification<RoomsInBooking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RoomsInBooking_.id));
            }
            if (criteria.getBookingsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBookingsId(),
                            root -> root.join(RoomsInBooking_.bookings, JoinType.LEFT).get(Bookings_.id)
                        )
                    );
            }
            if (criteria.getRoomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoomId(), root -> root.join(RoomsInBooking_.room, JoinType.LEFT).get(Room_.id))
                    );
            }
        }
        return specification;
    }
}
