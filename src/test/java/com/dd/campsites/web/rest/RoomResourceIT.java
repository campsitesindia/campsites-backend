package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Room;
import com.dd.campsites.domain.RoomType;
import com.dd.campsites.repository.RoomRepository;
import com.dd.campsites.service.criteria.RoomCriteria;
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
 * Integration tests for the {@link RoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROOM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IS_SMOKING = "AAAAAAAAAA";
    private static final String UPDATED_IS_SMOKING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomMockMvc;

    private Room room;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createEntity(EntityManager em) {
        Room room = new Room()
            .name(DEFAULT_NAME)
            .roomNumber(DEFAULT_ROOM_NUMBER)
            .isSmoking(DEFAULT_IS_SMOKING)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return room;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createUpdatedEntity(EntityManager em) {
        Room room = new Room()
            .name(UPDATED_NAME)
            .roomNumber(UPDATED_ROOM_NUMBER)
            .isSmoking(UPDATED_IS_SMOKING)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return room;
    }

    @BeforeEach
    public void initTest() {
        room = createEntity(em);
    }

    @Test
    @Transactional
    void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();
        // Create the Room
        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoom.getRoomNumber()).isEqualTo(DEFAULT_ROOM_NUMBER);
        assertThat(testRoom.getIsSmoking()).isEqualTo(DEFAULT_IS_SMOKING);
        assertThat(testRoom.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRoom.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoom.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRoom.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoom.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createRoomWithExistingId() throws Exception {
        // Create the Room with an existing ID
        room.setId(1L);

        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].roomNumber").value(hasItem(DEFAULT_ROOM_NUMBER)))
            .andExpect(jsonPath("$.[*].isSmoking").value(hasItem(DEFAULT_IS_SMOKING)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.roomNumber").value(DEFAULT_ROOM_NUMBER))
            .andExpect(jsonPath("$.isSmoking").value(DEFAULT_IS_SMOKING))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getRoomsByIdFiltering() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        Long id = room.getId();

        defaultRoomShouldBeFound("id.equals=" + id);
        defaultRoomShouldNotBeFound("id.notEquals=" + id);

        defaultRoomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoomsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where name equals to DEFAULT_NAME
        defaultRoomShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the roomList where name equals to UPDATED_NAME
        defaultRoomShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRoomsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where name not equals to DEFAULT_NAME
        defaultRoomShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the roomList where name not equals to UPDATED_NAME
        defaultRoomShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRoomsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRoomShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the roomList where name equals to UPDATED_NAME
        defaultRoomShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRoomsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where name is not null
        defaultRoomShouldBeFound("name.specified=true");

        // Get all the roomList where name is null
        defaultRoomShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByNameContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where name contains DEFAULT_NAME
        defaultRoomShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the roomList where name contains UPDATED_NAME
        defaultRoomShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRoomsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where name does not contain DEFAULT_NAME
        defaultRoomShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the roomList where name does not contain UPDATED_NAME
        defaultRoomShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where roomNumber equals to DEFAULT_ROOM_NUMBER
        defaultRoomShouldBeFound("roomNumber.equals=" + DEFAULT_ROOM_NUMBER);

        // Get all the roomList where roomNumber equals to UPDATED_ROOM_NUMBER
        defaultRoomShouldNotBeFound("roomNumber.equals=" + UPDATED_ROOM_NUMBER);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where roomNumber not equals to DEFAULT_ROOM_NUMBER
        defaultRoomShouldNotBeFound("roomNumber.notEquals=" + DEFAULT_ROOM_NUMBER);

        // Get all the roomList where roomNumber not equals to UPDATED_ROOM_NUMBER
        defaultRoomShouldBeFound("roomNumber.notEquals=" + UPDATED_ROOM_NUMBER);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNumberIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where roomNumber in DEFAULT_ROOM_NUMBER or UPDATED_ROOM_NUMBER
        defaultRoomShouldBeFound("roomNumber.in=" + DEFAULT_ROOM_NUMBER + "," + UPDATED_ROOM_NUMBER);

        // Get all the roomList where roomNumber equals to UPDATED_ROOM_NUMBER
        defaultRoomShouldNotBeFound("roomNumber.in=" + UPDATED_ROOM_NUMBER);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where roomNumber is not null
        defaultRoomShouldBeFound("roomNumber.specified=true");

        // Get all the roomList where roomNumber is null
        defaultRoomShouldNotBeFound("roomNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNumberContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where roomNumber contains DEFAULT_ROOM_NUMBER
        defaultRoomShouldBeFound("roomNumber.contains=" + DEFAULT_ROOM_NUMBER);

        // Get all the roomList where roomNumber contains UPDATED_ROOM_NUMBER
        defaultRoomShouldNotBeFound("roomNumber.contains=" + UPDATED_ROOM_NUMBER);
    }

    @Test
    @Transactional
    void getAllRoomsByRoomNumberNotContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where roomNumber does not contain DEFAULT_ROOM_NUMBER
        defaultRoomShouldNotBeFound("roomNumber.doesNotContain=" + DEFAULT_ROOM_NUMBER);

        // Get all the roomList where roomNumber does not contain UPDATED_ROOM_NUMBER
        defaultRoomShouldBeFound("roomNumber.doesNotContain=" + UPDATED_ROOM_NUMBER);
    }

    @Test
    @Transactional
    void getAllRoomsByIsSmokingIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where isSmoking equals to DEFAULT_IS_SMOKING
        defaultRoomShouldBeFound("isSmoking.equals=" + DEFAULT_IS_SMOKING);

        // Get all the roomList where isSmoking equals to UPDATED_IS_SMOKING
        defaultRoomShouldNotBeFound("isSmoking.equals=" + UPDATED_IS_SMOKING);
    }

    @Test
    @Transactional
    void getAllRoomsByIsSmokingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where isSmoking not equals to DEFAULT_IS_SMOKING
        defaultRoomShouldNotBeFound("isSmoking.notEquals=" + DEFAULT_IS_SMOKING);

        // Get all the roomList where isSmoking not equals to UPDATED_IS_SMOKING
        defaultRoomShouldBeFound("isSmoking.notEquals=" + UPDATED_IS_SMOKING);
    }

    @Test
    @Transactional
    void getAllRoomsByIsSmokingIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where isSmoking in DEFAULT_IS_SMOKING or UPDATED_IS_SMOKING
        defaultRoomShouldBeFound("isSmoking.in=" + DEFAULT_IS_SMOKING + "," + UPDATED_IS_SMOKING);

        // Get all the roomList where isSmoking equals to UPDATED_IS_SMOKING
        defaultRoomShouldNotBeFound("isSmoking.in=" + UPDATED_IS_SMOKING);
    }

    @Test
    @Transactional
    void getAllRoomsByIsSmokingIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where isSmoking is not null
        defaultRoomShouldBeFound("isSmoking.specified=true");

        // Get all the roomList where isSmoking is null
        defaultRoomShouldNotBeFound("isSmoking.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByIsSmokingContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where isSmoking contains DEFAULT_IS_SMOKING
        defaultRoomShouldBeFound("isSmoking.contains=" + DEFAULT_IS_SMOKING);

        // Get all the roomList where isSmoking contains UPDATED_IS_SMOKING
        defaultRoomShouldNotBeFound("isSmoking.contains=" + UPDATED_IS_SMOKING);
    }

    @Test
    @Transactional
    void getAllRoomsByIsSmokingNotContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where isSmoking does not contain DEFAULT_IS_SMOKING
        defaultRoomShouldNotBeFound("isSmoking.doesNotContain=" + DEFAULT_IS_SMOKING);

        // Get all the roomList where isSmoking does not contain UPDATED_IS_SMOKING
        defaultRoomShouldBeFound("isSmoking.doesNotContain=" + UPDATED_IS_SMOKING);
    }

    @Test
    @Transactional
    void getAllRoomsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where status equals to DEFAULT_STATUS
        defaultRoomShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the roomList where status equals to UPDATED_STATUS
        defaultRoomShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRoomsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where status not equals to DEFAULT_STATUS
        defaultRoomShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the roomList where status not equals to UPDATED_STATUS
        defaultRoomShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRoomsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRoomShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the roomList where status equals to UPDATED_STATUS
        defaultRoomShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRoomsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where status is not null
        defaultRoomShouldBeFound("status.specified=true");

        // Get all the roomList where status is null
        defaultRoomShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByStatusContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where status contains DEFAULT_STATUS
        defaultRoomShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the roomList where status contains UPDATED_STATUS
        defaultRoomShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRoomsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where status does not contain DEFAULT_STATUS
        defaultRoomShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the roomList where status does not contain UPDATED_STATUS
        defaultRoomShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdBy equals to DEFAULT_CREATED_BY
        defaultRoomShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the roomList where createdBy equals to UPDATED_CREATED_BY
        defaultRoomShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdBy not equals to DEFAULT_CREATED_BY
        defaultRoomShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the roomList where createdBy not equals to UPDATED_CREATED_BY
        defaultRoomShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultRoomShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the roomList where createdBy equals to UPDATED_CREATED_BY
        defaultRoomShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdBy is not null
        defaultRoomShouldBeFound("createdBy.specified=true");

        // Get all the roomList where createdBy is null
        defaultRoomShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdBy contains DEFAULT_CREATED_BY
        defaultRoomShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the roomList where createdBy contains UPDATED_CREATED_BY
        defaultRoomShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdBy does not contain DEFAULT_CREATED_BY
        defaultRoomShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the roomList where createdBy does not contain UPDATED_CREATED_BY
        defaultRoomShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdDate equals to DEFAULT_CREATED_DATE
        defaultRoomShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the roomList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoomShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultRoomShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the roomList where createdDate not equals to UPDATED_CREATED_DATE
        defaultRoomShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultRoomShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the roomList where createdDate equals to UPDATED_CREATED_DATE
        defaultRoomShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllRoomsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where createdDate is not null
        defaultRoomShouldBeFound("createdDate.specified=true");

        // Get all the roomList where createdDate is null
        defaultRoomShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultRoomShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the roomList where updatedBy equals to UPDATED_UPDATED_BY
        defaultRoomShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultRoomShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the roomList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultRoomShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultRoomShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the roomList where updatedBy equals to UPDATED_UPDATED_BY
        defaultRoomShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllRoomsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updatedBy is not null
        defaultRoomShouldBeFound("updatedBy.specified=true");

        // Get all the roomList where updatedBy is null
        defaultRoomShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultRoomShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the roomList where updateDate equals to UPDATED_UPDATE_DATE
        defaultRoomShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomsByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultRoomShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the roomList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultRoomShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultRoomShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the roomList where updateDate equals to UPDATED_UPDATE_DATE
        defaultRoomShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllRoomsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where updateDate is not null
        defaultRoomShouldBeFound("updateDate.specified=true");

        // Get all the roomList where updateDate is null
        defaultRoomShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRoomsByRoomTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);
        RoomType roomType = RoomTypeResourceIT.createEntity(em);
        em.persist(roomType);
        em.flush();
        room.setRoomType(roomType);
        roomRepository.saveAndFlush(room);
        Long roomTypeId = roomType.getId();

        // Get all the roomList where roomType equals to roomTypeId
        defaultRoomShouldBeFound("roomTypeId.equals=" + roomTypeId);

        // Get all the roomList where roomType equals to (roomTypeId + 1)
        defaultRoomShouldNotBeFound("roomTypeId.equals=" + (roomTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomShouldBeFound(String filter) throws Exception {
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].roomNumber").value(hasItem(DEFAULT_ROOM_NUMBER)))
            .andExpect(jsonPath("$.[*].isSmoking").value(hasItem(DEFAULT_IS_SMOKING)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomShouldNotBeFound(String filter) throws Exception {
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        Room updatedRoom = roomRepository.findById(room.getId()).get();
        // Disconnect from session so that the updates on updatedRoom are not directly saved in db
        em.detach(updatedRoom);
        updatedRoom
            .name(UPDATED_NAME)
            .roomNumber(UPDATED_ROOM_NUMBER)
            .isSmoking(UPDATED_IS_SMOKING)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoom.getRoomNumber()).isEqualTo(UPDATED_ROOM_NUMBER);
        assertThat(testRoom.getIsSmoking()).isEqualTo(UPDATED_IS_SMOKING);
        assertThat(testRoom.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRoom.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoom.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoom.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoom.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, room.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom
            .roomNumber(UPDATED_ROOM_NUMBER)
            .isSmoking(UPDATED_IS_SMOKING)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoom.getRoomNumber()).isEqualTo(UPDATED_ROOM_NUMBER);
        assertThat(testRoom.getIsSmoking()).isEqualTo(UPDATED_IS_SMOKING);
        assertThat(testRoom.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRoom.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRoom.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoom.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoom.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom
            .name(UPDATED_NAME)
            .roomNumber(UPDATED_ROOM_NUMBER)
            .isSmoking(UPDATED_IS_SMOKING)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoom.getRoomNumber()).isEqualTo(UPDATED_ROOM_NUMBER);
        assertThat(testRoom.getIsSmoking()).isEqualTo(UPDATED_IS_SMOKING);
        assertThat(testRoom.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRoom.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRoom.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRoom.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoom.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, room.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(room))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Delete the room
        restRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, room.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
