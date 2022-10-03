package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.TipoReceber;
import br.com.jhisolution.user.hunters.repository.TipoReceberRepository;
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
 * Integration tests for the {@link TipoReceberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoReceberResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-recebers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoReceberRepository tipoReceberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoReceberMockMvc;

    private TipoReceber tipoReceber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoReceber createEntity(EntityManager em) {
        TipoReceber tipoReceber = new TipoReceber().nome(DEFAULT_NOME);
        return tipoReceber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoReceber createUpdatedEntity(EntityManager em) {
        TipoReceber tipoReceber = new TipoReceber().nome(UPDATED_NOME);
        return tipoReceber;
    }

    @BeforeEach
    public void initTest() {
        tipoReceber = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoReceber() throws Exception {
        int databaseSizeBeforeCreate = tipoReceberRepository.findAll().size();
        // Create the TipoReceber
        restTipoReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoReceber)))
            .andExpect(status().isCreated());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeCreate + 1);
        TipoReceber testTipoReceber = tipoReceberList.get(tipoReceberList.size() - 1);
        assertThat(testTipoReceber.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createTipoReceberWithExistingId() throws Exception {
        // Create the TipoReceber with an existing ID
        tipoReceber.setId(1L);

        int databaseSizeBeforeCreate = tipoReceberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoReceber)))
            .andExpect(status().isBadRequest());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoReceberRepository.findAll().size();
        // set the field null
        tipoReceber.setNome(null);

        // Create the TipoReceber, which fails.

        restTipoReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoReceber)))
            .andExpect(status().isBadRequest());

        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoRecebers() throws Exception {
        // Initialize the database
        tipoReceberRepository.saveAndFlush(tipoReceber);

        // Get all the tipoReceberList
        restTipoReceberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoReceber.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getTipoReceber() throws Exception {
        // Initialize the database
        tipoReceberRepository.saveAndFlush(tipoReceber);

        // Get the tipoReceber
        restTipoReceberMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoReceber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoReceber.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingTipoReceber() throws Exception {
        // Get the tipoReceber
        restTipoReceberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoReceber() throws Exception {
        // Initialize the database
        tipoReceberRepository.saveAndFlush(tipoReceber);

        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();

        // Update the tipoReceber
        TipoReceber updatedTipoReceber = tipoReceberRepository.findById(tipoReceber.getId()).get();
        // Disconnect from session so that the updates on updatedTipoReceber are not directly saved in db
        em.detach(updatedTipoReceber);
        updatedTipoReceber.nome(UPDATED_NOME);

        restTipoReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoReceber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoReceber))
            )
            .andExpect(status().isOk());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
        TipoReceber testTipoReceber = tipoReceberList.get(tipoReceberList.size() - 1);
        assertThat(testTipoReceber.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingTipoReceber() throws Exception {
        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();
        tipoReceber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoReceber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoReceber() throws Exception {
        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();
        tipoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoReceber() throws Exception {
        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();
        tipoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReceberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoReceber)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoReceberWithPatch() throws Exception {
        // Initialize the database
        tipoReceberRepository.saveAndFlush(tipoReceber);

        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();

        // Update the tipoReceber using partial update
        TipoReceber partialUpdatedTipoReceber = new TipoReceber();
        partialUpdatedTipoReceber.setId(tipoReceber.getId());

        partialUpdatedTipoReceber.nome(UPDATED_NOME);

        restTipoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoReceber))
            )
            .andExpect(status().isOk());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
        TipoReceber testTipoReceber = tipoReceberList.get(tipoReceberList.size() - 1);
        assertThat(testTipoReceber.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void fullUpdateTipoReceberWithPatch() throws Exception {
        // Initialize the database
        tipoReceberRepository.saveAndFlush(tipoReceber);

        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();

        // Update the tipoReceber using partial update
        TipoReceber partialUpdatedTipoReceber = new TipoReceber();
        partialUpdatedTipoReceber.setId(tipoReceber.getId());

        partialUpdatedTipoReceber.nome(UPDATED_NOME);

        restTipoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoReceber))
            )
            .andExpect(status().isOk());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
        TipoReceber testTipoReceber = tipoReceberList.get(tipoReceberList.size() - 1);
        assertThat(testTipoReceber.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingTipoReceber() throws Exception {
        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();
        tipoReceber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoReceber() throws Exception {
        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();
        tipoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoReceber))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoReceber() throws Exception {
        int databaseSizeBeforeUpdate = tipoReceberRepository.findAll().size();
        tipoReceber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReceberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoReceber))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoReceber in the database
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoReceber() throws Exception {
        // Initialize the database
        tipoReceberRepository.saveAndFlush(tipoReceber);

        int databaseSizeBeforeDelete = tipoReceberRepository.findAll().size();

        // Delete the tipoReceber
        restTipoReceberMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoReceber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoReceber> tipoReceberList = tipoReceberRepository.findAll();
        assertThat(tipoReceberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
