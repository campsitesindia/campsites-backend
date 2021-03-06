package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.Bookings;
import com.dd.campsites.repository.BookingsRepository;
import com.dd.campsites.service.criteria.BookingsCriteria;
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
 * Service for executing complex queries for {@link Bookings} entities in the database.
 * The main input is a {@link BookingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Bookings} or a {@link Page} of {@link Bookings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookingsQueryService extends QueryService<Bookings> {

    private final Logger log = LoggerFactory.getLogger(BookingsQueryService.class);

    private final BookingsRepository bookingsRepository;

    public BookingsQueryService(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    /**
     * Return a {@link List} of {@link Bookings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Bookings> findByCriteria(BookingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bookings> specification = createSpecification(criteria);
        return bookingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Bookings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bookings> findByCriteria(BookingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bookings> specification = createSpecification(criteria);
        return bookingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BookingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bookings> specification = createSpecification(criteria);
        return bookingsRepository.count(specification);
    }

    /**
     * Function to convert {@link BookingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bookings> createSpecification(BookingsCriteria criteria) {
        Specification<Bookings> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bookings_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Bookings_.name));
            }
            if (criteria.getCheckInDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCheckInDate(), Bookings_.checkInDate));
            }
            if (criteria.getCheckOutDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCheckOutDate(), Bookings_.checkOutDate));
            }
            if (criteria.getPricePerNight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPricePerNight(), Bookings_.pricePerNight));
            }
            if (criteria.getChildPricePerNight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChildPricePerNight(), Bookings_.childPricePerNight));
            }
            if (criteria.getNumOfNights() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumOfNights(), Bookings_.numOfNights));
            }
            if (criteria.getRazorpayPaymentId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRazorpayPaymentId(), Bookings_.razorpayPaymentId));
            }
            if (criteria.getRazorpayOrderId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRazorpayOrderId(), Bookings_.razorpayOrderId));
            }
            if (criteria.getRazorpaySignature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRazorpaySignature(), Bookings_.razorpaySignature));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), Bookings_.discount));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), Bookings_.totalAmount));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Bookings_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Bookings_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Bookings_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Bookings_.updateDate));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Bookings_.user, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getListingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getListingId(), root -> root.join(Bookings_.listing, JoinType.LEFT).get(Listing_.id))
                    );
            }
            if (criteria.getInvoiceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getInvoiceId(), root -> root.join(Bookings_.invoices, JoinType.LEFT).get(Invoice_.id))
                    );
            }
        }
        return specification;
    }
}
