package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EventReport;
import com.mycompany.myapp.repository.EventReportRepository;
import com.mycompany.myapp.service.dto.EventReportDTO;
import com.mycompany.myapp.service.mapper.EventReportMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventReportResourceIT {

    private static final ZonedDateTime DEFAULT_DOCUMENT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOCUMENT_DATE_AND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_SPEED_GPS = new BigDecimal(1);
    private static final BigDecimal UPDATED_SPEED_GPS = new BigDecimal(2);

    private static final String DEFAULT_DOCUMENT_DISPLAY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_DISPLAY_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/event-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventReportRepository eventReportRepository;

    @Autowired
    private EventReportMapper eventReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventReportMockMvc;

    private EventReport eventReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReport createEntity(EntityManager em) {
        EventReport eventReport = new EventReport()
            .documentDateAndTime(DEFAULT_DOCUMENT_DATE_AND_TIME)
            .speedGps(DEFAULT_SPEED_GPS)
            .documentDisplayNumber(DEFAULT_DOCUMENT_DISPLAY_NUMBER);
        return eventReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReport createUpdatedEntity(EntityManager em) {
        EventReport eventReport = new EventReport()
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .speedGps(UPDATED_SPEED_GPS)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);
        return eventReport;
    }

    @BeforeEach
    public void initTest() {
        eventReport = createEntity(em);
    }

    @Test
    @Transactional
    void createEventReport() throws Exception {
        int databaseSizeBeforeCreate = eventReportRepository.findAll().size();
        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);
        restEventReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeCreate + 1);
        EventReport testEventReport = eventReportList.get(eventReportList.size() - 1);
        assertThat(testEventReport.getDocumentDateAndTime()).isEqualTo(DEFAULT_DOCUMENT_DATE_AND_TIME);
        assertThat(testEventReport.getSpeedGps()).isEqualByComparingTo(DEFAULT_SPEED_GPS);
        assertThat(testEventReport.getDocumentDisplayNumber()).isEqualTo(DEFAULT_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void createEventReportWithExistingId() throws Exception {
        // Create the EventReport with an existing ID
        eventReport.setId(1L);
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        int databaseSizeBeforeCreate = eventReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentDateAndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventReportRepository.findAll().size();
        // set the field null
        eventReport.setDocumentDateAndTime(null);

        // Create the EventReport, which fails.
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        restEventReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventReports() throws Exception {
        // Initialize the database
        eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDateAndTime").value(hasItem(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME))))
            .andExpect(jsonPath("$.[*].speedGps").value(hasItem(sameNumber(DEFAULT_SPEED_GPS))))
            .andExpect(jsonPath("$.[*].documentDisplayNumber").value(hasItem(DEFAULT_DOCUMENT_DISPLAY_NUMBER)));
    }

    @Test
    @Transactional
    void getEventReport() throws Exception {
        // Initialize the database
        eventReportRepository.saveAndFlush(eventReport);

        // Get the eventReport
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL_ID, eventReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventReport.getId().intValue()))
            .andExpect(jsonPath("$.documentDateAndTime").value(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME)))
            .andExpect(jsonPath("$.speedGps").value(sameNumber(DEFAULT_SPEED_GPS)))
            .andExpect(jsonPath("$.documentDisplayNumber").value(DEFAULT_DOCUMENT_DISPLAY_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingEventReport() throws Exception {
        // Get the eventReport
        restEventReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventReport() throws Exception {
        // Initialize the database
        eventReportRepository.saveAndFlush(eventReport);

        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();

        // Update the eventReport
        EventReport updatedEventReport = eventReportRepository.findById(eventReport.getId()).get();
        // Disconnect from session so that the updates on updatedEventReport are not directly saved in db
        em.detach(updatedEventReport);
        updatedEventReport
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .speedGps(UPDATED_SPEED_GPS)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);
        EventReportDTO eventReportDTO = eventReportMapper.toDto(updatedEventReport);

        restEventReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
        EventReport testEventReport = eventReportList.get(eventReportList.size() - 1);
        assertThat(testEventReport.getDocumentDateAndTime()).isEqualTo(UPDATED_DOCUMENT_DATE_AND_TIME);
        assertThat(testEventReport.getSpeedGps()).isEqualByComparingTo(UPDATED_SPEED_GPS);
        assertThat(testEventReport.getDocumentDisplayNumber()).isEqualTo(UPDATED_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingEventReport() throws Exception {
        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();
        eventReport.setId(count.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventReport() throws Exception {
        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();
        eventReport.setId(count.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventReport() throws Exception {
        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();
        eventReport.setId(count.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventReportWithPatch() throws Exception {
        // Initialize the database
        eventReportRepository.saveAndFlush(eventReport);

        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();

        // Update the eventReport using partial update
        EventReport partialUpdatedEventReport = new EventReport();
        partialUpdatedEventReport.setId(eventReport.getId());

        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReport))
            )
            .andExpect(status().isOk());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
        EventReport testEventReport = eventReportList.get(eventReportList.size() - 1);
        assertThat(testEventReport.getDocumentDateAndTime()).isEqualTo(DEFAULT_DOCUMENT_DATE_AND_TIME);
        assertThat(testEventReport.getSpeedGps()).isEqualByComparingTo(DEFAULT_SPEED_GPS);
        assertThat(testEventReport.getDocumentDisplayNumber()).isEqualTo(DEFAULT_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateEventReportWithPatch() throws Exception {
        // Initialize the database
        eventReportRepository.saveAndFlush(eventReport);

        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();

        // Update the eventReport using partial update
        EventReport partialUpdatedEventReport = new EventReport();
        partialUpdatedEventReport.setId(eventReport.getId());

        partialUpdatedEventReport
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .speedGps(UPDATED_SPEED_GPS)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);

        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReport))
            )
            .andExpect(status().isOk());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
        EventReport testEventReport = eventReportList.get(eventReportList.size() - 1);
        assertThat(testEventReport.getDocumentDateAndTime()).isEqualTo(UPDATED_DOCUMENT_DATE_AND_TIME);
        assertThat(testEventReport.getSpeedGps()).isEqualByComparingTo(UPDATED_SPEED_GPS);
        assertThat(testEventReport.getDocumentDisplayNumber()).isEqualTo(UPDATED_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingEventReport() throws Exception {
        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();
        eventReport.setId(count.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventReportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventReport() throws Exception {
        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();
        eventReport.setId(count.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventReport() throws Exception {
        int databaseSizeBeforeUpdate = eventReportRepository.findAll().size();
        eventReport.setId(count.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReport in the database
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventReport() throws Exception {
        // Initialize the database
        eventReportRepository.saveAndFlush(eventReport);

        int databaseSizeBeforeDelete = eventReportRepository.findAll().size();

        // Delete the eventReport
        restEventReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventReport> eventReportList = eventReportRepository.findAll();
        assertThat(eventReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
