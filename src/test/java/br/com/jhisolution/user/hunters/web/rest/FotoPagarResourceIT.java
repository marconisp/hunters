package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoPagar;
import br.com.jhisolution.user.hunters.repository.FotoPagarRepository;
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
 * Integration tests for the {@link FotoPagarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoPagarResourceIT {

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-pagars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoPagarRepository fotoPagarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoPagarMockMvc;

    private FotoPagar fotoPagar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoPagar createEntity(EntityManager em) {
        FotoPagar fotoPagar = new FotoPagar().conteudo(DEFAULT_CONTEUDO).conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return fotoPagar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoPagar createUpdatedEntity(EntityManager em) {
        FotoPagar fotoPagar = new FotoPagar().conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return fotoPagar;
    }

    @BeforeEach
    public void initTest() {
        fotoPagar = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoPagar() throws Exception {
        int databaseSizeBeforeCreate = fotoPagarRepository.findAll().size();
        // Create the FotoPagar
        restFotoPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPagar)))
            .andExpect(status().isCreated());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeCreate + 1);
        FotoPagar testFotoPagar = fotoPagarList.get(fotoPagarList.size() - 1);
        assertThat(testFotoPagar.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoPagar.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoPagarWithExistingId() throws Exception {
        // Create the FotoPagar with an existing ID
        fotoPagar.setId(1L);

        int databaseSizeBeforeCreate = fotoPagarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPagar)))
            .andExpect(status().isBadRequest());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoPagars() throws Exception {
        // Initialize the database
        fotoPagarRepository.saveAndFlush(fotoPagar);

        // Get all the fotoPagarList
        restFotoPagarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoPagar.getId().intValue())))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }

    @Test
    @Transactional
    void getFotoPagar() throws Exception {
        // Initialize the database
        fotoPagarRepository.saveAndFlush(fotoPagar);

        // Get the fotoPagar
        restFotoPagarMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoPagar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoPagar.getId().intValue()))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoPagar() throws Exception {
        // Get the fotoPagar
        restFotoPagarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoPagar() throws Exception {
        // Initialize the database
        fotoPagarRepository.saveAndFlush(fotoPagar);

        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();

        // Update the fotoPagar
        FotoPagar updatedFotoPagar = fotoPagarRepository.findById(fotoPagar.getId()).get();
        // Disconnect from session so that the updates on updatedFotoPagar are not directly saved in db
        em.detach(updatedFotoPagar);
        updatedFotoPagar.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoPagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoPagar))
            )
            .andExpect(status().isOk());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
        FotoPagar testFotoPagar = fotoPagarList.get(fotoPagarList.size() - 1);
        assertThat(testFotoPagar.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoPagar.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoPagar() throws Exception {
        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();
        fotoPagar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoPagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoPagar() throws Exception {
        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();
        fotoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoPagar() throws Exception {
        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();
        fotoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPagarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPagar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoPagarWithPatch() throws Exception {
        // Initialize the database
        fotoPagarRepository.saveAndFlush(fotoPagar);

        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();

        // Update the fotoPagar using partial update
        FotoPagar partialUpdatedFotoPagar = new FotoPagar();
        partialUpdatedFotoPagar.setId(fotoPagar.getId());

        restFotoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoPagar))
            )
            .andExpect(status().isOk());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
        FotoPagar testFotoPagar = fotoPagarList.get(fotoPagarList.size() - 1);
        assertThat(testFotoPagar.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoPagar.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoPagarWithPatch() throws Exception {
        // Initialize the database
        fotoPagarRepository.saveAndFlush(fotoPagar);

        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();

        // Update the fotoPagar using partial update
        FotoPagar partialUpdatedFotoPagar = new FotoPagar();
        partialUpdatedFotoPagar.setId(fotoPagar.getId());

        partialUpdatedFotoPagar.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoPagar))
            )
            .andExpect(status().isOk());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
        FotoPagar testFotoPagar = fotoPagarList.get(fotoPagarList.size() - 1);
        assertThat(testFotoPagar.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoPagar.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoPagar() throws Exception {
        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();
        fotoPagar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoPagar() throws Exception {
        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();
        fotoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoPagar() throws Exception {
        int databaseSizeBeforeUpdate = fotoPagarRepository.findAll().size();
        fotoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fotoPagar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoPagar in the database
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoPagar() throws Exception {
        // Initialize the database
        fotoPagarRepository.saveAndFlush(fotoPagar);

        int databaseSizeBeforeDelete = fotoPagarRepository.findAll().size();

        // Delete the fotoPagar
        restFotoPagarMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoPagar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoPagar> fotoPagarList = fotoPagarRepository.findAll();
        assertThat(fotoPagarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
