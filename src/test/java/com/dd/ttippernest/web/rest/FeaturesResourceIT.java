package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.Features;
import com.dd.ttippernest.repository.FeaturesRepository;
import com.dd.ttippernest.service.criteria.FeaturesCriteria;
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
 * Integration tests for the {@link FeaturesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeaturesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/features";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeaturesRepository featuresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeaturesMockMvc;

    private Features features;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Features createEntity(EntityManager em) {
        Features features = new Features()
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
        return features;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Features createUpdatedEntity(EntityManager em) {
        Features features = new Features()
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
        return features;
    }

    @BeforeEach
    public void initTest() {
        features = createEntity(em);
    }

    @Test
    @Transactional
    void createFeatures() throws Exception {
        int databaseSizeBeforeCreate = featuresRepository.findAll().size();
        // Create the Features
        restFeaturesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(features)))
            .andExpect(status().isCreated());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeCreate + 1);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFeatures.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testFeatures.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testFeatures.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testFeatures.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testFeatures.getImgIcon()).isEqualTo(DEFAULT_IMG_ICON);
        assertThat(testFeatures.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeatures.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testFeatures.getTaxonomy()).isEqualTo(DEFAULT_TAXONOMY);
        assertThat(testFeatures.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFeatures.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFeatures.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFeatures.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createFeaturesWithExistingId() throws Exception {
        // Create the Features with an existing ID
        features.setId(1L);

        int databaseSizeBeforeCreate = featuresRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeaturesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(features)))
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList
        restFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(features.getId().intValue())))
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
    void getFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get the features
        restFeaturesMockMvc
            .perform(get(ENTITY_API_URL_ID, features.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(features.getId().intValue()))
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
    void getFeaturesByIdFiltering() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        Long id = features.getId();

        defaultFeaturesShouldBeFound("id.equals=" + id);
        defaultFeaturesShouldNotBeFound("id.notEquals=" + id);

        defaultFeaturesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeaturesShouldNotBeFound("id.greaterThan=" + id);

        defaultFeaturesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeaturesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeaturesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where title equals to DEFAULT_TITLE
        defaultFeaturesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the featuresList where title equals to UPDATED_TITLE
        defaultFeaturesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFeaturesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where title not equals to DEFAULT_TITLE
        defaultFeaturesShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the featuresList where title not equals to UPDATED_TITLE
        defaultFeaturesShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFeaturesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultFeaturesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the featuresList where title equals to UPDATED_TITLE
        defaultFeaturesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFeaturesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where title is not null
        defaultFeaturesShouldBeFound("title.specified=true");

        // Get all the featuresList where title is null
        defaultFeaturesShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByTitleContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where title contains DEFAULT_TITLE
        defaultFeaturesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the featuresList where title contains UPDATED_TITLE
        defaultFeaturesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFeaturesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where title does not contain DEFAULT_TITLE
        defaultFeaturesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the featuresList where title does not contain UPDATED_TITLE
        defaultFeaturesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count equals to DEFAULT_COUNT
        defaultFeaturesShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the featuresList where count equals to UPDATED_COUNT
        defaultFeaturesShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count not equals to DEFAULT_COUNT
        defaultFeaturesShouldNotBeFound("count.notEquals=" + DEFAULT_COUNT);

        // Get all the featuresList where count not equals to UPDATED_COUNT
        defaultFeaturesShouldBeFound("count.notEquals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultFeaturesShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the featuresList where count equals to UPDATED_COUNT
        defaultFeaturesShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count is not null
        defaultFeaturesShouldBeFound("count.specified=true");

        // Get all the featuresList where count is null
        defaultFeaturesShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count is greater than or equal to DEFAULT_COUNT
        defaultFeaturesShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the featuresList where count is greater than or equal to UPDATED_COUNT
        defaultFeaturesShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count is less than or equal to DEFAULT_COUNT
        defaultFeaturesShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the featuresList where count is less than or equal to SMALLER_COUNT
        defaultFeaturesShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count is less than DEFAULT_COUNT
        defaultFeaturesShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the featuresList where count is less than UPDATED_COUNT
        defaultFeaturesShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllFeaturesByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where count is greater than DEFAULT_COUNT
        defaultFeaturesShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the featuresList where count is greater than SMALLER_COUNT
        defaultFeaturesShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllFeaturesByThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where thumbnail equals to DEFAULT_THUMBNAIL
        defaultFeaturesShouldBeFound("thumbnail.equals=" + DEFAULT_THUMBNAIL);

        // Get all the featuresList where thumbnail equals to UPDATED_THUMBNAIL
        defaultFeaturesShouldNotBeFound("thumbnail.equals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllFeaturesByThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where thumbnail not equals to DEFAULT_THUMBNAIL
        defaultFeaturesShouldNotBeFound("thumbnail.notEquals=" + DEFAULT_THUMBNAIL);

        // Get all the featuresList where thumbnail not equals to UPDATED_THUMBNAIL
        defaultFeaturesShouldBeFound("thumbnail.notEquals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllFeaturesByThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where thumbnail in DEFAULT_THUMBNAIL or UPDATED_THUMBNAIL
        defaultFeaturesShouldBeFound("thumbnail.in=" + DEFAULT_THUMBNAIL + "," + UPDATED_THUMBNAIL);

        // Get all the featuresList where thumbnail equals to UPDATED_THUMBNAIL
        defaultFeaturesShouldNotBeFound("thumbnail.in=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllFeaturesByThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where thumbnail is not null
        defaultFeaturesShouldBeFound("thumbnail.specified=true");

        // Get all the featuresList where thumbnail is null
        defaultFeaturesShouldNotBeFound("thumbnail.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByThumbnailContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where thumbnail contains DEFAULT_THUMBNAIL
        defaultFeaturesShouldBeFound("thumbnail.contains=" + DEFAULT_THUMBNAIL);

        // Get all the featuresList where thumbnail contains UPDATED_THUMBNAIL
        defaultFeaturesShouldNotBeFound("thumbnail.contains=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllFeaturesByThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where thumbnail does not contain DEFAULT_THUMBNAIL
        defaultFeaturesShouldNotBeFound("thumbnail.doesNotContain=" + DEFAULT_THUMBNAIL);

        // Get all the featuresList where thumbnail does not contain UPDATED_THUMBNAIL
        defaultFeaturesShouldBeFound("thumbnail.doesNotContain=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllFeaturesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where icon equals to DEFAULT_ICON
        defaultFeaturesShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the featuresList where icon equals to UPDATED_ICON
        defaultFeaturesShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where icon not equals to DEFAULT_ICON
        defaultFeaturesShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the featuresList where icon not equals to UPDATED_ICON
        defaultFeaturesShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultFeaturesShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the featuresList where icon equals to UPDATED_ICON
        defaultFeaturesShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where icon is not null
        defaultFeaturesShouldBeFound("icon.specified=true");

        // Get all the featuresList where icon is null
        defaultFeaturesShouldNotBeFound("icon.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByIconContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where icon contains DEFAULT_ICON
        defaultFeaturesShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the featuresList where icon contains UPDATED_ICON
        defaultFeaturesShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where icon does not contain DEFAULT_ICON
        defaultFeaturesShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the featuresList where icon does not contain UPDATED_ICON
        defaultFeaturesShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where color equals to DEFAULT_COLOR
        defaultFeaturesShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the featuresList where color equals to UPDATED_COLOR
        defaultFeaturesShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllFeaturesByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where color not equals to DEFAULT_COLOR
        defaultFeaturesShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the featuresList where color not equals to UPDATED_COLOR
        defaultFeaturesShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllFeaturesByColorIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultFeaturesShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the featuresList where color equals to UPDATED_COLOR
        defaultFeaturesShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllFeaturesByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where color is not null
        defaultFeaturesShouldBeFound("color.specified=true");

        // Get all the featuresList where color is null
        defaultFeaturesShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByColorContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where color contains DEFAULT_COLOR
        defaultFeaturesShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the featuresList where color contains UPDATED_COLOR
        defaultFeaturesShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllFeaturesByColorNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where color does not contain DEFAULT_COLOR
        defaultFeaturesShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the featuresList where color does not contain UPDATED_COLOR
        defaultFeaturesShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllFeaturesByImgIconIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where imgIcon equals to DEFAULT_IMG_ICON
        defaultFeaturesShouldBeFound("imgIcon.equals=" + DEFAULT_IMG_ICON);

        // Get all the featuresList where imgIcon equals to UPDATED_IMG_ICON
        defaultFeaturesShouldNotBeFound("imgIcon.equals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByImgIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where imgIcon not equals to DEFAULT_IMG_ICON
        defaultFeaturesShouldNotBeFound("imgIcon.notEquals=" + DEFAULT_IMG_ICON);

        // Get all the featuresList where imgIcon not equals to UPDATED_IMG_ICON
        defaultFeaturesShouldBeFound("imgIcon.notEquals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByImgIconIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where imgIcon in DEFAULT_IMG_ICON or UPDATED_IMG_ICON
        defaultFeaturesShouldBeFound("imgIcon.in=" + DEFAULT_IMG_ICON + "," + UPDATED_IMG_ICON);

        // Get all the featuresList where imgIcon equals to UPDATED_IMG_ICON
        defaultFeaturesShouldNotBeFound("imgIcon.in=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByImgIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where imgIcon is not null
        defaultFeaturesShouldBeFound("imgIcon.specified=true");

        // Get all the featuresList where imgIcon is null
        defaultFeaturesShouldNotBeFound("imgIcon.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByImgIconContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where imgIcon contains DEFAULT_IMG_ICON
        defaultFeaturesShouldBeFound("imgIcon.contains=" + DEFAULT_IMG_ICON);

        // Get all the featuresList where imgIcon contains UPDATED_IMG_ICON
        defaultFeaturesShouldNotBeFound("imgIcon.contains=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByImgIconNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where imgIcon does not contain DEFAULT_IMG_ICON
        defaultFeaturesShouldNotBeFound("imgIcon.doesNotContain=" + DEFAULT_IMG_ICON);

        // Get all the featuresList where imgIcon does not contain UPDATED_IMG_ICON
        defaultFeaturesShouldBeFound("imgIcon.doesNotContain=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllFeaturesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where description equals to DEFAULT_DESCRIPTION
        defaultFeaturesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the featuresList where description equals to UPDATED_DESCRIPTION
        defaultFeaturesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFeaturesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where description not equals to DEFAULT_DESCRIPTION
        defaultFeaturesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the featuresList where description not equals to UPDATED_DESCRIPTION
        defaultFeaturesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFeaturesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFeaturesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the featuresList where description equals to UPDATED_DESCRIPTION
        defaultFeaturesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFeaturesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where description is not null
        defaultFeaturesShouldBeFound("description.specified=true");

        // Get all the featuresList where description is null
        defaultFeaturesShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where description contains DEFAULT_DESCRIPTION
        defaultFeaturesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the featuresList where description contains UPDATED_DESCRIPTION
        defaultFeaturesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFeaturesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where description does not contain DEFAULT_DESCRIPTION
        defaultFeaturesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the featuresList where description does not contain UPDATED_DESCRIPTION
        defaultFeaturesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent equals to DEFAULT_PARENT
        defaultFeaturesShouldBeFound("parent.equals=" + DEFAULT_PARENT);

        // Get all the featuresList where parent equals to UPDATED_PARENT
        defaultFeaturesShouldNotBeFound("parent.equals=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent not equals to DEFAULT_PARENT
        defaultFeaturesShouldNotBeFound("parent.notEquals=" + DEFAULT_PARENT);

        // Get all the featuresList where parent not equals to UPDATED_PARENT
        defaultFeaturesShouldBeFound("parent.notEquals=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent in DEFAULT_PARENT or UPDATED_PARENT
        defaultFeaturesShouldBeFound("parent.in=" + DEFAULT_PARENT + "," + UPDATED_PARENT);

        // Get all the featuresList where parent equals to UPDATED_PARENT
        defaultFeaturesShouldNotBeFound("parent.in=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent is not null
        defaultFeaturesShouldBeFound("parent.specified=true");

        // Get all the featuresList where parent is null
        defaultFeaturesShouldNotBeFound("parent.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent is greater than or equal to DEFAULT_PARENT
        defaultFeaturesShouldBeFound("parent.greaterThanOrEqual=" + DEFAULT_PARENT);

        // Get all the featuresList where parent is greater than or equal to UPDATED_PARENT
        defaultFeaturesShouldNotBeFound("parent.greaterThanOrEqual=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent is less than or equal to DEFAULT_PARENT
        defaultFeaturesShouldBeFound("parent.lessThanOrEqual=" + DEFAULT_PARENT);

        // Get all the featuresList where parent is less than or equal to SMALLER_PARENT
        defaultFeaturesShouldNotBeFound("parent.lessThanOrEqual=" + SMALLER_PARENT);
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsLessThanSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent is less than DEFAULT_PARENT
        defaultFeaturesShouldNotBeFound("parent.lessThan=" + DEFAULT_PARENT);

        // Get all the featuresList where parent is less than UPDATED_PARENT
        defaultFeaturesShouldBeFound("parent.lessThan=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllFeaturesByParentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where parent is greater than DEFAULT_PARENT
        defaultFeaturesShouldNotBeFound("parent.greaterThan=" + DEFAULT_PARENT);

        // Get all the featuresList where parent is greater than SMALLER_PARENT
        defaultFeaturesShouldBeFound("parent.greaterThan=" + SMALLER_PARENT);
    }

    @Test
    @Transactional
    void getAllFeaturesByTaxonomyIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where taxonomy equals to DEFAULT_TAXONOMY
        defaultFeaturesShouldBeFound("taxonomy.equals=" + DEFAULT_TAXONOMY);

        // Get all the featuresList where taxonomy equals to UPDATED_TAXONOMY
        defaultFeaturesShouldNotBeFound("taxonomy.equals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllFeaturesByTaxonomyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where taxonomy not equals to DEFAULT_TAXONOMY
        defaultFeaturesShouldNotBeFound("taxonomy.notEquals=" + DEFAULT_TAXONOMY);

        // Get all the featuresList where taxonomy not equals to UPDATED_TAXONOMY
        defaultFeaturesShouldBeFound("taxonomy.notEquals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllFeaturesByTaxonomyIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where taxonomy in DEFAULT_TAXONOMY or UPDATED_TAXONOMY
        defaultFeaturesShouldBeFound("taxonomy.in=" + DEFAULT_TAXONOMY + "," + UPDATED_TAXONOMY);

        // Get all the featuresList where taxonomy equals to UPDATED_TAXONOMY
        defaultFeaturesShouldNotBeFound("taxonomy.in=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllFeaturesByTaxonomyIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where taxonomy is not null
        defaultFeaturesShouldBeFound("taxonomy.specified=true");

        // Get all the featuresList where taxonomy is null
        defaultFeaturesShouldNotBeFound("taxonomy.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByTaxonomyContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where taxonomy contains DEFAULT_TAXONOMY
        defaultFeaturesShouldBeFound("taxonomy.contains=" + DEFAULT_TAXONOMY);

        // Get all the featuresList where taxonomy contains UPDATED_TAXONOMY
        defaultFeaturesShouldNotBeFound("taxonomy.contains=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllFeaturesByTaxonomyNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where taxonomy does not contain DEFAULT_TAXONOMY
        defaultFeaturesShouldNotBeFound("taxonomy.doesNotContain=" + DEFAULT_TAXONOMY);

        // Get all the featuresList where taxonomy does not contain UPDATED_TAXONOMY
        defaultFeaturesShouldBeFound("taxonomy.doesNotContain=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdBy equals to DEFAULT_CREATED_BY
        defaultFeaturesShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the featuresList where createdBy equals to UPDATED_CREATED_BY
        defaultFeaturesShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdBy not equals to DEFAULT_CREATED_BY
        defaultFeaturesShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the featuresList where createdBy not equals to UPDATED_CREATED_BY
        defaultFeaturesShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultFeaturesShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the featuresList where createdBy equals to UPDATED_CREATED_BY
        defaultFeaturesShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdBy is not null
        defaultFeaturesShouldBeFound("createdBy.specified=true");

        // Get all the featuresList where createdBy is null
        defaultFeaturesShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdBy contains DEFAULT_CREATED_BY
        defaultFeaturesShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the featuresList where createdBy contains UPDATED_CREATED_BY
        defaultFeaturesShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdBy does not contain DEFAULT_CREATED_BY
        defaultFeaturesShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the featuresList where createdBy does not contain UPDATED_CREATED_BY
        defaultFeaturesShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdDate equals to DEFAULT_CREATED_DATE
        defaultFeaturesShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the featuresList where createdDate equals to UPDATED_CREATED_DATE
        defaultFeaturesShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultFeaturesShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the featuresList where createdDate not equals to UPDATED_CREATED_DATE
        defaultFeaturesShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultFeaturesShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the featuresList where createdDate equals to UPDATED_CREATED_DATE
        defaultFeaturesShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFeaturesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where createdDate is not null
        defaultFeaturesShouldBeFound("createdDate.specified=true");

        // Get all the featuresList where createdDate is null
        defaultFeaturesShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultFeaturesShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the featuresList where updatedBy equals to UPDATED_UPDATED_BY
        defaultFeaturesShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultFeaturesShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the featuresList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultFeaturesShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultFeaturesShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the featuresList where updatedBy equals to UPDATED_UPDATED_BY
        defaultFeaturesShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updatedBy is not null
        defaultFeaturesShouldBeFound("updatedBy.specified=true");

        // Get all the featuresList where updatedBy is null
        defaultFeaturesShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultFeaturesShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the featuresList where updateDate equals to UPDATED_UPDATE_DATE
        defaultFeaturesShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultFeaturesShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the featuresList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultFeaturesShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultFeaturesShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the featuresList where updateDate equals to UPDATED_UPDATE_DATE
        defaultFeaturesShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFeaturesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList where updateDate is not null
        defaultFeaturesShouldBeFound("updateDate.specified=true");

        // Get all the featuresList where updateDate is null
        defaultFeaturesShouldNotBeFound("updateDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeaturesShouldBeFound(String filter) throws Exception {
        restFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(features.getId().intValue())))
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
        restFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeaturesShouldNotBeFound(String filter) throws Exception {
        restFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeaturesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeatures() throws Exception {
        // Get the features
        restFeaturesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();

        // Update the features
        Features updatedFeatures = featuresRepository.findById(features.getId()).get();
        // Disconnect from session so that the updates on updatedFeatures are not directly saved in db
        em.detach(updatedFeatures);
        updatedFeatures
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

        restFeaturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeatures.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeatures))
            )
            .andExpect(status().isOk());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFeatures.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testFeatures.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testFeatures.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testFeatures.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testFeatures.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testFeatures.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeatures.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testFeatures.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testFeatures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFeatures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFeatures.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFeatures.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();
        features.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, features.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(features))
            )
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();
        features.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(features))
            )
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();
        features.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(features)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeaturesWithPatch() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();

        // Update the features using partial update
        Features partialUpdatedFeatures = new Features();
        partialUpdatedFeatures.setId(features.getId());

        partialUpdatedFeatures
            .title(UPDATED_TITLE)
            .count(UPDATED_COUNT)
            .thumbnail(UPDATED_THUMBNAIL)
            .color(UPDATED_COLOR)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeatures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeatures))
            )
            .andExpect(status().isOk());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFeatures.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testFeatures.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testFeatures.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testFeatures.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testFeatures.getImgIcon()).isEqualTo(DEFAULT_IMG_ICON);
        assertThat(testFeatures.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeatures.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testFeatures.getTaxonomy()).isEqualTo(DEFAULT_TAXONOMY);
        assertThat(testFeatures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFeatures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFeatures.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFeatures.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFeaturesWithPatch() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();

        // Update the features using partial update
        Features partialUpdatedFeatures = new Features();
        partialUpdatedFeatures.setId(features.getId());

        partialUpdatedFeatures
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

        restFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeatures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeatures))
            )
            .andExpect(status().isOk());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFeatures.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testFeatures.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testFeatures.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testFeatures.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testFeatures.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testFeatures.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeatures.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testFeatures.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testFeatures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFeatures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFeatures.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFeatures.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();
        features.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, features.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(features))
            )
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();
        features.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(features))
            )
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();
        features.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(features)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeDelete = featuresRepository.findAll().size();

        // Delete the features
        restFeaturesMockMvc
            .perform(delete(ENTITY_API_URL_ID, features.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
