package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.PeriodoDuracao;
import br.com.jhisolution.user.hunters.repository.PeriodoDuracaoRepository;
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
 * Integration tests for the {@link PeriodoDuracaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PeriodoDuracaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HORA_INICIO = "AAAAA";
    private static final String UPDATED_HORA_INICIO = "BBBBB";

    private static final String DEFAULT_HORA_FIM = "AAAAA";
    private static final String UPDATED_HORA_FIM = "BBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/periodo-duracaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PeriodoDuracaoRepository periodoDuracaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodoDuracaoMockMvc;

    private PeriodoDuracao periodoDuracao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoDuracao createEntity(EntityManager em) {
        PeriodoDuracao periodoDuracao = new PeriodoDuracao()
            .nome(DEFAULT_NOME)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFim(DEFAULT_HORA_FIM)
            .obs(DEFAULT_OBS);
        return periodoDuracao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoDuracao createUpdatedEntity(EntityManager em) {
        PeriodoDuracao periodoDuracao = new PeriodoDuracao()
            .nome(UPDATED_NOME)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .obs(UPDATED_OBS);
        return periodoDuracao;
    }

    @BeforeEach
    public void initTest() {
        periodoDuracao = createEntity(em);
    }

    @Test
    @Transactional
    void createPeriodoDuracao() throws Exception {
        int databaseSizeBeforeCreate = periodoDuracaoRepository.findAll().size();
        // Create the PeriodoDuracao
        restPeriodoDuracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isCreated());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodoDuracao testPeriodoDuracao = periodoDuracaoList.get(periodoDuracaoList.size() - 1);
        assertThat(testPeriodoDuracao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPeriodoDuracao.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testPeriodoDuracao.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testPeriodoDuracao.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testPeriodoDuracao.getHoraFim()).isEqualTo(DEFAULT_HORA_FIM);
        assertThat(testPeriodoDuracao.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createPeriodoDuracaoWithExistingId() throws Exception {
        // Create the PeriodoDuracao with an existing ID
        periodoDuracao.setId(1L);

        int databaseSizeBeforeCreate = periodoDuracaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoDuracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoDuracaoRepository.findAll().size();
        // set the field null
        periodoDuracao.setNome(null);

        // Create the PeriodoDuracao, which fails.

        restPeriodoDuracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoDuracaoRepository.findAll().size();
        // set the field null
        periodoDuracao.setDataInicio(null);

        // Create the PeriodoDuracao, which fails.

        restPeriodoDuracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodoDuracaoRepository.findAll().size();
        // set the field null
        periodoDuracao.setDataFim(null);

        // Create the PeriodoDuracao, which fails.

        restPeriodoDuracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeriodoDuracaos() throws Exception {
        // Initialize the database
        periodoDuracaoRepository.saveAndFlush(periodoDuracao);

        // Get all the periodoDuracaoList
        restPeriodoDuracaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoDuracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO)))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @Test
    @Transactional
    void getPeriodoDuracao() throws Exception {
        // Initialize the database
        periodoDuracaoRepository.saveAndFlush(periodoDuracao);

        // Get the periodoDuracao
        restPeriodoDuracaoMockMvc
            .perform(get(ENTITY_API_URL_ID, periodoDuracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodoDuracao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO))
            .andExpect(jsonPath("$.horaFim").value(DEFAULT_HORA_FIM))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingPeriodoDuracao() throws Exception {
        // Get the periodoDuracao
        restPeriodoDuracaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPeriodoDuracao() throws Exception {
        // Initialize the database
        periodoDuracaoRepository.saveAndFlush(periodoDuracao);

        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();

        // Update the periodoDuracao
        PeriodoDuracao updatedPeriodoDuracao = periodoDuracaoRepository.findById(periodoDuracao.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodoDuracao are not directly saved in db
        em.detach(updatedPeriodoDuracao);
        updatedPeriodoDuracao
            .nome(UPDATED_NOME)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .obs(UPDATED_OBS);

        restPeriodoDuracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPeriodoDuracao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPeriodoDuracao))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
        PeriodoDuracao testPeriodoDuracao = periodoDuracaoList.get(periodoDuracaoList.size() - 1);
        assertThat(testPeriodoDuracao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPeriodoDuracao.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testPeriodoDuracao.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testPeriodoDuracao.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testPeriodoDuracao.getHoraFim()).isEqualTo(UPDATED_HORA_FIM);
        assertThat(testPeriodoDuracao.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingPeriodoDuracao() throws Exception {
        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();
        periodoDuracao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoDuracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoDuracao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriodoDuracao() throws Exception {
        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();
        periodoDuracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoDuracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriodoDuracao() throws Exception {
        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();
        periodoDuracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoDuracaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDuracao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodoDuracaoWithPatch() throws Exception {
        // Initialize the database
        periodoDuracaoRepository.saveAndFlush(periodoDuracao);

        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();

        // Update the periodoDuracao using partial update
        PeriodoDuracao partialUpdatedPeriodoDuracao = new PeriodoDuracao();
        partialUpdatedPeriodoDuracao.setId(periodoDuracao.getId());

        partialUpdatedPeriodoDuracao
            .nome(UPDATED_NOME)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .obs(UPDATED_OBS);

        restPeriodoDuracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoDuracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodoDuracao))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
        PeriodoDuracao testPeriodoDuracao = periodoDuracaoList.get(periodoDuracaoList.size() - 1);
        assertThat(testPeriodoDuracao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPeriodoDuracao.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testPeriodoDuracao.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testPeriodoDuracao.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testPeriodoDuracao.getHoraFim()).isEqualTo(UPDATED_HORA_FIM);
        assertThat(testPeriodoDuracao.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdatePeriodoDuracaoWithPatch() throws Exception {
        // Initialize the database
        periodoDuracaoRepository.saveAndFlush(periodoDuracao);

        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();

        // Update the periodoDuracao using partial update
        PeriodoDuracao partialUpdatedPeriodoDuracao = new PeriodoDuracao();
        partialUpdatedPeriodoDuracao.setId(periodoDuracao.getId());

        partialUpdatedPeriodoDuracao
            .nome(UPDATED_NOME)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .obs(UPDATED_OBS);

        restPeriodoDuracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoDuracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodoDuracao))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
        PeriodoDuracao testPeriodoDuracao = periodoDuracaoList.get(periodoDuracaoList.size() - 1);
        assertThat(testPeriodoDuracao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPeriodoDuracao.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testPeriodoDuracao.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testPeriodoDuracao.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testPeriodoDuracao.getHoraFim()).isEqualTo(UPDATED_HORA_FIM);
        assertThat(testPeriodoDuracao.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingPeriodoDuracao() throws Exception {
        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();
        periodoDuracao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoDuracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodoDuracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriodoDuracao() throws Exception {
        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();
        periodoDuracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoDuracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriodoDuracao() throws Exception {
        int databaseSizeBeforeUpdate = periodoDuracaoRepository.findAll().size();
        periodoDuracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoDuracaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(periodoDuracao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoDuracao in the database
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriodoDuracao() throws Exception {
        // Initialize the database
        periodoDuracaoRepository.saveAndFlush(periodoDuracao);

        int databaseSizeBeforeDelete = periodoDuracaoRepository.findAll().size();

        // Delete the periodoDuracao
        restPeriodoDuracaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, periodoDuracao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PeriodoDuracao> periodoDuracaoList = periodoDuracaoRepository.findAll();
        assertThat(periodoDuracaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
