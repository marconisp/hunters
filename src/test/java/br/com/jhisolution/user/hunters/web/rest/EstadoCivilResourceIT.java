package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.EstadoCivil;
import br.com.jhisolution.user.hunters.repository.EstadoCivilRepository;
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
 * Integration tests for the {@link EstadoCivilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoCivilResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estado-civils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoCivilMockMvc;

    private EstadoCivil estadoCivil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoCivil createEntity(EntityManager em) {
        EstadoCivil estadoCivil = new EstadoCivil().codigo(DEFAULT_CODIGO).descricao(DEFAULT_DESCRICAO);
        return estadoCivil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoCivil createUpdatedEntity(EntityManager em) {
        EstadoCivil estadoCivil = new EstadoCivil().codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        return estadoCivil;
    }

    @BeforeEach
    public void initTest() {
        estadoCivil = createEntity(em);
    }

    @Test
    @Transactional
    void createEstadoCivil() throws Exception {
        int databaseSizeBeforeCreate = estadoCivilRepository.findAll().size();
        // Create the EstadoCivil
        restEstadoCivilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoCivil)))
            .andExpect(status().isCreated());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoCivil testEstadoCivil = estadoCivilList.get(estadoCivilList.size() - 1);
        assertThat(testEstadoCivil.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testEstadoCivil.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createEstadoCivilWithExistingId() throws Exception {
        // Create the EstadoCivil with an existing ID
        estadoCivil.setId(1L);

        int databaseSizeBeforeCreate = estadoCivilRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoCivilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoCivil)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoCivilRepository.findAll().size();
        // set the field null
        estadoCivil.setCodigo(null);

        // Create the EstadoCivil, which fails.

        restEstadoCivilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoCivil)))
            .andExpect(status().isBadRequest());

        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoCivilRepository.findAll().size();
        // set the field null
        estadoCivil.setDescricao(null);

        // Create the EstadoCivil, which fails.

        restEstadoCivilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoCivil)))
            .andExpect(status().isBadRequest());

        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstadoCivils() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        // Get all the estadoCivilList
        restEstadoCivilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoCivil.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getEstadoCivil() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        // Get the estadoCivil
        restEstadoCivilMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoCivil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoCivil.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingEstadoCivil() throws Exception {
        // Get the estadoCivil
        restEstadoCivilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstadoCivil() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();

        // Update the estadoCivil
        EstadoCivil updatedEstadoCivil = estadoCivilRepository.findById(estadoCivil.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoCivil are not directly saved in db
        em.detach(updatedEstadoCivil);
        updatedEstadoCivil.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restEstadoCivilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstadoCivil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstadoCivil))
            )
            .andExpect(status().isOk());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
        EstadoCivil testEstadoCivil = estadoCivilList.get(estadoCivilList.size() - 1);
        assertThat(testEstadoCivil.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEstadoCivil.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingEstadoCivil() throws Exception {
        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();
        estadoCivil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoCivilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoCivil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoCivil() throws Exception {
        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();
        estadoCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoCivilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoCivil() throws Exception {
        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();
        estadoCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoCivilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoCivil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoCivilWithPatch() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();

        // Update the estadoCivil using partial update
        EstadoCivil partialUpdatedEstadoCivil = new EstadoCivil();
        partialUpdatedEstadoCivil.setId(estadoCivil.getId());

        partialUpdatedEstadoCivil.codigo(UPDATED_CODIGO);

        restEstadoCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoCivil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoCivil))
            )
            .andExpect(status().isOk());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
        EstadoCivil testEstadoCivil = estadoCivilList.get(estadoCivilList.size() - 1);
        assertThat(testEstadoCivil.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEstadoCivil.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateEstadoCivilWithPatch() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();

        // Update the estadoCivil using partial update
        EstadoCivil partialUpdatedEstadoCivil = new EstadoCivil();
        partialUpdatedEstadoCivil.setId(estadoCivil.getId());

        partialUpdatedEstadoCivil.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restEstadoCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoCivil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoCivil))
            )
            .andExpect(status().isOk());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
        EstadoCivil testEstadoCivil = estadoCivilList.get(estadoCivilList.size() - 1);
        assertThat(testEstadoCivil.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEstadoCivil.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingEstadoCivil() throws Exception {
        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();
        estadoCivil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoCivil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoCivil() throws Exception {
        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();
        estadoCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoCivil() throws Exception {
        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();
        estadoCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoCivilMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estadoCivil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoCivil() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        int databaseSizeBeforeDelete = estadoCivilRepository.findAll().size();

        // Delete the estadoCivil
        restEstadoCivilMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoCivil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
