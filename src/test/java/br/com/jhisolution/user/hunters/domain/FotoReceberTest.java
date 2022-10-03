package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoReceberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoReceber.class);
        FotoReceber fotoReceber1 = new FotoReceber();
        fotoReceber1.setId(1L);
        FotoReceber fotoReceber2 = new FotoReceber();
        fotoReceber2.setId(fotoReceber1.getId());
        assertThat(fotoReceber1).isEqualTo(fotoReceber2);
        fotoReceber2.setId(2L);
        assertThat(fotoReceber1).isNotEqualTo(fotoReceber2);
        fotoReceber1.setId(null);
        assertThat(fotoReceber1).isNotEqualTo(fotoReceber2);
    }
}
