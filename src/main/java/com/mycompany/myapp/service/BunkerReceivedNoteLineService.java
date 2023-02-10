package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BunkerReceivedNoteLine;
import com.mycompany.myapp.repository.BunkerReceivedNoteLineRepository;
import com.mycompany.myapp.service.dto.BunkerReceivedNoteLineDTO;
import com.mycompany.myapp.service.mapper.BunkerReceivedNoteLineMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BunkerReceivedNoteLine}.
 */
@Service
@Transactional
public class BunkerReceivedNoteLineService {

    private final Logger log = LoggerFactory.getLogger(BunkerReceivedNoteLineService.class);

    private final BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository;

    private final BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper;

    public BunkerReceivedNoteLineService(
        BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository,
        BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper
    ) {
        this.bunkerReceivedNoteLineRepository = bunkerReceivedNoteLineRepository;
        this.bunkerReceivedNoteLineMapper = bunkerReceivedNoteLineMapper;
    }

    /**
     * Save a bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLineDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteLineDTO save(BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO) {
        log.debug("Request to save BunkerReceivedNoteLine : {}", bunkerReceivedNoteLineDTO);
        BunkerReceivedNoteLine bunkerReceivedNoteLine = bunkerReceivedNoteLineMapper.toEntity(bunkerReceivedNoteLineDTO);
        bunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.save(bunkerReceivedNoteLine);
        return bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);
    }

    /**
     * Update a bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLineDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteLineDTO update(BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO) {
        log.debug("Request to update BunkerReceivedNoteLine : {}", bunkerReceivedNoteLineDTO);
        BunkerReceivedNoteLine bunkerReceivedNoteLine = bunkerReceivedNoteLineMapper.toEntity(bunkerReceivedNoteLineDTO);
        bunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.save(bunkerReceivedNoteLine);
        return bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);
    }

    /**
     * Partially update a bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLineDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BunkerReceivedNoteLineDTO> partialUpdate(BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO) {
        log.debug("Request to partially update BunkerReceivedNoteLine : {}", bunkerReceivedNoteLineDTO);

        return bunkerReceivedNoteLineRepository
            .findById(bunkerReceivedNoteLineDTO.getId())
            .map(existingBunkerReceivedNoteLine -> {
                bunkerReceivedNoteLineMapper.partialUpdate(existingBunkerReceivedNoteLine, bunkerReceivedNoteLineDTO);

                return existingBunkerReceivedNoteLine;
            })
            .map(bunkerReceivedNoteLineRepository::save)
            .map(bunkerReceivedNoteLineMapper::toDto);
    }

    /**
     * Get all the bunkerReceivedNoteLines.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BunkerReceivedNoteLineDTO> findAll() {
        log.debug("Request to get all BunkerReceivedNoteLines");
        return bunkerReceivedNoteLineRepository
            .findAll()
            .stream()
            .map(bunkerReceivedNoteLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bunkerReceivedNoteLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BunkerReceivedNoteLineDTO> findOne(Long id) {
        log.debug("Request to get BunkerReceivedNoteLine : {}", id);
        return bunkerReceivedNoteLineRepository.findById(id).map(bunkerReceivedNoteLineMapper::toDto);
    }

    /**
     * Delete the bunkerReceivedNoteLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BunkerReceivedNoteLine : {}", id);
        bunkerReceivedNoteLineRepository.deleteById(id);
    }
}
