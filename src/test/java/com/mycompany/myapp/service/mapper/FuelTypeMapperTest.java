package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuelTypeMapperTest {

    private FuelTypeMapper fuelTypeMapper;

    @BeforeEach
    public void setUp() {
        fuelTypeMapper = new FuelTypeMapperImpl();
    }
}
