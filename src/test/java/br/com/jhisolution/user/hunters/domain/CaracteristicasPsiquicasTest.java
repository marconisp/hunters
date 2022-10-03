package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaracteristicasPsiquicasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaracteristicasPsiquicas.class);
        CaracteristicasPsiquicas caracteristicasPsiquicas1 = new CaracteristicasPsiquicas();
        caracteristicasPsiquicas1.setId(1L);
        CaracteristicasPsiquicas caracteristicasPsiquicas2 = new CaracteristicasPsiquicas();
        caracteristicasPsiquicas2.setId(caracteristicasPsiquicas1.getId());
        assertThat(caracteristicasPsiquicas1).isEqualTo(caracteristicasPsiquicas2);
        caracteristicasPsiquicas2.setId(2L);
        assertThat(caracteristicasPsiquicas1).isNotEqualTo(caracteristicasPsiquicas2);
        caracteristicasPsiquicas1.setId(null);
        assertThat(caracteristicasPsiquicas1).isNotEqualTo(caracteristicasPsiquicas2);
    }
}
