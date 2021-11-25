package com.dd.campsites.web.rest;

import com.dd.campsites.domain.Photos;
import com.dd.campsites.repository.PhotosRepository;
import com.dd.campsites.service.PhotosQueryService;
import com.dd.campsites.service.PhotosService;
import com.dd.campsites.service.criteria.PhotosCriteria;
import com.dd.campsites.web.rest.errors.BadRequestAlertException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
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
 * REST controller for managing {@link com.dd.campsites.domain.Photos}.
 */
@RestController
@RequestMapping("/api")
public class PhotosResource {

    private final Logger log = LoggerFactory.getLogger(PhotosResource.class);

    private static final String ENTITY_NAME = "photos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhotosService photosService;

    private final PhotosRepository photosRepository;

    private final PhotosQueryService photosQueryService;

    public PhotosResource(PhotosService photosService, PhotosRepository photosRepository, PhotosQueryService photosQueryService) {
        this.photosService = photosService;
        this.photosRepository = photosRepository;
        this.photosQueryService = photosQueryService;
    }

    /**
     * {@code POST  /photos} : Create a new photos.
     *
     * @param photos the photos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new photos, or with status {@code 400 (Bad Request)} if the photos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/photos")
    public ResponseEntity<Photos> createPhotos(@Valid @RequestBody Photos photos) throws URISyntaxException {
        log.debug("REST request to save Photos : {}", photos);
        if (photos.getId() != null) {
            throw new BadRequestAlertException("A new photos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            photos = setMetadata(photos);
        } catch (ImageProcessingException ipe) {
            log.error(ipe.getMessage());
        } catch (MetadataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Photos result = photosService.save(photos);
        return ResponseEntity
            .created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /photos/:id} : Updates an existing photos.
     *
     * @param id the id of the photos to save.
     * @param photos the photos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photos,
     * or with status {@code 400 (Bad Request)} if the photos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the photos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/photos/{id}")
    public ResponseEntity<Photos> updatePhotos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Photos photos
    ) throws URISyntaxException {
        log.debug("REST request to update Photos : {}, {}", id, photos);
        if (photos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, photos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Photos result = photosService.save(photos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photos.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /photos/:id} : Partial updates given fields of an existing photos, field will ignore if it is null
     *
     * @param id the id of the photos to save.
     * @param photos the photos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photos,
     * or with status {@code 400 (Bad Request)} if the photos is not valid,
     * or with status {@code 404 (Not Found)} if the photos is not found,
     * or with status {@code 500 (Internal Server Error)} if the photos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/photos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Photos> partialUpdatePhotos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Photos photos
    ) throws URISyntaxException {
        log.debug("REST request to partial update Photos partially : {}, {}", id, photos);
        if (photos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, photos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Photos> result = photosService.partialUpdate(photos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photos.getId().toString())
        );
    }

    /**
     * {@code GET  /photos} : get all the photos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of photos in body.
     */
    @GetMapping("/photos")
    public ResponseEntity<List<Photos>> getAllPhotos(PhotosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Photos by criteria: {}", criteria);
        Page<Photos> page = photosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /photos/count} : count all the photos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/photos/count")
    public ResponseEntity<Long> countPhotos(PhotosCriteria criteria) {
        log.debug("REST request to count Photos by criteria: {}", criteria);
        return ResponseEntity.ok().body(photosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /photos/:id} : get the "id" photos.
     *
     * @param id the id of the photos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the photos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/photos/{id}")
    public ResponseEntity<Photos> getPhotos(@PathVariable Long id) {
        log.debug("REST request to get Photos : {}", id);
        Optional<Photos> photos = photosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photos);
    }

    /**
     * {@code DELETE  /photos/:id} : delete the "id" photos.
     *
     * @param id the id of the photos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/photos/{id}")
    public ResponseEntity<Void> deletePhotos(@PathVariable Long id) {
        log.debug("REST request to delete Photos : {}", id);
        photosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private Photos setMetadata(Photos photo) throws ImageProcessingException, IOException, MetadataException {
        String str = DatatypeConverter.printBase64Binary(photo.getImage());
        byte[] data2 = DatatypeConverter.parseBase64Binary(str);
        InputStream inputStream = new ByteArrayInputStream(data2);
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        Metadata metadata = ImageMetadataReader.readMetadata(bis);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        if (directory != null) {
            Date date = directory.getDateDigitized();
            if (date != null) {
                photo.setTaken(date.toInstant());
            }
        }

        if (photo.getTaken() == null) {
            log.debug("Photo EXIF date digitized not available, setting taken on date to now...");
            photo.setTaken(Instant.now());
        }

        photo.setUploaded(Instant.now());

        JpegDirectory jpgDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        if (jpgDirectory != null) {
            photo.setHeight(jpgDirectory.getImageHeight());
            photo.setWidth(jpgDirectory.getImageWidth());
        }

        return photo;
    }
}
