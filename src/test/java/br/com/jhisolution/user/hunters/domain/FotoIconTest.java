package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoIconTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoIcon.class);
        FotoIcon fotoIcon1 = new FotoIcon();
        fotoIcon1.setId(1L);
        FotoIcon fotoIcon2 = new FotoIcon();
        fotoIcon2.setId(fotoIcon1.getId());
        assertThat(fotoIcon1).isEqualTo(fotoIcon2);
        fotoIcon2.setId(2L);
        assertThat(fotoIcon1).isNotEqualTo(fotoIcon2);
        fotoIcon1.setId(null);
        assertThat(fotoIcon1).isNotEqualTo(fotoIcon2);
    }
}
