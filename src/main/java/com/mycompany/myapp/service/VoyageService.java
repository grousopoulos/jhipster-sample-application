package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Voyage;
import com.mycompany.myapp.repository.VoyageRepository;
import com.mycompany.myapp.service.dto.VoyageDTO;
import com.mycompany.myapp.service.mapper.VoyageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Voyage}.
 */
@Service
@Transactional
public class VoyageService {

    private final Logger log = LoggerFactory.getLogger(VoyageService.class);

    private final VoyageRepository voyageRepository;

    private final VoyageMapper voyageMapper;

    public VoyageService(VoyageRepository voyageRepository, VoyageMapper voyageMapper) {
        this.voyageRepository = voyageRepository;
        this.voyageMapper = voyageMapper;
    }

    /**
     * Save a voyage.
     *
     * @param voyageDTO the entity to save.
     * @return the persisted entity.
     */
    public VoyageDTO save(VoyageDTO voyageDTO) {
        log.debug("Request to save Voyage : {}", voyageDTO);
        Voyage voyage = voyageMapper.toEntity(voyageDTO);
        voyage = voyageRepository.save(voyage);
        return voyageMapper.toDto(voyage);
    }

    /**
     * Update a voyage.
     *
     * @param voyageDTO the entity to save.
     * @return the persisted entity.
     */
    public VoyageDTO update(VoyageDTO voyageDTO) {
        log.debug("Request to update Voyage : {}", voyageDTO);
        Voyage voyage = voyageMapper.toEntity(voyageDTO);
        voyage = voyageRepository.save(voyage);
        return voyageMapper.toDto(voyage);
    }

    /**
     * Partially update a voyage.
     *
     * @param voyageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VoyageDTO> partialUpdate(VoyageDTO voyageDTO) {
        log.debug("Request to partially update Voyage : {}", voyageDTO);

        return voyageRepository
            .findById(voyageDTO.getId())
            .map(existingVoyage -> {
                voyageMapper.partialUpdate(existingVoyage, voyageDTO);

                return existingVoyage;
            })
            .map(voyageRepository::save)
            .map(voyageMapper::toDto);
    }

    /**
     * Get all the voyages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VoyageDTO> findAll() {
        log.debug("Request to get all Voyages");
        return voyageRepository.findAll().stream().map(voyageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one voyage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoyageDTO> findOne(Long id) {
        log.debug("Request to get Voyage : {}", id);
        return voyageRepository.findById(id).map(voyageMapper::toDto);
    }

    /**
     * Delete the voyage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Voyage : {}", id);
        voyageRepository.deleteById(id);
    }
}
