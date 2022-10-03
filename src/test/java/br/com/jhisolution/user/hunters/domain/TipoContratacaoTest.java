package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoContratacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoContratacao.class);
        TipoContratacao tipoContratacao1 = new TipoContratacao();
        tipoContratacao1.setId(1L);
        TipoContratacao tipoContratacao2 = new TipoContratacao();
        tipoContratacao2.setId(tipoContratacao1.getId());
        assertThat(tipoContratacao1).isEqualTo(tipoContratacao2);
        tipoContratacao2.setId(2L);
        assertThat(tipoContratacao1).isNotEqualTo(tipoContratacao2);
        tipoContratacao1.setId(null);
        assertThat(tipoContratacao1).isNotEqualTo(tipoContratacao2);
    }
}
