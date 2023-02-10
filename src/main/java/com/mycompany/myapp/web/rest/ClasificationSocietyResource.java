package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ClasificationSocietyRepository;
import com.mycompany.myapp.service.ClasificationSocietyService;
import com.mycompany.myapp.service.dto.ClasificationSocietyDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ClasificationSociety}.
 */
@RestController
@RequestMapping("/api")
public class ClasificationSocietyResource {

    private final Logger log = LoggerFactory.getLogger(ClasificationSocietyResource.class);

    private static final String ENTITY_NAME = "clasificationSociety";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasificationSocietyService clasificationSocietyService;

    private final ClasificationSocietyRepository clasificationSocietyRepository;

    public ClasificationSocietyResource(
        ClasificationSocietyService clasificationSocietyService,
        ClasificationSocietyRepository clasificationSocietyRepository
    ) {
        this.clasificationSocietyService = clasificationSocietyService;
        this.clasificationSocietyRepository = clasificationSocietyRepository;
    }

    /**
     * {@code POST  /clasification-societies} : Create a new clasificationSociety.
     *
     * @param clasificationSocietyDTO the clasificationSocietyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clasificationSocietyDTO, or with status {@code 400 (Bad Request)} if the clasificationSociety has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clasification-societies")
    public ResponseEntity<ClasificationSocietyDTO> createClasificationSociety(
        @Valid @RequestBody ClasificationSocietyDTO clasificationSocietyDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ClasificationSociety : {}", clasificationSocietyDTO);
        if (clasificationSocietyDTO.getId() != null) {
            throw new BadRequestAlertException("A new clasificationSociety cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasificationSocietyDTO result = clasificationSocietyService.save(clasificationSocietyDTO);
        return ResponseEntity
            .created(new URI("/api/clasification-societies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clasification-societies/:id} : Updates an existing clasificationSociety.
     *
     * @param id the id of the clasificationSocietyDTO to save.
     * @param clasificationSocietyDTO the clasificationSocietyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clasificationSocietyDTO,
     * or with status {@code 400 (Bad Request)} if the clasificationSocietyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clasificationSocietyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clasification-societies/{id}")
    public ResponseEntity<ClasificationSocietyDTO> updateClasificationSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClasificationSocietyDTO clasificationSocietyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClasificationSociety : {}, {}", id, clasificationSocietyDTO);
        if (clasificationSocietyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clasificationSocietyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clasificationSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClasificationSocietyDTO result = clasificationSocietyService.update(clasificationSocietyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clasificationSocietyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clasification-societies/:id} : Partial updates given fields of an existing clasificationSociety, field will ignore if it is null
     *
     * @param id the id of the clasificationSocietyDTO to save.
     * @param clasificationSocietyDTO the clasificationSocietyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clasificationSocietyDTO,
     * or with status {@code 400 (Bad Request)} if the clasificationSocietyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clasificationSocietyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clasificationSocietyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clasification-societies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClasificationSocietyDTO> partialUpdateClasificationSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClasificationSocietyDTO clasificationSocietyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClasificationSociety partially : {}, {}", id, clasificationSocietyDTO);
        if (clasificationSocietyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clasificationSocietyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clasificationSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClasificationSocietyDTO> result = clasificationSocietyService.partialUpdate(clasificationSocietyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clasificationSocietyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clasification-societies} : get all the clasificationSocieties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clasificationSocieties in body.
     */
    @GetMapping("/clasification-societies")
    public List<ClasificationSocietyDTO> getAllClasificationSocieties() {
        log.debug("REST request to get all ClasificationSocieties");
        return clasificationSocietyService.findAll();
    }

    /**
     * {@code GET  /clasification-societies/:id} : get the "id" clasificationSociety.
     *
     * @param id the id of the clasificationSocietyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clasificationSocietyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clasification-societies/{id}")
    public ResponseEntity<ClasificationSocietyDTO> getClasificationSociety(@PathVariable Long id) {
        log.debug("REST request to get ClasificationSociety : {}", id);
        Optional<ClasificationSocietyDTO> clasificationSocietyDTO = clasificationSocietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clasificationSocietyDTO);
    }

    /**
     * {@code DELETE  /clasification-societies/:id} : delete the "id" clasificationSociety.
     *
     * @param id the id of the clasificationSocietyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clasification-societies/{id}")
    public ResponseEntity<Void> deleteClasificationSociety(@PathVariable Long id) {
        log.debug("REST request to delete ClasificationSociety : {}", id);
        clasificationSocietyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
