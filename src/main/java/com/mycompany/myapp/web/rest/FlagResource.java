package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Flag;
import com.mycompany.myapp.repository.FlagRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Flag}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FlagResource {

    private final Logger log = LoggerFactory.getLogger(FlagResource.class);

    private static final String ENTITY_NAME = "flag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlagRepository flagRepository;

    public FlagResource(FlagRepository flagRepository) {
        this.flagRepository = flagRepository;
    }

    /**
     * {@code POST  /flags} : Create a new flag.
     *
     * @param flag the flag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flag, or with status {@code 400 (Bad Request)} if the flag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flags")
    public ResponseEntity<Flag> createFlag(@Valid @RequestBody Flag flag) throws URISyntaxException {
        log.debug("REST request to save Flag : {}", flag);
        if (flag.getId() != null) {
            throw new BadRequestAlertException("A new flag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Flag result = flagRepository.save(flag);
        return ResponseEntity
            .created(new URI("/api/flags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flags/:id} : Updates an existing flag.
     *
     * @param id the id of the flag to save.
     * @param flag the flag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flag,
     * or with status {@code 400 (Bad Request)} if the flag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flags/{id}")
    public ResponseEntity<Flag> updateFlag(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Flag flag)
        throws URISyntaxException {
        log.debug("REST request to update Flag : {}, {}", id, flag);
        if (flag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Flag result = flagRepository.save(flag);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flag.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flags/:id} : Partial updates given fields of an existing flag, field will ignore if it is null
     *
     * @param id the id of the flag to save.
     * @param flag the flag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flag,
     * or with status {@code 400 (Bad Request)} if the flag is not valid,
     * or with status {@code 404 (Not Found)} if the flag is not found,
     * or with status {@code 500 (Internal Server Error)} if the flag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flags/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Flag> partialUpdateFlag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Flag flag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Flag partially : {}, {}", id, flag);
        if (flag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Flag> result = flagRepository
            .findById(flag.getId())
            .map(existingFlag -> {
                if (flag.getCode() != null) {
                    existingFlag.setCode(flag.getCode());
                }
                if (flag.getName() != null) {
                    existingFlag.setName(flag.getName());
                }

                return existingFlag;
            })
            .map(flagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flag.getId().toString())
        );
    }

    /**
     * {@code GET  /flags} : get all the flags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flags in body.
     */
    @GetMapping("/flags")
    public List<Flag> getAllFlags() {
        log.debug("REST request to get all Flags");
        return flagRepository.findAll();
    }

    /**
     * {@code GET  /flags/:id} : get the "id" flag.
     *
     * @param id the id of the flag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flags/{id}")
    public ResponseEntity<Flag> getFlag(@PathVariable Long id) {
        log.debug("REST request to get Flag : {}", id);
        Optional<Flag> flag = flagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(flag);
    }

    /**
     * {@code DELETE  /flags/:id} : delete the "id" flag.
     *
     * @param id the id of the flag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flags/{id}")
    public ResponseEntity<Void> deleteFlag(@PathVariable Long id) {
        log.debug("REST request to delete Flag : {}", id);
        flagRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
