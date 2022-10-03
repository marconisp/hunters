package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoReceber;
import br.com.jhisolution.user.hunters.repository.FotoReceberRepository;
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
 * Integration tests for the {@link FotoReceberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoReceberResourceIT {

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-recebers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoReceberRepository fotoReceberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoReceberMockMvc;

    private FotoReceber fotoReceber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoReceber createEntity(EntityManager em) {
        FotoReceber fotoReceber = new FotoReceber().conteudo(DEFAULT_CONTEUDO).conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return fotoReceber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoReceber createUpdatedEntity(EntityManager em) {
        FotoReceber fotoReceber = new FotoReceber().conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return fotoReceber;
    }

    @BeforeEach
    public void initTest() {
        fotoReceber = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoReceber() throws Exception {
        int databaseSizeBeforeCreate = fotoReceberRepository.findAll().size();
        // Create the FotoReceber
        restFotoReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoReceber)))
            .andExpect(status().isCreated());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeCreate + 1);
        FotoReceber testFotoReceber = fotoReceberList.get(fotoReceberList.size() - 1);
        assertThat(testFotoReceber.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoReceber.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoReceberWithExistingId() throws Exception {
        // Create the FotoReceber with an existing ID
        fotoReceber.setId(1L);

        int databaseSizeBeforeCreate = fotoReceberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoReceber)))
            .andExpect(status().isBadRequest());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoRecebers() throws Exception {
        // Initialize the database
        fotoReceberRepository.saveAndFlush(fotoReceber);

        // Get all the fotoReceberList
        restFotoReceberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoReceber.getId().intValue())))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }

    @Test
    @Transactional
    void getFotoReceber() throws Exception {
        // Initialize the database
        fotoReceberRepository.saveAndFlush(fotoReceber);

        // Get the fotoReceber
        restFotoReceberMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoReceber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoReceber.getId().intValue()))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoReceber() throws Exception {
        // Get the fotoReceber
        restFotoReceberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoReceber() throws Exception {
        // Initialize the database
        fotoReceberRepository.saveAndFlush(fotoReceber);

        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();

        // Update the fotoReceber
        FotoReceber updatedFotoReceber = fotoReceberRepository.findById(fotoReceber.getId()).get();
        // Disconnect from session so that the updates on updatedFotoReceber are not directly saved in db
        em.detach(updatedFotoReceber);
        updatedFotoReceber.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoReceber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoReceber))
            )
            .andExpect(status().isOk());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
        FotoReceber testFotoReceber = fotoReceberList.get(fotoReceberList.size() - 1);
        assertThat(testFotoReceber.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoReceber.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoReceber() throws Exception {
        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();
        fotoReceber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoReceber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoReceber() throws Exception {
        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();
        fotoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoReceber() throws Exception {
        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();
        fotoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoReceberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoReceber)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoReceberWithPatch() throws Exception {
        // Initialize the database
        fotoReceberRepository.saveAndFlush(fotoReceber);

        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();

        // Update the fotoReceber using partial update
        FotoReceber partialUpdatedFotoReceber = new FotoReceber();
        partialUpdatedFotoReceber.setId(fotoReceber.getId());

        partialUpdatedFotoReceber.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoReceber))
            )
            .andExpect(status().isOk());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
        FotoReceber testFotoReceber = fotoReceberList.get(fotoReceberList.size() - 1);
        assertThat(testFotoReceber.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoReceber.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoReceberWithPatch() throws Exception {
        // Initialize the database
        fotoReceberRepository.saveAndFlush(fotoReceber);

        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();

        // Update the fotoReceber using partial update
        FotoReceber partialUpdatedFotoReceber = new FotoReceber();
        partialUpdatedFotoReceber.setId(fotoReceber.getId());

        partialUpdatedFotoReceber.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoReceber))
            )
            .andExpect(status().isOk());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
        FotoReceber testFotoReceber = fotoReceberList.get(fotoReceberList.size() - 1);
        assertThat(testFotoReceber.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoReceber.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoReceber() throws Exception {
        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();
        fotoReceber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoReceber() throws Exception {
        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();
        fotoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoReceber() throws Exception {
        int databaseSizeBeforeUpdate = fotoReceberRepository.findAll().size();
        fotoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fotoReceber))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoReceber in the database
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoReceber() throws Exception {
        // Initialize the database
        fotoReceberRepository.saveAndFlush(fotoReceber);

        int databaseSizeBeforeDelete = fotoReceberRepository.findAll().size();

        // Delete the fotoReceber
        restFotoReceberMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoReceber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoReceber> fotoReceberList = fotoReceberRepository.findAll();
        assertThat(fotoReceberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
