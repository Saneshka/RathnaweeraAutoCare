package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutocareappointmentAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autocareappointment;
import com.heavenscode.rac.repository.AutocareappointmentRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AutocareappointmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutocareappointmentResourceIT {

    private static final Integer DEFAULT_APPOINTMENTTYPE = 1;
    private static final Integer UPDATED_APPOINTMENTTYPE = 2;

    private static final Instant DEFAULT_APPOINTMENTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPOINTMENTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ADDEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONFORMDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFORMDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_APPOINTMENTNUMBER = 1;
    private static final Integer UPDATED_APPOINTMENTNUMBER = 2;

    private static final String DEFAULT_VEHICLENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLENUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPOINTMENTTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPOINTMENTTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISCONFORMED = false;
    private static final Boolean UPDATED_ISCONFORMED = true;

    private static final Integer DEFAULT_CONFORMEDBY = 1;
    private static final Integer UPDATED_CONFORMEDBY = 2;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;

    private static final Integer DEFAULT_CUSTOMERID = 1;
    private static final Integer UPDATED_CUSTOMERID = 2;

    private static final String DEFAULT_CONTACTNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERNAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERNAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISSUED = false;
    private static final Boolean UPDATED_ISSUED = true;

    private static final Integer DEFAULT_HOISTID = 1;
    private static final Integer UPDATED_HOISTID = 2;

    private static final Boolean DEFAULT_ISARRIVED = false;
    private static final Boolean UPDATED_ISARRIVED = true;

    private static final Boolean DEFAULT_ISCANCEL = false;
    private static final Boolean UPDATED_ISCANCEL = true;

    private static final Boolean DEFAULT_ISNOANSWER = false;
    private static final Boolean UPDATED_ISNOANSWER = true;

    private static final String DEFAULT_MISSEDAPPOINTMENTCALL = "AAAAAAAAAA";
    private static final String UPDATED_MISSEDAPPOINTMENTCALL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMERMOBILEID = 1;
    private static final Integer UPDATED_CUSTOMERMOBILEID = 2;

    private static final Integer DEFAULT_CUSTOMERMOBILEVEHICLEID = 1;
    private static final Integer UPDATED_CUSTOMERMOBILEVEHICLEID = 2;

    private static final Integer DEFAULT_VEHICLEID = 1;
    private static final Integer UPDATED_VEHICLEID = 2;

    private static final Boolean DEFAULT_ISMOBILEAPPOINTMENT = false;
    private static final Boolean UPDATED_ISMOBILEAPPOINTMENT = true;

    private static final Float DEFAULT_ADVANCEPAYMENT = 1F;
    private static final Float UPDATED_ADVANCEPAYMENT = 2F;

    private static final Integer DEFAULT_JOBID = 1;
    private static final Integer UPDATED_JOBID = 2;

    private static final String ENTITY_API_URL = "/api/autocareappointments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutocareappointmentRepository autocareappointmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutocareappointmentMockMvc;

    private Autocareappointment autocareappointment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autocareappointment createEntity(EntityManager em) {
        Autocareappointment autocareappointment = new Autocareappointment()
            .appointmenttype(DEFAULT_APPOINTMENTTYPE)
            .appointmentdate(DEFAULT_APPOINTMENTDATE)
            .addeddate(DEFAULT_ADDEDDATE)
            .conformdate(DEFAULT_CONFORMDATE)
            .appointmentnumber(DEFAULT_APPOINTMENTNUMBER)
            .vehiclenumber(DEFAULT_VEHICLENUMBER)
            .appointmenttime(DEFAULT_APPOINTMENTTIME)
            .isconformed(DEFAULT_ISCONFORMED)
            .conformedby(DEFAULT_CONFORMEDBY)
            .lmd(DEFAULT_LMD)
            .lmu(DEFAULT_LMU)
            .customerid(DEFAULT_CUSTOMERID)
            .contactnumber(DEFAULT_CONTACTNUMBER)
            .customername(DEFAULT_CUSTOMERNAME)
            .issued(DEFAULT_ISSUED)
            .hoistid(DEFAULT_HOISTID)
            .isarrived(DEFAULT_ISARRIVED)
            .iscancel(DEFAULT_ISCANCEL)
            .isnoanswer(DEFAULT_ISNOANSWER)
            .missedappointmentcall(DEFAULT_MISSEDAPPOINTMENTCALL)
            .customermobileid(DEFAULT_CUSTOMERMOBILEID)
            .customermobilevehicleid(DEFAULT_CUSTOMERMOBILEVEHICLEID)
            .vehicleid(DEFAULT_VEHICLEID)
            .ismobileappointment(DEFAULT_ISMOBILEAPPOINTMENT)
            .advancepayment(DEFAULT_ADVANCEPAYMENT)
            .jobid(DEFAULT_JOBID);
        return autocareappointment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autocareappointment createUpdatedEntity(EntityManager em) {
        Autocareappointment autocareappointment = new Autocareappointment()
            .appointmenttype(UPDATED_APPOINTMENTTYPE)
            .appointmentdate(UPDATED_APPOINTMENTDATE)
            .addeddate(UPDATED_ADDEDDATE)
            .conformdate(UPDATED_CONFORMDATE)
            .appointmentnumber(UPDATED_APPOINTMENTNUMBER)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .appointmenttime(UPDATED_APPOINTMENTTIME)
            .isconformed(UPDATED_ISCONFORMED)
            .conformedby(UPDATED_CONFORMEDBY)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU)
            .customerid(UPDATED_CUSTOMERID)
            .contactnumber(UPDATED_CONTACTNUMBER)
            .customername(UPDATED_CUSTOMERNAME)
            .issued(UPDATED_ISSUED)
            .hoistid(UPDATED_HOISTID)
            .isarrived(UPDATED_ISARRIVED)
            .iscancel(UPDATED_ISCANCEL)
            .isnoanswer(UPDATED_ISNOANSWER)
            .missedappointmentcall(UPDATED_MISSEDAPPOINTMENTCALL)
            .customermobileid(UPDATED_CUSTOMERMOBILEID)
            .customermobilevehicleid(UPDATED_CUSTOMERMOBILEVEHICLEID)
            .vehicleid(UPDATED_VEHICLEID)
            .ismobileappointment(UPDATED_ISMOBILEAPPOINTMENT)
            .advancepayment(UPDATED_ADVANCEPAYMENT)
            .jobid(UPDATED_JOBID);
        return autocareappointment;
    }

    @BeforeEach
    public void initTest() {
        autocareappointment = createEntity(em);
    }

    @Test
    @Transactional
    void createAutocareappointment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autocareappointment
        var returnedAutocareappointment = om.readValue(
            restAutocareappointmentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autocareappointment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autocareappointment.class
        );

        // Validate the Autocareappointment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutocareappointmentUpdatableFieldsEquals(
            returnedAutocareappointment,
            getPersistedAutocareappointment(returnedAutocareappointment)
        );
    }

    @Test
    @Transactional
    void createAutocareappointmentWithExistingId() throws Exception {
        // Create the Autocareappointment with an existing ID
        autocareappointment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutocareappointmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autocareappointment)))
            .andExpect(status().isBadRequest());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutocareappointments() throws Exception {
        // Initialize the database
        autocareappointmentRepository.saveAndFlush(autocareappointment);

        // Get all the autocareappointmentList
        restAutocareappointmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autocareappointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].appointmenttype").value(hasItem(DEFAULT_APPOINTMENTTYPE)))
            .andExpect(jsonPath("$.[*].appointmentdate").value(hasItem(DEFAULT_APPOINTMENTDATE.toString())))
            .andExpect(jsonPath("$.[*].addeddate").value(hasItem(DEFAULT_ADDEDDATE.toString())))
            .andExpect(jsonPath("$.[*].conformdate").value(hasItem(DEFAULT_CONFORMDATE.toString())))
            .andExpect(jsonPath("$.[*].appointmentnumber").value(hasItem(DEFAULT_APPOINTMENTNUMBER)))
            .andExpect(jsonPath("$.[*].vehiclenumber").value(hasItem(DEFAULT_VEHICLENUMBER)))
            .andExpect(jsonPath("$.[*].appointmenttime").value(hasItem(DEFAULT_APPOINTMENTTIME.toString())))
            .andExpect(jsonPath("$.[*].isconformed").value(hasItem(DEFAULT_ISCONFORMED.booleanValue())))
            .andExpect(jsonPath("$.[*].conformedby").value(hasItem(DEFAULT_CONFORMEDBY)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].contactnumber").value(hasItem(DEFAULT_CONTACTNUMBER)))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME)))
            .andExpect(jsonPath("$.[*].issued").value(hasItem(DEFAULT_ISSUED.booleanValue())))
            .andExpect(jsonPath("$.[*].hoistid").value(hasItem(DEFAULT_HOISTID)))
            .andExpect(jsonPath("$.[*].isarrived").value(hasItem(DEFAULT_ISARRIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].iscancel").value(hasItem(DEFAULT_ISCANCEL.booleanValue())))
            .andExpect(jsonPath("$.[*].isnoanswer").value(hasItem(DEFAULT_ISNOANSWER.booleanValue())))
            .andExpect(jsonPath("$.[*].missedappointmentcall").value(hasItem(DEFAULT_MISSEDAPPOINTMENTCALL)))
            .andExpect(jsonPath("$.[*].customermobileid").value(hasItem(DEFAULT_CUSTOMERMOBILEID)))
            .andExpect(jsonPath("$.[*].customermobilevehicleid").value(hasItem(DEFAULT_CUSTOMERMOBILEVEHICLEID)))
            .andExpect(jsonPath("$.[*].vehicleid").value(hasItem(DEFAULT_VEHICLEID)))
            .andExpect(jsonPath("$.[*].ismobileappointment").value(hasItem(DEFAULT_ISMOBILEAPPOINTMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].advancepayment").value(hasItem(DEFAULT_ADVANCEPAYMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].jobid").value(hasItem(DEFAULT_JOBID)));
    }

    @Test
    @Transactional
    void getAutocareappointment() throws Exception {
        // Initialize the database
        autocareappointmentRepository.saveAndFlush(autocareappointment);

        // Get the autocareappointment
        restAutocareappointmentMockMvc
            .perform(get(ENTITY_API_URL_ID, autocareappointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autocareappointment.getId().intValue()))
            .andExpect(jsonPath("$.appointmenttype").value(DEFAULT_APPOINTMENTTYPE))
            .andExpect(jsonPath("$.appointmentdate").value(DEFAULT_APPOINTMENTDATE.toString()))
            .andExpect(jsonPath("$.addeddate").value(DEFAULT_ADDEDDATE.toString()))
            .andExpect(jsonPath("$.conformdate").value(DEFAULT_CONFORMDATE.toString()))
            .andExpect(jsonPath("$.appointmentnumber").value(DEFAULT_APPOINTMENTNUMBER))
            .andExpect(jsonPath("$.vehiclenumber").value(DEFAULT_VEHICLENUMBER))
            .andExpect(jsonPath("$.appointmenttime").value(DEFAULT_APPOINTMENTTIME.toString()))
            .andExpect(jsonPath("$.isconformed").value(DEFAULT_ISCONFORMED.booleanValue()))
            .andExpect(jsonPath("$.conformedby").value(DEFAULT_CONFORMEDBY))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.customerid").value(DEFAULT_CUSTOMERID))
            .andExpect(jsonPath("$.contactnumber").value(DEFAULT_CONTACTNUMBER))
            .andExpect(jsonPath("$.customername").value(DEFAULT_CUSTOMERNAME))
            .andExpect(jsonPath("$.issued").value(DEFAULT_ISSUED.booleanValue()))
            .andExpect(jsonPath("$.hoistid").value(DEFAULT_HOISTID))
            .andExpect(jsonPath("$.isarrived").value(DEFAULT_ISARRIVED.booleanValue()))
            .andExpect(jsonPath("$.iscancel").value(DEFAULT_ISCANCEL.booleanValue()))
            .andExpect(jsonPath("$.isnoanswer").value(DEFAULT_ISNOANSWER.booleanValue()))
            .andExpect(jsonPath("$.missedappointmentcall").value(DEFAULT_MISSEDAPPOINTMENTCALL))
            .andExpect(jsonPath("$.customermobileid").value(DEFAULT_CUSTOMERMOBILEID))
            .andExpect(jsonPath("$.customermobilevehicleid").value(DEFAULT_CUSTOMERMOBILEVEHICLEID))
            .andExpect(jsonPath("$.vehicleid").value(DEFAULT_VEHICLEID))
            .andExpect(jsonPath("$.ismobileappointment").value(DEFAULT_ISMOBILEAPPOINTMENT.booleanValue()))
            .andExpect(jsonPath("$.advancepayment").value(DEFAULT_ADVANCEPAYMENT.doubleValue()))
            .andExpect(jsonPath("$.jobid").value(DEFAULT_JOBID));
    }

    @Test
    @Transactional
    void getNonExistingAutocareappointment() throws Exception {
        // Get the autocareappointment
        restAutocareappointmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutocareappointment() throws Exception {
        // Initialize the database
        autocareappointmentRepository.saveAndFlush(autocareappointment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autocareappointment
        Autocareappointment updatedAutocareappointment = autocareappointmentRepository.findById(autocareappointment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAutocareappointment are not directly saved in db
        em.detach(updatedAutocareappointment);
        updatedAutocareappointment
            .appointmenttype(UPDATED_APPOINTMENTTYPE)
            .appointmentdate(UPDATED_APPOINTMENTDATE)
            .addeddate(UPDATED_ADDEDDATE)
            .conformdate(UPDATED_CONFORMDATE)
            .appointmentnumber(UPDATED_APPOINTMENTNUMBER)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .appointmenttime(UPDATED_APPOINTMENTTIME)
            .isconformed(UPDATED_ISCONFORMED)
            .conformedby(UPDATED_CONFORMEDBY)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU)
            .customerid(UPDATED_CUSTOMERID)
            .contactnumber(UPDATED_CONTACTNUMBER)
            .customername(UPDATED_CUSTOMERNAME)
            .issued(UPDATED_ISSUED)
            .hoistid(UPDATED_HOISTID)
            .isarrived(UPDATED_ISARRIVED)
            .iscancel(UPDATED_ISCANCEL)
            .isnoanswer(UPDATED_ISNOANSWER)
            .missedappointmentcall(UPDATED_MISSEDAPPOINTMENTCALL)
            .customermobileid(UPDATED_CUSTOMERMOBILEID)
            .customermobilevehicleid(UPDATED_CUSTOMERMOBILEVEHICLEID)
            .vehicleid(UPDATED_VEHICLEID)
            .ismobileappointment(UPDATED_ISMOBILEAPPOINTMENT)
            .advancepayment(UPDATED_ADVANCEPAYMENT)
            .jobid(UPDATED_JOBID);

        restAutocareappointmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutocareappointment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutocareappointment))
            )
            .andExpect(status().isOk());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutocareappointmentToMatchAllProperties(updatedAutocareappointment);
    }

    @Test
    @Transactional
    void putNonExistingAutocareappointment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocareappointment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutocareappointmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autocareappointment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autocareappointment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutocareappointment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocareappointment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocareappointmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autocareappointment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutocareappointment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocareappointment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocareappointmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autocareappointment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutocareappointmentWithPatch() throws Exception {
        // Initialize the database
        autocareappointmentRepository.saveAndFlush(autocareappointment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autocareappointment using partial update
        Autocareappointment partialUpdatedAutocareappointment = new Autocareappointment();
        partialUpdatedAutocareappointment.setId(autocareappointment.getId());

        partialUpdatedAutocareappointment
            .appointmentdate(UPDATED_APPOINTMENTDATE)
            .appointmentnumber(UPDATED_APPOINTMENTNUMBER)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .isconformed(UPDATED_ISCONFORMED)
            .lmd(UPDATED_LMD)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .hoistid(UPDATED_HOISTID)
            .iscancel(UPDATED_ISCANCEL)
            .isnoanswer(UPDATED_ISNOANSWER)
            .missedappointmentcall(UPDATED_MISSEDAPPOINTMENTCALL)
            .customermobileid(UPDATED_CUSTOMERMOBILEID)
            .jobid(UPDATED_JOBID);

        restAutocareappointmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutocareappointment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutocareappointment))
            )
            .andExpect(status().isOk());

        // Validate the Autocareappointment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutocareappointmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutocareappointment, autocareappointment),
            getPersistedAutocareappointment(autocareappointment)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutocareappointmentWithPatch() throws Exception {
        // Initialize the database
        autocareappointmentRepository.saveAndFlush(autocareappointment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autocareappointment using partial update
        Autocareappointment partialUpdatedAutocareappointment = new Autocareappointment();
        partialUpdatedAutocareappointment.setId(autocareappointment.getId());

        partialUpdatedAutocareappointment
            .appointmenttype(UPDATED_APPOINTMENTTYPE)
            .appointmentdate(UPDATED_APPOINTMENTDATE)
            .addeddate(UPDATED_ADDEDDATE)
            .conformdate(UPDATED_CONFORMDATE)
            .appointmentnumber(UPDATED_APPOINTMENTNUMBER)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .appointmenttime(UPDATED_APPOINTMENTTIME)
            .isconformed(UPDATED_ISCONFORMED)
            .conformedby(UPDATED_CONFORMEDBY)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU)
            .customerid(UPDATED_CUSTOMERID)
            .contactnumber(UPDATED_CONTACTNUMBER)
            .customername(UPDATED_CUSTOMERNAME)
            .issued(UPDATED_ISSUED)
            .hoistid(UPDATED_HOISTID)
            .isarrived(UPDATED_ISARRIVED)
            .iscancel(UPDATED_ISCANCEL)
            .isnoanswer(UPDATED_ISNOANSWER)
            .missedappointmentcall(UPDATED_MISSEDAPPOINTMENTCALL)
            .customermobileid(UPDATED_CUSTOMERMOBILEID)
            .customermobilevehicleid(UPDATED_CUSTOMERMOBILEVEHICLEID)
            .vehicleid(UPDATED_VEHICLEID)
            .ismobileappointment(UPDATED_ISMOBILEAPPOINTMENT)
            .advancepayment(UPDATED_ADVANCEPAYMENT)
            .jobid(UPDATED_JOBID);

        restAutocareappointmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutocareappointment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutocareappointment))
            )
            .andExpect(status().isOk());

        // Validate the Autocareappointment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutocareappointmentUpdatableFieldsEquals(
            partialUpdatedAutocareappointment,
            getPersistedAutocareappointment(partialUpdatedAutocareappointment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutocareappointment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocareappointment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutocareappointmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autocareappointment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autocareappointment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutocareappointment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocareappointment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocareappointmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autocareappointment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutocareappointment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autocareappointment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutocareappointmentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autocareappointment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autocareappointment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutocareappointment() throws Exception {
        // Initialize the database
        autocareappointmentRepository.saveAndFlush(autocareappointment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autocareappointment
        restAutocareappointmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, autocareappointment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autocareappointmentRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Autocareappointment getPersistedAutocareappointment(Autocareappointment autocareappointment) {
        return autocareappointmentRepository.findById(autocareappointment.getId()).orElseThrow();
    }

    protected void assertPersistedAutocareappointmentToMatchAllProperties(Autocareappointment expectedAutocareappointment) {
        assertAutocareappointmentAllPropertiesEquals(
            expectedAutocareappointment,
            getPersistedAutocareappointment(expectedAutocareappointment)
        );
    }

    protected void assertPersistedAutocareappointmentToMatchUpdatableProperties(Autocareappointment expectedAutocareappointment) {
        assertAutocareappointmentAllUpdatablePropertiesEquals(
            expectedAutocareappointment,
            getPersistedAutocareappointment(expectedAutocareappointment)
        );
    }
}
