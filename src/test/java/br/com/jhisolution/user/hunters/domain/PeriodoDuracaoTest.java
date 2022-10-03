package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodoDuracaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoDuracao.class);
        PeriodoDuracao periodoDuracao1 = new PeriodoDuracao();
        periodoDuracao1.setId(1L);
        PeriodoDuracao periodoDuracao2 = new PeriodoDuracao();
        periodoDuracao2.setId(periodoDuracao1.getId());
        assertThat(periodoDuracao1).isEqualTo(periodoDuracao2);
        periodoDuracao2.setId(2L);
        assertThat(periodoDuracao1).isNotEqualTo(periodoDuracao2);
        periodoDuracao1.setId(null);
        assertThat(periodoDuracao1).isNotEqualTo(periodoDuracao2);
    }
}
