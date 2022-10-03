package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.TipoTransacao;
import br.com.jhisolution.user.hunters.repository.TipoTransacaoRepository;
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
 * Integration tests for the {@link TipoTransacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoTransacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-transacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoTransacaoMockMvc;

    private TipoTransacao tipoTransacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTransacao createEntity(EntityManager em) {
        TipoTransacao tipoTransacao = new TipoTransacao().nome(DEFAULT_NOME);
        return tipoTransacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTransacao createUpdatedEntity(EntityManager em) {
        TipoTransacao tipoTransacao = new TipoTransacao().nome(UPDATED_NOME);
        return tipoTransacao;
    }

    @BeforeEach
    public void initTest() {
        tipoTransacao = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoTransacao() throws Exception {
        int databaseSizeBeforeCreate = tipoTransacaoRepository.findAll().size();
        // Create the TipoTransacao
        restTipoTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoTransacao)))
            .andExpect(status().isCreated());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoTransacao testTipoTransacao = tipoTransacaoList.get(tipoTransacaoList.size() - 1);
        assertThat(testTipoTransacao.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createTipoTransacaoWithExistingId() throws Exception {
        // Create the TipoTransacao with an existing ID
        tipoTransacao.setId(1L);

        int databaseSizeBeforeCreate = tipoTransacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoTransacao)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoTransacaoRepository.findAll().size();
        // set the field null
        tipoTransacao.setNome(null);

        // Create the TipoTransacao, which fails.

        restTipoTransacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoTransacao)))
            .andExpect(status().isBadRequest());

        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoTransacaos() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        // Get all the tipoTransacaoList
        restTipoTransacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTransacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getTipoTransacao() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        // Get the tipoTransacao
        restTipoTransacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoTransacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoTransacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingTipoTransacao() throws Exception {
        // Get the tipoTransacao
        restTipoTransacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoTransacao() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();

        // Update the tipoTransacao
        TipoTransacao updatedTipoTransacao = tipoTransacaoRepository.findById(tipoTransacao.getId()).get();
        // Disconnect from session so that the updates on updatedTipoTransacao are not directly saved in db
        em.detach(updatedTipoTransacao);
        updatedTipoTransacao.nome(UPDATED_NOME);

        restTipoTransacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoTransacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoTransacao))
            )
            .andExpect(status().isOk());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoTransacao testTipoTransacao = tipoTransacaoList.get(tipoTransacaoList.size() - 1);
        assertThat(testTipoTransacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingTipoTransacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();
        tipoTransacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoTransacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoTransacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoTransacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoTransacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();
        tipoTransacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTransacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoTransacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoTransacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();
        tipoTransacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTransacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoTransacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoTransacaoWithPatch() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();

        // Update the tipoTransacao using partial update
        TipoTransacao partialUpdatedTipoTransacao = new TipoTransacao();
        partialUpdatedTipoTransacao.setId(tipoTransacao.getId());

        partialUpdatedTipoTransacao.nome(UPDATED_NOME);

        restTipoTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoTransacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoTransacao))
            )
            .andExpect(status().isOk());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoTransacao testTipoTransacao = tipoTransacaoList.get(tipoTransacaoList.size() - 1);
        assertThat(testTipoTransacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void fullUpdateTipoTransacaoWithPatch() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();

        // Update the tipoTransacao using partial update
        TipoTransacao partialUpdatedTipoTransacao = new TipoTransacao();
        partialUpdatedTipoTransacao.setId(tipoTransacao.getId());

        partialUpdatedTipoTransacao.nome(UPDATED_NOME);

        restTipoTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoTransacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoTransacao))
            )
            .andExpect(status().isOk());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoTransacao testTipoTransacao = tipoTransacaoList.get(tipoTransacaoList.size() - 1);
        assertThat(testTipoTransacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingTipoTransacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();
        tipoTransacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoTransacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoTransacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoTransacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();
        tipoTransacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoTransacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoTransacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();
        tipoTransacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTransacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoTransacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoTransacao() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        int databaseSizeBeforeDelete = tipoTransacaoRepository.findAll().size();

        // Delete the tipoTransacao
        restTipoTransacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoTransacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
