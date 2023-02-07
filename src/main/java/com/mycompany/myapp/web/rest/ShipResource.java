package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ship;
import com.mycompany.myapp.repository.ShipRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ship}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShipResource {

    private final Logger log = LoggerFactory.getLogger(ShipResource.class);

    private static final String ENTITY_NAME = "ship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipRepository shipRepository;

    public ShipResource(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    /**
     * {@code POST  /ships} : Create a new ship.
     *
     * @param ship the ship to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ship, or with status {@code 400 (Bad Request)} if the ship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ships")
    public ResponseEntity<Ship> createShip(@Valid @RequestBody Ship ship) throws URISyntaxException {
        log.debug("REST request to save Ship : {}", ship);
        if (ship.getId() != null) {
            throw new BadRequestAlertException("A new ship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ship result = shipRepository.save(ship);
        return ResponseEntity
            .created(new URI("/api/ships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ships/:id} : Updates an existing ship.
     *
     * @param id the id of the ship to save.
     * @param ship the ship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ship,
     * or with status {@code 400 (Bad Request)} if the ship is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Ship ship)
        throws URISyntaxException {
        log.debug("REST request to update Ship : {}, {}", id, ship);
        if (ship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ship.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ship result = shipRepository.save(ship);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ship.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ships/:id} : Partial updates given fields of an existing ship, field will ignore if it is null
     *
     * @param id the id of the ship to save.
     * @param ship the ship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ship,
     * or with status {@code 400 (Bad Request)} if the ship is not valid,
     * or with status {@code 404 (Not Found)} if the ship is not found,
     * or with status {@code 500 (Internal Server Error)} if the ship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ship> partialUpdateShip(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ship ship
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ship partially : {}, {}", id, ship);
        if (ship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ship.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ship> result = shipRepository
            .findById(ship.getId())
            .map(existingShip -> {
                if (ship.getName() != null) {
                    existingShip.setName(ship.getName());
                }
                if (ship.getClassificationSociety() != null) {
                    existingShip.setClassificationSociety(ship.getClassificationSociety());
                }
                if (ship.getIceClassPolarCode() != null) {
                    existingShip.setIceClassPolarCode(ship.getIceClassPolarCode());
                }
                if (ship.getTechnicalEfficiencyCode() != null) {
                    existingShip.setTechnicalEfficiencyCode(ship.getTechnicalEfficiencyCode());
                }
                if (ship.getShipType() != null) {
                    existingShip.setShipType(ship.getShipType());
                }

                return existingShip;
            })
            .map(shipRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ship.getId().toString())
        );
    }

    /**
     * {@code GET  /ships} : get all the ships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ships in body.
     */
    @GetMapping("/ships")
    public List<Ship> getAllShips() {
        log.debug("REST request to get all Ships");
        return shipRepository.findAll();
    }

    /**
     * {@code GET  /ships/:id} : get the "id" ship.
     *
     * @param id the id of the ship to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ship, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ships/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Long id) {
        log.debug("REST request to get Ship : {}", id);
        Optional<Ship> ship = shipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ship);
    }

    /**
     * {@code DELETE  /ships/:id} : delete the "id" ship.
     *
     * @param id the id of the ship to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ships/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Long id) {
        log.debug("REST request to delete Ship : {}", id);
        shipRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
