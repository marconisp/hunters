package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PagarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagar.class);
        Pagar pagar1 = new Pagar();
        pagar1.setId(1L);
        Pagar pagar2 = new Pagar();
        pagar2.setId(pagar1.getId());
        assertThat(pagar1).isEqualTo(pagar2);
        pagar2.setId(2L);
        assertThat(pagar1).isNotEqualTo(pagar2);
        pagar1.setId(null);
        assertThat(pagar1).isNotEqualTo(pagar2);
    }
}
