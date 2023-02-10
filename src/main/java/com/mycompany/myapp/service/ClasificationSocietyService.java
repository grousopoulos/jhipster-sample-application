package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ClasificationSociety;
import com.mycompany.myapp.repository.ClasificationSocietyRepository;
import com.mycompany.myapp.service.dto.ClasificationSocietyDTO;
import com.mycompany.myapp.service.mapper.ClasificationSocietyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClasificationSociety}.
 */
@Service
@Transactional
public class ClasificationSocietyService {

    private final Logger log = LoggerFactory.getLogger(ClasificationSocietyService.class);

    private final ClasificationSocietyRepository clasificationSocietyRepository;

    private final ClasificationSocietyMapper clasificationSocietyMapper;

    public ClasificationSocietyService(
        ClasificationSocietyRepository clasificationSocietyRepository,
        ClasificationSocietyMapper clasificationSocietyMapper
    ) {
        this.clasificationSocietyRepository = clasificationSocietyRepository;
        this.clasificationSocietyMapper = clasificationSocietyMapper;
    }

    /**
     * Save a clasificationSociety.
     *
     * @param clasificationSocietyDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasificationSocietyDTO save(ClasificationSocietyDTO clasificationSocietyDTO) {
        log.debug("Request to save ClasificationSociety : {}", clasificationSocietyDTO);
        ClasificationSociety clasificationSociety = clasificationSocietyMapper.toEntity(clasificationSocietyDTO);
        clasificationSociety = clasificationSocietyRepository.save(clasificationSociety);
        return clasificationSocietyMapper.toDto(clasificationSociety);
    }

    /**
     * Update a clasificationSociety.
     *
     * @param clasificationSocietyDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasificationSocietyDTO update(ClasificationSocietyDTO clasificationSocietyDTO) {
        log.debug("Request to update ClasificationSociety : {}", clasificationSocietyDTO);
        ClasificationSociety clasificationSociety = clasificationSocietyMapper.toEntity(clasificationSocietyDTO);
        clasificationSociety = clasificationSocietyRepository.save(clasificationSociety);
        return clasificationSocietyMapper.toDto(clasificationSociety);
    }

    /**
     * Partially update a clasificationSociety.
     *
     * @param clasificationSocietyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClasificationSocietyDTO> partialUpdate(ClasificationSocietyDTO clasificationSocietyDTO) {
        log.debug("Request to partially update ClasificationSociety : {}", clasificationSocietyDTO);

        return clasificationSocietyRepository
            .findById(clasificationSocietyDTO.getId())
            .map(existingClasificationSociety -> {
                clasificationSocietyMapper.partialUpdate(existingClasificationSociety, clasificationSocietyDTO);

                return existingClasificationSociety;
            })
            .map(clasificationSocietyRepository::save)
            .map(clasificationSocietyMapper::toDto);
    }

    /**
     * Get all the clasificationSocieties.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClasificationSocietyDTO> findAll() {
        log.debug("Request to get all ClasificationSocieties");
        return clasificationSocietyRepository
            .findAll()
            .stream()
            .map(clasificationSocietyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one clasificationSociety by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClasificationSocietyDTO> findOne(Long id) {
        log.debug("Request to get ClasificationSociety : {}", id);
        return clasificationSocietyRepository.findById(id).map(clasificationSocietyMapper::toDto);
    }

    /**
     * Delete the clasificationSociety by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClasificationSociety : {}", id);
        clasificationSocietyRepository.deleteById(id);
    }
}
