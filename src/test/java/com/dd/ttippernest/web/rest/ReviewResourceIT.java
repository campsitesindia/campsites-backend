package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.Bookings;
import com.dd.ttippernest.domain.Review;
import com.dd.ttippernest.repository.ReviewRepository;
import com.dd.ttippernest.service.criteria.ReviewCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ReviewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReviewResourceIT {

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;
    private static final Integer SMALLER_RATING = 1 - 1;

    private static final byte[] DEFAULT_REVIEWB_BODY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REVIEWB_BODY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REVIEWB_BODY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REVIEWB_BODY_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReviewMockMvc;

    private Review review;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Review createEntity(EntityManager em) {
        Review review = new Review()
            .rating(DEFAULT_RATING)
            .reviewbBody(DEFAULT_REVIEWB_BODY)
            .reviewbBodyContentType(DEFAULT_REVIEWB_BODY_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return review;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Review createUpdatedEntity(EntityManager em) {
        Review review = new Review()
            .rating(UPDATED_RATING)
            .reviewbBody(UPDATED_REVIEWB_BODY)
            .reviewbBodyContentType(UPDATED_REVIEWB_BODY_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return review;
    }

    @BeforeEach
    public void initTest() {
        review = createEntity(em);
    }

    @Test
    @Transactional
    void createReview() throws Exception {
        int databaseSizeBeforeCreate = reviewRepository.findAll().size();
        // Create the Review
        restReviewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(review)))
            .andExpect(status().isCreated());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeCreate + 1);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testReview.getReviewbBody()).isEqualTo(DEFAULT_REVIEWB_BODY);
        assertThat(testReview.getReviewbBodyContentType()).isEqualTo(DEFAULT_REVIEWB_BODY_CONTENT_TYPE);
        assertThat(testReview.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testReview.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReview.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testReview.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createReviewWithExistingId() throws Exception {
        // Create the Review with an existing ID
        review.setId(1L);

        int databaseSizeBeforeCreate = reviewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(review)))
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReviews() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList
        restReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].reviewbBodyContentType").value(hasItem(DEFAULT_REVIEWB_BODY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].reviewbBody").value(hasItem(Base64Utils.encodeToString(DEFAULT_REVIEWB_BODY))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get the review
        restReviewMockMvc
            .perform(get(ENTITY_API_URL_ID, review.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(review.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.reviewbBodyContentType").value(DEFAULT_REVIEWB_BODY_CONTENT_TYPE))
            .andExpect(jsonPath("$.reviewbBody").value(Base64Utils.encodeToString(DEFAULT_REVIEWB_BODY)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getReviewsByIdFiltering() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        Long id = review.getId();

        defaultReviewShouldBeFound("id.equals=" + id);
        defaultReviewShouldNotBeFound("id.notEquals=" + id);

        defaultReviewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReviewShouldNotBeFound("id.greaterThan=" + id);

        defaultReviewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReviewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating equals to DEFAULT_RATING
        defaultReviewShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the reviewList where rating equals to UPDATED_RATING
        defaultReviewShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating not equals to DEFAULT_RATING
        defaultReviewShouldNotBeFound("rating.notEquals=" + DEFAULT_RATING);

        // Get all the reviewList where rating not equals to UPDATED_RATING
        defaultReviewShouldBeFound("rating.notEquals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultReviewShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the reviewList where rating equals to UPDATED_RATING
        defaultReviewShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating is not null
        defaultReviewShouldBeFound("rating.specified=true");

        // Get all the reviewList where rating is null
        defaultReviewShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating is greater than or equal to DEFAULT_RATING
        defaultReviewShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the reviewList where rating is greater than or equal to UPDATED_RATING
        defaultReviewShouldNotBeFound("rating.greaterThanOrEqual=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating is less than or equal to DEFAULT_RATING
        defaultReviewShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the reviewList where rating is less than or equal to SMALLER_RATING
        defaultReviewShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating is less than DEFAULT_RATING
        defaultReviewShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the reviewList where rating is less than UPDATED_RATING
        defaultReviewShouldBeFound("rating.lessThan=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllReviewsByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where rating is greater than DEFAULT_RATING
        defaultReviewShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the reviewList where rating is greater than SMALLER_RATING
        defaultReviewShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdBy equals to DEFAULT_CREATED_BY
        defaultReviewShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the reviewList where createdBy equals to UPDATED_CREATED_BY
        defaultReviewShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdBy not equals to DEFAULT_CREATED_BY
        defaultReviewShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the reviewList where createdBy not equals to UPDATED_CREATED_BY
        defaultReviewShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultReviewShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the reviewList where createdBy equals to UPDATED_CREATED_BY
        defaultReviewShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdBy is not null
        defaultReviewShouldBeFound("createdBy.specified=true");

        // Get all the reviewList where createdBy is null
        defaultReviewShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdBy contains DEFAULT_CREATED_BY
        defaultReviewShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the reviewList where createdBy contains UPDATED_CREATED_BY
        defaultReviewShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdBy does not contain DEFAULT_CREATED_BY
        defaultReviewShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the reviewList where createdBy does not contain UPDATED_CREATED_BY
        defaultReviewShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdDate equals to DEFAULT_CREATED_DATE
        defaultReviewShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the reviewList where createdDate equals to UPDATED_CREATED_DATE
        defaultReviewShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultReviewShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the reviewList where createdDate not equals to UPDATED_CREATED_DATE
        defaultReviewShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultReviewShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the reviewList where createdDate equals to UPDATED_CREATED_DATE
        defaultReviewShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllReviewsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where createdDate is not null
        defaultReviewShouldBeFound("createdDate.specified=true");

        // Get all the reviewList where createdDate is null
        defaultReviewShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllReviewsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultReviewShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the reviewList where updatedBy equals to UPDATED_UPDATED_BY
        defaultReviewShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultReviewShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the reviewList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultReviewShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultReviewShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the reviewList where updatedBy equals to UPDATED_UPDATED_BY
        defaultReviewShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllReviewsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updatedBy is not null
        defaultReviewShouldBeFound("updatedBy.specified=true");

        // Get all the reviewList where updatedBy is null
        defaultReviewShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllReviewsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultReviewShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the reviewList where updateDate equals to UPDATED_UPDATE_DATE
        defaultReviewShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllReviewsByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultReviewShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the reviewList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultReviewShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllReviewsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultReviewShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the reviewList where updateDate equals to UPDATED_UPDATE_DATE
        defaultReviewShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllReviewsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where updateDate is not null
        defaultReviewShouldBeFound("updateDate.specified=true");

        // Get all the reviewList where updateDate is null
        defaultReviewShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllReviewsByBookingIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);
        Bookings booking = BookingsResourceIT.createEntity(em);
        em.persist(booking);
        em.flush();
        review.setBooking(booking);
        reviewRepository.saveAndFlush(review);
        Long bookingId = booking.getId();

        // Get all the reviewList where booking equals to bookingId
        defaultReviewShouldBeFound("bookingId.equals=" + bookingId);

        // Get all the reviewList where booking equals to (bookingId + 1)
        defaultReviewShouldNotBeFound("bookingId.equals=" + (bookingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReviewShouldBeFound(String filter) throws Exception {
        restReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].reviewbBodyContentType").value(hasItem(DEFAULT_REVIEWB_BODY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].reviewbBody").value(hasItem(Base64Utils.encodeToString(DEFAULT_REVIEWB_BODY))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReviewShouldNotBeFound(String filter) throws Exception {
        restReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingReview() throws Exception {
        // Get the review
        restReviewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Update the review
        Review updatedReview = reviewRepository.findById(review.getId()).get();
        // Disconnect from session so that the updates on updatedReview are not directly saved in db
        em.detach(updatedReview);
        updatedReview
            .rating(UPDATED_RATING)
            .reviewbBody(UPDATED_REVIEWB_BODY)
            .reviewbBodyContentType(UPDATED_REVIEWB_BODY_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReview.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReview))
            )
            .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReview.getReviewbBody()).isEqualTo(UPDATED_REVIEWB_BODY);
        assertThat(testReview.getReviewbBodyContentType()).isEqualTo(UPDATED_REVIEWB_BODY_CONTENT_TYPE);
        assertThat(testReview.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReview.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReview.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testReview.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();
        review.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, review.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(review))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();
        review.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(review))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();
        review.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(review)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReviewWithPatch() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Update the review using partial update
        Review partialUpdatedReview = new Review();
        partialUpdatedReview.setId(review.getId());

        partialUpdatedReview.createdBy(UPDATED_CREATED_BY).createdDate(UPDATED_CREATED_DATE).updatedBy(UPDATED_UPDATED_BY);

        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReview))
            )
            .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testReview.getReviewbBody()).isEqualTo(DEFAULT_REVIEWB_BODY);
        assertThat(testReview.getReviewbBodyContentType()).isEqualTo(DEFAULT_REVIEWB_BODY_CONTENT_TYPE);
        assertThat(testReview.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReview.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReview.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testReview.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateReviewWithPatch() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Update the review using partial update
        Review partialUpdatedReview = new Review();
        partialUpdatedReview.setId(review.getId());

        partialUpdatedReview
            .rating(UPDATED_RATING)
            .reviewbBody(UPDATED_REVIEWB_BODY)
            .reviewbBodyContentType(UPDATED_REVIEWB_BODY_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReview))
            )
            .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReview.getReviewbBody()).isEqualTo(UPDATED_REVIEWB_BODY);
        assertThat(testReview.getReviewbBodyContentType()).isEqualTo(UPDATED_REVIEWB_BODY_CONTENT_TYPE);
        assertThat(testReview.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReview.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReview.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testReview.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();
        review.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, review.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(review))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();
        review.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(review))
            )
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();
        review.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(review)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeDelete = reviewRepository.findAll().size();

        // Delete the review
        restReviewMockMvc
            .perform(delete(ENTITY_API_URL_ID, review.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
