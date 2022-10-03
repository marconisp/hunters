package br.com.jhisolution.user.hunters.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhisolution.user.hunters.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemMateriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemMateria.class);
        ItemMateria itemMateria1 = new ItemMateria();
        itemMateria1.setId(1L);
        ItemMateria itemMateria2 = new ItemMateria();
        itemMateria2.setId(itemMateria1.getId());
        assertThat(itemMateria1).isEqualTo(itemMateria2);
        itemMateria2.setId(2L);
        assertThat(itemMateria1).isNotEqualTo(itemMateria2);
        itemMateria1.setId(null);
        assertThat(itemMateria1).isNotEqualTo(itemMateria2);
    }
}
