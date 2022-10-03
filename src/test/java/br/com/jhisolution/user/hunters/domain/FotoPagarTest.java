package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoPagarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoPagar.class);
        FotoPagar fotoPagar1 = new FotoPagar();
        fotoPagar1.setId(1L);
        FotoPagar fotoPagar2 = new FotoPagar();
        fotoPagar2.setId(fotoPagar1.getId());
        assertThat(fotoPagar1).isEqualTo(fotoPagar2);
        fotoPagar2.setId(2L);
        assertThat(fotoPagar1).isNotEqualTo(fotoPagar2);
        fotoPagar1.setId(null);
        assertThat(fotoPagar1).isNotEqualTo(fotoPagar2);
    }
}
