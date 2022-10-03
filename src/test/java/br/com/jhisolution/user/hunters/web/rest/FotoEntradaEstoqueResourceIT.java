package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoEntradaEstoque;
import br.com.jhisolution.user.hunters.repository.FotoEntradaEstoqueRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FotoEntradaEstoqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoEntradaEstoqueResourceIT {

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-entrada-estoques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoEntradaEstoqueRepository fotoEntradaEstoqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoEntradaEstoqueMockMvc;

    private FotoEntradaEstoque fotoEntradaEstoque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoEntradaEstoque createEntity(EntityManager em) {
        FotoEntradaEstoque fotoEntradaEstoque = new FotoEntradaEstoque()
            .conteudo(DEFAULT_CONTEUDO)
            .conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return fotoEntradaEstoque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoEntradaEstoque createUpdatedEntity(EntityManager em) {
        FotoEntradaEstoque fotoEntradaEstoque = new FotoEntradaEstoque()
            .conteudo(UPDATED_CONTEUDO)
            .conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return fotoEntradaEstoque;
    }

    @BeforeEach
    public void initTest() {
        fotoEntradaEstoque = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoEntradaEstoque() throws Exception {
        int databaseSizeBeforeCreate = fotoEntradaEstoqueRepository.findAll().size();
        // Create the FotoEntradaEstoque
        restFotoEntradaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isCreated());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeCreate + 1);
        FotoEntradaEstoque testFotoEntradaEstoque = fotoEntradaEstoqueList.get(fotoEntradaEstoqueList.size() - 1);
        assertThat(testFotoEntradaEstoque.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoEntradaEstoque.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoEntradaEstoqueWithExistingId() throws Exception {
        // Create the FotoEntradaEstoque with an existing ID
        fotoEntradaEstoque.setId(1L);

        int databaseSizeBeforeCreate = fotoEntradaEstoqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoEntradaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoEntradaEstoques() throws Exception {
        // Initialize the database
        fotoEntradaEstoqueRepository.saveAndFlush(fotoEntradaEstoque);

        // Get all the fotoEntradaEstoqueList
        restFotoEntradaEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoEntradaEstoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }

    @Test
    @Transactional
    void getFotoEntradaEstoque() throws Exception {
        // Initialize the database
        fotoEntradaEstoqueRepository.saveAndFlush(fotoEntradaEstoque);

        // Get the fotoEntradaEstoque
        restFotoEntradaEstoqueMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoEntradaEstoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoEntradaEstoque.getId().intValue()))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoEntradaEstoque() throws Exception {
        // Get the fotoEntradaEstoque
        restFotoEntradaEstoqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoEntradaEstoque() throws Exception {
        // Initialize the database
        fotoEntradaEstoqueRepository.saveAndFlush(fotoEntradaEstoque);

        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();

        // Update the fotoEntradaEstoque
        FotoEntradaEstoque updatedFotoEntradaEstoque = fotoEntradaEstoqueRepository.findById(fotoEntradaEstoque.getId()).get();
        // Disconnect from session so that the updates on updatedFotoEntradaEstoque are not directly saved in db
        em.detach(updatedFotoEntradaEstoque);
        updatedFotoEntradaEstoque.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoEntradaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoEntradaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoEntradaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        FotoEntradaEstoque testFotoEntradaEstoque = fotoEntradaEstoqueList.get(fotoEntradaEstoqueList.size() - 1);
        assertThat(testFotoEntradaEstoque.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoEntradaEstoque.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();
        fotoEntradaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoEntradaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoEntradaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();
        fotoEntradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoEntradaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();
        fotoEntradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoEntradaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoEntradaEstoqueWithPatch() throws Exception {
        // Initialize the database
        fotoEntradaEstoqueRepository.saveAndFlush(fotoEntradaEstoque);

        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();

        // Update the fotoEntradaEstoque using partial update
        FotoEntradaEstoque partialUpdatedFotoEntradaEstoque = new FotoEntradaEstoque();
        partialUpdatedFotoEntradaEstoque.setId(fotoEntradaEstoque.getId());

        restFotoEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoEntradaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoEntradaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        FotoEntradaEstoque testFotoEntradaEstoque = fotoEntradaEstoqueList.get(fotoEntradaEstoqueList.size() - 1);
        assertThat(testFotoEntradaEstoque.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoEntradaEstoque.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoEntradaEstoqueWithPatch() throws Exception {
        // Initialize the database
        fotoEntradaEstoqueRepository.saveAndFlush(fotoEntradaEstoque);

        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();

        // Update the fotoEntradaEstoque using partial update
        FotoEntradaEstoque partialUpdatedFotoEntradaEstoque = new FotoEntradaEstoque();
        partialUpdatedFotoEntradaEstoque.setId(fotoEntradaEstoque.getId());

        partialUpdatedFotoEntradaEstoque.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoEntradaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoEntradaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        FotoEntradaEstoque testFotoEntradaEstoque = fotoEntradaEstoqueList.get(fotoEntradaEstoqueList.size() - 1);
        assertThat(testFotoEntradaEstoque.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoEntradaEstoque.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();
        fotoEntradaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoEntradaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();
        fotoEntradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoEntradaEstoqueRepository.findAll().size();
        fotoEntradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoEntradaEstoque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoEntradaEstoque in the database
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoEntradaEstoque() throws Exception {
        // Initialize the database
        fotoEntradaEstoqueRepository.saveAndFlush(fotoEntradaEstoque);

        int databaseSizeBeforeDelete = fotoEntradaEstoqueRepository.findAll().size();

        // Delete the fotoEntradaEstoque
        restFotoEntradaEstoqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoEntradaEstoque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoEntradaEstoque> fotoEntradaEstoqueList = fotoEntradaEstoqueRepository.findAll();
        assertThat(fotoEntradaEstoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
