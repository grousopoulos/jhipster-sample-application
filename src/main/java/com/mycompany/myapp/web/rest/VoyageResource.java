package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.VoyageRepository;
import com.mycompany.myapp.service.VoyageService;
import com.mycompany.myapp.service.dto.VoyageDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Voyage}.
 */
@RestController
@RequestMapping("/api")
public class VoyageResource {

    private final Logger log = LoggerFactory.getLogger(VoyageResource.class);

    private static final String ENTITY_NAME = "voyage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoyageService voyageService;

    private final VoyageRepository voyageRepository;

    public VoyageResource(VoyageService voyageService, VoyageRepository voyageRepository) {
        this.voyageService = voyageService;
        this.voyageRepository = voyageRepository;
    }

    /**
     * {@code POST  /voyages} : Create a new voyage.
     *
     * @param voyageDTO the voyageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voyageDTO, or with status {@code 400 (Bad Request)} if the voyage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voyages")
    public ResponseEntity<VoyageDTO> createVoyage(@Valid @RequestBody VoyageDTO voyageDTO) throws URISyntaxException {
        log.debug("REST request to save Voyage : {}", voyageDTO);
        if (voyageDTO.getId() != null) {
            throw new BadRequestAlertException("A new voyage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoyageDTO result = voyageService.save(voyageDTO);
        return ResponseEntity
            .created(new URI("/api/voyages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voyages/:id} : Updates an existing voyage.
     *
     * @param id the id of the voyageDTO to save.
     * @param voyageDTO the voyageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyageDTO,
     * or with status {@code 400 (Bad Request)} if the voyageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voyageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voyages/{id}")
    public ResponseEntity<VoyageDTO> updateVoyage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VoyageDTO voyageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Voyage : {}, {}", id, voyageDTO);
        if (voyageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VoyageDTO result = voyageService.update(voyageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voyageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voyages/:id} : Partial updates given fields of an existing voyage, field will ignore if it is null
     *
     * @param id the id of the voyageDTO to save.
     * @param voyageDTO the voyageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyageDTO,
     * or with status {@code 400 (Bad Request)} if the voyageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the voyageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the voyageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voyages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VoyageDTO> partialUpdateVoyage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VoyageDTO voyageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Voyage partially : {}, {}", id, voyageDTO);
        if (voyageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoyageDTO> result = voyageService.partialUpdate(voyageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voyageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /voyages} : get all the voyages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voyages in body.
     */
    @GetMapping("/voyages")
    public List<VoyageDTO> getAllVoyages() {
        log.debug("REST request to get all Voyages");
        return voyageService.findAll();
    }

    /**
     * {@code GET  /voyages/:id} : get the "id" voyage.
     *
     * @param id the id of the voyageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voyageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voyages/{id}")
    public ResponseEntity<VoyageDTO> getVoyage(@PathVariable Long id) {
        log.debug("REST request to get Voyage : {}", id);
        Optional<VoyageDTO> voyageDTO = voyageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voyageDTO);
    }

    /**
     * {@code DELETE  /voyages/:id} : delete the "id" voyage.
     *
     * @param id the id of the voyageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voyages/{id}")
    public ResponseEntity<Void> deleteVoyage(@PathVariable Long id) {
        log.debug("REST request to delete Voyage : {}", id);
        voyageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
