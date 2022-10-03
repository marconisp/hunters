package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendaColaboradorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaColaborador.class);
        AgendaColaborador agendaColaborador1 = new AgendaColaborador();
        agendaColaborador1.setId(1L);
        AgendaColaborador agendaColaborador2 = new AgendaColaborador();
        agendaColaborador2.setId(agendaColaborador1.getId());
        assertThat(agendaColaborador1).isEqualTo(agendaColaborador2);
        agendaColaborador2.setId(2L);
        assertThat(agendaColaborador1).isNotEqualTo(agendaColaborador2);
        agendaColaborador1.setId(null);
        assertThat(agendaColaborador1).isNotEqualTo(agendaColaborador2);
    }
}
