package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.BunkerReceivedNoteRepository;
import com.mycompany.myapp.service.BunkerReceivedNoteService;
import com.mycompany.myapp.service.dto.BunkerReceivedNoteDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BunkerReceivedNote}.
 */
@RestController
@RequestMapping("/api")
public class BunkerReceivedNoteResource {

    private final Logger log = LoggerFactory.getLogger(BunkerReceivedNoteResource.class);

    private static final String ENTITY_NAME = "bunkerReceivedNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BunkerReceivedNoteService bunkerReceivedNoteService;

    private final BunkerReceivedNoteRepository bunkerReceivedNoteRepository;

    public BunkerReceivedNoteResource(
        BunkerReceivedNoteService bunkerReceivedNoteService,
        BunkerReceivedNoteRepository bunkerReceivedNoteRepository
    ) {
        this.bunkerReceivedNoteService = bunkerReceivedNoteService;
        this.bunkerReceivedNoteRepository = bunkerReceivedNoteRepository;
    }

    /**
     * {@code POST  /bunker-received-notes} : Create a new bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the bunkerReceivedNoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bunkerReceivedNoteDTO, or with status {@code 400 (Bad Request)} if the bunkerReceivedNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bunker-received-notes")
    public ResponseEntity<BunkerReceivedNoteDTO> createBunkerReceivedNote(@Valid @RequestBody BunkerReceivedNoteDTO bunkerReceivedNoteDTO)
        throws URISyntaxException {
        log.debug("REST request to save BunkerReceivedNote : {}", bunkerReceivedNoteDTO);
        if (bunkerReceivedNoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new bunkerReceivedNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BunkerReceivedNoteDTO result = bunkerReceivedNoteService.save(bunkerReceivedNoteDTO);
        return ResponseEntity
            .created(new URI("/api/bunker-received-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bunker-received-notes/:id} : Updates an existing bunkerReceivedNote.
     *
     * @param id the id of the bunkerReceivedNoteDTO to save.
     * @param bunkerReceivedNoteDTO the bunkerReceivedNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteDTO,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bunker-received-notes/{id}")
    public ResponseEntity<BunkerReceivedNoteDTO> updateBunkerReceivedNote(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BunkerReceivedNoteDTO bunkerReceivedNoteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BunkerReceivedNote : {}, {}", id, bunkerReceivedNoteDTO);
        if (bunkerReceivedNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BunkerReceivedNoteDTO result = bunkerReceivedNoteService.update(bunkerReceivedNoteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bunkerReceivedNoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bunker-received-notes/:id} : Partial updates given fields of an existing bunkerReceivedNote, field will ignore if it is null
     *
     * @param id the id of the bunkerReceivedNoteDTO to save.
     * @param bunkerReceivedNoteDTO the bunkerReceivedNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteDTO,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bunkerReceivedNoteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bunker-received-notes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BunkerReceivedNoteDTO> partialUpdateBunkerReceivedNote(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BunkerReceivedNoteDTO bunkerReceivedNoteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BunkerReceivedNote partially : {}, {}", id, bunkerReceivedNoteDTO);
        if (bunkerReceivedNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BunkerReceivedNoteDTO> result = bunkerReceivedNoteService.partialUpdate(bunkerReceivedNoteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bunkerReceivedNoteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bunker-received-notes} : get all the bunkerReceivedNotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bunkerReceivedNotes in body.
     */
    @GetMapping("/bunker-received-notes")
    public List<BunkerReceivedNoteDTO> getAllBunkerReceivedNotes() {
        log.debug("REST request to get all BunkerReceivedNotes");
        return bunkerReceivedNoteService.findAll();
    }

    /**
     * {@code GET  /bunker-received-notes/:id} : get the "id" bunkerReceivedNote.
     *
     * @param id the id of the bunkerReceivedNoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bunkerReceivedNoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bunker-received-notes/{id}")
    public ResponseEntity<BunkerReceivedNoteDTO> getBunkerReceivedNote(@PathVariable Long id) {
        log.debug("REST request to get BunkerReceivedNote : {}", id);
        Optional<BunkerReceivedNoteDTO> bunkerReceivedNoteDTO = bunkerReceivedNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bunkerReceivedNoteDTO);
    }

    /**
     * {@code DELETE  /bunker-received-notes/:id} : delete the "id" bunkerReceivedNote.
     *
     * @param id the id of the bunkerReceivedNoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bunker-received-notes/{id}")
    public ResponseEntity<Void> deleteBunkerReceivedNote(@PathVariable Long id) {
        log.debug("REST request to delete BunkerReceivedNote : {}", id);
        bunkerReceivedNoteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
