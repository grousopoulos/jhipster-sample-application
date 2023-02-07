package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Voyage;
import com.mycompany.myapp.repository.VoyageRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Voyage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VoyageResource {

    private final Logger log = LoggerFactory.getLogger(VoyageResource.class);

    private static final String ENTITY_NAME = "voyage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoyageRepository voyageRepository;

    public VoyageResource(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    /**
     * {@code POST  /voyages} : Create a new voyage.
     *
     * @param voyage the voyage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voyage, or with status {@code 400 (Bad Request)} if the voyage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voyages")
    public ResponseEntity<Voyage> createVoyage(@Valid @RequestBody Voyage voyage) throws URISyntaxException {
        log.debug("REST request to save Voyage : {}", voyage);
        if (voyage.getId() != null) {
            throw new BadRequestAlertException("A new voyage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Voyage result = voyageRepository.save(voyage);
        return ResponseEntity
            .created(new URI("/api/voyages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voyages/:id} : Updates an existing voyage.
     *
     * @param id the id of the voyage to save.
     * @param voyage the voyage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyage,
     * or with status {@code 400 (Bad Request)} if the voyage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voyage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voyages/{id}")
    public ResponseEntity<Voyage> updateVoyage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Voyage voyage
    ) throws URISyntaxException {
        log.debug("REST request to update Voyage : {}, {}", id, voyage);
        if (voyage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Voyage result = voyageRepository.save(voyage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voyage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voyages/:id} : Partial updates given fields of an existing voyage, field will ignore if it is null
     *
     * @param id the id of the voyage to save.
     * @param voyage the voyage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyage,
     * or with status {@code 400 (Bad Request)} if the voyage is not valid,
     * or with status {@code 404 (Not Found)} if the voyage is not found,
     * or with status {@code 500 (Internal Server Error)} if the voyage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voyages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Voyage> partialUpdateVoyage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Voyage voyage
    ) throws URISyntaxException {
        log.debug("REST request to partial update Voyage partially : {}, {}", id, voyage);
        if (voyage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Voyage> result = voyageRepository
            .findById(voyage.getId())
            .map(existingVoyage -> {
                if (voyage.getNumber() != null) {
                    existingVoyage.setNumber(voyage.getNumber());
                }

                return existingVoyage;
            })
            .map(voyageRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voyage.getId().toString())
        );
    }

    /**
     * {@code GET  /voyages} : get all the voyages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voyages in body.
     */
    @GetMapping("/voyages")
    public List<Voyage> getAllVoyages() {
        log.debug("REST request to get all Voyages");
        return voyageRepository.findAll();
    }

    /**
     * {@code GET  /voyages/:id} : get the "id" voyage.
     *
     * @param id the id of the voyage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voyage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voyages/{id}")
    public ResponseEntity<Voyage> getVoyage(@PathVariable Long id) {
        log.debug("REST request to get Voyage : {}", id);
        Optional<Voyage> voyage = voyageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voyage);
    }

    /**
     * {@code DELETE  /voyages/:id} : delete the "id" voyage.
     *
     * @param id the id of the voyage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voyages/{id}")
    public ResponseEntity<Void> deleteVoyage(@PathVariable Long id) {
        log.debug("REST request to delete Voyage : {}", id);
        voyageRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
