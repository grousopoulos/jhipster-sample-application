package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoyageMapperTest {

    private VoyageMapper voyageMapper;

    @BeforeEach
    public void setUp() {
        voyageMapper = new VoyageMapperImpl();
    }
}
