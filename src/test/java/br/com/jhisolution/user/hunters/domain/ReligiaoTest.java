package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReligiaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Religiao.class);
        Religiao religiao1 = new Religiao();
        religiao1.setId(1L);
        Religiao religiao2 = new Religiao();
        religiao2.setId(religiao1.getId());
        assertThat(religiao1).isEqualTo(religiao2);
        religiao2.setId(2L);
        assertThat(religiao1).isNotEqualTo(religiao2);
        religiao1.setId(null);
        assertThat(religiao1).isNotEqualTo(religiao2);
    }
}
