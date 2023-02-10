package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventReportMapperTest {

    private EventReportMapper eventReportMapper;

    @BeforeEach
    public void setUp() {
        eventReportMapper = new EventReportMapperImpl();
    }
}
