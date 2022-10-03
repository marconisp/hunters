package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receber.class);
        Receber receber1 = new Receber();
        receber1.setId(1L);
        Receber receber2 = new Receber();
        receber2.setId(receber1.getId());
        assertThat(receber1).isEqualTo(receber2);
        receber2.setId(2L);
        assertThat(receber1).isNotEqualTo(receber2);
        receber1.setId(null);
        assertThat(receber1).isNotEqualTo(receber2);
    }
}
