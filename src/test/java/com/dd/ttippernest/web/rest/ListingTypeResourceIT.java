package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.ListingType;
import com.dd.ttippernest.repository.ListingTypeRepository;
import com.dd.ttippernest.service.criteria.ListingTypeCriteria;
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
 * Integration tests for the {@link ListingTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListingTypeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/listing-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListingTypeRepository listingTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListingTypeMockMvc;

    private ListingType listingType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListingType createEntity(EntityManager em) {
        ListingType listingType = new ListingType()
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
        return listingType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListingType createUpdatedEntity(EntityManager em) {
        ListingType listingType = new ListingType()
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
        return listingType;
    }

    @BeforeEach
    public void initTest() {
        listingType = createEntity(em);
    }

    @Test
    @Transactional
    void createListingType() throws Exception {
        int databaseSizeBeforeCreate = listingTypeRepository.findAll().size();
        // Create the ListingType
        restListingTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listingType)))
            .andExpect(status().isCreated());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ListingType testListingType = listingTypeList.get(listingTypeList.size() - 1);
        assertThat(testListingType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testListingType.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testListingType.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testListingType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testListingType.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testListingType.getImgIcon()).isEqualTo(DEFAULT_IMG_ICON);
        assertThat(testListingType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testListingType.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testListingType.getTaxonomy()).isEqualTo(DEFAULT_TAXONOMY);
        assertThat(testListingType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testListingType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testListingType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testListingType.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createListingTypeWithExistingId() throws Exception {
        // Create the ListingType with an existing ID
        listingType.setId(1L);

        int databaseSizeBeforeCreate = listingTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListingTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listingType)))
            .andExpect(status().isBadRequest());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListingTypes() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList
        restListingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listingType.getId().intValue())))
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
    void getListingType() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get the listingType
        restListingTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, listingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listingType.getId().intValue()))
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
    void getListingTypesByIdFiltering() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        Long id = listingType.getId();

        defaultListingTypeShouldBeFound("id.equals=" + id);
        defaultListingTypeShouldNotBeFound("id.notEquals=" + id);

        defaultListingTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultListingTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultListingTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultListingTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllListingTypesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where title equals to DEFAULT_TITLE
        defaultListingTypeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the listingTypeList where title equals to UPDATED_TITLE
        defaultListingTypeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingTypesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where title not equals to DEFAULT_TITLE
        defaultListingTypeShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the listingTypeList where title not equals to UPDATED_TITLE
        defaultListingTypeShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingTypesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultListingTypeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the listingTypeList where title equals to UPDATED_TITLE
        defaultListingTypeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingTypesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where title is not null
        defaultListingTypeShouldBeFound("title.specified=true");

        // Get all the listingTypeList where title is null
        defaultListingTypeShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByTitleContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where title contains DEFAULT_TITLE
        defaultListingTypeShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the listingTypeList where title contains UPDATED_TITLE
        defaultListingTypeShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingTypesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where title does not contain DEFAULT_TITLE
        defaultListingTypeShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the listingTypeList where title does not contain UPDATED_TITLE
        defaultListingTypeShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count equals to DEFAULT_COUNT
        defaultListingTypeShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the listingTypeList where count equals to UPDATED_COUNT
        defaultListingTypeShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count not equals to DEFAULT_COUNT
        defaultListingTypeShouldNotBeFound("count.notEquals=" + DEFAULT_COUNT);

        // Get all the listingTypeList where count not equals to UPDATED_COUNT
        defaultListingTypeShouldBeFound("count.notEquals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultListingTypeShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the listingTypeList where count equals to UPDATED_COUNT
        defaultListingTypeShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count is not null
        defaultListingTypeShouldBeFound("count.specified=true");

        // Get all the listingTypeList where count is null
        defaultListingTypeShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count is greater than or equal to DEFAULT_COUNT
        defaultListingTypeShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the listingTypeList where count is greater than or equal to UPDATED_COUNT
        defaultListingTypeShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count is less than or equal to DEFAULT_COUNT
        defaultListingTypeShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the listingTypeList where count is less than or equal to SMALLER_COUNT
        defaultListingTypeShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count is less than DEFAULT_COUNT
        defaultListingTypeShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the listingTypeList where count is less than UPDATED_COUNT
        defaultListingTypeShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    void getAllListingTypesByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where count is greater than DEFAULT_COUNT
        defaultListingTypeShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the listingTypeList where count is greater than SMALLER_COUNT
        defaultListingTypeShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    void getAllListingTypesByThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where thumbnail equals to DEFAULT_THUMBNAIL
        defaultListingTypeShouldBeFound("thumbnail.equals=" + DEFAULT_THUMBNAIL);

        // Get all the listingTypeList where thumbnail equals to UPDATED_THUMBNAIL
        defaultListingTypeShouldNotBeFound("thumbnail.equals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingTypesByThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where thumbnail not equals to DEFAULT_THUMBNAIL
        defaultListingTypeShouldNotBeFound("thumbnail.notEquals=" + DEFAULT_THUMBNAIL);

        // Get all the listingTypeList where thumbnail not equals to UPDATED_THUMBNAIL
        defaultListingTypeShouldBeFound("thumbnail.notEquals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingTypesByThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where thumbnail in DEFAULT_THUMBNAIL or UPDATED_THUMBNAIL
        defaultListingTypeShouldBeFound("thumbnail.in=" + DEFAULT_THUMBNAIL + "," + UPDATED_THUMBNAIL);

        // Get all the listingTypeList where thumbnail equals to UPDATED_THUMBNAIL
        defaultListingTypeShouldNotBeFound("thumbnail.in=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingTypesByThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where thumbnail is not null
        defaultListingTypeShouldBeFound("thumbnail.specified=true");

        // Get all the listingTypeList where thumbnail is null
        defaultListingTypeShouldNotBeFound("thumbnail.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByThumbnailContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where thumbnail contains DEFAULT_THUMBNAIL
        defaultListingTypeShouldBeFound("thumbnail.contains=" + DEFAULT_THUMBNAIL);

        // Get all the listingTypeList where thumbnail contains UPDATED_THUMBNAIL
        defaultListingTypeShouldNotBeFound("thumbnail.contains=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingTypesByThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where thumbnail does not contain DEFAULT_THUMBNAIL
        defaultListingTypeShouldNotBeFound("thumbnail.doesNotContain=" + DEFAULT_THUMBNAIL);

        // Get all the listingTypeList where thumbnail does not contain UPDATED_THUMBNAIL
        defaultListingTypeShouldBeFound("thumbnail.doesNotContain=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingTypesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where icon equals to DEFAULT_ICON
        defaultListingTypeShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the listingTypeList where icon equals to UPDATED_ICON
        defaultListingTypeShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where icon not equals to DEFAULT_ICON
        defaultListingTypeShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the listingTypeList where icon not equals to UPDATED_ICON
        defaultListingTypeShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultListingTypeShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the listingTypeList where icon equals to UPDATED_ICON
        defaultListingTypeShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where icon is not null
        defaultListingTypeShouldBeFound("icon.specified=true");

        // Get all the listingTypeList where icon is null
        defaultListingTypeShouldNotBeFound("icon.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByIconContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where icon contains DEFAULT_ICON
        defaultListingTypeShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the listingTypeList where icon contains UPDATED_ICON
        defaultListingTypeShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where icon does not contain DEFAULT_ICON
        defaultListingTypeShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the listingTypeList where icon does not contain UPDATED_ICON
        defaultListingTypeShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where color equals to DEFAULT_COLOR
        defaultListingTypeShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the listingTypeList where color equals to UPDATED_COLOR
        defaultListingTypeShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllListingTypesByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where color not equals to DEFAULT_COLOR
        defaultListingTypeShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the listingTypeList where color not equals to UPDATED_COLOR
        defaultListingTypeShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllListingTypesByColorIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultListingTypeShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the listingTypeList where color equals to UPDATED_COLOR
        defaultListingTypeShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllListingTypesByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where color is not null
        defaultListingTypeShouldBeFound("color.specified=true");

        // Get all the listingTypeList where color is null
        defaultListingTypeShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByColorContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where color contains DEFAULT_COLOR
        defaultListingTypeShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the listingTypeList where color contains UPDATED_COLOR
        defaultListingTypeShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllListingTypesByColorNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where color does not contain DEFAULT_COLOR
        defaultListingTypeShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the listingTypeList where color does not contain UPDATED_COLOR
        defaultListingTypeShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllListingTypesByImgIconIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where imgIcon equals to DEFAULT_IMG_ICON
        defaultListingTypeShouldBeFound("imgIcon.equals=" + DEFAULT_IMG_ICON);

        // Get all the listingTypeList where imgIcon equals to UPDATED_IMG_ICON
        defaultListingTypeShouldNotBeFound("imgIcon.equals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByImgIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where imgIcon not equals to DEFAULT_IMG_ICON
        defaultListingTypeShouldNotBeFound("imgIcon.notEquals=" + DEFAULT_IMG_ICON);

        // Get all the listingTypeList where imgIcon not equals to UPDATED_IMG_ICON
        defaultListingTypeShouldBeFound("imgIcon.notEquals=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByImgIconIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where imgIcon in DEFAULT_IMG_ICON or UPDATED_IMG_ICON
        defaultListingTypeShouldBeFound("imgIcon.in=" + DEFAULT_IMG_ICON + "," + UPDATED_IMG_ICON);

        // Get all the listingTypeList where imgIcon equals to UPDATED_IMG_ICON
        defaultListingTypeShouldNotBeFound("imgIcon.in=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByImgIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where imgIcon is not null
        defaultListingTypeShouldBeFound("imgIcon.specified=true");

        // Get all the listingTypeList where imgIcon is null
        defaultListingTypeShouldNotBeFound("imgIcon.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByImgIconContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where imgIcon contains DEFAULT_IMG_ICON
        defaultListingTypeShouldBeFound("imgIcon.contains=" + DEFAULT_IMG_ICON);

        // Get all the listingTypeList where imgIcon contains UPDATED_IMG_ICON
        defaultListingTypeShouldNotBeFound("imgIcon.contains=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByImgIconNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where imgIcon does not contain DEFAULT_IMG_ICON
        defaultListingTypeShouldNotBeFound("imgIcon.doesNotContain=" + DEFAULT_IMG_ICON);

        // Get all the listingTypeList where imgIcon does not contain UPDATED_IMG_ICON
        defaultListingTypeShouldBeFound("imgIcon.doesNotContain=" + UPDATED_IMG_ICON);
    }

    @Test
    @Transactional
    void getAllListingTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where description equals to DEFAULT_DESCRIPTION
        defaultListingTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the listingTypeList where description equals to UPDATED_DESCRIPTION
        defaultListingTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllListingTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultListingTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the listingTypeList where description not equals to UPDATED_DESCRIPTION
        defaultListingTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllListingTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultListingTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the listingTypeList where description equals to UPDATED_DESCRIPTION
        defaultListingTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllListingTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where description is not null
        defaultListingTypeShouldBeFound("description.specified=true");

        // Get all the listingTypeList where description is null
        defaultListingTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where description contains DEFAULT_DESCRIPTION
        defaultListingTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the listingTypeList where description contains UPDATED_DESCRIPTION
        defaultListingTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllListingTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultListingTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the listingTypeList where description does not contain UPDATED_DESCRIPTION
        defaultListingTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent equals to DEFAULT_PARENT
        defaultListingTypeShouldBeFound("parent.equals=" + DEFAULT_PARENT);

        // Get all the listingTypeList where parent equals to UPDATED_PARENT
        defaultListingTypeShouldNotBeFound("parent.equals=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent not equals to DEFAULT_PARENT
        defaultListingTypeShouldNotBeFound("parent.notEquals=" + DEFAULT_PARENT);

        // Get all the listingTypeList where parent not equals to UPDATED_PARENT
        defaultListingTypeShouldBeFound("parent.notEquals=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent in DEFAULT_PARENT or UPDATED_PARENT
        defaultListingTypeShouldBeFound("parent.in=" + DEFAULT_PARENT + "," + UPDATED_PARENT);

        // Get all the listingTypeList where parent equals to UPDATED_PARENT
        defaultListingTypeShouldNotBeFound("parent.in=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent is not null
        defaultListingTypeShouldBeFound("parent.specified=true");

        // Get all the listingTypeList where parent is null
        defaultListingTypeShouldNotBeFound("parent.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent is greater than or equal to DEFAULT_PARENT
        defaultListingTypeShouldBeFound("parent.greaterThanOrEqual=" + DEFAULT_PARENT);

        // Get all the listingTypeList where parent is greater than or equal to UPDATED_PARENT
        defaultListingTypeShouldNotBeFound("parent.greaterThanOrEqual=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent is less than or equal to DEFAULT_PARENT
        defaultListingTypeShouldBeFound("parent.lessThanOrEqual=" + DEFAULT_PARENT);

        // Get all the listingTypeList where parent is less than or equal to SMALLER_PARENT
        defaultListingTypeShouldNotBeFound("parent.lessThanOrEqual=" + SMALLER_PARENT);
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsLessThanSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent is less than DEFAULT_PARENT
        defaultListingTypeShouldNotBeFound("parent.lessThan=" + DEFAULT_PARENT);

        // Get all the listingTypeList where parent is less than UPDATED_PARENT
        defaultListingTypeShouldBeFound("parent.lessThan=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllListingTypesByParentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where parent is greater than DEFAULT_PARENT
        defaultListingTypeShouldNotBeFound("parent.greaterThan=" + DEFAULT_PARENT);

        // Get all the listingTypeList where parent is greater than SMALLER_PARENT
        defaultListingTypeShouldBeFound("parent.greaterThan=" + SMALLER_PARENT);
    }

    @Test
    @Transactional
    void getAllListingTypesByTaxonomyIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where taxonomy equals to DEFAULT_TAXONOMY
        defaultListingTypeShouldBeFound("taxonomy.equals=" + DEFAULT_TAXONOMY);

        // Get all the listingTypeList where taxonomy equals to UPDATED_TAXONOMY
        defaultListingTypeShouldNotBeFound("taxonomy.equals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllListingTypesByTaxonomyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where taxonomy not equals to DEFAULT_TAXONOMY
        defaultListingTypeShouldNotBeFound("taxonomy.notEquals=" + DEFAULT_TAXONOMY);

        // Get all the listingTypeList where taxonomy not equals to UPDATED_TAXONOMY
        defaultListingTypeShouldBeFound("taxonomy.notEquals=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllListingTypesByTaxonomyIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where taxonomy in DEFAULT_TAXONOMY or UPDATED_TAXONOMY
        defaultListingTypeShouldBeFound("taxonomy.in=" + DEFAULT_TAXONOMY + "," + UPDATED_TAXONOMY);

        // Get all the listingTypeList where taxonomy equals to UPDATED_TAXONOMY
        defaultListingTypeShouldNotBeFound("taxonomy.in=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllListingTypesByTaxonomyIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where taxonomy is not null
        defaultListingTypeShouldBeFound("taxonomy.specified=true");

        // Get all the listingTypeList where taxonomy is null
        defaultListingTypeShouldNotBeFound("taxonomy.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByTaxonomyContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where taxonomy contains DEFAULT_TAXONOMY
        defaultListingTypeShouldBeFound("taxonomy.contains=" + DEFAULT_TAXONOMY);

        // Get all the listingTypeList where taxonomy contains UPDATED_TAXONOMY
        defaultListingTypeShouldNotBeFound("taxonomy.contains=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllListingTypesByTaxonomyNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where taxonomy does not contain DEFAULT_TAXONOMY
        defaultListingTypeShouldNotBeFound("taxonomy.doesNotContain=" + DEFAULT_TAXONOMY);

        // Get all the listingTypeList where taxonomy does not contain UPDATED_TAXONOMY
        defaultListingTypeShouldBeFound("taxonomy.doesNotContain=" + UPDATED_TAXONOMY);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultListingTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the listingTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultListingTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdBy not equals to DEFAULT_CREATED_BY
        defaultListingTypeShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the listingTypeList where createdBy not equals to UPDATED_CREATED_BY
        defaultListingTypeShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultListingTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the listingTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultListingTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdBy is not null
        defaultListingTypeShouldBeFound("createdBy.specified=true");

        // Get all the listingTypeList where createdBy is null
        defaultListingTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdBy contains DEFAULT_CREATED_BY
        defaultListingTypeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the listingTypeList where createdBy contains UPDATED_CREATED_BY
        defaultListingTypeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultListingTypeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the listingTypeList where createdBy does not contain UPDATED_CREATED_BY
        defaultListingTypeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultListingTypeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the listingTypeList where createdDate equals to UPDATED_CREATED_DATE
        defaultListingTypeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultListingTypeShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the listingTypeList where createdDate not equals to UPDATED_CREATED_DATE
        defaultListingTypeShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultListingTypeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the listingTypeList where createdDate equals to UPDATED_CREATED_DATE
        defaultListingTypeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllListingTypesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where createdDate is not null
        defaultListingTypeShouldBeFound("createdDate.specified=true");

        // Get all the listingTypeList where createdDate is null
        defaultListingTypeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultListingTypeShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the listingTypeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultListingTypeShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultListingTypeShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the listingTypeList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultListingTypeShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultListingTypeShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the listingTypeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultListingTypeShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updatedBy is not null
        defaultListingTypeShouldBeFound("updatedBy.specified=true");

        // Get all the listingTypeList where updatedBy is null
        defaultListingTypeShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultListingTypeShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the listingTypeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultListingTypeShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultListingTypeShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the listingTypeList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultListingTypeShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultListingTypeShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the listingTypeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultListingTypeShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllListingTypesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        // Get all the listingTypeList where updateDate is not null
        defaultListingTypeShouldBeFound("updateDate.specified=true");

        // Get all the listingTypeList where updateDate is null
        defaultListingTypeShouldNotBeFound("updateDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultListingTypeShouldBeFound(String filter) throws Exception {
        restListingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listingType.getId().intValue())))
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
        restListingTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultListingTypeShouldNotBeFound(String filter) throws Exception {
        restListingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restListingTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingListingType() throws Exception {
        // Get the listingType
        restListingTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListingType() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();

        // Update the listingType
        ListingType updatedListingType = listingTypeRepository.findById(listingType.getId()).get();
        // Disconnect from session so that the updates on updatedListingType are not directly saved in db
        em.detach(updatedListingType);
        updatedListingType
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

        restListingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListingType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListingType))
            )
            .andExpect(status().isOk());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
        ListingType testListingType = listingTypeList.get(listingTypeList.size() - 1);
        assertThat(testListingType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testListingType.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testListingType.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testListingType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testListingType.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testListingType.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testListingType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testListingType.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testListingType.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testListingType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testListingType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testListingType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testListingType.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingListingType() throws Exception {
        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();
        listingType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listingType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listingType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListingType() throws Exception {
        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();
        listingType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listingType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListingType() throws Exception {
        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();
        listingType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listingType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListingTypeWithPatch() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();

        // Update the listingType using partial update
        ListingType partialUpdatedListingType = new ListingType();
        partialUpdatedListingType.setId(listingType.getId());

        partialUpdatedListingType
            .title(UPDATED_TITLE)
            .thumbnail(UPDATED_THUMBNAIL)
            .color(UPDATED_COLOR)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restListingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListingType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListingType))
            )
            .andExpect(status().isOk());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
        ListingType testListingType = listingTypeList.get(listingTypeList.size() - 1);
        assertThat(testListingType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testListingType.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testListingType.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testListingType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testListingType.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testListingType.getImgIcon()).isEqualTo(DEFAULT_IMG_ICON);
        assertThat(testListingType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testListingType.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testListingType.getTaxonomy()).isEqualTo(DEFAULT_TAXONOMY);
        assertThat(testListingType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testListingType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testListingType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testListingType.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateListingTypeWithPatch() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();

        // Update the listingType using partial update
        ListingType partialUpdatedListingType = new ListingType();
        partialUpdatedListingType.setId(listingType.getId());

        partialUpdatedListingType
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

        restListingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListingType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListingType))
            )
            .andExpect(status().isOk());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
        ListingType testListingType = listingTypeList.get(listingTypeList.size() - 1);
        assertThat(testListingType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testListingType.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testListingType.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testListingType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testListingType.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testListingType.getImgIcon()).isEqualTo(UPDATED_IMG_ICON);
        assertThat(testListingType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testListingType.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testListingType.getTaxonomy()).isEqualTo(UPDATED_TAXONOMY);
        assertThat(testListingType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testListingType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testListingType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testListingType.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingListingType() throws Exception {
        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();
        listingType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listingType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listingType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListingType() throws Exception {
        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();
        listingType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listingType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListingType() throws Exception {
        int databaseSizeBeforeUpdate = listingTypeRepository.findAll().size();
        listingType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(listingType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListingType in the database
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListingType() throws Exception {
        // Initialize the database
        listingTypeRepository.saveAndFlush(listingType);

        int databaseSizeBeforeDelete = listingTypeRepository.findAll().size();

        // Delete the listingType
        restListingTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, listingType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListingType> listingTypeList = listingTypeRepository.findAll();
        assertThat(listingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
