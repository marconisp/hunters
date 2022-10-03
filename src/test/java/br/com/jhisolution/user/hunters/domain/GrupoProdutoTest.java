package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoProdutoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoProduto.class);
        GrupoProduto grupoProduto1 = new GrupoProduto();
        grupoProduto1.setId(1L);
        GrupoProduto grupoProduto2 = new GrupoProduto();
        grupoProduto2.setId(grupoProduto1.getId());
        assertThat(grupoProduto1).isEqualTo(grupoProduto2);
        grupoProduto2.setId(2L);
        assertThat(grupoProduto1).isNotEqualTo(grupoProduto2);
        grupoProduto1.setId(null);
        assertThat(grupoProduto1).isNotEqualTo(grupoProduto2);
    }
}
