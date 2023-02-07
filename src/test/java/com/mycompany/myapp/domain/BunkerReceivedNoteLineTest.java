package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BunkerReceivedNoteLine.class);
        BunkerReceivedNoteLine bunkerReceivedNoteLine1 = new BunkerReceivedNoteLine();
        bunkerReceivedNoteLine1.setId(1L);
        BunkerReceivedNoteLine bunkerReceivedNoteLine2 = new BunkerReceivedNoteLine();
        bunkerReceivedNoteLine2.setId(bunkerReceivedNoteLine1.getId());
        assertThat(bunkerReceivedNoteLine1).isEqualTo(bunkerReceivedNoteLine2);
        bunkerReceivedNoteLine2.setId(2L);
        assertThat(bunkerReceivedNoteLine1).isNotEqualTo(bunkerReceivedNoteLine2);
        bunkerReceivedNoteLine1.setId(null);
        assertThat(bunkerReceivedNoteLine1).isNotEqualTo(bunkerReceivedNoteLine2);
    }
}
