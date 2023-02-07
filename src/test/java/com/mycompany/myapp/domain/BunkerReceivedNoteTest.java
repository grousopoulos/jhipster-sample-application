package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BunkerReceivedNote.class);
        BunkerReceivedNote bunkerReceivedNote1 = new BunkerReceivedNote();
        bunkerReceivedNote1.setId(1L);
        BunkerReceivedNote bunkerReceivedNote2 = new BunkerReceivedNote();
        bunkerReceivedNote2.setId(bunkerReceivedNote1.getId());
        assertThat(bunkerReceivedNote1).isEqualTo(bunkerReceivedNote2);
        bunkerReceivedNote2.setId(2L);
        assertThat(bunkerReceivedNote1).isNotEqualTo(bunkerReceivedNote2);
        bunkerReceivedNote1.setId(null);
        assertThat(bunkerReceivedNote1).isNotEqualTo(bunkerReceivedNote2);
    }
}
