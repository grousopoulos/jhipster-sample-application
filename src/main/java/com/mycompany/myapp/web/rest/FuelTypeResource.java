package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FuelType;
import com.mycompany.myapp.repository.FuelTypeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FuelType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FuelTypeResource {

    private final Logger log = LoggerFactory.getLogger(FuelTypeResource.class);

    private static final String ENTITY_NAME = "fuelType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTypeRepository fuelTypeRepository;

    public FuelTypeResource(FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
    }

    /**
     * {@code POST  /fuel-types} : Create a new fuelType.
     *
     * @param fuelType the fuelType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelType, or with status {@code 400 (Bad Request)} if the fuelType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuel-types")
    public ResponseEntity<FuelType> createFuelType(@Valid @RequestBody FuelType fuelType) throws URISyntaxException {
        log.debug("REST request to save FuelType : {}", fuelType);
        if (fuelType.getId() != null) {
            throw new BadRequestAlertException("A new fuelType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuelType result = fuelTypeRepository.save(fuelType);
        return ResponseEntity
            .created(new URI("/api/fuel-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuel-types/:id} : Updates an existing fuelType.
     *
     * @param id the id of the fuelType to save.
     * @param fuelType the fuelType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelType,
     * or with status {@code 400 (Bad Request)} if the fuelType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuel-types/{id}")
    public ResponseEntity<FuelType> updateFuelType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuelType fuelType
    ) throws URISyntaxException {
        log.debug("REST request to update FuelType : {}, {}", id, fuelType);
        if (fuelType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuelType result = fuelTypeRepository.save(fuelType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fuel-types/:id} : Partial updates given fields of an existing fuelType, field will ignore if it is null
     *
     * @param id the id of the fuelType to save.
     * @param fuelType the fuelType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelType,
     * or with status {@code 400 (Bad Request)} if the fuelType is not valid,
     * or with status {@code 404 (Not Found)} if the fuelType is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fuel-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelType> partialUpdateFuelType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuelType fuelType
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuelType partially : {}, {}", id, fuelType);
        if (fuelType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelType> result = fuelTypeRepository
            .findById(fuelType.getId())
            .map(existingFuelType -> {
                if (fuelType.getName() != null) {
                    existingFuelType.setName(fuelType.getName());
                }
                if (fuelType.getFuelTypeCode() != null) {
                    existingFuelType.setFuelTypeCode(fuelType.getFuelTypeCode());
                }
                if (fuelType.getCarbonFactory() != null) {
                    existingFuelType.setCarbonFactory(fuelType.getCarbonFactory());
                }

                return existingFuelType;
            })
            .map(fuelTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelType.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-types} : get all the fuelTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTypes in body.
     */
    @GetMapping("/fuel-types")
    public List<FuelType> getAllFuelTypes() {
        log.debug("REST request to get all FuelTypes");
        return fuelTypeRepository.findAll();
    }

    /**
     * {@code GET  /fuel-types/:id} : get the "id" fuelType.
     *
     * @param id the id of the fuelType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuel-types/{id}")
    public ResponseEntity<FuelType> getFuelType(@PathVariable Long id) {
        log.debug("REST request to get FuelType : {}", id);
        Optional<FuelType> fuelType = fuelTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fuelType);
    }

    /**
     * {@code DELETE  /fuel-types/:id} : delete the "id" fuelType.
     *
     * @param id the id of the fuelType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuel-types/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable Long id) {
        log.debug("REST request to delete FuelType : {}", id);
        fuelTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
