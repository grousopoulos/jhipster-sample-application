package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteLineDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BunkerReceivedNoteLineDTO.class);
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO1 = new BunkerReceivedNoteLineDTO();
        bunkerReceivedNoteLineDTO1.setId(1L);
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO2 = new BunkerReceivedNoteLineDTO();
        assertThat(bunkerReceivedNoteLineDTO1).isNotEqualTo(bunkerReceivedNoteLineDTO2);
        bunkerReceivedNoteLineDTO2.setId(bunkerReceivedNoteLineDTO1.getId());
        assertThat(bunkerReceivedNoteLineDTO1).isEqualTo(bunkerReceivedNoteLineDTO2);
        bunkerReceivedNoteLineDTO2.setId(2L);
        assertThat(bunkerReceivedNoteLineDTO1).isNotEqualTo(bunkerReceivedNoteLineDTO2);
        bunkerReceivedNoteLineDTO1.setId(null);
        assertThat(bunkerReceivedNoteLineDTO1).isNotEqualTo(bunkerReceivedNoteLineDTO2);
    }
}
