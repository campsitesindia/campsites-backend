package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.Listing;
import com.dd.ttippernest.domain.Videos;
import com.dd.ttippernest.repository.VideosRepository;
import com.dd.ttippernest.service.criteria.VideosCriteria;
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
 * Integration tests for the {@link VideosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideosResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VideosRepository videosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideosMockMvc;

    private Videos videos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Videos createEntity(EntityManager em) {
        Videos videos = new Videos()
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return videos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Videos createUpdatedEntity(EntityManager em) {
        Videos videos = new Videos()
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return videos;
    }

    @BeforeEach
    public void initTest() {
        videos = createEntity(em);
    }

    @Test
    @Transactional
    void createVideos() throws Exception {
        int databaseSizeBeforeCreate = videosRepository.findAll().size();
        // Create the Videos
        restVideosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videos)))
            .andExpect(status().isCreated());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeCreate + 1);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVideos.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testVideos.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testVideos.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVideos.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testVideos.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createVideosWithExistingId() throws Exception {
        // Create the Videos with an existing ID
        videos.setId(1L);

        int databaseSizeBeforeCreate = videosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videos)))
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList
        restVideosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videos.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get the videos
        restVideosMockMvc
            .perform(get(ENTITY_API_URL_ID, videos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videos.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getVideosByIdFiltering() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        Long id = videos.getId();

        defaultVideosShouldBeFound("id.equals=" + id);
        defaultVideosShouldNotBeFound("id.notEquals=" + id);

        defaultVideosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVideosShouldNotBeFound("id.greaterThan=" + id);

        defaultVideosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVideosShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVideosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where name equals to DEFAULT_NAME
        defaultVideosShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the videosList where name equals to UPDATED_NAME
        defaultVideosShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVideosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where name not equals to DEFAULT_NAME
        defaultVideosShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the videosList where name not equals to UPDATED_NAME
        defaultVideosShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVideosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVideosShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the videosList where name equals to UPDATED_NAME
        defaultVideosShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVideosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where name is not null
        defaultVideosShouldBeFound("name.specified=true");

        // Get all the videosList where name is null
        defaultVideosShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllVideosByNameContainsSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where name contains DEFAULT_NAME
        defaultVideosShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the videosList where name contains UPDATED_NAME
        defaultVideosShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVideosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where name does not contain DEFAULT_NAME
        defaultVideosShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the videosList where name does not contain UPDATED_NAME
        defaultVideosShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVideosByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where url equals to DEFAULT_URL
        defaultVideosShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the videosList where url equals to UPDATED_URL
        defaultVideosShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllVideosByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where url not equals to DEFAULT_URL
        defaultVideosShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the videosList where url not equals to UPDATED_URL
        defaultVideosShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllVideosByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where url in DEFAULT_URL or UPDATED_URL
        defaultVideosShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the videosList where url equals to UPDATED_URL
        defaultVideosShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllVideosByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where url is not null
        defaultVideosShouldBeFound("url.specified=true");

        // Get all the videosList where url is null
        defaultVideosShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    void getAllVideosByUrlContainsSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where url contains DEFAULT_URL
        defaultVideosShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the videosList where url contains UPDATED_URL
        defaultVideosShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllVideosByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where url does not contain DEFAULT_URL
        defaultVideosShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the videosList where url does not contain UPDATED_URL
        defaultVideosShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdBy equals to DEFAULT_CREATED_BY
        defaultVideosShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the videosList where createdBy equals to UPDATED_CREATED_BY
        defaultVideosShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdBy not equals to DEFAULT_CREATED_BY
        defaultVideosShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the videosList where createdBy not equals to UPDATED_CREATED_BY
        defaultVideosShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultVideosShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the videosList where createdBy equals to UPDATED_CREATED_BY
        defaultVideosShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdBy is not null
        defaultVideosShouldBeFound("createdBy.specified=true");

        // Get all the videosList where createdBy is null
        defaultVideosShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVideosByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdBy contains DEFAULT_CREATED_BY
        defaultVideosShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the videosList where createdBy contains UPDATED_CREATED_BY
        defaultVideosShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdBy does not contain DEFAULT_CREATED_BY
        defaultVideosShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the videosList where createdBy does not contain UPDATED_CREATED_BY
        defaultVideosShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdDate equals to DEFAULT_CREATED_DATE
        defaultVideosShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the videosList where createdDate equals to UPDATED_CREATED_DATE
        defaultVideosShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultVideosShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the videosList where createdDate not equals to UPDATED_CREATED_DATE
        defaultVideosShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultVideosShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the videosList where createdDate equals to UPDATED_CREATED_DATE
        defaultVideosShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllVideosByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where createdDate is not null
        defaultVideosShouldBeFound("createdDate.specified=true");

        // Get all the videosList where createdDate is null
        defaultVideosShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVideosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultVideosShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the videosList where updatedBy equals to UPDATED_UPDATED_BY
        defaultVideosShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultVideosShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the videosList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultVideosShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultVideosShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the videosList where updatedBy equals to UPDATED_UPDATED_BY
        defaultVideosShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllVideosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updatedBy is not null
        defaultVideosShouldBeFound("updatedBy.specified=true");

        // Get all the videosList where updatedBy is null
        defaultVideosShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVideosByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultVideosShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the videosList where updateDate equals to UPDATED_UPDATE_DATE
        defaultVideosShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllVideosByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultVideosShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the videosList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultVideosShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllVideosByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultVideosShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the videosList where updateDate equals to UPDATED_UPDATE_DATE
        defaultVideosShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllVideosByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList where updateDate is not null
        defaultVideosShouldBeFound("updateDate.specified=true");

        // Get all the videosList where updateDate is null
        defaultVideosShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVideosByListingIsEqualToSomething() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);
        Listing listing = ListingResourceIT.createEntity(em);
        em.persist(listing);
        em.flush();
        videos.setListing(listing);
        videosRepository.saveAndFlush(videos);
        Long listingId = listing.getId();

        // Get all the videosList where listing equals to listingId
        defaultVideosShouldBeFound("listingId.equals=" + listingId);

        // Get all the videosList where listing equals to (listingId + 1)
        defaultVideosShouldNotBeFound("listingId.equals=" + (listingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVideosShouldBeFound(String filter) throws Exception {
        restVideosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videos.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restVideosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVideosShouldNotBeFound(String filter) throws Exception {
        restVideosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVideosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVideos() throws Exception {
        // Get the videos
        restVideosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeUpdate = videosRepository.findAll().size();

        // Update the videos
        Videos updatedVideos = videosRepository.findById(videos.getId()).get();
        // Disconnect from session so that the updates on updatedVideos are not directly saved in db
        em.detach(updatedVideos);
        updatedVideos
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restVideosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVideos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVideos))
            )
            .andExpect(status().isOk());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVideos.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testVideos.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVideos.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVideos.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testVideos.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVideosWithPatch() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeUpdate = videosRepository.findAll().size();

        // Update the videos using partial update
        Videos partialUpdatedVideos = new Videos();
        partialUpdatedVideos.setId(videos.getId());

        partialUpdatedVideos
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideos))
            )
            .andExpect(status().isOk());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVideos.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testVideos.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVideos.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVideos.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testVideos.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateVideosWithPatch() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeUpdate = videosRepository.findAll().size();

        // Update the videos using partial update
        Videos partialUpdatedVideos = new Videos();
        partialUpdatedVideos.setId(videos.getId());

        partialUpdatedVideos
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideos))
            )
            .andExpect(status().isOk());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVideos.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testVideos.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVideos.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVideos.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testVideos.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, videos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(videos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeDelete = videosRepository.findAll().size();

        // Delete the videos
        restVideosMockMvc
            .perform(delete(ENTITY_API_URL_ID, videos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
