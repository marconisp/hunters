package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvisoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aviso.class);
        Aviso aviso1 = new Aviso();
        aviso1.setId(1L);
        Aviso aviso2 = new Aviso();
        aviso2.setId(aviso1.getId());
        assertThat(aviso1).isEqualTo(aviso2);
        aviso2.setId(2L);
        assertThat(aviso1).isNotEqualTo(aviso2);
        aviso1.setId(null);
        assertThat(aviso1).isNotEqualTo(aviso2);
    }
}
