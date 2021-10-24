package com.dd.campsites.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dd.campsites.IntegrationTest;
import com.dd.campsites.domain.Listing;
import com.dd.campsites.domain.ListingType;
import com.dd.campsites.domain.Location;
import com.dd.campsites.domain.User;
import com.dd.campsites.repository.ListingRepository;
import com.dd.campsites.service.criteria.ListingCriteria;
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
 * Integration tests for the {@link ListingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListingResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_FEATURED = false;
    private static final Boolean UPDATED_IS_FEATURED = true;

    private static final Double DEFAULT_PRICE_PER_PERSON = 1D;
    private static final Double UPDATED_PRICE_PER_PERSON = 2D;
    private static final Double SMALLER_PRICE_PER_PERSON = 1D - 1D;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISABLE_BOOKING = false;
    private static final Boolean UPDATED_DISABLE_BOOKING = true;

    private static final Integer DEFAULT_VIEW_COUNT = 1;
    private static final Integer UPDATED_VIEW_COUNT = 2;
    private static final Integer SMALLER_VIEW_COUNT = 1 - 1;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_BY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_BY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/listings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListingMockMvc;

    private Listing listing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Listing createEntity(EntityManager em) {
        Listing listing = new Listing()
            .address(DEFAULT_ADDRESS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .url(DEFAULT_URL)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .thumbnail(DEFAULT_THUMBNAIL)
            .isFeatured(DEFAULT_IS_FEATURED)
            .pricePerPerson(DEFAULT_PRICE_PER_PERSON)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .comment(DEFAULT_COMMENT)
            .disableBooking(DEFAULT_DISABLE_BOOKING)
            .viewCount(DEFAULT_VIEW_COUNT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateDate(DEFAULT_UPDATE_DATE);
        return listing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Listing createUpdatedEntity(EntityManager em) {
        Listing listing = new Listing()
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .url(UPDATED_URL)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .thumbnail(UPDATED_THUMBNAIL)
            .isFeatured(UPDATED_IS_FEATURED)
            .pricePerPerson(UPDATED_PRICE_PER_PERSON)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .comment(UPDATED_COMMENT)
            .disableBooking(UPDATED_DISABLE_BOOKING)
            .viewCount(UPDATED_VIEW_COUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);
        return listing;
    }

    @BeforeEach
    public void initTest() {
        listing = createEntity(em);
    }

    @Test
    @Transactional
    void createListing() throws Exception {
        int databaseSizeBeforeCreate = listingRepository.findAll().size();
        // Create the Listing
        restListingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isCreated());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeCreate + 1);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testListing.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testListing.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testListing.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testListing.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testListing.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testListing.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testListing.getIsFeatured()).isEqualTo(DEFAULT_IS_FEATURED);
        assertThat(testListing.getPricePerPerson()).isEqualTo(DEFAULT_PRICE_PER_PERSON);
        assertThat(testListing.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testListing.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testListing.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testListing.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testListing.getDisableBooking()).isEqualTo(DEFAULT_DISABLE_BOOKING);
        assertThat(testListing.getViewCount()).isEqualTo(DEFAULT_VIEW_COUNT);
        assertThat(testListing.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testListing.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testListing.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testListing.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createListingWithExistingId() throws Exception {
        // Create the Listing with an existing ID
        listing.setId(1L);

        int databaseSizeBeforeCreate = listingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListings() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList
        restListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listing.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].isFeatured").value(hasItem(DEFAULT_IS_FEATURED.booleanValue())))
            .andExpect(jsonPath("$.[*].pricePerPerson").value(hasItem(DEFAULT_PRICE_PER_PERSON.doubleValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].disableBooking").value(hasItem(DEFAULT_DISABLE_BOOKING.booleanValue())))
            .andExpect(jsonPath("$.[*].viewCount").value(hasItem(DEFAULT_VIEW_COUNT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getListing() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get the listing
        restListingMockMvc
            .perform(get(ENTITY_API_URL_ID, listing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listing.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL))
            .andExpect(jsonPath("$.isFeatured").value(DEFAULT_IS_FEATURED.booleanValue()))
            .andExpect(jsonPath("$.pricePerPerson").value(DEFAULT_PRICE_PER_PERSON.doubleValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.disableBooking").value(DEFAULT_DISABLE_BOOKING.booleanValue()))
            .andExpect(jsonPath("$.viewCount").value(DEFAULT_VIEW_COUNT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getListingsByIdFiltering() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        Long id = listing.getId();

        defaultListingShouldBeFound("id.equals=" + id);
        defaultListingShouldNotBeFound("id.notEquals=" + id);

        defaultListingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultListingShouldNotBeFound("id.greaterThan=" + id);

        defaultListingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultListingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllListingsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where address equals to DEFAULT_ADDRESS
        defaultListingShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the listingList where address equals to UPDATED_ADDRESS
        defaultListingShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllListingsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where address not equals to DEFAULT_ADDRESS
        defaultListingShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the listingList where address not equals to UPDATED_ADDRESS
        defaultListingShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllListingsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultListingShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the listingList where address equals to UPDATED_ADDRESS
        defaultListingShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllListingsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where address is not null
        defaultListingShouldBeFound("address.specified=true");

        // Get all the listingList where address is null
        defaultListingShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByAddressContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where address contains DEFAULT_ADDRESS
        defaultListingShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the listingList where address contains UPDATED_ADDRESS
        defaultListingShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllListingsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where address does not contain DEFAULT_ADDRESS
        defaultListingShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the listingList where address does not contain UPDATED_ADDRESS
        defaultListingShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude equals to DEFAULT_LATITUDE
        defaultListingShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the listingList where latitude equals to UPDATED_LATITUDE
        defaultListingShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude not equals to DEFAULT_LATITUDE
        defaultListingShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the listingList where latitude not equals to UPDATED_LATITUDE
        defaultListingShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultListingShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the listingList where latitude equals to UPDATED_LATITUDE
        defaultListingShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude is not null
        defaultListingShouldBeFound("latitude.specified=true");

        // Get all the listingList where latitude is null
        defaultListingShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultListingShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the listingList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultListingShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultListingShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the listingList where latitude is less than or equal to SMALLER_LATITUDE
        defaultListingShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude is less than DEFAULT_LATITUDE
        defaultListingShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the listingList where latitude is less than UPDATED_LATITUDE
        defaultListingShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where latitude is greater than DEFAULT_LATITUDE
        defaultListingShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the listingList where latitude is greater than SMALLER_LATITUDE
        defaultListingShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude equals to DEFAULT_LONGITUDE
        defaultListingShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the listingList where longitude equals to UPDATED_LONGITUDE
        defaultListingShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude not equals to DEFAULT_LONGITUDE
        defaultListingShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the listingList where longitude not equals to UPDATED_LONGITUDE
        defaultListingShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultListingShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the listingList where longitude equals to UPDATED_LONGITUDE
        defaultListingShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude is not null
        defaultListingShouldBeFound("longitude.specified=true");

        // Get all the listingList where longitude is null
        defaultListingShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultListingShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the listingList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultListingShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultListingShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the listingList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultListingShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude is less than DEFAULT_LONGITUDE
        defaultListingShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the listingList where longitude is less than UPDATED_LONGITUDE
        defaultListingShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where longitude is greater than DEFAULT_LONGITUDE
        defaultListingShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the listingList where longitude is greater than SMALLER_LONGITUDE
        defaultListingShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllListingsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where url equals to DEFAULT_URL
        defaultListingShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the listingList where url equals to UPDATED_URL
        defaultListingShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllListingsByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where url not equals to DEFAULT_URL
        defaultListingShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the listingList where url not equals to UPDATED_URL
        defaultListingShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllListingsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where url in DEFAULT_URL or UPDATED_URL
        defaultListingShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the listingList where url equals to UPDATED_URL
        defaultListingShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllListingsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where url is not null
        defaultListingShouldBeFound("url.specified=true");

        // Get all the listingList where url is null
        defaultListingShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByUrlContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where url contains DEFAULT_URL
        defaultListingShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the listingList where url contains UPDATED_URL
        defaultListingShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllListingsByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where url does not contain DEFAULT_URL
        defaultListingShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the listingList where url does not contain UPDATED_URL
        defaultListingShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }

    @Test
    @Transactional
    void getAllListingsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where title equals to DEFAULT_TITLE
        defaultListingShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the listingList where title equals to UPDATED_TITLE
        defaultListingShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where title not equals to DEFAULT_TITLE
        defaultListingShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the listingList where title not equals to UPDATED_TITLE
        defaultListingShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultListingShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the listingList where title equals to UPDATED_TITLE
        defaultListingShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where title is not null
        defaultListingShouldBeFound("title.specified=true");

        // Get all the listingList where title is null
        defaultListingShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByTitleContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where title contains DEFAULT_TITLE
        defaultListingShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the listingList where title contains UPDATED_TITLE
        defaultListingShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where title does not contain DEFAULT_TITLE
        defaultListingShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the listingList where title does not contain UPDATED_TITLE
        defaultListingShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllListingsByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where content equals to DEFAULT_CONTENT
        defaultListingShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the listingList where content equals to UPDATED_CONTENT
        defaultListingShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllListingsByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where content not equals to DEFAULT_CONTENT
        defaultListingShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the listingList where content not equals to UPDATED_CONTENT
        defaultListingShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllListingsByContentIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultListingShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the listingList where content equals to UPDATED_CONTENT
        defaultListingShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllListingsByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where content is not null
        defaultListingShouldBeFound("content.specified=true");

        // Get all the listingList where content is null
        defaultListingShouldNotBeFound("content.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByContentContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where content contains DEFAULT_CONTENT
        defaultListingShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the listingList where content contains UPDATED_CONTENT
        defaultListingShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllListingsByContentNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where content does not contain DEFAULT_CONTENT
        defaultListingShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the listingList where content does not contain UPDATED_CONTENT
        defaultListingShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllListingsByThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where thumbnail equals to DEFAULT_THUMBNAIL
        defaultListingShouldBeFound("thumbnail.equals=" + DEFAULT_THUMBNAIL);

        // Get all the listingList where thumbnail equals to UPDATED_THUMBNAIL
        defaultListingShouldNotBeFound("thumbnail.equals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingsByThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where thumbnail not equals to DEFAULT_THUMBNAIL
        defaultListingShouldNotBeFound("thumbnail.notEquals=" + DEFAULT_THUMBNAIL);

        // Get all the listingList where thumbnail not equals to UPDATED_THUMBNAIL
        defaultListingShouldBeFound("thumbnail.notEquals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingsByThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where thumbnail in DEFAULT_THUMBNAIL or UPDATED_THUMBNAIL
        defaultListingShouldBeFound("thumbnail.in=" + DEFAULT_THUMBNAIL + "," + UPDATED_THUMBNAIL);

        // Get all the listingList where thumbnail equals to UPDATED_THUMBNAIL
        defaultListingShouldNotBeFound("thumbnail.in=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingsByThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where thumbnail is not null
        defaultListingShouldBeFound("thumbnail.specified=true");

        // Get all the listingList where thumbnail is null
        defaultListingShouldNotBeFound("thumbnail.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByThumbnailContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where thumbnail contains DEFAULT_THUMBNAIL
        defaultListingShouldBeFound("thumbnail.contains=" + DEFAULT_THUMBNAIL);

        // Get all the listingList where thumbnail contains UPDATED_THUMBNAIL
        defaultListingShouldNotBeFound("thumbnail.contains=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingsByThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where thumbnail does not contain DEFAULT_THUMBNAIL
        defaultListingShouldNotBeFound("thumbnail.doesNotContain=" + DEFAULT_THUMBNAIL);

        // Get all the listingList where thumbnail does not contain UPDATED_THUMBNAIL
        defaultListingShouldBeFound("thumbnail.doesNotContain=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllListingsByIsFeaturedIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where isFeatured equals to DEFAULT_IS_FEATURED
        defaultListingShouldBeFound("isFeatured.equals=" + DEFAULT_IS_FEATURED);

        // Get all the listingList where isFeatured equals to UPDATED_IS_FEATURED
        defaultListingShouldNotBeFound("isFeatured.equals=" + UPDATED_IS_FEATURED);
    }

    @Test
    @Transactional
    void getAllListingsByIsFeaturedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where isFeatured not equals to DEFAULT_IS_FEATURED
        defaultListingShouldNotBeFound("isFeatured.notEquals=" + DEFAULT_IS_FEATURED);

        // Get all the listingList where isFeatured not equals to UPDATED_IS_FEATURED
        defaultListingShouldBeFound("isFeatured.notEquals=" + UPDATED_IS_FEATURED);
    }

    @Test
    @Transactional
    void getAllListingsByIsFeaturedIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where isFeatured in DEFAULT_IS_FEATURED or UPDATED_IS_FEATURED
        defaultListingShouldBeFound("isFeatured.in=" + DEFAULT_IS_FEATURED + "," + UPDATED_IS_FEATURED);

        // Get all the listingList where isFeatured equals to UPDATED_IS_FEATURED
        defaultListingShouldNotBeFound("isFeatured.in=" + UPDATED_IS_FEATURED);
    }

    @Test
    @Transactional
    void getAllListingsByIsFeaturedIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where isFeatured is not null
        defaultListingShouldBeFound("isFeatured.specified=true");

        // Get all the listingList where isFeatured is null
        defaultListingShouldNotBeFound("isFeatured.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson equals to DEFAULT_PRICE_PER_PERSON
        defaultListingShouldBeFound("pricePerPerson.equals=" + DEFAULT_PRICE_PER_PERSON);

        // Get all the listingList where pricePerPerson equals to UPDATED_PRICE_PER_PERSON
        defaultListingShouldNotBeFound("pricePerPerson.equals=" + UPDATED_PRICE_PER_PERSON);
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson not equals to DEFAULT_PRICE_PER_PERSON
        defaultListingShouldNotBeFound("pricePerPerson.notEquals=" + DEFAULT_PRICE_PER_PERSON);

        // Get all the listingList where pricePerPerson not equals to UPDATED_PRICE_PER_PERSON
        defaultListingShouldBeFound("pricePerPerson.notEquals=" + UPDATED_PRICE_PER_PERSON);
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson in DEFAULT_PRICE_PER_PERSON or UPDATED_PRICE_PER_PERSON
        defaultListingShouldBeFound("pricePerPerson.in=" + DEFAULT_PRICE_PER_PERSON + "," + UPDATED_PRICE_PER_PERSON);

        // Get all the listingList where pricePerPerson equals to UPDATED_PRICE_PER_PERSON
        defaultListingShouldNotBeFound("pricePerPerson.in=" + UPDATED_PRICE_PER_PERSON);
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson is not null
        defaultListingShouldBeFound("pricePerPerson.specified=true");

        // Get all the listingList where pricePerPerson is null
        defaultListingShouldNotBeFound("pricePerPerson.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson is greater than or equal to DEFAULT_PRICE_PER_PERSON
        defaultListingShouldBeFound("pricePerPerson.greaterThanOrEqual=" + DEFAULT_PRICE_PER_PERSON);

        // Get all the listingList where pricePerPerson is greater than or equal to UPDATED_PRICE_PER_PERSON
        defaultListingShouldNotBeFound("pricePerPerson.greaterThanOrEqual=" + UPDATED_PRICE_PER_PERSON);
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson is less than or equal to DEFAULT_PRICE_PER_PERSON
        defaultListingShouldBeFound("pricePerPerson.lessThanOrEqual=" + DEFAULT_PRICE_PER_PERSON);

        // Get all the listingList where pricePerPerson is less than or equal to SMALLER_PRICE_PER_PERSON
        defaultListingShouldNotBeFound("pricePerPerson.lessThanOrEqual=" + SMALLER_PRICE_PER_PERSON);
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsLessThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson is less than DEFAULT_PRICE_PER_PERSON
        defaultListingShouldNotBeFound("pricePerPerson.lessThan=" + DEFAULT_PRICE_PER_PERSON);

        // Get all the listingList where pricePerPerson is less than UPDATED_PRICE_PER_PERSON
        defaultListingShouldBeFound("pricePerPerson.lessThan=" + UPDATED_PRICE_PER_PERSON);
    }

    @Test
    @Transactional
    void getAllListingsByPricePerPersonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where pricePerPerson is greater than DEFAULT_PRICE_PER_PERSON
        defaultListingShouldNotBeFound("pricePerPerson.greaterThan=" + DEFAULT_PRICE_PER_PERSON);

        // Get all the listingList where pricePerPerson is greater than SMALLER_PRICE_PER_PERSON
        defaultListingShouldBeFound("pricePerPerson.greaterThan=" + SMALLER_PRICE_PER_PERSON);
    }

    @Test
    @Transactional
    void getAllListingsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where phone equals to DEFAULT_PHONE
        defaultListingShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the listingList where phone equals to UPDATED_PHONE
        defaultListingShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllListingsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where phone not equals to DEFAULT_PHONE
        defaultListingShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the listingList where phone not equals to UPDATED_PHONE
        defaultListingShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllListingsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultListingShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the listingList where phone equals to UPDATED_PHONE
        defaultListingShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllListingsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where phone is not null
        defaultListingShouldBeFound("phone.specified=true");

        // Get all the listingList where phone is null
        defaultListingShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where phone contains DEFAULT_PHONE
        defaultListingShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the listingList where phone contains UPDATED_PHONE
        defaultListingShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllListingsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where phone does not contain DEFAULT_PHONE
        defaultListingShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the listingList where phone does not contain UPDATED_PHONE
        defaultListingShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllListingsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where email equals to DEFAULT_EMAIL
        defaultListingShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the listingList where email equals to UPDATED_EMAIL
        defaultListingShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllListingsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where email not equals to DEFAULT_EMAIL
        defaultListingShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the listingList where email not equals to UPDATED_EMAIL
        defaultListingShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllListingsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultListingShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the listingList where email equals to UPDATED_EMAIL
        defaultListingShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllListingsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where email is not null
        defaultListingShouldBeFound("email.specified=true");

        // Get all the listingList where email is null
        defaultListingShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByEmailContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where email contains DEFAULT_EMAIL
        defaultListingShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the listingList where email contains UPDATED_EMAIL
        defaultListingShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllListingsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where email does not contain DEFAULT_EMAIL
        defaultListingShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the listingList where email does not contain UPDATED_EMAIL
        defaultListingShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllListingsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where website equals to DEFAULT_WEBSITE
        defaultListingShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the listingList where website equals to UPDATED_WEBSITE
        defaultListingShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllListingsByWebsiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where website not equals to DEFAULT_WEBSITE
        defaultListingShouldNotBeFound("website.notEquals=" + DEFAULT_WEBSITE);

        // Get all the listingList where website not equals to UPDATED_WEBSITE
        defaultListingShouldBeFound("website.notEquals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllListingsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultListingShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the listingList where website equals to UPDATED_WEBSITE
        defaultListingShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllListingsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where website is not null
        defaultListingShouldBeFound("website.specified=true");

        // Get all the listingList where website is null
        defaultListingShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where website contains DEFAULT_WEBSITE
        defaultListingShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the listingList where website contains UPDATED_WEBSITE
        defaultListingShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllListingsByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where website does not contain DEFAULT_WEBSITE
        defaultListingShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the listingList where website does not contain UPDATED_WEBSITE
        defaultListingShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllListingsByDisableBookingIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where disableBooking equals to DEFAULT_DISABLE_BOOKING
        defaultListingShouldBeFound("disableBooking.equals=" + DEFAULT_DISABLE_BOOKING);

        // Get all the listingList where disableBooking equals to UPDATED_DISABLE_BOOKING
        defaultListingShouldNotBeFound("disableBooking.equals=" + UPDATED_DISABLE_BOOKING);
    }

    @Test
    @Transactional
    void getAllListingsByDisableBookingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where disableBooking not equals to DEFAULT_DISABLE_BOOKING
        defaultListingShouldNotBeFound("disableBooking.notEquals=" + DEFAULT_DISABLE_BOOKING);

        // Get all the listingList where disableBooking not equals to UPDATED_DISABLE_BOOKING
        defaultListingShouldBeFound("disableBooking.notEquals=" + UPDATED_DISABLE_BOOKING);
    }

    @Test
    @Transactional
    void getAllListingsByDisableBookingIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where disableBooking in DEFAULT_DISABLE_BOOKING or UPDATED_DISABLE_BOOKING
        defaultListingShouldBeFound("disableBooking.in=" + DEFAULT_DISABLE_BOOKING + "," + UPDATED_DISABLE_BOOKING);

        // Get all the listingList where disableBooking equals to UPDATED_DISABLE_BOOKING
        defaultListingShouldNotBeFound("disableBooking.in=" + UPDATED_DISABLE_BOOKING);
    }

    @Test
    @Transactional
    void getAllListingsByDisableBookingIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where disableBooking is not null
        defaultListingShouldBeFound("disableBooking.specified=true");

        // Get all the listingList where disableBooking is null
        defaultListingShouldNotBeFound("disableBooking.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount equals to DEFAULT_VIEW_COUNT
        defaultListingShouldBeFound("viewCount.equals=" + DEFAULT_VIEW_COUNT);

        // Get all the listingList where viewCount equals to UPDATED_VIEW_COUNT
        defaultListingShouldNotBeFound("viewCount.equals=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount not equals to DEFAULT_VIEW_COUNT
        defaultListingShouldNotBeFound("viewCount.notEquals=" + DEFAULT_VIEW_COUNT);

        // Get all the listingList where viewCount not equals to UPDATED_VIEW_COUNT
        defaultListingShouldBeFound("viewCount.notEquals=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount in DEFAULT_VIEW_COUNT or UPDATED_VIEW_COUNT
        defaultListingShouldBeFound("viewCount.in=" + DEFAULT_VIEW_COUNT + "," + UPDATED_VIEW_COUNT);

        // Get all the listingList where viewCount equals to UPDATED_VIEW_COUNT
        defaultListingShouldNotBeFound("viewCount.in=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount is not null
        defaultListingShouldBeFound("viewCount.specified=true");

        // Get all the listingList where viewCount is null
        defaultListingShouldNotBeFound("viewCount.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount is greater than or equal to DEFAULT_VIEW_COUNT
        defaultListingShouldBeFound("viewCount.greaterThanOrEqual=" + DEFAULT_VIEW_COUNT);

        // Get all the listingList where viewCount is greater than or equal to UPDATED_VIEW_COUNT
        defaultListingShouldNotBeFound("viewCount.greaterThanOrEqual=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount is less than or equal to DEFAULT_VIEW_COUNT
        defaultListingShouldBeFound("viewCount.lessThanOrEqual=" + DEFAULT_VIEW_COUNT);

        // Get all the listingList where viewCount is less than or equal to SMALLER_VIEW_COUNT
        defaultListingShouldNotBeFound("viewCount.lessThanOrEqual=" + SMALLER_VIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsLessThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount is less than DEFAULT_VIEW_COUNT
        defaultListingShouldNotBeFound("viewCount.lessThan=" + DEFAULT_VIEW_COUNT);

        // Get all the listingList where viewCount is less than UPDATED_VIEW_COUNT
        defaultListingShouldBeFound("viewCount.lessThan=" + UPDATED_VIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllListingsByViewCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where viewCount is greater than DEFAULT_VIEW_COUNT
        defaultListingShouldNotBeFound("viewCount.greaterThan=" + DEFAULT_VIEW_COUNT);

        // Get all the listingList where viewCount is greater than SMALLER_VIEW_COUNT
        defaultListingShouldBeFound("viewCount.greaterThan=" + SMALLER_VIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdBy equals to DEFAULT_CREATED_BY
        defaultListingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the listingList where createdBy equals to UPDATED_CREATED_BY
        defaultListingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdBy not equals to DEFAULT_CREATED_BY
        defaultListingShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the listingList where createdBy not equals to UPDATED_CREATED_BY
        defaultListingShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultListingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the listingList where createdBy equals to UPDATED_CREATED_BY
        defaultListingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdBy is not null
        defaultListingShouldBeFound("createdBy.specified=true");

        // Get all the listingList where createdBy is null
        defaultListingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdBy contains DEFAULT_CREATED_BY
        defaultListingShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the listingList where createdBy contains UPDATED_CREATED_BY
        defaultListingShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdBy does not contain DEFAULT_CREATED_BY
        defaultListingShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the listingList where createdBy does not contain UPDATED_CREATED_BY
        defaultListingShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdDate equals to DEFAULT_CREATED_DATE
        defaultListingShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the listingList where createdDate equals to UPDATED_CREATED_DATE
        defaultListingShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultListingShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the listingList where createdDate not equals to UPDATED_CREATED_DATE
        defaultListingShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultListingShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the listingList where createdDate equals to UPDATED_CREATED_DATE
        defaultListingShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllListingsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where createdDate is not null
        defaultListingShouldBeFound("createdDate.specified=true");

        // Get all the listingList where createdDate is null
        defaultListingShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultListingShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the listingList where updatedBy equals to UPDATED_UPDATED_BY
        defaultListingShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultListingShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the listingList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultListingShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultListingShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the listingList where updatedBy equals to UPDATED_UPDATED_BY
        defaultListingShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllListingsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updatedBy is not null
        defaultListingShouldBeFound("updatedBy.specified=true");

        // Get all the listingList where updatedBy is null
        defaultListingShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultListingShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the listingList where updateDate equals to UPDATED_UPDATE_DATE
        defaultListingShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllListingsByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultListingShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the listingList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultListingShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllListingsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultListingShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the listingList where updateDate equals to UPDATED_UPDATE_DATE
        defaultListingShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllListingsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where updateDate is not null
        defaultListingShouldBeFound("updateDate.specified=true");

        // Get all the listingList where updateDate is null
        defaultListingShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllListingsByListingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);
        ListingType listingType = ListingTypeResourceIT.createEntity(em);
        em.persist(listingType);
        em.flush();
        listing.setListingType(listingType);
        listingRepository.saveAndFlush(listing);
        Long listingTypeId = listingType.getId();

        // Get all the listingList where listingType equals to listingTypeId
        defaultListingShouldBeFound("listingTypeId.equals=" + listingTypeId);

        // Get all the listingList where listingType equals to (listingTypeId + 1)
        defaultListingShouldNotBeFound("listingTypeId.equals=" + (listingTypeId + 1));
    }

    @Test
    @Transactional
    void getAllListingsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        listing.setLocation(location);
        listingRepository.saveAndFlush(listing);
        Long locationId = location.getId();

        // Get all the listingList where location equals to locationId
        defaultListingShouldBeFound("locationId.equals=" + locationId);

        // Get all the listingList where location equals to (locationId + 1)
        defaultListingShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    @Test
    @Transactional
    void getAllListingsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);
        User owner = UserResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        listing.setOwner(owner);
        listingRepository.saveAndFlush(listing);
        Long ownerId = owner.getId();

        // Get all the listingList where owner equals to ownerId
        defaultListingShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the listingList where owner equals to (ownerId + 1)
        defaultListingShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultListingShouldBeFound(String filter) throws Exception {
        restListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listing.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].isFeatured").value(hasItem(DEFAULT_IS_FEATURED.booleanValue())))
            .andExpect(jsonPath("$.[*].pricePerPerson").value(hasItem(DEFAULT_PRICE_PER_PERSON.doubleValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].disableBooking").value(hasItem(DEFAULT_DISABLE_BOOKING.booleanValue())))
            .andExpect(jsonPath("$.[*].viewCount").value(hasItem(DEFAULT_VIEW_COUNT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restListingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultListingShouldNotBeFound(String filter) throws Exception {
        restListingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restListingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingListing() throws Exception {
        // Get the listing
        restListingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListing() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        int databaseSizeBeforeUpdate = listingRepository.findAll().size();

        // Update the listing
        Listing updatedListing = listingRepository.findById(listing.getId()).get();
        // Disconnect from session so that the updates on updatedListing are not directly saved in db
        em.detach(updatedListing);
        updatedListing
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .url(UPDATED_URL)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .thumbnail(UPDATED_THUMBNAIL)
            .isFeatured(UPDATED_IS_FEATURED)
            .pricePerPerson(UPDATED_PRICE_PER_PERSON)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .comment(UPDATED_COMMENT)
            .disableBooking(UPDATED_DISABLE_BOOKING)
            .viewCount(UPDATED_VIEW_COUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListing))
            )
            .andExpect(status().isOk());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testListing.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testListing.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testListing.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testListing.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testListing.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testListing.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testListing.getIsFeatured()).isEqualTo(UPDATED_IS_FEATURED);
        assertThat(testListing.getPricePerPerson()).isEqualTo(UPDATED_PRICE_PER_PERSON);
        assertThat(testListing.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testListing.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testListing.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testListing.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testListing.getDisableBooking()).isEqualTo(UPDATED_DISABLE_BOOKING);
        assertThat(testListing.getViewCount()).isEqualTo(UPDATED_VIEW_COUNT);
        assertThat(testListing.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testListing.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testListing.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testListing.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();
        listing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();
        listing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();
        listing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListingWithPatch() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        int databaseSizeBeforeUpdate = listingRepository.findAll().size();

        // Update the listing using partial update
        Listing partialUpdatedListing = new Listing();
        partialUpdatedListing.setId(listing.getId());

        partialUpdatedListing
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .comment(UPDATED_COMMENT)
            .disableBooking(UPDATED_DISABLE_BOOKING)
            .createdBy(UPDATED_CREATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListing))
            )
            .andExpect(status().isOk());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testListing.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testListing.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testListing.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testListing.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testListing.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testListing.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testListing.getIsFeatured()).isEqualTo(DEFAULT_IS_FEATURED);
        assertThat(testListing.getPricePerPerson()).isEqualTo(DEFAULT_PRICE_PER_PERSON);
        assertThat(testListing.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testListing.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testListing.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testListing.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testListing.getDisableBooking()).isEqualTo(UPDATED_DISABLE_BOOKING);
        assertThat(testListing.getViewCount()).isEqualTo(DEFAULT_VIEW_COUNT);
        assertThat(testListing.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testListing.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testListing.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testListing.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateListingWithPatch() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        int databaseSizeBeforeUpdate = listingRepository.findAll().size();

        // Update the listing using partial update
        Listing partialUpdatedListing = new Listing();
        partialUpdatedListing.setId(listing.getId());

        partialUpdatedListing
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .url(UPDATED_URL)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .thumbnail(UPDATED_THUMBNAIL)
            .isFeatured(UPDATED_IS_FEATURED)
            .pricePerPerson(UPDATED_PRICE_PER_PERSON)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .comment(UPDATED_COMMENT)
            .disableBooking(UPDATED_DISABLE_BOOKING)
            .viewCount(UPDATED_VIEW_COUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateDate(UPDATED_UPDATE_DATE);

        restListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListing))
            )
            .andExpect(status().isOk());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testListing.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testListing.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testListing.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testListing.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testListing.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testListing.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testListing.getIsFeatured()).isEqualTo(UPDATED_IS_FEATURED);
        assertThat(testListing.getPricePerPerson()).isEqualTo(UPDATED_PRICE_PER_PERSON);
        assertThat(testListing.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testListing.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testListing.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testListing.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testListing.getDisableBooking()).isEqualTo(UPDATED_DISABLE_BOOKING);
        assertThat(testListing.getViewCount()).isEqualTo(UPDATED_VIEW_COUNT);
        assertThat(testListing.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testListing.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testListing.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testListing.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();
        listing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();
        listing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();
        listing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListing() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        int databaseSizeBeforeDelete = listingRepository.findAll().size();

        // Delete the listing
        restListingMockMvc
            .perform(delete(ENTITY_API_URL_ID, listing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
