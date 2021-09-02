package com.dd.ttippernest.web.rest;

import com.dd.ttippernest.domain.Comments;
import com.dd.ttippernest.repository.CommentsRepository;
import com.dd.ttippernest.service.CommentsQueryService;
import com.dd.ttippernest.service.CommentsService;
import com.dd.ttippernest.service.criteria.CommentsCriteria;
import com.dd.ttippernest.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dd.ttippernest.domain.Comments}.
 */
@RestController
@RequestMapping("/api")
public class CommentsResource {

    private final Logger log = LoggerFactory.getLogger(CommentsResource.class);

    private static final String ENTITY_NAME = "comments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentsService commentsService;

    private final CommentsRepository commentsRepository;

    private final CommentsQueryService commentsQueryService;

    public CommentsResource(
        CommentsService commentsService,
        CommentsRepository commentsRepository,
        CommentsQueryService commentsQueryService
    ) {
        this.commentsService = commentsService;
        this.commentsRepository = commentsRepository;
        this.commentsQueryService = commentsQueryService;
    }

    /**
     * {@code POST  /comments} : Create a new comments.
     *
     * @param comments the comments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comments, or with status {@code 400 (Bad Request)} if the comments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments")
    public ResponseEntity<Comments> createComments(@RequestBody Comments comments) throws URISyntaxException {
        log.debug("REST request to save Comments : {}", comments);
        if (comments.getId() != null) {
            throw new BadRequestAlertException("A new comments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comments result = commentsService.save(comments);
        return ResponseEntity
            .created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comments/:id} : Updates an existing comments.
     *
     * @param id the id of the comments to save.
     * @param comments the comments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comments,
     * or with status {@code 400 (Bad Request)} if the comments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comments> updateComments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Comments comments
    ) throws URISyntaxException {
        log.debug("REST request to update Comments : {}, {}", id, comments);
        if (comments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Comments result = commentsService.save(comments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comments.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comments/:id} : Partial updates given fields of an existing comments, field will ignore if it is null
     *
     * @param id the id of the comments to save.
     * @param comments the comments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comments,
     * or with status {@code 400 (Bad Request)} if the comments is not valid,
     * or with status {@code 404 (Not Found)} if the comments is not found,
     * or with status {@code 500 (Internal Server Error)} if the comments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Comments> partialUpdateComments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Comments comments
    ) throws URISyntaxException {
        log.debug("REST request to partial update Comments partially : {}, {}", id, comments);
        if (comments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Comments> result = commentsService.partialUpdate(comments);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comments.getId().toString())
        );
    }

    /**
     * {@code GET  /comments} : get all the comments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comments in body.
     */
    @GetMapping("/comments")
    public ResponseEntity<List<Comments>> getAllComments(CommentsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Comments by criteria: {}", criteria);
        Page<Comments> page = commentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comments/count} : count all the comments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comments/count")
    public ResponseEntity<Long> countComments(CommentsCriteria criteria) {
        log.debug("REST request to count Comments by criteria: {}", criteria);
        return ResponseEntity.ok().body(commentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comments/:id} : get the "id" comments.
     *
     * @param id the id of the comments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments/{id}")
    public ResponseEntity<Comments> getComments(@PathVariable Long id) {
        log.debug("REST request to get Comments : {}", id);
        Optional<Comments> comments = commentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comments);
    }

    /**
     * {@code DELETE  /comments/:id} : delete the "id" comments.
     *
     * @param id the id of the comments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long id) {
        log.debug("REST request to delete Comments : {}", id);
        commentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
