package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.RoomType;
import com.dd.campsites.repository.RoomTypeRepository;
import com.dd.campsites.service.criteria.RoomTypeCriteria;
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
 * Integration tests for the {@link RoomTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_CAPACITY = "AAAAAAAAAA";
    private static final String UPDATED_MAX_CAPACITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_BEDS = 1;
    private static final Integer UPDATED_NUMBER_OF_BEDS = 2;
    private static final Integer SMALLER_NUMBER_OF_BEDS = 1 - 1;

    private static final Integer DEFAULT_NUMBER_OF_BATHROOMS = 1;
    private static final Integer UPDATED_NUMBER_OF_BATHROOMS = 2;
    private static final Integer SMALLER_NUMBER_OF_BATHROOMS = 1 - 1;

    private static final Double DEFAULT_ROOM_RATE_PER_NIGHT = 1D;
    private static final Double UPDATED_ROOM_RATE_PER_NIGHT = 2D;
    private static final Double SMALLER_ROOM_RATE_PER_NIGHT = 1D - 1D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/room-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomTypeMockMvc;

    private RoomType roomType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomType createEntity(EntityManager em) {
        RoomType roomType = new RoomType()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .maxCapacity(DEFAULT_MAX_CAPACITY)
            .numberOfBeds(DEFAULT_NUMBER_OF_BEDS)
            .numberOfBathrooms(DEFAULT_NUMBER_OF_BATHROOMS)
            .roomRatePerNight(DEFAULT_ROOM_RATE_PER_NIGHT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return roomType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomType createUpdatedEntity(EntityManager em) {
        RoomType roomType = new RoomType()
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .maxCapacity(UPDATED_MAX_CAPACITY)
            .numberOfBeds(UPDATED_NUMBER_OF_BEDS)
            .numberOfBathrooms(UPDATED_NUMBER_OF_BATHROOMS)
            .roomRatePerNight(UPDATED_ROOM_RATE_PER_NIGHT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return roomType;
    }

    @BeforeEach
    public void initTest() {
        roomType = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomType() throws Exception {
        int databaseSizeBeforeCreate = roomTypeRepository.findAll().size();
        // Create the RoomType
        restRoomTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomType)))
            .andExpect(status().isCreated());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RoomType testRoomType = roomTypeList.get(roomTypeList.size() - 1);
        assertThat(testRoomType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRoomType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoomType.getMaxCapacity()).isEqualTo(DEFAULT_MAX_CAPACITY);
        assertThat(testRoomType.getNumberOfBeds()).isEqualTo(DEFAULT_NUMBER_OF_BEDS);
        assertThat(testRoomType.getNumberOfBathrooms()).isEqualTo(DEFAULT_NUMBER_OF_BATHROOMS);
        assertThat(testRoomType.getRoomRatePerNight()).isEqualTo(DEFAULT_ROOM_RATE_PER_NIGHT);
        assertThat(testRoomType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoomType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRoomType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoomType.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createRoomTypeWithExistingId() throws Exception {
        // Create the RoomType with an existing ID
        roomType.setId(1L);

        int databaseSizeBeforeCreate = roomTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomType)))
            .andExpect(status().isBadRequest());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoomTypes() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList
        restRoomTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].maxCapacity").value(hasItem(DEFAULT_MAX_CAPACITY)))
            .andExpect(jsonPath("$.[*].numberOfBeds").value(hasItem(DEFAULT_NUMBER_OF_BEDS)))
            .andExpect(jsonPath("$.[*].numberOfBathrooms").value(hasItem(DEFAULT_NUMBER_OF_BATHROOMS)))
            .andExpect(jsonPath("$.[*].roomRatePerNight").value(hasItem(DEFAULT_ROOM_RATE_PER_NIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getRoomType() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get the roomType
        restRoomTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, roomType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.maxCapacity").value(DEFAULT_MAX_CAPACITY))
            .andExpect(jsonPath("$.numberOfBeds").value(DEFAULT_NUMBER_OF_BEDS))
            .andExpect(jsonPath("$.numberOfBathrooms").value(DEFAULT_NUMBER_OF_BATHROOMS))
            .andExpect(jsonPath("$.roomRatePerNight").value(DEFAULT_ROOM_RATE_PER_NIGHT.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getRoomTypesByIdFiltering() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        Long id = roomType.getId();

        defaultRoomTypeShouldBeFound("id.equals=" + id);
        defaultRoomTypeShouldNotBeFound("id.notEquals=" + id);

        defaultRoomTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoomTypesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where type equals to DEFAULT_TYPE
        defaultRoomTypeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the roomTypeList where type equals to UPDATED_TYPE
        defaultRoomTypeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where type not equals to DEFAULT_TYPE
        defaultRoomTypeShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the roomTypeList where type not equals to UPDATED_TYPE
        defaultRoomTypeShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultRoomTypeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the roomTypeList where type equals to UPDATED_TYPE
        defaultRoomTypeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where type is not null
        defaultRoomTypeShouldBeFound("type.specified=true");

        // Get all the roomTypeList where type is null
        defaultRoomTypeShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByTypeContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where type contains DEFAULT_TYPE
        defaultRoomTypeShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the roomTypeList where type contains UPDATED_TYPE
        defaultRoomTypeShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where type does not contain DEFAULT_TYPE
        defaultRoomTypeShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the roomTypeList where type does not contain UPDATED_TYPE
        defaultRoomTypeShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where description equals to DEFAULT_DESCRIPTION
        defaultRoomTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the roomTypeList where description equals to UPDATED_DESCRIPTION
        defaultRoomTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultRoomTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the roomTypeList where description not equals to UPDATED_DESCRIPTION
        defaultRoomTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRoomTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the roomTypeList where description equals to UPDATED_DESCRIPTION
        defaultRoomTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where description is not null
        defaultRoomTypeShouldBeFound("description.specified=true");

        // Get all the roomTypeList where description is null
        defaultRoomTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where description contains DEFAULT_DESCRIPTION
        defaultRoomTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the roomTypeList where description contains UPDATED_DESCRIPTION
        defaultRoomTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultRoomTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the roomTypeList where description does not contain UPDATED_DESCRIPTION
        defaultRoomTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRoomTypesByMaxCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where maxCapacity equals to DEFAULT_MAX_CAPACITY
        defaultRoomTypeShouldBeFound("maxCapacity.equals=" + DEFAULT_MAX_CAPACITY);

        // Get all the roomTypeList where maxCapacity equals to UPDATED_MAX_CAPACITY
        defaultRoomTypeShouldNotBeFound("maxCapacity.equals=" + UPDATED_MAX_CAPACITY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByMaxCapacityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where maxCapacity not equals to DEFAULT_MAX_CAPACITY
        defaultRoomTypeShouldNotBeFound("maxCapacity.notEquals=" + DEFAULT_MAX_CAPACITY);

        // Get all the roomTypeList where maxCapacity not equals to UPDATED_MAX_CAPACITY
        defaultRoomTypeShouldBeFound("maxCapacity.notEquals=" + UPDATED_MAX_CAPACITY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByMaxCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where maxCapacity in DEFAULT_MAX_CAPACITY or UPDATED_MAX_CAPACITY
        defaultRoomTypeShouldBeFound("maxCapacity.in=" + DEFAULT_MAX_CAPACITY + "," + UPDATED_MAX_CAPACITY);

        // Get all the roomTypeList where maxCapacity equals to UPDATED_MAX_CAPACITY
        defaultRoomTypeShouldNotBeFound("maxCapacity.in=" + UPDATED_MAX_CAPACITY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByMaxCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where maxCapacity is not null
        defaultRoomTypeShouldBeFound("maxCapacity.specified=true");

        // Get all the roomTypeList where maxCapacity is null
        defaultRoomTypeShouldNotBeFound("maxCapacity.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByMaxCapacityContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where maxCapacity contains DEFAULT_MAX_CAPACITY
        defaultRoomTypeShouldBeFound("maxCapacity.contains=" + DEFAULT_MAX_CAPACITY);

        // Get all the roomTypeList where maxCapacity contains UPDATED_MAX_CAPACITY
        defaultRoomTypeShouldNotBeFound("maxCapacity.contains=" + UPDATED_MAX_CAPACITY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByMaxCapacityNotContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where maxCapacity does not contain DEFAULT_MAX_CAPACITY
        defaultRoomTypeShouldNotBeFound("maxCapacity.doesNotContain=" + DEFAULT_MAX_CAPACITY);

        // Get all the roomTypeList where maxCapacity does not contain UPDATED_MAX_CAPACITY
        defaultRoomTypeShouldBeFound("maxCapacity.doesNotContain=" + UPDATED_MAX_CAPACITY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds equals to DEFAULT_NUMBER_OF_BEDS
        defaultRoomTypeShouldBeFound("numberOfBeds.equals=" + DEFAULT_NUMBER_OF_BEDS);

        // Get all the roomTypeList where numberOfBeds equals to UPDATED_NUMBER_OF_BEDS
        defaultRoomTypeShouldNotBeFound("numberOfBeds.equals=" + UPDATED_NUMBER_OF_BEDS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds not equals to DEFAULT_NUMBER_OF_BEDS
        defaultRoomTypeShouldNotBeFound("numberOfBeds.notEquals=" + DEFAULT_NUMBER_OF_BEDS);

        // Get all the roomTypeList where numberOfBeds not equals to UPDATED_NUMBER_OF_BEDS
        defaultRoomTypeShouldBeFound("numberOfBeds.notEquals=" + UPDATED_NUMBER_OF_BEDS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds in DEFAULT_NUMBER_OF_BEDS or UPDATED_NUMBER_OF_BEDS
        defaultRoomTypeShouldBeFound("numberOfBeds.in=" + DEFAULT_NUMBER_OF_BEDS + "," + UPDATED_NUMBER_OF_BEDS);

        // Get all the roomTypeList where numberOfBeds equals to UPDATED_NUMBER_OF_BEDS
        defaultRoomTypeShouldNotBeFound("numberOfBeds.in=" + UPDATED_NUMBER_OF_BEDS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds is not null
        defaultRoomTypeShouldBeFound("numberOfBeds.specified=true");

        // Get all the roomTypeList where numberOfBeds is null
        defaultRoomTypeShouldNotBeFound("numberOfBeds.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds is greater than or equal to DEFAULT_NUMBER_OF_BEDS
        defaultRoomTypeShouldBeFound("numberOfBeds.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_BEDS);

        // Get all the roomTypeList where numberOfBeds is greater than or equal to UPDATED_NUMBER_OF_BEDS
        defaultRoomTypeShouldNotBeFound("numberOfBeds.greaterThanOrEqual=" + UPDATED_NUMBER_OF_BEDS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds is less than or equal to DEFAULT_NUMBER_OF_BEDS
        defaultRoomTypeShouldBeFound("numberOfBeds.lessThanOrEqual=" + DEFAULT_NUMBER_OF_BEDS);

        // Get all the roomTypeList where numberOfBeds is less than or equal to SMALLER_NUMBER_OF_BEDS
        defaultRoomTypeShouldNotBeFound("numberOfBeds.lessThanOrEqual=" + SMALLER_NUMBER_OF_BEDS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsLessThanSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds is less than DEFAULT_NUMBER_OF_BEDS
        defaultRoomTypeShouldNotBeFound("numberOfBeds.lessThan=" + DEFAULT_NUMBER_OF_BEDS);

        // Get all the roomTypeList where numberOfBeds is less than UPDATED_NUMBER_OF_BEDS
        defaultRoomTypeShouldBeFound("numberOfBeds.lessThan=" + UPDATED_NUMBER_OF_BEDS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBedsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBeds is greater than DEFAULT_NUMBER_OF_BEDS
        defaultRoomTypeShouldNotBeFound("numberOfBeds.greaterThan=" + DEFAULT_NUMBER_OF_BEDS);

        // Get all the roomTypeList where numberOfBeds is greater than SMALLER_NUMBER_OF_BEDS
        defaultRoomTypeShouldBeFound("numberOfBeds.greaterThan=" + SMALLER_NUMBER_OF_BEDS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms equals to DEFAULT_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldBeFound("numberOfBathrooms.equals=" + DEFAULT_NUMBER_OF_BATHROOMS);

        // Get all the roomTypeList where numberOfBathrooms equals to UPDATED_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.equals=" + UPDATED_NUMBER_OF_BATHROOMS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms not equals to DEFAULT_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.notEquals=" + DEFAULT_NUMBER_OF_BATHROOMS);

        // Get all the roomTypeList where numberOfBathrooms not equals to UPDATED_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldBeFound("numberOfBathrooms.notEquals=" + UPDATED_NUMBER_OF_BATHROOMS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms in DEFAULT_NUMBER_OF_BATHROOMS or UPDATED_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldBeFound("numberOfBathrooms.in=" + DEFAULT_NUMBER_OF_BATHROOMS + "," + UPDATED_NUMBER_OF_BATHROOMS);

        // Get all the roomTypeList where numberOfBathrooms equals to UPDATED_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.in=" + UPDATED_NUMBER_OF_BATHROOMS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms is not null
        defaultRoomTypeShouldBeFound("numberOfBathrooms.specified=true");

        // Get all the roomTypeList where numberOfBathrooms is null
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms is greater than or equal to DEFAULT_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldBeFound("numberOfBathrooms.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_BATHROOMS);

        // Get all the roomTypeList where numberOfBathrooms is greater than or equal to UPDATED_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.greaterThanOrEqual=" + UPDATED_NUMBER_OF_BATHROOMS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms is less than or equal to DEFAULT_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldBeFound("numberOfBathrooms.lessThanOrEqual=" + DEFAULT_NUMBER_OF_BATHROOMS);

        // Get all the roomTypeList where numberOfBathrooms is less than or equal to SMALLER_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.lessThanOrEqual=" + SMALLER_NUMBER_OF_BATHROOMS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsLessThanSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms is less than DEFAULT_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.lessThan=" + DEFAULT_NUMBER_OF_BATHROOMS);

        // Get all the roomTypeList where numberOfBathrooms is less than UPDATED_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldBeFound("numberOfBathrooms.lessThan=" + UPDATED_NUMBER_OF_BATHROOMS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByNumberOfBathroomsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where numberOfBathrooms is greater than DEFAULT_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldNotBeFound("numberOfBathrooms.greaterThan=" + DEFAULT_NUMBER_OF_BATHROOMS);

        // Get all the roomTypeList where numberOfBathrooms is greater than SMALLER_NUMBER_OF_BATHROOMS
        defaultRoomTypeShouldBeFound("numberOfBathrooms.greaterThan=" + SMALLER_NUMBER_OF_BATHROOMS);
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight equals to DEFAULT_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldBeFound("roomRatePerNight.equals=" + DEFAULT_ROOM_RATE_PER_NIGHT);

        // Get all the roomTypeList where roomRatePerNight equals to UPDATED_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.equals=" + UPDATED_ROOM_RATE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight not equals to DEFAULT_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.notEquals=" + DEFAULT_ROOM_RATE_PER_NIGHT);

        // Get all the roomTypeList where roomRatePerNight not equals to UPDATED_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldBeFound("roomRatePerNight.notEquals=" + UPDATED_ROOM_RATE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight in DEFAULT_ROOM_RATE_PER_NIGHT or UPDATED_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldBeFound("roomRatePerNight.in=" + DEFAULT_ROOM_RATE_PER_NIGHT + "," + UPDATED_ROOM_RATE_PER_NIGHT);

        // Get all the roomTypeList where roomRatePerNight equals to UPDATED_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.in=" + UPDATED_ROOM_RATE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight is not null
        defaultRoomTypeShouldBeFound("roomRatePerNight.specified=true");

        // Get all the roomTypeList where roomRatePerNight is null
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight is greater than or equal to DEFAULT_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldBeFound("roomRatePerNight.greaterThanOrEqual=" + DEFAULT_ROOM_RATE_PER_NIGHT);

        // Get all the roomTypeList where roomRatePerNight is greater than or equal to UPDATED_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.greaterThanOrEqual=" + UPDATED_ROOM_RATE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight is less than or equal to DEFAULT_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldBeFound("roomRatePerNight.lessThanOrEqual=" + DEFAULT_ROOM_RATE_PER_NIGHT);

        // Get all the roomTypeList where roomRatePerNight is less than or equal to SMALLER_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.lessThanOrEqual=" + SMALLER_ROOM_RATE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsLessThanSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight is less than DEFAULT_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.lessThan=" + DEFAULT_ROOM_RATE_PER_NIGHT);

        // Get all the roomTypeList where roomRatePerNight is less than UPDATED_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldBeFound("roomRatePerNight.lessThan=" + UPDATED_ROOM_RATE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllRoomTypesByRoomRatePerNightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where roomRatePerNight is greater than DEFAULT_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldNotBeFound("roomRatePerNight.greaterThan=" + DEFAULT_ROOM_RATE_PER_NIGHT);

        // Get all the roomTypeList where roomRatePerNight is greater than SMALLER_ROOM_RATE_PER_NIGHT
        defaultRoomTypeShouldBeFound("roomRatePerNight.greaterThan=" + SMALLER_ROOM_RATE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultRoomTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the roomTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultRoomTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdBy not equals to DEFAULT_CREATED_BY
        defaultRoomTypeShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the roomTypeList where createdBy not equals to UPDATED_CREATED_BY
        defaultRoomTypeShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultRoomTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the roomTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultRoomTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdBy is not null
        defaultRoomTypeShouldBeFound("createdBy.specified=true");

        // Get all the roomTypeList where createdBy is null
        defaultRoomTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdBy contains DEFAULT_CREATED_BY
        defaultRoomTypeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the roomTypeList where createdBy contains UPDATED_CREATED_BY
        defaultRoomTypeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultRoomTypeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the roomTypeList where createdBy does not contain UPDATED_CREATED_BY
        defaultRoomTypeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultRoomTypeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the roomTypeList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoomTypeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultRoomTypeShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the roomTypeList where createdDate not equals to UPDATED_CREATED_DATE
        defaultRoomTypeShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultRoomTypeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the roomTypeList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoomTypeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where createdDate is not null
        defaultRoomTypeShouldBeFound("createdDate.specified=true");

        // Get all the roomTypeList where createdDate is null
        defaultRoomTypeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultRoomTypeShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the roomTypeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultRoomTypeShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultRoomTypeShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the roomTypeList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultRoomTypeShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultRoomTypeShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the roomTypeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultRoomTypeShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updatedBy is not null
        defaultRoomTypeShouldBeFound("updatedBy.specified=true");

        // Get all the roomTypeList where updatedBy is null
        defaultRoomTypeShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultRoomTypeShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the roomTypeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultRoomTypeShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultRoomTypeShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the roomTypeList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultRoomTypeShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultRoomTypeShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the roomTypeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultRoomTypeShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomTypesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        // Get all the roomTypeList where updateDate is not null
        defaultRoomTypeShouldBeFound("updateDate.specified=true");

        // Get all the roomTypeList where updateDate is null
        defaultRoomTypeShouldNotBeFound("updateDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomTypeShouldBeFound(String filter) throws Exception {
        restRoomTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].maxCapacity").value(hasItem(DEFAULT_MAX_CAPACITY)))
            .andExpect(jsonPath("$.[*].numberOfBeds").value(hasItem(DEFAULT_NUMBER_OF_BEDS)))
            .andExpect(jsonPath("$.[*].numberOfBathrooms").value(hasItem(DEFAULT_NUMBER_OF_BATHROOMS)))
            .andExpect(jsonPath("$.[*].roomRatePerNight").value(hasItem(DEFAULT_ROOM_RATE_PER_NIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restRoomTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomTypeShouldNotBeFound(String filter) throws Exception {
        restRoomTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoomType() throws Exception {
        // Get the roomType
        restRoomTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoomType() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();

        // Update the roomType
        RoomType updatedRoomType = roomTypeRepository.findById(roomType.getId()).get();
        // Disconnect from session so that the updates on updatedRoomType are not directly saved in db
        em.detach(updatedRoomType);
        updatedRoomType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .maxCapacity(UPDATED_MAX_CAPACITY)
            .numberOfBeds(UPDATED_NUMBER_OF_BEDS)
            .numberOfBathrooms(UPDATED_NUMBER_OF_BATHROOMS)
            .roomRatePerNight(UPDATED_ROOM_RATE_PER_NIGHT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restRoomTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoomType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoomType))
            )
            .andExpect(status().isOk());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
        RoomType testRoomType = roomTypeList.get(roomTypeList.size() - 1);
        assertThat(testRoomType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoomType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomType.getMaxCapacity()).isEqualTo(UPDATED_MAX_CAPACITY);
        assertThat(testRoomType.getNumberOfBeds()).isEqualTo(UPDATED_NUMBER_OF_BEDS);
        assertThat(testRoomType.getNumberOfBathrooms()).isEqualTo(UPDATED_NUMBER_OF_BATHROOMS);
        assertThat(testRoomType.getRoomRatePerNight()).isEqualTo(UPDATED_ROOM_RATE_PER_NIGHT);
        assertThat(testRoomType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoomType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoomType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoomType.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRoomType() throws Exception {
        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();
        roomType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomType() throws Exception {
        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();
        roomType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomType() throws Exception {
        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();
        roomType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomTypeWithPatch() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();

        // Update the roomType using partial update
        RoomType partialUpdatedRoomType = new RoomType();
        partialUpdatedRoomType.setId(roomType.getId());

        partialUpdatedRoomType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .roomRatePerNight(UPDATED_ROOM_RATE_PER_NIGHT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restRoomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomType))
            )
            .andExpect(status().isOk());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
        RoomType testRoomType = roomTypeList.get(roomTypeList.size() - 1);
        assertThat(testRoomType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoomType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomType.getMaxCapacity()).isEqualTo(DEFAULT_MAX_CAPACITY);
        assertThat(testRoomType.getNumberOfBeds()).isEqualTo(DEFAULT_NUMBER_OF_BEDS);
        assertThat(testRoomType.getNumberOfBathrooms()).isEqualTo(DEFAULT_NUMBER_OF_BATHROOMS);
        assertThat(testRoomType.getRoomRatePerNight()).isEqualTo(UPDATED_ROOM_RATE_PER_NIGHT);
        assertThat(testRoomType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoomType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoomType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoomType.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRoomTypeWithPatch() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();

        // Update the roomType using partial update
        RoomType partialUpdatedRoomType = new RoomType();
        partialUpdatedRoomType.setId(roomType.getId());

        partialUpdatedRoomType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .maxCapacity(UPDATED_MAX_CAPACITY)
            .numberOfBeds(UPDATED_NUMBER_OF_BEDS)
            .numberOfBathrooms(UPDATED_NUMBER_OF_BATHROOMS)
            .roomRatePerNight(UPDATED_ROOM_RATE_PER_NIGHT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restRoomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomType))
            )
            .andExpect(status().isOk());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
        RoomType testRoomType = roomTypeList.get(roomTypeList.size() - 1);
        assertThat(testRoomType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoomType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomType.getMaxCapacity()).isEqualTo(UPDATED_MAX_CAPACITY);
        assertThat(testRoomType.getNumberOfBeds()).isEqualTo(UPDATED_NUMBER_OF_BEDS);
        assertThat(testRoomType.getNumberOfBathrooms()).isEqualTo(UPDATED_NUMBER_OF_BATHROOMS);
        assertThat(testRoomType.getRoomRatePerNight()).isEqualTo(UPDATED_ROOM_RATE_PER_NIGHT);
        assertThat(testRoomType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoomType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoomType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoomType.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRoomType() throws Exception {
        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();
        roomType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomType() throws Exception {
        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();
        roomType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomType))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomType() throws Exception {
        int databaseSizeBeforeUpdate = roomTypeRepository.findAll().size();
        roomType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roomType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomType in the database
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomType() throws Exception {
        // Initialize the database
        roomTypeRepository.saveAndFlush(roomType);

        int databaseSizeBeforeDelete = roomTypeRepository.findAll().size();

        // Delete the roomType
        restRoomTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        assertThat(roomTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
