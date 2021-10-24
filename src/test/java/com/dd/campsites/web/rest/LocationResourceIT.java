package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Location;
import com.dd.campsites.domain.Location;
import com.dd.campsites.repository.LocationRepository;
import com.dd.campsites.service.criteria.LocationCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;
    private static final Integer SMALLER_COUNT = 1 - 1;

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_ICON = "AAAAAAAAAA";
    private static final String UPDATED_IMG_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TAXONOMY = "AAAAAAAAAA";
    private static final String UPDATED_TAXONOMY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationMockMvc;

    private Location location;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .title(DEFAULT_TITLE)
            .count(DEFAULT_COUNT)
            .thumbnail(DEFAULT_THUMBNAIL)
            .icon(DEFAULT_ICON)
            .color(DEFAULT_COLOR)
            .imgIcon(DEFAULT_IMG_ICON)
            .description(DEFAULT_DESCRIPTION)
            .taxonomy(DEFAULT_TAXONOMY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return location;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity(EntityManager em) {
        Location location = new Location()
            .title(UPDATED_TITLE)
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .color(UPDATED_COLOR)
            .imgIcon(UPDATED_IMG_ICON)
            .description(UPDATED_DESCRIPTION)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return location;
    }

    @BeforeEach
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();
        // Create the Location
        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLocation.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testLocation.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testLocation.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testLocation.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testLocation.getImgIcon()).isEqualTo(DEFAULT_IMG_ICON);
        assertThat(testLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLocation.getTaxonomy()).isEqualTo(DEFAULT_TAXONOMY);
        assertThat(testLocation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLocation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLocation.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId(1L);

        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].imgIcon").value(hasItem(DEFAULT_IMG_ICON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].taxonomy").value(hasItem(DEFAULT_TAXONOMY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.imgIcon").value(DEFAULT_IMG_ICON))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.taxonomy").value(DEFAULT_TAXONOMY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getLocationsByIdFiltering() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        Long id = location.getId();

        defaultLocationShouldBeFound("id.equals=" + id);
        defaultLocationShouldNotBeFound("id.notEquals=" + id);

        defaultLocationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLocationShouldNotBeFound("id.greaterThan=" + id);

        defaultLocationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLocationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLocationsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where title equals to DEFAULT_TITLE
        defaultLocationShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the locationList where title equals to UPDATED_TITLE
        defaultLocationShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLocationsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where title not equals to DEFAULT_TITLE
        defaultLocationShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the locationList where title not equals to UPDATED_TITLE
        defaultLocationShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLocationsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultLocationShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the locationList where title equals to UPDATED_TITLE
        defaultLocationShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLocationsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where title is not null
        defaultLocationShouldBeFound("title.specified=true");

        // Get all the locationList where title is null
        defaultLocationShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByTitleContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where title contains DEFAULT_TITLE
        defaultLocationShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the locationList where title contains UPDATED_TITLE
        defaultLocationShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLocationsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where title does not contain DEFAULT_TITLE
        defaultLocationShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the locationList where title does not contain UPDATED_TITLE
        defaultLocationShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count equals to DEFAULT_COUNT
        defaultLocationShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the locationList where count equals to UPDATED_COUNT
        defaultLocationShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count not equals to DEFAULT_COUNT
        defaultLocationShouldNotBeFound("count.notEquals=" + DEFAULT_COUNT);

        // Get all the locationList where count not equals to UPDATED_COUNT
        defaultLocationShouldBeFound("count.notEquals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultLocationShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the locationList where count equals to UPDATED_COUNT
        defaultLocationShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count is not null
        defaultLocationShouldBeFound("count.specified=true");

        // Get all the locationList where count is null
        defaultLocationShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count is greater than or equal to DEFAULT_COUNT
        defaultLocationShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the locationList where count is greater than or equal to UPDATED_COUNT
        defaultLocationShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count is less than or equal to DEFAULT_COUNT
        defaultLocationShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the locationList where count is less than or equal to SMALLER_COUNT
        defaultLocationShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count is less than DEFAULT_COUNT
        defaultLocationShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the locationList where count is less than UPDATED_COUNT
        defaultLocationShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllLocationsByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where count is greater than DEFAULT_COUNT
        defaultLocationShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the locationList where count is greater than SMALLER_COUNT
        defaultLocationShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllLocationsByThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where thumbnail equals to DEFAULT_THUMBNAIL
        defaultLocationShouldBeFound("thumbnail.equals=" + DEFAULT_THUMBNAIL);

        // Get all the locationList where thumbnail equals to UPDATED_THUMBNAIL
        defaultLocationShouldNotBeFound("thumbnail.equals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllLocationsByThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where thumbnail not equals to DEFAULT_THUMBNAIL
        defaultLocationShouldNotBeFound("thumbnail.notEquals=" + DEFAULT_THUMBNAIL);

        // Get all the locationList where thumbnail not equals to UPDATED_THUMBNAIL
        defaultLocationShouldBeFound("thumbnail.notEquals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllLocationsByThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where thumbnail in DEFAULT_THUMBNAIL or UPDATED_THUMBNAIL
        defaultLocationShouldBeFound("thumbnail.in=" + DEFAULT_THUMBNAIL + "," + UPDATED_THUMBNAIL);

        // Get all the locationList where thumbnail equals to UPDATED_THUMBNAIL
        defaultLocationShouldNotBeFound("thumbnail.in=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllLocationsByThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where thumbnail is not null
        defaultLocationShouldBeFound("thumbnail.specified=true");

        // Get all the locationList where thumbnail is null
        defaultLocationShouldNotBeFound("thumbnail.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByThumbnailContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where thumbnail contains DEFAULT_THUMBNAIL
        defaultLocationShouldBeFound("thumbnail.contains=" + DEFAULT_THUMBNAIL);

        // Get all the locationList where thumbnail contains UPDATED_THUMBNAIL
        defaultLocationShouldNotBeFound("thumbnail.contains=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllLocationsByThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where thumbnail does not contain DEFAULT_THUMBNAIL
        defaultLocationShouldNotBeFound("thumbnail.doesNotContain=" + DEFAULT_THUMBNAIL);

        // Get all the locationList where thumbnail does not contain UPDATED_THUMBNAIL
        defaultLocationShouldBeFound("thumbnail.doesNotContain=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllLocationsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where icon equals to DEFAULT_ICON
        defaultLocationShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the locationList where icon equals to UPDATED_ICON
        defaultLocationShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where icon not equals to DEFAULT_ICON
        defaultLocationShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the locationList where icon not equals to UPDATED_ICON
        defaultLocationShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByIconIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultLocationShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the locationList where icon equals to UPDATED_ICON
        defaultLocationShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where icon is not null
        defaultLocationShouldBeFound("icon.specified=true");

        // Get all the locationList where icon is null
        defaultLocationShouldNotBeFound("icon.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByIconContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where icon contains DEFAULT_ICON
        defaultLocationShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the locationList where icon contains UPDATED_ICON
        defaultLocationShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByIconNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where icon does not contain DEFAULT_ICON
        defaultLocationShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the locationList where icon does not contain UPDATED_ICON
        defaultLocationShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where color equals to DEFAULT_COLOR
        defaultLocationShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the locationList where color equals to UPDATED_COLOR
        defaultLocationShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllLocationsByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where color not equals to DEFAULT_COLOR
        defaultLocationShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the locationList where color not equals to UPDATED_COLOR
        defaultLocationShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllLocationsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultLocationShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the locationList where color equals to UPDATED_COLOR
        defaultLocationShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllLocationsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where color is not null
        defaultLocationShouldBeFound("color.specified=true");

        // Get all the locationList where color is null
        defaultLocationShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByColorContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where color contains DEFAULT_COLOR
        defaultLocationShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the locationList where color contains UPDATED_COLOR
        defaultLocationShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllLocationsByColorNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where color does not contain DEFAULT_COLOR
        defaultLocationShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the locationList where color does not contain UPDATED_COLOR
        defaultLocationShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllLocationsByImgIconIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where imgIcon equals to DEFAULT_IMG_ICON
        defaultLocationShouldBeFound("imgIcon.equals=" + DEFAULT_IMG_ICON);

        // Get all the locationList where imgIcon equals to UPDATED_IMG_ICON
        defaultLocationShouldNotBeFound("imgIcon.equals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByImgIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where imgIcon not equals to DEFAULT_IMG_ICON
        defaultLocationShouldNotBeFound("imgIcon.notEquals=" + DEFAULT_IMG_ICON);

        // Get all the locationList where imgIcon not equals to UPDATED_IMG_ICON
        defaultLocationShouldBeFound("imgIcon.notEquals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByImgIconIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where imgIcon in DEFAULT_IMG_ICON or UPDATED_IMG_ICON
        defaultLocationShouldBeFound("imgIcon.in=" + DEFAULT_IMG_ICON + "," + UPDATED_IMG_ICON);

        // Get all the locationList where imgIcon equals to UPDATED_IMG_ICON
        defaultLocationShouldNotBeFound("imgIcon.in=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByImgIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where imgIcon is not null
        defaultLocationShouldBeFound("imgIcon.specified=true");

        // Get all the locationList where imgIcon is null
        defaultLocationShouldNotBeFound("imgIcon.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByImgIconContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where imgIcon contains DEFAULT_IMG_ICON
        defaultLocationShouldBeFound("imgIcon.contains=" + DEFAULT_IMG_ICON);

        // Get all the locationList where imgIcon contains UPDATED_IMG_ICON
        defaultLocationShouldNotBeFound("imgIcon.contains=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByImgIconNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where imgIcon does not contain DEFAULT_IMG_ICON
        defaultLocationShouldNotBeFound("imgIcon.doesNotContain=" + DEFAULT_IMG_ICON);

        // Get all the locationList where imgIcon does not contain UPDATED_IMG_ICON
        defaultLocationShouldBeFound("imgIcon.doesNotContain=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllLocationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where description equals to DEFAULT_DESCRIPTION
        defaultLocationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the locationList where description equals to UPDATED_DESCRIPTION
        defaultLocationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLocationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where description not equals to DEFAULT_DESCRIPTION
        defaultLocationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the locationList where description not equals to UPDATED_DESCRIPTION
        defaultLocationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLocationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLocationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the locationList where description equals to UPDATED_DESCRIPTION
        defaultLocationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLocationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where description is not null
        defaultLocationShouldBeFound("description.specified=true");

        // Get all the locationList where description is null
        defaultLocationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where description contains DEFAULT_DESCRIPTION
        defaultLocationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the locationList where description contains UPDATED_DESCRIPTION
        defaultLocationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLocationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where description does not contain DEFAULT_DESCRIPTION
        defaultLocationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the locationList where description does not contain UPDATED_DESCRIPTION
        defaultLocationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLocationsByTaxonomyIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where taxonomy equals to DEFAULT_TAXONOMY
        defaultLocationShouldBeFound("taxonomy.equals=" + DEFAULT_TAXONOMY);

        // Get all the locationList where taxonomy equals to UPDATED_TAXONOMY
        defaultLocationShouldNotBeFound("taxonomy.equals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllLocationsByTaxonomyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where taxonomy not equals to DEFAULT_TAXONOMY
        defaultLocationShouldNotBeFound("taxonomy.notEquals=" + DEFAULT_TAXONOMY);

        // Get all the locationList where taxonomy not equals to UPDATED_TAXONOMY
        defaultLocationShouldBeFound("taxonomy.notEquals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllLocationsByTaxonomyIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where taxonomy in DEFAULT_TAXONOMY or UPDATED_TAXONOMY
        defaultLocationShouldBeFound("taxonomy.in=" + DEFAULT_TAXONOMY + "," + UPDATED_TAXONOMY);

        // Get all the locationList where taxonomy equals to UPDATED_TAXONOMY
        defaultLocationShouldNotBeFound("taxonomy.in=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllLocationsByTaxonomyIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where taxonomy is not null
        defaultLocationShouldBeFound("taxonomy.specified=true");

        // Get all the locationList where taxonomy is null
        defaultLocationShouldNotBeFound("taxonomy.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByTaxonomyContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where taxonomy contains DEFAULT_TAXONOMY
        defaultLocationShouldBeFound("taxonomy.contains=" + DEFAULT_TAXONOMY);

        // Get all the locationList where taxonomy contains UPDATED_TAXONOMY
        defaultLocationShouldNotBeFound("taxonomy.contains=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllLocationsByTaxonomyNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where taxonomy does not contain DEFAULT_TAXONOMY
        defaultLocationShouldNotBeFound("taxonomy.doesNotContain=" + DEFAULT_TAXONOMY);

        // Get all the locationList where taxonomy does not contain UPDATED_TAXONOMY
        defaultLocationShouldBeFound("taxonomy.doesNotContain=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy equals to DEFAULT_CREATED_BY
        defaultLocationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the locationList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy not equals to DEFAULT_CREATED_BY
        defaultLocationShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the locationList where createdBy not equals to UPDATED_CREATED_BY
        defaultLocationShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLocationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the locationList where createdBy equals to UPDATED_CREATED_BY
        defaultLocationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy is not null
        defaultLocationShouldBeFound("createdBy.specified=true");

        // Get all the locationList where createdBy is null
        defaultLocationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy contains DEFAULT_CREATED_BY
        defaultLocationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the locationList where createdBy contains UPDATED_CREATED_BY
        defaultLocationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultLocationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the locationList where createdBy does not contain UPDATED_CREATED_BY
        defaultLocationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultLocationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the locationList where createdDate equals to UPDATED_CREATED_DATE
        defaultLocationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultLocationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the locationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultLocationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultLocationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the locationList where createdDate equals to UPDATED_CREATED_DATE
        defaultLocationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllLocationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where createdDate is not null
        defaultLocationShouldBeFound("createdDate.specified=true");

        // Get all the locationList where createdDate is null
        defaultLocationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultLocationShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the locationList where updatedBy equals to UPDATED_UPDATED_BY
        defaultLocationShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultLocationShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the locationList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultLocationShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultLocationShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the locationList where updatedBy equals to UPDATED_UPDATED_BY
        defaultLocationShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updatedBy is not null
        defaultLocationShouldBeFound("updatedBy.specified=true");

        // Get all the locationList where updatedBy is null
        defaultLocationShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultLocationShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the locationList where updateDate equals to UPDATED_UPDATE_DATE
        defaultLocationShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultLocationShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the locationList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultLocationShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultLocationShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the locationList where updateDate equals to UPDATED_UPDATE_DATE
        defaultLocationShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllLocationsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList where updateDate is not null
        defaultLocationShouldBeFound("updateDate.specified=true");

        // Get all the locationList where updateDate is null
        defaultLocationShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        Location parent = LocationResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        location.setParent(parent);
        locationRepository.saveAndFlush(location);
        Long parentId = parent.getId();

        // Get all the locationList where parent equals to parentId
        defaultLocationShouldBeFound("parentId.equals=" + parentId);

        // Get all the locationList where parent equals to (parentId + 1)
        defaultLocationShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationShouldBeFound(String filter) throws Exception {
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].imgIcon").value(hasItem(DEFAULT_IMG_ICON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].taxonomy").value(hasItem(DEFAULT_TAXONOMY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationShouldNotBeFound(String filter) throws Exception {
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).get();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .title(UPDATED_TITLE)
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .color(UPDATED_COLOR)
            .imgIcon(UPDATED_IMG_ICON)
            .description(UPDATED_DESCRIPTION)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLocation.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testLocation.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testLocation.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testLocation.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testLocation.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLocation.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLocation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, location.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .title(UPDATED_TITLE)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .imgIcon(UPDATED_IMG_ICON)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLocation.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testLocation.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testLocation.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testLocation.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testLocation.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLocation.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLocation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .title(UPDATED_TITLE)
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .color(UPDATED_COLOR)
            .imgIcon(UPDATED_IMG_ICON)
            .description(UPDATED_DESCRIPTION)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLocation.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testLocation.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testLocation.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testLocation.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testLocation.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLocation.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLocation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLocation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, location.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Delete the location
        restLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, location.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
