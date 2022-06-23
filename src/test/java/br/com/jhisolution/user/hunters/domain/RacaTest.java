package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RacaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raca.class);
        Raca raca1 = new Raca();
        raca1.setId(1L);
        Raca raca2 = new Raca();
        raca2.setId(raca1.getId());
        assertThat(raca1).isEqualTo(raca2);
        raca2.setId(2L);
        assertThat(raca1).isNotEqualTo(raca2);
        raca1.setId(null);
        assertThat(raca1).isNotEqualTo(raca2);
    }
}
