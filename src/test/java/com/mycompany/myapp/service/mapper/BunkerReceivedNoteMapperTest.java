package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteMapperTest {

    private BunkerReceivedNoteMapper bunkerReceivedNoteMapper;

    @BeforeEach
    public void setUp() {
        bunkerReceivedNoteMapper = new BunkerReceivedNoteMapperImpl();
    }
}
