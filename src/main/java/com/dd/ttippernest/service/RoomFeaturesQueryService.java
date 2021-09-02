package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.*; // for static metamodels
import com.dd.ttippernest.domain.RoomFeatures;
import com.dd.ttippernest.repository.RoomFeaturesRepository;
import com.dd.ttippernest.service.criteria.RoomFeaturesCriteria;
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
 * Service for executing complex queries for {@link RoomFeatures} entities in the database.
 * The main input is a {@link RoomFeaturesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoomFeatures} or a {@link Page} of {@link RoomFeatures} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoomFeaturesQueryService extends QueryService<RoomFeatures> {

    private final Logger log = LoggerFactory.getLogger(RoomFeaturesQueryService.class);

    private final RoomFeaturesRepository roomFeaturesRepository;

    public RoomFeaturesQueryService(RoomFeaturesRepository roomFeaturesRepository) {
        this.roomFeaturesRepository = roomFeaturesRepository;
    }

    /**
     * Return a {@link List} of {@link RoomFeatures} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoomFeatures> findByCriteria(RoomFeaturesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RoomFeatures> specification = createSpecification(criteria);
        return roomFeaturesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RoomFeatures} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoomFeatures> findByCriteria(RoomFeaturesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RoomFeatures> specification = createSpecification(criteria);
        return roomFeaturesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoomFeaturesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RoomFeatures> specification = createSpecification(criteria);
        return roomFeaturesRepository.count(specification);
    }

    /**
     * Function to convert {@link RoomFeaturesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RoomFeatures> createSpecification(RoomFeaturesCriteria criteria) {
        Specification<RoomFeatures> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RoomFeatures_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), RoomFeatures_.title));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), RoomFeatures_.count));
            }
            if (criteria.getThumbnail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnail(), RoomFeatures_.thumbnail));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), RoomFeatures_.icon));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), RoomFeatures_.color));
            }
            if (criteria.getImgIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgIcon(), RoomFeatures_.imgIcon));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RoomFeatures_.description));
            }
            if (criteria.getParent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParent(), RoomFeatures_.parent));
            }
            if (criteria.getTaxonomy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxonomy(), RoomFeatures_.taxonomy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), RoomFeatures_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), RoomFeatures_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), RoomFeatures_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), RoomFeatures_.updateDate));
            }
            if (criteria.getRoomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoomId(), root -> root.join(RoomFeatures_.room, JoinType.LEFT).get(Room_.id))
                    );
            }
        }
        return specification;
    }
}
