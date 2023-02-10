package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReportDTO.class);
        EventReportDTO eventReportDTO1 = new EventReportDTO();
        eventReportDTO1.setId(1L);
        EventReportDTO eventReportDTO2 = new EventReportDTO();
        assertThat(eventReportDTO1).isNotEqualTo(eventReportDTO2);
        eventReportDTO2.setId(eventReportDTO1.getId());
        assertThat(eventReportDTO1).isEqualTo(eventReportDTO2);
        eventReportDTO2.setId(2L);
        assertThat(eventReportDTO1).isNotEqualTo(eventReportDTO2);
        eventReportDTO1.setId(null);
        assertThat(eventReportDTO1).isNotEqualTo(eventReportDTO2);
    }
}
