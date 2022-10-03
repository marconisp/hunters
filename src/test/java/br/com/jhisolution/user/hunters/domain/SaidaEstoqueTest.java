package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaidaEstoqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaidaEstoque.class);
        SaidaEstoque saidaEstoque1 = new SaidaEstoque();
        saidaEstoque1.setId(1L);
        SaidaEstoque saidaEstoque2 = new SaidaEstoque();
        saidaEstoque2.setId(saidaEstoque1.getId());
        assertThat(saidaEstoque1).isEqualTo(saidaEstoque2);
        saidaEstoque2.setId(2L);
        assertThat(saidaEstoque1).isNotEqualTo(saidaEstoque2);
        saidaEstoque1.setId(null);
        assertThat(saidaEstoque1).isNotEqualTo(saidaEstoque2);
    }
}
