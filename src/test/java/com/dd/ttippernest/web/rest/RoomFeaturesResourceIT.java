package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.Room;
import com.dd.ttippernest.domain.RoomFeatures;
import com.dd.ttippernest.repository.RoomFeaturesRepository;
import com.dd.ttippernest.service.criteria.RoomFeaturesCriteria;
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
 * Integration tests for the {@link RoomFeaturesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomFeaturesResourceIT {

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

    private static final Integer DEFAULT_PARENT = 1;
    private static final Integer UPDATED_PARENT = 2;
    private static final Integer SMALLER_PARENT = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/room-features";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomFeaturesRepository roomFeaturesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomFeaturesMockMvc;

    private RoomFeatures roomFeatures;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomFeatures createEntity(EntityManager em) {
        RoomFeatures roomFeatures = new RoomFeatures()
            .title(DEFAULT_TITLE)
            .count(DEFAULT_COUNT)
            .thumbnail(DEFAULT_THUMBNAIL)
            .icon(DEFAULT_ICON)
            .color(DEFAULT_COLOR)
            .imgIcon(DEFAULT_IMG_ICON)
            .description(DEFAULT_DESCRIPTION)
            .parent(DEFAULT_PARENT)
            .taxonomy(DEFAULT_TAXONOMY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return roomFeatures;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomFeatures createUpdatedEntity(EntityManager em) {
        RoomFeatures roomFeatures = new RoomFeatures()
            .title(UPDATED_TITLE)
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .color(UPDATED_COLOR)
            .imgIcon(UPDATED_IMG_ICON)
            .description(UPDATED_DESCRIPTION)
            .parent(UPDATED_PARENT)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return roomFeatures;
    }

    @BeforeEach
    public void initTest() {
        roomFeatures = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomFeatures() throws Exception {
        int databaseSizeBeforeCreate = roomFeaturesRepository.findAll().size();
        // Create the RoomFeatures
        restRoomFeaturesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomFeatures)))
            .andExpect(status().isCreated());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeCreate + 1);
        RoomFeatures testRoomFeatures = roomFeaturesList.get(roomFeaturesList.size() - 1);
        assertThat(testRoomFeatures.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRoomFeatures.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testRoomFeatures.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testRoomFeatures.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testRoomFeatures.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testRoomFeatures.getImgIcon()).isEqualTo(DEFAULT_IMG_ICON);
        assertThat(testRoomFeatures.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoomFeatures.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testRoomFeatures.getTaxonomy()).isEqualTo(DEFAULT_TAXONOMY);
        assertThat(testRoomFeatures.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoomFeatures.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRoomFeatures.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoomFeatures.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createRoomFeaturesWithExistingId() throws Exception {
        // Create the RoomFeatures with an existing ID
        roomFeatures.setId(1L);

        int databaseSizeBeforeCreate = roomFeaturesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomFeaturesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomFeatures)))
            .andExpect(status().isBadRequest());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoomFeatures() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList
        restRoomFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomFeatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].imgIcon").value(hasItem(DEFAULT_IMG_ICON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)))
            .andExpect(jsonPath("$.[*].taxonomy").value(hasItem(DEFAULT_TAXONOMY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getRoomFeatures() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get the roomFeatures
        restRoomFeaturesMockMvc
            .perform(get(ENTITY_API_URL_ID, roomFeatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomFeatures.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.imgIcon").value(DEFAULT_IMG_ICON))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT))
            .andExpect(jsonPath("$.taxonomy").value(DEFAULT_TAXONOMY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getRoomFeaturesByIdFiltering() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        Long id = roomFeatures.getId();

        defaultRoomFeaturesShouldBeFound("id.equals=" + id);
        defaultRoomFeaturesShouldNotBeFound("id.notEquals=" + id);

        defaultRoomFeaturesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomFeaturesShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomFeaturesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomFeaturesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where title equals to DEFAULT_TITLE
        defaultRoomFeaturesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the roomFeaturesList where title equals to UPDATED_TITLE
        defaultRoomFeaturesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where title not equals to DEFAULT_TITLE
        defaultRoomFeaturesShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the roomFeaturesList where title not equals to UPDATED_TITLE
        defaultRoomFeaturesShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultRoomFeaturesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the roomFeaturesList where title equals to UPDATED_TITLE
        defaultRoomFeaturesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where title is not null
        defaultRoomFeaturesShouldBeFound("title.specified=true");

        // Get all the roomFeaturesList where title is null
        defaultRoomFeaturesShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTitleContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where title contains DEFAULT_TITLE
        defaultRoomFeaturesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the roomFeaturesList where title contains UPDATED_TITLE
        defaultRoomFeaturesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where title does not contain DEFAULT_TITLE
        defaultRoomFeaturesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the roomFeaturesList where title does not contain UPDATED_TITLE
        defaultRoomFeaturesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count equals to DEFAULT_COUNT
        defaultRoomFeaturesShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the roomFeaturesList where count equals to UPDATED_COUNT
        defaultRoomFeaturesShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count not equals to DEFAULT_COUNT
        defaultRoomFeaturesShouldNotBeFound("count.notEquals=" + DEFAULT_COUNT);

        // Get all the roomFeaturesList where count not equals to UPDATED_COUNT
        defaultRoomFeaturesShouldBeFound("count.notEquals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultRoomFeaturesShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the roomFeaturesList where count equals to UPDATED_COUNT
        defaultRoomFeaturesShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count is not null
        defaultRoomFeaturesShouldBeFound("count.specified=true");

        // Get all the roomFeaturesList where count is null
        defaultRoomFeaturesShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count is greater than or equal to DEFAULT_COUNT
        defaultRoomFeaturesShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the roomFeaturesList where count is greater than or equal to UPDATED_COUNT
        defaultRoomFeaturesShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count is less than or equal to DEFAULT_COUNT
        defaultRoomFeaturesShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the roomFeaturesList where count is less than or equal to SMALLER_COUNT
        defaultRoomFeaturesShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count is less than DEFAULT_COUNT
        defaultRoomFeaturesShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the roomFeaturesList where count is less than UPDATED_COUNT
        defaultRoomFeaturesShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where count is greater than DEFAULT_COUNT
        defaultRoomFeaturesShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the roomFeaturesList where count is greater than SMALLER_COUNT
        defaultRoomFeaturesShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where thumbnail equals to DEFAULT_THUMBNAIL
        defaultRoomFeaturesShouldBeFound("thumbnail.equals=" + DEFAULT_THUMBNAIL);

        // Get all the roomFeaturesList where thumbnail equals to UPDATED_THUMBNAIL
        defaultRoomFeaturesShouldNotBeFound("thumbnail.equals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where thumbnail not equals to DEFAULT_THUMBNAIL
        defaultRoomFeaturesShouldNotBeFound("thumbnail.notEquals=" + DEFAULT_THUMBNAIL);

        // Get all the roomFeaturesList where thumbnail not equals to UPDATED_THUMBNAIL
        defaultRoomFeaturesShouldBeFound("thumbnail.notEquals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where thumbnail in DEFAULT_THUMBNAIL or UPDATED_THUMBNAIL
        defaultRoomFeaturesShouldBeFound("thumbnail.in=" + DEFAULT_THUMBNAIL + "," + UPDATED_THUMBNAIL);

        // Get all the roomFeaturesList where thumbnail equals to UPDATED_THUMBNAIL
        defaultRoomFeaturesShouldNotBeFound("thumbnail.in=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where thumbnail is not null
        defaultRoomFeaturesShouldBeFound("thumbnail.specified=true");

        // Get all the roomFeaturesList where thumbnail is null
        defaultRoomFeaturesShouldNotBeFound("thumbnail.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByThumbnailContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where thumbnail contains DEFAULT_THUMBNAIL
        defaultRoomFeaturesShouldBeFound("thumbnail.contains=" + DEFAULT_THUMBNAIL);

        // Get all the roomFeaturesList where thumbnail contains UPDATED_THUMBNAIL
        defaultRoomFeaturesShouldNotBeFound("thumbnail.contains=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where thumbnail does not contain DEFAULT_THUMBNAIL
        defaultRoomFeaturesShouldNotBeFound("thumbnail.doesNotContain=" + DEFAULT_THUMBNAIL);

        // Get all the roomFeaturesList where thumbnail does not contain UPDATED_THUMBNAIL
        defaultRoomFeaturesShouldBeFound("thumbnail.doesNotContain=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where icon equals to DEFAULT_ICON
        defaultRoomFeaturesShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the roomFeaturesList where icon equals to UPDATED_ICON
        defaultRoomFeaturesShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where icon not equals to DEFAULT_ICON
        defaultRoomFeaturesShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the roomFeaturesList where icon not equals to UPDATED_ICON
        defaultRoomFeaturesShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultRoomFeaturesShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the roomFeaturesList where icon equals to UPDATED_ICON
        defaultRoomFeaturesShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where icon is not null
        defaultRoomFeaturesShouldBeFound("icon.specified=true");

        // Get all the roomFeaturesList where icon is null
        defaultRoomFeaturesShouldNotBeFound("icon.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByIconContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where icon contains DEFAULT_ICON
        defaultRoomFeaturesShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the roomFeaturesList where icon contains UPDATED_ICON
        defaultRoomFeaturesShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where icon does not contain DEFAULT_ICON
        defaultRoomFeaturesShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the roomFeaturesList where icon does not contain UPDATED_ICON
        defaultRoomFeaturesShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where color equals to DEFAULT_COLOR
        defaultRoomFeaturesShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the roomFeaturesList where color equals to UPDATED_COLOR
        defaultRoomFeaturesShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where color not equals to DEFAULT_COLOR
        defaultRoomFeaturesShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the roomFeaturesList where color not equals to UPDATED_COLOR
        defaultRoomFeaturesShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByColorIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultRoomFeaturesShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the roomFeaturesList where color equals to UPDATED_COLOR
        defaultRoomFeaturesShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where color is not null
        defaultRoomFeaturesShouldBeFound("color.specified=true");

        // Get all the roomFeaturesList where color is null
        defaultRoomFeaturesShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByColorContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where color contains DEFAULT_COLOR
        defaultRoomFeaturesShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the roomFeaturesList where color contains UPDATED_COLOR
        defaultRoomFeaturesShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByColorNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where color does not contain DEFAULT_COLOR
        defaultRoomFeaturesShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the roomFeaturesList where color does not contain UPDATED_COLOR
        defaultRoomFeaturesShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByImgIconIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where imgIcon equals to DEFAULT_IMG_ICON
        defaultRoomFeaturesShouldBeFound("imgIcon.equals=" + DEFAULT_IMG_ICON);

        // Get all the roomFeaturesList where imgIcon equals to UPDATED_IMG_ICON
        defaultRoomFeaturesShouldNotBeFound("imgIcon.equals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByImgIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where imgIcon not equals to DEFAULT_IMG_ICON
        defaultRoomFeaturesShouldNotBeFound("imgIcon.notEquals=" + DEFAULT_IMG_ICON);

        // Get all the roomFeaturesList where imgIcon not equals to UPDATED_IMG_ICON
        defaultRoomFeaturesShouldBeFound("imgIcon.notEquals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByImgIconIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where imgIcon in DEFAULT_IMG_ICON or UPDATED_IMG_ICON
        defaultRoomFeaturesShouldBeFound("imgIcon.in=" + DEFAULT_IMG_ICON + "," + UPDATED_IMG_ICON);

        // Get all the roomFeaturesList where imgIcon equals to UPDATED_IMG_ICON
        defaultRoomFeaturesShouldNotBeFound("imgIcon.in=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByImgIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where imgIcon is not null
        defaultRoomFeaturesShouldBeFound("imgIcon.specified=true");

        // Get all the roomFeaturesList where imgIcon is null
        defaultRoomFeaturesShouldNotBeFound("imgIcon.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByImgIconContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where imgIcon contains DEFAULT_IMG_ICON
        defaultRoomFeaturesShouldBeFound("imgIcon.contains=" + DEFAULT_IMG_ICON);

        // Get all the roomFeaturesList where imgIcon contains UPDATED_IMG_ICON
        defaultRoomFeaturesShouldNotBeFound("imgIcon.contains=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByImgIconNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where imgIcon does not contain DEFAULT_IMG_ICON
        defaultRoomFeaturesShouldNotBeFound("imgIcon.doesNotContain=" + DEFAULT_IMG_ICON);

        // Get all the roomFeaturesList where imgIcon does not contain UPDATED_IMG_ICON
        defaultRoomFeaturesShouldBeFound("imgIcon.doesNotContain=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where description equals to DEFAULT_DESCRIPTION
        defaultRoomFeaturesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the roomFeaturesList where description equals to UPDATED_DESCRIPTION
        defaultRoomFeaturesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where description not equals to DEFAULT_DESCRIPTION
        defaultRoomFeaturesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the roomFeaturesList where description not equals to UPDATED_DESCRIPTION
        defaultRoomFeaturesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRoomFeaturesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the roomFeaturesList where description equals to UPDATED_DESCRIPTION
        defaultRoomFeaturesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where description is not null
        defaultRoomFeaturesShouldBeFound("description.specified=true");

        // Get all the roomFeaturesList where description is null
        defaultRoomFeaturesShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where description contains DEFAULT_DESCRIPTION
        defaultRoomFeaturesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the roomFeaturesList where description contains UPDATED_DESCRIPTION
        defaultRoomFeaturesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where description does not contain DEFAULT_DESCRIPTION
        defaultRoomFeaturesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the roomFeaturesList where description does not contain UPDATED_DESCRIPTION
        defaultRoomFeaturesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent equals to DEFAULT_PARENT
        defaultRoomFeaturesShouldBeFound("parent.equals=" + DEFAULT_PARENT);

        // Get all the roomFeaturesList where parent equals to UPDATED_PARENT
        defaultRoomFeaturesShouldNotBeFound("parent.equals=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent not equals to DEFAULT_PARENT
        defaultRoomFeaturesShouldNotBeFound("parent.notEquals=" + DEFAULT_PARENT);

        // Get all the roomFeaturesList where parent not equals to UPDATED_PARENT
        defaultRoomFeaturesShouldBeFound("parent.notEquals=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent in DEFAULT_PARENT or UPDATED_PARENT
        defaultRoomFeaturesShouldBeFound("parent.in=" + DEFAULT_PARENT + "," + UPDATED_PARENT);

        // Get all the roomFeaturesList where parent equals to UPDATED_PARENT
        defaultRoomFeaturesShouldNotBeFound("parent.in=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent is not null
        defaultRoomFeaturesShouldBeFound("parent.specified=true");

        // Get all the roomFeaturesList where parent is null
        defaultRoomFeaturesShouldNotBeFound("parent.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent is greater than or equal to DEFAULT_PARENT
        defaultRoomFeaturesShouldBeFound("parent.greaterThanOrEqual=" + DEFAULT_PARENT);

        // Get all the roomFeaturesList where parent is greater than or equal to UPDATED_PARENT
        defaultRoomFeaturesShouldNotBeFound("parent.greaterThanOrEqual=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent is less than or equal to DEFAULT_PARENT
        defaultRoomFeaturesShouldBeFound("parent.lessThanOrEqual=" + DEFAULT_PARENT);

        // Get all the roomFeaturesList where parent is less than or equal to SMALLER_PARENT
        defaultRoomFeaturesShouldNotBeFound("parent.lessThanOrEqual=" + SMALLER_PARENT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsLessThanSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent is less than DEFAULT_PARENT
        defaultRoomFeaturesShouldNotBeFound("parent.lessThan=" + DEFAULT_PARENT);

        // Get all the roomFeaturesList where parent is less than UPDATED_PARENT
        defaultRoomFeaturesShouldBeFound("parent.lessThan=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByParentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where parent is greater than DEFAULT_PARENT
        defaultRoomFeaturesShouldNotBeFound("parent.greaterThan=" + DEFAULT_PARENT);

        // Get all the roomFeaturesList where parent is greater than SMALLER_PARENT
        defaultRoomFeaturesShouldBeFound("parent.greaterThan=" + SMALLER_PARENT);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTaxonomyIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where taxonomy equals to DEFAULT_TAXONOMY
        defaultRoomFeaturesShouldBeFound("taxonomy.equals=" + DEFAULT_TAXONOMY);

        // Get all the roomFeaturesList where taxonomy equals to UPDATED_TAXONOMY
        defaultRoomFeaturesShouldNotBeFound("taxonomy.equals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTaxonomyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where taxonomy not equals to DEFAULT_TAXONOMY
        defaultRoomFeaturesShouldNotBeFound("taxonomy.notEquals=" + DEFAULT_TAXONOMY);

        // Get all the roomFeaturesList where taxonomy not equals to UPDATED_TAXONOMY
        defaultRoomFeaturesShouldBeFound("taxonomy.notEquals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTaxonomyIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where taxonomy in DEFAULT_TAXONOMY or UPDATED_TAXONOMY
        defaultRoomFeaturesShouldBeFound("taxonomy.in=" + DEFAULT_TAXONOMY + "," + UPDATED_TAXONOMY);

        // Get all the roomFeaturesList where taxonomy equals to UPDATED_TAXONOMY
        defaultRoomFeaturesShouldNotBeFound("taxonomy.in=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTaxonomyIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where taxonomy is not null
        defaultRoomFeaturesShouldBeFound("taxonomy.specified=true");

        // Get all the roomFeaturesList where taxonomy is null
        defaultRoomFeaturesShouldNotBeFound("taxonomy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTaxonomyContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where taxonomy contains DEFAULT_TAXONOMY
        defaultRoomFeaturesShouldBeFound("taxonomy.contains=" + DEFAULT_TAXONOMY);

        // Get all the roomFeaturesList where taxonomy contains UPDATED_TAXONOMY
        defaultRoomFeaturesShouldNotBeFound("taxonomy.contains=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByTaxonomyNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where taxonomy does not contain DEFAULT_TAXONOMY
        defaultRoomFeaturesShouldNotBeFound("taxonomy.doesNotContain=" + DEFAULT_TAXONOMY);

        // Get all the roomFeaturesList where taxonomy does not contain UPDATED_TAXONOMY
        defaultRoomFeaturesShouldBeFound("taxonomy.doesNotContain=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdBy equals to DEFAULT_CREATED_BY
        defaultRoomFeaturesShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the roomFeaturesList where createdBy equals to UPDATED_CREATED_BY
        defaultRoomFeaturesShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdBy not equals to DEFAULT_CREATED_BY
        defaultRoomFeaturesShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the roomFeaturesList where createdBy not equals to UPDATED_CREATED_BY
        defaultRoomFeaturesShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultRoomFeaturesShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the roomFeaturesList where createdBy equals to UPDATED_CREATED_BY
        defaultRoomFeaturesShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdBy is not null
        defaultRoomFeaturesShouldBeFound("createdBy.specified=true");

        // Get all the roomFeaturesList where createdBy is null
        defaultRoomFeaturesShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdBy contains DEFAULT_CREATED_BY
        defaultRoomFeaturesShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the roomFeaturesList where createdBy contains UPDATED_CREATED_BY
        defaultRoomFeaturesShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdBy does not contain DEFAULT_CREATED_BY
        defaultRoomFeaturesShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the roomFeaturesList where createdBy does not contain UPDATED_CREATED_BY
        defaultRoomFeaturesShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdDate equals to DEFAULT_CREATED_DATE
        defaultRoomFeaturesShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the roomFeaturesList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoomFeaturesShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultRoomFeaturesShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the roomFeaturesList where createdDate not equals to UPDATED_CREATED_DATE
        defaultRoomFeaturesShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultRoomFeaturesShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the roomFeaturesList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoomFeaturesShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where createdDate is not null
        defaultRoomFeaturesShouldBeFound("createdDate.specified=true");

        // Get all the roomFeaturesList where createdDate is null
        defaultRoomFeaturesShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultRoomFeaturesShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the roomFeaturesList where updatedBy equals to UPDATED_UPDATED_BY
        defaultRoomFeaturesShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultRoomFeaturesShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the roomFeaturesList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultRoomFeaturesShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultRoomFeaturesShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the roomFeaturesList where updatedBy equals to UPDATED_UPDATED_BY
        defaultRoomFeaturesShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updatedBy is not null
        defaultRoomFeaturesShouldBeFound("updatedBy.specified=true");

        // Get all the roomFeaturesList where updatedBy is null
        defaultRoomFeaturesShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultRoomFeaturesShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the roomFeaturesList where updateDate equals to UPDATED_UPDATE_DATE
        defaultRoomFeaturesShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultRoomFeaturesShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the roomFeaturesList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultRoomFeaturesShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultRoomFeaturesShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the roomFeaturesList where updateDate equals to UPDATED_UPDATE_DATE
        defaultRoomFeaturesShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        // Get all the roomFeaturesList where updateDate is not null
        defaultRoomFeaturesShouldBeFound("updateDate.specified=true");

        // Get all the roomFeaturesList where updateDate is null
        defaultRoomFeaturesShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomFeaturesByRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);
        Room room = RoomResourceIT.createEntity(em);
        em.persist(room);
        em.flush();
        roomFeatures.setRoom(room);
        roomFeaturesRepository.saveAndFlush(roomFeatures);
        Long roomId = room.getId();

        // Get all the roomFeaturesList where room equals to roomId
        defaultRoomFeaturesShouldBeFound("roomId.equals=" + roomId);

        // Get all the roomFeaturesList where room equals to (roomId + 1)
        defaultRoomFeaturesShouldNotBeFound("roomId.equals=" + (roomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomFeaturesShouldBeFound(String filter) throws Exception {
        restRoomFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomFeatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].imgIcon").value(hasItem(DEFAULT_IMG_ICON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)))
            .andExpect(jsonPath("$.[*].taxonomy").value(hasItem(DEFAULT_TAXONOMY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restRoomFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomFeaturesShouldNotBeFound(String filter) throws Exception {
        restRoomFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoomFeatures() throws Exception {
        // Get the roomFeatures
        restRoomFeaturesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoomFeatures() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();

        // Update the roomFeatures
        RoomFeatures updatedRoomFeatures = roomFeaturesRepository.findById(roomFeatures.getId()).get();
        // Disconnect from session so that the updates on updatedRoomFeatures are not directly saved in db
        em.detach(updatedRoomFeatures);
        updatedRoomFeatures
            .title(UPDATED_TITLE)
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .color(UPDATED_COLOR)
            .imgIcon(UPDATED_IMG_ICON)
            .description(UPDATED_DESCRIPTION)
            .parent(UPDATED_PARENT)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restRoomFeaturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoomFeatures.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoomFeatures))
            )
            .andExpect(status().isOk());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
        RoomFeatures testRoomFeatures = roomFeaturesList.get(roomFeaturesList.size() - 1);
        assertThat(testRoomFeatures.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRoomFeatures.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testRoomFeatures.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testRoomFeatures.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testRoomFeatures.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testRoomFeatures.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testRoomFeatures.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomFeatures.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testRoomFeatures.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testRoomFeatures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoomFeatures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoomFeatures.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoomFeatures.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRoomFeatures() throws Exception {
        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();
        roomFeatures.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomFeaturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomFeatures.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomFeatures))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomFeatures() throws Exception {
        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();
        roomFeatures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomFeaturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomFeatures))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomFeatures() throws Exception {
        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();
        roomFeatures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomFeaturesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomFeatures)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomFeaturesWithPatch() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();

        // Update the roomFeatures using partial update
        RoomFeatures partialUpdatedRoomFeatures = new RoomFeatures();
        partialUpdatedRoomFeatures.setId(roomFeatures.getId());

        partialUpdatedRoomFeatures
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .color(UPDATED_COLOR)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restRoomFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomFeatures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomFeatures))
            )
            .andExpect(status().isOk());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
        RoomFeatures testRoomFeatures = roomFeaturesList.get(roomFeaturesList.size() - 1);
        assertThat(testRoomFeatures.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRoomFeatures.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testRoomFeatures.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testRoomFeatures.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testRoomFeatures.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testRoomFeatures.getImgIcon()).isEqualTo(DEFAULT_IMG_ICON);
        assertThat(testRoomFeatures.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoomFeatures.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testRoomFeatures.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testRoomFeatures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoomFeatures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoomFeatures.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoomFeatures.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRoomFeaturesWithPatch() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();

        // Update the roomFeatures using partial update
        RoomFeatures partialUpdatedRoomFeatures = new RoomFeatures();
        partialUpdatedRoomFeatures.setId(roomFeatures.getId());

        partialUpdatedRoomFeatures
            .title(UPDATED_TITLE)
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .icon(UPDATED_ICON)
            .color(UPDATED_COLOR)
            .imgIcon(UPDATED_IMG_ICON)
            .description(UPDATED_DESCRIPTION)
            .parent(UPDATED_PARENT)
            .taxonomy(UPDATED_TAXONOMY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restRoomFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomFeatures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomFeatures))
            )
            .andExpect(status().isOk());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
        RoomFeatures testRoomFeatures = roomFeaturesList.get(roomFeaturesList.size() - 1);
        assertThat(testRoomFeatures.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRoomFeatures.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testRoomFeatures.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testRoomFeatures.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testRoomFeatures.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testRoomFeatures.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testRoomFeatures.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomFeatures.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testRoomFeatures.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testRoomFeatures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoomFeatures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoomFeatures.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoomFeatures.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRoomFeatures() throws Exception {
        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();
        roomFeatures.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomFeatures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomFeatures))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomFeatures() throws Exception {
        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();
        roomFeatures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomFeatures))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomFeatures() throws Exception {
        int databaseSizeBeforeUpdate = roomFeaturesRepository.findAll().size();
        roomFeatures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roomFeatures))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomFeatures in the database
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomFeatures() throws Exception {
        // Initialize the database
        roomFeaturesRepository.saveAndFlush(roomFeatures);

        int databaseSizeBeforeDelete = roomFeaturesRepository.findAll().size();

        // Delete the roomFeatures
        restRoomFeaturesMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomFeatures.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomFeatures> roomFeaturesList = roomFeaturesRepository.findAll();
        assertThat(roomFeaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
