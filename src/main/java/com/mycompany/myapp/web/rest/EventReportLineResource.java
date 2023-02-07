package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EventReportLine;
import com.mycompany.myapp.repository.EventReportLineRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.EventReportLine}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EventReportLineResource {

    private final Logger log = LoggerFactory.getLogger(EventReportLineResource.class);

    private static final String ENTITY_NAME = "eventReportLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventReportLineRepository eventReportLineRepository;

    public EventReportLineResource(EventReportLineRepository eventReportLineRepository) {
        this.eventReportLineRepository = eventReportLineRepository;
    }

    /**
     * {@code POST  /event-report-lines} : Create a new eventReportLine.
     *
     * @param eventReportLine the eventReportLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventReportLine, or with status {@code 400 (Bad Request)} if the eventReportLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-report-lines")
    public ResponseEntity<EventReportLine> createEventReportLine(@RequestBody EventReportLine eventReportLine) throws URISyntaxException {
        log.debug("REST request to save EventReportLine : {}", eventReportLine);
        if (eventReportLine.getId() != null) {
            throw new BadRequestAlertException("A new eventReportLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventReportLine result = eventReportLineRepository.save(eventReportLine);
        return ResponseEntity
            .created(new URI("/api/event-report-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-report-lines/:id} : Updates an existing eventReportLine.
     *
     * @param id the id of the eventReportLine to save.
     * @param eventReportLine the eventReportLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportLine,
     * or with status {@code 400 (Bad Request)} if the eventReportLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventReportLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-report-lines/{id}")
    public ResponseEntity<EventReportLine> updateEventReportLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReportLine eventReportLine
    ) throws URISyntaxException {
        log.debug("REST request to update EventReportLine : {}, {}", id, eventReportLine);
        if (eventReportLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportLine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventReportLine result = eventReportLineRepository.save(eventReportLine);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReportLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-report-lines/:id} : Partial updates given fields of an existing eventReportLine, field will ignore if it is null
     *
     * @param id the id of the eventReportLine to save.
     * @param eventReportLine the eventReportLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportLine,
     * or with status {@code 400 (Bad Request)} if the eventReportLine is not valid,
     * or with status {@code 404 (Not Found)} if the eventReportLine is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventReportLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-report-lines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventReportLine> partialUpdateEventReportLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReportLine eventReportLine
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventReportLine partially : {}, {}", id, eventReportLine);
        if (eventReportLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportLine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventReportLine> result = eventReportLineRepository
            .findById(eventReportLine.getId())
            .map(existingEventReportLine -> {
                if (eventReportLine.getQuantity() != null) {
                    existingEventReportLine.setQuantity(eventReportLine.getQuantity());
                }
                if (eventReportLine.getUnitOfMeasure() != null) {
                    existingEventReportLine.setUnitOfMeasure(eventReportLine.getUnitOfMeasure());
                }

                return existingEventReportLine;
            })
            .map(eventReportLineRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReportLine.getId().toString())
        );
    }

    /**
     * {@code GET  /event-report-lines} : get all the eventReportLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventReportLines in body.
     */
    @GetMapping("/event-report-lines")
    public List<EventReportLine> getAllEventReportLines() {
        log.debug("REST request to get all EventReportLines");
        return eventReportLineRepository.findAll();
    }

    /**
     * {@code GET  /event-report-lines/:id} : get the "id" eventReportLine.
     *
     * @param id the id of the eventReportLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventReportLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-report-lines/{id}")
    public ResponseEntity<EventReportLine> getEventReportLine(@PathVariable Long id) {
        log.debug("REST request to get EventReportLine : {}", id);
        Optional<EventReportLine> eventReportLine = eventReportLineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eventReportLine);
    }

    /**
     * {@code DELETE  /event-report-lines/:id} : delete the "id" eventReportLine.
     *
     * @param id the id of the eventReportLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-report-lines/{id}")
    public ResponseEntity<Void> deleteEventReportLine(@PathVariable Long id) {
        log.debug("REST request to delete EventReportLine : {}", id);
        eventReportLineRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
