package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoCivilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoCivil.class);
        EstadoCivil estadoCivil1 = new EstadoCivil();
        estadoCivil1.setId(1L);
        EstadoCivil estadoCivil2 = new EstadoCivil();
        estadoCivil2.setId(estadoCivil1.getId());
        assertThat(estadoCivil1).isEqualTo(estadoCivil2);
        estadoCivil2.setId(2L);
        assertThat(estadoCivil1).isNotEqualTo(estadoCivil2);
        estadoCivil1.setId(null);
        assertThat(estadoCivil1).isNotEqualTo(estadoCivil2);
    }
}
