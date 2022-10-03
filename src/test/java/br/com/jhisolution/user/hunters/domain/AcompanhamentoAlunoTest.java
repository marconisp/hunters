package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcompanhamentoAlunoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcompanhamentoAluno.class);
        AcompanhamentoAluno acompanhamentoAluno1 = new AcompanhamentoAluno();
        acompanhamentoAluno1.setId(1L);
        AcompanhamentoAluno acompanhamentoAluno2 = new AcompanhamentoAluno();
        acompanhamentoAluno2.setId(acompanhamentoAluno1.getId());
        assertThat(acompanhamentoAluno1).isEqualTo(acompanhamentoAluno2);
        acompanhamentoAluno2.setId(2L);
        assertThat(acompanhamentoAluno1).isNotEqualTo(acompanhamentoAluno2);
        acompanhamentoAluno1.setId(null);
        assertThat(acompanhamentoAluno1).isNotEqualTo(acompanhamentoAluno2);
    }
}
