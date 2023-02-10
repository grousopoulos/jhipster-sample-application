package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipMapperTest {

    private ShipMapper shipMapper;

    @BeforeEach
    public void setUp() {
        shipMapper = new ShipMapperImpl();
    }
}
