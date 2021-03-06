package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Album;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.domain.Photos;
import com.dd.campsites.domain.Tag;
import com.dd.campsites.repository.PhotosRepository;
import com.dd.campsites.service.PhotosService;
import com.dd.campsites.service.criteria.PhotosCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PhotosResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PhotosResourceIT {

    private static final String DEFAULT_ALT = "AAAAAAAAAA";
    private static final String UPDATED_ALT = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_SRC = "AAAAAAAAAA";
    private static final String UPDATED_SRC = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_COVER_IMAGE = false;
    private static final Boolean UPDATED_IS_COVER_IMAGE = true;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;
    private static final Integer SMALLER_HEIGHT = 1 - 1;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;
    private static final Integer SMALLER_WIDTH = 1 - 1;

    private static final Instant DEFAULT_TAKEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TAKEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPLOADED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOADED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/photos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhotosRepository photosRepository;

    @Mock
    private PhotosRepository photosRepositoryMock;

    @Mock
    private PhotosService photosServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhotosMockMvc;

    private Photos photos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photos createEntity(EntityManager em) {
        Photos photos = new Photos()
            .alt(DEFAULT_ALT)
            .caption(DEFAULT_CAPTION)
            .description(DEFAULT_DESCRIPTION)
            .href(DEFAULT_HREF)
            .src(DEFAULT_SRC)
            .title(DEFAULT_TITLE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .isCoverImage(DEFAULT_IS_COVER_IMAGE)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH)
            .taken(DEFAULT_TAKEN)
            .uploaded(DEFAULT_UPLOADED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return photos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photos createUpdatedEntity(EntityManager em) {
        Photos photos = new Photos()
            .alt(UPDATED_ALT)
            .caption(UPDATED_CAPTION)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .src(UPDATED_SRC)
            .title(UPDATED_TITLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isCoverImage(UPDATED_IS_COVER_IMAGE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return photos;
    }

    @BeforeEach
    public void initTest() {
        photos = createEntity(em);
    }

    @Test
    @Transactional
    void createPhotos() throws Exception {
        int databaseSizeBeforeCreate = photosRepository.findAll().size();
        // Create the Photos
        restPhotosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photos)))
            .andExpect(status().isCreated());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate + 1);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getAlt()).isEqualTo(DEFAULT_ALT);
        assertThat(testPhotos.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testPhotos.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhotos.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testPhotos.getSrc()).isEqualTo(DEFAULT_SRC);
        assertThat(testPhotos.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPhotos.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPhotos.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testPhotos.getIsCoverImage()).isEqualTo(DEFAULT_IS_COVER_IMAGE);
        assertThat(testPhotos.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testPhotos.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testPhotos.getTaken()).isEqualTo(DEFAULT_TAKEN);
        assertThat(testPhotos.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
        assertThat(testPhotos.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPhotos.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPhotos.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPhotos.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createPhotosWithExistingId() throws Exception {
        // Create the Photos with an existing ID
        photos.setId(1L);

        int databaseSizeBeforeCreate = photosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photos)))
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList
        restPhotosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].src").value(hasItem(DEFAULT_SRC)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isCoverImage").value(hasItem(DEFAULT_IS_COVER_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPhotosWithEagerRelationshipsIsEnabled() throws Exception {
        when(photosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPhotosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(photosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPhotosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(photosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPhotosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(photosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get the photos
        restPhotosMockMvc
            .perform(get(ENTITY_API_URL_ID, photos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(photos.getId().intValue()))
            .andExpect(jsonPath("$.alt").value(DEFAULT_ALT))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.src").value(DEFAULT_SRC))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.isCoverImage").value(DEFAULT_IS_COVER_IMAGE.booleanValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.taken").value(DEFAULT_TAKEN.toString()))
            .andExpect(jsonPath("$.uploaded").value(DEFAULT_UPLOADED.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getPhotosByIdFiltering() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        Long id = photos.getId();

        defaultPhotosShouldBeFound("id.equals=" + id);
        defaultPhotosShouldNotBeFound("id.notEquals=" + id);

        defaultPhotosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhotosShouldNotBeFound("id.greaterThan=" + id);

        defaultPhotosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhotosShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPhotosByAltIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where alt equals to DEFAULT_ALT
        defaultPhotosShouldBeFound("alt.equals=" + DEFAULT_ALT);

        // Get all the photosList where alt equals to UPDATED_ALT
        defaultPhotosShouldNotBeFound("alt.equals=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    void getAllPhotosByAltIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where alt not equals to DEFAULT_ALT
        defaultPhotosShouldNotBeFound("alt.notEquals=" + DEFAULT_ALT);

        // Get all the photosList where alt not equals to UPDATED_ALT
        defaultPhotosShouldBeFound("alt.notEquals=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    void getAllPhotosByAltIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where alt in DEFAULT_ALT or UPDATED_ALT
        defaultPhotosShouldBeFound("alt.in=" + DEFAULT_ALT + "," + UPDATED_ALT);

        // Get all the photosList where alt equals to UPDATED_ALT
        defaultPhotosShouldNotBeFound("alt.in=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    void getAllPhotosByAltIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where alt is not null
        defaultPhotosShouldBeFound("alt.specified=true");

        // Get all the photosList where alt is null
        defaultPhotosShouldNotBeFound("alt.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByAltContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where alt contains DEFAULT_ALT
        defaultPhotosShouldBeFound("alt.contains=" + DEFAULT_ALT);

        // Get all the photosList where alt contains UPDATED_ALT
        defaultPhotosShouldNotBeFound("alt.contains=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    void getAllPhotosByAltNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where alt does not contain DEFAULT_ALT
        defaultPhotosShouldNotBeFound("alt.doesNotContain=" + DEFAULT_ALT);

        // Get all the photosList where alt does not contain UPDATED_ALT
        defaultPhotosShouldBeFound("alt.doesNotContain=" + UPDATED_ALT);
    }

    @Test
    @Transactional
    void getAllPhotosByCaptionIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where caption equals to DEFAULT_CAPTION
        defaultPhotosShouldBeFound("caption.equals=" + DEFAULT_CAPTION);

        // Get all the photosList where caption equals to UPDATED_CAPTION
        defaultPhotosShouldNotBeFound("caption.equals=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByCaptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where caption not equals to DEFAULT_CAPTION
        defaultPhotosShouldNotBeFound("caption.notEquals=" + DEFAULT_CAPTION);

        // Get all the photosList where caption not equals to UPDATED_CAPTION
        defaultPhotosShouldBeFound("caption.notEquals=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByCaptionIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where caption in DEFAULT_CAPTION or UPDATED_CAPTION
        defaultPhotosShouldBeFound("caption.in=" + DEFAULT_CAPTION + "," + UPDATED_CAPTION);

        // Get all the photosList where caption equals to UPDATED_CAPTION
        defaultPhotosShouldNotBeFound("caption.in=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByCaptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where caption is not null
        defaultPhotosShouldBeFound("caption.specified=true");

        // Get all the photosList where caption is null
        defaultPhotosShouldNotBeFound("caption.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByCaptionContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where caption contains DEFAULT_CAPTION
        defaultPhotosShouldBeFound("caption.contains=" + DEFAULT_CAPTION);

        // Get all the photosList where caption contains UPDATED_CAPTION
        defaultPhotosShouldNotBeFound("caption.contains=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByCaptionNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where caption does not contain DEFAULT_CAPTION
        defaultPhotosShouldNotBeFound("caption.doesNotContain=" + DEFAULT_CAPTION);

        // Get all the photosList where caption does not contain UPDATED_CAPTION
        defaultPhotosShouldBeFound("caption.doesNotContain=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where description equals to DEFAULT_DESCRIPTION
        defaultPhotosShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the photosList where description equals to UPDATED_DESCRIPTION
        defaultPhotosShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where description not equals to DEFAULT_DESCRIPTION
        defaultPhotosShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the photosList where description not equals to UPDATED_DESCRIPTION
        defaultPhotosShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPhotosShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the photosList where description equals to UPDATED_DESCRIPTION
        defaultPhotosShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where description is not null
        defaultPhotosShouldBeFound("description.specified=true");

        // Get all the photosList where description is null
        defaultPhotosShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where description contains DEFAULT_DESCRIPTION
        defaultPhotosShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the photosList where description contains UPDATED_DESCRIPTION
        defaultPhotosShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where description does not contain DEFAULT_DESCRIPTION
        defaultPhotosShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the photosList where description does not contain UPDATED_DESCRIPTION
        defaultPhotosShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhotosByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where href equals to DEFAULT_HREF
        defaultPhotosShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the photosList where href equals to UPDATED_HREF
        defaultPhotosShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllPhotosByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where href not equals to DEFAULT_HREF
        defaultPhotosShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the photosList where href not equals to UPDATED_HREF
        defaultPhotosShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllPhotosByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where href in DEFAULT_HREF or UPDATED_HREF
        defaultPhotosShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the photosList where href equals to UPDATED_HREF
        defaultPhotosShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllPhotosByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where href is not null
        defaultPhotosShouldBeFound("href.specified=true");

        // Get all the photosList where href is null
        defaultPhotosShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByHrefContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where href contains DEFAULT_HREF
        defaultPhotosShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the photosList where href contains UPDATED_HREF
        defaultPhotosShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllPhotosByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where href does not contain DEFAULT_HREF
        defaultPhotosShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the photosList where href does not contain UPDATED_HREF
        defaultPhotosShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllPhotosBySrcIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where src equals to DEFAULT_SRC
        defaultPhotosShouldBeFound("src.equals=" + DEFAULT_SRC);

        // Get all the photosList where src equals to UPDATED_SRC
        defaultPhotosShouldNotBeFound("src.equals=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    void getAllPhotosBySrcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where src not equals to DEFAULT_SRC
        defaultPhotosShouldNotBeFound("src.notEquals=" + DEFAULT_SRC);

        // Get all the photosList where src not equals to UPDATED_SRC
        defaultPhotosShouldBeFound("src.notEquals=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    void getAllPhotosBySrcIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where src in DEFAULT_SRC or UPDATED_SRC
        defaultPhotosShouldBeFound("src.in=" + DEFAULT_SRC + "," + UPDATED_SRC);

        // Get all the photosList where src equals to UPDATED_SRC
        defaultPhotosShouldNotBeFound("src.in=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    void getAllPhotosBySrcIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where src is not null
        defaultPhotosShouldBeFound("src.specified=true");

        // Get all the photosList where src is null
        defaultPhotosShouldNotBeFound("src.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosBySrcContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where src contains DEFAULT_SRC
        defaultPhotosShouldBeFound("src.contains=" + DEFAULT_SRC);

        // Get all the photosList where src contains UPDATED_SRC
        defaultPhotosShouldNotBeFound("src.contains=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    void getAllPhotosBySrcNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where src does not contain DEFAULT_SRC
        defaultPhotosShouldNotBeFound("src.doesNotContain=" + DEFAULT_SRC);

        // Get all the photosList where src does not contain UPDATED_SRC
        defaultPhotosShouldBeFound("src.doesNotContain=" + UPDATED_SRC);
    }

    @Test
    @Transactional
    void getAllPhotosByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where title equals to DEFAULT_TITLE
        defaultPhotosShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the photosList where title equals to UPDATED_TITLE
        defaultPhotosShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPhotosByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where title not equals to DEFAULT_TITLE
        defaultPhotosShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the photosList where title not equals to UPDATED_TITLE
        defaultPhotosShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPhotosByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultPhotosShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the photosList where title equals to UPDATED_TITLE
        defaultPhotosShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPhotosByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where title is not null
        defaultPhotosShouldBeFound("title.specified=true");

        // Get all the photosList where title is null
        defaultPhotosShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByTitleContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where title contains DEFAULT_TITLE
        defaultPhotosShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the photosList where title contains UPDATED_TITLE
        defaultPhotosShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPhotosByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where title does not contain DEFAULT_TITLE
        defaultPhotosShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the photosList where title does not contain UPDATED_TITLE
        defaultPhotosShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPhotosByIsCoverImageIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where isCoverImage equals to DEFAULT_IS_COVER_IMAGE
        defaultPhotosShouldBeFound("isCoverImage.equals=" + DEFAULT_IS_COVER_IMAGE);

        // Get all the photosList where isCoverImage equals to UPDATED_IS_COVER_IMAGE
        defaultPhotosShouldNotBeFound("isCoverImage.equals=" + UPDATED_IS_COVER_IMAGE);
    }

    @Test
    @Transactional
    void getAllPhotosByIsCoverImageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where isCoverImage not equals to DEFAULT_IS_COVER_IMAGE
        defaultPhotosShouldNotBeFound("isCoverImage.notEquals=" + DEFAULT_IS_COVER_IMAGE);

        // Get all the photosList where isCoverImage not equals to UPDATED_IS_COVER_IMAGE
        defaultPhotosShouldBeFound("isCoverImage.notEquals=" + UPDATED_IS_COVER_IMAGE);
    }

    @Test
    @Transactional
    void getAllPhotosByIsCoverImageIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where isCoverImage in DEFAULT_IS_COVER_IMAGE or UPDATED_IS_COVER_IMAGE
        defaultPhotosShouldBeFound("isCoverImage.in=" + DEFAULT_IS_COVER_IMAGE + "," + UPDATED_IS_COVER_IMAGE);

        // Get all the photosList where isCoverImage equals to UPDATED_IS_COVER_IMAGE
        defaultPhotosShouldNotBeFound("isCoverImage.in=" + UPDATED_IS_COVER_IMAGE);
    }

    @Test
    @Transactional
    void getAllPhotosByIsCoverImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where isCoverImage is not null
        defaultPhotosShouldBeFound("isCoverImage.specified=true");

        // Get all the photosList where isCoverImage is null
        defaultPhotosShouldNotBeFound("isCoverImage.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height equals to DEFAULT_HEIGHT
        defaultPhotosShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the photosList where height equals to UPDATED_HEIGHT
        defaultPhotosShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height not equals to DEFAULT_HEIGHT
        defaultPhotosShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the photosList where height not equals to UPDATED_HEIGHT
        defaultPhotosShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultPhotosShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the photosList where height equals to UPDATED_HEIGHT
        defaultPhotosShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height is not null
        defaultPhotosShouldBeFound("height.specified=true");

        // Get all the photosList where height is null
        defaultPhotosShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height is greater than or equal to DEFAULT_HEIGHT
        defaultPhotosShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the photosList where height is greater than or equal to UPDATED_HEIGHT
        defaultPhotosShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height is less than or equal to DEFAULT_HEIGHT
        defaultPhotosShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the photosList where height is less than or equal to SMALLER_HEIGHT
        defaultPhotosShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height is less than DEFAULT_HEIGHT
        defaultPhotosShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the photosList where height is less than UPDATED_HEIGHT
        defaultPhotosShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllPhotosByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where height is greater than DEFAULT_HEIGHT
        defaultPhotosShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the photosList where height is greater than SMALLER_HEIGHT
        defaultPhotosShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width equals to DEFAULT_WIDTH
        defaultPhotosShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the photosList where width equals to UPDATED_WIDTH
        defaultPhotosShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width not equals to DEFAULT_WIDTH
        defaultPhotosShouldNotBeFound("width.notEquals=" + DEFAULT_WIDTH);

        // Get all the photosList where width not equals to UPDATED_WIDTH
        defaultPhotosShouldBeFound("width.notEquals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultPhotosShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the photosList where width equals to UPDATED_WIDTH
        defaultPhotosShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width is not null
        defaultPhotosShouldBeFound("width.specified=true");

        // Get all the photosList where width is null
        defaultPhotosShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width is greater than or equal to DEFAULT_WIDTH
        defaultPhotosShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the photosList where width is greater than or equal to UPDATED_WIDTH
        defaultPhotosShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width is less than or equal to DEFAULT_WIDTH
        defaultPhotosShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the photosList where width is less than or equal to SMALLER_WIDTH
        defaultPhotosShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width is less than DEFAULT_WIDTH
        defaultPhotosShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the photosList where width is less than UPDATED_WIDTH
        defaultPhotosShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPhotosByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where width is greater than DEFAULT_WIDTH
        defaultPhotosShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the photosList where width is greater than SMALLER_WIDTH
        defaultPhotosShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllPhotosByTakenIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where taken equals to DEFAULT_TAKEN
        defaultPhotosShouldBeFound("taken.equals=" + DEFAULT_TAKEN);

        // Get all the photosList where taken equals to UPDATED_TAKEN
        defaultPhotosShouldNotBeFound("taken.equals=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllPhotosByTakenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where taken not equals to DEFAULT_TAKEN
        defaultPhotosShouldNotBeFound("taken.notEquals=" + DEFAULT_TAKEN);

        // Get all the photosList where taken not equals to UPDATED_TAKEN
        defaultPhotosShouldBeFound("taken.notEquals=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllPhotosByTakenIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where taken in DEFAULT_TAKEN or UPDATED_TAKEN
        defaultPhotosShouldBeFound("taken.in=" + DEFAULT_TAKEN + "," + UPDATED_TAKEN);

        // Get all the photosList where taken equals to UPDATED_TAKEN
        defaultPhotosShouldNotBeFound("taken.in=" + UPDATED_TAKEN);
    }

    @Test
    @Transactional
    void getAllPhotosByTakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where taken is not null
        defaultPhotosShouldBeFound("taken.specified=true");

        // Get all the photosList where taken is null
        defaultPhotosShouldNotBeFound("taken.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByUploadedIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uploaded equals to DEFAULT_UPLOADED
        defaultPhotosShouldBeFound("uploaded.equals=" + DEFAULT_UPLOADED);

        // Get all the photosList where uploaded equals to UPDATED_UPLOADED
        defaultPhotosShouldNotBeFound("uploaded.equals=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllPhotosByUploadedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uploaded not equals to DEFAULT_UPLOADED
        defaultPhotosShouldNotBeFound("uploaded.notEquals=" + DEFAULT_UPLOADED);

        // Get all the photosList where uploaded not equals to UPDATED_UPLOADED
        defaultPhotosShouldBeFound("uploaded.notEquals=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllPhotosByUploadedIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uploaded in DEFAULT_UPLOADED or UPDATED_UPLOADED
        defaultPhotosShouldBeFound("uploaded.in=" + DEFAULT_UPLOADED + "," + UPDATED_UPLOADED);

        // Get all the photosList where uploaded equals to UPDATED_UPLOADED
        defaultPhotosShouldNotBeFound("uploaded.in=" + UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    void getAllPhotosByUploadedIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uploaded is not null
        defaultPhotosShouldBeFound("uploaded.specified=true");

        // Get all the photosList where uploaded is null
        defaultPhotosShouldNotBeFound("uploaded.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdBy equals to DEFAULT_CREATED_BY
        defaultPhotosShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the photosList where createdBy equals to UPDATED_CREATED_BY
        defaultPhotosShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdBy not equals to DEFAULT_CREATED_BY
        defaultPhotosShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the photosList where createdBy not equals to UPDATED_CREATED_BY
        defaultPhotosShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultPhotosShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the photosList where createdBy equals to UPDATED_CREATED_BY
        defaultPhotosShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdBy is not null
        defaultPhotosShouldBeFound("createdBy.specified=true");

        // Get all the photosList where createdBy is null
        defaultPhotosShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdBy contains DEFAULT_CREATED_BY
        defaultPhotosShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the photosList where createdBy contains UPDATED_CREATED_BY
        defaultPhotosShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdBy does not contain DEFAULT_CREATED_BY
        defaultPhotosShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the photosList where createdBy does not contain UPDATED_CREATED_BY
        defaultPhotosShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPhotosShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the photosList where createdDate equals to UPDATED_CREATED_DATE
        defaultPhotosShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultPhotosShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the photosList where createdDate not equals to UPDATED_CREATED_DATE
        defaultPhotosShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPhotosShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the photosList where createdDate equals to UPDATED_CREATED_DATE
        defaultPhotosShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPhotosByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where createdDate is not null
        defaultPhotosShouldBeFound("createdDate.specified=true");

        // Get all the photosList where createdDate is null
        defaultPhotosShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultPhotosShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the photosList where updatedBy equals to UPDATED_UPDATED_BY
        defaultPhotosShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultPhotosShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the photosList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultPhotosShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultPhotosShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the photosList where updatedBy equals to UPDATED_UPDATED_BY
        defaultPhotosShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllPhotosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updatedBy is not null
        defaultPhotosShouldBeFound("updatedBy.specified=true");

        // Get all the photosList where updatedBy is null
        defaultPhotosShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultPhotosShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the photosList where updateDate equals to UPDATED_UPDATE_DATE
        defaultPhotosShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllPhotosByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultPhotosShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the photosList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultPhotosShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllPhotosByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultPhotosShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the photosList where updateDate equals to UPDATED_UPDATE_DATE
        defaultPhotosShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllPhotosByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where updateDate is not null
        defaultPhotosShouldBeFound("updateDate.specified=true");

        // Get all the photosList where updateDate is null
        defaultPhotosShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPhotosByAlbumIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        Album album = AlbumResourceIT.createEntity(em);
        em.persist(album);
        em.flush();
        photos.setAlbum(album);
        photosRepository.saveAndFlush(photos);
        Long albumId = album.getId();

        // Get all the photosList where album equals to albumId
        defaultPhotosShouldBeFound("albumId.equals=" + albumId);

        // Get all the photosList where album equals to (albumId + 1)
        defaultPhotosShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }

    @Test
    @Transactional
    void getAllPhotosByListingIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        Listing listing = ListingResourceIT.createEntity(em);
        em.persist(listing);
        em.flush();
        photos.setListing(listing);
        photosRepository.saveAndFlush(photos);
        Long listingId = listing.getId();

        // Get all the photosList where listing equals to listingId
        defaultPhotosShouldBeFound("listingId.equals=" + listingId);

        // Get all the photosList where listing equals to (listingId + 1)
        defaultPhotosShouldNotBeFound("listingId.equals=" + (listingId + 1));
    }

    @Test
    @Transactional
    void getAllPhotosByTagIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        Tag tag = TagResourceIT.createEntity(em);
        em.persist(tag);
        em.flush();
        photos.addTag(tag);
        photosRepository.saveAndFlush(photos);
        Long tagId = tag.getId();

        // Get all the photosList where tag equals to tagId
        defaultPhotosShouldBeFound("tagId.equals=" + tagId);

        // Get all the photosList where tag equals to (tagId + 1)
        defaultPhotosShouldNotBeFound("tagId.equals=" + (tagId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhotosShouldBeFound(String filter) throws Exception {
        restPhotosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].src").value(hasItem(DEFAULT_SRC)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].isCoverImage").value(hasItem(DEFAULT_IS_COVER_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].taken").value(hasItem(DEFAULT_TAKEN.toString())))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restPhotosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhotosShouldNotBeFound(String filter) throws Exception {
        restPhotosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhotosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPhotos() throws Exception {
        // Get the photos
        restPhotosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Update the photos
        Photos updatedPhotos = photosRepository.findById(photos.getId()).get();
        // Disconnect from session so that the updates on updatedPhotos are not directly saved in db
        em.detach(updatedPhotos);
        updatedPhotos
            .alt(UPDATED_ALT)
            .caption(UPDATED_CAPTION)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .src(UPDATED_SRC)
            .title(UPDATED_TITLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isCoverImage(UPDATED_IS_COVER_IMAGE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restPhotosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhotos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhotos))
            )
            .andExpect(status().isOk());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getAlt()).isEqualTo(UPDATED_ALT);
        assertThat(testPhotos.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testPhotos.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhotos.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testPhotos.getSrc()).isEqualTo(UPDATED_SRC);
        assertThat(testPhotos.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPhotos.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPhotos.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testPhotos.getIsCoverImage()).isEqualTo(UPDATED_IS_COVER_IMAGE);
        assertThat(testPhotos.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPhotos.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPhotos.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testPhotos.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testPhotos.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPhotos.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPhotos.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPhotos.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();
        photos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, photos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();
        photos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(photos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();
        photos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(photos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhotosWithPatch() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Update the photos using partial update
        Photos partialUpdatedPhotos = new Photos();
        partialUpdatedPhotos.setId(photos.getId());

        partialUpdatedPhotos
            .caption(UPDATED_CAPTION)
            .href(UPDATED_HREF)
            .src(UPDATED_SRC)
            .title(UPDATED_TITLE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH);

        restPhotosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhotos))
            )
            .andExpect(status().isOk());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getAlt()).isEqualTo(DEFAULT_ALT);
        assertThat(testPhotos.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testPhotos.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhotos.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testPhotos.getSrc()).isEqualTo(UPDATED_SRC);
        assertThat(testPhotos.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPhotos.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPhotos.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testPhotos.getIsCoverImage()).isEqualTo(DEFAULT_IS_COVER_IMAGE);
        assertThat(testPhotos.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPhotos.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPhotos.getTaken()).isEqualTo(DEFAULT_TAKEN);
        assertThat(testPhotos.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
        assertThat(testPhotos.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPhotos.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPhotos.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPhotos.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePhotosWithPatch() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Update the photos using partial update
        Photos partialUpdatedPhotos = new Photos();
        partialUpdatedPhotos.setId(photos.getId());

        partialUpdatedPhotos
            .alt(UPDATED_ALT)
            .caption(UPDATED_CAPTION)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .src(UPDATED_SRC)
            .title(UPDATED_TITLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .isCoverImage(UPDATED_IS_COVER_IMAGE)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .taken(UPDATED_TAKEN)
            .uploaded(UPDATED_UPLOADED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restPhotosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhotos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhotos))
            )
            .andExpect(status().isOk());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getAlt()).isEqualTo(UPDATED_ALT);
        assertThat(testPhotos.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testPhotos.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhotos.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testPhotos.getSrc()).isEqualTo(UPDATED_SRC);
        assertThat(testPhotos.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPhotos.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPhotos.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testPhotos.getIsCoverImage()).isEqualTo(UPDATED_IS_COVER_IMAGE);
        assertThat(testPhotos.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPhotos.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPhotos.getTaken()).isEqualTo(UPDATED_TAKEN);
        assertThat(testPhotos.getUploaded()).isEqualTo(UPDATED_UPLOADED);
        assertThat(testPhotos.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPhotos.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPhotos.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPhotos.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();
        photos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, photos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();
        photos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(photos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();
        photos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhotosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(photos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeDelete = photosRepository.findAll().size();

        // Delete the photos
        restPhotosMockMvc
            .perform(delete(ENTITY_API_URL_ID, photos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
