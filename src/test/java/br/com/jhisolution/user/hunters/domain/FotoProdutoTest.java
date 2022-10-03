package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoProdutoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoProduto.class);
        FotoProduto fotoProduto1 = new FotoProduto();
        fotoProduto1.setId(1L);
        FotoProduto fotoProduto2 = new FotoProduto();
        fotoProduto2.setId(fotoProduto1.getId());
        assertThat(fotoProduto1).isEqualTo(fotoProduto2);
        fotoProduto2.setId(2L);
        assertThat(fotoProduto1).isNotEqualTo(fotoProduto2);
        fotoProduto1.setId(null);
        assertThat(fotoProduto1).isNotEqualTo(fotoProduto2);
    }
}
