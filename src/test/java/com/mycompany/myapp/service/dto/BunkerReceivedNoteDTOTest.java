package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BunkerReceivedNoteDTO.class);
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO1 = new BunkerReceivedNoteDTO();
        bunkerReceivedNoteDTO1.setId(1L);
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO2 = new BunkerReceivedNoteDTO();
        assertThat(bunkerReceivedNoteDTO1).isNotEqualTo(bunkerReceivedNoteDTO2);
        bunkerReceivedNoteDTO2.setId(bunkerReceivedNoteDTO1.getId());
        assertThat(bunkerReceivedNoteDTO1).isEqualTo(bunkerReceivedNoteDTO2);
        bunkerReceivedNoteDTO2.setId(2L);
        assertThat(bunkerReceivedNoteDTO1).isNotEqualTo(bunkerReceivedNoteDTO2);
        bunkerReceivedNoteDTO1.setId(null);
        assertThat(bunkerReceivedNoteDTO1).isNotEqualTo(bunkerReceivedNoteDTO2);
    }
}
