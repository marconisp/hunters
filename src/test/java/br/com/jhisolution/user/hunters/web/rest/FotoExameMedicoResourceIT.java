package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoExameMedico;
import br.com.jhisolution.user.hunters.repository.FotoExameMedicoRepository;
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
 * Integration tests for the {@link FotoExameMedicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoExameMedicoResourceIT {

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-exame-medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoExameMedicoRepository fotoExameMedicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoExameMedicoMockMvc;

    private FotoExameMedico fotoExameMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoExameMedico createEntity(EntityManager em) {
        FotoExameMedico fotoExameMedico = new FotoExameMedico().foto(DEFAULT_FOTO).fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return fotoExameMedico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoExameMedico createUpdatedEntity(EntityManager em) {
        FotoExameMedico fotoExameMedico = new FotoExameMedico().foto(UPDATED_FOTO).fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        return fotoExameMedico;
    }

    @BeforeEach
    public void initTest() {
        fotoExameMedico = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoExameMedico() throws Exception {
        int databaseSizeBeforeCreate = fotoExameMedicoRepository.findAll().size();
        // Create the FotoExameMedico
        restFotoExameMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isCreated());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeCreate + 1);
        FotoExameMedico testFotoExameMedico = fotoExameMedicoList.get(fotoExameMedicoList.size() - 1);
        assertThat(testFotoExameMedico.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testFotoExameMedico.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoExameMedicoWithExistingId() throws Exception {
        // Create the FotoExameMedico with an existing ID
        fotoExameMedico.setId(1L);

        int databaseSizeBeforeCreate = fotoExameMedicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoExameMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoExameMedicos() throws Exception {
        // Initialize the database
        fotoExameMedicoRepository.saveAndFlush(fotoExameMedico);

        // Get all the fotoExameMedicoList
        restFotoExameMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoExameMedico.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    void getFotoExameMedico() throws Exception {
        // Initialize the database
        fotoExameMedicoRepository.saveAndFlush(fotoExameMedico);

        // Get the fotoExameMedico
        restFotoExameMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoExameMedico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoExameMedico.getId().intValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoExameMedico() throws Exception {
        // Get the fotoExameMedico
        restFotoExameMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoExameMedico() throws Exception {
        // Initialize the database
        fotoExameMedicoRepository.saveAndFlush(fotoExameMedico);

        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();

        // Update the fotoExameMedico
        FotoExameMedico updatedFotoExameMedico = fotoExameMedicoRepository.findById(fotoExameMedico.getId()).get();
        // Disconnect from session so that the updates on updatedFotoExameMedico are not directly saved in db
        em.detach(updatedFotoExameMedico);
        updatedFotoExameMedico.foto(UPDATED_FOTO).fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restFotoExameMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoExameMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoExameMedico))
            )
            .andExpect(status().isOk());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
        FotoExameMedico testFotoExameMedico = fotoExameMedicoList.get(fotoExameMedicoList.size() - 1);
        assertThat(testFotoExameMedico.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testFotoExameMedico.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();
        fotoExameMedico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoExameMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoExameMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();
        fotoExameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoExameMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();
        fotoExameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoExameMedicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoExameMedicoWithPatch() throws Exception {
        // Initialize the database
        fotoExameMedicoRepository.saveAndFlush(fotoExameMedico);

        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();

        // Update the fotoExameMedico using partial update
        FotoExameMedico partialUpdatedFotoExameMedico = new FotoExameMedico();
        partialUpdatedFotoExameMedico.setId(fotoExameMedico.getId());

        restFotoExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoExameMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoExameMedico))
            )
            .andExpect(status().isOk());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
        FotoExameMedico testFotoExameMedico = fotoExameMedicoList.get(fotoExameMedicoList.size() - 1);
        assertThat(testFotoExameMedico.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testFotoExameMedico.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoExameMedicoWithPatch() throws Exception {
        // Initialize the database
        fotoExameMedicoRepository.saveAndFlush(fotoExameMedico);

        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();

        // Update the fotoExameMedico using partial update
        FotoExameMedico partialUpdatedFotoExameMedico = new FotoExameMedico();
        partialUpdatedFotoExameMedico.setId(fotoExameMedico.getId());

        partialUpdatedFotoExameMedico.foto(UPDATED_FOTO).fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restFotoExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoExameMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoExameMedico))
            )
            .andExpect(status().isOk());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
        FotoExameMedico testFotoExameMedico = fotoExameMedicoList.get(fotoExameMedicoList.size() - 1);
        assertThat(testFotoExameMedico.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testFotoExameMedico.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();
        fotoExameMedico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoExameMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();
        fotoExameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = fotoExameMedicoRepository.findAll().size();
        fotoExameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoExameMedico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoExameMedico in the database
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoExameMedico() throws Exception {
        // Initialize the database
        fotoExameMedicoRepository.saveAndFlush(fotoExameMedico);

        int databaseSizeBeforeDelete = fotoExameMedicoRepository.findAll().size();

        // Delete the fotoExameMedico
        restFotoExameMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoExameMedico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoExameMedico> fotoExameMedicoList = fotoExameMedicoRepository.findAll();
        assertThat(fotoExameMedicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
