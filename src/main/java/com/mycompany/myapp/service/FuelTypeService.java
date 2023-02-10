package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FuelType;
import com.mycompany.myapp.repository.FuelTypeRepository;
import com.mycompany.myapp.service.dto.FuelTypeDTO;
import com.mycompany.myapp.service.mapper.FuelTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FuelType}.
 */
@Service
@Transactional
public class FuelTypeService {

    private final Logger log = LoggerFactory.getLogger(FuelTypeService.class);

    private final FuelTypeRepository fuelTypeRepository;

    private final FuelTypeMapper fuelTypeMapper;

    public FuelTypeService(FuelTypeRepository fuelTypeRepository, FuelTypeMapper fuelTypeMapper) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.fuelTypeMapper = fuelTypeMapper;
    }

    /**
     * Save a fuelType.
     *
     * @param fuelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public FuelTypeDTO save(FuelTypeDTO fuelTypeDTO) {
        log.debug("Request to save FuelType : {}", fuelTypeDTO);
        FuelType fuelType = fuelTypeMapper.toEntity(fuelTypeDTO);
        fuelType = fuelTypeRepository.save(fuelType);
        return fuelTypeMapper.toDto(fuelType);
    }

    /**
     * Update a fuelType.
     *
     * @param fuelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public FuelTypeDTO update(FuelTypeDTO fuelTypeDTO) {
        log.debug("Request to update FuelType : {}", fuelTypeDTO);
        FuelType fuelType = fuelTypeMapper.toEntity(fuelTypeDTO);
        fuelType = fuelTypeRepository.save(fuelType);
        return fuelTypeMapper.toDto(fuelType);
    }

    /**
     * Partially update a fuelType.
     *
     * @param fuelTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuelTypeDTO> partialUpdate(FuelTypeDTO fuelTypeDTO) {
        log.debug("Request to partially update FuelType : {}", fuelTypeDTO);

        return fuelTypeRepository
            .findById(fuelTypeDTO.getId())
            .map(existingFuelType -> {
                fuelTypeMapper.partialUpdate(existingFuelType, fuelTypeDTO);

                return existingFuelType;
            })
            .map(fuelTypeRepository::save)
            .map(fuelTypeMapper::toDto);
    }

    /**
     * Get all the fuelTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FuelTypeDTO> findAll() {
        log.debug("Request to get all FuelTypes");
        return fuelTypeRepository.findAll().stream().map(fuelTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one fuelType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuelTypeDTO> findOne(Long id) {
        log.debug("Request to get FuelType : {}", id);
        return fuelTypeRepository.findById(id).map(fuelTypeMapper::toDto);
    }

    /**
     * Delete the fuelType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FuelType : {}", id);
        fuelTypeRepository.deleteById(id);
    }
}
