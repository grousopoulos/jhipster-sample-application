package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EventReport;
import com.mycompany.myapp.repository.EventReportRepository;
import com.mycompany.myapp.service.dto.EventReportDTO;
import com.mycompany.myapp.service.mapper.EventReportMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventReport}.
 */
@Service
@Transactional
public class EventReportService {

    private final Logger log = LoggerFactory.getLogger(EventReportService.class);

    private final EventReportRepository eventReportRepository;

    private final EventReportMapper eventReportMapper;

    public EventReportService(EventReportRepository eventReportRepository, EventReportMapper eventReportMapper) {
        this.eventReportRepository = eventReportRepository;
        this.eventReportMapper = eventReportMapper;
    }

    /**
     * Save a eventReport.
     *
     * @param eventReportDTO the entity to save.
     * @return the persisted entity.
     */
    public EventReportDTO save(EventReportDTO eventReportDTO) {
        log.debug("Request to save EventReport : {}", eventReportDTO);
        EventReport eventReport = eventReportMapper.toEntity(eventReportDTO);
        eventReport = eventReportRepository.save(eventReport);
        return eventReportMapper.toDto(eventReport);
    }

    /**
     * Update a eventReport.
     *
     * @param eventReportDTO the entity to save.
     * @return the persisted entity.
     */
    public EventReportDTO update(EventReportDTO eventReportDTO) {
        log.debug("Request to update EventReport : {}", eventReportDTO);
        EventReport eventReport = eventReportMapper.toEntity(eventReportDTO);
        eventReport = eventReportRepository.save(eventReport);
        return eventReportMapper.toDto(eventReport);
    }

    /**
     * Partially update a eventReport.
     *
     * @param eventReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventReportDTO> partialUpdate(EventReportDTO eventReportDTO) {
        log.debug("Request to partially update EventReport : {}", eventReportDTO);

        return eventReportRepository
            .findById(eventReportDTO.getId())
            .map(existingEventReport -> {
                eventReportMapper.partialUpdate(existingEventReport, eventReportDTO);

                return existingEventReport;
            })
            .map(eventReportRepository::save)
            .map(eventReportMapper::toDto);
    }

    /**
     * Get all the eventReports.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EventReportDTO> findAll() {
        log.debug("Request to get all EventReports");
        return eventReportRepository.findAll().stream().map(eventReportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eventReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventReportDTO> findOne(Long id) {
        log.debug("Request to get EventReport : {}", id);
        return eventReportRepository.findById(id).map(eventReportMapper::toDto);
    }

    /**
     * Delete the eventReport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventReport : {}", id);
        eventReportRepository.deleteById(id);
    }
}
