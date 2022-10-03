package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoEntradaEstoqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoEntradaEstoque.class);
        FotoEntradaEstoque fotoEntradaEstoque1 = new FotoEntradaEstoque();
        fotoEntradaEstoque1.setId(1L);
        FotoEntradaEstoque fotoEntradaEstoque2 = new FotoEntradaEstoque();
        fotoEntradaEstoque2.setId(fotoEntradaEstoque1.getId());
        assertThat(fotoEntradaEstoque1).isEqualTo(fotoEntradaEstoque2);
        fotoEntradaEstoque2.setId(2L);
        assertThat(fotoEntradaEstoque1).isNotEqualTo(fotoEntradaEstoque2);
        fotoEntradaEstoque1.setId(null);
        assertThat(fotoEntradaEstoque1).isNotEqualTo(fotoEntradaEstoque2);
    }
}
