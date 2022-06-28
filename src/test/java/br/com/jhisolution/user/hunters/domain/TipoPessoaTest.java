package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoPessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPessoa.class);
        TipoPessoa tipoPessoa1 = new TipoPessoa();
        tipoPessoa1.setId(1L);
        TipoPessoa tipoPessoa2 = new TipoPessoa();
        tipoPessoa2.setId(tipoPessoa1.getId());
        assertThat(tipoPessoa1).isEqualTo(tipoPessoa2);
        tipoPessoa2.setId(2L);
        assertThat(tipoPessoa1).isNotEqualTo(tipoPessoa2);
        tipoPessoa1.setId(null);
        assertThat(tipoPessoa1).isNotEqualTo(tipoPessoa2);
    }
}
