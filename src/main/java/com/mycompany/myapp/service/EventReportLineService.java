package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EventReportLine;
import com.mycompany.myapp.repository.EventReportLineRepository;
import com.mycompany.myapp.service.dto.EventReportLineDTO;
import com.mycompany.myapp.service.mapper.EventReportLineMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventReportLine}.
 */
@Service
@Transactional
public class EventReportLineService {

    private final Logger log = LoggerFactory.getLogger(EventReportLineService.class);

    private final EventReportLineRepository eventReportLineRepository;

    private final EventReportLineMapper eventReportLineMapper;

    public EventReportLineService(EventReportLineRepository eventReportLineRepository, EventReportLineMapper eventReportLineMapper) {
        this.eventReportLineRepository = eventReportLineRepository;
        this.eventReportLineMapper = eventReportLineMapper;
    }

    /**
     * Save a eventReportLine.
     *
     * @param eventReportLineDTO the entity to save.
     * @return the persisted entity.
     */
    public EventReportLineDTO save(EventReportLineDTO eventReportLineDTO) {
        log.debug("Request to save EventReportLine : {}", eventReportLineDTO);
        EventReportLine eventReportLine = eventReportLineMapper.toEntity(eventReportLineDTO);
        eventReportLine = eventReportLineRepository.save(eventReportLine);
        return eventReportLineMapper.toDto(eventReportLine);
    }

    /**
     * Update a eventReportLine.
     *
     * @param eventReportLineDTO the entity to save.
     * @return the persisted entity.
     */
    public EventReportLineDTO update(EventReportLineDTO eventReportLineDTO) {
        log.debug("Request to update EventReportLine : {}", eventReportLineDTO);
        EventReportLine eventReportLine = eventReportLineMapper.toEntity(eventReportLineDTO);
        eventReportLine = eventReportLineRepository.save(eventReportLine);
        return eventReportLineMapper.toDto(eventReportLine);
    }

    /**
     * Partially update a eventReportLine.
     *
     * @param eventReportLineDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventReportLineDTO> partialUpdate(EventReportLineDTO eventReportLineDTO) {
        log.debug("Request to partially update EventReportLine : {}", eventReportLineDTO);

        return eventReportLineRepository
            .findById(eventReportLineDTO.getId())
            .map(existingEventReportLine -> {
                eventReportLineMapper.partialUpdate(existingEventReportLine, eventReportLineDTO);

                return existingEventReportLine;
            })
            .map(eventReportLineRepository::save)
            .map(eventReportLineMapper::toDto);
    }

    /**
     * Get all the eventReportLines.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EventReportLineDTO> findAll() {
        log.debug("Request to get all EventReportLines");
        return eventReportLineRepository
            .findAll()
            .stream()
            .map(eventReportLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eventReportLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventReportLineDTO> findOne(Long id) {
        log.debug("Request to get EventReportLine : {}", id);
        return eventReportLineRepository.findById(id).map(eventReportLineMapper::toDto);
    }

    /**
     * Delete the eventReportLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventReportLine : {}", id);
        eventReportLineRepository.deleteById(id);
    }
}
