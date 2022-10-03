package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.ExameMedico;
import br.com.jhisolution.user.hunters.repository.ExameMedicoRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ExameMedicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExameMedicoResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOME_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_CRM_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_CRM_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_RESUMO = "AAAAAAAAAA";
    private static final String UPDATED_RESUMO = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/exame-medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExameMedicoRepository exameMedicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExameMedicoMockMvc;

    private ExameMedico exameMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExameMedico createEntity(EntityManager em) {
        ExameMedico exameMedico = new ExameMedico()
            .data(DEFAULT_DATA)
            .nomeMedico(DEFAULT_NOME_MEDICO)
            .crmMedico(DEFAULT_CRM_MEDICO)
            .resumo(DEFAULT_RESUMO)
            .obs(DEFAULT_OBS);
        return exameMedico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExameMedico createUpdatedEntity(EntityManager em) {
        ExameMedico exameMedico = new ExameMedico()
            .data(UPDATED_DATA)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .crmMedico(UPDATED_CRM_MEDICO)
            .resumo(UPDATED_RESUMO)
            .obs(UPDATED_OBS);
        return exameMedico;
    }

    @BeforeEach
    public void initTest() {
        exameMedico = createEntity(em);
    }

    @Test
    @Transactional
    void createExameMedico() throws Exception {
        int databaseSizeBeforeCreate = exameMedicoRepository.findAll().size();
        // Create the ExameMedico
        restExameMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exameMedico)))
            .andExpect(status().isCreated());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeCreate + 1);
        ExameMedico testExameMedico = exameMedicoList.get(exameMedicoList.size() - 1);
        assertThat(testExameMedico.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testExameMedico.getNomeMedico()).isEqualTo(DEFAULT_NOME_MEDICO);
        assertThat(testExameMedico.getCrmMedico()).isEqualTo(DEFAULT_CRM_MEDICO);
        assertThat(testExameMedico.getResumo()).isEqualTo(DEFAULT_RESUMO);
        assertThat(testExameMedico.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createExameMedicoWithExistingId() throws Exception {
        // Create the ExameMedico with an existing ID
        exameMedico.setId(1L);

        int databaseSizeBeforeCreate = exameMedicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExameMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exameMedico)))
            .andExpect(status().isBadRequest());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameMedicoRepository.findAll().size();
        // set the field null
        exameMedico.setData(null);

        // Create the ExameMedico, which fails.

        restExameMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exameMedico)))
            .andExpect(status().isBadRequest());

        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExameMedicos() throws Exception {
        // Initialize the database
        exameMedicoRepository.saveAndFlush(exameMedico);

        // Get all the exameMedicoList
        restExameMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exameMedico.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].nomeMedico").value(hasItem(DEFAULT_NOME_MEDICO)))
            .andExpect(jsonPath("$.[*].crmMedico").value(hasItem(DEFAULT_CRM_MEDICO)))
            .andExpect(jsonPath("$.[*].resumo").value(hasItem(DEFAULT_RESUMO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @Test
    @Transactional
    void getExameMedico() throws Exception {
        // Initialize the database
        exameMedicoRepository.saveAndFlush(exameMedico);

        // Get the exameMedico
        restExameMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, exameMedico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exameMedico.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.nomeMedico").value(DEFAULT_NOME_MEDICO))
            .andExpect(jsonPath("$.crmMedico").value(DEFAULT_CRM_MEDICO))
            .andExpect(jsonPath("$.resumo").value(DEFAULT_RESUMO))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingExameMedico() throws Exception {
        // Get the exameMedico
        restExameMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExameMedico() throws Exception {
        // Initialize the database
        exameMedicoRepository.saveAndFlush(exameMedico);

        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();

        // Update the exameMedico
        ExameMedico updatedExameMedico = exameMedicoRepository.findById(exameMedico.getId()).get();
        // Disconnect from session so that the updates on updatedExameMedico are not directly saved in db
        em.detach(updatedExameMedico);
        updatedExameMedico
            .data(UPDATED_DATA)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .crmMedico(UPDATED_CRM_MEDICO)
            .resumo(UPDATED_RESUMO)
            .obs(UPDATED_OBS);

        restExameMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExameMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedExameMedico))
            )
            .andExpect(status().isOk());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
        ExameMedico testExameMedico = exameMedicoList.get(exameMedicoList.size() - 1);
        assertThat(testExameMedico.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testExameMedico.getNomeMedico()).isEqualTo(UPDATED_NOME_MEDICO);
        assertThat(testExameMedico.getCrmMedico()).isEqualTo(UPDATED_CRM_MEDICO);
        assertThat(testExameMedico.getResumo()).isEqualTo(UPDATED_RESUMO);
        assertThat(testExameMedico.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();
        exameMedico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExameMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exameMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();
        exameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();
        exameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMedicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exameMedico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExameMedicoWithPatch() throws Exception {
        // Initialize the database
        exameMedicoRepository.saveAndFlush(exameMedico);

        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();

        // Update the exameMedico using partial update
        ExameMedico partialUpdatedExameMedico = new ExameMedico();
        partialUpdatedExameMedico.setId(exameMedico.getId());

        partialUpdatedExameMedico.nomeMedico(UPDATED_NOME_MEDICO).crmMedico(UPDATED_CRM_MEDICO).resumo(UPDATED_RESUMO).obs(UPDATED_OBS);

        restExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExameMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExameMedico))
            )
            .andExpect(status().isOk());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
        ExameMedico testExameMedico = exameMedicoList.get(exameMedicoList.size() - 1);
        assertThat(testExameMedico.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testExameMedico.getNomeMedico()).isEqualTo(UPDATED_NOME_MEDICO);
        assertThat(testExameMedico.getCrmMedico()).isEqualTo(UPDATED_CRM_MEDICO);
        assertThat(testExameMedico.getResumo()).isEqualTo(UPDATED_RESUMO);
        assertThat(testExameMedico.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdateExameMedicoWithPatch() throws Exception {
        // Initialize the database
        exameMedicoRepository.saveAndFlush(exameMedico);

        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();

        // Update the exameMedico using partial update
        ExameMedico partialUpdatedExameMedico = new ExameMedico();
        partialUpdatedExameMedico.setId(exameMedico.getId());

        partialUpdatedExameMedico
            .data(UPDATED_DATA)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .crmMedico(UPDATED_CRM_MEDICO)
            .resumo(UPDATED_RESUMO)
            .obs(UPDATED_OBS);

        restExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExameMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExameMedico))
            )
            .andExpect(status().isOk());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
        ExameMedico testExameMedico = exameMedicoList.get(exameMedicoList.size() - 1);
        assertThat(testExameMedico.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testExameMedico.getNomeMedico()).isEqualTo(UPDATED_NOME_MEDICO);
        assertThat(testExameMedico.getCrmMedico()).isEqualTo(UPDATED_CRM_MEDICO);
        assertThat(testExameMedico.getResumo()).isEqualTo(UPDATED_RESUMO);
        assertThat(testExameMedico.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();
        exameMedico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, exameMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();
        exameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exameMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExameMedico() throws Exception {
        int databaseSizeBeforeUpdate = exameMedicoRepository.findAll().size();
        exameMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExameMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(exameMedico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExameMedico in the database
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExameMedico() throws Exception {
        // Initialize the database
        exameMedicoRepository.saveAndFlush(exameMedico);

        int databaseSizeBeforeDelete = exameMedicoRepository.findAll().size();

        // Delete the exameMedico
        restExameMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, exameMedico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExameMedico> exameMedicoList = exameMedicoRepository.findAll();
        assertThat(exameMedicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
