package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasificationSocietyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasificationSociety.class);
        ClasificationSociety clasificationSociety1 = new ClasificationSociety();
        clasificationSociety1.setId(1L);
        ClasificationSociety clasificationSociety2 = new ClasificationSociety();
        clasificationSociety2.setId(clasificationSociety1.getId());
        assertThat(clasificationSociety1).isEqualTo(clasificationSociety2);
        clasificationSociety2.setId(2L);
        assertThat(clasificationSociety1).isNotEqualTo(clasificationSociety2);
        clasificationSociety1.setId(null);
        assertThat(clasificationSociety1).isNotEqualTo(clasificationSociety2);
    }
}
