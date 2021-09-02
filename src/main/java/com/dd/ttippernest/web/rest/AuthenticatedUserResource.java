package com.dd.ttippernest.web.rest;

import com.dd.ttippernest.domain.AuthenticatedUser;
import com.dd.ttippernest.repository.AuthenticatedUserRepository;
import com.dd.ttippernest.service.AuthenticatedUserQueryService;
import com.dd.ttippernest.service.AuthenticatedUserService;
import com.dd.ttippernest.service.criteria.AuthenticatedUserCriteria;
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
 * REST controller for managing {@link com.dd.ttippernest.domain.AuthenticatedUser}.
 */
@RestController
@RequestMapping("/api")
public class AuthenticatedUserResource {

    private final Logger log = LoggerFactory.getLogger(AuthenticatedUserResource.class);

    private static final String ENTITY_NAME = "authenticatedUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthenticatedUserService authenticatedUserService;

    private final AuthenticatedUserRepository authenticatedUserRepository;

    private final AuthenticatedUserQueryService authenticatedUserQueryService;

    public AuthenticatedUserResource(
        AuthenticatedUserService authenticatedUserService,
        AuthenticatedUserRepository authenticatedUserRepository,
        AuthenticatedUserQueryService authenticatedUserQueryService
    ) {
        this.authenticatedUserService = authenticatedUserService;
        this.authenticatedUserRepository = authenticatedUserRepository;
        this.authenticatedUserQueryService = authenticatedUserQueryService;
    }

    /**
     * {@code POST  /authenticated-users} : Create a new authenticatedUser.
     *
     * @param authenticatedUser the authenticatedUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authenticatedUser, or with status {@code 400 (Bad Request)} if the authenticatedUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authenticated-users")
    public ResponseEntity<AuthenticatedUser> createAuthenticatedUser(@RequestBody AuthenticatedUser authenticatedUser)
        throws URISyntaxException {
        log.debug("REST request to save AuthenticatedUser : {}", authenticatedUser);
        if (authenticatedUser.getId() != null) {
            throw new BadRequestAlertException("A new authenticatedUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthenticatedUser result = authenticatedUserService.save(authenticatedUser);
        return ResponseEntity
            .created(new URI("/api/authenticated-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /authenticated-users/:id} : Updates an existing authenticatedUser.
     *
     * @param id the id of the authenticatedUser to save.
     * @param authenticatedUser the authenticatedUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authenticatedUser,
     * or with status {@code 400 (Bad Request)} if the authenticatedUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authenticatedUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/authenticated-users/{id}")
    public ResponseEntity<AuthenticatedUser> updateAuthenticatedUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthenticatedUser authenticatedUser
    ) throws URISyntaxException {
        log.debug("REST request to update AuthenticatedUser : {}, {}", id, authenticatedUser);
        if (authenticatedUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authenticatedUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authenticatedUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthenticatedUser result = authenticatedUserService.save(authenticatedUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authenticatedUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /authenticated-users/:id} : Partial updates given fields of an existing authenticatedUser, field will ignore if it is null
     *
     * @param id the id of the authenticatedUser to save.
     * @param authenticatedUser the authenticatedUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authenticatedUser,
     * or with status {@code 400 (Bad Request)} if the authenticatedUser is not valid,
     * or with status {@code 404 (Not Found)} if the authenticatedUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the authenticatedUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/authenticated-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AuthenticatedUser> partialUpdateAuthenticatedUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthenticatedUser authenticatedUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuthenticatedUser partially : {}, {}", id, authenticatedUser);
        if (authenticatedUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authenticatedUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authenticatedUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthenticatedUser> result = authenticatedUserService.partialUpdate(authenticatedUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authenticatedUser.getId().toString())
        );
    }

    /**
     * {@code GET  /authenticated-users} : get all the authenticatedUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authenticatedUsers in body.
     */
    @GetMapping("/authenticated-users")
    public ResponseEntity<List<AuthenticatedUser>> getAllAuthenticatedUsers(AuthenticatedUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AuthenticatedUsers by criteria: {}", criteria);
        Page<AuthenticatedUser> page = authenticatedUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /authenticated-users/count} : count all the authenticatedUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/authenticated-users/count")
    public ResponseEntity<Long> countAuthenticatedUsers(AuthenticatedUserCriteria criteria) {
        log.debug("REST request to count AuthenticatedUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(authenticatedUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /authenticated-users/:id} : get the "id" authenticatedUser.
     *
     * @param id the id of the authenticatedUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authenticatedUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/authenticated-users/{id}")
    public ResponseEntity<AuthenticatedUser> getAuthenticatedUser(@PathVariable Long id) {
        log.debug("REST request to get AuthenticatedUser : {}", id);
        Optional<AuthenticatedUser> authenticatedUser = authenticatedUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authenticatedUser);
    }

    /**
     * {@code DELETE  /authenticated-users/:id} : delete the "id" authenticatedUser.
     *
     * @param id the id of the authenticatedUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/authenticated-users/{id}")
    public ResponseEntity<Void> deleteAuthenticatedUser(@PathVariable Long id) {
        log.debug("REST request to delete AuthenticatedUser : {}", id);
        authenticatedUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
