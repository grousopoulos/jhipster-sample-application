package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteLineMapperTest {

    private BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper;

    @BeforeEach
    public void setUp() {
        bunkerReceivedNoteLineMapper = new BunkerReceivedNoteLineMapperImpl();
    }
}
