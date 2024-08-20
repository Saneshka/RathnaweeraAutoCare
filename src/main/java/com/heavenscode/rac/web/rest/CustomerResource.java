package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Customer;
import com.heavenscode.rac.repository.CustomerRepository;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.Customer}.
 */
@RestController
@RequestMapping("/api/customers")
@Transactional
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerRepository customerRepository;

    public CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * {@code POST  /customers} : Create a new customer.
     *
     * @param customer the customer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customer, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customer);
        if (customer.getId() != null) {
            throw new BadRequestAlertException("A new customer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        customer = customerRepository.save(customer);
        return ResponseEntity.created(new URI("/api/customers/" + customer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, customer.getId().toString()))
            .body(customer);
    }

    /**
     * {@code PUT  /customers/:id} : Updates an existing customer.
     *
     * @param id the id of the customer to save.
     * @param customer the customer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customer,
     * or with status {@code 400 (Bad Request)} if the customer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Customer customer
    ) throws URISyntaxException {
        log.debug("REST request to update Customer : {}, {}", id, customer);
        if (customer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        customer = customerRepository.save(customer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customer.getId().toString()))
            .body(customer);
    }

    /**
     * {@code PATCH  /customers/:id} : Partial updates given fields of an existing customer, field will ignore if it is null
     *
     * @param id the id of the customer to save.
     * @param customer the customer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customer,
     * or with status {@code 400 (Bad Request)} if the customer is not valid,
     * or with status {@code 404 (Not Found)} if the customer is not found,
     * or with status {@code 500 (Internal Server Error)} if the customer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Customer> partialUpdateCustomer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Customer customer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Customer partially : {}, {}", id, customer);
        if (customer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Customer> result = customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getCustomertype() != null) {
                    existingCustomer.setCustomertype(customer.getCustomertype());
                }
                if (customer.getCode() != null) {
                    existingCustomer.setCode(customer.getCode());
                }
                if (customer.getTitle() != null) {
                    existingCustomer.setTitle(customer.getTitle());
                }
                if (customer.getNamewithinitials() != null) {
                    existingCustomer.setNamewithinitials(customer.getNamewithinitials());
                }
                if (customer.getFullname() != null) {
                    existingCustomer.setFullname(customer.getFullname());
                }
                if (customer.getCallingname() != null) {
                    existingCustomer.setCallingname(customer.getCallingname());
                }
                if (customer.getNicno() != null) {
                    existingCustomer.setNicno(customer.getNicno());
                }
                if (customer.getNicissueddate() != null) {
                    existingCustomer.setNicissueddate(customer.getNicissueddate());
                }
                if (customer.getDateofbirth() != null) {
                    existingCustomer.setDateofbirth(customer.getDateofbirth());
                }
                if (customer.getBloodgroup() != null) {
                    existingCustomer.setBloodgroup(customer.getBloodgroup());
                }
                if (customer.getGender() != null) {
                    existingCustomer.setGender(customer.getGender());
                }
                if (customer.getMaritalstatus() != null) {
                    existingCustomer.setMaritalstatus(customer.getMaritalstatus());
                }
                if (customer.getMarrieddate() != null) {
                    existingCustomer.setMarrieddate(customer.getMarrieddate());
                }
                if (customer.getNationality() != null) {
                    existingCustomer.setNationality(customer.getNationality());
                }
                if (customer.getTerritory() != null) {
                    existingCustomer.setTerritory(customer.getTerritory());
                }
                if (customer.getReligion() != null) {
                    existingCustomer.setReligion(customer.getReligion());
                }
                if (customer.getTeam() != null) {
                    existingCustomer.setTeam(customer.getTeam());
                }
                if (customer.getBusinessname() != null) {
                    existingCustomer.setBusinessname(customer.getBusinessname());
                }
                if (customer.getBusinessregdate() != null) {
                    existingCustomer.setBusinessregdate(customer.getBusinessregdate());
                }
                if (customer.getBusinessregno() != null) {
                    existingCustomer.setBusinessregno(customer.getBusinessregno());
                }
                if (customer.getProfilepicturepath() != null) {
                    existingCustomer.setProfilepicturepath(customer.getProfilepicturepath());
                }
                if (customer.getResidencehouseno() != null) {
                    existingCustomer.setResidencehouseno(customer.getResidencehouseno());
                }
                if (customer.getResidenceaddress() != null) {
                    existingCustomer.setResidenceaddress(customer.getResidenceaddress());
                }
                if (customer.getResidencecity() != null) {
                    existingCustomer.setResidencecity(customer.getResidencecity());
                }
                if (customer.getResidencephone() != null) {
                    existingCustomer.setResidencephone(customer.getResidencephone());
                }
                if (customer.getBusinesslocationno() != null) {
                    existingCustomer.setBusinesslocationno(customer.getBusinesslocationno());
                }
                if (customer.getBusinessaddress() != null) {
                    existingCustomer.setBusinessaddress(customer.getBusinessaddress());
                }
                if (customer.getBusinesscity() != null) {
                    existingCustomer.setBusinesscity(customer.getBusinesscity());
                }
                if (customer.getBusinessphone1() != null) {
                    existingCustomer.setBusinessphone1(customer.getBusinessphone1());
                }
                if (customer.getBusinessphone2() != null) {
                    existingCustomer.setBusinessphone2(customer.getBusinessphone2());
                }
                if (customer.getBusinessmobile() != null) {
                    existingCustomer.setBusinessmobile(customer.getBusinessmobile());
                }
                if (customer.getBusinessfax() != null) {
                    existingCustomer.setBusinessfax(customer.getBusinessfax());
                }
                if (customer.getBusinessemail() != null) {
                    existingCustomer.setBusinessemail(customer.getBusinessemail());
                }
                if (customer.getBusinessprovinceid() != null) {
                    existingCustomer.setBusinessprovinceid(customer.getBusinessprovinceid());
                }
                if (customer.getBusinessdistrictid() != null) {
                    existingCustomer.setBusinessdistrictid(customer.getBusinessdistrictid());
                }
                if (customer.getContactpersonname() != null) {
                    existingCustomer.setContactpersonname(customer.getContactpersonname());
                }
                if (customer.getContactpersonphone() != null) {
                    existingCustomer.setContactpersonphone(customer.getContactpersonphone());
                }
                if (customer.getContactpersonmobile() != null) {
                    existingCustomer.setContactpersonmobile(customer.getContactpersonmobile());
                }
                if (customer.getContactpersonemail() != null) {
                    existingCustomer.setContactpersonemail(customer.getContactpersonemail());
                }
                if (customer.getRootmappath() != null) {
                    existingCustomer.setRootmappath(customer.getRootmappath());
                }
                if (customer.getWebsite() != null) {
                    existingCustomer.setWebsite(customer.getWebsite());
                }
                if (customer.getRegistrationdate() != null) {
                    existingCustomer.setRegistrationdate(customer.getRegistrationdate());
                }
                if (customer.getIsactive() != null) {
                    existingCustomer.setIsactive(customer.getIsactive());
                }
                if (customer.getIsinternalcustomer() != null) {
                    existingCustomer.setIsinternalcustomer(customer.getIsinternalcustomer());
                }
                if (customer.getDescription() != null) {
                    existingCustomer.setDescription(customer.getDescription());
                }
                if (customer.getLmu() != null) {
                    existingCustomer.setLmu(customer.getLmu());
                }
                if (customer.getLmd() != null) {
                    existingCustomer.setLmd(customer.getLmd());
                }
                if (customer.getMaximumdiscount() != null) {
                    existingCustomer.setMaximumdiscount(customer.getMaximumdiscount());
                }
                if (customer.getCreditlimit() != null) {
                    existingCustomer.setCreditlimit(customer.getCreditlimit());
                }
                if (customer.getHassecuritydeposit() != null) {
                    existingCustomer.setHassecuritydeposit(customer.getHassecuritydeposit());
                }
                if (customer.getSecuritydepositamount() != null) {
                    existingCustomer.setSecuritydepositamount(customer.getSecuritydepositamount());
                }
                if (customer.getPaybycash() != null) {
                    existingCustomer.setPaybycash(customer.getPaybycash());
                }
                if (customer.getCashpaymentbeforeload() != null) {
                    existingCustomer.setCashpaymentbeforeload(customer.getCashpaymentbeforeload());
                }
                if (customer.getCashlastinvoicebeforeload() != null) {
                    existingCustomer.setCashlastinvoicebeforeload(customer.getCashlastinvoicebeforeload());
                }
                if (customer.getPaybycredit() != null) {
                    existingCustomer.setPaybycredit(customer.getPaybycredit());
                }
                if (customer.getCreditoneweekcheck() != null) {
                    existingCustomer.setCreditoneweekcheck(customer.getCreditoneweekcheck());
                }
                if (customer.getCreditpaymentbydays() != null) {
                    existingCustomer.setCreditpaymentbydays(customer.getCreditpaymentbydays());
                }
                if (customer.getHaspurchasingdeposit() != null) {
                    existingCustomer.setHaspurchasingdeposit(customer.getHaspurchasingdeposit());
                }
                if (customer.getHassecuritydepositbond() != null) {
                    existingCustomer.setHassecuritydepositbond(customer.getHassecuritydepositbond());
                }
                if (customer.getHasassestsdeposit() != null) {
                    existingCustomer.setHasassestsdeposit(customer.getHasassestsdeposit());
                }
                if (customer.getCustomerrootmappath() != null) {
                    existingCustomer.setCustomerrootmappath(customer.getCustomerrootmappath());
                }
                if (customer.getEmployername() != null) {
                    existingCustomer.setEmployername(customer.getEmployername());
                }
                if (customer.getEmployeraddress() != null) {
                    existingCustomer.setEmployeraddress(customer.getEmployeraddress());
                }
                if (customer.getEmployerphone() != null) {
                    existingCustomer.setEmployerphone(customer.getEmployerphone());
                }
                if (customer.getEmployerdesignation() != null) {
                    existingCustomer.setEmployerdesignation(customer.getEmployerdesignation());
                }
                if (customer.getPreviousemployername() != null) {
                    existingCustomer.setPreviousemployername(customer.getPreviousemployername());
                }
                if (customer.getPreviousemployeraddress() != null) {
                    existingCustomer.setPreviousemployeraddress(customer.getPreviousemployeraddress());
                }
                if (customer.getPreviousindustry() != null) {
                    existingCustomer.setPreviousindustry(customer.getPreviousindustry());
                }
                if (customer.getPreviousperiod() != null) {
                    existingCustomer.setPreviousperiod(customer.getPreviousperiod());
                }
                if (customer.getPreviouspositions() != null) {
                    existingCustomer.setPreviouspositions(customer.getPreviouspositions());
                }
                if (customer.getPreviousresionforleaving() != null) {
                    existingCustomer.setPreviousresionforleaving(customer.getPreviousresionforleaving());
                }
                if (customer.getHascreaditlimit() != null) {
                    existingCustomer.setHascreaditlimit(customer.getHascreaditlimit());
                }
                if (customer.getAccountid() != null) {
                    existingCustomer.setAccountid(customer.getAccountid());
                }
                if (customer.getAccountcode() != null) {
                    existingCustomer.setAccountcode(customer.getAccountcode());
                }
                if (customer.getIsregistered() != null) {
                    existingCustomer.setIsregistered(customer.getIsregistered());
                }
                if (customer.getVatregnumber() != null) {
                    existingCustomer.setVatregnumber(customer.getVatregnumber());
                }
                if (customer.getTinnumber() != null) {
                    existingCustomer.setTinnumber(customer.getTinnumber());
                }
                if (customer.getLat() != null) {
                    existingCustomer.setLat(customer.getLat());
                }
                if (customer.getLon() != null) {
                    existingCustomer.setLon(customer.getLon());
                }
                if (customer.getCreditperiod() != null) {
                    existingCustomer.setCreditperiod(customer.getCreditperiod());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customer.getId().toString())
        );
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Customers");
        Page<Customer> page = customerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customers/:id} : get the "id" customer.
     *
     * @param id the id of the customer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        log.debug("REST request to get Customer : {}", id);
        Optional<Customer> customer = customerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customer);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customer.
     *
     * @param id the id of the customer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
