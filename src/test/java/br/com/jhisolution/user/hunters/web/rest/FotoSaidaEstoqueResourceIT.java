package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoSaidaEstoque;
import br.com.jhisolution.user.hunters.repository.FotoSaidaEstoqueRepository;
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
 * Integration tests for the {@link FotoSaidaEstoqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoSaidaEstoqueResourceIT {

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-saida-estoques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoSaidaEstoqueRepository fotoSaidaEstoqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoSaidaEstoqueMockMvc;

    private FotoSaidaEstoque fotoSaidaEstoque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoSaidaEstoque createEntity(EntityManager em) {
        FotoSaidaEstoque fotoSaidaEstoque = new FotoSaidaEstoque()
            .conteudo(DEFAULT_CONTEUDO)
            .conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return fotoSaidaEstoque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoSaidaEstoque createUpdatedEntity(EntityManager em) {
        FotoSaidaEstoque fotoSaidaEstoque = new FotoSaidaEstoque()
            .conteudo(UPDATED_CONTEUDO)
            .conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return fotoSaidaEstoque;
    }

    @BeforeEach
    public void initTest() {
        fotoSaidaEstoque = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoSaidaEstoque() throws Exception {
        int databaseSizeBeforeCreate = fotoSaidaEstoqueRepository.findAll().size();
        // Create the FotoSaidaEstoque
        restFotoSaidaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isCreated());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeCreate + 1);
        FotoSaidaEstoque testFotoSaidaEstoque = fotoSaidaEstoqueList.get(fotoSaidaEstoqueList.size() - 1);
        assertThat(testFotoSaidaEstoque.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoSaidaEstoque.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoSaidaEstoqueWithExistingId() throws Exception {
        // Create the FotoSaidaEstoque with an existing ID
        fotoSaidaEstoque.setId(1L);

        int databaseSizeBeforeCreate = fotoSaidaEstoqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoSaidaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoSaidaEstoques() throws Exception {
        // Initialize the database
        fotoSaidaEstoqueRepository.saveAndFlush(fotoSaidaEstoque);

        // Get all the fotoSaidaEstoqueList
        restFotoSaidaEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoSaidaEstoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }

    @Test
    @Transactional
    void getFotoSaidaEstoque() throws Exception {
        // Initialize the database
        fotoSaidaEstoqueRepository.saveAndFlush(fotoSaidaEstoque);

        // Get the fotoSaidaEstoque
        restFotoSaidaEstoqueMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoSaidaEstoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoSaidaEstoque.getId().intValue()))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoSaidaEstoque() throws Exception {
        // Get the fotoSaidaEstoque
        restFotoSaidaEstoqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoSaidaEstoque() throws Exception {
        // Initialize the database
        fotoSaidaEstoqueRepository.saveAndFlush(fotoSaidaEstoque);

        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();

        // Update the fotoSaidaEstoque
        FotoSaidaEstoque updatedFotoSaidaEstoque = fotoSaidaEstoqueRepository.findById(fotoSaidaEstoque.getId()).get();
        // Disconnect from session so that the updates on updatedFotoSaidaEstoque are not directly saved in db
        em.detach(updatedFotoSaidaEstoque);
        updatedFotoSaidaEstoque.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoSaidaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoSaidaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoSaidaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        FotoSaidaEstoque testFotoSaidaEstoque = fotoSaidaEstoqueList.get(fotoSaidaEstoqueList.size() - 1);
        assertThat(testFotoSaidaEstoque.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoSaidaEstoque.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();
        fotoSaidaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoSaidaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoSaidaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();
        fotoSaidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoSaidaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();
        fotoSaidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoSaidaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoSaidaEstoqueWithPatch() throws Exception {
        // Initialize the database
        fotoSaidaEstoqueRepository.saveAndFlush(fotoSaidaEstoque);

        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();

        // Update the fotoSaidaEstoque using partial update
        FotoSaidaEstoque partialUpdatedFotoSaidaEstoque = new FotoSaidaEstoque();
        partialUpdatedFotoSaidaEstoque.setId(fotoSaidaEstoque.getId());

        partialUpdatedFotoSaidaEstoque.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoSaidaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoSaidaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        FotoSaidaEstoque testFotoSaidaEstoque = fotoSaidaEstoqueList.get(fotoSaidaEstoqueList.size() - 1);
        assertThat(testFotoSaidaEstoque.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoSaidaEstoque.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoSaidaEstoqueWithPatch() throws Exception {
        // Initialize the database
        fotoSaidaEstoqueRepository.saveAndFlush(fotoSaidaEstoque);

        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();

        // Update the fotoSaidaEstoque using partial update
        FotoSaidaEstoque partialUpdatedFotoSaidaEstoque = new FotoSaidaEstoque();
        partialUpdatedFotoSaidaEstoque.setId(fotoSaidaEstoque.getId());

        partialUpdatedFotoSaidaEstoque.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoSaidaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoSaidaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        FotoSaidaEstoque testFotoSaidaEstoque = fotoSaidaEstoqueList.get(fotoSaidaEstoqueList.size() - 1);
        assertThat(testFotoSaidaEstoque.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoSaidaEstoque.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();
        fotoSaidaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoSaidaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();
        fotoSaidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = fotoSaidaEstoqueRepository.findAll().size();
        fotoSaidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoSaidaEstoque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoSaidaEstoque in the database
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoSaidaEstoque() throws Exception {
        // Initialize the database
        fotoSaidaEstoqueRepository.saveAndFlush(fotoSaidaEstoque);

        int databaseSizeBeforeDelete = fotoSaidaEstoqueRepository.findAll().size();

        // Delete the fotoSaidaEstoque
        restFotoSaidaEstoqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoSaidaEstoque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoSaidaEstoque> fotoSaidaEstoqueList = fotoSaidaEstoqueRepository.findAll();
        assertThat(fotoSaidaEstoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
