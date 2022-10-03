package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoEventoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoEvento.class);
        EnderecoEvento enderecoEvento1 = new EnderecoEvento();
        enderecoEvento1.setId(1L);
        EnderecoEvento enderecoEvento2 = new EnderecoEvento();
        enderecoEvento2.setId(enderecoEvento1.getId());
        assertThat(enderecoEvento1).isEqualTo(enderecoEvento2);
        enderecoEvento2.setId(2L);
        assertThat(enderecoEvento1).isNotEqualTo(enderecoEvento2);
        enderecoEvento1.setId(null);
        assertThat(enderecoEvento1).isNotEqualTo(enderecoEvento2);
    }
}
