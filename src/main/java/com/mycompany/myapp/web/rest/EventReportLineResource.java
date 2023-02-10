package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EventReportLineRepository;
import com.mycompany.myapp.service.EventReportLineService;
import com.mycompany.myapp.service.dto.EventReportLineDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.EventReportLine}.
 */
@RestController
@RequestMapping("/api")
public class EventReportLineResource {

    private final Logger log = LoggerFactory.getLogger(EventReportLineResource.class);

    private static final String ENTITY_NAME = "eventReportLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventReportLineService eventReportLineService;

    private final EventReportLineRepository eventReportLineRepository;

    public EventReportLineResource(EventReportLineService eventReportLineService, EventReportLineRepository eventReportLineRepository) {
        this.eventReportLineService = eventReportLineService;
        this.eventReportLineRepository = eventReportLineRepository;
    }

    /**
     * {@code POST  /event-report-lines} : Create a new eventReportLine.
     *
     * @param eventReportLineDTO the eventReportLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventReportLineDTO, or with status {@code 400 (Bad Request)} if the eventReportLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-report-lines")
    public ResponseEntity<EventReportLineDTO> createEventReportLine(@RequestBody EventReportLineDTO eventReportLineDTO)
        throws URISyntaxException {
        log.debug("REST request to save EventReportLine : {}", eventReportLineDTO);
        if (eventReportLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventReportLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventReportLineDTO result = eventReportLineService.save(eventReportLineDTO);
        return ResponseEntity
            .created(new URI("/api/event-report-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-report-lines/:id} : Updates an existing eventReportLine.
     *
     * @param id the id of the eventReportLineDTO to save.
     * @param eventReportLineDTO the eventReportLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportLineDTO,
     * or with status {@code 400 (Bad Request)} if the eventReportLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventReportLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-report-lines/{id}")
    public ResponseEntity<EventReportLineDTO> updateEventReportLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReportLineDTO eventReportLineDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventReportLine : {}, {}", id, eventReportLineDTO);
        if (eventReportLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventReportLineDTO result = eventReportLineService.update(eventReportLineDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReportLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-report-lines/:id} : Partial updates given fields of an existing eventReportLine, field will ignore if it is null
     *
     * @param id the id of the eventReportLineDTO to save.
     * @param eventReportLineDTO the eventReportLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportLineDTO,
     * or with status {@code 400 (Bad Request)} if the eventReportLineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventReportLineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventReportLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-report-lines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventReportLineDTO> partialUpdateEventReportLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReportLineDTO eventReportLineDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventReportLine partially : {}, {}", id, eventReportLineDTO);
        if (eventReportLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventReportLineDTO> result = eventReportLineService.partialUpdate(eventReportLineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReportLineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-report-lines} : get all the eventReportLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventReportLines in body.
     */
    @GetMapping("/event-report-lines")
    public List<EventReportLineDTO> getAllEventReportLines() {
        log.debug("REST request to get all EventReportLines");
        return eventReportLineService.findAll();
    }

    /**
     * {@code GET  /event-report-lines/:id} : get the "id" eventReportLine.
     *
     * @param id the id of the eventReportLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventReportLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-report-lines/{id}")
    public ResponseEntity<EventReportLineDTO> getEventReportLine(@PathVariable Long id) {
        log.debug("REST request to get EventReportLine : {}", id);
        Optional<EventReportLineDTO> eventReportLineDTO = eventReportLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventReportLineDTO);
    }

    /**
     * {@code DELETE  /event-report-lines/:id} : delete the "id" eventReportLine.
     *
     * @param id the id of the eventReportLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-report-lines/{id}")
    public ResponseEntity<Void> deleteEventReportLine(@PathVariable Long id) {
        log.debug("REST request to delete EventReportLine : {}", id);
        eventReportLineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
