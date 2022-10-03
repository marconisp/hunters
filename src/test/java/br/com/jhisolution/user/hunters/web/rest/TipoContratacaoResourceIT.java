package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.TipoContratacao;
import br.com.jhisolution.user.hunters.repository.TipoContratacaoRepository;
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
 * Integration tests for the {@link TipoContratacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoContratacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-contratacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoContratacaoRepository tipoContratacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoContratacaoMockMvc;

    private TipoContratacao tipoContratacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoContratacao createEntity(EntityManager em) {
        TipoContratacao tipoContratacao = new TipoContratacao().nome(DEFAULT_NOME);
        return tipoContratacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoContratacao createUpdatedEntity(EntityManager em) {
        TipoContratacao tipoContratacao = new TipoContratacao().nome(UPDATED_NOME);
        return tipoContratacao;
    }

    @BeforeEach
    public void initTest() {
        tipoContratacao = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoContratacao() throws Exception {
        int databaseSizeBeforeCreate = tipoContratacaoRepository.findAll().size();
        // Create the TipoContratacao
        restTipoContratacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isCreated());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoContratacao testTipoContratacao = tipoContratacaoList.get(tipoContratacaoList.size() - 1);
        assertThat(testTipoContratacao.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createTipoContratacaoWithExistingId() throws Exception {
        // Create the TipoContratacao with an existing ID
        tipoContratacao.setId(1L);

        int databaseSizeBeforeCreate = tipoContratacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoContratacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoContratacaoRepository.findAll().size();
        // set the field null
        tipoContratacao.setNome(null);

        // Create the TipoContratacao, which fails.

        restTipoContratacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isBadRequest());

        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoContratacaos() throws Exception {
        // Initialize the database
        tipoContratacaoRepository.saveAndFlush(tipoContratacao);

        // Get all the tipoContratacaoList
        restTipoContratacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoContratacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getTipoContratacao() throws Exception {
        // Initialize the database
        tipoContratacaoRepository.saveAndFlush(tipoContratacao);

        // Get the tipoContratacao
        restTipoContratacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoContratacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoContratacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingTipoContratacao() throws Exception {
        // Get the tipoContratacao
        restTipoContratacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoContratacao() throws Exception {
        // Initialize the database
        tipoContratacaoRepository.saveAndFlush(tipoContratacao);

        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();

        // Update the tipoContratacao
        TipoContratacao updatedTipoContratacao = tipoContratacaoRepository.findById(tipoContratacao.getId()).get();
        // Disconnect from session so that the updates on updatedTipoContratacao are not directly saved in db
        em.detach(updatedTipoContratacao);
        updatedTipoContratacao.nome(UPDATED_NOME);

        restTipoContratacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoContratacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoContratacao))
            )
            .andExpect(status().isOk());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoContratacao testTipoContratacao = tipoContratacaoList.get(tipoContratacaoList.size() - 1);
        assertThat(testTipoContratacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingTipoContratacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();
        tipoContratacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoContratacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoContratacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoContratacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();
        tipoContratacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContratacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoContratacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();
        tipoContratacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContratacaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoContratacaoWithPatch() throws Exception {
        // Initialize the database
        tipoContratacaoRepository.saveAndFlush(tipoContratacao);

        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();

        // Update the tipoContratacao using partial update
        TipoContratacao partialUpdatedTipoContratacao = new TipoContratacao();
        partialUpdatedTipoContratacao.setId(tipoContratacao.getId());

        partialUpdatedTipoContratacao.nome(UPDATED_NOME);

        restTipoContratacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoContratacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoContratacao))
            )
            .andExpect(status().isOk());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoContratacao testTipoContratacao = tipoContratacaoList.get(tipoContratacaoList.size() - 1);
        assertThat(testTipoContratacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void fullUpdateTipoContratacaoWithPatch() throws Exception {
        // Initialize the database
        tipoContratacaoRepository.saveAndFlush(tipoContratacao);

        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();

        // Update the tipoContratacao using partial update
        TipoContratacao partialUpdatedTipoContratacao = new TipoContratacao();
        partialUpdatedTipoContratacao.setId(tipoContratacao.getId());

        partialUpdatedTipoContratacao.nome(UPDATED_NOME);

        restTipoContratacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoContratacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoContratacao))
            )
            .andExpect(status().isOk());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoContratacao testTipoContratacao = tipoContratacaoList.get(tipoContratacaoList.size() - 1);
        assertThat(testTipoContratacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingTipoContratacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();
        tipoContratacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoContratacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoContratacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoContratacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();
        tipoContratacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContratacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoContratacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoContratacaoRepository.findAll().size();
        tipoContratacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContratacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoContratacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoContratacao in the database
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoContratacao() throws Exception {
        // Initialize the database
        tipoContratacaoRepository.saveAndFlush(tipoContratacao);

        int databaseSizeBeforeDelete = tipoContratacaoRepository.findAll().size();

        // Delete the tipoContratacao
        restTipoContratacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoContratacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoContratacao> tipoContratacaoList = tipoContratacaoRepository.findAll();
        assertThat(tipoContratacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
