package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.*; // for static metamodels
import com.dd.ttippernest.domain.Images;
import com.dd.ttippernest.repository.ImagesRepository;
import com.dd.ttippernest.service.criteria.ImagesCriteria;
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
 * Service for executing complex queries for {@link Images} entities in the database.
 * The main input is a {@link ImagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Images} or a {@link Page} of {@link Images} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImagesQueryService extends QueryService<Images> {

    private final Logger log = LoggerFactory.getLogger(ImagesQueryService.class);

    private final ImagesRepository imagesRepository;

    public ImagesQueryService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    /**
     * Return a {@link List} of {@link Images} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Images> findByCriteria(ImagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Images> specification = createSpecification(criteria);
        return imagesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Images} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Images> findByCriteria(ImagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Images> specification = createSpecification(criteria);
        return imagesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Images> specification = createSpecification(criteria);
        return imagesRepository.count(specification);
    }

    /**
     * Function to convert {@link ImagesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Images> createSpecification(ImagesCriteria criteria) {
        Specification<Images> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Images_.id));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Images_.imageUrl));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Images_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Images_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Images_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Images_.updateDate));
            }
            if (criteria.getPostId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPostId(), root -> root.join(Images_.post, JoinType.LEFT).get(Post_.id))
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Images_.user, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getLikeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLikeId(), root -> root.join(Images_.like, JoinType.LEFT).get(Like_.id))
                    );
            }
        }
        return specification;
    }
}
