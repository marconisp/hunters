package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.GrupoProduto;
import br.com.jhisolution.user.hunters.repository.GrupoProdutoRepository;
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
 * Integration tests for the {@link GrupoProdutoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoProdutoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grupo-produtos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GrupoProdutoRepository grupoProdutoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoProdutoMockMvc;

    private GrupoProduto grupoProduto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoProduto createEntity(EntityManager em) {
        GrupoProduto grupoProduto = new GrupoProduto().nome(DEFAULT_NOME).obs(DEFAULT_OBS);
        return grupoProduto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoProduto createUpdatedEntity(EntityManager em) {
        GrupoProduto grupoProduto = new GrupoProduto().nome(UPDATED_NOME).obs(UPDATED_OBS);
        return grupoProduto;
    }

    @BeforeEach
    public void initTest() {
        grupoProduto = createEntity(em);
    }

    @Test
    @Transactional
    void createGrupoProduto() throws Exception {
        int databaseSizeBeforeCreate = grupoProdutoRepository.findAll().size();
        // Create the GrupoProduto
        restGrupoProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoProduto)))
            .andExpect(status().isCreated());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoProduto testGrupoProduto = grupoProdutoList.get(grupoProdutoList.size() - 1);
        assertThat(testGrupoProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testGrupoProduto.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createGrupoProdutoWithExistingId() throws Exception {
        // Create the GrupoProduto with an existing ID
        grupoProduto.setId(1L);

        int databaseSizeBeforeCreate = grupoProdutoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoProduto)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoProdutoRepository.findAll().size();
        // set the field null
        grupoProduto.setNome(null);

        // Create the GrupoProduto, which fails.

        restGrupoProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoProduto)))
            .andExpect(status().isBadRequest());

        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGrupoProdutos() throws Exception {
        // Initialize the database
        grupoProdutoRepository.saveAndFlush(grupoProduto);

        // Get all the grupoProdutoList
        restGrupoProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @Test
    @Transactional
    void getGrupoProduto() throws Exception {
        // Initialize the database
        grupoProdutoRepository.saveAndFlush(grupoProduto);

        // Get the grupoProduto
        restGrupoProdutoMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoProduto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingGrupoProduto() throws Exception {
        // Get the grupoProduto
        restGrupoProdutoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGrupoProduto() throws Exception {
        // Initialize the database
        grupoProdutoRepository.saveAndFlush(grupoProduto);

        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();

        // Update the grupoProduto
        GrupoProduto updatedGrupoProduto = grupoProdutoRepository.findById(grupoProduto.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoProduto are not directly saved in db
        em.detach(updatedGrupoProduto);
        updatedGrupoProduto.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restGrupoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGrupoProduto))
            )
            .andExpect(status().isOk());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
        GrupoProduto testGrupoProduto = grupoProdutoList.get(grupoProdutoList.size() - 1);
        assertThat(testGrupoProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testGrupoProduto.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();
        grupoProduto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();
        grupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();
        grupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoProdutoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoProduto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoProdutoWithPatch() throws Exception {
        // Initialize the database
        grupoProdutoRepository.saveAndFlush(grupoProduto);

        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();

        // Update the grupoProduto using partial update
        GrupoProduto partialUpdatedGrupoProduto = new GrupoProduto();
        partialUpdatedGrupoProduto.setId(grupoProduto.getId());

        restGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoProduto))
            )
            .andExpect(status().isOk());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
        GrupoProduto testGrupoProduto = grupoProdutoList.get(grupoProdutoList.size() - 1);
        assertThat(testGrupoProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testGrupoProduto.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void fullUpdateGrupoProdutoWithPatch() throws Exception {
        // Initialize the database
        grupoProdutoRepository.saveAndFlush(grupoProduto);

        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();

        // Update the grupoProduto using partial update
        GrupoProduto partialUpdatedGrupoProduto = new GrupoProduto();
        partialUpdatedGrupoProduto.setId(grupoProduto.getId());

        partialUpdatedGrupoProduto.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoProduto))
            )
            .andExpect(status().isOk());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
        GrupoProduto testGrupoProduto = grupoProdutoList.get(grupoProdutoList.size() - 1);
        assertThat(testGrupoProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testGrupoProduto.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();
        grupoProduto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();
        grupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoProduto() throws Exception {
        int databaseSizeBeforeUpdate = grupoProdutoRepository.findAll().size();
        grupoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(grupoProduto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoProduto in the database
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoProduto() throws Exception {
        // Initialize the database
        grupoProdutoRepository.saveAndFlush(grupoProduto);

        int databaseSizeBeforeDelete = grupoProdutoRepository.findAll().size();

        // Delete the grupoProduto
        restGrupoProdutoMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoProduto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoProduto> grupoProdutoList = grupoProdutoRepository.findAll();
        assertThat(grupoProdutoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
