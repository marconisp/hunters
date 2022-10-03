package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.ItemMateria;
import br.com.jhisolution.user.hunters.repository.ItemMateriaRepository;
import br.com.jhisolution.user.hunters.service.ItemMateriaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ItemMateriaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ItemMateriaResourceIT {

    private static final String DEFAULT_NOTA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/item-materias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemMateriaRepository itemMateriaRepository;

    @Mock
    private ItemMateriaRepository itemMateriaRepositoryMock;

    @Mock
    private ItemMateriaService itemMateriaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemMateriaMockMvc;

    private ItemMateria itemMateria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemMateria createEntity(EntityManager em) {
        ItemMateria itemMateria = new ItemMateria().nota(DEFAULT_NOTA).obs(DEFAULT_OBS);
        return itemMateria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemMateria createUpdatedEntity(EntityManager em) {
        ItemMateria itemMateria = new ItemMateria().nota(UPDATED_NOTA).obs(UPDATED_OBS);
        return itemMateria;
    }

    @BeforeEach
    public void initTest() {
        itemMateria = createEntity(em);
    }

    @Test
    @Transactional
    void createItemMateria() throws Exception {
        int databaseSizeBeforeCreate = itemMateriaRepository.findAll().size();
        // Create the ItemMateria
        restItemMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemMateria)))
            .andExpect(status().isCreated());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemMateria testItemMateria = itemMateriaList.get(itemMateriaList.size() - 1);
        assertThat(testItemMateria.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testItemMateria.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createItemMateriaWithExistingId() throws Exception {
        // Create the ItemMateria with an existing ID
        itemMateria.setId(1L);

        int databaseSizeBeforeCreate = itemMateriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemMateria)))
            .andExpect(status().isBadRequest());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemMateriaRepository.findAll().size();
        // set the field null
        itemMateria.setNota(null);

        // Create the ItemMateria, which fails.

        restItemMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemMateria)))
            .andExpect(status().isBadRequest());

        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllItemMaterias() throws Exception {
        // Initialize the database
        itemMateriaRepository.saveAndFlush(itemMateria);

        // Get all the itemMateriaList
        restItemMateriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemMateria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemMateriasWithEagerRelationshipsIsEnabled() throws Exception {
        when(itemMateriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemMateriaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemMateriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemMateriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(itemMateriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemMateriaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemMateriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getItemMateria() throws Exception {
        // Initialize the database
        itemMateriaRepository.saveAndFlush(itemMateria);

        // Get the itemMateria
        restItemMateriaMockMvc
            .perform(get(ENTITY_API_URL_ID, itemMateria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemMateria.getId().intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingItemMateria() throws Exception {
        // Get the itemMateria
        restItemMateriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewItemMateria() throws Exception {
        // Initialize the database
        itemMateriaRepository.saveAndFlush(itemMateria);

        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();

        // Update the itemMateria
        ItemMateria updatedItemMateria = itemMateriaRepository.findById(itemMateria.getId()).get();
        // Disconnect from session so that the updates on updatedItemMateria are not directly saved in db
        em.detach(updatedItemMateria);
        updatedItemMateria.nota(UPDATED_NOTA).obs(UPDATED_OBS);

        restItemMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItemMateria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItemMateria))
            )
            .andExpect(status().isOk());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
        ItemMateria testItemMateria = itemMateriaList.get(itemMateriaList.size() - 1);
        assertThat(testItemMateria.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testItemMateria.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingItemMateria() throws Exception {
        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();
        itemMateria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemMateria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemMateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemMateria() throws Exception {
        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();
        itemMateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemMateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemMateria() throws Exception {
        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();
        itemMateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMateriaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemMateria)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemMateriaWithPatch() throws Exception {
        // Initialize the database
        itemMateriaRepository.saveAndFlush(itemMateria);

        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();

        // Update the itemMateria using partial update
        ItemMateria partialUpdatedItemMateria = new ItemMateria();
        partialUpdatedItemMateria.setId(itemMateria.getId());

        partialUpdatedItemMateria.nota(UPDATED_NOTA).obs(UPDATED_OBS);

        restItemMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemMateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemMateria))
            )
            .andExpect(status().isOk());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
        ItemMateria testItemMateria = itemMateriaList.get(itemMateriaList.size() - 1);
        assertThat(testItemMateria.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testItemMateria.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdateItemMateriaWithPatch() throws Exception {
        // Initialize the database
        itemMateriaRepository.saveAndFlush(itemMateria);

        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();

        // Update the itemMateria using partial update
        ItemMateria partialUpdatedItemMateria = new ItemMateria();
        partialUpdatedItemMateria.setId(itemMateria.getId());

        partialUpdatedItemMateria.nota(UPDATED_NOTA).obs(UPDATED_OBS);

        restItemMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemMateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemMateria))
            )
            .andExpect(status().isOk());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
        ItemMateria testItemMateria = itemMateriaList.get(itemMateriaList.size() - 1);
        assertThat(testItemMateria.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testItemMateria.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingItemMateria() throws Exception {
        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();
        itemMateria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemMateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemMateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemMateria() throws Exception {
        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();
        itemMateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemMateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemMateria() throws Exception {
        int databaseSizeBeforeUpdate = itemMateriaRepository.findAll().size();
        itemMateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemMateria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemMateria in the database
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemMateria() throws Exception {
        // Initialize the database
        itemMateriaRepository.saveAndFlush(itemMateria);

        int databaseSizeBeforeDelete = itemMateriaRepository.findAll().size();

        // Delete the itemMateria
        restItemMateriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemMateria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemMateria> itemMateriaList = itemMateriaRepository.findAll();
        assertThat(itemMateriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
