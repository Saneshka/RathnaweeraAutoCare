package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autocareappointment;
import com.heavenscode.rac.repository.AutocareappointmentRepository;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autocareappointment}.
 */
@RestController
@RequestMapping("/api/autocareappointments")
@Transactional
public class AutocareappointmentResource {

    private final Logger log = LoggerFactory.getLogger(AutocareappointmentResource.class);

    private static final String ENTITY_NAME = "autocareappointment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutocareappointmentRepository autocareappointmentRepository;

    public AutocareappointmentResource(AutocareappointmentRepository autocareappointmentRepository) {
        this.autocareappointmentRepository = autocareappointmentRepository;
    }

    /**
     * {@code POST  /autocareappointments} : Create a new autocareappointment.
     *
     * @param autocareappointment the autocareappointment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autocareappointment, or with status {@code 400 (Bad Request)} if the autocareappointment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autocareappointment> createAutocareappointment(@RequestBody Autocareappointment autocareappointment)
        throws URISyntaxException {
        log.debug("REST request to save Autocareappointment : {}", autocareappointment);
        if (autocareappointment.getId() != null) {
            throw new BadRequestAlertException("A new autocareappointment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        autocareappointment = autocareappointmentRepository.save(autocareappointment);
        return ResponseEntity.created(new URI("/api/autocareappointments/" + autocareappointment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, autocareappointment.getId().toString()))
            .body(autocareappointment);
    }

    /**
     * {@code PUT  /autocareappointments/:id} : Updates an existing autocareappointment.
     *
     * @param id the id of the autocareappointment to save.
     * @param autocareappointment the autocareappointment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autocareappointment,
     * or with status {@code 400 (Bad Request)} if the autocareappointment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autocareappointment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autocareappointment> updateAutocareappointment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autocareappointment autocareappointment
    ) throws URISyntaxException {
        log.debug("REST request to update Autocareappointment : {}, {}", id, autocareappointment);
        if (autocareappointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autocareappointment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autocareappointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autocareappointment = autocareappointmentRepository.save(autocareappointment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autocareappointment.getId().toString()))
            .body(autocareappointment);
    }

    /**
     * {@code PATCH  /autocareappointments/:id} : Partial updates given fields of an existing autocareappointment, field will ignore if it is null
     *
     * @param id the id of the autocareappointment to save.
     * @param autocareappointment the autocareappointment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autocareappointment,
     * or with status {@code 400 (Bad Request)} if the autocareappointment is not valid,
     * or with status {@code 404 (Not Found)} if the autocareappointment is not found,
     * or with status {@code 500 (Internal Server Error)} if the autocareappointment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autocareappointment> partialUpdateAutocareappointment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autocareappointment autocareappointment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Autocareappointment partially : {}, {}", id, autocareappointment);
        if (autocareappointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autocareappointment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autocareappointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autocareappointment> result = autocareappointmentRepository
            .findById(autocareappointment.getId())
            .map(existingAutocareappointment -> {
                if (autocareappointment.getAppointmenttype() != null) {
                    existingAutocareappointment.setAppointmenttype(autocareappointment.getAppointmenttype());
                }
                if (autocareappointment.getAppointmentdate() != null) {
                    existingAutocareappointment.setAppointmentdate(autocareappointment.getAppointmentdate());
                }
                if (autocareappointment.getAddeddate() != null) {
                    existingAutocareappointment.setAddeddate(autocareappointment.getAddeddate());
                }
                if (autocareappointment.getConformdate() != null) {
                    existingAutocareappointment.setConformdate(autocareappointment.getConformdate());
                }
                if (autocareappointment.getAppointmentnumber() != null) {
                    existingAutocareappointment.setAppointmentnumber(autocareappointment.getAppointmentnumber());
                }
                if (autocareappointment.getVehiclenumber() != null) {
                    existingAutocareappointment.setVehiclenumber(autocareappointment.getVehiclenumber());
                }
                if (autocareappointment.getAppointmenttime() != null) {
                    existingAutocareappointment.setAppointmenttime(autocareappointment.getAppointmenttime());
                }
                if (autocareappointment.getIsconformed() != null) {
                    existingAutocareappointment.setIsconformed(autocareappointment.getIsconformed());
                }
                if (autocareappointment.getConformedby() != null) {
                    existingAutocareappointment.setConformedby(autocareappointment.getConformedby());
                }
                if (autocareappointment.getLmd() != null) {
                    existingAutocareappointment.setLmd(autocareappointment.getLmd());
                }
                if (autocareappointment.getLmu() != null) {
                    existingAutocareappointment.setLmu(autocareappointment.getLmu());
                }
                if (autocareappointment.getCustomerid() != null) {
                    existingAutocareappointment.setCustomerid(autocareappointment.getCustomerid());
                }
                if (autocareappointment.getContactnumber() != null) {
                    existingAutocareappointment.setContactnumber(autocareappointment.getContactnumber());
                }
                if (autocareappointment.getCustomername() != null) {
                    existingAutocareappointment.setCustomername(autocareappointment.getCustomername());
                }
                if (autocareappointment.getIssued() != null) {
                    existingAutocareappointment.setIssued(autocareappointment.getIssued());
                }
                if (autocareappointment.getHoistid() != null) {
                    existingAutocareappointment.setHoistid(autocareappointment.getHoistid());
                }
                if (autocareappointment.getIsarrived() != null) {
                    existingAutocareappointment.setIsarrived(autocareappointment.getIsarrived());
                }
                if (autocareappointment.getIscancel() != null) {
                    existingAutocareappointment.setIscancel(autocareappointment.getIscancel());
                }
                if (autocareappointment.getIsnoanswer() != null) {
                    existingAutocareappointment.setIsnoanswer(autocareappointment.getIsnoanswer());
                }
                if (autocareappointment.getMissedappointmentcall() != null) {
                    existingAutocareappointment.setMissedappointmentcall(autocareappointment.getMissedappointmentcall());
                }
                if (autocareappointment.getCustomermobileid() != null) {
                    existingAutocareappointment.setCustomermobileid(autocareappointment.getCustomermobileid());
                }
                if (autocareappointment.getCustomermobilevehicleid() != null) {
                    existingAutocareappointment.setCustomermobilevehicleid(autocareappointment.getCustomermobilevehicleid());
                }
                if (autocareappointment.getVehicleid() != null) {
                    existingAutocareappointment.setVehicleid(autocareappointment.getVehicleid());
                }
                if (autocareappointment.getIsmobileappointment() != null) {
                    existingAutocareappointment.setIsmobileappointment(autocareappointment.getIsmobileappointment());
                }
                if (autocareappointment.getAdvancepayment() != null) {
                    existingAutocareappointment.setAdvancepayment(autocareappointment.getAdvancepayment());
                }
                if (autocareappointment.getJobid() != null) {
                    existingAutocareappointment.setJobid(autocareappointment.getJobid());
                }

                return existingAutocareappointment;
            })
            .map(autocareappointmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autocareappointment.getId().toString())
        );
    }

    /**
     * {@code GET  /autocareappointments} : get all the autocareappointments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autocareappointments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autocareappointment>> getAllAutocareappointments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Autocareappointments");
        Page<Autocareappointment> page = autocareappointmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autocareappointments/:id} : get the "id" autocareappointment.
     *
     * @param id the id of the autocareappointment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autocareappointment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autocareappointment> getAutocareappointment(@PathVariable("id") Long id) {
        log.debug("REST request to get Autocareappointment : {}", id);
        Optional<Autocareappointment> autocareappointment = autocareappointmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autocareappointment);
    }

    /**
     * {@code DELETE  /autocareappointments/:id} : delete the "id" autocareappointment.
     *
     * @param id the id of the autocareappointment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutocareappointment(@PathVariable("id") Long id) {
        log.debug("REST request to delete Autocareappointment : {}", id);
        autocareappointmentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
