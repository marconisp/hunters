package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Alergia;
import br.com.jhisolution.user.hunters.repository.AlergiaRepository;
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
 * Integration tests for the {@link AlergiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlergiaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SINTOMA = "AAAAAAAAAA";
    private static final String UPDATED_SINTOMA = "BBBBBBBBBB";

    private static final String DEFAULT_PRECAUCOES = "AAAAAAAAAA";
    private static final String UPDATED_PRECAUCOES = "BBBBBBBBBB";

    private static final String DEFAULT_SOCORRO = "AAAAAAAAAA";
    private static final String UPDATED_SOCORRO = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/alergias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlergiaRepository alergiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlergiaMockMvc;

    private Alergia alergia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alergia createEntity(EntityManager em) {
        Alergia alergia = new Alergia()
            .nome(DEFAULT_NOME)
            .sintoma(DEFAULT_SINTOMA)
            .precaucoes(DEFAULT_PRECAUCOES)
            .socorro(DEFAULT_SOCORRO)
            .obs(DEFAULT_OBS);
        return alergia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alergia createUpdatedEntity(EntityManager em) {
        Alergia alergia = new Alergia()
            .nome(UPDATED_NOME)
            .sintoma(UPDATED_SINTOMA)
            .precaucoes(UPDATED_PRECAUCOES)
            .socorro(UPDATED_SOCORRO)
            .obs(UPDATED_OBS);
        return alergia;
    }

    @BeforeEach
    public void initTest() {
        alergia = createEntity(em);
    }

    @Test
    @Transactional
    void createAlergia() throws Exception {
        int databaseSizeBeforeCreate = alergiaRepository.findAll().size();
        // Create the Alergia
        restAlergiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alergia)))
            .andExpect(status().isCreated());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeCreate + 1);
        Alergia testAlergia = alergiaList.get(alergiaList.size() - 1);
        assertThat(testAlergia.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlergia.getSintoma()).isEqualTo(DEFAULT_SINTOMA);
        assertThat(testAlergia.getPrecaucoes()).isEqualTo(DEFAULT_PRECAUCOES);
        assertThat(testAlergia.getSocorro()).isEqualTo(DEFAULT_SOCORRO);
        assertThat(testAlergia.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createAlergiaWithExistingId() throws Exception {
        // Create the Alergia with an existing ID
        alergia.setId(1L);

        int databaseSizeBeforeCreate = alergiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlergiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alergia)))
            .andExpect(status().isBadRequest());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alergiaRepository.findAll().size();
        // set the field null
        alergia.setNome(null);

        // Create the Alergia, which fails.

        restAlergiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alergia)))
            .andExpect(status().isBadRequest());

        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlergias() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        // Get all the alergiaList
        restAlergiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alergia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sintoma").value(hasItem(DEFAULT_SINTOMA)))
            .andExpect(jsonPath("$.[*].precaucoes").value(hasItem(DEFAULT_PRECAUCOES)))
            .andExpect(jsonPath("$.[*].socorro").value(hasItem(DEFAULT_SOCORRO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @Test
    @Transactional
    void getAlergia() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        // Get the alergia
        restAlergiaMockMvc
            .perform(get(ENTITY_API_URL_ID, alergia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alergia.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sintoma").value(DEFAULT_SINTOMA))
            .andExpect(jsonPath("$.precaucoes").value(DEFAULT_PRECAUCOES))
            .andExpect(jsonPath("$.socorro").value(DEFAULT_SOCORRO))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingAlergia() throws Exception {
        // Get the alergia
        restAlergiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlergia() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();

        // Update the alergia
        Alergia updatedAlergia = alergiaRepository.findById(alergia.getId()).get();
        // Disconnect from session so that the updates on updatedAlergia are not directly saved in db
        em.detach(updatedAlergia);
        updatedAlergia.nome(UPDATED_NOME).sintoma(UPDATED_SINTOMA).precaucoes(UPDATED_PRECAUCOES).socorro(UPDATED_SOCORRO).obs(UPDATED_OBS);

        restAlergiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAlergia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAlergia))
            )
            .andExpect(status().isOk());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
        Alergia testAlergia = alergiaList.get(alergiaList.size() - 1);
        assertThat(testAlergia.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlergia.getSintoma()).isEqualTo(UPDATED_SINTOMA);
        assertThat(testAlergia.getPrecaucoes()).isEqualTo(UPDATED_PRECAUCOES);
        assertThat(testAlergia.getSocorro()).isEqualTo(UPDATED_SOCORRO);
        assertThat(testAlergia.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingAlergia() throws Exception {
        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();
        alergia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlergiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alergia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alergia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlergia() throws Exception {
        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();
        alergia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlergiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alergia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlergia() throws Exception {
        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();
        alergia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlergiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alergia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlergiaWithPatch() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();

        // Update the alergia using partial update
        Alergia partialUpdatedAlergia = new Alergia();
        partialUpdatedAlergia.setId(alergia.getId());

        partialUpdatedAlergia.sintoma(UPDATED_SINTOMA);

        restAlergiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlergia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlergia))
            )
            .andExpect(status().isOk());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
        Alergia testAlergia = alergiaList.get(alergiaList.size() - 1);
        assertThat(testAlergia.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlergia.getSintoma()).isEqualTo(UPDATED_SINTOMA);
        assertThat(testAlergia.getPrecaucoes()).isEqualTo(DEFAULT_PRECAUCOES);
        assertThat(testAlergia.getSocorro()).isEqualTo(DEFAULT_SOCORRO);
        assertThat(testAlergia.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void fullUpdateAlergiaWithPatch() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();

        // Update the alergia using partial update
        Alergia partialUpdatedAlergia = new Alergia();
        partialUpdatedAlergia.setId(alergia.getId());

        partialUpdatedAlergia
            .nome(UPDATED_NOME)
            .sintoma(UPDATED_SINTOMA)
            .precaucoes(UPDATED_PRECAUCOES)
            .socorro(UPDATED_SOCORRO)
            .obs(UPDATED_OBS);

        restAlergiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlergia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlergia))
            )
            .andExpect(status().isOk());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
        Alergia testAlergia = alergiaList.get(alergiaList.size() - 1);
        assertThat(testAlergia.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlergia.getSintoma()).isEqualTo(UPDATED_SINTOMA);
        assertThat(testAlergia.getPrecaucoes()).isEqualTo(UPDATED_PRECAUCOES);
        assertThat(testAlergia.getSocorro()).isEqualTo(UPDATED_SOCORRO);
        assertThat(testAlergia.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingAlergia() throws Exception {
        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();
        alergia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlergiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alergia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alergia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlergia() throws Exception {
        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();
        alergia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlergiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alergia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlergia() throws Exception {
        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();
        alergia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlergiaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alergia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlergia() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        int databaseSizeBeforeDelete = alergiaRepository.findAll().size();

        // Delete the alergia
        restAlergiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, alergia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
