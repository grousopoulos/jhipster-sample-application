package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BunkerReceivedNote;
import com.mycompany.myapp.repository.BunkerReceivedNoteRepository;
import com.mycompany.myapp.service.dto.BunkerReceivedNoteDTO;
import com.mycompany.myapp.service.mapper.BunkerReceivedNoteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BunkerReceivedNote}.
 */
@Service
@Transactional
public class BunkerReceivedNoteService {

    private final Logger log = LoggerFactory.getLogger(BunkerReceivedNoteService.class);

    private final BunkerReceivedNoteRepository bunkerReceivedNoteRepository;

    private final BunkerReceivedNoteMapper bunkerReceivedNoteMapper;

    public BunkerReceivedNoteService(
        BunkerReceivedNoteRepository bunkerReceivedNoteRepository,
        BunkerReceivedNoteMapper bunkerReceivedNoteMapper
    ) {
        this.bunkerReceivedNoteRepository = bunkerReceivedNoteRepository;
        this.bunkerReceivedNoteMapper = bunkerReceivedNoteMapper;
    }

    /**
     * Save a bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteDTO save(BunkerReceivedNoteDTO bunkerReceivedNoteDTO) {
        log.debug("Request to save BunkerReceivedNote : {}", bunkerReceivedNoteDTO);
        BunkerReceivedNote bunkerReceivedNote = bunkerReceivedNoteMapper.toEntity(bunkerReceivedNoteDTO);
        bunkerReceivedNote = bunkerReceivedNoteRepository.save(bunkerReceivedNote);
        return bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);
    }

    /**
     * Update a bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteDTO update(BunkerReceivedNoteDTO bunkerReceivedNoteDTO) {
        log.debug("Request to update BunkerReceivedNote : {}", bunkerReceivedNoteDTO);
        BunkerReceivedNote bunkerReceivedNote = bunkerReceivedNoteMapper.toEntity(bunkerReceivedNoteDTO);
        bunkerReceivedNote = bunkerReceivedNoteRepository.save(bunkerReceivedNote);
        return bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);
    }

    /**
     * Partially update a bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BunkerReceivedNoteDTO> partialUpdate(BunkerReceivedNoteDTO bunkerReceivedNoteDTO) {
        log.debug("Request to partially update BunkerReceivedNote : {}", bunkerReceivedNoteDTO);

        return bunkerReceivedNoteRepository
            .findById(bunkerReceivedNoteDTO.getId())
            .map(existingBunkerReceivedNote -> {
                bunkerReceivedNoteMapper.partialUpdate(existingBunkerReceivedNote, bunkerReceivedNoteDTO);

                return existingBunkerReceivedNote;
            })
            .map(bunkerReceivedNoteRepository::save)
            .map(bunkerReceivedNoteMapper::toDto);
    }

    /**
     * Get all the bunkerReceivedNotes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BunkerReceivedNoteDTO> findAll() {
        log.debug("Request to get all BunkerReceivedNotes");
        return bunkerReceivedNoteRepository
            .findAll()
            .stream()
            .map(bunkerReceivedNoteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bunkerReceivedNote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BunkerReceivedNoteDTO> findOne(Long id) {
        log.debug("Request to get BunkerReceivedNote : {}", id);
        return bunkerReceivedNoteRepository.findById(id).map(bunkerReceivedNoteMapper::toDto);
    }

    /**
     * Delete the bunkerReceivedNote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BunkerReceivedNote : {}", id);
        bunkerReceivedNoteRepository.deleteById(id);
    }
}
