package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BunkerReceivedNoteLine;
import com.mycompany.myapp.repository.BunkerReceivedNoteLineRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BunkerReceivedNoteLine}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BunkerReceivedNoteLineResource {

    private final Logger log = LoggerFactory.getLogger(BunkerReceivedNoteLineResource.class);

    private static final String ENTITY_NAME = "bunkerReceivedNoteLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository;

    public BunkerReceivedNoteLineResource(BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository) {
        this.bunkerReceivedNoteLineRepository = bunkerReceivedNoteLineRepository;
    }

    /**
     * {@code POST  /bunker-received-note-lines} : Create a new bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLine the bunkerReceivedNoteLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bunkerReceivedNoteLine, or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bunker-received-note-lines")
    public ResponseEntity<BunkerReceivedNoteLine> createBunkerReceivedNoteLine(@RequestBody BunkerReceivedNoteLine bunkerReceivedNoteLine)
        throws URISyntaxException {
        log.debug("REST request to save BunkerReceivedNoteLine : {}", bunkerReceivedNoteLine);
        if (bunkerReceivedNoteLine.getId() != null) {
            throw new BadRequestAlertException("A new bunkerReceivedNoteLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BunkerReceivedNoteLine result = bunkerReceivedNoteLineRepository.save(bunkerReceivedNoteLine);
        return ResponseEntity
            .created(new URI("/api/bunker-received-note-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bunker-received-note-lines/:id} : Updates an existing bunkerReceivedNoteLine.
     *
     * @param id the id of the bunkerReceivedNoteLine to save.
     * @param bunkerReceivedNoteLine the bunkerReceivedNoteLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteLine,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bunker-received-note-lines/{id}")
    public ResponseEntity<BunkerReceivedNoteLine> updateBunkerReceivedNoteLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BunkerReceivedNoteLine bunkerReceivedNoteLine
    ) throws URISyntaxException {
        log.debug("REST request to update BunkerReceivedNoteLine : {}, {}", id, bunkerReceivedNoteLine);
        if (bunkerReceivedNoteLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteLine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BunkerReceivedNoteLine result = bunkerReceivedNoteLineRepository.save(bunkerReceivedNoteLine);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bunkerReceivedNoteLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bunker-received-note-lines/:id} : Partial updates given fields of an existing bunkerReceivedNoteLine, field will ignore if it is null
     *
     * @param id the id of the bunkerReceivedNoteLine to save.
     * @param bunkerReceivedNoteLine the bunkerReceivedNoteLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteLine,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteLine is not valid,
     * or with status {@code 404 (Not Found)} if the bunkerReceivedNoteLine is not found,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bunker-received-note-lines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BunkerReceivedNoteLine> partialUpdateBunkerReceivedNoteLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BunkerReceivedNoteLine bunkerReceivedNoteLine
    ) throws URISyntaxException {
        log.debug("REST request to partial update BunkerReceivedNoteLine partially : {}, {}", id, bunkerReceivedNoteLine);
        if (bunkerReceivedNoteLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteLine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BunkerReceivedNoteLine> result = bunkerReceivedNoteLineRepository
            .findById(bunkerReceivedNoteLine.getId())
            .map(existingBunkerReceivedNoteLine -> {
                if (bunkerReceivedNoteLine.getQuantity() != null) {
                    existingBunkerReceivedNoteLine.setQuantity(bunkerReceivedNoteLine.getQuantity());
                }

                return existingBunkerReceivedNoteLine;
            })
            .map(bunkerReceivedNoteLineRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bunkerReceivedNoteLine.getId().toString())
        );
    }

    /**
     * {@code GET  /bunker-received-note-lines} : get all the bunkerReceivedNoteLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bunkerReceivedNoteLines in body.
     */
    @GetMapping("/bunker-received-note-lines")
    public List<BunkerReceivedNoteLine> getAllBunkerReceivedNoteLines() {
        log.debug("REST request to get all BunkerReceivedNoteLines");
        return bunkerReceivedNoteLineRepository.findAll();
    }

    /**
     * {@code GET  /bunker-received-note-lines/:id} : get the "id" bunkerReceivedNoteLine.
     *
     * @param id the id of the bunkerReceivedNoteLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bunkerReceivedNoteLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bunker-received-note-lines/{id}")
    public ResponseEntity<BunkerReceivedNoteLine> getBunkerReceivedNoteLine(@PathVariable Long id) {
        log.debug("REST request to get BunkerReceivedNoteLine : {}", id);
        Optional<BunkerReceivedNoteLine> bunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bunkerReceivedNoteLine);
    }

    /**
     * {@code DELETE  /bunker-received-note-lines/:id} : delete the "id" bunkerReceivedNoteLine.
     *
     * @param id the id of the bunkerReceivedNoteLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bunker-received-note-lines/{id}")
    public ResponseEntity<Void> deleteBunkerReceivedNoteLine(@PathVariable Long id) {
        log.debug("REST request to delete BunkerReceivedNoteLine : {}", id);
        bunkerReceivedNoteLineRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
