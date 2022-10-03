package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntradaEstoqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntradaEstoque.class);
        EntradaEstoque entradaEstoque1 = new EntradaEstoque();
        entradaEstoque1.setId(1L);
        EntradaEstoque entradaEstoque2 = new EntradaEstoque();
        entradaEstoque2.setId(entradaEstoque1.getId());
        assertThat(entradaEstoque1).isEqualTo(entradaEstoque2);
        entradaEstoque2.setId(2L);
        assertThat(entradaEstoque1).isNotEqualTo(entradaEstoque2);
        entradaEstoque1.setId(null);
        assertThat(entradaEstoque1).isNotEqualTo(entradaEstoque2);
    }
}
