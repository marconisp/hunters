package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.SubGrupoProduto;
import br.com.jhisolution.user.hunters.repository.SubGrupoProdutoRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubGrupoProdutoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubGrupoProdutoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-grupo-produtos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubGrupoProdutoRepository subGrupoProdutoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubGrupoProdutoMockMvc;

    private SubGrupoProduto subGrupoProduto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubGrupoProduto createEntity(EntityManager em) {
        SubGrupoProduto subGrupoProduto = new SubGrupoProduto().nome(DEFAULT_NOME).obs(DEFAULT_OBS);
        return subGrupoProduto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubGrupoProduto createUpdatedEntity(EntityManager em) {
        SubGrupoProduto subGrupoProduto = new SubGrupoProduto().nome(UPDATED_NOME).obs(UPDATED_OBS);
        return subGrupoProduto;
    }

    @BeforeEach
    public void initTest() {
        subGrupoProduto = createEntity(em);
    }

    @Test
    @Transactional
    void createSubGrupoProduto() throws Exception {
        int databaseSizeBeforeCreate = subGrupoProdutoRepository.findAll().size();
        // Create the SubGrupoProduto
        restSubGrupoProdutoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isCreated());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeCreate + 1);
        SubGrupoProduto testSubGrupoProduto = subGrupoProdutoList.get(subGrupoProdutoList.size() - 1);
        assertThat(testSubGrupoProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSubGrupoProduto.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createSubGrupoProdutoWithExistingId() throws Exception {
        // Create the SubGrupoProduto with an existing ID
        subGrupoProduto.setId(1L);

        int databaseSizeBeforeCreate = subGrupoProdutoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubGrupoProdutoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subGrupoProdutoRepository.findAll().size();
        // set the field null
        subGrupoProduto.setNome(null);

        // Create the SubGrupoProduto, which fails.

        restSubGrupoProdutoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isBadRequest());

        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubGrupoProdutos() throws Exception {
        // Initialize the database
        subGrupoProdutoRepository.saveAndFlush(subGrupoProduto);

        // Get all the subGrupoProdutoList
        restSubGrupoProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subGrupoProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @Test
    @Transactional
    void getSubGrupoProduto() throws Exception {
        // Initialize the database
        subGrupoProdutoRepository.saveAndFlush(subGrupoProduto);

        // Get the subGrupoProduto
        restSubGrupoProdutoMockMvc
            .perform(get(ENTITY_API_URL_ID, subGrupoProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subGrupoProduto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingSubGrupoProduto() throws Exception {
        // Get the subGrupoProduto
        restSubGrupoProdutoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubGrupoProduto() throws Exception {
        // Initialize the database
        subGrupoProdutoRepository.saveAndFlush(subGrupoProduto);

        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();

        // Update the subGrupoProduto
        SubGrupoProduto updatedSubGrupoProduto = subGrupoProdutoRepository.findById(subGrupoProduto.getId()).get();
        // Disconnect from session so that the updates on updatedSubGrupoProduto are not directly saved in db
        em.detach(updatedSubGrupoProduto);
        updatedSubGrupoProduto.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restSubGrupoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubGrupoProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubGrupoProduto))
            )
            .andExpect(status().isOk());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
        SubGrupoProduto testSubGrupoProduto = subGrupoProdutoList.get(subGrupoProdutoList.size() - 1);
        assertThat(testSubGrupoProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSubGrupoProduto.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingSubGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();
        subGrupoProduto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubGrupoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subGrupoProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();
        subGrupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubGrupoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();
        subGrupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubGrupoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubGrupoProdutoWithPatch() throws Exception {
        // Initialize the database
        subGrupoProdutoRepository.saveAndFlush(subGrupoProduto);

        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();

        // Update the subGrupoProduto using partial update
        SubGrupoProduto partialUpdatedSubGrupoProduto = new SubGrupoProduto();
        partialUpdatedSubGrupoProduto.setId(subGrupoProduto.getId());

        partialUpdatedSubGrupoProduto.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restSubGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubGrupoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubGrupoProduto))
            )
            .andExpect(status().isOk());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
        SubGrupoProduto testSubGrupoProduto = subGrupoProdutoList.get(subGrupoProdutoList.size() - 1);
        assertThat(testSubGrupoProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSubGrupoProduto.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdateSubGrupoProdutoWithPatch() throws Exception {
        // Initialize the database
        subGrupoProdutoRepository.saveAndFlush(subGrupoProduto);

        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();

        // Update the subGrupoProduto using partial update
        SubGrupoProduto partialUpdatedSubGrupoProduto = new SubGrupoProduto();
        partialUpdatedSubGrupoProduto.setId(subGrupoProduto.getId());

        partialUpdatedSubGrupoProduto.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restSubGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubGrupoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubGrupoProduto))
            )
            .andExpect(status().isOk());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
        SubGrupoProduto testSubGrupoProduto = subGrupoProdutoList.get(subGrupoProdutoList.size() - 1);
        assertThat(testSubGrupoProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSubGrupoProduto.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingSubGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();
        subGrupoProduto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subGrupoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();
        subGrupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = subGrupoProdutoRepository.findAll().size();
        subGrupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subGrupoProduto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubGrupoProduto in the database
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubGrupoProduto() throws Exception {
        // Initialize the database
        subGrupoProdutoRepository.saveAndFlush(subGrupoProduto);

        int databaseSizeBeforeDelete = subGrupoProdutoRepository.findAll().size();

        // Delete the subGrupoProduto
        restSubGrupoProdutoMockMvc
            .perform(delete(ENTITY_API_URL_ID, subGrupoProduto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubGrupoProduto> subGrupoProdutoList = subGrupoProdutoRepository.findAll();
        assertThat(subGrupoProdutoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
