package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.Followers;
import com.dd.ttippernest.domain.User;
import com.dd.ttippernest.repository.FollowersRepository;
import com.dd.ttippernest.service.criteria.FollowersCriteria;
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
 * Integration tests for the {@link FollowersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FollowersResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/followers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FollowersRepository followersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFollowersMockMvc;

    private Followers followers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Followers createEntity(EntityManager em) {
        Followers followers = new Followers()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return followers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Followers createUpdatedEntity(EntityManager em) {
        Followers followers = new Followers()
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return followers;
    }

    @BeforeEach
    public void initTest() {
        followers = createEntity(em);
    }

    @Test
    @Transactional
    void createFollowers() throws Exception {
        int databaseSizeBeforeCreate = followersRepository.findAll().size();
        // Create the Followers
        restFollowersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(followers)))
            .andExpect(status().isCreated());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeCreate + 1);
        Followers testFollowers = followersList.get(followersList.size() - 1);
        assertThat(testFollowers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFollowers.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFollowers.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFollowers.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createFollowersWithExistingId() throws Exception {
        // Create the Followers with an existing ID
        followers.setId(1L);

        int databaseSizeBeforeCreate = followersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(followers)))
            .andExpect(status().isBadRequest());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFollowers() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList
        restFollowersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(followers.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getFollowers() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get the followers
        restFollowersMockMvc
            .perform(get(ENTITY_API_URL_ID, followers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(followers.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getFollowersByIdFiltering() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        Long id = followers.getId();

        defaultFollowersShouldBeFound("id.equals=" + id);
        defaultFollowersShouldNotBeFound("id.notEquals=" + id);

        defaultFollowersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFollowersShouldNotBeFound("id.greaterThan=" + id);

        defaultFollowersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFollowersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdBy equals to DEFAULT_CREATED_BY
        defaultFollowersShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the followersList where createdBy equals to UPDATED_CREATED_BY
        defaultFollowersShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdBy not equals to DEFAULT_CREATED_BY
        defaultFollowersShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the followersList where createdBy not equals to UPDATED_CREATED_BY
        defaultFollowersShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultFollowersShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the followersList where createdBy equals to UPDATED_CREATED_BY
        defaultFollowersShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdBy is not null
        defaultFollowersShouldBeFound("createdBy.specified=true");

        // Get all the followersList where createdBy is null
        defaultFollowersShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdBy contains DEFAULT_CREATED_BY
        defaultFollowersShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the followersList where createdBy contains UPDATED_CREATED_BY
        defaultFollowersShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdBy does not contain DEFAULT_CREATED_BY
        defaultFollowersShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the followersList where createdBy does not contain UPDATED_CREATED_BY
        defaultFollowersShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdDate equals to DEFAULT_CREATED_DATE
        defaultFollowersShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the followersList where createdDate equals to UPDATED_CREATED_DATE
        defaultFollowersShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultFollowersShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the followersList where createdDate not equals to UPDATED_CREATED_DATE
        defaultFollowersShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultFollowersShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the followersList where createdDate equals to UPDATED_CREATED_DATE
        defaultFollowersShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFollowersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where createdDate is not null
        defaultFollowersShouldBeFound("createdDate.specified=true");

        // Get all the followersList where createdDate is null
        defaultFollowersShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFollowersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultFollowersShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the followersList where updatedBy equals to UPDATED_UPDATED_BY
        defaultFollowersShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultFollowersShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the followersList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultFollowersShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultFollowersShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the followersList where updatedBy equals to UPDATED_UPDATED_BY
        defaultFollowersShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFollowersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updatedBy is not null
        defaultFollowersShouldBeFound("updatedBy.specified=true");

        // Get all the followersList where updatedBy is null
        defaultFollowersShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFollowersByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultFollowersShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the followersList where updateDate equals to UPDATED_UPDATE_DATE
        defaultFollowersShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFollowersByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultFollowersShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the followersList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultFollowersShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFollowersByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultFollowersShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the followersList where updateDate equals to UPDATED_UPDATE_DATE
        defaultFollowersShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllFollowersByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        // Get all the followersList where updateDate is not null
        defaultFollowersShouldBeFound("updateDate.specified=true");

        // Get all the followersList where updateDate is null
        defaultFollowersShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFollowersByFollowedByIsEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);
        User followedBy = UserResourceIT.createEntity(em);
        em.persist(followedBy);
        em.flush();
        followers.setFollowedBy(followedBy);
        followersRepository.saveAndFlush(followers);
        Long followedById = followedBy.getId();

        // Get all the followersList where followedBy equals to followedById
        defaultFollowersShouldBeFound("followedById.equals=" + followedById);

        // Get all the followersList where followedBy equals to (followedById + 1)
        defaultFollowersShouldNotBeFound("followedById.equals=" + (followedById + 1));
    }

    @Test
    @Transactional
    void getAllFollowersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        followers.setUser(user);
        followersRepository.saveAndFlush(followers);
        Long userId = user.getId();

        // Get all the followersList where user equals to userId
        defaultFollowersShouldBeFound("userId.equals=" + userId);

        // Get all the followersList where user equals to (userId + 1)
        defaultFollowersShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFollowersShouldBeFound(String filter) throws Exception {
        restFollowersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(followers.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restFollowersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFollowersShouldNotBeFound(String filter) throws Exception {
        restFollowersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFollowersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFollowers() throws Exception {
        // Get the followers
        restFollowersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFollowers() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        int databaseSizeBeforeUpdate = followersRepository.findAll().size();

        // Update the followers
        Followers updatedFollowers = followersRepository.findById(followers.getId()).get();
        // Disconnect from session so that the updates on updatedFollowers are not directly saved in db
        em.detach(updatedFollowers);
        updatedFollowers
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restFollowersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFollowers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFollowers))
            )
            .andExpect(status().isOk());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
        Followers testFollowers = followersList.get(followersList.size() - 1);
        assertThat(testFollowers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFollowers.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFollowers.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFollowers.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFollowers() throws Exception {
        int databaseSizeBeforeUpdate = followersRepository.findAll().size();
        followers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, followers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(followers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFollowers() throws Exception {
        int databaseSizeBeforeUpdate = followersRepository.findAll().size();
        followers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(followers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFollowers() throws Exception {
        int databaseSizeBeforeUpdate = followersRepository.findAll().size();
        followers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(followers)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFollowersWithPatch() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        int databaseSizeBeforeUpdate = followersRepository.findAll().size();

        // Update the followers using partial update
        Followers partialUpdatedFollowers = new Followers();
        partialUpdatedFollowers.setId(followers.getId());

        partialUpdatedFollowers.updatedBy(UPDATED_UPDATED_BY).updateDate(UPDATED_UPDATE_DATE);

        restFollowersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFollowers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFollowers))
            )
            .andExpect(status().isOk());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
        Followers testFollowers = followersList.get(followersList.size() - 1);
        assertThat(testFollowers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFollowers.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFollowers.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFollowers.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFollowersWithPatch() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        int databaseSizeBeforeUpdate = followersRepository.findAll().size();

        // Update the followers using partial update
        Followers partialUpdatedFollowers = new Followers();
        partialUpdatedFollowers.setId(followers.getId());

        partialUpdatedFollowers
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restFollowersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFollowers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFollowers))
            )
            .andExpect(status().isOk());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
        Followers testFollowers = followersList.get(followersList.size() - 1);
        assertThat(testFollowers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFollowers.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFollowers.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFollowers.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFollowers() throws Exception {
        int databaseSizeBeforeUpdate = followersRepository.findAll().size();
        followers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, followers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(followers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFollowers() throws Exception {
        int databaseSizeBeforeUpdate = followersRepository.findAll().size();
        followers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(followers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFollowers() throws Exception {
        int databaseSizeBeforeUpdate = followersRepository.findAll().size();
        followers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFollowersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(followers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Followers in the database
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFollowers() throws Exception {
        // Initialize the database
        followersRepository.saveAndFlush(followers);

        int databaseSizeBeforeDelete = followersRepository.findAll().size();

        // Delete the followers
        restFollowersMockMvc
            .perform(delete(ENTITY_API_URL_ID, followers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Followers> followersList = followersRepository.findAll();
        assertThat(followersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
