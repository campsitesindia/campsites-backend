package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.*; // for static metamodels
import com.dd.ttippernest.domain.Videos;
import com.dd.ttippernest.repository.VideosRepository;
import com.dd.ttippernest.service.criteria.VideosCriteria;
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
 * Service for executing complex queries for {@link Videos} entities in the database.
 * The main input is a {@link VideosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Videos} or a {@link Page} of {@link Videos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VideosQueryService extends QueryService<Videos> {

    private final Logger log = LoggerFactory.getLogger(VideosQueryService.class);

    private final VideosRepository videosRepository;

    public VideosQueryService(VideosRepository videosRepository) {
        this.videosRepository = videosRepository;
    }

    /**
     * Return a {@link List} of {@link Videos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Videos> findByCriteria(VideosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Videos> specification = createSpecification(criteria);
        return videosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Videos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Videos> findByCriteria(VideosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Videos> specification = createSpecification(criteria);
        return videosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VideosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Videos> specification = createSpecification(criteria);
        return videosRepository.count(specification);
    }

    /**
     * Function to convert {@link VideosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Videos> createSpecification(VideosCriteria criteria) {
        Specification<Videos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Videos_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Videos_.name));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Videos_.url));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Videos_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Videos_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Videos_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Videos_.updateDate));
            }
            if (criteria.getListingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getListingId(), root -> root.join(Videos_.listing, JoinType.LEFT).get(Listing_.id))
                    );
            }
        }
        return specification;
    }
}
