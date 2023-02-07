package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ClasificationSociety;
import com.mycompany.myapp.repository.ClasificationSocietyRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ClasificationSociety}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClasificationSocietyResource {

    private final Logger log = LoggerFactory.getLogger(ClasificationSocietyResource.class);

    private static final String ENTITY_NAME = "clasificationSociety";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasificationSocietyRepository clasificationSocietyRepository;

    public ClasificationSocietyResource(ClasificationSocietyRepository clasificationSocietyRepository) {
        this.clasificationSocietyRepository = clasificationSocietyRepository;
    }

    /**
     * {@code POST  /clasification-societies} : Create a new clasificationSociety.
     *
     * @param clasificationSociety the clasificationSociety to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clasificationSociety, or with status {@code 400 (Bad Request)} if the clasificationSociety has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clasification-societies")
    public ResponseEntity<ClasificationSociety> createClasificationSociety(@Valid @RequestBody ClasificationSociety clasificationSociety)
        throws URISyntaxException {
        log.debug("REST request to save ClasificationSociety : {}", clasificationSociety);
        if (clasificationSociety.getId() != null) {
            throw new BadRequestAlertException("A new clasificationSociety cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasificationSociety result = clasificationSocietyRepository.save(clasificationSociety);
        return ResponseEntity
            .created(new URI("/api/clasification-societies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clasification-societies/:id} : Updates an existing clasificationSociety.
     *
     * @param id the id of the clasificationSociety to save.
     * @param clasificationSociety the clasificationSociety to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clasificationSociety,
     * or with status {@code 400 (Bad Request)} if the clasificationSociety is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clasificationSociety couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clasification-societies/{id}")
    public ResponseEntity<ClasificationSociety> updateClasificationSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClasificationSociety clasificationSociety
    ) throws URISyntaxException {
        log.debug("REST request to update ClasificationSociety : {}, {}", id, clasificationSociety);
        if (clasificationSociety.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clasificationSociety.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clasificationSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClasificationSociety result = clasificationSocietyRepository.save(clasificationSociety);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clasificationSociety.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clasification-societies/:id} : Partial updates given fields of an existing clasificationSociety, field will ignore if it is null
     *
     * @param id the id of the clasificationSociety to save.
     * @param clasificationSociety the clasificationSociety to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clasificationSociety,
     * or with status {@code 400 (Bad Request)} if the clasificationSociety is not valid,
     * or with status {@code 404 (Not Found)} if the clasificationSociety is not found,
     * or with status {@code 500 (Internal Server Error)} if the clasificationSociety couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clasification-societies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClasificationSociety> partialUpdateClasificationSociety(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClasificationSociety clasificationSociety
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClasificationSociety partially : {}, {}", id, clasificationSociety);
        if (clasificationSociety.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clasificationSociety.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clasificationSocietyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClasificationSociety> result = clasificationSocietyRepository
            .findById(clasificationSociety.getId())
            .map(existingClasificationSociety -> {
                if (clasificationSociety.getCode() != null) {
                    existingClasificationSociety.setCode(clasificationSociety.getCode());
                }
                if (clasificationSociety.getName() != null) {
                    existingClasificationSociety.setName(clasificationSociety.getName());
                }

                return existingClasificationSociety;
            })
            .map(clasificationSocietyRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clasificationSociety.getId().toString())
        );
    }

    /**
     * {@code GET  /clasification-societies} : get all the clasificationSocieties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clasificationSocieties in body.
     */
    @GetMapping("/clasification-societies")
    public List<ClasificationSociety> getAllClasificationSocieties() {
        log.debug("REST request to get all ClasificationSocieties");
        return clasificationSocietyRepository.findAll();
    }

    /**
     * {@code GET  /clasification-societies/:id} : get the "id" clasificationSociety.
     *
     * @param id the id of the clasificationSociety to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clasificationSociety, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clasification-societies/{id}")
    public ResponseEntity<ClasificationSociety> getClasificationSociety(@PathVariable Long id) {
        log.debug("REST request to get ClasificationSociety : {}", id);
        Optional<ClasificationSociety> clasificationSociety = clasificationSocietyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clasificationSociety);
    }

    /**
     * {@code DELETE  /clasification-societies/:id} : delete the "id" clasificationSociety.
     *
     * @param id the id of the clasificationSociety to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clasification-societies/{id}")
    public ResponseEntity<Void> deleteClasificationSociety(@PathVariable Long id) {
        log.debug("REST request to delete ClasificationSociety : {}", id);
        clasificationSocietyRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
