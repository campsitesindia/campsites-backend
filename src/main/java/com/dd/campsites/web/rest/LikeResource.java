package com.dd.campsites.web.rest;

import com.dd.campsites.domain.Like;
import com.dd.campsites.repository.LikeRepository;
import com.dd.campsites.service.LikeQueryService;
import com.dd.campsites.service.LikeService;
import com.dd.campsites.service.criteria.LikeCriteria;
import com.dd.campsites.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dd.campsites.domain.Like}.
 */
@RestController
@RequestMapping("/api")
public class LikeResource {

    private final Logger log = LoggerFactory.getLogger(LikeResource.class);

    private static final String ENTITY_NAME = "like";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeService likeService;

    private final LikeRepository likeRepository;

    private final LikeQueryService likeQueryService;

    public LikeResource(LikeService likeService, LikeRepository likeRepository, LikeQueryService likeQueryService) {
        this.likeService = likeService;
        this.likeRepository = likeRepository;
        this.likeQueryService = likeQueryService;
    }

    /**
     * {@code POST  /likes} : Create a new like.
     *
     * @param like the like to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new like, or with status {@code 400 (Bad Request)} if the like has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/likes")
    public ResponseEntity<Like> createLike(@RequestBody Like like) throws URISyntaxException {
        log.debug("REST request to save Like : {}", like);
        if (like.getId() != null) {
            throw new BadRequestAlertException("A new like cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Like result = likeService.save(like);
        return ResponseEntity
            .created(new URI("/api/likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /likes/:id} : Updates an existing like.
     *
     * @param id the id of the like to save.
     * @param like the like to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated like,
     * or with status {@code 400 (Bad Request)} if the like is not valid,
     * or with status {@code 500 (Internal Server Error)} if the like couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/likes/{id}")
    public ResponseEntity<Like> updateLike(@PathVariable(value = "id", required = false) final Long id, @RequestBody Like like)
        throws URISyntaxException {
        log.debug("REST request to update Like : {}, {}", id, like);
        if (like.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, like.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Like result = likeService.save(like);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, like.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /likes/:id} : Partial updates given fields of an existing like, field will ignore if it is null
     *
     * @param id the id of the like to save.
     * @param like the like to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated like,
     * or with status {@code 400 (Bad Request)} if the like is not valid,
     * or with status {@code 404 (Not Found)} if the like is not found,
     * or with status {@code 500 (Internal Server Error)} if the like couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/likes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Like> partialUpdateLike(@PathVariable(value = "id", required = false) final Long id, @RequestBody Like like)
        throws URISyntaxException {
        log.debug("REST request to partial update Like partially : {}, {}", id, like);
        if (like.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, like.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Like> result = likeService.partialUpdate(like);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, like.getId().toString())
        );
    }

    /**
     * {@code GET  /likes} : get all the likes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likes in body.
     */
    @GetMapping("/likes")
    public ResponseEntity<List<Like>> getAllLikes(LikeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Likes by criteria: {}", criteria);
        Page<Like> page = likeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /likes/count} : count all the likes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/likes/count")
    public ResponseEntity<Long> countLikes(LikeCriteria criteria) {
        log.debug("REST request to count Likes by criteria: {}", criteria);
        return ResponseEntity.ok().body(likeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /likes/:id} : get the "id" like.
     *
     * @param id the id of the like to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the like, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/likes/{id}")
    public ResponseEntity<Like> getLike(@PathVariable Long id) {
        log.debug("REST request to get Like : {}", id);
        Optional<Like> like = likeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(like);
    }

    /**
     * {@code DELETE  /likes/:id} : delete the "id" like.
     *
     * @param id the id of the like to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/likes/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        log.debug("REST request to delete Like : {}", id);
        likeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
