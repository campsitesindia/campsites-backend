package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.FeaturesInRoom;
import com.dd.campsites.repository.FeaturesInRoomRepository;
import com.dd.campsites.service.criteria.FeaturesInRoomCriteria;
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
 * Service for executing complex queries for {@link FeaturesInRoom} entities in the database.
 * The main input is a {@link FeaturesInRoomCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeaturesInRoom} or a {@link Page} of {@link FeaturesInRoom} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeaturesInRoomQueryService extends QueryService<FeaturesInRoom> {

    private final Logger log = LoggerFactory.getLogger(FeaturesInRoomQueryService.class);

    private final FeaturesInRoomRepository featuresInRoomRepository;

    public FeaturesInRoomQueryService(FeaturesInRoomRepository featuresInRoomRepository) {
        this.featuresInRoomRepository = featuresInRoomRepository;
    }

    /**
     * Return a {@link List} of {@link FeaturesInRoom} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeaturesInRoom> findByCriteria(FeaturesInRoomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeaturesInRoom> specification = createSpecification(criteria);
        return featuresInRoomRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FeaturesInRoom} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeaturesInRoom> findByCriteria(FeaturesInRoomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeaturesInRoom> specification = createSpecification(criteria);
        return featuresInRoomRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeaturesInRoomCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeaturesInRoom> specification = createSpecification(criteria);
        return featuresInRoomRepository.count(specification);
    }

    /**
     * Function to convert {@link FeaturesInRoomCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeaturesInRoom> createSpecification(FeaturesInRoomCriteria criteria) {
        Specification<FeaturesInRoom> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeaturesInRoom_.id));
            }
            if (criteria.getRoomId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoomId(), root -> root.join(FeaturesInRoom_.room, JoinType.LEFT).get(Room_.id))
                    );
            }
            if (criteria.getFeatureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeatureId(),
                            root -> root.join(FeaturesInRoom_.feature, JoinType.LEFT).get(Features_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
