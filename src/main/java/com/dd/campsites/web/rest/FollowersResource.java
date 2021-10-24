package com.dd.campsites.web.rest;

import com.dd.campsites.domain.Followers;
import com.dd.campsites.repository.FollowersRepository;
import com.dd.campsites.service.FollowersQueryService;
import com.dd.campsites.service.FollowersService;
import com.dd.campsites.service.criteria.FollowersCriteria;
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
 * REST controller for managing {@link com.dd.campsites.domain.Followers}.
 */
@RestController
@RequestMapping("/api")
public class FollowersResource {

    private final Logger log = LoggerFactory.getLogger(FollowersResource.class);

    private static final String ENTITY_NAME = "followers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FollowersService followersService;

    private final FollowersRepository followersRepository;

    private final FollowersQueryService followersQueryService;

    public FollowersResource(
        FollowersService followersService,
        FollowersRepository followersRepository,
        FollowersQueryService followersQueryService
    ) {
        this.followersService = followersService;
        this.followersRepository = followersRepository;
        this.followersQueryService = followersQueryService;
    }

    /**
     * {@code POST  /followers} : Create a new followers.
     *
     * @param followers the followers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new followers, or with status {@code 400 (Bad Request)} if the followers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/followers")
    public ResponseEntity<Followers> createFollowers(@RequestBody Followers followers) throws URISyntaxException {
        log.debug("REST request to save Followers : {}", followers);
        if (followers.getId() != null) {
            throw new BadRequestAlertException("A new followers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Followers result = followersService.save(followers);
        return ResponseEntity
            .created(new URI("/api/followers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /followers/:id} : Updates an existing followers.
     *
     * @param id the id of the followers to save.
     * @param followers the followers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated followers,
     * or with status {@code 400 (Bad Request)} if the followers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the followers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/followers/{id}")
    public ResponseEntity<Followers> updateFollowers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Followers followers
    ) throws URISyntaxException {
        log.debug("REST request to update Followers : {}, {}", id, followers);
        if (followers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, followers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!followersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Followers result = followersService.save(followers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, followers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /followers/:id} : Partial updates given fields of an existing followers, field will ignore if it is null
     *
     * @param id the id of the followers to save.
     * @param followers the followers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated followers,
     * or with status {@code 400 (Bad Request)} if the followers is not valid,
     * or with status {@code 404 (Not Found)} if the followers is not found,
     * or with status {@code 500 (Internal Server Error)} if the followers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/followers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Followers> partialUpdateFollowers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Followers followers
    ) throws URISyntaxException {
        log.debug("REST request to partial update Followers partially : {}, {}", id, followers);
        if (followers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, followers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!followersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Followers> result = followersService.partialUpdate(followers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, followers.getId().toString())
        );
    }

    /**
     * {@code GET  /followers} : get all the followers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of followers in body.
     */
    @GetMapping("/followers")
    public ResponseEntity<List<Followers>> getAllFollowers(FollowersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Followers by criteria: {}", criteria);
        Page<Followers> page = followersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /followers/count} : count all the followers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/followers/count")
    public ResponseEntity<Long> countFollowers(FollowersCriteria criteria) {
        log.debug("REST request to count Followers by criteria: {}", criteria);
        return ResponseEntity.ok().body(followersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /followers/:id} : get the "id" followers.
     *
     * @param id the id of the followers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the followers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/followers/{id}")
    public ResponseEntity<Followers> getFollowers(@PathVariable Long id) {
        log.debug("REST request to get Followers : {}", id);
        Optional<Followers> followers = followersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(followers);
    }

    /**
     * {@code DELETE  /followers/:id} : delete the "id" followers.
     *
     * @param id the id of the followers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/followers/{id}")
    public ResponseEntity<Void> deleteFollowers(@PathVariable Long id) {
        log.debug("REST request to delete Followers : {}", id);
        followersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
