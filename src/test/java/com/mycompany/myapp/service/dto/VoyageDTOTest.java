package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoyageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoyageDTO.class);
        VoyageDTO voyageDTO1 = new VoyageDTO();
        voyageDTO1.setId(1L);
        VoyageDTO voyageDTO2 = new VoyageDTO();
        assertThat(voyageDTO1).isNotEqualTo(voyageDTO2);
        voyageDTO2.setId(voyageDTO1.getId());
        assertThat(voyageDTO1).isEqualTo(voyageDTO2);
        voyageDTO2.setId(2L);
        assertThat(voyageDTO1).isNotEqualTo(voyageDTO2);
        voyageDTO1.setId(null);
        assertThat(voyageDTO1).isNotEqualTo(voyageDTO2);
    }
}
