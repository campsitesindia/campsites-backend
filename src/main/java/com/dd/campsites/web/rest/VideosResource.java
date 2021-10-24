package com.dd.campsites.web.rest;

import com.dd.campsites.domain.Videos;
import com.dd.campsites.repository.VideosRepository;
import com.dd.campsites.service.VideosQueryService;
import com.dd.campsites.service.VideosService;
import com.dd.campsites.service.criteria.VideosCriteria;
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
 * REST controller for managing {@link com.dd.campsites.domain.Videos}.
 */
@RestController
@RequestMapping("/api")
public class VideosResource {

    private final Logger log = LoggerFactory.getLogger(VideosResource.class);

    private static final String ENTITY_NAME = "videos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideosService videosService;

    private final VideosRepository videosRepository;

    private final VideosQueryService videosQueryService;

    public VideosResource(VideosService videosService, VideosRepository videosRepository, VideosQueryService videosQueryService) {
        this.videosService = videosService;
        this.videosRepository = videosRepository;
        this.videosQueryService = videosQueryService;
    }

    /**
     * {@code POST  /videos} : Create a new videos.
     *
     * @param videos the videos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videos, or with status {@code 400 (Bad Request)} if the videos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/videos")
    public ResponseEntity<Videos> createVideos(@RequestBody Videos videos) throws URISyntaxException {
        log.debug("REST request to save Videos : {}", videos);
        if (videos.getId() != null) {
            throw new BadRequestAlertException("A new videos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Videos result = videosService.save(videos);
        return ResponseEntity
            .created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /videos/:id} : Updates an existing videos.
     *
     * @param id the id of the videos to save.
     * @param videos the videos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videos,
     * or with status {@code 400 (Bad Request)} if the videos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/videos/{id}")
    public ResponseEntity<Videos> updateVideos(@PathVariable(value = "id", required = false) final Long id, @RequestBody Videos videos)
        throws URISyntaxException {
        log.debug("REST request to update Videos : {}, {}", id, videos);
        if (videos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Videos result = videosService.save(videos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videos.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /videos/:id} : Partial updates given fields of an existing videos, field will ignore if it is null
     *
     * @param id the id of the videos to save.
     * @param videos the videos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videos,
     * or with status {@code 400 (Bad Request)} if the videos is not valid,
     * or with status {@code 404 (Not Found)} if the videos is not found,
     * or with status {@code 500 (Internal Server Error)} if the videos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/videos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Videos> partialUpdateVideos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Videos videos
    ) throws URISyntaxException {
        log.debug("REST request to partial update Videos partially : {}, {}", id, videos);
        if (videos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Videos> result = videosService.partialUpdate(videos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videos.getId().toString())
        );
    }

    /**
     * {@code GET  /videos} : get all the videos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videos in body.
     */
    @GetMapping("/videos")
    public ResponseEntity<List<Videos>> getAllVideos(VideosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Videos by criteria: {}", criteria);
        Page<Videos> page = videosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /videos/count} : count all the videos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/videos/count")
    public ResponseEntity<Long> countVideos(VideosCriteria criteria) {
        log.debug("REST request to count Videos by criteria: {}", criteria);
        return ResponseEntity.ok().body(videosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /videos/:id} : get the "id" videos.
     *
     * @param id the id of the videos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/videos/{id}")
    public ResponseEntity<Videos> getVideos(@PathVariable Long id) {
        log.debug("REST request to get Videos : {}", id);
        Optional<Videos> videos = videosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videos);
    }

    /**
     * {@code DELETE  /videos/:id} : delete the "id" videos.
     *
     * @param id the id of the videos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> deleteVideos(@PathVariable Long id) {
        log.debug("REST request to delete Videos : {}", id);
        videosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
