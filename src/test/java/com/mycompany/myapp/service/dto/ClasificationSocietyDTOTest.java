package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasificationSocietyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasificationSocietyDTO.class);
        ClasificationSocietyDTO clasificationSocietyDTO1 = new ClasificationSocietyDTO();
        clasificationSocietyDTO1.setId(1L);
        ClasificationSocietyDTO clasificationSocietyDTO2 = new ClasificationSocietyDTO();
        assertThat(clasificationSocietyDTO1).isNotEqualTo(clasificationSocietyDTO2);
        clasificationSocietyDTO2.setId(clasificationSocietyDTO1.getId());
        assertThat(clasificationSocietyDTO1).isEqualTo(clasificationSocietyDTO2);
        clasificationSocietyDTO2.setId(2L);
        assertThat(clasificationSocietyDTO1).isNotEqualTo(clasificationSocietyDTO2);
        clasificationSocietyDTO1.setId(null);
        assertThat(clasificationSocietyDTO1).isNotEqualTo(clasificationSocietyDTO2);
    }
}
