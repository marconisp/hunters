package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoMensagemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMensagem.class);
        TipoMensagem tipoMensagem1 = new TipoMensagem();
        tipoMensagem1.setId(1L);
        TipoMensagem tipoMensagem2 = new TipoMensagem();
        tipoMensagem2.setId(tipoMensagem1.getId());
        assertThat(tipoMensagem1).isEqualTo(tipoMensagem2);
        tipoMensagem2.setId(2L);
        assertThat(tipoMensagem1).isNotEqualTo(tipoMensagem2);
        tipoMensagem1.setId(null);
        assertThat(tipoMensagem1).isNotEqualTo(tipoMensagem2);
    }
}
