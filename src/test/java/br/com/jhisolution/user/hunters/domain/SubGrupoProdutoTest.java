package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubGrupoProdutoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubGrupoProduto.class);
        SubGrupoProduto subGrupoProduto1 = new SubGrupoProduto();
        subGrupoProduto1.setId(1L);
        SubGrupoProduto subGrupoProduto2 = new SubGrupoProduto();
        subGrupoProduto2.setId(subGrupoProduto1.getId());
        assertThat(subGrupoProduto1).isEqualTo(subGrupoProduto2);
        subGrupoProduto2.setId(2L);
        assertThat(subGrupoProduto1).isNotEqualTo(subGrupoProduto2);
        subGrupoProduto1.setId(null);
        assertThat(subGrupoProduto1).isNotEqualTo(subGrupoProduto2);
    }
}
