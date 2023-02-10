package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTypeDTO.class);
        FuelTypeDTO fuelTypeDTO1 = new FuelTypeDTO();
        fuelTypeDTO1.setId(1L);
        FuelTypeDTO fuelTypeDTO2 = new FuelTypeDTO();
        assertThat(fuelTypeDTO1).isNotEqualTo(fuelTypeDTO2);
        fuelTypeDTO2.setId(fuelTypeDTO1.getId());
        assertThat(fuelTypeDTO1).isEqualTo(fuelTypeDTO2);
        fuelTypeDTO2.setId(2L);
        assertThat(fuelTypeDTO1).isNotEqualTo(fuelTypeDTO2);
        fuelTypeDTO1.setId(null);
        assertThat(fuelTypeDTO1).isNotEqualTo(fuelTypeDTO2);
    }
}
