package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoSaidaEstoqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoSaidaEstoque.class);
        FotoSaidaEstoque fotoSaidaEstoque1 = new FotoSaidaEstoque();
        fotoSaidaEstoque1.setId(1L);
        FotoSaidaEstoque fotoSaidaEstoque2 = new FotoSaidaEstoque();
        fotoSaidaEstoque2.setId(fotoSaidaEstoque1.getId());
        assertThat(fotoSaidaEstoque1).isEqualTo(fotoSaidaEstoque2);
        fotoSaidaEstoque2.setId(2L);
        assertThat(fotoSaidaEstoque1).isNotEqualTo(fotoSaidaEstoque2);
        fotoSaidaEstoque1.setId(null);
        assertThat(fotoSaidaEstoque1).isNotEqualTo(fotoSaidaEstoque2);
    }
}
