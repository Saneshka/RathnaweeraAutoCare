package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.CustomervehicleAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Customervehicle;
import com.heavenscode.rac.repository.CustomervehicleRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CustomervehicleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomervehicleResourceIT {

    private static final Integer DEFAULT_CUSTOMERID = 1;
    private static final Integer UPDATED_CUSTOMERID = 2;

    private static final String DEFAULT_VEHICLENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLENUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATEGORYID = 1;
    private static final Integer UPDATED_CATEGORYID = 2;

    private static final String DEFAULT_CATEGORYNAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORYNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPEID = 1;
    private static final Integer UPDATED_TYPEID = 2;

    private static final String DEFAULT_TYPENAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPENAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAKEID = 1;
    private static final Integer UPDATED_MAKEID = 2;

    private static final String DEFAULT_MAKENAME = "AAAAAAAAAA";
    private static final String UPDATED_MAKENAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_YOM = "AAAAAAAAAA";
    private static final String UPDATED_YOM = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERCODE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SERVICECOUNT = 1;
    private static final Integer UPDATED_SERVICECOUNT = 2;

    private static final String DEFAULT_ENG_NO = "AAAAAAAAAA";
    private static final String UPDATED_ENG_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CHA_NO = "AAAAAAAAAA";
    private static final String UPDATED_CHA_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MILAGE = "AAAAAAAAAA";
    private static final String UPDATED_MILAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LASTSERVICEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LASTSERVICEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_NEXTSERVICEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXTSERVICEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NEXTGEAROILMILAGE = "AAAAAAAAAA";
    private static final String UPDATED_NEXTGEAROILMILAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NEXTMILAGE = "AAAAAAAAAA";
    private static final String UPDATED_NEXTMILAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SERVICEPERIOD = 1;
    private static final Integer UPDATED_SERVICEPERIOD = 2;

    private static final String ENTITY_API_URL = "/api/customervehicles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CustomervehicleRepository customervehicleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomervehicleMockMvc;

    private Customervehicle customervehicle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customervehicle createEntity(EntityManager em) {
        Customervehicle customervehicle = new Customervehicle()
            .customerid(DEFAULT_CUSTOMERID)
            .vehiclenumber(DEFAULT_VEHICLENUMBER)
            .categoryid(DEFAULT_CATEGORYID)
            .categoryname(DEFAULT_CATEGORYNAME)
            .typeid(DEFAULT_TYPEID)
            .typename(DEFAULT_TYPENAME)
            .makeid(DEFAULT_MAKEID)
            .makename(DEFAULT_MAKENAME)
            .model(DEFAULT_MODEL)
            .yom(DEFAULT_YOM)
            .customercode(DEFAULT_CUSTOMERCODE)
            .remarks(DEFAULT_REMARKS)
            .servicecount(DEFAULT_SERVICECOUNT)
            .engNo(DEFAULT_ENG_NO)
            .chaNo(DEFAULT_CHA_NO)
            .milage(DEFAULT_MILAGE)
            .lastservicedate(DEFAULT_LASTSERVICEDATE)
            .nextservicedate(DEFAULT_NEXTSERVICEDATE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .nextgearoilmilage(DEFAULT_NEXTGEAROILMILAGE)
            .nextmilage(DEFAULT_NEXTMILAGE)
            .serviceperiod(DEFAULT_SERVICEPERIOD);
        return customervehicle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customervehicle createUpdatedEntity(EntityManager em) {
        Customervehicle customervehicle = new Customervehicle()
            .customerid(UPDATED_CUSTOMERID)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .categoryid(UPDATED_CATEGORYID)
            .categoryname(UPDATED_CATEGORYNAME)
            .typeid(UPDATED_TYPEID)
            .typename(UPDATED_TYPENAME)
            .makeid(UPDATED_MAKEID)
            .makename(UPDATED_MAKENAME)
            .model(UPDATED_MODEL)
            .yom(UPDATED_YOM)
            .customercode(UPDATED_CUSTOMERCODE)
            .remarks(UPDATED_REMARKS)
            .servicecount(UPDATED_SERVICECOUNT)
            .engNo(UPDATED_ENG_NO)
            .chaNo(UPDATED_CHA_NO)
            .milage(UPDATED_MILAGE)
            .lastservicedate(UPDATED_LASTSERVICEDATE)
            .nextservicedate(UPDATED_NEXTSERVICEDATE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nextgearoilmilage(UPDATED_NEXTGEAROILMILAGE)
            .nextmilage(UPDATED_NEXTMILAGE)
            .serviceperiod(UPDATED_SERVICEPERIOD);
        return customervehicle;
    }

    @BeforeEach
    public void initTest() {
        customervehicle = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomervehicle() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Customervehicle
        var returnedCustomervehicle = om.readValue(
            restCustomervehicleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customervehicle)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Customervehicle.class
        );

        // Validate the Customervehicle in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCustomervehicleUpdatableFieldsEquals(returnedCustomervehicle, getPersistedCustomervehicle(returnedCustomervehicle));
    }

    @Test
    @Transactional
    void createCustomervehicleWithExistingId() throws Exception {
        // Create the Customervehicle with an existing ID
        customervehicle.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomervehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customervehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomervehicles() throws Exception {
        // Initialize the database
        customervehicleRepository.saveAndFlush(customervehicle);

        // Get all the customervehicleList
        restCustomervehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customervehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].vehiclenumber").value(hasItem(DEFAULT_VEHICLENUMBER)))
            .andExpect(jsonPath("$.[*].categoryid").value(hasItem(DEFAULT_CATEGORYID)))
            .andExpect(jsonPath("$.[*].categoryname").value(hasItem(DEFAULT_CATEGORYNAME)))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID)))
            .andExpect(jsonPath("$.[*].typename").value(hasItem(DEFAULT_TYPENAME)))
            .andExpect(jsonPath("$.[*].makeid").value(hasItem(DEFAULT_MAKEID)))
            .andExpect(jsonPath("$.[*].makename").value(hasItem(DEFAULT_MAKENAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].yom").value(hasItem(DEFAULT_YOM)))
            .andExpect(jsonPath("$.[*].customercode").value(hasItem(DEFAULT_CUSTOMERCODE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].servicecount").value(hasItem(DEFAULT_SERVICECOUNT)))
            .andExpect(jsonPath("$.[*].engNo").value(hasItem(DEFAULT_ENG_NO)))
            .andExpect(jsonPath("$.[*].chaNo").value(hasItem(DEFAULT_CHA_NO)))
            .andExpect(jsonPath("$.[*].milage").value(hasItem(DEFAULT_MILAGE)))
            .andExpect(jsonPath("$.[*].lastservicedate").value(hasItem(DEFAULT_LASTSERVICEDATE.toString())))
            .andExpect(jsonPath("$.[*].nextservicedate").value(hasItem(DEFAULT_NEXTSERVICEDATE.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nextgearoilmilage").value(hasItem(DEFAULT_NEXTGEAROILMILAGE)))
            .andExpect(jsonPath("$.[*].nextmilage").value(hasItem(DEFAULT_NEXTMILAGE)))
            .andExpect(jsonPath("$.[*].serviceperiod").value(hasItem(DEFAULT_SERVICEPERIOD)));
    }

    @Test
    @Transactional
    void getCustomervehicle() throws Exception {
        // Initialize the database
        customervehicleRepository.saveAndFlush(customervehicle);

        // Get the customervehicle
        restCustomervehicleMockMvc
            .perform(get(ENTITY_API_URL_ID, customervehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customervehicle.getId().intValue()))
            .andExpect(jsonPath("$.customerid").value(DEFAULT_CUSTOMERID))
            .andExpect(jsonPath("$.vehiclenumber").value(DEFAULT_VEHICLENUMBER))
            .andExpect(jsonPath("$.categoryid").value(DEFAULT_CATEGORYID))
            .andExpect(jsonPath("$.categoryname").value(DEFAULT_CATEGORYNAME))
            .andExpect(jsonPath("$.typeid").value(DEFAULT_TYPEID))
            .andExpect(jsonPath("$.typename").value(DEFAULT_TYPENAME))
            .andExpect(jsonPath("$.makeid").value(DEFAULT_MAKEID))
            .andExpect(jsonPath("$.makename").value(DEFAULT_MAKENAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.yom").value(DEFAULT_YOM))
            .andExpect(jsonPath("$.customercode").value(DEFAULT_CUSTOMERCODE))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.servicecount").value(DEFAULT_SERVICECOUNT))
            .andExpect(jsonPath("$.engNo").value(DEFAULT_ENG_NO))
            .andExpect(jsonPath("$.chaNo").value(DEFAULT_CHA_NO))
            .andExpect(jsonPath("$.milage").value(DEFAULT_MILAGE))
            .andExpect(jsonPath("$.lastservicedate").value(DEFAULT_LASTSERVICEDATE.toString()))
            .andExpect(jsonPath("$.nextservicedate").value(DEFAULT_NEXTSERVICEDATE.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.nextgearoilmilage").value(DEFAULT_NEXTGEAROILMILAGE))
            .andExpect(jsonPath("$.nextmilage").value(DEFAULT_NEXTMILAGE))
            .andExpect(jsonPath("$.serviceperiod").value(DEFAULT_SERVICEPERIOD));
    }

    @Test
    @Transactional
    void getNonExistingCustomervehicle() throws Exception {
        // Get the customervehicle
        restCustomervehicleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomervehicle() throws Exception {
        // Initialize the database
        customervehicleRepository.saveAndFlush(customervehicle);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customervehicle
        Customervehicle updatedCustomervehicle = customervehicleRepository.findById(customervehicle.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCustomervehicle are not directly saved in db
        em.detach(updatedCustomervehicle);
        updatedCustomervehicle
            .customerid(UPDATED_CUSTOMERID)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .categoryid(UPDATED_CATEGORYID)
            .categoryname(UPDATED_CATEGORYNAME)
            .typeid(UPDATED_TYPEID)
            .typename(UPDATED_TYPENAME)
            .makeid(UPDATED_MAKEID)
            .makename(UPDATED_MAKENAME)
            .model(UPDATED_MODEL)
            .yom(UPDATED_YOM)
            .customercode(UPDATED_CUSTOMERCODE)
            .remarks(UPDATED_REMARKS)
            .servicecount(UPDATED_SERVICECOUNT)
            .engNo(UPDATED_ENG_NO)
            .chaNo(UPDATED_CHA_NO)
            .milage(UPDATED_MILAGE)
            .lastservicedate(UPDATED_LASTSERVICEDATE)
            .nextservicedate(UPDATED_NEXTSERVICEDATE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nextgearoilmilage(UPDATED_NEXTGEAROILMILAGE)
            .nextmilage(UPDATED_NEXTMILAGE)
            .serviceperiod(UPDATED_SERVICEPERIOD);

        restCustomervehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomervehicle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCustomervehicle))
            )
            .andExpect(status().isOk());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCustomervehicleToMatchAllProperties(updatedCustomervehicle);
    }

    @Test
    @Transactional
    void putNonExistingCustomervehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customervehicle.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomervehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customervehicle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(customervehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomervehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customervehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomervehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(customervehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomervehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customervehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomervehicleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customervehicle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomervehicleWithPatch() throws Exception {
        // Initialize the database
        customervehicleRepository.saveAndFlush(customervehicle);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customervehicle using partial update
        Customervehicle partialUpdatedCustomervehicle = new Customervehicle();
        partialUpdatedCustomervehicle.setId(customervehicle.getId());

        partialUpdatedCustomervehicle
            .customerid(UPDATED_CUSTOMERID)
            .categoryname(UPDATED_CATEGORYNAME)
            .makename(UPDATED_MAKENAME)
            .yom(UPDATED_YOM)
            .customercode(UPDATED_CUSTOMERCODE)
            .remarks(UPDATED_REMARKS)
            .servicecount(UPDATED_SERVICECOUNT)
            .engNo(UPDATED_ENG_NO)
            .lastservicedate(UPDATED_LASTSERVICEDATE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nextmilage(UPDATED_NEXTMILAGE);

        restCustomervehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomervehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCustomervehicle))
            )
            .andExpect(status().isOk());

        // Validate the Customervehicle in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCustomervehicleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCustomervehicle, customervehicle),
            getPersistedCustomervehicle(customervehicle)
        );
    }

    @Test
    @Transactional
    void fullUpdateCustomervehicleWithPatch() throws Exception {
        // Initialize the database
        customervehicleRepository.saveAndFlush(customervehicle);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customervehicle using partial update
        Customervehicle partialUpdatedCustomervehicle = new Customervehicle();
        partialUpdatedCustomervehicle.setId(customervehicle.getId());

        partialUpdatedCustomervehicle
            .customerid(UPDATED_CUSTOMERID)
            .vehiclenumber(UPDATED_VEHICLENUMBER)
            .categoryid(UPDATED_CATEGORYID)
            .categoryname(UPDATED_CATEGORYNAME)
            .typeid(UPDATED_TYPEID)
            .typename(UPDATED_TYPENAME)
            .makeid(UPDATED_MAKEID)
            .makename(UPDATED_MAKENAME)
            .model(UPDATED_MODEL)
            .yom(UPDATED_YOM)
            .customercode(UPDATED_CUSTOMERCODE)
            .remarks(UPDATED_REMARKS)
            .servicecount(UPDATED_SERVICECOUNT)
            .engNo(UPDATED_ENG_NO)
            .chaNo(UPDATED_CHA_NO)
            .milage(UPDATED_MILAGE)
            .lastservicedate(UPDATED_LASTSERVICEDATE)
            .nextservicedate(UPDATED_NEXTSERVICEDATE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nextgearoilmilage(UPDATED_NEXTGEAROILMILAGE)
            .nextmilage(UPDATED_NEXTMILAGE)
            .serviceperiod(UPDATED_SERVICEPERIOD);

        restCustomervehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomervehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCustomervehicle))
            )
            .andExpect(status().isOk());

        // Validate the Customervehicle in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCustomervehicleUpdatableFieldsEquals(
            partialUpdatedCustomervehicle,
            getPersistedCustomervehicle(partialUpdatedCustomervehicle)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCustomervehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customervehicle.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomervehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customervehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(customervehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomervehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customervehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomervehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(customervehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomervehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customervehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomervehicleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(customervehicle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customervehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomervehicle() throws Exception {
        // Initialize the database
        customervehicleRepository.saveAndFlush(customervehicle);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the customervehicle
        restCustomervehicleMockMvc
            .perform(delete(ENTITY_API_URL_ID, customervehicle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return customervehicleRepository.count();
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

    protected Customervehicle getPersistedCustomervehicle(Customervehicle customervehicle) {
        return customervehicleRepository.findById(customervehicle.getId()).orElseThrow();
    }

    protected void assertPersistedCustomervehicleToMatchAllProperties(Customervehicle expectedCustomervehicle) {
        assertCustomervehicleAllPropertiesEquals(expectedCustomervehicle, getPersistedCustomervehicle(expectedCustomervehicle));
    }

    protected void assertPersistedCustomervehicleToMatchUpdatableProperties(Customervehicle expectedCustomervehicle) {
        assertCustomervehicleAllUpdatablePropertiesEquals(expectedCustomervehicle, getPersistedCustomervehicle(expectedCustomervehicle));
    }
}
