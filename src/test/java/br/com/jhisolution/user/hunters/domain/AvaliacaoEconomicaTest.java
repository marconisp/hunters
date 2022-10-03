package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvaliacaoEconomicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvaliacaoEconomica.class);
        AvaliacaoEconomica avaliacaoEconomica1 = new AvaliacaoEconomica();
        avaliacaoEconomica1.setId(1L);
        AvaliacaoEconomica avaliacaoEconomica2 = new AvaliacaoEconomica();
        avaliacaoEconomica2.setId(avaliacaoEconomica1.getId());
        assertThat(avaliacaoEconomica1).isEqualTo(avaliacaoEconomica2);
        avaliacaoEconomica2.setId(2L);
        assertThat(avaliacaoEconomica1).isNotEqualTo(avaliacaoEconomica2);
        avaliacaoEconomica1.setId(null);
        assertThat(avaliacaoEconomica1).isNotEqualTo(avaliacaoEconomica2);
    }
}
