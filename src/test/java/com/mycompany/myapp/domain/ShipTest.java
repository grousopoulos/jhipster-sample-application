package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ship.class);
        Ship ship1 = new Ship();
        ship1.setId(1L);
        Ship ship2 = new Ship();
        ship2.setId(ship1.getId());
        assertThat(ship1).isEqualTo(ship2);
        ship2.setId(2L);
        assertThat(ship1).isNotEqualTo(ship2);
        ship1.setId(null);
        assertThat(ship1).isNotEqualTo(ship2);
    }
}
