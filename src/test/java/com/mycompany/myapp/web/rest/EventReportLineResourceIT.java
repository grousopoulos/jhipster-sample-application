package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EventReportLine;
import com.mycompany.myapp.domain.enumeration.UnitOfMeasure;
import com.mycompany.myapp.repository.EventReportLineRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link EventReportLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventReportLineResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final UnitOfMeasure DEFAULT_UNIT_OF_MEASURE = UnitOfMeasure.M_TONNES;
    private static final UnitOfMeasure UPDATED_UNIT_OF_MEASURE = UnitOfMeasure.M3;

    private static final String ENTITY_API_URL = "/api/event-report-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventReportLineRepository eventReportLineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventReportLineMockMvc;

    private EventReportLine eventReportLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReportLine createEntity(EntityManager em) {
        EventReportLine eventReportLine = new EventReportLine().quantity(DEFAULT_QUANTITY).unitOfMeasure(DEFAULT_UNIT_OF_MEASURE);
        return eventReportLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReportLine createUpdatedEntity(EntityManager em) {
        EventReportLine eventReportLine = new EventReportLine().quantity(UPDATED_QUANTITY).unitOfMeasure(UPDATED_UNIT_OF_MEASURE);
        return eventReportLine;
    }

    @BeforeEach
    public void initTest() {
        eventReportLine = createEntity(em);
    }

    @Test
    @Transactional
    void createEventReportLine() throws Exception {
        int databaseSizeBeforeCreate = eventReportLineRepository.findAll().size();
        // Create the EventReportLine
        restEventReportLineMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isCreated());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeCreate + 1);
        EventReportLine testEventReportLine = eventReportLineList.get(eventReportLineList.size() - 1);
        assertThat(testEventReportLine.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testEventReportLine.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void createEventReportLineWithExistingId() throws Exception {
        // Create the EventReportLine with an existing ID
        eventReportLine.setId(1L);

        int databaseSizeBeforeCreate = eventReportLineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventReportLineMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventReportLines() throws Exception {
        // Initialize the database
        eventReportLineRepository.saveAndFlush(eventReportLine);

        // Get all the eventReportLineList
        restEventReportLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventReportLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())));
    }

    @Test
    @Transactional
    void getEventReportLine() throws Exception {
        // Initialize the database
        eventReportLineRepository.saveAndFlush(eventReportLine);

        // Get the eventReportLine
        restEventReportLineMockMvc
            .perform(get(ENTITY_API_URL_ID, eventReportLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventReportLine.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEventReportLine() throws Exception {
        // Get the eventReportLine
        restEventReportLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventReportLine() throws Exception {
        // Initialize the database
        eventReportLineRepository.saveAndFlush(eventReportLine);

        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();

        // Update the eventReportLine
        EventReportLine updatedEventReportLine = eventReportLineRepository.findById(eventReportLine.getId()).get();
        // Disconnect from session so that the updates on updatedEventReportLine are not directly saved in db
        em.detach(updatedEventReportLine);
        updatedEventReportLine.quantity(UPDATED_QUANTITY).unitOfMeasure(UPDATED_UNIT_OF_MEASURE);

        restEventReportLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEventReportLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEventReportLine))
            )
            .andExpect(status().isOk());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
        EventReportLine testEventReportLine = eventReportLineList.get(eventReportLineList.size() - 1);
        assertThat(testEventReportLine.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testEventReportLine.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void putNonExistingEventReportLine() throws Exception {
        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();
        eventReportLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReportLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReportLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventReportLine() throws Exception {
        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();
        eventReportLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventReportLine() throws Exception {
        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();
        eventReportLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportLineMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventReportLineWithPatch() throws Exception {
        // Initialize the database
        eventReportLineRepository.saveAndFlush(eventReportLine);

        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();

        // Update the eventReportLine using partial update
        EventReportLine partialUpdatedEventReportLine = new EventReportLine();
        partialUpdatedEventReportLine.setId(eventReportLine.getId());

        restEventReportLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReportLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReportLine))
            )
            .andExpect(status().isOk());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
        EventReportLine testEventReportLine = eventReportLineList.get(eventReportLineList.size() - 1);
        assertThat(testEventReportLine.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testEventReportLine.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void fullUpdateEventReportLineWithPatch() throws Exception {
        // Initialize the database
        eventReportLineRepository.saveAndFlush(eventReportLine);

        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();

        // Update the eventReportLine using partial update
        EventReportLine partialUpdatedEventReportLine = new EventReportLine();
        partialUpdatedEventReportLine.setId(eventReportLine.getId());

        partialUpdatedEventReportLine.quantity(UPDATED_QUANTITY).unitOfMeasure(UPDATED_UNIT_OF_MEASURE);

        restEventReportLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReportLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReportLine))
            )
            .andExpect(status().isOk());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
        EventReportLine testEventReportLine = eventReportLineList.get(eventReportLineList.size() - 1);
        assertThat(testEventReportLine.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testEventReportLine.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
    }

    @Test
    @Transactional
    void patchNonExistingEventReportLine() throws Exception {
        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();
        eventReportLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReportLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventReportLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventReportLine() throws Exception {
        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();
        eventReportLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventReportLine() throws Exception {
        int databaseSizeBeforeUpdate = eventReportLineRepository.findAll().size();
        eventReportLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportLineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReportLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReportLine in the database
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventReportLine() throws Exception {
        // Initialize the database
        eventReportLineRepository.saveAndFlush(eventReportLine);

        int databaseSizeBeforeDelete = eventReportLineRepository.findAll().size();

        // Delete the eventReportLine
        restEventReportLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventReportLine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventReportLine> eventReportLineList = eventReportLineRepository.findAll();
        assertThat(eventReportLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
