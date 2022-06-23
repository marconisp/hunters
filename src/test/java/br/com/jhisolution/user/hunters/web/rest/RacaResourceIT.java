package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Raca;
import br.com.jhisolution.user.hunters.repository.RacaRepository;
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
 * Integration tests for the {@link RacaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RacaResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/racas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRacaMockMvc;

    private Raca raca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raca createEntity(EntityManager em) {
        Raca raca = new Raca().codigo(DEFAULT_CODIGO).descricao(DEFAULT_DESCRICAO);
        return raca;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raca createUpdatedEntity(EntityManager em) {
        Raca raca = new Raca().codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        return raca;
    }

    @BeforeEach
    public void initTest() {
        raca = createEntity(em);
    }

    @Test
    @Transactional
    void createRaca() throws Exception {
        int databaseSizeBeforeCreate = racaRepository.findAll().size();
        // Create the Raca
        restRacaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isCreated());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate + 1);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testRaca.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createRacaWithExistingId() throws Exception {
        // Create the Raca with an existing ID
        raca.setId(1L);

        int databaseSizeBeforeCreate = racaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = racaRepository.findAll().size();
        // set the field null
        raca.setCodigo(null);

        // Create the Raca, which fails.

        restRacaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isBadRequest());

        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = racaRepository.findAll().size();
        // set the field null
        raca.setDescricao(null);

        // Create the Raca, which fails.

        restRacaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isBadRequest());

        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRacas() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList
        restRacaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raca.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get the raca
        restRacaMockMvc
            .perform(get(ENTITY_API_URL_ID, raca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raca.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingRaca() throws Exception {
        // Get the raca
        restRacaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Update the raca
        Raca updatedRaca = racaRepository.findById(raca.getId()).get();
        // Disconnect from session so that the updates on updatedRaca are not directly saved in db
        em.detach(updatedRaca);
        updatedRaca.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restRacaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRaca.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRaca))
            )
            .andExpect(status().isOk());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testRaca.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();
        raca.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raca.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();
        raca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();
        raca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRacaWithPatch() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Update the raca using partial update
        Raca partialUpdatedRaca = new Raca();
        partialUpdatedRaca.setId(raca.getId());

        restRacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaca.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaca))
            )
            .andExpect(status().isOk());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testRaca.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateRacaWithPatch() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Update the raca using partial update
        Raca partialUpdatedRaca = new Raca();
        partialUpdatedRaca.setId(raca.getId());

        partialUpdatedRaca.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restRacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRaca.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaca))
            )
            .andExpect(status().isOk());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testRaca.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();
        raca.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raca.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();
        raca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();
        raca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeDelete = racaRepository.findAll().size();

        // Delete the raca
        restRacaMockMvc
            .perform(delete(ENTITY_API_URL_ID, raca.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
