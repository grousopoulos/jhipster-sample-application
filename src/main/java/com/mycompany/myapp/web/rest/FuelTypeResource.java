package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.FuelTypeRepository;
import com.mycompany.myapp.service.FuelTypeService;
import com.mycompany.myapp.service.dto.FuelTypeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FuelType}.
 */
@RestController
@RequestMapping("/api")
public class FuelTypeResource {

    private final Logger log = LoggerFactory.getLogger(FuelTypeResource.class);

    private static final String ENTITY_NAME = "fuelType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTypeService fuelTypeService;

    private final FuelTypeRepository fuelTypeRepository;

    public FuelTypeResource(FuelTypeService fuelTypeService, FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeService = fuelTypeService;
        this.fuelTypeRepository = fuelTypeRepository;
    }

    /**
     * {@code POST  /fuel-types} : Create a new fuelType.
     *
     * @param fuelTypeDTO the fuelTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelTypeDTO, or with status {@code 400 (Bad Request)} if the fuelType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuel-types")
    public ResponseEntity<FuelTypeDTO> createFuelType(@Valid @RequestBody FuelTypeDTO fuelTypeDTO) throws URISyntaxException {
        log.debug("REST request to save FuelType : {}", fuelTypeDTO);
        if (fuelTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new fuelType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuelTypeDTO result = fuelTypeService.save(fuelTypeDTO);
        return ResponseEntity
            .created(new URI("/api/fuel-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuel-types/:id} : Updates an existing fuelType.
     *
     * @param id the id of the fuelTypeDTO to save.
     * @param fuelTypeDTO the fuelTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuel-types/{id}")
    public ResponseEntity<FuelTypeDTO> updateFuelType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuelTypeDTO fuelTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FuelType : {}, {}", id, fuelTypeDTO);
        if (fuelTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuelTypeDTO result = fuelTypeService.update(fuelTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fuel-types/:id} : Partial updates given fields of an existing fuelType, field will ignore if it is null
     *
     * @param id the id of the fuelTypeDTO to save.
     * @param fuelTypeDTO the fuelTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fuelTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fuel-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelTypeDTO> partialUpdateFuelType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuelTypeDTO fuelTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuelType partially : {}, {}", id, fuelTypeDTO);
        if (fuelTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelTypeDTO> result = fuelTypeService.partialUpdate(fuelTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-types} : get all the fuelTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTypes in body.
     */
    @GetMapping("/fuel-types")
    public List<FuelTypeDTO> getAllFuelTypes() {
        log.debug("REST request to get all FuelTypes");
        return fuelTypeService.findAll();
    }

    /**
     * {@code GET  /fuel-types/:id} : get the "id" fuelType.
     *
     * @param id the id of the fuelTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuel-types/{id}")
    public ResponseEntity<FuelTypeDTO> getFuelType(@PathVariable Long id) {
        log.debug("REST request to get FuelType : {}", id);
        Optional<FuelTypeDTO> fuelTypeDTO = fuelTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelTypeDTO);
    }

    /**
     * {@code DELETE  /fuel-types/:id} : delete the "id" fuelType.
     *
     * @param id the id of the fuelTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuel-types/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable Long id) {
        log.debug("REST request to delete FuelType : {}", id);
        fuelTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
