package br.com.jhisolution.user.hunters.web.rest;

import static br.com.jhisolution.user.hunters.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Colaborador;
import br.com.jhisolution.user.hunters.repository.ColaboradorRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ColaboradorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColaboradorResourceIT {

    private static final LocalDate DEFAULT_DATA_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CADASTRO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_ADMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ADMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_RECISAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_RECISAO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SALARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARIO = new BigDecimal(2);

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/colaboradors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColaboradorMockMvc;

    private Colaborador colaborador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaborador createEntity(EntityManager em) {
        Colaborador colaborador = new Colaborador()
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .dataAdmissao(DEFAULT_DATA_ADMISSAO)
            .dataRecisao(DEFAULT_DATA_RECISAO)
            .salario(DEFAULT_SALARIO)
            .ativo(DEFAULT_ATIVO)
            .obs(DEFAULT_OBS);
        return colaborador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaborador createUpdatedEntity(EntityManager em) {
        Colaborador colaborador = new Colaborador()
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .dataRecisao(UPDATED_DATA_RECISAO)
            .salario(UPDATED_SALARIO)
            .ativo(UPDATED_ATIVO)
            .obs(UPDATED_OBS);
        return colaborador;
    }

    @BeforeEach
    public void initTest() {
        colaborador = createEntity(em);
    }

    @Test
    @Transactional
    void createColaborador() throws Exception {
        int databaseSizeBeforeCreate = colaboradorRepository.findAll().size();
        // Create the Colaborador
        restColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaborador)))
            .andExpect(status().isCreated());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeCreate + 1);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testColaborador.getDataAdmissao()).isEqualTo(DEFAULT_DATA_ADMISSAO);
        assertThat(testColaborador.getDataRecisao()).isEqualTo(DEFAULT_DATA_RECISAO);
        assertThat(testColaborador.getSalario()).isEqualByComparingTo(DEFAULT_SALARIO);
        assertThat(testColaborador.getAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testColaborador.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createColaboradorWithExistingId() throws Exception {
        // Create the Colaborador with an existing ID
        colaborador.setId(1L);

        int databaseSizeBeforeCreate = colaboradorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaborador)))
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataCadastroIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradorRepository.findAll().size();
        // set the field null
        colaborador.setDataCadastro(null);

        // Create the Colaborador, which fails.

        restColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaborador)))
            .andExpect(status().isBadRequest());

        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllColaboradors() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colaborador.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].dataAdmissao").value(hasItem(DEFAULT_DATA_ADMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataRecisao").value(hasItem(DEFAULT_DATA_RECISAO.toString())))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(sameNumber(DEFAULT_SALARIO))))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @Test
    @Transactional
    void getColaborador() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get the colaborador
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL_ID, colaborador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colaborador.getId().intValue()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.dataAdmissao").value(DEFAULT_DATA_ADMISSAO.toString()))
            .andExpect(jsonPath("$.dataRecisao").value(DEFAULT_DATA_RECISAO.toString()))
            .andExpect(jsonPath("$.salario").value(sameNumber(DEFAULT_SALARIO)))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingColaborador() throws Exception {
        // Get the colaborador
        restColaboradorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewColaborador() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();

        // Update the colaborador
        Colaborador updatedColaborador = colaboradorRepository.findById(colaborador.getId()).get();
        // Disconnect from session so that the updates on updatedColaborador are not directly saved in db
        em.detach(updatedColaborador);
        updatedColaborador
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .dataRecisao(UPDATED_DATA_RECISAO)
            .salario(UPDATED_SALARIO)
            .ativo(UPDATED_ATIVO)
            .obs(UPDATED_OBS);

        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedColaborador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedColaborador))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testColaborador.getDataAdmissao()).isEqualTo(UPDATED_DATA_ADMISSAO);
        assertThat(testColaborador.getDataRecisao()).isEqualTo(UPDATED_DATA_RECISAO);
        assertThat(testColaborador.getSalario()).isEqualByComparingTo(UPDATED_SALARIO);
        assertThat(testColaborador.getAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testColaborador.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colaborador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaborador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateColaboradorWithPatch() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();

        // Update the colaborador using partial update
        Colaborador partialUpdatedColaborador = new Colaborador();
        partialUpdatedColaborador.setId(colaborador.getId());

        partialUpdatedColaborador
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .dataRecisao(UPDATED_DATA_RECISAO)
            .obs(UPDATED_OBS);

        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColaborador))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testColaborador.getDataAdmissao()).isEqualTo(UPDATED_DATA_ADMISSAO);
        assertThat(testColaborador.getDataRecisao()).isEqualTo(UPDATED_DATA_RECISAO);
        assertThat(testColaborador.getSalario()).isEqualByComparingTo(DEFAULT_SALARIO);
        assertThat(testColaborador.getAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testColaborador.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdateColaboradorWithPatch() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();

        // Update the colaborador using partial update
        Colaborador partialUpdatedColaborador = new Colaborador();
        partialUpdatedColaborador.setId(colaborador.getId());

        partialUpdatedColaborador
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .dataRecisao(UPDATED_DATA_RECISAO)
            .salario(UPDATED_SALARIO)
            .ativo(UPDATED_ATIVO)
            .obs(UPDATED_OBS);

        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColaborador))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testColaborador.getDataAdmissao()).isEqualTo(UPDATED_DATA_ADMISSAO);
        assertThat(testColaborador.getDataRecisao()).isEqualTo(UPDATED_DATA_RECISAO);
        assertThat(testColaborador.getSalario()).isEqualByComparingTo(UPDATED_SALARIO);
        assertThat(testColaborador.getAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testColaborador.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, colaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(colaborador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteColaborador() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeDelete = colaboradorRepository.findAll().size();

        // Delete the colaborador
        restColaboradorMockMvc
            .perform(delete(ENTITY_API_URL_ID, colaborador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
