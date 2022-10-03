package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.DiaSemana;
import br.com.jhisolution.user.hunters.repository.DiaSemanaRepository;
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
 * Integration tests for the {@link DiaSemanaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiaSemanaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dia-semanas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiaSemanaRepository diaSemanaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiaSemanaMockMvc;

    private DiaSemana diaSemana;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaSemana createEntity(EntityManager em) {
        DiaSemana diaSemana = new DiaSemana().nome(DEFAULT_NOME).obs(DEFAULT_OBS);
        return diaSemana;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaSemana createUpdatedEntity(EntityManager em) {
        DiaSemana diaSemana = new DiaSemana().nome(UPDATED_NOME).obs(UPDATED_OBS);
        return diaSemana;
    }

    @BeforeEach
    public void initTest() {
        diaSemana = createEntity(em);
    }

    @Test
    @Transactional
    void createDiaSemana() throws Exception {
        int databaseSizeBeforeCreate = diaSemanaRepository.findAll().size();
        // Create the DiaSemana
        restDiaSemanaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaSemana)))
            .andExpect(status().isCreated());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeCreate + 1);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDiaSemana.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createDiaSemanaWithExistingId() throws Exception {
        // Create the DiaSemana with an existing ID
        diaSemana.setId(1L);

        int databaseSizeBeforeCreate = diaSemanaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaSemanaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaSemana)))
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diaSemanaRepository.findAll().size();
        // set the field null
        diaSemana.setNome(null);

        // Create the DiaSemana, which fails.

        restDiaSemanaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaSemana)))
            .andExpect(status().isBadRequest());

        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDiaSemanas() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList
        restDiaSemanaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaSemana.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @Test
    @Transactional
    void getDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get the diaSemana
        restDiaSemanaMockMvc
            .perform(get(ENTITY_API_URL_ID, diaSemana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diaSemana.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingDiaSemana() throws Exception {
        // Get the diaSemana
        restDiaSemanaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();

        // Update the diaSemana
        DiaSemana updatedDiaSemana = diaSemanaRepository.findById(diaSemana.getId()).get();
        // Disconnect from session so that the updates on updatedDiaSemana are not directly saved in db
        em.detach(updatedDiaSemana);
        updatedDiaSemana.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restDiaSemanaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiaSemana.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDiaSemana))
            )
            .andExpect(status().isOk());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDiaSemana.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();
        diaSemana.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diaSemana.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diaSemana))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();
        diaSemana.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diaSemana))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();
        diaSemana.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaSemana)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiaSemanaWithPatch() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();

        // Update the diaSemana using partial update
        DiaSemana partialUpdatedDiaSemana = new DiaSemana();
        partialUpdatedDiaSemana.setId(diaSemana.getId());

        partialUpdatedDiaSemana.nome(UPDATED_NOME);

        restDiaSemanaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiaSemana.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaSemana))
            )
            .andExpect(status().isOk());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDiaSemana.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void fullUpdateDiaSemanaWithPatch() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();

        // Update the diaSemana using partial update
        DiaSemana partialUpdatedDiaSemana = new DiaSemana();
        partialUpdatedDiaSemana.setId(diaSemana.getId());

        partialUpdatedDiaSemana.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restDiaSemanaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiaSemana.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaSemana))
            )
            .andExpect(status().isOk());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDiaSemana.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();
        diaSemana.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diaSemana.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diaSemana))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();
        diaSemana.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diaSemana))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();
        diaSemana.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diaSemana))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeDelete = diaSemanaRepository.findAll().size();

        // Delete the diaSemana
        restDiaSemanaMockMvc
            .perform(delete(ENTITY_API_URL_ID, diaSemana.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
