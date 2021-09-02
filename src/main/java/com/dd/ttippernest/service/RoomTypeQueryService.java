package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.*; // for static metamodels
import com.dd.ttippernest.domain.RoomType;
import com.dd.ttippernest.repository.RoomTypeRepository;
import com.dd.ttippernest.service.criteria.RoomTypeCriteria;
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
 * Service for executing complex queries for {@link RoomType} entities in the database.
 * The main input is a {@link RoomTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoomType} or a {@link Page} of {@link RoomType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoomTypeQueryService extends QueryService<RoomType> {

    private final Logger log = LoggerFactory.getLogger(RoomTypeQueryService.class);

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeQueryService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    /**
     * Return a {@link List} of {@link RoomType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoomType> findByCriteria(RoomTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RoomType> specification = createSpecification(criteria);
        return roomTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RoomType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoomType> findByCriteria(RoomTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RoomType> specification = createSpecification(criteria);
        return roomTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoomTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RoomType> specification = createSpecification(criteria);
        return roomTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link RoomTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RoomType> createSpecification(RoomTypeCriteria criteria) {
        Specification<RoomType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RoomType_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RoomType_.description));
            }
            if (criteria.getMaxCapacity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaxCapacity(), RoomType_.maxCapacity));
            }
            if (criteria.getNumberOfBeds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfBeds(), RoomType_.numberOfBeds));
            }
            if (criteria.getNumberOfBathrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfBathrooms(), RoomType_.numberOfBathrooms));
            }
            if (criteria.getRoomRatePerNigt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoomRatePerNigt(), RoomType_.roomRatePerNigt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), RoomType_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), RoomType_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), RoomType_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), RoomType_.updateDate));
            }
        }
        return specification;
    }
}
