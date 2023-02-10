package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClasificationSocietyMapperTest {

    private ClasificationSocietyMapper clasificationSocietyMapper;

    @BeforeEach
    public void setUp() {
        clasificationSocietyMapper = new ClasificationSocietyMapperImpl();
    }
}
