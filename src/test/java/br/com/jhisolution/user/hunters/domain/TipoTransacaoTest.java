package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoTransacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTransacao.class);
        TipoTransacao tipoTransacao1 = new TipoTransacao();
        tipoTransacao1.setId(1L);
        TipoTransacao tipoTransacao2 = new TipoTransacao();
        tipoTransacao2.setId(tipoTransacao1.getId());
        assertThat(tipoTransacao1).isEqualTo(tipoTransacao2);
        tipoTransacao2.setId(2L);
        assertThat(tipoTransacao1).isNotEqualTo(tipoTransacao2);
        tipoTransacao1.setId(null);
        assertThat(tipoTransacao1).isNotEqualTo(tipoTransacao2);
    }
}
