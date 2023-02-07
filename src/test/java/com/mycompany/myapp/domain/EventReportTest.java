package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReport.class);
        EventReport eventReport1 = new EventReport();
        eventReport1.setId(1L);
        EventReport eventReport2 = new EventReport();
        eventReport2.setId(eventReport1.getId());
        assertThat(eventReport1).isEqualTo(eventReport2);
        eventReport2.setId(2L);
        assertThat(eventReport1).isNotEqualTo(eventReport2);
        eventReport1.setId(null);
        assertThat(eventReport1).isNotEqualTo(eventReport2);
    }
}
