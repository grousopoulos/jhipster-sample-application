package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.FlagRepository;
import com.mycompany.myapp.service.FlagService;
import com.mycompany.myapp.service.dto.FlagDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Flag}.
 */
@RestController
@RequestMapping("/api")
public class FlagResource {

    private final Logger log = LoggerFactory.getLogger(FlagResource.class);

    private static final String ENTITY_NAME = "flag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlagService flagService;

    private final FlagRepository flagRepository;

    public FlagResource(FlagService flagService, FlagRepository flagRepository) {
        this.flagService = flagService;
        this.flagRepository = flagRepository;
    }

    /**
     * {@code POST  /flags} : Create a new flag.
     *
     * @param flagDTO the flagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flagDTO, or with status {@code 400 (Bad Request)} if the flag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flags")
    public ResponseEntity<FlagDTO> createFlag(@Valid @RequestBody FlagDTO flagDTO) throws URISyntaxException {
        log.debug("REST request to save Flag : {}", flagDTO);
        if (flagDTO.getId() != null) {
            throw new BadRequestAlertException("A new flag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FlagDTO result = flagService.save(flagDTO);
        return ResponseEntity
            .created(new URI("/api/flags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flags/:id} : Updates an existing flag.
     *
     * @param id the id of the flagDTO to save.
     * @param flagDTO the flagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flagDTO,
     * or with status {@code 400 (Bad Request)} if the flagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flags/{id}")
    public ResponseEntity<FlagDTO> updateFlag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FlagDTO flagDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Flag : {}, {}", id, flagDTO);
        if (flagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FlagDTO result = flagService.update(flagDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flags/:id} : Partial updates given fields of an existing flag, field will ignore if it is null
     *
     * @param id the id of the flagDTO to save.
     * @param flagDTO the flagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flagDTO,
     * or with status {@code 400 (Bad Request)} if the flagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the flagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the flagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flags/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FlagDTO> partialUpdateFlag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FlagDTO flagDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Flag partially : {}, {}", id, flagDTO);
        if (flagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FlagDTO> result = flagService.partialUpdate(flagDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flagDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /flags} : get all the flags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flags in body.
     */
    @GetMapping("/flags")
    public List<FlagDTO> getAllFlags() {
        log.debug("REST request to get all Flags");
        return flagService.findAll();
    }

    /**
     * {@code GET  /flags/:id} : get the "id" flag.
     *
     * @param id the id of the flagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flags/{id}")
    public ResponseEntity<FlagDTO> getFlag(@PathVariable Long id) {
        log.debug("REST request to get Flag : {}", id);
        Optional<FlagDTO> flagDTO = flagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flagDTO);
    }

    /**
     * {@code DELETE  /flags/:id} : delete the "id" flag.
     *
     * @param id the id of the flagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flags/{id}")
    public ResponseEntity<Void> deleteFlag(@PathVariable Long id) {
        log.debug("REST request to delete Flag : {}", id);
        flagService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
