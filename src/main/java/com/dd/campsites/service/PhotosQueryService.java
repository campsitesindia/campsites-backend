package com.dd.campsites.service;

import com.dd.campsites.domain.*; // for static metamodels
import com.dd.campsites.domain.Photos;
import com.dd.campsites.repository.PhotosRepository;
import com.dd.campsites.service.criteria.PhotosCriteria;
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
 * Service for executing complex queries for {@link Photos} entities in the database.
 * The main input is a {@link PhotosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Photos} or a {@link Page} of {@link Photos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhotosQueryService extends QueryService<Photos> {

    private final Logger log = LoggerFactory.getLogger(PhotosQueryService.class);

    private final PhotosRepository photosRepository;

    public PhotosQueryService(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    /**
     * Return a {@link List} of {@link Photos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Photos> findByCriteria(PhotosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Photos> specification = createSpecification(criteria);
        return photosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Photos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Photos> findByCriteria(PhotosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Photos> specification = createSpecification(criteria);
        return photosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhotosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Photos> specification = createSpecification(criteria);
        return photosRepository.count(specification);
    }

    /**
     * Function to convert {@link PhotosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Photos> createSpecification(PhotosCriteria criteria) {
        Specification<Photos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Photos_.id));
            }
            if (criteria.getAlt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlt(), Photos_.alt));
            }
            if (criteria.getCaption() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaption(), Photos_.caption));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Photos_.description));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), Photos_.href));
            }
            if (criteria.getSrc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSrc(), Photos_.src));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Photos_.title));
            }
            if (criteria.getIsCoverImage() != null) {
                specification = specification.and(buildSpecification(criteria.getIsCoverImage(), Photos_.isCoverImage));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Photos_.height));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Photos_.width));
            }
            if (criteria.getTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaken(), Photos_.taken));
            }
            if (criteria.getUploaded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploaded(), Photos_.uploaded));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Photos_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Photos_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedBy(), Photos_.updatedBy));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), Photos_.updateDate));
            }
            if (criteria.getAlbumId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAlbumId(), root -> root.join(Photos_.album, JoinType.LEFT).get(Album_.id))
                    );
            }
            if (criteria.getListingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getListingId(), root -> root.join(Photos_.listing, JoinType.LEFT).get(Listing_.id))
                    );
            }
            if (criteria.getTagId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getTagId(), root -> root.join(Photos_.tags, JoinType.LEFT).get(Tag_.id)));
            }
        }
        return specification;
    }
}
