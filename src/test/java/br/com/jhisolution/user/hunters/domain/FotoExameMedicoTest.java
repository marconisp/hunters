package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoExameMedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoExameMedico.class);
        FotoExameMedico fotoExameMedico1 = new FotoExameMedico();
        fotoExameMedico1.setId(1L);
        FotoExameMedico fotoExameMedico2 = new FotoExameMedico();
        fotoExameMedico2.setId(fotoExameMedico1.getId());
        assertThat(fotoExameMedico1).isEqualTo(fotoExameMedico2);
        fotoExameMedico2.setId(2L);
        assertThat(fotoExameMedico1).isNotEqualTo(fotoExameMedico2);
        fotoExameMedico1.setId(null);
        assertThat(fotoExameMedico1).isNotEqualTo(fotoExameMedico2);
    }
}
