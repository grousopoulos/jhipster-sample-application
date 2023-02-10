package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlagDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlagDTO.class);
        FlagDTO flagDTO1 = new FlagDTO();
        flagDTO1.setId(1L);
        FlagDTO flagDTO2 = new FlagDTO();
        assertThat(flagDTO1).isNotEqualTo(flagDTO2);
        flagDTO2.setId(flagDTO1.getId());
        assertThat(flagDTO1).isEqualTo(flagDTO2);
        flagDTO2.setId(2L);
        assertThat(flagDTO1).isNotEqualTo(flagDTO2);
        flagDTO1.setId(null);
        assertThat(flagDTO1).isNotEqualTo(flagDTO2);
    }
}
