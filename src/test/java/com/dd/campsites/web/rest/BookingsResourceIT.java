package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Bookings;
import com.dd.campsites.domain.Invoice;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.domain.User;
import com.dd.campsites.repository.BookingsRepository;
import com.dd.campsites.service.criteria.BookingsCriteria;
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
 * Integration tests for the {@link BookingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookingsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CHECK_IN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_IN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CHECK_OUT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_OUT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRICE_PER_NIGHT = 1D;
    private static final Double UPDATED_PRICE_PER_NIGHT = 2D;
    private static final Double SMALLER_PRICE_PER_NIGHT = 1D - 1D;

    private static final Double DEFAULT_CHILD_PRICE_PER_NIGHT = 1D;
    private static final Double UPDATED_CHILD_PRICE_PER_NIGHT = 2D;
    private static final Double SMALLER_CHILD_PRICE_PER_NIGHT = 1D - 1D;

    private static final Integer DEFAULT_NUM_OF_NIGHTS = 1;
    private static final Integer UPDATED_NUM_OF_NIGHTS = 2;
    private static final Integer SMALLER_NUM_OF_NIGHTS = 1 - 1;

    private static final String DEFAULT_RAZORPAY_PAYMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RAZORPAY_PAYMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RAZORPAY_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_RAZORPAY_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RAZORPAY_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_RAZORPAY_SIGNATURE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;
    private static final Double SMALLER_DISCOUNT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_AMOUNT = 1D - 1D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bookings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingsMockMvc;

    private Bookings bookings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookings createEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .name(DEFAULT_NAME)
            .checkInDate(DEFAULT_CHECK_IN_DATE)
            .checkOutDate(DEFAULT_CHECK_OUT_DATE)
            .pricePerNight(DEFAULT_PRICE_PER_NIGHT)
            .childPricePerNight(DEFAULT_CHILD_PRICE_PER_NIGHT)
            .numOfNights(DEFAULT_NUM_OF_NIGHTS)
            .razorpayPaymentId(DEFAULT_RAZORPAY_PAYMENT_ID)
            .razorpayOrderId(DEFAULT_RAZORPAY_ORDER_ID)
            .razorpaySignature(DEFAULT_RAZORPAY_SIGNATURE)
            .discount(DEFAULT_DISCOUNT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return bookings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookings createUpdatedEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .name(UPDATED_NAME)
            .checkInDate(UPDATED_CHECK_IN_DATE)
            .checkOutDate(UPDATED_CHECK_OUT_DATE)
            .pricePerNight(UPDATED_PRICE_PER_NIGHT)
            .childPricePerNight(UPDATED_CHILD_PRICE_PER_NIGHT)
            .numOfNights(UPDATED_NUM_OF_NIGHTS)
            .razorpayPaymentId(UPDATED_RAZORPAY_PAYMENT_ID)
            .razorpayOrderId(UPDATED_RAZORPAY_ORDER_ID)
            .razorpaySignature(UPDATED_RAZORPAY_SIGNATURE)
            .discount(UPDATED_DISCOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return bookings;
    }

    @BeforeEach
    public void initTest() {
        bookings = createEntity(em);
    }

    @Test
    @Transactional
    void createBookings() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();
        // Create the Bookings
        restBookingsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isCreated());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate + 1);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBookings.getCheckInDate()).isEqualTo(DEFAULT_CHECK_IN_DATE);
        assertThat(testBookings.getCheckOutDate()).isEqualTo(DEFAULT_CHECK_OUT_DATE);
        assertThat(testBookings.getPricePerNight()).isEqualTo(DEFAULT_PRICE_PER_NIGHT);
        assertThat(testBookings.getChildPricePerNight()).isEqualTo(DEFAULT_CHILD_PRICE_PER_NIGHT);
        assertThat(testBookings.getNumOfNights()).isEqualTo(DEFAULT_NUM_OF_NIGHTS);
        assertThat(testBookings.getRazorpayPaymentId()).isEqualTo(DEFAULT_RAZORPAY_PAYMENT_ID);
        assertThat(testBookings.getRazorpayOrderId()).isEqualTo(DEFAULT_RAZORPAY_ORDER_ID);
        assertThat(testBookings.getRazorpaySignature()).isEqualTo(DEFAULT_RAZORPAY_SIGNATURE);
        assertThat(testBookings.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testBookings.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testBookings.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBookings.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBookings.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testBookings.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createBookingsWithExistingId() throws Exception {
        // Create the Bookings with an existing ID
        bookings.setId(1L);

        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList
        restBookingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].checkInDate").value(hasItem(DEFAULT_CHECK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].checkOutDate").value(hasItem(DEFAULT_CHECK_OUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].pricePerNight").value(hasItem(DEFAULT_PRICE_PER_NIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].childPricePerNight").value(hasItem(DEFAULT_CHILD_PRICE_PER_NIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].numOfNights").value(hasItem(DEFAULT_NUM_OF_NIGHTS)))
            .andExpect(jsonPath("$.[*].razorpayPaymentId").value(hasItem(DEFAULT_RAZORPAY_PAYMENT_ID)))
            .andExpect(jsonPath("$.[*].razorpayOrderId").value(hasItem(DEFAULT_RAZORPAY_ORDER_ID)))
            .andExpect(jsonPath("$.[*].razorpaySignature").value(hasItem(DEFAULT_RAZORPAY_SIGNATURE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get the bookings
        restBookingsMockMvc
            .perform(get(ENTITY_API_URL_ID, bookings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookings.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.checkInDate").value(DEFAULT_CHECK_IN_DATE.toString()))
            .andExpect(jsonPath("$.checkOutDate").value(DEFAULT_CHECK_OUT_DATE.toString()))
            .andExpect(jsonPath("$.pricePerNight").value(DEFAULT_PRICE_PER_NIGHT.doubleValue()))
            .andExpect(jsonPath("$.childPricePerNight").value(DEFAULT_CHILD_PRICE_PER_NIGHT.doubleValue()))
            .andExpect(jsonPath("$.numOfNights").value(DEFAULT_NUM_OF_NIGHTS))
            .andExpect(jsonPath("$.razorpayPaymentId").value(DEFAULT_RAZORPAY_PAYMENT_ID))
            .andExpect(jsonPath("$.razorpayOrderId").value(DEFAULT_RAZORPAY_ORDER_ID))
            .andExpect(jsonPath("$.razorpaySignature").value(DEFAULT_RAZORPAY_SIGNATURE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getBookingsByIdFiltering() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        Long id = bookings.getId();

        defaultBookingsShouldBeFound("id.equals=" + id);
        defaultBookingsShouldNotBeFound("id.notEquals=" + id);

        defaultBookingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookingsShouldNotBeFound("id.greaterThan=" + id);

        defaultBookingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBookingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where name equals to DEFAULT_NAME
        defaultBookingsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bookingsList where name equals to UPDATED_NAME
        defaultBookingsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBookingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where name not equals to DEFAULT_NAME
        defaultBookingsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the bookingsList where name not equals to UPDATED_NAME
        defaultBookingsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBookingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBookingsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bookingsList where name equals to UPDATED_NAME
        defaultBookingsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBookingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where name is not null
        defaultBookingsShouldBeFound("name.specified=true");

        // Get all the bookingsList where name is null
        defaultBookingsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByNameContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where name contains DEFAULT_NAME
        defaultBookingsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bookingsList where name contains UPDATED_NAME
        defaultBookingsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBookingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where name does not contain DEFAULT_NAME
        defaultBookingsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bookingsList where name does not contain UPDATED_NAME
        defaultBookingsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBookingsByCheckInDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkInDate equals to DEFAULT_CHECK_IN_DATE
        defaultBookingsShouldBeFound("checkInDate.equals=" + DEFAULT_CHECK_IN_DATE);

        // Get all the bookingsList where checkInDate equals to UPDATED_CHECK_IN_DATE
        defaultBookingsShouldNotBeFound("checkInDate.equals=" + UPDATED_CHECK_IN_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCheckInDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkInDate not equals to DEFAULT_CHECK_IN_DATE
        defaultBookingsShouldNotBeFound("checkInDate.notEquals=" + DEFAULT_CHECK_IN_DATE);

        // Get all the bookingsList where checkInDate not equals to UPDATED_CHECK_IN_DATE
        defaultBookingsShouldBeFound("checkInDate.notEquals=" + UPDATED_CHECK_IN_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCheckInDateIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkInDate in DEFAULT_CHECK_IN_DATE or UPDATED_CHECK_IN_DATE
        defaultBookingsShouldBeFound("checkInDate.in=" + DEFAULT_CHECK_IN_DATE + "," + UPDATED_CHECK_IN_DATE);

        // Get all the bookingsList where checkInDate equals to UPDATED_CHECK_IN_DATE
        defaultBookingsShouldNotBeFound("checkInDate.in=" + UPDATED_CHECK_IN_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCheckInDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkInDate is not null
        defaultBookingsShouldBeFound("checkInDate.specified=true");

        // Get all the bookingsList where checkInDate is null
        defaultBookingsShouldNotBeFound("checkInDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByCheckOutDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkOutDate equals to DEFAULT_CHECK_OUT_DATE
        defaultBookingsShouldBeFound("checkOutDate.equals=" + DEFAULT_CHECK_OUT_DATE);

        // Get all the bookingsList where checkOutDate equals to UPDATED_CHECK_OUT_DATE
        defaultBookingsShouldNotBeFound("checkOutDate.equals=" + UPDATED_CHECK_OUT_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCheckOutDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkOutDate not equals to DEFAULT_CHECK_OUT_DATE
        defaultBookingsShouldNotBeFound("checkOutDate.notEquals=" + DEFAULT_CHECK_OUT_DATE);

        // Get all the bookingsList where checkOutDate not equals to UPDATED_CHECK_OUT_DATE
        defaultBookingsShouldBeFound("checkOutDate.notEquals=" + UPDATED_CHECK_OUT_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCheckOutDateIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkOutDate in DEFAULT_CHECK_OUT_DATE or UPDATED_CHECK_OUT_DATE
        defaultBookingsShouldBeFound("checkOutDate.in=" + DEFAULT_CHECK_OUT_DATE + "," + UPDATED_CHECK_OUT_DATE);

        // Get all the bookingsList where checkOutDate equals to UPDATED_CHECK_OUT_DATE
        defaultBookingsShouldNotBeFound("checkOutDate.in=" + UPDATED_CHECK_OUT_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCheckOutDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where checkOutDate is not null
        defaultBookingsShouldBeFound("checkOutDate.specified=true");

        // Get all the bookingsList where checkOutDate is null
        defaultBookingsShouldNotBeFound("checkOutDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight equals to DEFAULT_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("pricePerNight.equals=" + DEFAULT_PRICE_PER_NIGHT);

        // Get all the bookingsList where pricePerNight equals to UPDATED_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("pricePerNight.equals=" + UPDATED_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight not equals to DEFAULT_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("pricePerNight.notEquals=" + DEFAULT_PRICE_PER_NIGHT);

        // Get all the bookingsList where pricePerNight not equals to UPDATED_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("pricePerNight.notEquals=" + UPDATED_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight in DEFAULT_PRICE_PER_NIGHT or UPDATED_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("pricePerNight.in=" + DEFAULT_PRICE_PER_NIGHT + "," + UPDATED_PRICE_PER_NIGHT);

        // Get all the bookingsList where pricePerNight equals to UPDATED_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("pricePerNight.in=" + UPDATED_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight is not null
        defaultBookingsShouldBeFound("pricePerNight.specified=true");

        // Get all the bookingsList where pricePerNight is null
        defaultBookingsShouldNotBeFound("pricePerNight.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight is greater than or equal to DEFAULT_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("pricePerNight.greaterThanOrEqual=" + DEFAULT_PRICE_PER_NIGHT);

        // Get all the bookingsList where pricePerNight is greater than or equal to UPDATED_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("pricePerNight.greaterThanOrEqual=" + UPDATED_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight is less than or equal to DEFAULT_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("pricePerNight.lessThanOrEqual=" + DEFAULT_PRICE_PER_NIGHT);

        // Get all the bookingsList where pricePerNight is less than or equal to SMALLER_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("pricePerNight.lessThanOrEqual=" + SMALLER_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight is less than DEFAULT_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("pricePerNight.lessThan=" + DEFAULT_PRICE_PER_NIGHT);

        // Get all the bookingsList where pricePerNight is less than UPDATED_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("pricePerNight.lessThan=" + UPDATED_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByPricePerNightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where pricePerNight is greater than DEFAULT_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("pricePerNight.greaterThan=" + DEFAULT_PRICE_PER_NIGHT);

        // Get all the bookingsList where pricePerNight is greater than SMALLER_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("pricePerNight.greaterThan=" + SMALLER_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight equals to DEFAULT_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("childPricePerNight.equals=" + DEFAULT_CHILD_PRICE_PER_NIGHT);

        // Get all the bookingsList where childPricePerNight equals to UPDATED_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("childPricePerNight.equals=" + UPDATED_CHILD_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight not equals to DEFAULT_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("childPricePerNight.notEquals=" + DEFAULT_CHILD_PRICE_PER_NIGHT);

        // Get all the bookingsList where childPricePerNight not equals to UPDATED_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("childPricePerNight.notEquals=" + UPDATED_CHILD_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight in DEFAULT_CHILD_PRICE_PER_NIGHT or UPDATED_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("childPricePerNight.in=" + DEFAULT_CHILD_PRICE_PER_NIGHT + "," + UPDATED_CHILD_PRICE_PER_NIGHT);

        // Get all the bookingsList where childPricePerNight equals to UPDATED_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("childPricePerNight.in=" + UPDATED_CHILD_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight is not null
        defaultBookingsShouldBeFound("childPricePerNight.specified=true");

        // Get all the bookingsList where childPricePerNight is null
        defaultBookingsShouldNotBeFound("childPricePerNight.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight is greater than or equal to DEFAULT_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("childPricePerNight.greaterThanOrEqual=" + DEFAULT_CHILD_PRICE_PER_NIGHT);

        // Get all the bookingsList where childPricePerNight is greater than or equal to UPDATED_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("childPricePerNight.greaterThanOrEqual=" + UPDATED_CHILD_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight is less than or equal to DEFAULT_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("childPricePerNight.lessThanOrEqual=" + DEFAULT_CHILD_PRICE_PER_NIGHT);

        // Get all the bookingsList where childPricePerNight is less than or equal to SMALLER_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("childPricePerNight.lessThanOrEqual=" + SMALLER_CHILD_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight is less than DEFAULT_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("childPricePerNight.lessThan=" + DEFAULT_CHILD_PRICE_PER_NIGHT);

        // Get all the bookingsList where childPricePerNight is less than UPDATED_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("childPricePerNight.lessThan=" + UPDATED_CHILD_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByChildPricePerNightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where childPricePerNight is greater than DEFAULT_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldNotBeFound("childPricePerNight.greaterThan=" + DEFAULT_CHILD_PRICE_PER_NIGHT);

        // Get all the bookingsList where childPricePerNight is greater than SMALLER_CHILD_PRICE_PER_NIGHT
        defaultBookingsShouldBeFound("childPricePerNight.greaterThan=" + SMALLER_CHILD_PRICE_PER_NIGHT);
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights equals to DEFAULT_NUM_OF_NIGHTS
        defaultBookingsShouldBeFound("numOfNights.equals=" + DEFAULT_NUM_OF_NIGHTS);

        // Get all the bookingsList where numOfNights equals to UPDATED_NUM_OF_NIGHTS
        defaultBookingsShouldNotBeFound("numOfNights.equals=" + UPDATED_NUM_OF_NIGHTS);
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights not equals to DEFAULT_NUM_OF_NIGHTS
        defaultBookingsShouldNotBeFound("numOfNights.notEquals=" + DEFAULT_NUM_OF_NIGHTS);

        // Get all the bookingsList where numOfNights not equals to UPDATED_NUM_OF_NIGHTS
        defaultBookingsShouldBeFound("numOfNights.notEquals=" + UPDATED_NUM_OF_NIGHTS);
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights in DEFAULT_NUM_OF_NIGHTS or UPDATED_NUM_OF_NIGHTS
        defaultBookingsShouldBeFound("numOfNights.in=" + DEFAULT_NUM_OF_NIGHTS + "," + UPDATED_NUM_OF_NIGHTS);

        // Get all the bookingsList where numOfNights equals to UPDATED_NUM_OF_NIGHTS
        defaultBookingsShouldNotBeFound("numOfNights.in=" + UPDATED_NUM_OF_NIGHTS);
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights is not null
        defaultBookingsShouldBeFound("numOfNights.specified=true");

        // Get all the bookingsList where numOfNights is null
        defaultBookingsShouldNotBeFound("numOfNights.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights is greater than or equal to DEFAULT_NUM_OF_NIGHTS
        defaultBookingsShouldBeFound("numOfNights.greaterThanOrEqual=" + DEFAULT_NUM_OF_NIGHTS);

        // Get all the bookingsList where numOfNights is greater than or equal to UPDATED_NUM_OF_NIGHTS
        defaultBookingsShouldNotBeFound("numOfNights.greaterThanOrEqual=" + UPDATED_NUM_OF_NIGHTS);
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights is less than or equal to DEFAULT_NUM_OF_NIGHTS
        defaultBookingsShouldBeFound("numOfNights.lessThanOrEqual=" + DEFAULT_NUM_OF_NIGHTS);

        // Get all the bookingsList where numOfNights is less than or equal to SMALLER_NUM_OF_NIGHTS
        defaultBookingsShouldNotBeFound("numOfNights.lessThanOrEqual=" + SMALLER_NUM_OF_NIGHTS);
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights is less than DEFAULT_NUM_OF_NIGHTS
        defaultBookingsShouldNotBeFound("numOfNights.lessThan=" + DEFAULT_NUM_OF_NIGHTS);

        // Get all the bookingsList where numOfNights is less than UPDATED_NUM_OF_NIGHTS
        defaultBookingsShouldBeFound("numOfNights.lessThan=" + UPDATED_NUM_OF_NIGHTS);
    }

    @Test
    @Transactional
    void getAllBookingsByNumOfNightsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where numOfNights is greater than DEFAULT_NUM_OF_NIGHTS
        defaultBookingsShouldNotBeFound("numOfNights.greaterThan=" + DEFAULT_NUM_OF_NIGHTS);

        // Get all the bookingsList where numOfNights is greater than SMALLER_NUM_OF_NIGHTS
        defaultBookingsShouldBeFound("numOfNights.greaterThan=" + SMALLER_NUM_OF_NIGHTS);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayPaymentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayPaymentId equals to DEFAULT_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldBeFound("razorpayPaymentId.equals=" + DEFAULT_RAZORPAY_PAYMENT_ID);

        // Get all the bookingsList where razorpayPaymentId equals to UPDATED_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldNotBeFound("razorpayPaymentId.equals=" + UPDATED_RAZORPAY_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayPaymentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayPaymentId not equals to DEFAULT_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldNotBeFound("razorpayPaymentId.notEquals=" + DEFAULT_RAZORPAY_PAYMENT_ID);

        // Get all the bookingsList where razorpayPaymentId not equals to UPDATED_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldBeFound("razorpayPaymentId.notEquals=" + UPDATED_RAZORPAY_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayPaymentIdIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayPaymentId in DEFAULT_RAZORPAY_PAYMENT_ID or UPDATED_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldBeFound("razorpayPaymentId.in=" + DEFAULT_RAZORPAY_PAYMENT_ID + "," + UPDATED_RAZORPAY_PAYMENT_ID);

        // Get all the bookingsList where razorpayPaymentId equals to UPDATED_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldNotBeFound("razorpayPaymentId.in=" + UPDATED_RAZORPAY_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayPaymentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayPaymentId is not null
        defaultBookingsShouldBeFound("razorpayPaymentId.specified=true");

        // Get all the bookingsList where razorpayPaymentId is null
        defaultBookingsShouldNotBeFound("razorpayPaymentId.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayPaymentIdContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayPaymentId contains DEFAULT_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldBeFound("razorpayPaymentId.contains=" + DEFAULT_RAZORPAY_PAYMENT_ID);

        // Get all the bookingsList where razorpayPaymentId contains UPDATED_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldNotBeFound("razorpayPaymentId.contains=" + UPDATED_RAZORPAY_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayPaymentIdNotContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayPaymentId does not contain DEFAULT_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldNotBeFound("razorpayPaymentId.doesNotContain=" + DEFAULT_RAZORPAY_PAYMENT_ID);

        // Get all the bookingsList where razorpayPaymentId does not contain UPDATED_RAZORPAY_PAYMENT_ID
        defaultBookingsShouldBeFound("razorpayPaymentId.doesNotContain=" + UPDATED_RAZORPAY_PAYMENT_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayOrderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayOrderId equals to DEFAULT_RAZORPAY_ORDER_ID
        defaultBookingsShouldBeFound("razorpayOrderId.equals=" + DEFAULT_RAZORPAY_ORDER_ID);

        // Get all the bookingsList where razorpayOrderId equals to UPDATED_RAZORPAY_ORDER_ID
        defaultBookingsShouldNotBeFound("razorpayOrderId.equals=" + UPDATED_RAZORPAY_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayOrderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayOrderId not equals to DEFAULT_RAZORPAY_ORDER_ID
        defaultBookingsShouldNotBeFound("razorpayOrderId.notEquals=" + DEFAULT_RAZORPAY_ORDER_ID);

        // Get all the bookingsList where razorpayOrderId not equals to UPDATED_RAZORPAY_ORDER_ID
        defaultBookingsShouldBeFound("razorpayOrderId.notEquals=" + UPDATED_RAZORPAY_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayOrderIdIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayOrderId in DEFAULT_RAZORPAY_ORDER_ID or UPDATED_RAZORPAY_ORDER_ID
        defaultBookingsShouldBeFound("razorpayOrderId.in=" + DEFAULT_RAZORPAY_ORDER_ID + "," + UPDATED_RAZORPAY_ORDER_ID);

        // Get all the bookingsList where razorpayOrderId equals to UPDATED_RAZORPAY_ORDER_ID
        defaultBookingsShouldNotBeFound("razorpayOrderId.in=" + UPDATED_RAZORPAY_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayOrderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayOrderId is not null
        defaultBookingsShouldBeFound("razorpayOrderId.specified=true");

        // Get all the bookingsList where razorpayOrderId is null
        defaultBookingsShouldNotBeFound("razorpayOrderId.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayOrderIdContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayOrderId contains DEFAULT_RAZORPAY_ORDER_ID
        defaultBookingsShouldBeFound("razorpayOrderId.contains=" + DEFAULT_RAZORPAY_ORDER_ID);

        // Get all the bookingsList where razorpayOrderId contains UPDATED_RAZORPAY_ORDER_ID
        defaultBookingsShouldNotBeFound("razorpayOrderId.contains=" + UPDATED_RAZORPAY_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpayOrderIdNotContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpayOrderId does not contain DEFAULT_RAZORPAY_ORDER_ID
        defaultBookingsShouldNotBeFound("razorpayOrderId.doesNotContain=" + DEFAULT_RAZORPAY_ORDER_ID);

        // Get all the bookingsList where razorpayOrderId does not contain UPDATED_RAZORPAY_ORDER_ID
        defaultBookingsShouldBeFound("razorpayOrderId.doesNotContain=" + UPDATED_RAZORPAY_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpaySignatureIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpaySignature equals to DEFAULT_RAZORPAY_SIGNATURE
        defaultBookingsShouldBeFound("razorpaySignature.equals=" + DEFAULT_RAZORPAY_SIGNATURE);

        // Get all the bookingsList where razorpaySignature equals to UPDATED_RAZORPAY_SIGNATURE
        defaultBookingsShouldNotBeFound("razorpaySignature.equals=" + UPDATED_RAZORPAY_SIGNATURE);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpaySignatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpaySignature not equals to DEFAULT_RAZORPAY_SIGNATURE
        defaultBookingsShouldNotBeFound("razorpaySignature.notEquals=" + DEFAULT_RAZORPAY_SIGNATURE);

        // Get all the bookingsList where razorpaySignature not equals to UPDATED_RAZORPAY_SIGNATURE
        defaultBookingsShouldBeFound("razorpaySignature.notEquals=" + UPDATED_RAZORPAY_SIGNATURE);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpaySignatureIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpaySignature in DEFAULT_RAZORPAY_SIGNATURE or UPDATED_RAZORPAY_SIGNATURE
        defaultBookingsShouldBeFound("razorpaySignature.in=" + DEFAULT_RAZORPAY_SIGNATURE + "," + UPDATED_RAZORPAY_SIGNATURE);

        // Get all the bookingsList where razorpaySignature equals to UPDATED_RAZORPAY_SIGNATURE
        defaultBookingsShouldNotBeFound("razorpaySignature.in=" + UPDATED_RAZORPAY_SIGNATURE);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpaySignatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpaySignature is not null
        defaultBookingsShouldBeFound("razorpaySignature.specified=true");

        // Get all the bookingsList where razorpaySignature is null
        defaultBookingsShouldNotBeFound("razorpaySignature.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpaySignatureContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpaySignature contains DEFAULT_RAZORPAY_SIGNATURE
        defaultBookingsShouldBeFound("razorpaySignature.contains=" + DEFAULT_RAZORPAY_SIGNATURE);

        // Get all the bookingsList where razorpaySignature contains UPDATED_RAZORPAY_SIGNATURE
        defaultBookingsShouldNotBeFound("razorpaySignature.contains=" + UPDATED_RAZORPAY_SIGNATURE);
    }

    @Test
    @Transactional
    void getAllBookingsByRazorpaySignatureNotContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where razorpaySignature does not contain DEFAULT_RAZORPAY_SIGNATURE
        defaultBookingsShouldNotBeFound("razorpaySignature.doesNotContain=" + DEFAULT_RAZORPAY_SIGNATURE);

        // Get all the bookingsList where razorpaySignature does not contain UPDATED_RAZORPAY_SIGNATURE
        defaultBookingsShouldBeFound("razorpaySignature.doesNotContain=" + UPDATED_RAZORPAY_SIGNATURE);
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount equals to DEFAULT_DISCOUNT
        defaultBookingsShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the bookingsList where discount equals to UPDATED_DISCOUNT
        defaultBookingsShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount not equals to DEFAULT_DISCOUNT
        defaultBookingsShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the bookingsList where discount not equals to UPDATED_DISCOUNT
        defaultBookingsShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultBookingsShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the bookingsList where discount equals to UPDATED_DISCOUNT
        defaultBookingsShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount is not null
        defaultBookingsShouldBeFound("discount.specified=true");

        // Get all the bookingsList where discount is null
        defaultBookingsShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultBookingsShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the bookingsList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultBookingsShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultBookingsShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the bookingsList where discount is less than or equal to SMALLER_DISCOUNT
        defaultBookingsShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount is less than DEFAULT_DISCOUNT
        defaultBookingsShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the bookingsList where discount is less than UPDATED_DISCOUNT
        defaultBookingsShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where discount is greater than DEFAULT_DISCOUNT
        defaultBookingsShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the bookingsList where discount is greater than SMALLER_DISCOUNT
        defaultBookingsShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultBookingsShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the bookingsList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultBookingsShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount not equals to DEFAULT_TOTAL_AMOUNT
        defaultBookingsShouldNotBeFound("totalAmount.notEquals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the bookingsList where totalAmount not equals to UPDATED_TOTAL_AMOUNT
        defaultBookingsShouldBeFound("totalAmount.notEquals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultBookingsShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the bookingsList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultBookingsShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount is not null
        defaultBookingsShouldBeFound("totalAmount.specified=true");

        // Get all the bookingsList where totalAmount is null
        defaultBookingsShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultBookingsShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the bookingsList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultBookingsShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultBookingsShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the bookingsList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultBookingsShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultBookingsShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the bookingsList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultBookingsShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultBookingsShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the bookingsList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultBookingsShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdBy equals to DEFAULT_CREATED_BY
        defaultBookingsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the bookingsList where createdBy equals to UPDATED_CREATED_BY
        defaultBookingsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdBy not equals to DEFAULT_CREATED_BY
        defaultBookingsShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the bookingsList where createdBy not equals to UPDATED_CREATED_BY
        defaultBookingsShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultBookingsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the bookingsList where createdBy equals to UPDATED_CREATED_BY
        defaultBookingsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdBy is not null
        defaultBookingsShouldBeFound("createdBy.specified=true");

        // Get all the bookingsList where createdBy is null
        defaultBookingsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdBy contains DEFAULT_CREATED_BY
        defaultBookingsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the bookingsList where createdBy contains UPDATED_CREATED_BY
        defaultBookingsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultBookingsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the bookingsList where createdBy does not contain UPDATED_CREATED_BY
        defaultBookingsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultBookingsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the bookingsList where createdDate equals to UPDATED_CREATED_DATE
        defaultBookingsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultBookingsShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the bookingsList where createdDate not equals to UPDATED_CREATED_DATE
        defaultBookingsShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultBookingsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the bookingsList where createdDate equals to UPDATED_CREATED_DATE
        defaultBookingsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where createdDate is not null
        defaultBookingsShouldBeFound("createdDate.specified=true");

        // Get all the bookingsList where createdDate is null
        defaultBookingsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultBookingsShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the bookingsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultBookingsShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultBookingsShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the bookingsList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultBookingsShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultBookingsShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the bookingsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultBookingsShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllBookingsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updatedBy is not null
        defaultBookingsShouldBeFound("updatedBy.specified=true");

        // Get all the bookingsList where updatedBy is null
        defaultBookingsShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultBookingsShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the bookingsList where updateDate equals to UPDATED_UPDATE_DATE
        defaultBookingsShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultBookingsShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the bookingsList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultBookingsShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultBookingsShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the bookingsList where updateDate equals to UPDATED_UPDATE_DATE
        defaultBookingsShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllBookingsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList where updateDate is not null
        defaultBookingsShouldBeFound("updateDate.specified=true");

        // Get all the bookingsList where updateDate is null
        defaultBookingsShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        bookings.setUser(user);
        bookingsRepository.saveAndFlush(bookings);
        Long userId = user.getId();

        // Get all the bookingsList where user equals to userId
        defaultBookingsShouldBeFound("userId.equals=" + userId);

        // Get all the bookingsList where user equals to (userId + 1)
        defaultBookingsShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllBookingsByListingIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);
        Listing listing = ListingResourceIT.createEntity(em);
        em.persist(listing);
        em.flush();
        bookings.setListing(listing);
        bookingsRepository.saveAndFlush(bookings);
        Long listingId = listing.getId();

        // Get all the bookingsList where listing equals to listingId
        defaultBookingsShouldBeFound("listingId.equals=" + listingId);

        // Get all the bookingsList where listing equals to (listingId + 1)
        defaultBookingsShouldNotBeFound("listingId.equals=" + (listingId + 1));
    }

    @Test
    @Transactional
    void getAllBookingsByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);
        Invoice invoice = InvoiceResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        bookings.addInvoice(invoice);
        bookingsRepository.saveAndFlush(bookings);
        Long invoiceId = invoice.getId();

        // Get all the bookingsList where invoice equals to invoiceId
        defaultBookingsShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the bookingsList where invoice equals to (invoiceId + 1)
        defaultBookingsShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookingsShouldBeFound(String filter) throws Exception {
        restBookingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].checkInDate").value(hasItem(DEFAULT_CHECK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].checkOutDate").value(hasItem(DEFAULT_CHECK_OUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].pricePerNight").value(hasItem(DEFAULT_PRICE_PER_NIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].childPricePerNight").value(hasItem(DEFAULT_CHILD_PRICE_PER_NIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].numOfNights").value(hasItem(DEFAULT_NUM_OF_NIGHTS)))
            .andExpect(jsonPath("$.[*].razorpayPaymentId").value(hasItem(DEFAULT_RAZORPAY_PAYMENT_ID)))
            .andExpect(jsonPath("$.[*].razorpayOrderId").value(hasItem(DEFAULT_RAZORPAY_ORDER_ID)))
            .andExpect(jsonPath("$.[*].razorpaySignature").value(hasItem(DEFAULT_RAZORPAY_SIGNATURE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restBookingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookingsShouldNotBeFound(String filter) throws Exception {
        restBookingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBookings() throws Exception {
        // Get the bookings
        restBookingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Update the bookings
        Bookings updatedBookings = bookingsRepository.findById(bookings.getId()).get();
        // Disconnect from session so that the updates on updatedBookings are not directly saved in db
        em.detach(updatedBookings);
        updatedBookings
            .name(UPDATED_NAME)
            .checkInDate(UPDATED_CHECK_IN_DATE)
            .checkOutDate(UPDATED_CHECK_OUT_DATE)
            .pricePerNight(UPDATED_PRICE_PER_NIGHT)
            .childPricePerNight(UPDATED_CHILD_PRICE_PER_NIGHT)
            .numOfNights(UPDATED_NUM_OF_NIGHTS)
            .razorpayPaymentId(UPDATED_RAZORPAY_PAYMENT_ID)
            .razorpayOrderId(UPDATED_RAZORPAY_ORDER_ID)
            .razorpaySignature(UPDATED_RAZORPAY_SIGNATURE)
            .discount(UPDATED_DISCOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restBookingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBookings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBookings))
            )
            .andExpect(status().isOk());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBookings.getCheckInDate()).isEqualTo(UPDATED_CHECK_IN_DATE);
        assertThat(testBookings.getCheckOutDate()).isEqualTo(UPDATED_CHECK_OUT_DATE);
        assertThat(testBookings.getPricePerNight()).isEqualTo(UPDATED_PRICE_PER_NIGHT);
        assertThat(testBookings.getChildPricePerNight()).isEqualTo(UPDATED_CHILD_PRICE_PER_NIGHT);
        assertThat(testBookings.getNumOfNights()).isEqualTo(UPDATED_NUM_OF_NIGHTS);
        assertThat(testBookings.getRazorpayPaymentId()).isEqualTo(UPDATED_RAZORPAY_PAYMENT_ID);
        assertThat(testBookings.getRazorpayOrderId()).isEqualTo(UPDATED_RAZORPAY_ORDER_ID);
        assertThat(testBookings.getRazorpaySignature()).isEqualTo(UPDATED_RAZORPAY_SIGNATURE);
        assertThat(testBookings.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testBookings.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testBookings.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBookings.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBookings.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testBookings.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();
        bookings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();
        bookings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();
        bookings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookingsWithPatch() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Update the bookings using partial update
        Bookings partialUpdatedBookings = new Bookings();
        partialUpdatedBookings.setId(bookings.getId());

        partialUpdatedBookings
            .checkOutDate(UPDATED_CHECK_OUT_DATE)
            .childPricePerNight(UPDATED_CHILD_PRICE_PER_NIGHT)
            .razorpayPaymentId(UPDATED_RAZORPAY_PAYMENT_ID)
            .razorpaySignature(UPDATED_RAZORPAY_SIGNATURE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restBookingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookings))
            )
            .andExpect(status().isOk());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBookings.getCheckInDate()).isEqualTo(DEFAULT_CHECK_IN_DATE);
        assertThat(testBookings.getCheckOutDate()).isEqualTo(UPDATED_CHECK_OUT_DATE);
        assertThat(testBookings.getPricePerNight()).isEqualTo(DEFAULT_PRICE_PER_NIGHT);
        assertThat(testBookings.getChildPricePerNight()).isEqualTo(UPDATED_CHILD_PRICE_PER_NIGHT);
        assertThat(testBookings.getNumOfNights()).isEqualTo(DEFAULT_NUM_OF_NIGHTS);
        assertThat(testBookings.getRazorpayPaymentId()).isEqualTo(UPDATED_RAZORPAY_PAYMENT_ID);
        assertThat(testBookings.getRazorpayOrderId()).isEqualTo(DEFAULT_RAZORPAY_ORDER_ID);
        assertThat(testBookings.getRazorpaySignature()).isEqualTo(UPDATED_RAZORPAY_SIGNATURE);
        assertThat(testBookings.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testBookings.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testBookings.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBookings.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBookings.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testBookings.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBookingsWithPatch() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Update the bookings using partial update
        Bookings partialUpdatedBookings = new Bookings();
        partialUpdatedBookings.setId(bookings.getId());

        partialUpdatedBookings
            .name(UPDATED_NAME)
            .checkInDate(UPDATED_CHECK_IN_DATE)
            .checkOutDate(UPDATED_CHECK_OUT_DATE)
            .pricePerNight(UPDATED_PRICE_PER_NIGHT)
            .childPricePerNight(UPDATED_CHILD_PRICE_PER_NIGHT)
            .numOfNights(UPDATED_NUM_OF_NIGHTS)
            .razorpayPaymentId(UPDATED_RAZORPAY_PAYMENT_ID)
            .razorpayOrderId(UPDATED_RAZORPAY_ORDER_ID)
            .razorpaySignature(UPDATED_RAZORPAY_SIGNATURE)
            .discount(UPDATED_DISCOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restBookingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookings))
            )
            .andExpect(status().isOk());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBookings.getCheckInDate()).isEqualTo(UPDATED_CHECK_IN_DATE);
        assertThat(testBookings.getCheckOutDate()).isEqualTo(UPDATED_CHECK_OUT_DATE);
        assertThat(testBookings.getPricePerNight()).isEqualTo(UPDATED_PRICE_PER_NIGHT);
        assertThat(testBookings.getChildPricePerNight()).isEqualTo(UPDATED_CHILD_PRICE_PER_NIGHT);
        assertThat(testBookings.getNumOfNights()).isEqualTo(UPDATED_NUM_OF_NIGHTS);
        assertThat(testBookings.getRazorpayPaymentId()).isEqualTo(UPDATED_RAZORPAY_PAYMENT_ID);
        assertThat(testBookings.getRazorpayOrderId()).isEqualTo(UPDATED_RAZORPAY_ORDER_ID);
        assertThat(testBookings.getRazorpaySignature()).isEqualTo(UPDATED_RAZORPAY_SIGNATURE);
        assertThat(testBookings.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testBookings.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testBookings.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBookings.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBookings.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testBookings.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();
        bookings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();
        bookings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookings))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();
        bookings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeDelete = bookingsRepository.findAll().size();

        // Delete the bookings
        restBookingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
