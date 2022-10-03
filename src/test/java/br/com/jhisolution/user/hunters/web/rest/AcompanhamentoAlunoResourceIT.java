package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.AcompanhamentoAluno;
import br.com.jhisolution.user.hunters.domain.enumeration.Ensino;
import br.com.jhisolution.user.hunters.repository.AcompanhamentoAlunoRepository;
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
 * Integration tests for the {@link AcompanhamentoAlunoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcompanhamentoAlunoResourceIT {

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    private static final Ensino DEFAULT_ENSINO = Ensino.FUNDAMENTAL1;
    private static final Ensino UPDATED_ENSINO = Ensino.FUNDAMENTAL2;

    private static final Integer DEFAULT_BIMESTRE = 1;
    private static final Integer UPDATED_BIMESTRE = 2;

    private static final String ENTITY_API_URL = "/api/acompanhamento-alunos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcompanhamentoAlunoRepository acompanhamentoAlunoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcompanhamentoAlunoMockMvc;

    private AcompanhamentoAluno acompanhamentoAluno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcompanhamentoAluno createEntity(EntityManager em) {
        AcompanhamentoAluno acompanhamentoAluno = new AcompanhamentoAluno()
            .ano(DEFAULT_ANO)
            .ensino(DEFAULT_ENSINO)
            .bimestre(DEFAULT_BIMESTRE);
        return acompanhamentoAluno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcompanhamentoAluno createUpdatedEntity(EntityManager em) {
        AcompanhamentoAluno acompanhamentoAluno = new AcompanhamentoAluno()
            .ano(UPDATED_ANO)
            .ensino(UPDATED_ENSINO)
            .bimestre(UPDATED_BIMESTRE);
        return acompanhamentoAluno;
    }

    @BeforeEach
    public void initTest() {
        acompanhamentoAluno = createEntity(em);
    }

    @Test
    @Transactional
    void createAcompanhamentoAluno() throws Exception {
        int databaseSizeBeforeCreate = acompanhamentoAlunoRepository.findAll().size();
        // Create the AcompanhamentoAluno
        restAcompanhamentoAlunoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isCreated());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeCreate + 1);
        AcompanhamentoAluno testAcompanhamentoAluno = acompanhamentoAlunoList.get(acompanhamentoAlunoList.size() - 1);
        assertThat(testAcompanhamentoAluno.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testAcompanhamentoAluno.getEnsino()).isEqualTo(DEFAULT_ENSINO);
        assertThat(testAcompanhamentoAluno.getBimestre()).isEqualTo(DEFAULT_BIMESTRE);
    }

    @Test
    @Transactional
    void createAcompanhamentoAlunoWithExistingId() throws Exception {
        // Create the AcompanhamentoAluno with an existing ID
        acompanhamentoAluno.setId(1L);

        int databaseSizeBeforeCreate = acompanhamentoAlunoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcompanhamentoAlunoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acompanhamentoAlunoRepository.findAll().size();
        // set the field null
        acompanhamentoAluno.setAno(null);

        // Create the AcompanhamentoAluno, which fails.

        restAcompanhamentoAlunoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnsinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acompanhamentoAlunoRepository.findAll().size();
        // set the field null
        acompanhamentoAluno.setEnsino(null);

        // Create the AcompanhamentoAluno, which fails.

        restAcompanhamentoAlunoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBimestreIsRequired() throws Exception {
        int databaseSizeBeforeTest = acompanhamentoAlunoRepository.findAll().size();
        // set the field null
        acompanhamentoAluno.setBimestre(null);

        // Create the AcompanhamentoAluno, which fails.

        restAcompanhamentoAlunoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAcompanhamentoAlunos() throws Exception {
        // Initialize the database
        acompanhamentoAlunoRepository.saveAndFlush(acompanhamentoAluno);

        // Get all the acompanhamentoAlunoList
        restAcompanhamentoAlunoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acompanhamentoAluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)))
            .andExpect(jsonPath("$.[*].ensino").value(hasItem(DEFAULT_ENSINO.toString())))
            .andExpect(jsonPath("$.[*].bimestre").value(hasItem(DEFAULT_BIMESTRE)));
    }

    @Test
    @Transactional
    void getAcompanhamentoAluno() throws Exception {
        // Initialize the database
        acompanhamentoAlunoRepository.saveAndFlush(acompanhamentoAluno);

        // Get the acompanhamentoAluno
        restAcompanhamentoAlunoMockMvc
            .perform(get(ENTITY_API_URL_ID, acompanhamentoAluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acompanhamentoAluno.getId().intValue()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO))
            .andExpect(jsonPath("$.ensino").value(DEFAULT_ENSINO.toString()))
            .andExpect(jsonPath("$.bimestre").value(DEFAULT_BIMESTRE));
    }

    @Test
    @Transactional
    void getNonExistingAcompanhamentoAluno() throws Exception {
        // Get the acompanhamentoAluno
        restAcompanhamentoAlunoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAcompanhamentoAluno() throws Exception {
        // Initialize the database
        acompanhamentoAlunoRepository.saveAndFlush(acompanhamentoAluno);

        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();

        // Update the acompanhamentoAluno
        AcompanhamentoAluno updatedAcompanhamentoAluno = acompanhamentoAlunoRepository.findById(acompanhamentoAluno.getId()).get();
        // Disconnect from session so that the updates on updatedAcompanhamentoAluno are not directly saved in db
        em.detach(updatedAcompanhamentoAluno);
        updatedAcompanhamentoAluno.ano(UPDATED_ANO).ensino(UPDATED_ENSINO).bimestre(UPDATED_BIMESTRE);

        restAcompanhamentoAlunoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcompanhamentoAluno.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAcompanhamentoAluno))
            )
            .andExpect(status().isOk());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
        AcompanhamentoAluno testAcompanhamentoAluno = acompanhamentoAlunoList.get(acompanhamentoAlunoList.size() - 1);
        assertThat(testAcompanhamentoAluno.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testAcompanhamentoAluno.getEnsino()).isEqualTo(UPDATED_ENSINO);
        assertThat(testAcompanhamentoAluno.getBimestre()).isEqualTo(UPDATED_BIMESTRE);
    }

    @Test
    @Transactional
    void putNonExistingAcompanhamentoAluno() throws Exception {
        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();
        acompanhamentoAluno.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcompanhamentoAlunoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acompanhamentoAluno.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcompanhamentoAluno() throws Exception {
        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();
        acompanhamentoAluno.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcompanhamentoAlunoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcompanhamentoAluno() throws Exception {
        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();
        acompanhamentoAluno.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcompanhamentoAlunoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcompanhamentoAlunoWithPatch() throws Exception {
        // Initialize the database
        acompanhamentoAlunoRepository.saveAndFlush(acompanhamentoAluno);

        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();

        // Update the acompanhamentoAluno using partial update
        AcompanhamentoAluno partialUpdatedAcompanhamentoAluno = new AcompanhamentoAluno();
        partialUpdatedAcompanhamentoAluno.setId(acompanhamentoAluno.getId());

        partialUpdatedAcompanhamentoAluno.ensino(UPDATED_ENSINO);

        restAcompanhamentoAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcompanhamentoAluno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcompanhamentoAluno))
            )
            .andExpect(status().isOk());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
        AcompanhamentoAluno testAcompanhamentoAluno = acompanhamentoAlunoList.get(acompanhamentoAlunoList.size() - 1);
        assertThat(testAcompanhamentoAluno.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testAcompanhamentoAluno.getEnsino()).isEqualTo(UPDATED_ENSINO);
        assertThat(testAcompanhamentoAluno.getBimestre()).isEqualTo(DEFAULT_BIMESTRE);
    }

    @Test
    @Transactional
    void fullUpdateAcompanhamentoAlunoWithPatch() throws Exception {
        // Initialize the database
        acompanhamentoAlunoRepository.saveAndFlush(acompanhamentoAluno);

        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();

        // Update the acompanhamentoAluno using partial update
        AcompanhamentoAluno partialUpdatedAcompanhamentoAluno = new AcompanhamentoAluno();
        partialUpdatedAcompanhamentoAluno.setId(acompanhamentoAluno.getId());

        partialUpdatedAcompanhamentoAluno.ano(UPDATED_ANO).ensino(UPDATED_ENSINO).bimestre(UPDATED_BIMESTRE);

        restAcompanhamentoAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcompanhamentoAluno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcompanhamentoAluno))
            )
            .andExpect(status().isOk());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
        AcompanhamentoAluno testAcompanhamentoAluno = acompanhamentoAlunoList.get(acompanhamentoAlunoList.size() - 1);
        assertThat(testAcompanhamentoAluno.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testAcompanhamentoAluno.getEnsino()).isEqualTo(UPDATED_ENSINO);
        assertThat(testAcompanhamentoAluno.getBimestre()).isEqualTo(UPDATED_BIMESTRE);
    }

    @Test
    @Transactional
    void patchNonExistingAcompanhamentoAluno() throws Exception {
        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();
        acompanhamentoAluno.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcompanhamentoAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, acompanhamentoAluno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcompanhamentoAluno() throws Exception {
        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();
        acompanhamentoAluno.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcompanhamentoAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcompanhamentoAluno() throws Exception {
        int databaseSizeBeforeUpdate = acompanhamentoAlunoRepository.findAll().size();
        acompanhamentoAluno.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcompanhamentoAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acompanhamentoAluno))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcompanhamentoAluno in the database
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcompanhamentoAluno() throws Exception {
        // Initialize the database
        acompanhamentoAlunoRepository.saveAndFlush(acompanhamentoAluno);

        int databaseSizeBeforeDelete = acompanhamentoAlunoRepository.findAll().size();

        // Delete the acompanhamentoAluno
        restAcompanhamentoAlunoMockMvc
            .perform(delete(ENTITY_API_URL_ID, acompanhamentoAluno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcompanhamentoAluno> acompanhamentoAlunoList = acompanhamentoAlunoRepository.findAll();
        assertThat(acompanhamentoAlunoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
