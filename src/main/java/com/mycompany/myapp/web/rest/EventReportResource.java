package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EventReport;
import com.mycompany.myapp.repository.EventReportRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.EventReport}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EventReportResource {

    private final Logger log = LoggerFactory.getLogger(EventReportResource.class);

    private static final String ENTITY_NAME = "eventReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventReportRepository eventReportRepository;

    public EventReportResource(EventReportRepository eventReportRepository) {
        this.eventReportRepository = eventReportRepository;
    }

    /**
     * {@code POST  /event-reports} : Create a new eventReport.
     *
     * @param eventReport the eventReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventReport, or with status {@code 400 (Bad Request)} if the eventReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-reports")
    public ResponseEntity<EventReport> createEventReport(@Valid @RequestBody EventReport eventReport) throws URISyntaxException {
        log.debug("REST request to save EventReport : {}", eventReport);
        if (eventReport.getId() != null) {
            throw new BadRequestAlertException("A new eventReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventReport result = eventReportRepository.save(eventReport);
        return ResponseEntity
            .created(new URI("/api/event-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-reports/:id} : Updates an existing eventReport.
     *
     * @param id the id of the eventReport to save.
     * @param eventReport the eventReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReport,
     * or with status {@code 400 (Bad Request)} if the eventReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-reports/{id}")
    public ResponseEntity<EventReport> updateEventReport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventReport eventReport
    ) throws URISyntaxException {
        log.debug("REST request to update EventReport : {}, {}", id, eventReport);
        if (eventReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventReport result = eventReportRepository.save(eventReport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-reports/:id} : Partial updates given fields of an existing eventReport, field will ignore if it is null
     *
     * @param id the id of the eventReport to save.
     * @param eventReport the eventReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReport,
     * or with status {@code 400 (Bad Request)} if the eventReport is not valid,
     * or with status {@code 404 (Not Found)} if the eventReport is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventReport> partialUpdateEventReport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventReport eventReport
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventReport partially : {}, {}", id, eventReport);
        if (eventReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventReport> result = eventReportRepository
            .findById(eventReport.getId())
            .map(existingEventReport -> {
                if (eventReport.getDocumentDateAndTime() != null) {
                    existingEventReport.setDocumentDateAndTime(eventReport.getDocumentDateAndTime());
                }
                if (eventReport.getSpeedGps() != null) {
                    existingEventReport.setSpeedGps(eventReport.getSpeedGps());
                }
                if (eventReport.getDocumentDisplayNumber() != null) {
                    existingEventReport.setDocumentDisplayNumber(eventReport.getDocumentDisplayNumber());
                }

                return existingEventReport;
            })
            .map(eventReportRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReport.getId().toString())
        );
    }

    /**
     * {@code GET  /event-reports} : get all the eventReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventReports in body.
     */
    @GetMapping("/event-reports")
    public List<EventReport> getAllEventReports() {
        log.debug("REST request to get all EventReports");
        return eventReportRepository.findAll();
    }

    /**
     * {@code GET  /event-reports/:id} : get the "id" eventReport.
     *
     * @param id the id of the eventReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-reports/{id}")
    public ResponseEntity<EventReport> getEventReport(@PathVariable Long id) {
        log.debug("REST request to get EventReport : {}", id);
        Optional<EventReport> eventReport = eventReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eventReport);
    }

    /**
     * {@code DELETE  /event-reports/:id} : delete the "id" eventReport.
     *
     * @param id the id of the eventReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-reports/{id}")
    public ResponseEntity<Void> deleteEventReport(@PathVariable Long id) {
        log.debug("REST request to delete EventReport : {}", id);
        eventReportRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
