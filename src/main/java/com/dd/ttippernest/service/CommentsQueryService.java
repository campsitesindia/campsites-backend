package com.dd.ttippernest.service;

import com.dd.ttippernest.domain.*; // for static metamodels
import com.dd.ttippernest.domain.Comments;
import com.dd.ttippernest.repository.CommentsRepository;
import com.dd.ttippernest.service.criteria.CommentsCriteria;
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
 * Service for executing complex queries for {@link Comments} entities in the database.
 * The main input is a {@link CommentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Comments} or a {@link Page} of {@link Comments} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommentsQueryService extends QueryService<Comments> {

    private final Logger log = LoggerFactory.getLogger(CommentsQueryService.class);

    private final CommentsRepository commentsRepository;

    public CommentsQueryService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    /**
     * Return a {@link List} of {@link Comments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Comments> findByCriteria(CommentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Comments> specification = createSpecification(criteria);
        return commentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Comments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Comments> findByCriteria(CommentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Comments> specification = createSpecification(criteria);
        return commentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Comments> specification = createSpecification(criteria);
        return commentsRepository.count(specification);
    }

    /**
     * Function to convert {@link CommentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Comments> createSpecification(CommentsCriteria criteria) {
        Specification<Comments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Comments_.id));
            }
            if (criteria.getCommentText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommentText(), Comments_.commentText));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Comments_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Comments_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Comments_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Comments_.updateDate));
            }
            if (criteria.getPostId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPostId(), root -> root.join(Comments_.post, JoinType.LEFT).get(Post_.id))
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Comments_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
