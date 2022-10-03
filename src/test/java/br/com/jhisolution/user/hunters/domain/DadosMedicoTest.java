package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DadosMedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DadosMedico.class);
        DadosMedico dadosMedico1 = new DadosMedico();
        dadosMedico1.setId(1L);
        DadosMedico dadosMedico2 = new DadosMedico();
        dadosMedico2.setId(dadosMedico1.getId());
        assertThat(dadosMedico1).isEqualTo(dadosMedico2);
        dadosMedico2.setId(2L);
        assertThat(dadosMedico1).isNotEqualTo(dadosMedico2);
        dadosMedico1.setId(null);
        assertThat(dadosMedico1).isNotEqualTo(dadosMedico2);
    }
}
