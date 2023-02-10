package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipDTO.class);
        ShipDTO shipDTO1 = new ShipDTO();
        shipDTO1.setId(1L);
        ShipDTO shipDTO2 = new ShipDTO();
        assertThat(shipDTO1).isNotEqualTo(shipDTO2);
        shipDTO2.setId(shipDTO1.getId());
        assertThat(shipDTO1).isEqualTo(shipDTO2);
        shipDTO2.setId(2L);
        assertThat(shipDTO1).isNotEqualTo(shipDTO2);
        shipDTO1.setId(null);
        assertThat(shipDTO1).isNotEqualTo(shipDTO2);
    }
}
