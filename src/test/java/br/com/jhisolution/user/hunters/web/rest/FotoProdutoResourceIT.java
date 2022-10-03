package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoProduto;
import br.com.jhisolution.user.hunters.repository.FotoProdutoRepository;
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
 * Integration tests for the {@link FotoProdutoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoProdutoResourceIT {

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-produtos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoProdutoRepository fotoProdutoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoProdutoMockMvc;

    private FotoProduto fotoProduto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoProduto createEntity(EntityManager em) {
        FotoProduto fotoProduto = new FotoProduto().conteudo(DEFAULT_CONTEUDO).conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return fotoProduto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoProduto createUpdatedEntity(EntityManager em) {
        FotoProduto fotoProduto = new FotoProduto().conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return fotoProduto;
    }

    @BeforeEach
    public void initTest() {
        fotoProduto = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoProduto() throws Exception {
        int databaseSizeBeforeCreate = fotoProdutoRepository.findAll().size();
        // Create the FotoProduto
        restFotoProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoProduto)))
            .andExpect(status().isCreated());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeCreate + 1);
        FotoProduto testFotoProduto = fotoProdutoList.get(fotoProdutoList.size() - 1);
        assertThat(testFotoProduto.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoProduto.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoProdutoWithExistingId() throws Exception {
        // Create the FotoProduto with an existing ID
        fotoProduto.setId(1L);

        int databaseSizeBeforeCreate = fotoProdutoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoProdutoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoProduto)))
            .andExpect(status().isBadRequest());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoProdutos() throws Exception {
        // Initialize the database
        fotoProdutoRepository.saveAndFlush(fotoProduto);

        // Get all the fotoProdutoList
        restFotoProdutoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }

    @Test
    @Transactional
    void getFotoProduto() throws Exception {
        // Initialize the database
        fotoProdutoRepository.saveAndFlush(fotoProduto);

        // Get the fotoProduto
        restFotoProdutoMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoProduto.getId().intValue()))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoProduto() throws Exception {
        // Get the fotoProduto
        restFotoProdutoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoProduto() throws Exception {
        // Initialize the database
        fotoProdutoRepository.saveAndFlush(fotoProduto);

        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();

        // Update the fotoProduto
        FotoProduto updatedFotoProduto = fotoProdutoRepository.findById(fotoProduto.getId()).get();
        // Disconnect from session so that the updates on updatedFotoProduto are not directly saved in db
        em.detach(updatedFotoProduto);
        updatedFotoProduto.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoProduto))
            )
            .andExpect(status().isOk());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
        FotoProduto testFotoProduto = fotoProdutoList.get(fotoProdutoList.size() - 1);
        assertThat(testFotoProduto.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoProduto.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoProduto() throws Exception {
        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();
        fotoProduto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoProduto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoProduto() throws Exception {
        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();
        fotoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoProdutoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoProduto() throws Exception {
        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();
        fotoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoProdutoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoProduto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoProdutoWithPatch() throws Exception {
        // Initialize the database
        fotoProdutoRepository.saveAndFlush(fotoProduto);

        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();

        // Update the fotoProduto using partial update
        FotoProduto partialUpdatedFotoProduto = new FotoProduto();
        partialUpdatedFotoProduto.setId(fotoProduto.getId());

        partialUpdatedFotoProduto.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoProduto))
            )
            .andExpect(status().isOk());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
        FotoProduto testFotoProduto = fotoProdutoList.get(fotoProdutoList.size() - 1);
        assertThat(testFotoProduto.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoProduto.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoProdutoWithPatch() throws Exception {
        // Initialize the database
        fotoProdutoRepository.saveAndFlush(fotoProduto);

        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();

        // Update the fotoProduto using partial update
        FotoProduto partialUpdatedFotoProduto = new FotoProduto();
        partialUpdatedFotoProduto.setId(fotoProduto.getId());

        partialUpdatedFotoProduto.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoProduto))
            )
            .andExpect(status().isOk());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
        FotoProduto testFotoProduto = fotoProdutoList.get(fotoProdutoList.size() - 1);
        assertThat(testFotoProduto.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoProduto.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoProduto() throws Exception {
        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();
        fotoProduto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoProduto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoProduto() throws Exception {
        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();
        fotoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoProduto))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoProduto() throws Exception {
        int databaseSizeBeforeUpdate = fotoProdutoRepository.findAll().size();
        fotoProduto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoProdutoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fotoProduto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoProduto in the database
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoProduto() throws Exception {
        // Initialize the database
        fotoProdutoRepository.saveAndFlush(fotoProduto);

        int databaseSizeBeforeDelete = fotoProdutoRepository.findAll().size();

        // Delete the fotoProduto
        restFotoProdutoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoProduto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoProduto> fotoProdutoList = fotoProdutoRepository.findAll();
        assertThat(fotoProdutoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
