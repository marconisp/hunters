package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiaSemanaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaSemana.class);
        DiaSemana diaSemana1 = new DiaSemana();
        diaSemana1.setId(1L);
        DiaSemana diaSemana2 = new DiaSemana();
        diaSemana2.setId(diaSemana1.getId());
        assertThat(diaSemana1).isEqualTo(diaSemana2);
        diaSemana2.setId(2L);
        assertThat(diaSemana1).isNotEqualTo(diaSemana2);
        diaSemana1.setId(null);
        assertThat(diaSemana1).isNotEqualTo(diaSemana2);
    }
}
