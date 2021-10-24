package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Images;
import com.dd.campsites.domain.Like;
import com.dd.campsites.domain.Post;
import com.dd.campsites.domain.User;
import com.dd.campsites.repository.LikeRepository;
import com.dd.campsites.service.criteria.LikeCriteria;
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
 * Integration tests for the {@link LikeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LikeResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/likes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikeMockMvc;

    private Like like;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createEntity(EntityManager em) {
        Like like = new Like()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return like;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createUpdatedEntity(EntityManager em) {
        Like like = new Like()
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return like;
    }

    @BeforeEach
    public void initTest() {
        like = createEntity(em);
    }

    @Test
    @Transactional
    void createLike() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();
        // Create the Like
        restLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isCreated());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate + 1);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLike.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLike.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLike.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createLikeWithExistingId() throws Exception {
        // Create the Like with an existing ID
        like.setId(1L);

        int databaseSizeBeforeCreate = likeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLikes() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList
        restLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get the like
        restLikeMockMvc
            .perform(get(ENTITY_API_URL_ID, like.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(like.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getLikesByIdFiltering() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        Long id = like.getId();

        defaultLikeShouldBeFound("id.equals=" + id);
        defaultLikeShouldNotBeFound("id.notEquals=" + id);

        defaultLikeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLikeShouldNotBeFound("id.greaterThan=" + id);

        defaultLikeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLikeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdBy equals to DEFAULT_CREATED_BY
        defaultLikeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the likeList where createdBy equals to UPDATED_CREATED_BY
        defaultLikeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdBy not equals to DEFAULT_CREATED_BY
        defaultLikeShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the likeList where createdBy not equals to UPDATED_CREATED_BY
        defaultLikeShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLikeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the likeList where createdBy equals to UPDATED_CREATED_BY
        defaultLikeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdBy is not null
        defaultLikeShouldBeFound("createdBy.specified=true");

        // Get all the likeList where createdBy is null
        defaultLikeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLikesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdBy contains DEFAULT_CREATED_BY
        defaultLikeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the likeList where createdBy contains UPDATED_CREATED_BY
        defaultLikeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultLikeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the likeList where createdBy does not contain UPDATED_CREATED_BY
        defaultLikeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultLikeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the likeList where createdDate equals to UPDATED_CREATED_DATE
        defaultLikeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultLikeShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the likeList where createdDate not equals to UPDATED_CREATED_DATE
        defaultLikeShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultLikeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the likeList where createdDate equals to UPDATED_CREATED_DATE
        defaultLikeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllLikesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where createdDate is not null
        defaultLikeShouldBeFound("createdDate.specified=true");

        // Get all the likeList where createdDate is null
        defaultLikeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLikesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultLikeShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the likeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultLikeShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultLikeShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the likeList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultLikeShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultLikeShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the likeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultLikeShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLikesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updatedBy is not null
        defaultLikeShouldBeFound("updatedBy.specified=true");

        // Get all the likeList where updatedBy is null
        defaultLikeShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLikesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultLikeShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the likeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultLikeShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllLikesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultLikeShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the likeList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultLikeShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllLikesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultLikeShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the likeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultLikeShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllLikesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList where updateDate is not null
        defaultLikeShouldBeFound("updateDate.specified=true");

        // Get all the likeList where updateDate is null
        defaultLikeShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLikesByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        like.setPost(post);
        likeRepository.saveAndFlush(like);
        Long postId = post.getId();

        // Get all the likeList where post equals to postId
        defaultLikeShouldBeFound("postId.equals=" + postId);

        // Get all the likeList where post equals to (postId + 1)
        defaultLikeShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    @Test
    @Transactional
    void getAllLikesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        like.setUser(user);
        likeRepository.saveAndFlush(like);
        Long userId = user.getId();

        // Get all the likeList where user equals to userId
        defaultLikeShouldBeFound("userId.equals=" + userId);

        // Get all the likeList where user equals to (userId + 1)
        defaultLikeShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllLikesByImagesIsEqualToSomething() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);
        Images images = ImagesResourceIT.createEntity(em);
        em.persist(images);
        em.flush();
        like.setImages(images);
        likeRepository.saveAndFlush(like);
        Long imagesId = images.getId();

        // Get all the likeList where images equals to imagesId
        defaultLikeShouldBeFound("imagesId.equals=" + imagesId);

        // Get all the likeList where images equals to (imagesId + 1)
        defaultLikeShouldNotBeFound("imagesId.equals=" + (imagesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLikeShouldBeFound(String filter) throws Exception {
        restLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restLikeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLikeShouldNotBeFound(String filter) throws Exception {
        restLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLikeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLike() throws Exception {
        // Get the like
        restLikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like
        Like updatedLike = likeRepository.findById(like.getId()).get();
        // Disconnect from session so that the updates on updatedLike are not directly saved in db
        em.detach(updatedLike);
        updatedLike
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLike.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLike))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLike.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLike.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLike.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, like.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(like))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(like))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLikeWithPatch() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like using partial update
        Like partialUpdatedLike = new Like();
        partialUpdatedLike.setId(like.getId());

        partialUpdatedLike.createdBy(UPDATED_CREATED_BY).updateDate(UPDATED_UPDATE_DATE);

        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLike))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLike.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLike.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLike.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateLikeWithPatch() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like using partial update
        Like partialUpdatedLike = new Like();
        partialUpdatedLike.setId(like.getId());

        partialUpdatedLike
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLike))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLike.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLike.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLike.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, like.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(like))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(like))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();
        like.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeDelete = likeRepository.findAll().size();

        // Delete the like
        restLikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, like.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
