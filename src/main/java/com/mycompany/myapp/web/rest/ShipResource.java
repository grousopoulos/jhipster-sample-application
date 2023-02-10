package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ShipRepository;
import com.mycompany.myapp.service.ShipService;
import com.mycompany.myapp.service.dto.ShipDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ship}.
 */
@RestController
@RequestMapping("/api")
public class ShipResource {

    private final Logger log = LoggerFactory.getLogger(ShipResource.class);

    private static final String ENTITY_NAME = "ship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipService shipService;

    private final ShipRepository shipRepository;

    public ShipResource(ShipService shipService, ShipRepository shipRepository) {
        this.shipService = shipService;
        this.shipRepository = shipRepository;
    }

    /**
     * {@code POST  /ships} : Create a new ship.
     *
     * @param shipDTO the shipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipDTO, or with status {@code 400 (Bad Request)} if the ship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ships")
    public ResponseEntity<ShipDTO> createShip(@Valid @RequestBody ShipDTO shipDTO) throws URISyntaxException {
        log.debug("REST request to save Ship : {}", shipDTO);
        if (shipDTO.getId() != null) {
            throw new BadRequestAlertException("A new ship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipDTO result = shipService.save(shipDTO);
        return ResponseEntity
            .created(new URI("/api/ships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ships/:id} : Updates an existing ship.
     *
     * @param id the id of the shipDTO to save.
     * @param shipDTO the shipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipDTO,
     * or with status {@code 400 (Bad Request)} if the shipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ships/{id}")
    public ResponseEntity<ShipDTO> updateShip(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShipDTO shipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ship : {}, {}", id, shipDTO);
        if (shipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShipDTO result = shipService.update(shipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ships/:id} : Partial updates given fields of an existing ship, field will ignore if it is null
     *
     * @param id the id of the shipDTO to save.
     * @param shipDTO the shipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipDTO,
     * or with status {@code 400 (Bad Request)} if the shipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipDTO> partialUpdateShip(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShipDTO shipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ship partially : {}, {}", id, shipDTO);
        if (shipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipDTO> result = shipService.partialUpdate(shipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ships} : get all the ships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ships in body.
     */
    @GetMapping("/ships")
    public List<ShipDTO> getAllShips() {
        log.debug("REST request to get all Ships");
        return shipService.findAll();
    }

    /**
     * {@code GET  /ships/:id} : get the "id" ship.
     *
     * @param id the id of the shipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ships/{id}")
    public ResponseEntity<ShipDTO> getShip(@PathVariable Long id) {
        log.debug("REST request to get Ship : {}", id);
        Optional<ShipDTO> shipDTO = shipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipDTO);
    }

    /**
     * {@code DELETE  /ships/:id} : delete the "id" ship.
     *
     * @param id the id of the shipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ships/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Long id) {
        log.debug("REST request to delete Ship : {}", id);
        shipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
