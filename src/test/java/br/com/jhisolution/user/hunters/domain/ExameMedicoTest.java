package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExameMedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExameMedico.class);
        ExameMedico exameMedico1 = new ExameMedico();
        exameMedico1.setId(1L);
        ExameMedico exameMedico2 = new ExameMedico();
        exameMedico2.setId(exameMedico1.getId());
        assertThat(exameMedico1).isEqualTo(exameMedico2);
        exameMedico2.setId(2L);
        assertThat(exameMedico1).isNotEqualTo(exameMedico2);
        exameMedico1.setId(null);
        assertThat(exameMedico1).isNotEqualTo(exameMedico2);
    }
}
