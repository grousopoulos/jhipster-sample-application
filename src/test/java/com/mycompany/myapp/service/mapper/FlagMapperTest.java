package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlagMapperTest {

    private FlagMapper flagMapper;

    @BeforeEach
    public void setUp() {
        flagMapper = new FlagMapperImpl();
    }
}
