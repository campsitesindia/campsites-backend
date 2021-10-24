package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Features;
import com.dd.campsites.domain.FeaturesListing;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.repository.FeaturesListingRepository;
import com.dd.campsites.service.criteria.FeaturesListingCriteria;
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
 * Integration tests for the {@link FeaturesListingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeaturesListingResourceIT {

    private static final String ENTITY_API_URL = "/api/features-listings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeaturesListingRepository featuresListingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeaturesListingMockMvc;

    private FeaturesListing featuresListing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeaturesListing createEntity(EntityManager em) {
        FeaturesListing featuresListing = new FeaturesListing();
        return featuresListing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeaturesListing createUpdatedEntity(EntityManager em) {
        FeaturesListing featuresListing = new FeaturesListing();
        return featuresListing;
    }

    @BeforeEach
    public void initTest() {
        featuresListing = createEntity(em);
    }

    @Test
    @Transactional
    void createFeaturesListing() throws Exception {
        int databaseSizeBeforeCreate = featuresListingRepository.findAll().size();
        // Create the FeaturesListing
        restFeaturesListingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isCreated());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeCreate + 1);
        FeaturesListing testFeaturesListing = featuresListingList.get(featuresListingList.size() - 1);
    }

    @Test
    @Transactional
    void createFeaturesListingWithExistingId() throws Exception {
        // Create the FeaturesListing with an existing ID
        featuresListing.setId(1L);

        int databaseSizeBeforeCreate = featuresListingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeaturesListingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeaturesListings() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);

        // Get all the featuresListingList
        restFeaturesListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(featuresListing.getId().intValue())));
    }

    @Test
    @Transactional
    void getFeaturesListing() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);

        // Get the featuresListing
        restFeaturesListingMockMvc
            .perform(get(ENTITY_API_URL_ID, featuresListing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(featuresListing.getId().intValue()));
    }

    @Test
    @Transactional
    void getFeaturesListingsByIdFiltering() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);

        Long id = featuresListing.getId();

        defaultFeaturesListingShouldBeFound("id.equals=" + id);
        defaultFeaturesListingShouldNotBeFound("id.notEquals=" + id);

        defaultFeaturesListingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeaturesListingShouldNotBeFound("id.greaterThan=" + id);

        defaultFeaturesListingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeaturesListingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeaturesListingsByListingIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);
        Listing listing = ListingResourceIT.createEntity(em);
        em.persist(listing);
        em.flush();
        featuresListing.setListing(listing);
        featuresListingRepository.saveAndFlush(featuresListing);
        Long listingId = listing.getId();

        // Get all the featuresListingList where listing equals to listingId
        defaultFeaturesListingShouldBeFound("listingId.equals=" + listingId);

        // Get all the featuresListingList where listing equals to (listingId + 1)
        defaultFeaturesListingShouldNotBeFound("listingId.equals=" + (listingId + 1));
    }

    @Test
    @Transactional
    void getAllFeaturesListingsByFeatureIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);
        Features feature = FeaturesResourceIT.createEntity(em);
        em.persist(feature);
        em.flush();
        featuresListing.setFeature(feature);
        featuresListingRepository.saveAndFlush(featuresListing);
        Long featureId = feature.getId();

        // Get all the featuresListingList where feature equals to featureId
        defaultFeaturesListingShouldBeFound("featureId.equals=" + featureId);

        // Get all the featuresListingList where feature equals to (featureId + 1)
        defaultFeaturesListingShouldNotBeFound("featureId.equals=" + (featureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeaturesListingShouldBeFound(String filter) throws Exception {
        restFeaturesListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(featuresListing.getId().intValue())));

        // Check, that the count call also returns 1
        restFeaturesListingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeaturesListingShouldNotBeFound(String filter) throws Exception {
        restFeaturesListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeaturesListingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeaturesListing() throws Exception {
        // Get the featuresListing
        restFeaturesListingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFeaturesListing() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);

        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();

        // Update the featuresListing
        FeaturesListing updatedFeaturesListing = featuresListingRepository.findById(featuresListing.getId()).get();
        // Disconnect from session so that the updates on updatedFeaturesListing are not directly saved in db
        em.detach(updatedFeaturesListing);

        restFeaturesListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeaturesListing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeaturesListing))
            )
            .andExpect(status().isOk());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
        FeaturesListing testFeaturesListing = featuresListingList.get(featuresListingList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingFeaturesListing() throws Exception {
        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();
        featuresListing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, featuresListing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeaturesListing() throws Exception {
        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();
        featuresListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeaturesListing() throws Exception {
        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();
        featuresListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesListingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeaturesListingWithPatch() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);

        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();

        // Update the featuresListing using partial update
        FeaturesListing partialUpdatedFeaturesListing = new FeaturesListing();
        partialUpdatedFeaturesListing.setId(featuresListing.getId());

        restFeaturesListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeaturesListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeaturesListing))
            )
            .andExpect(status().isOk());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
        FeaturesListing testFeaturesListing = featuresListingList.get(featuresListingList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateFeaturesListingWithPatch() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);

        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();

        // Update the featuresListing using partial update
        FeaturesListing partialUpdatedFeaturesListing = new FeaturesListing();
        partialUpdatedFeaturesListing.setId(featuresListing.getId());

        restFeaturesListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeaturesListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeaturesListing))
            )
            .andExpect(status().isOk());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
        FeaturesListing testFeaturesListing = featuresListingList.get(featuresListingList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingFeaturesListing() throws Exception {
        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();
        featuresListing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, featuresListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeaturesListing() throws Exception {
        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();
        featuresListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeaturesListing() throws Exception {
        int databaseSizeBeforeUpdate = featuresListingRepository.findAll().size();
        featuresListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesListingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(featuresListing))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeaturesListing in the database
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeaturesListing() throws Exception {
        // Initialize the database
        featuresListingRepository.saveAndFlush(featuresListing);

        int databaseSizeBeforeDelete = featuresListingRepository.findAll().size();

        // Delete the featuresListing
        restFeaturesListingMockMvc
            .perform(delete(ENTITY_API_URL_ID, featuresListing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeaturesListing> featuresListingList = featuresListingRepository.findAll();
        assertThat(featuresListingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
