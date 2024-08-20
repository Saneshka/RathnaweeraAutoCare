package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.CustomerAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Customer;
import com.heavenscode.rac.repository.CustomerRepository;
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
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerResourceIT {

    private static final Integer DEFAULT_CUSTOMERTYPE = 1;
    private static final Integer UPDATED_CUSTOMERTYPE = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAMEWITHINITIALS = "AAAAAAAAAA";
    private static final String UPDATED_NAMEWITHINITIALS = "BBBBBBBBBB";

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CALLINGNAME = "AAAAAAAAAA";
    private static final String UPDATED_CALLINGNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NICNO = "AAAAAAAAAA";
    private static final String UPDATED_NICNO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NICISSUEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NICISSUEDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEOFBIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEOFBIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BLOODGROUP = "AAAAAAAAAA";
    private static final String UPDATED_BLOODGROUP = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_MARITALSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITALSTATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MARRIEDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MARRIEDDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NATIONALITY = 1;
    private static final Integer UPDATED_NATIONALITY = 2;

    private static final String DEFAULT_TERRITORY = "AAAAAAAAAA";
    private static final String UPDATED_TERRITORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_RELIGION = 1;
    private static final Integer UPDATED_RELIGION = 2;

    private static final String DEFAULT_TEAM = "AAAAAAAAAA";
    private static final String UPDATED_TEAM = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSNAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BUSINESSREGDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUSINESSREGDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BUSINESSREGNO = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSREGNO = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILEPICTUREPATH = "AAAAAAAAAA";
    private static final String UPDATED_PROFILEPICTUREPATH = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCEHOUSENO = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCEHOUSENO = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCEADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCEADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCECITY = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCECITY = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENCEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSLOCATIONNO = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSLOCATIONNO = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSCITY = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSCITY = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSPHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSPHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSPHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSPHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSMOBILE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSMOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSFAX = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSFAX = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESSEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESSEMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_BUSINESSPROVINCEID = 1;
    private static final Integer UPDATED_BUSINESSPROVINCEID = 2;

    private static final Integer DEFAULT_BUSINESSDISTRICTID = 1;
    private static final Integer UPDATED_BUSINESSDISTRICTID = 2;

    private static final String DEFAULT_CONTACTPERSONNAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSONNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTPERSONPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSONPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTPERSONMOBILE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSONMOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTPERSONEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSONEMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ROOTMAPPATH = "AAAAAAAAAA";
    private static final String UPDATED_ROOTMAPPATH = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Instant DEFAULT_REGISTRATIONDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTRATIONDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ISACTIVE = 1;
    private static final Integer UPDATED_ISACTIVE = 2;

    private static final Integer DEFAULT_ISINTERNALCUSTOMER = 1;
    private static final Integer UPDATED_ISINTERNALCUSTOMER = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_MAXIMUMDISCOUNT = 1F;
    private static final Float UPDATED_MAXIMUMDISCOUNT = 2F;

    private static final Float DEFAULT_CREDITLIMIT = 1F;
    private static final Float UPDATED_CREDITLIMIT = 2F;

    private static final Boolean DEFAULT_HASSECURITYDEPOSIT = false;
    private static final Boolean UPDATED_HASSECURITYDEPOSIT = true;

    private static final Float DEFAULT_SECURITYDEPOSITAMOUNT = 1F;
    private static final Float UPDATED_SECURITYDEPOSITAMOUNT = 2F;

    private static final Boolean DEFAULT_PAYBYCASH = false;
    private static final Boolean UPDATED_PAYBYCASH = true;

    private static final Boolean DEFAULT_CASHPAYMENTBEFORELOAD = false;
    private static final Boolean UPDATED_CASHPAYMENTBEFORELOAD = true;

    private static final Boolean DEFAULT_CASHLASTINVOICEBEFORELOAD = false;
    private static final Boolean UPDATED_CASHLASTINVOICEBEFORELOAD = true;

    private static final Boolean DEFAULT_PAYBYCREDIT = false;
    private static final Boolean UPDATED_PAYBYCREDIT = true;

    private static final Boolean DEFAULT_CREDITONEWEEKCHECK = false;
    private static final Boolean UPDATED_CREDITONEWEEKCHECK = true;

    private static final Integer DEFAULT_CREDITPAYMENTBYDAYS = 1;
    private static final Integer UPDATED_CREDITPAYMENTBYDAYS = 2;

    private static final Boolean DEFAULT_HASPURCHASINGDEPOSIT = false;
    private static final Boolean UPDATED_HASPURCHASINGDEPOSIT = true;

    private static final Boolean DEFAULT_HASSECURITYDEPOSITBOND = false;
    private static final Boolean UPDATED_HASSECURITYDEPOSITBOND = true;

    private static final Boolean DEFAULT_HASASSESTSDEPOSIT = false;
    private static final Boolean UPDATED_HASASSESTSDEPOSIT = true;

    private static final String DEFAULT_CUSTOMERROOTMAPPATH = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERROOTMAPPATH = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYERNAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYERADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYERADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYERPHONE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYERPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYERDESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYERDESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUSEMPLOYERNAME = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUSEMPLOYERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUSEMPLOYERADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUSEMPLOYERADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUSINDUSTRY = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUSINDUSTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUSPERIOD = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUSPERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUSPOSITIONS = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUSPOSITIONS = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUSRESIONFORLEAVING = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUSRESIONFORLEAVING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HASCREADITLIMIT = false;
    private static final Boolean UPDATED_HASCREADITLIMIT = true;

    private static final Integer DEFAULT_ACCOUNTID = 1;
    private static final Integer UPDATED_ACCOUNTID = 2;

    private static final String DEFAULT_ACCOUNTCODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISREGISTERED = false;
    private static final Boolean UPDATED_ISREGISTERED = true;

    private static final String DEFAULT_VATREGNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VATREGNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TINNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TINNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LAT = "AAAAAAAAAA";
    private static final String UPDATED_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_LON = "AAAAAAAAAA";
    private static final String UPDATED_LON = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDITPERIOD = 1;
    private static final Integer UPDATED_CREDITPERIOD = 2;

    private static final String ENTITY_API_URL = "/api/customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .customertype(DEFAULT_CUSTOMERTYPE)
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .namewithinitials(DEFAULT_NAMEWITHINITIALS)
            .fullname(DEFAULT_FULLNAME)
            .callingname(DEFAULT_CALLINGNAME)
            .nicno(DEFAULT_NICNO)
            .nicissueddate(DEFAULT_NICISSUEDDATE)
            .dateofbirth(DEFAULT_DATEOFBIRTH)
            .bloodgroup(DEFAULT_BLOODGROUP)
            .gender(DEFAULT_GENDER)
            .maritalstatus(DEFAULT_MARITALSTATUS)
            .marrieddate(DEFAULT_MARRIEDDATE)
            .nationality(DEFAULT_NATIONALITY)
            .territory(DEFAULT_TERRITORY)
            .religion(DEFAULT_RELIGION)
            .team(DEFAULT_TEAM)
            .businessname(DEFAULT_BUSINESSNAME)
            .businessregdate(DEFAULT_BUSINESSREGDATE)
            .businessregno(DEFAULT_BUSINESSREGNO)
            .profilepicturepath(DEFAULT_PROFILEPICTUREPATH)
            .residencehouseno(DEFAULT_RESIDENCEHOUSENO)
            .residenceaddress(DEFAULT_RESIDENCEADDRESS)
            .residencecity(DEFAULT_RESIDENCECITY)
            .residencephone(DEFAULT_RESIDENCEPHONE)
            .businesslocationno(DEFAULT_BUSINESSLOCATIONNO)
            .businessaddress(DEFAULT_BUSINESSADDRESS)
            .businesscity(DEFAULT_BUSINESSCITY)
            .businessphone1(DEFAULT_BUSINESSPHONE_1)
            .businessphone2(DEFAULT_BUSINESSPHONE_2)
            .businessmobile(DEFAULT_BUSINESSMOBILE)
            .businessfax(DEFAULT_BUSINESSFAX)
            .businessemail(DEFAULT_BUSINESSEMAIL)
            .businessprovinceid(DEFAULT_BUSINESSPROVINCEID)
            .businessdistrictid(DEFAULT_BUSINESSDISTRICTID)
            .contactpersonname(DEFAULT_CONTACTPERSONNAME)
            .contactpersonphone(DEFAULT_CONTACTPERSONPHONE)
            .contactpersonmobile(DEFAULT_CONTACTPERSONMOBILE)
            .contactpersonemail(DEFAULT_CONTACTPERSONEMAIL)
            .rootmappath(DEFAULT_ROOTMAPPATH)
            .website(DEFAULT_WEBSITE)
            .registrationdate(DEFAULT_REGISTRATIONDATE)
            .isactive(DEFAULT_ISACTIVE)
            .isinternalcustomer(DEFAULT_ISINTERNALCUSTOMER)
            .description(DEFAULT_DESCRIPTION)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .maximumdiscount(DEFAULT_MAXIMUMDISCOUNT)
            .creditlimit(DEFAULT_CREDITLIMIT)
            .hassecuritydeposit(DEFAULT_HASSECURITYDEPOSIT)
            .securitydepositamount(DEFAULT_SECURITYDEPOSITAMOUNT)
            .paybycash(DEFAULT_PAYBYCASH)
            .cashpaymentbeforeload(DEFAULT_CASHPAYMENTBEFORELOAD)
            .cashlastinvoicebeforeload(DEFAULT_CASHLASTINVOICEBEFORELOAD)
            .paybycredit(DEFAULT_PAYBYCREDIT)
            .creditoneweekcheck(DEFAULT_CREDITONEWEEKCHECK)
            .creditpaymentbydays(DEFAULT_CREDITPAYMENTBYDAYS)
            .haspurchasingdeposit(DEFAULT_HASPURCHASINGDEPOSIT)
            .hassecuritydepositbond(DEFAULT_HASSECURITYDEPOSITBOND)
            .hasassestsdeposit(DEFAULT_HASASSESTSDEPOSIT)
            .customerrootmappath(DEFAULT_CUSTOMERROOTMAPPATH)
            .employername(DEFAULT_EMPLOYERNAME)
            .employeraddress(DEFAULT_EMPLOYERADDRESS)
            .employerphone(DEFAULT_EMPLOYERPHONE)
            .employerdesignation(DEFAULT_EMPLOYERDESIGNATION)
            .previousemployername(DEFAULT_PREVIOUSEMPLOYERNAME)
            .previousemployeraddress(DEFAULT_PREVIOUSEMPLOYERADDRESS)
            .previousindustry(DEFAULT_PREVIOUSINDUSTRY)
            .previousperiod(DEFAULT_PREVIOUSPERIOD)
            .previouspositions(DEFAULT_PREVIOUSPOSITIONS)
            .previousresionforleaving(DEFAULT_PREVIOUSRESIONFORLEAVING)
            .hascreaditlimit(DEFAULT_HASCREADITLIMIT)
            .accountid(DEFAULT_ACCOUNTID)
            .accountcode(DEFAULT_ACCOUNTCODE)
            .isregistered(DEFAULT_ISREGISTERED)
            .vatregnumber(DEFAULT_VATREGNUMBER)
            .tinnumber(DEFAULT_TINNUMBER)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .creditperiod(DEFAULT_CREDITPERIOD);
        return customer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .customertype(UPDATED_CUSTOMERTYPE)
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .namewithinitials(UPDATED_NAMEWITHINITIALS)
            .fullname(UPDATED_FULLNAME)
            .callingname(UPDATED_CALLINGNAME)
            .nicno(UPDATED_NICNO)
            .nicissueddate(UPDATED_NICISSUEDDATE)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .bloodgroup(UPDATED_BLOODGROUP)
            .gender(UPDATED_GENDER)
            .maritalstatus(UPDATED_MARITALSTATUS)
            .marrieddate(UPDATED_MARRIEDDATE)
            .nationality(UPDATED_NATIONALITY)
            .territory(UPDATED_TERRITORY)
            .religion(UPDATED_RELIGION)
            .team(UPDATED_TEAM)
            .businessname(UPDATED_BUSINESSNAME)
            .businessregdate(UPDATED_BUSINESSREGDATE)
            .businessregno(UPDATED_BUSINESSREGNO)
            .profilepicturepath(UPDATED_PROFILEPICTUREPATH)
            .residencehouseno(UPDATED_RESIDENCEHOUSENO)
            .residenceaddress(UPDATED_RESIDENCEADDRESS)
            .residencecity(UPDATED_RESIDENCECITY)
            .residencephone(UPDATED_RESIDENCEPHONE)
            .businesslocationno(UPDATED_BUSINESSLOCATIONNO)
            .businessaddress(UPDATED_BUSINESSADDRESS)
            .businesscity(UPDATED_BUSINESSCITY)
            .businessphone1(UPDATED_BUSINESSPHONE_1)
            .businessphone2(UPDATED_BUSINESSPHONE_2)
            .businessmobile(UPDATED_BUSINESSMOBILE)
            .businessfax(UPDATED_BUSINESSFAX)
            .businessemail(UPDATED_BUSINESSEMAIL)
            .businessprovinceid(UPDATED_BUSINESSPROVINCEID)
            .businessdistrictid(UPDATED_BUSINESSDISTRICTID)
            .contactpersonname(UPDATED_CONTACTPERSONNAME)
            .contactpersonphone(UPDATED_CONTACTPERSONPHONE)
            .contactpersonmobile(UPDATED_CONTACTPERSONMOBILE)
            .contactpersonemail(UPDATED_CONTACTPERSONEMAIL)
            .rootmappath(UPDATED_ROOTMAPPATH)
            .website(UPDATED_WEBSITE)
            .registrationdate(UPDATED_REGISTRATIONDATE)
            .isactive(UPDATED_ISACTIVE)
            .isinternalcustomer(UPDATED_ISINTERNALCUSTOMER)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .maximumdiscount(UPDATED_MAXIMUMDISCOUNT)
            .creditlimit(UPDATED_CREDITLIMIT)
            .hassecuritydeposit(UPDATED_HASSECURITYDEPOSIT)
            .securitydepositamount(UPDATED_SECURITYDEPOSITAMOUNT)
            .paybycash(UPDATED_PAYBYCASH)
            .cashpaymentbeforeload(UPDATED_CASHPAYMENTBEFORELOAD)
            .cashlastinvoicebeforeload(UPDATED_CASHLASTINVOICEBEFORELOAD)
            .paybycredit(UPDATED_PAYBYCREDIT)
            .creditoneweekcheck(UPDATED_CREDITONEWEEKCHECK)
            .creditpaymentbydays(UPDATED_CREDITPAYMENTBYDAYS)
            .haspurchasingdeposit(UPDATED_HASPURCHASINGDEPOSIT)
            .hassecuritydepositbond(UPDATED_HASSECURITYDEPOSITBOND)
            .hasassestsdeposit(UPDATED_HASASSESTSDEPOSIT)
            .customerrootmappath(UPDATED_CUSTOMERROOTMAPPATH)
            .employername(UPDATED_EMPLOYERNAME)
            .employeraddress(UPDATED_EMPLOYERADDRESS)
            .employerphone(UPDATED_EMPLOYERPHONE)
            .employerdesignation(UPDATED_EMPLOYERDESIGNATION)
            .previousemployername(UPDATED_PREVIOUSEMPLOYERNAME)
            .previousemployeraddress(UPDATED_PREVIOUSEMPLOYERADDRESS)
            .previousindustry(UPDATED_PREVIOUSINDUSTRY)
            .previousperiod(UPDATED_PREVIOUSPERIOD)
            .previouspositions(UPDATED_PREVIOUSPOSITIONS)
            .previousresionforleaving(UPDATED_PREVIOUSRESIONFORLEAVING)
            .hascreaditlimit(UPDATED_HASCREADITLIMIT)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .isregistered(UPDATED_ISREGISTERED)
            .vatregnumber(UPDATED_VATREGNUMBER)
            .tinnumber(UPDATED_TINNUMBER)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .creditperiod(UPDATED_CREDITPERIOD);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Customer
        var returnedCustomer = om.readValue(
            restCustomerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Customer.class
        );

        // Validate the Customer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCustomerUpdatableFieldsEquals(returnedCustomer, getPersistedCustomer(returnedCustomer));
    }

    @Test
    @Transactional
    void createCustomerWithExistingId() throws Exception {
        // Create the Customer with an existing ID
        customer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].customertype").value(hasItem(DEFAULT_CUSTOMERTYPE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].namewithinitials").value(hasItem(DEFAULT_NAMEWITHINITIALS)))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].callingname").value(hasItem(DEFAULT_CALLINGNAME)))
            .andExpect(jsonPath("$.[*].nicno").value(hasItem(DEFAULT_NICNO)))
            .andExpect(jsonPath("$.[*].nicissueddate").value(hasItem(DEFAULT_NICISSUEDDATE.toString())))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(DEFAULT_DATEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].bloodgroup").value(hasItem(DEFAULT_BLOODGROUP)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].maritalstatus").value(hasItem(DEFAULT_MARITALSTATUS)))
            .andExpect(jsonPath("$.[*].marrieddate").value(hasItem(DEFAULT_MARRIEDDATE.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].territory").value(hasItem(DEFAULT_TERRITORY)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].team").value(hasItem(DEFAULT_TEAM)))
            .andExpect(jsonPath("$.[*].businessname").value(hasItem(DEFAULT_BUSINESSNAME)))
            .andExpect(jsonPath("$.[*].businessregdate").value(hasItem(DEFAULT_BUSINESSREGDATE.toString())))
            .andExpect(jsonPath("$.[*].businessregno").value(hasItem(DEFAULT_BUSINESSREGNO)))
            .andExpect(jsonPath("$.[*].profilepicturepath").value(hasItem(DEFAULT_PROFILEPICTUREPATH)))
            .andExpect(jsonPath("$.[*].residencehouseno").value(hasItem(DEFAULT_RESIDENCEHOUSENO)))
            .andExpect(jsonPath("$.[*].residenceaddress").value(hasItem(DEFAULT_RESIDENCEADDRESS)))
            .andExpect(jsonPath("$.[*].residencecity").value(hasItem(DEFAULT_RESIDENCECITY)))
            .andExpect(jsonPath("$.[*].residencephone").value(hasItem(DEFAULT_RESIDENCEPHONE)))
            .andExpect(jsonPath("$.[*].businesslocationno").value(hasItem(DEFAULT_BUSINESSLOCATIONNO)))
            .andExpect(jsonPath("$.[*].businessaddress").value(hasItem(DEFAULT_BUSINESSADDRESS)))
            .andExpect(jsonPath("$.[*].businesscity").value(hasItem(DEFAULT_BUSINESSCITY)))
            .andExpect(jsonPath("$.[*].businessphone1").value(hasItem(DEFAULT_BUSINESSPHONE_1)))
            .andExpect(jsonPath("$.[*].businessphone2").value(hasItem(DEFAULT_BUSINESSPHONE_2)))
            .andExpect(jsonPath("$.[*].businessmobile").value(hasItem(DEFAULT_BUSINESSMOBILE)))
            .andExpect(jsonPath("$.[*].businessfax").value(hasItem(DEFAULT_BUSINESSFAX)))
            .andExpect(jsonPath("$.[*].businessemail").value(hasItem(DEFAULT_BUSINESSEMAIL)))
            .andExpect(jsonPath("$.[*].businessprovinceid").value(hasItem(DEFAULT_BUSINESSPROVINCEID)))
            .andExpect(jsonPath("$.[*].businessdistrictid").value(hasItem(DEFAULT_BUSINESSDISTRICTID)))
            .andExpect(jsonPath("$.[*].contactpersonname").value(hasItem(DEFAULT_CONTACTPERSONNAME)))
            .andExpect(jsonPath("$.[*].contactpersonphone").value(hasItem(DEFAULT_CONTACTPERSONPHONE)))
            .andExpect(jsonPath("$.[*].contactpersonmobile").value(hasItem(DEFAULT_CONTACTPERSONMOBILE)))
            .andExpect(jsonPath("$.[*].contactpersonemail").value(hasItem(DEFAULT_CONTACTPERSONEMAIL)))
            .andExpect(jsonPath("$.[*].rootmappath").value(hasItem(DEFAULT_ROOTMAPPATH)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].registrationdate").value(hasItem(DEFAULT_REGISTRATIONDATE.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE)))
            .andExpect(jsonPath("$.[*].isinternalcustomer").value(hasItem(DEFAULT_ISINTERNALCUSTOMER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].maximumdiscount").value(hasItem(DEFAULT_MAXIMUMDISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].creditlimit").value(hasItem(DEFAULT_CREDITLIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].hassecuritydeposit").value(hasItem(DEFAULT_HASSECURITYDEPOSIT.booleanValue())))
            .andExpect(jsonPath("$.[*].securitydepositamount").value(hasItem(DEFAULT_SECURITYDEPOSITAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paybycash").value(hasItem(DEFAULT_PAYBYCASH.booleanValue())))
            .andExpect(jsonPath("$.[*].cashpaymentbeforeload").value(hasItem(DEFAULT_CASHPAYMENTBEFORELOAD.booleanValue())))
            .andExpect(jsonPath("$.[*].cashlastinvoicebeforeload").value(hasItem(DEFAULT_CASHLASTINVOICEBEFORELOAD.booleanValue())))
            .andExpect(jsonPath("$.[*].paybycredit").value(hasItem(DEFAULT_PAYBYCREDIT.booleanValue())))
            .andExpect(jsonPath("$.[*].creditoneweekcheck").value(hasItem(DEFAULT_CREDITONEWEEKCHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].creditpaymentbydays").value(hasItem(DEFAULT_CREDITPAYMENTBYDAYS)))
            .andExpect(jsonPath("$.[*].haspurchasingdeposit").value(hasItem(DEFAULT_HASPURCHASINGDEPOSIT.booleanValue())))
            .andExpect(jsonPath("$.[*].hassecuritydepositbond").value(hasItem(DEFAULT_HASSECURITYDEPOSITBOND.booleanValue())))
            .andExpect(jsonPath("$.[*].hasassestsdeposit").value(hasItem(DEFAULT_HASASSESTSDEPOSIT.booleanValue())))
            .andExpect(jsonPath("$.[*].customerrootmappath").value(hasItem(DEFAULT_CUSTOMERROOTMAPPATH)))
            .andExpect(jsonPath("$.[*].employername").value(hasItem(DEFAULT_EMPLOYERNAME)))
            .andExpect(jsonPath("$.[*].employeraddress").value(hasItem(DEFAULT_EMPLOYERADDRESS)))
            .andExpect(jsonPath("$.[*].employerphone").value(hasItem(DEFAULT_EMPLOYERPHONE)))
            .andExpect(jsonPath("$.[*].employerdesignation").value(hasItem(DEFAULT_EMPLOYERDESIGNATION)))
            .andExpect(jsonPath("$.[*].previousemployername").value(hasItem(DEFAULT_PREVIOUSEMPLOYERNAME)))
            .andExpect(jsonPath("$.[*].previousemployeraddress").value(hasItem(DEFAULT_PREVIOUSEMPLOYERADDRESS)))
            .andExpect(jsonPath("$.[*].previousindustry").value(hasItem(DEFAULT_PREVIOUSINDUSTRY)))
            .andExpect(jsonPath("$.[*].previousperiod").value(hasItem(DEFAULT_PREVIOUSPERIOD)))
            .andExpect(jsonPath("$.[*].previouspositions").value(hasItem(DEFAULT_PREVIOUSPOSITIONS)))
            .andExpect(jsonPath("$.[*].previousresionforleaving").value(hasItem(DEFAULT_PREVIOUSRESIONFORLEAVING)))
            .andExpect(jsonPath("$.[*].hascreaditlimit").value(hasItem(DEFAULT_HASCREADITLIMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID)))
            .andExpect(jsonPath("$.[*].accountcode").value(hasItem(DEFAULT_ACCOUNTCODE)))
            .andExpect(jsonPath("$.[*].isregistered").value(hasItem(DEFAULT_ISREGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].vatregnumber").value(hasItem(DEFAULT_VATREGNUMBER)))
            .andExpect(jsonPath("$.[*].tinnumber").value(hasItem(DEFAULT_TINNUMBER)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT)))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON)))
            .andExpect(jsonPath("$.[*].creditperiod").value(hasItem(DEFAULT_CREDITPERIOD)));
    }

    @Test
    @Transactional
    void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL_ID, customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.customertype").value(DEFAULT_CUSTOMERTYPE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.namewithinitials").value(DEFAULT_NAMEWITHINITIALS))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.callingname").value(DEFAULT_CALLINGNAME))
            .andExpect(jsonPath("$.nicno").value(DEFAULT_NICNO))
            .andExpect(jsonPath("$.nicissueddate").value(DEFAULT_NICISSUEDDATE.toString()))
            .andExpect(jsonPath("$.dateofbirth").value(DEFAULT_DATEOFBIRTH.toString()))
            .andExpect(jsonPath("$.bloodgroup").value(DEFAULT_BLOODGROUP))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.maritalstatus").value(DEFAULT_MARITALSTATUS))
            .andExpect(jsonPath("$.marrieddate").value(DEFAULT_MARRIEDDATE.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.territory").value(DEFAULT_TERRITORY))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.team").value(DEFAULT_TEAM))
            .andExpect(jsonPath("$.businessname").value(DEFAULT_BUSINESSNAME))
            .andExpect(jsonPath("$.businessregdate").value(DEFAULT_BUSINESSREGDATE.toString()))
            .andExpect(jsonPath("$.businessregno").value(DEFAULT_BUSINESSREGNO))
            .andExpect(jsonPath("$.profilepicturepath").value(DEFAULT_PROFILEPICTUREPATH))
            .andExpect(jsonPath("$.residencehouseno").value(DEFAULT_RESIDENCEHOUSENO))
            .andExpect(jsonPath("$.residenceaddress").value(DEFAULT_RESIDENCEADDRESS))
            .andExpect(jsonPath("$.residencecity").value(DEFAULT_RESIDENCECITY))
            .andExpect(jsonPath("$.residencephone").value(DEFAULT_RESIDENCEPHONE))
            .andExpect(jsonPath("$.businesslocationno").value(DEFAULT_BUSINESSLOCATIONNO))
            .andExpect(jsonPath("$.businessaddress").value(DEFAULT_BUSINESSADDRESS))
            .andExpect(jsonPath("$.businesscity").value(DEFAULT_BUSINESSCITY))
            .andExpect(jsonPath("$.businessphone1").value(DEFAULT_BUSINESSPHONE_1))
            .andExpect(jsonPath("$.businessphone2").value(DEFAULT_BUSINESSPHONE_2))
            .andExpect(jsonPath("$.businessmobile").value(DEFAULT_BUSINESSMOBILE))
            .andExpect(jsonPath("$.businessfax").value(DEFAULT_BUSINESSFAX))
            .andExpect(jsonPath("$.businessemail").value(DEFAULT_BUSINESSEMAIL))
            .andExpect(jsonPath("$.businessprovinceid").value(DEFAULT_BUSINESSPROVINCEID))
            .andExpect(jsonPath("$.businessdistrictid").value(DEFAULT_BUSINESSDISTRICTID))
            .andExpect(jsonPath("$.contactpersonname").value(DEFAULT_CONTACTPERSONNAME))
            .andExpect(jsonPath("$.contactpersonphone").value(DEFAULT_CONTACTPERSONPHONE))
            .andExpect(jsonPath("$.contactpersonmobile").value(DEFAULT_CONTACTPERSONMOBILE))
            .andExpect(jsonPath("$.contactpersonemail").value(DEFAULT_CONTACTPERSONEMAIL))
            .andExpect(jsonPath("$.rootmappath").value(DEFAULT_ROOTMAPPATH))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.registrationdate").value(DEFAULT_REGISTRATIONDATE.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE))
            .andExpect(jsonPath("$.isinternalcustomer").value(DEFAULT_ISINTERNALCUSTOMER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.maximumdiscount").value(DEFAULT_MAXIMUMDISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.creditlimit").value(DEFAULT_CREDITLIMIT.doubleValue()))
            .andExpect(jsonPath("$.hassecuritydeposit").value(DEFAULT_HASSECURITYDEPOSIT.booleanValue()))
            .andExpect(jsonPath("$.securitydepositamount").value(DEFAULT_SECURITYDEPOSITAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paybycash").value(DEFAULT_PAYBYCASH.booleanValue()))
            .andExpect(jsonPath("$.cashpaymentbeforeload").value(DEFAULT_CASHPAYMENTBEFORELOAD.booleanValue()))
            .andExpect(jsonPath("$.cashlastinvoicebeforeload").value(DEFAULT_CASHLASTINVOICEBEFORELOAD.booleanValue()))
            .andExpect(jsonPath("$.paybycredit").value(DEFAULT_PAYBYCREDIT.booleanValue()))
            .andExpect(jsonPath("$.creditoneweekcheck").value(DEFAULT_CREDITONEWEEKCHECK.booleanValue()))
            .andExpect(jsonPath("$.creditpaymentbydays").value(DEFAULT_CREDITPAYMENTBYDAYS))
            .andExpect(jsonPath("$.haspurchasingdeposit").value(DEFAULT_HASPURCHASINGDEPOSIT.booleanValue()))
            .andExpect(jsonPath("$.hassecuritydepositbond").value(DEFAULT_HASSECURITYDEPOSITBOND.booleanValue()))
            .andExpect(jsonPath("$.hasassestsdeposit").value(DEFAULT_HASASSESTSDEPOSIT.booleanValue()))
            .andExpect(jsonPath("$.customerrootmappath").value(DEFAULT_CUSTOMERROOTMAPPATH))
            .andExpect(jsonPath("$.employername").value(DEFAULT_EMPLOYERNAME))
            .andExpect(jsonPath("$.employeraddress").value(DEFAULT_EMPLOYERADDRESS))
            .andExpect(jsonPath("$.employerphone").value(DEFAULT_EMPLOYERPHONE))
            .andExpect(jsonPath("$.employerdesignation").value(DEFAULT_EMPLOYERDESIGNATION))
            .andExpect(jsonPath("$.previousemployername").value(DEFAULT_PREVIOUSEMPLOYERNAME))
            .andExpect(jsonPath("$.previousemployeraddress").value(DEFAULT_PREVIOUSEMPLOYERADDRESS))
            .andExpect(jsonPath("$.previousindustry").value(DEFAULT_PREVIOUSINDUSTRY))
            .andExpect(jsonPath("$.previousperiod").value(DEFAULT_PREVIOUSPERIOD))
            .andExpect(jsonPath("$.previouspositions").value(DEFAULT_PREVIOUSPOSITIONS))
            .andExpect(jsonPath("$.previousresionforleaving").value(DEFAULT_PREVIOUSRESIONFORLEAVING))
            .andExpect(jsonPath("$.hascreaditlimit").value(DEFAULT_HASCREADITLIMIT.booleanValue()))
            .andExpect(jsonPath("$.accountid").value(DEFAULT_ACCOUNTID))
            .andExpect(jsonPath("$.accountcode").value(DEFAULT_ACCOUNTCODE))
            .andExpect(jsonPath("$.isregistered").value(DEFAULT_ISREGISTERED.booleanValue()))
            .andExpect(jsonPath("$.vatregnumber").value(DEFAULT_VATREGNUMBER))
            .andExpect(jsonPath("$.tinnumber").value(DEFAULT_TINNUMBER))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON))
            .andExpect(jsonPath("$.creditperiod").value(DEFAULT_CREDITPERIOD));
    }

    @Test
    @Transactional
    void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .customertype(UPDATED_CUSTOMERTYPE)
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .namewithinitials(UPDATED_NAMEWITHINITIALS)
            .fullname(UPDATED_FULLNAME)
            .callingname(UPDATED_CALLINGNAME)
            .nicno(UPDATED_NICNO)
            .nicissueddate(UPDATED_NICISSUEDDATE)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .bloodgroup(UPDATED_BLOODGROUP)
            .gender(UPDATED_GENDER)
            .maritalstatus(UPDATED_MARITALSTATUS)
            .marrieddate(UPDATED_MARRIEDDATE)
            .nationality(UPDATED_NATIONALITY)
            .territory(UPDATED_TERRITORY)
            .religion(UPDATED_RELIGION)
            .team(UPDATED_TEAM)
            .businessname(UPDATED_BUSINESSNAME)
            .businessregdate(UPDATED_BUSINESSREGDATE)
            .businessregno(UPDATED_BUSINESSREGNO)
            .profilepicturepath(UPDATED_PROFILEPICTUREPATH)
            .residencehouseno(UPDATED_RESIDENCEHOUSENO)
            .residenceaddress(UPDATED_RESIDENCEADDRESS)
            .residencecity(UPDATED_RESIDENCECITY)
            .residencephone(UPDATED_RESIDENCEPHONE)
            .businesslocationno(UPDATED_BUSINESSLOCATIONNO)
            .businessaddress(UPDATED_BUSINESSADDRESS)
            .businesscity(UPDATED_BUSINESSCITY)
            .businessphone1(UPDATED_BUSINESSPHONE_1)
            .businessphone2(UPDATED_BUSINESSPHONE_2)
            .businessmobile(UPDATED_BUSINESSMOBILE)
            .businessfax(UPDATED_BUSINESSFAX)
            .businessemail(UPDATED_BUSINESSEMAIL)
            .businessprovinceid(UPDATED_BUSINESSPROVINCEID)
            .businessdistrictid(UPDATED_BUSINESSDISTRICTID)
            .contactpersonname(UPDATED_CONTACTPERSONNAME)
            .contactpersonphone(UPDATED_CONTACTPERSONPHONE)
            .contactpersonmobile(UPDATED_CONTACTPERSONMOBILE)
            .contactpersonemail(UPDATED_CONTACTPERSONEMAIL)
            .rootmappath(UPDATED_ROOTMAPPATH)
            .website(UPDATED_WEBSITE)
            .registrationdate(UPDATED_REGISTRATIONDATE)
            .isactive(UPDATED_ISACTIVE)
            .isinternalcustomer(UPDATED_ISINTERNALCUSTOMER)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .maximumdiscount(UPDATED_MAXIMUMDISCOUNT)
            .creditlimit(UPDATED_CREDITLIMIT)
            .hassecuritydeposit(UPDATED_HASSECURITYDEPOSIT)
            .securitydepositamount(UPDATED_SECURITYDEPOSITAMOUNT)
            .paybycash(UPDATED_PAYBYCASH)
            .cashpaymentbeforeload(UPDATED_CASHPAYMENTBEFORELOAD)
            .cashlastinvoicebeforeload(UPDATED_CASHLASTINVOICEBEFORELOAD)
            .paybycredit(UPDATED_PAYBYCREDIT)
            .creditoneweekcheck(UPDATED_CREDITONEWEEKCHECK)
            .creditpaymentbydays(UPDATED_CREDITPAYMENTBYDAYS)
            .haspurchasingdeposit(UPDATED_HASPURCHASINGDEPOSIT)
            .hassecuritydepositbond(UPDATED_HASSECURITYDEPOSITBOND)
            .hasassestsdeposit(UPDATED_HASASSESTSDEPOSIT)
            .customerrootmappath(UPDATED_CUSTOMERROOTMAPPATH)
            .employername(UPDATED_EMPLOYERNAME)
            .employeraddress(UPDATED_EMPLOYERADDRESS)
            .employerphone(UPDATED_EMPLOYERPHONE)
            .employerdesignation(UPDATED_EMPLOYERDESIGNATION)
            .previousemployername(UPDATED_PREVIOUSEMPLOYERNAME)
            .previousemployeraddress(UPDATED_PREVIOUSEMPLOYERADDRESS)
            .previousindustry(UPDATED_PREVIOUSINDUSTRY)
            .previousperiod(UPDATED_PREVIOUSPERIOD)
            .previouspositions(UPDATED_PREVIOUSPOSITIONS)
            .previousresionforleaving(UPDATED_PREVIOUSRESIONFORLEAVING)
            .hascreaditlimit(UPDATED_HASCREADITLIMIT)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .isregistered(UPDATED_ISREGISTERED)
            .vatregnumber(UPDATED_VATREGNUMBER)
            .tinnumber(UPDATED_TINNUMBER)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .creditperiod(UPDATED_CREDITPERIOD);

        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCustomerToMatchAllProperties(updatedCustomer);
    }

    @Test
    @Transactional
    void putNonExistingCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customer.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .code(UPDATED_CODE)
            .namewithinitials(UPDATED_NAMEWITHINITIALS)
            .fullname(UPDATED_FULLNAME)
            .callingname(UPDATED_CALLINGNAME)
            .gender(UPDATED_GENDER)
            .maritalstatus(UPDATED_MARITALSTATUS)
            .marrieddate(UPDATED_MARRIEDDATE)
            .religion(UPDATED_RELIGION)
            .businessname(UPDATED_BUSINESSNAME)
            .businessregdate(UPDATED_BUSINESSREGDATE)
            .residenceaddress(UPDATED_RESIDENCEADDRESS)
            .residencecity(UPDATED_RESIDENCECITY)
            .residencephone(UPDATED_RESIDENCEPHONE)
            .businessmobile(UPDATED_BUSINESSMOBILE)
            .businessfax(UPDATED_BUSINESSFAX)
            .businessemail(UPDATED_BUSINESSEMAIL)
            .businessdistrictid(UPDATED_BUSINESSDISTRICTID)
            .contactpersonname(UPDATED_CONTACTPERSONNAME)
            .contactpersonphone(UPDATED_CONTACTPERSONPHONE)
            .rootmappath(UPDATED_ROOTMAPPATH)
            .website(UPDATED_WEBSITE)
            .isinternalcustomer(UPDATED_ISINTERNALCUSTOMER)
            .lmu(UPDATED_LMU)
            .paybycash(UPDATED_PAYBYCASH)
            .cashpaymentbeforeload(UPDATED_CASHPAYMENTBEFORELOAD)
            .cashlastinvoicebeforeload(UPDATED_CASHLASTINVOICEBEFORELOAD)
            .haspurchasingdeposit(UPDATED_HASPURCHASINGDEPOSIT)
            .hasassestsdeposit(UPDATED_HASASSESTSDEPOSIT)
            .customerrootmappath(UPDATED_CUSTOMERROOTMAPPATH)
            .employername(UPDATED_EMPLOYERNAME)
            .employeraddress(UPDATED_EMPLOYERADDRESS)
            .employerphone(UPDATED_EMPLOYERPHONE)
            .employerdesignation(UPDATED_EMPLOYERDESIGNATION)
            .previousemployername(UPDATED_PREVIOUSEMPLOYERNAME)
            .previousemployeraddress(UPDATED_PREVIOUSEMPLOYERADDRESS)
            .previouspositions(UPDATED_PREVIOUSPOSITIONS)
            .hascreaditlimit(UPDATED_HASCREADITLIMIT)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .vatregnumber(UPDATED_VATREGNUMBER)
            .lat(UPDATED_LAT);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCustomerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCustomer, customer), getPersistedCustomer(customer));
    }

    @Test
    @Transactional
    void fullUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .customertype(UPDATED_CUSTOMERTYPE)
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .namewithinitials(UPDATED_NAMEWITHINITIALS)
            .fullname(UPDATED_FULLNAME)
            .callingname(UPDATED_CALLINGNAME)
            .nicno(UPDATED_NICNO)
            .nicissueddate(UPDATED_NICISSUEDDATE)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .bloodgroup(UPDATED_BLOODGROUP)
            .gender(UPDATED_GENDER)
            .maritalstatus(UPDATED_MARITALSTATUS)
            .marrieddate(UPDATED_MARRIEDDATE)
            .nationality(UPDATED_NATIONALITY)
            .territory(UPDATED_TERRITORY)
            .religion(UPDATED_RELIGION)
            .team(UPDATED_TEAM)
            .businessname(UPDATED_BUSINESSNAME)
            .businessregdate(UPDATED_BUSINESSREGDATE)
            .businessregno(UPDATED_BUSINESSREGNO)
            .profilepicturepath(UPDATED_PROFILEPICTUREPATH)
            .residencehouseno(UPDATED_RESIDENCEHOUSENO)
            .residenceaddress(UPDATED_RESIDENCEADDRESS)
            .residencecity(UPDATED_RESIDENCECITY)
            .residencephone(UPDATED_RESIDENCEPHONE)
            .businesslocationno(UPDATED_BUSINESSLOCATIONNO)
            .businessaddress(UPDATED_BUSINESSADDRESS)
            .businesscity(UPDATED_BUSINESSCITY)
            .businessphone1(UPDATED_BUSINESSPHONE_1)
            .businessphone2(UPDATED_BUSINESSPHONE_2)
            .businessmobile(UPDATED_BUSINESSMOBILE)
            .businessfax(UPDATED_BUSINESSFAX)
            .businessemail(UPDATED_BUSINESSEMAIL)
            .businessprovinceid(UPDATED_BUSINESSPROVINCEID)
            .businessdistrictid(UPDATED_BUSINESSDISTRICTID)
            .contactpersonname(UPDATED_CONTACTPERSONNAME)
            .contactpersonphone(UPDATED_CONTACTPERSONPHONE)
            .contactpersonmobile(UPDATED_CONTACTPERSONMOBILE)
            .contactpersonemail(UPDATED_CONTACTPERSONEMAIL)
            .rootmappath(UPDATED_ROOTMAPPATH)
            .website(UPDATED_WEBSITE)
            .registrationdate(UPDATED_REGISTRATIONDATE)
            .isactive(UPDATED_ISACTIVE)
            .isinternalcustomer(UPDATED_ISINTERNALCUSTOMER)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .maximumdiscount(UPDATED_MAXIMUMDISCOUNT)
            .creditlimit(UPDATED_CREDITLIMIT)
            .hassecuritydeposit(UPDATED_HASSECURITYDEPOSIT)
            .securitydepositamount(UPDATED_SECURITYDEPOSITAMOUNT)
            .paybycash(UPDATED_PAYBYCASH)
            .cashpaymentbeforeload(UPDATED_CASHPAYMENTBEFORELOAD)
            .cashlastinvoicebeforeload(UPDATED_CASHLASTINVOICEBEFORELOAD)
            .paybycredit(UPDATED_PAYBYCREDIT)
            .creditoneweekcheck(UPDATED_CREDITONEWEEKCHECK)
            .creditpaymentbydays(UPDATED_CREDITPAYMENTBYDAYS)
            .haspurchasingdeposit(UPDATED_HASPURCHASINGDEPOSIT)
            .hassecuritydepositbond(UPDATED_HASSECURITYDEPOSITBOND)
            .hasassestsdeposit(UPDATED_HASASSESTSDEPOSIT)
            .customerrootmappath(UPDATED_CUSTOMERROOTMAPPATH)
            .employername(UPDATED_EMPLOYERNAME)
            .employeraddress(UPDATED_EMPLOYERADDRESS)
            .employerphone(UPDATED_EMPLOYERPHONE)
            .employerdesignation(UPDATED_EMPLOYERDESIGNATION)
            .previousemployername(UPDATED_PREVIOUSEMPLOYERNAME)
            .previousemployeraddress(UPDATED_PREVIOUSEMPLOYERADDRESS)
            .previousindustry(UPDATED_PREVIOUSINDUSTRY)
            .previousperiod(UPDATED_PREVIOUSPERIOD)
            .previouspositions(UPDATED_PREVIOUSPOSITIONS)
            .previousresionforleaving(UPDATED_PREVIOUSRESIONFORLEAVING)
            .hascreaditlimit(UPDATED_HASCREADITLIMIT)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .isregistered(UPDATED_ISREGISTERED)
            .vatregnumber(UPDATED_VATREGNUMBER)
            .tinnumber(UPDATED_TINNUMBER)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .creditperiod(UPDATED_CREDITPERIOD);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCustomerUpdatableFieldsEquals(partialUpdatedCustomer, getPersistedCustomer(partialUpdatedCustomer));
    }

    @Test
    @Transactional
    void patchNonExistingCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(customer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(customer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the customer
        restCustomerMockMvc
            .perform(delete(ENTITY_API_URL_ID, customer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return customerRepository.count();
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

    protected Customer getPersistedCustomer(Customer customer) {
        return customerRepository.findById(customer.getId()).orElseThrow();
    }

    protected void assertPersistedCustomerToMatchAllProperties(Customer expectedCustomer) {
        assertCustomerAllPropertiesEquals(expectedCustomer, getPersistedCustomer(expectedCustomer));
    }

    protected void assertPersistedCustomerToMatchUpdatableProperties(Customer expectedCustomer) {
        assertCustomerAllUpdatablePropertiesEquals(expectedCustomer, getPersistedCustomer(expectedCustomer));
    }
}
