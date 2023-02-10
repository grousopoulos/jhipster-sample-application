package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EventReportRepository;
import com.mycompany.myapp.service.EventReportService;
import com.mycompany.myapp.service.dto.EventReportDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.EventReport}.
 */
@RestController
@RequestMapping("/api")
public class EventReportResource {

    private final Logger log = LoggerFactory.getLogger(EventReportResource.class);

    private static final String ENTITY_NAME = "eventReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventReportService eventReportService;

    private final EventReportRepository eventReportRepository;

    public EventReportResource(EventReportService eventReportService, EventReportRepository eventReportRepository) {
        this.eventReportService = eventReportService;
        this.eventReportRepository = eventReportRepository;
    }

    /**
     * {@code POST  /event-reports} : Create a new eventReport.
     *
     * @param eventReportDTO the eventReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventReportDTO, or with status {@code 400 (Bad Request)} if the eventReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-reports")
    public ResponseEntity<EventReportDTO> createEventReport(@Valid @RequestBody EventReportDTO eventReportDTO) throws URISyntaxException {
        log.debug("REST request to save EventReport : {}", eventReportDTO);
        if (eventReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventReportDTO result = eventReportService.save(eventReportDTO);
        return ResponseEntity
            .created(new URI("/api/event-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-reports/:id} : Updates an existing eventReport.
     *
     * @param id the id of the eventReportDTO to save.
     * @param eventReportDTO the eventReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportDTO,
     * or with status {@code 400 (Bad Request)} if the eventReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-reports/{id}")
    public ResponseEntity<EventReportDTO> updateEventReport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventReportDTO eventReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventReport : {}, {}", id, eventReportDTO);
        if (eventReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventReportDTO result = eventReportService.update(eventReportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-reports/:id} : Partial updates given fields of an existing eventReport, field will ignore if it is null
     *
     * @param id the id of the eventReportDTO to save.
     * @param eventReportDTO the eventReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportDTO,
     * or with status {@code 400 (Bad Request)} if the eventReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventReportDTO> partialUpdateEventReport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventReportDTO eventReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventReport partially : {}, {}", id, eventReportDTO);
        if (eventReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventReportDTO> result = eventReportService.partialUpdate(eventReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-reports} : get all the eventReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventReports in body.
     */
    @GetMapping("/event-reports")
    public List<EventReportDTO> getAllEventReports() {
        log.debug("REST request to get all EventReports");
        return eventReportService.findAll();
    }

    /**
     * {@code GET  /event-reports/:id} : get the "id" eventReport.
     *
     * @param id the id of the eventReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-reports/{id}")
    public ResponseEntity<EventReportDTO> getEventReport(@PathVariable Long id) {
        log.debug("REST request to get EventReport : {}", id);
        Optional<EventReportDTO> eventReportDTO = eventReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventReportDTO);
    }

    /**
     * {@code DELETE  /event-reports/:id} : delete the "id" eventReport.
     *
     * @param id the id of the eventReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-reports/{id}")
    public ResponseEntity<Void> deleteEventReport(@PathVariable Long id) {
        log.debug("REST request to delete EventReport : {}", id);
        eventReportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
