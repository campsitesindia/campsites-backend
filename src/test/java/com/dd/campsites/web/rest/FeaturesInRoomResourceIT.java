package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Features;
import com.dd.campsites.domain.FeaturesInRoom;
import com.dd.campsites.domain.Room;
import com.dd.campsites.repository.FeaturesInRoomRepository;
import com.dd.campsites.service.criteria.FeaturesInRoomCriteria;
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
 * Integration tests for the {@link FeaturesInRoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeaturesInRoomResourceIT {

    private static final String ENTITY_API_URL = "/api/features-in-rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeaturesInRoomRepository featuresInRoomRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeaturesInRoomMockMvc;

    private FeaturesInRoom featuresInRoom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeaturesInRoom createEntity(EntityManager em) {
        FeaturesInRoom featuresInRoom = new FeaturesInRoom();
        return featuresInRoom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeaturesInRoom createUpdatedEntity(EntityManager em) {
        FeaturesInRoom featuresInRoom = new FeaturesInRoom();
        return featuresInRoom;
    }

    @BeforeEach
    public void initTest() {
        featuresInRoom = createEntity(em);
    }

    @Test
    @Transactional
    void createFeaturesInRoom() throws Exception {
        int databaseSizeBeforeCreate = featuresInRoomRepository.findAll().size();
        // Create the FeaturesInRoom
        restFeaturesInRoomMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(featuresInRoom))
            )
            .andExpect(status().isCreated());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeCreate + 1);
        FeaturesInRoom testFeaturesInRoom = featuresInRoomList.get(featuresInRoomList.size() - 1);
    }

    @Test
    @Transactional
    void createFeaturesInRoomWithExistingId() throws Exception {
        // Create the FeaturesInRoom with an existing ID
        featuresInRoom.setId(1L);

        int databaseSizeBeforeCreate = featuresInRoomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeaturesInRoomMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(featuresInRoom))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeaturesInRooms() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);

        // Get all the featuresInRoomList
        restFeaturesInRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(featuresInRoom.getId().intValue())));
    }

    @Test
    @Transactional
    void getFeaturesInRoom() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);

        // Get the featuresInRoom
        restFeaturesInRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, featuresInRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(featuresInRoom.getId().intValue()));
    }

    @Test
    @Transactional
    void getFeaturesInRoomsByIdFiltering() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);

        Long id = featuresInRoom.getId();

        defaultFeaturesInRoomShouldBeFound("id.equals=" + id);
        defaultFeaturesInRoomShouldNotBeFound("id.notEquals=" + id);

        defaultFeaturesInRoomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeaturesInRoomShouldNotBeFound("id.greaterThan=" + id);

        defaultFeaturesInRoomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeaturesInRoomShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeaturesInRoomsByRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);
        Room room = RoomResourceIT.createEntity(em);
        em.persist(room);
        em.flush();
        featuresInRoom.setRoom(room);
        featuresInRoomRepository.saveAndFlush(featuresInRoom);
        Long roomId = room.getId();

        // Get all the featuresInRoomList where room equals to roomId
        defaultFeaturesInRoomShouldBeFound("roomId.equals=" + roomId);

        // Get all the featuresInRoomList where room equals to (roomId + 1)
        defaultFeaturesInRoomShouldNotBeFound("roomId.equals=" + (roomId + 1));
    }

    @Test
    @Transactional
    void getAllFeaturesInRoomsByFeatureIsEqualToSomething() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);
        Features feature = FeaturesResourceIT.createEntity(em);
        em.persist(feature);
        em.flush();
        featuresInRoom.setFeature(feature);
        featuresInRoomRepository.saveAndFlush(featuresInRoom);
        Long featureId = feature.getId();

        // Get all the featuresInRoomList where feature equals to featureId
        defaultFeaturesInRoomShouldBeFound("featureId.equals=" + featureId);

        // Get all the featuresInRoomList where feature equals to (featureId + 1)
        defaultFeaturesInRoomShouldNotBeFound("featureId.equals=" + (featureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeaturesInRoomShouldBeFound(String filter) throws Exception {
        restFeaturesInRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(featuresInRoom.getId().intValue())));

        // Check, that the count call also returns 1
        restFeaturesInRoomMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeaturesInRoomShouldNotBeFound(String filter) throws Exception {
        restFeaturesInRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeaturesInRoomMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeaturesInRoom() throws Exception {
        // Get the featuresInRoom
        restFeaturesInRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFeaturesInRoom() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);

        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();

        // Update the featuresInRoom
        FeaturesInRoom updatedFeaturesInRoom = featuresInRoomRepository.findById(featuresInRoom.getId()).get();
        // Disconnect from session so that the updates on updatedFeaturesInRoom are not directly saved in db
        em.detach(updatedFeaturesInRoom);

        restFeaturesInRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeaturesInRoom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeaturesInRoom))
            )
            .andExpect(status().isOk());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
        FeaturesInRoom testFeaturesInRoom = featuresInRoomList.get(featuresInRoomList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingFeaturesInRoom() throws Exception {
        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();
        featuresInRoom.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesInRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, featuresInRoom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(featuresInRoom))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeaturesInRoom() throws Exception {
        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();
        featuresInRoom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesInRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(featuresInRoom))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeaturesInRoom() throws Exception {
        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();
        featuresInRoom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesInRoomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(featuresInRoom)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeaturesInRoomWithPatch() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);

        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();

        // Update the featuresInRoom using partial update
        FeaturesInRoom partialUpdatedFeaturesInRoom = new FeaturesInRoom();
        partialUpdatedFeaturesInRoom.setId(featuresInRoom.getId());

        restFeaturesInRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeaturesInRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeaturesInRoom))
            )
            .andExpect(status().isOk());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
        FeaturesInRoom testFeaturesInRoom = featuresInRoomList.get(featuresInRoomList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateFeaturesInRoomWithPatch() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);

        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();

        // Update the featuresInRoom using partial update
        FeaturesInRoom partialUpdatedFeaturesInRoom = new FeaturesInRoom();
        partialUpdatedFeaturesInRoom.setId(featuresInRoom.getId());

        restFeaturesInRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeaturesInRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeaturesInRoom))
            )
            .andExpect(status().isOk());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
        FeaturesInRoom testFeaturesInRoom = featuresInRoomList.get(featuresInRoomList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingFeaturesInRoom() throws Exception {
        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();
        featuresInRoom.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesInRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, featuresInRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(featuresInRoom))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeaturesInRoom() throws Exception {
        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();
        featuresInRoom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesInRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(featuresInRoom))
            )
            .andExpect(status().isBadRequest());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeaturesInRoom() throws Exception {
        int databaseSizeBeforeUpdate = featuresInRoomRepository.findAll().size();
        featuresInRoom.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeaturesInRoomMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(featuresInRoom))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FeaturesInRoom in the database
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeaturesInRoom() throws Exception {
        // Initialize the database
        featuresInRoomRepository.saveAndFlush(featuresInRoom);

        int databaseSizeBeforeDelete = featuresInRoomRepository.findAll().size();

        // Delete the featuresInRoom
        restFeaturesInRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, featuresInRoom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeaturesInRoom> featuresInRoomList = featuresInRoomRepository.findAll();
        assertThat(featuresInRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
