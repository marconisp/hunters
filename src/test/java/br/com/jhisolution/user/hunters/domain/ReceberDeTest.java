package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceberDeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceberDe.class);
        ReceberDe receberDe1 = new ReceberDe();
        receberDe1.setId(1L);
        ReceberDe receberDe2 = new ReceberDe();
        receberDe2.setId(receberDe1.getId());
        assertThat(receberDe1).isEqualTo(receberDe2);
        receberDe2.setId(2L);
        assertThat(receberDe1).isNotEqualTo(receberDe2);
        receberDe1.setId(null);
        assertThat(receberDe1).isNotEqualTo(receberDe2);
    }
}
