package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.domain.Room;
import com.dd.campsites.domain.RoomsForListing;
import com.dd.campsites.repository.RoomsForListingRepository;
import com.dd.campsites.service.criteria.RoomsForListingCriteria;
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
 * Integration tests for the {@link RoomsForListingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomsForListingResourceIT {

    private static final String ENTITY_API_URL = "/api/rooms-for-listings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomsForListingRepository roomsForListingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomsForListingMockMvc;

    private RoomsForListing roomsForListing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomsForListing createEntity(EntityManager em) {
        RoomsForListing roomsForListing = new RoomsForListing();
        return roomsForListing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomsForListing createUpdatedEntity(EntityManager em) {
        RoomsForListing roomsForListing = new RoomsForListing();
        return roomsForListing;
    }

    @BeforeEach
    public void initTest() {
        roomsForListing = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomsForListing() throws Exception {
        int databaseSizeBeforeCreate = roomsForListingRepository.findAll().size();
        // Create the RoomsForListing
        restRoomsForListingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isCreated());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeCreate + 1);
        RoomsForListing testRoomsForListing = roomsForListingList.get(roomsForListingList.size() - 1);
    }

    @Test
    @Transactional
    void createRoomsForListingWithExistingId() throws Exception {
        // Create the RoomsForListing with an existing ID
        roomsForListing.setId(1L);

        int databaseSizeBeforeCreate = roomsForListingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomsForListingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoomsForListings() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);

        // Get all the roomsForListingList
        restRoomsForListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomsForListing.getId().intValue())));
    }

    @Test
    @Transactional
    void getRoomsForListing() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);

        // Get the roomsForListing
        restRoomsForListingMockMvc
            .perform(get(ENTITY_API_URL_ID, roomsForListing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomsForListing.getId().intValue()));
    }

    @Test
    @Transactional
    void getRoomsForListingsByIdFiltering() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);

        Long id = roomsForListing.getId();

        defaultRoomsForListingShouldBeFound("id.equals=" + id);
        defaultRoomsForListingShouldNotBeFound("id.notEquals=" + id);

        defaultRoomsForListingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomsForListingShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomsForListingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomsForListingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoomsForListingsByListingIsEqualToSomething() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);
        Listing listing = ListingResourceIT.createEntity(em);
        em.persist(listing);
        em.flush();
        roomsForListing.setListing(listing);
        roomsForListingRepository.saveAndFlush(roomsForListing);
        Long listingId = listing.getId();

        // Get all the roomsForListingList where listing equals to listingId
        defaultRoomsForListingShouldBeFound("listingId.equals=" + listingId);

        // Get all the roomsForListingList where listing equals to (listingId + 1)
        defaultRoomsForListingShouldNotBeFound("listingId.equals=" + (listingId + 1));
    }

    @Test
    @Transactional
    void getAllRoomsForListingsByRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);
        Room room = RoomResourceIT.createEntity(em);
        em.persist(room);
        em.flush();
        roomsForListing.setRoom(room);
        roomsForListingRepository.saveAndFlush(roomsForListing);
        Long roomId = room.getId();

        // Get all the roomsForListingList where room equals to roomId
        defaultRoomsForListingShouldBeFound("roomId.equals=" + roomId);

        // Get all the roomsForListingList where room equals to (roomId + 1)
        defaultRoomsForListingShouldNotBeFound("roomId.equals=" + (roomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomsForListingShouldBeFound(String filter) throws Exception {
        restRoomsForListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomsForListing.getId().intValue())));

        // Check, that the count call also returns 1
        restRoomsForListingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomsForListingShouldNotBeFound(String filter) throws Exception {
        restRoomsForListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomsForListingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoomsForListing() throws Exception {
        // Get the roomsForListing
        restRoomsForListingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoomsForListing() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);

        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();

        // Update the roomsForListing
        RoomsForListing updatedRoomsForListing = roomsForListingRepository.findById(roomsForListing.getId()).get();
        // Disconnect from session so that the updates on updatedRoomsForListing are not directly saved in db
        em.detach(updatedRoomsForListing);

        restRoomsForListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoomsForListing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoomsForListing))
            )
            .andExpect(status().isOk());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
        RoomsForListing testRoomsForListing = roomsForListingList.get(roomsForListingList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingRoomsForListing() throws Exception {
        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();
        roomsForListing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomsForListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomsForListing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomsForListing() throws Exception {
        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();
        roomsForListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsForListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomsForListing() throws Exception {
        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();
        roomsForListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsForListingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomsForListingWithPatch() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);

        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();

        // Update the roomsForListing using partial update
        RoomsForListing partialUpdatedRoomsForListing = new RoomsForListing();
        partialUpdatedRoomsForListing.setId(roomsForListing.getId());

        restRoomsForListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomsForListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomsForListing))
            )
            .andExpect(status().isOk());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
        RoomsForListing testRoomsForListing = roomsForListingList.get(roomsForListingList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateRoomsForListingWithPatch() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);

        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();

        // Update the roomsForListing using partial update
        RoomsForListing partialUpdatedRoomsForListing = new RoomsForListing();
        partialUpdatedRoomsForListing.setId(roomsForListing.getId());

        restRoomsForListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomsForListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomsForListing))
            )
            .andExpect(status().isOk());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
        RoomsForListing testRoomsForListing = roomsForListingList.get(roomsForListingList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingRoomsForListing() throws Exception {
        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();
        roomsForListing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomsForListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomsForListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomsForListing() throws Exception {
        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();
        roomsForListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsForListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomsForListing() throws Exception {
        int databaseSizeBeforeUpdate = roomsForListingRepository.findAll().size();
        roomsForListing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsForListingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomsForListing))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomsForListing in the database
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomsForListing() throws Exception {
        // Initialize the database
        roomsForListingRepository.saveAndFlush(roomsForListing);

        int databaseSizeBeforeDelete = roomsForListingRepository.findAll().size();

        // Delete the roomsForListing
        restRoomsForListingMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomsForListing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomsForListing> roomsForListingList = roomsForListingRepository.findAll();
        assertThat(roomsForListingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
