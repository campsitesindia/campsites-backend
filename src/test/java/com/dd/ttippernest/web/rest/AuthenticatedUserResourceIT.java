package com.dd.ttippernest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.ttippernest.IntegrationTest;
import com.dd.ttippernest.domain.AuthenticatedUser;
import com.dd.ttippernest.domain.User;
import com.dd.ttippernest.repository.AuthenticatedUserRepository;
import com.dd.ttippernest.service.criteria.AuthenticatedUserCriteria;
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
 * Integration tests for the {@link AuthenticatedUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthenticatedUserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_AUTH_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AUTH_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/authenticated-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthenticatedUserMockMvc;

    private AuthenticatedUser authenticatedUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthenticatedUser createEntity(EntityManager em) {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .authTimestamp(DEFAULT_AUTH_TIMESTAMP);
        return authenticatedUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthenticatedUser createUpdatedEntity(EntityManager em) {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .authTimestamp(UPDATED_AUTH_TIMESTAMP);
        return authenticatedUser;
    }

    @BeforeEach
    public void initTest() {
        authenticatedUser = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthenticatedUser() throws Exception {
        int databaseSizeBeforeCreate = authenticatedUserRepository.findAll().size();
        // Create the AuthenticatedUser
        restAuthenticatedUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isCreated());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeCreate + 1);
        AuthenticatedUser testAuthenticatedUser = authenticatedUserList.get(authenticatedUserList.size() - 1);
        assertThat(testAuthenticatedUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAuthenticatedUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAuthenticatedUser.getAuthTimestamp()).isEqualTo(DEFAULT_AUTH_TIMESTAMP);
    }

    @Test
    @Transactional
    void createAuthenticatedUserWithExistingId() throws Exception {
        // Create the AuthenticatedUser with an existing ID
        authenticatedUser.setId(1L);

        int databaseSizeBeforeCreate = authenticatedUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthenticatedUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsers() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList
        restAuthenticatedUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authenticatedUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].authTimestamp").value(hasItem(DEFAULT_AUTH_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    void getAuthenticatedUser() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get the authenticatedUser
        restAuthenticatedUserMockMvc
            .perform(get(ENTITY_API_URL_ID, authenticatedUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(authenticatedUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.authTimestamp").value(DEFAULT_AUTH_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    void getAuthenticatedUsersByIdFiltering() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        Long id = authenticatedUser.getId();

        defaultAuthenticatedUserShouldBeFound("id.equals=" + id);
        defaultAuthenticatedUserShouldNotBeFound("id.notEquals=" + id);

        defaultAuthenticatedUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuthenticatedUserShouldNotBeFound("id.greaterThan=" + id);

        defaultAuthenticatedUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuthenticatedUserShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where firstName equals to DEFAULT_FIRST_NAME
        defaultAuthenticatedUserShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the authenticatedUserList where firstName equals to UPDATED_FIRST_NAME
        defaultAuthenticatedUserShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where firstName not equals to DEFAULT_FIRST_NAME
        defaultAuthenticatedUserShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the authenticatedUserList where firstName not equals to UPDATED_FIRST_NAME
        defaultAuthenticatedUserShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultAuthenticatedUserShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the authenticatedUserList where firstName equals to UPDATED_FIRST_NAME
        defaultAuthenticatedUserShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where firstName is not null
        defaultAuthenticatedUserShouldBeFound("firstName.specified=true");

        // Get all the authenticatedUserList where firstName is null
        defaultAuthenticatedUserShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where firstName contains DEFAULT_FIRST_NAME
        defaultAuthenticatedUserShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the authenticatedUserList where firstName contains UPDATED_FIRST_NAME
        defaultAuthenticatedUserShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where firstName does not contain DEFAULT_FIRST_NAME
        defaultAuthenticatedUserShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the authenticatedUserList where firstName does not contain UPDATED_FIRST_NAME
        defaultAuthenticatedUserShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where lastName equals to DEFAULT_LAST_NAME
        defaultAuthenticatedUserShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the authenticatedUserList where lastName equals to UPDATED_LAST_NAME
        defaultAuthenticatedUserShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where lastName not equals to DEFAULT_LAST_NAME
        defaultAuthenticatedUserShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the authenticatedUserList where lastName not equals to UPDATED_LAST_NAME
        defaultAuthenticatedUserShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultAuthenticatedUserShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the authenticatedUserList where lastName equals to UPDATED_LAST_NAME
        defaultAuthenticatedUserShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where lastName is not null
        defaultAuthenticatedUserShouldBeFound("lastName.specified=true");

        // Get all the authenticatedUserList where lastName is null
        defaultAuthenticatedUserShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where lastName contains DEFAULT_LAST_NAME
        defaultAuthenticatedUserShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the authenticatedUserList where lastName contains UPDATED_LAST_NAME
        defaultAuthenticatedUserShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where lastName does not contain DEFAULT_LAST_NAME
        defaultAuthenticatedUserShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the authenticatedUserList where lastName does not contain UPDATED_LAST_NAME
        defaultAuthenticatedUserShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByAuthTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where authTimestamp equals to DEFAULT_AUTH_TIMESTAMP
        defaultAuthenticatedUserShouldBeFound("authTimestamp.equals=" + DEFAULT_AUTH_TIMESTAMP);

        // Get all the authenticatedUserList where authTimestamp equals to UPDATED_AUTH_TIMESTAMP
        defaultAuthenticatedUserShouldNotBeFound("authTimestamp.equals=" + UPDATED_AUTH_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByAuthTimestampIsNotEqualToSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where authTimestamp not equals to DEFAULT_AUTH_TIMESTAMP
        defaultAuthenticatedUserShouldNotBeFound("authTimestamp.notEquals=" + DEFAULT_AUTH_TIMESTAMP);

        // Get all the authenticatedUserList where authTimestamp not equals to UPDATED_AUTH_TIMESTAMP
        defaultAuthenticatedUserShouldBeFound("authTimestamp.notEquals=" + UPDATED_AUTH_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByAuthTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where authTimestamp in DEFAULT_AUTH_TIMESTAMP or UPDATED_AUTH_TIMESTAMP
        defaultAuthenticatedUserShouldBeFound("authTimestamp.in=" + DEFAULT_AUTH_TIMESTAMP + "," + UPDATED_AUTH_TIMESTAMP);

        // Get all the authenticatedUserList where authTimestamp equals to UPDATED_AUTH_TIMESTAMP
        defaultAuthenticatedUserShouldNotBeFound("authTimestamp.in=" + UPDATED_AUTH_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByAuthTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        // Get all the authenticatedUserList where authTimestamp is not null
        defaultAuthenticatedUserShouldBeFound("authTimestamp.specified=true");

        // Get all the authenticatedUserList where authTimestamp is null
        defaultAuthenticatedUserShouldNotBeFound("authTimestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthenticatedUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        authenticatedUser.setUser(user);
        authenticatedUserRepository.saveAndFlush(authenticatedUser);
        Long userId = user.getId();

        // Get all the authenticatedUserList where user equals to userId
        defaultAuthenticatedUserShouldBeFound("userId.equals=" + userId);

        // Get all the authenticatedUserList where user equals to (userId + 1)
        defaultAuthenticatedUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuthenticatedUserShouldBeFound(String filter) throws Exception {
        restAuthenticatedUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authenticatedUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].authTimestamp").value(hasItem(DEFAULT_AUTH_TIMESTAMP.toString())));

        // Check, that the count call also returns 1
        restAuthenticatedUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuthenticatedUserShouldNotBeFound(String filter) throws Exception {
        restAuthenticatedUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuthenticatedUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAuthenticatedUser() throws Exception {
        // Get the authenticatedUser
        restAuthenticatedUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuthenticatedUser() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();

        // Update the authenticatedUser
        AuthenticatedUser updatedAuthenticatedUser = authenticatedUserRepository.findById(authenticatedUser.getId()).get();
        // Disconnect from session so that the updates on updatedAuthenticatedUser are not directly saved in db
        em.detach(updatedAuthenticatedUser);
        updatedAuthenticatedUser.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).authTimestamp(UPDATED_AUTH_TIMESTAMP);

        restAuthenticatedUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAuthenticatedUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAuthenticatedUser))
            )
            .andExpect(status().isOk());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
        AuthenticatedUser testAuthenticatedUser = authenticatedUserList.get(authenticatedUserList.size() - 1);
        assertThat(testAuthenticatedUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAuthenticatedUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAuthenticatedUser.getAuthTimestamp()).isEqualTo(UPDATED_AUTH_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingAuthenticatedUser() throws Exception {
        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();
        authenticatedUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthenticatedUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authenticatedUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthenticatedUser() throws Exception {
        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();
        authenticatedUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthenticatedUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthenticatedUser() throws Exception {
        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();
        authenticatedUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthenticatedUserMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthenticatedUserWithPatch() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();

        // Update the authenticatedUser using partial update
        AuthenticatedUser partialUpdatedAuthenticatedUser = new AuthenticatedUser();
        partialUpdatedAuthenticatedUser.setId(authenticatedUser.getId());

        partialUpdatedAuthenticatedUser.firstName(UPDATED_FIRST_NAME);

        restAuthenticatedUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthenticatedUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthenticatedUser))
            )
            .andExpect(status().isOk());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
        AuthenticatedUser testAuthenticatedUser = authenticatedUserList.get(authenticatedUserList.size() - 1);
        assertThat(testAuthenticatedUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAuthenticatedUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAuthenticatedUser.getAuthTimestamp()).isEqualTo(DEFAULT_AUTH_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateAuthenticatedUserWithPatch() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();

        // Update the authenticatedUser using partial update
        AuthenticatedUser partialUpdatedAuthenticatedUser = new AuthenticatedUser();
        partialUpdatedAuthenticatedUser.setId(authenticatedUser.getId());

        partialUpdatedAuthenticatedUser.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).authTimestamp(UPDATED_AUTH_TIMESTAMP);

        restAuthenticatedUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthenticatedUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthenticatedUser))
            )
            .andExpect(status().isOk());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
        AuthenticatedUser testAuthenticatedUser = authenticatedUserList.get(authenticatedUserList.size() - 1);
        assertThat(testAuthenticatedUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAuthenticatedUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAuthenticatedUser.getAuthTimestamp()).isEqualTo(UPDATED_AUTH_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingAuthenticatedUser() throws Exception {
        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();
        authenticatedUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthenticatedUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authenticatedUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthenticatedUser() throws Exception {
        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();
        authenticatedUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthenticatedUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthenticatedUser() throws Exception {
        int databaseSizeBeforeUpdate = authenticatedUserRepository.findAll().size();
        authenticatedUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthenticatedUserMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authenticatedUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthenticatedUser in the database
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthenticatedUser() throws Exception {
        // Initialize the database
        authenticatedUserRepository.saveAndFlush(authenticatedUser);

        int databaseSizeBeforeDelete = authenticatedUserRepository.findAll().size();

        // Delete the authenticatedUser
        restAuthenticatedUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, authenticatedUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuthenticatedUser> authenticatedUserList = authenticatedUserRepository.findAll();
        assertThat(authenticatedUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
