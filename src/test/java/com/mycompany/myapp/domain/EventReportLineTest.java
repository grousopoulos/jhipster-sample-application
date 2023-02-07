package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReportLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReportLine.class);
        EventReportLine eventReportLine1 = new EventReportLine();
        eventReportLine1.setId(1L);
        EventReportLine eventReportLine2 = new EventReportLine();
        eventReportLine2.setId(eventReportLine1.getId());
        assertThat(eventReportLine1).isEqualTo(eventReportLine2);
        eventReportLine2.setId(2L);
        assertThat(eventReportLine1).isNotEqualTo(eventReportLine2);
        eventReportLine1.setId(null);
        assertThat(eventReportLine1).isNotEqualTo(eventReportLine2);
    }
}
