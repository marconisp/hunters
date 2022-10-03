package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PagarParaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagarPara.class);
        PagarPara pagarPara1 = new PagarPara();
        pagarPara1.setId(1L);
        PagarPara pagarPara2 = new PagarPara();
        pagarPara2.setId(pagarPara1.getId());
        assertThat(pagarPara1).isEqualTo(pagarPara2);
        pagarPara2.setId(2L);
        assertThat(pagarPara1).isNotEqualTo(pagarPara2);
        pagarPara1.setId(null);
        assertThat(pagarPara1).isNotEqualTo(pagarPara2);
    }
}
