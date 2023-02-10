package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ship;
import com.mycompany.myapp.repository.ShipRepository;
import com.mycompany.myapp.service.dto.ShipDTO;
import com.mycompany.myapp.service.mapper.ShipMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ship}.
 */
@Service
@Transactional
public class ShipService {

    private final Logger log = LoggerFactory.getLogger(ShipService.class);

    private final ShipRepository shipRepository;

    private final ShipMapper shipMapper;

    public ShipService(ShipRepository shipRepository, ShipMapper shipMapper) {
        this.shipRepository = shipRepository;
        this.shipMapper = shipMapper;
    }

    /**
     * Save a ship.
     *
     * @param shipDTO the entity to save.
     * @return the persisted entity.
     */
    public ShipDTO save(ShipDTO shipDTO) {
        log.debug("Request to save Ship : {}", shipDTO);
        Ship ship = shipMapper.toEntity(shipDTO);
        ship = shipRepository.save(ship);
        return shipMapper.toDto(ship);
    }

    /**
     * Update a ship.
     *
     * @param shipDTO the entity to save.
     * @return the persisted entity.
     */
    public ShipDTO update(ShipDTO shipDTO) {
        log.debug("Request to update Ship : {}", shipDTO);
        Ship ship = shipMapper.toEntity(shipDTO);
        ship = shipRepository.save(ship);
        return shipMapper.toDto(ship);
    }

    /**
     * Partially update a ship.
     *
     * @param shipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ShipDTO> partialUpdate(ShipDTO shipDTO) {
        log.debug("Request to partially update Ship : {}", shipDTO);

        return shipRepository
            .findById(shipDTO.getId())
            .map(existingShip -> {
                shipMapper.partialUpdate(existingShip, shipDTO);

                return existingShip;
            })
            .map(shipRepository::save)
            .map(shipMapper::toDto);
    }

    /**
     * Get all the ships.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ShipDTO> findAll() {
        log.debug("Request to get all Ships");
        return shipRepository.findAll().stream().map(shipMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShipDTO> findOne(Long id) {
        log.debug("Request to get Ship : {}", id);
        return shipRepository.findById(id).map(shipMapper::toDto);
    }

    /**
     * Delete the ship by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ship : {}", id);
        shipRepository.deleteById(id);
    }
}
