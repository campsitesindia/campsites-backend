package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Bookings;
import com.dd.campsites.domain.Room;
import com.dd.campsites.domain.RoomsInBooking;
import com.dd.campsites.repository.RoomsInBookingRepository;
import com.dd.campsites.service.criteria.RoomsInBookingCriteria;
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
 * Integration tests for the {@link RoomsInBookingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomsInBookingResourceIT {

    private static final String ENTITY_API_URL = "/api/rooms-in-bookings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomsInBookingRepository roomsInBookingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomsInBookingMockMvc;

    private RoomsInBooking roomsInBooking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomsInBooking createEntity(EntityManager em) {
        RoomsInBooking roomsInBooking = new RoomsInBooking();
        return roomsInBooking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomsInBooking createUpdatedEntity(EntityManager em) {
        RoomsInBooking roomsInBooking = new RoomsInBooking();
        return roomsInBooking;
    }

    @BeforeEach
    public void initTest() {
        roomsInBooking = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomsInBooking() throws Exception {
        int databaseSizeBeforeCreate = roomsInBookingRepository.findAll().size();
        // Create the RoomsInBooking
        restRoomsInBookingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsInBooking))
            )
            .andExpect(status().isCreated());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeCreate + 1);
        RoomsInBooking testRoomsInBooking = roomsInBookingList.get(roomsInBookingList.size() - 1);
    }

    @Test
    @Transactional
    void createRoomsInBookingWithExistingId() throws Exception {
        // Create the RoomsInBooking with an existing ID
        roomsInBooking.setId(1L);

        int databaseSizeBeforeCreate = roomsInBookingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomsInBookingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsInBooking))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoomsInBookings() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);

        // Get all the roomsInBookingList
        restRoomsInBookingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomsInBooking.getId().intValue())));
    }

    @Test
    @Transactional
    void getRoomsInBooking() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);

        // Get the roomsInBooking
        restRoomsInBookingMockMvc
            .perform(get(ENTITY_API_URL_ID, roomsInBooking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomsInBooking.getId().intValue()));
    }

    @Test
    @Transactional
    void getRoomsInBookingsByIdFiltering() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);

        Long id = roomsInBooking.getId();

        defaultRoomsInBookingShouldBeFound("id.equals=" + id);
        defaultRoomsInBookingShouldNotBeFound("id.notEquals=" + id);

        defaultRoomsInBookingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomsInBookingShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomsInBookingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomsInBookingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoomsInBookingsByBookingsIsEqualToSomething() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);
        Bookings bookings = BookingsResourceIT.createEntity(em);
        em.persist(bookings);
        em.flush();
        roomsInBooking.setBookings(bookings);
        roomsInBookingRepository.saveAndFlush(roomsInBooking);
        Long bookingsId = bookings.getId();

        // Get all the roomsInBookingList where bookings equals to bookingsId
        defaultRoomsInBookingShouldBeFound("bookingsId.equals=" + bookingsId);

        // Get all the roomsInBookingList where bookings equals to (bookingsId + 1)
        defaultRoomsInBookingShouldNotBeFound("bookingsId.equals=" + (bookingsId + 1));
    }

    @Test
    @Transactional
    void getAllRoomsInBookingsByRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);
        Room room = RoomResourceIT.createEntity(em);
        em.persist(room);
        em.flush();
        roomsInBooking.setRoom(room);
        roomsInBookingRepository.saveAndFlush(roomsInBooking);
        Long roomId = room.getId();

        // Get all the roomsInBookingList where room equals to roomId
        defaultRoomsInBookingShouldBeFound("roomId.equals=" + roomId);

        // Get all the roomsInBookingList where room equals to (roomId + 1)
        defaultRoomsInBookingShouldNotBeFound("roomId.equals=" + (roomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomsInBookingShouldBeFound(String filter) throws Exception {
        restRoomsInBookingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomsInBooking.getId().intValue())));

        // Check, that the count call also returns 1
        restRoomsInBookingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomsInBookingShouldNotBeFound(String filter) throws Exception {
        restRoomsInBookingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomsInBookingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoomsInBooking() throws Exception {
        // Get the roomsInBooking
        restRoomsInBookingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoomsInBooking() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);

        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();

        // Update the roomsInBooking
        RoomsInBooking updatedRoomsInBooking = roomsInBookingRepository.findById(roomsInBooking.getId()).get();
        // Disconnect from session so that the updates on updatedRoomsInBooking are not directly saved in db
        em.detach(updatedRoomsInBooking);

        restRoomsInBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoomsInBooking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoomsInBooking))
            )
            .andExpect(status().isOk());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
        RoomsInBooking testRoomsInBooking = roomsInBookingList.get(roomsInBookingList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingRoomsInBooking() throws Exception {
        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();
        roomsInBooking.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomsInBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomsInBooking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomsInBooking))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomsInBooking() throws Exception {
        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();
        roomsInBooking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsInBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomsInBooking))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomsInBooking() throws Exception {
        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();
        roomsInBooking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsInBookingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomsInBooking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomsInBookingWithPatch() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);

        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();

        // Update the roomsInBooking using partial update
        RoomsInBooking partialUpdatedRoomsInBooking = new RoomsInBooking();
        partialUpdatedRoomsInBooking.setId(roomsInBooking.getId());

        restRoomsInBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomsInBooking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomsInBooking))
            )
            .andExpect(status().isOk());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
        RoomsInBooking testRoomsInBooking = roomsInBookingList.get(roomsInBookingList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateRoomsInBookingWithPatch() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);

        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();

        // Update the roomsInBooking using partial update
        RoomsInBooking partialUpdatedRoomsInBooking = new RoomsInBooking();
        partialUpdatedRoomsInBooking.setId(roomsInBooking.getId());

        restRoomsInBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomsInBooking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomsInBooking))
            )
            .andExpect(status().isOk());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
        RoomsInBooking testRoomsInBooking = roomsInBookingList.get(roomsInBookingList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingRoomsInBooking() throws Exception {
        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();
        roomsInBooking.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomsInBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomsInBooking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomsInBooking))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomsInBooking() throws Exception {
        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();
        roomsInBooking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsInBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomsInBooking))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomsInBooking() throws Exception {
        int databaseSizeBeforeUpdate = roomsInBookingRepository.findAll().size();
        roomsInBooking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomsInBookingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roomsInBooking))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomsInBooking in the database
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomsInBooking() throws Exception {
        // Initialize the database
        roomsInBookingRepository.saveAndFlush(roomsInBooking);

        int databaseSizeBeforeDelete = roomsInBookingRepository.findAll().size();

        // Delete the roomsInBooking
        restRoomsInBookingMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomsInBooking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomsInBooking> roomsInBookingList = roomsInBookingRepository.findAll();
        assertThat(roomsInBookingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
