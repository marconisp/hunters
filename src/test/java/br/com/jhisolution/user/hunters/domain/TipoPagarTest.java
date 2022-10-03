package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoPagarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPagar.class);
        TipoPagar tipoPagar1 = new TipoPagar();
        tipoPagar1.setId(1L);
        TipoPagar tipoPagar2 = new TipoPagar();
        tipoPagar2.setId(tipoPagar1.getId());
        assertThat(tipoPagar1).isEqualTo(tipoPagar2);
        tipoPagar2.setId(2L);
        assertThat(tipoPagar1).isNotEqualTo(tipoPagar2);
        tipoPagar1.setId(null);
        assertThat(tipoPagar1).isNotEqualTo(tipoPagar2);
    }
}
