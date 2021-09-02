package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.Rating;
import com.dd.ttippernest.repository.RatingRepository;
import com.dd.ttippernest.service.criteria.RatingCriteria;
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
 * Integration tests for the {@link RatingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingMockMvc;

    private Rating rating;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createEntity(EntityManager em) {
        Rating rating = new Rating().name(DEFAULT_NAME);
        return rating;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createUpdatedEntity(EntityManager em) {
        Rating rating = new Rating().name(UPDATED_NAME);
        return rating;
    }

    @BeforeEach
    public void initTest() {
        rating = createEntity(em);
    }

    @Test
    @Transactional
    void createRating() throws Exception {
        int databaseSizeBeforeCreate = ratingRepository.findAll().size();
        // Create the Rating
        restRatingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isCreated());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate + 1);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRatingWithExistingId() throws Exception {
        // Create the Rating with an existing ID
        rating.setId(1L);

        int databaseSizeBeforeCreate = ratingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRatings() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList
        restRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get the rating
        restRatingMockMvc
            .perform(get(ENTITY_API_URL_ID, rating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rating.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getRatingsByIdFiltering() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        Long id = rating.getId();

        defaultRatingShouldBeFound("id.equals=" + id);
        defaultRatingShouldNotBeFound("id.notEquals=" + id);

        defaultRatingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRatingShouldNotBeFound("id.greaterThan=" + id);

        defaultRatingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRatingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRatingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where name equals to DEFAULT_NAME
        defaultRatingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ratingList where name equals to UPDATED_NAME
        defaultRatingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where name not equals to DEFAULT_NAME
        defaultRatingShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ratingList where name not equals to UPDATED_NAME
        defaultRatingShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRatingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ratingList where name equals to UPDATED_NAME
        defaultRatingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where name is not null
        defaultRatingShouldBeFound("name.specified=true");

        // Get all the ratingList where name is null
        defaultRatingShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRatingsByNameContainsSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where name contains DEFAULT_NAME
        defaultRatingShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ratingList where name contains UPDATED_NAME
        defaultRatingShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRatingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where name does not contain DEFAULT_NAME
        defaultRatingShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ratingList where name does not contain UPDATED_NAME
        defaultRatingShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingShouldBeFound(String filter) throws Exception {
        restRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restRatingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingShouldNotBeFound(String filter) throws Exception {
        restRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRating() throws Exception {
        // Get the rating
        restRatingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Update the rating
        Rating updatedRating = ratingRepository.findById(rating.getId()).get();
        // Disconnect from session so that the updates on updatedRating are not directly saved in db
        em.detach(updatedRating);
        updatedRating.name(UPDATED_NAME);

        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRating.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRating))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        rating.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rating.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rating))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        rating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rating))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        rating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingWithPatch() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Update the rating using partial update
        Rating partialUpdatedRating = new Rating();
        partialUpdatedRating.setId(rating.getId());

        partialUpdatedRating.name(UPDATED_NAME);

        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRating.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRating))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRatingWithPatch() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Update the rating using partial update
        Rating partialUpdatedRating = new Rating();
        partialUpdatedRating.setId(rating.getId());

        partialUpdatedRating.name(UPDATED_NAME);

        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRating.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRating))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        rating.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rating.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rating))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        rating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rating))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        rating.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeDelete = ratingRepository.findAll().size();

        // Delete the rating
        restRatingMockMvc
            .perform(delete(ENTITY_API_URL_ID, rating.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
