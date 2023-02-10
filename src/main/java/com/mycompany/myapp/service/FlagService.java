package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Flag;
import com.mycompany.myapp.repository.FlagRepository;
import com.mycompany.myapp.service.dto.FlagDTO;
import com.mycompany.myapp.service.mapper.FlagMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Flag}.
 */
@Service
@Transactional
public class FlagService {

    private final Logger log = LoggerFactory.getLogger(FlagService.class);

    private final FlagRepository flagRepository;

    private final FlagMapper flagMapper;

    public FlagService(FlagRepository flagRepository, FlagMapper flagMapper) {
        this.flagRepository = flagRepository;
        this.flagMapper = flagMapper;
    }

    /**
     * Save a flag.
     *
     * @param flagDTO the entity to save.
     * @return the persisted entity.
     */
    public FlagDTO save(FlagDTO flagDTO) {
        log.debug("Request to save Flag : {}", flagDTO);
        Flag flag = flagMapper.toEntity(flagDTO);
        flag = flagRepository.save(flag);
        return flagMapper.toDto(flag);
    }

    /**
     * Update a flag.
     *
     * @param flagDTO the entity to save.
     * @return the persisted entity.
     */
    public FlagDTO update(FlagDTO flagDTO) {
        log.debug("Request to update Flag : {}", flagDTO);
        Flag flag = flagMapper.toEntity(flagDTO);
        flag = flagRepository.save(flag);
        return flagMapper.toDto(flag);
    }

    /**
     * Partially update a flag.
     *
     * @param flagDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FlagDTO> partialUpdate(FlagDTO flagDTO) {
        log.debug("Request to partially update Flag : {}", flagDTO);

        return flagRepository
            .findById(flagDTO.getId())
            .map(existingFlag -> {
                flagMapper.partialUpdate(existingFlag, flagDTO);

                return existingFlag;
            })
            .map(flagRepository::save)
            .map(flagMapper::toDto);
    }

    /**
     * Get all the flags.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FlagDTO> findAll() {
        log.debug("Request to get all Flags");
        return flagRepository.findAll().stream().map(flagMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one flag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FlagDTO> findOne(Long id) {
        log.debug("Request to get Flag : {}", id);
        return flagRepository.findById(id).map(flagMapper::toDto);
    }

    /**
     * Delete the flag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Flag : {}", id);
        flagRepository.deleteById(id);
    }
}
