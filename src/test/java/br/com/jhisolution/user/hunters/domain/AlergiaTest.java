package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlergiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alergia.class);
        Alergia alergia1 = new Alergia();
        alergia1.setId(1L);
        Alergia alergia2 = new Alergia();
        alergia2.setId(alergia1.getId());
        assertThat(alergia1).isEqualTo(alergia2);
        alergia2.setId(2L);
        assertThat(alergia1).isNotEqualTo(alergia2);
        alergia1.setId(null);
        assertThat(alergia1).isNotEqualTo(alergia2);
    }
}
