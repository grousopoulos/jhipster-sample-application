package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReportLineDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReportLineDTO.class);
        EventReportLineDTO eventReportLineDTO1 = new EventReportLineDTO();
        eventReportLineDTO1.setId(1L);
        EventReportLineDTO eventReportLineDTO2 = new EventReportLineDTO();
        assertThat(eventReportLineDTO1).isNotEqualTo(eventReportLineDTO2);
        eventReportLineDTO2.setId(eventReportLineDTO1.getId());
        assertThat(eventReportLineDTO1).isEqualTo(eventReportLineDTO2);
        eventReportLineDTO2.setId(2L);
        assertThat(eventReportLineDTO1).isNotEqualTo(eventReportLineDTO2);
        eventReportLineDTO1.setId(null);
        assertThat(eventReportLineDTO1).isNotEqualTo(eventReportLineDTO2);
    }
}
