package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoReceberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoReceber.class);
        TipoReceber tipoReceber1 = new TipoReceber();
        tipoReceber1.setId(1L);
        TipoReceber tipoReceber2 = new TipoReceber();
        tipoReceber2.setId(tipoReceber1.getId());
        assertThat(tipoReceber1).isEqualTo(tipoReceber2);
        tipoReceber2.setId(2L);
        assertThat(tipoReceber1).isNotEqualTo(tipoReceber2);
        tipoReceber1.setId(null);
        assertThat(tipoReceber1).isNotEqualTo(tipoReceber2);
    }
}
