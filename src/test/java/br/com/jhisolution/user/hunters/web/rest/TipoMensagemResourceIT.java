package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.TipoMensagem;
import br.com.jhisolution.user.hunters.repository.TipoMensagemRepository;
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
 * Integration tests for the {@link TipoMensagemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoMensagemResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-mensagems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoMensagemRepository tipoMensagemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoMensagemMockMvc;

    private TipoMensagem tipoMensagem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoMensagem createEntity(EntityManager em) {
        TipoMensagem tipoMensagem = new TipoMensagem().codigo(DEFAULT_CODIGO).nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return tipoMensagem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoMensagem createUpdatedEntity(EntityManager em) {
        TipoMensagem tipoMensagem = new TipoMensagem().codigo(UPDATED_CODIGO).nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return tipoMensagem;
    }

    @BeforeEach
    public void initTest() {
        tipoMensagem = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoMensagem() throws Exception {
        int databaseSizeBeforeCreate = tipoMensagemRepository.findAll().size();
        // Create the TipoMensagem
        restTipoMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMensagem)))
            .andExpect(status().isCreated());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeCreate + 1);
        TipoMensagem testTipoMensagem = tipoMensagemList.get(tipoMensagemList.size() - 1);
        assertThat(testTipoMensagem.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoMensagem.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoMensagem.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createTipoMensagemWithExistingId() throws Exception {
        // Create the TipoMensagem with an existing ID
        tipoMensagem.setId(1L);

        int databaseSizeBeforeCreate = tipoMensagemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMensagem)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoMensagemRepository.findAll().size();
        // set the field null
        tipoMensagem.setCodigo(null);

        // Create the TipoMensagem, which fails.

        restTipoMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMensagem)))
            .andExpect(status().isBadRequest());

        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoMensagemRepository.findAll().size();
        // set the field null
        tipoMensagem.setNome(null);

        // Create the TipoMensagem, which fails.

        restTipoMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMensagem)))
            .andExpect(status().isBadRequest());

        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoMensagems() throws Exception {
        // Initialize the database
        tipoMensagemRepository.saveAndFlush(tipoMensagem);

        // Get all the tipoMensagemList
        restTipoMensagemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoMensagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getTipoMensagem() throws Exception {
        // Initialize the database
        tipoMensagemRepository.saveAndFlush(tipoMensagem);

        // Get the tipoMensagem
        restTipoMensagemMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoMensagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoMensagem.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingTipoMensagem() throws Exception {
        // Get the tipoMensagem
        restTipoMensagemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoMensagem() throws Exception {
        // Initialize the database
        tipoMensagemRepository.saveAndFlush(tipoMensagem);

        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();

        // Update the tipoMensagem
        TipoMensagem updatedTipoMensagem = tipoMensagemRepository.findById(tipoMensagem.getId()).get();
        // Disconnect from session so that the updates on updatedTipoMensagem are not directly saved in db
        em.detach(updatedTipoMensagem);
        updatedTipoMensagem.codigo(UPDATED_CODIGO).nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restTipoMensagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoMensagem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoMensagem))
            )
            .andExpect(status().isOk());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
        TipoMensagem testTipoMensagem = tipoMensagemList.get(tipoMensagemList.size() - 1);
        assertThat(testTipoMensagem.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoMensagem.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoMensagem.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingTipoMensagem() throws Exception {
        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();
        tipoMensagem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMensagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoMensagem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoMensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoMensagem() throws Exception {
        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();
        tipoMensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMensagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoMensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoMensagem() throws Exception {
        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();
        tipoMensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMensagemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMensagem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoMensagemWithPatch() throws Exception {
        // Initialize the database
        tipoMensagemRepository.saveAndFlush(tipoMensagem);

        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();

        // Update the tipoMensagem using partial update
        TipoMensagem partialUpdatedTipoMensagem = new TipoMensagem();
        partialUpdatedTipoMensagem.setId(tipoMensagem.getId());

        partialUpdatedTipoMensagem.codigo(UPDATED_CODIGO).nome(UPDATED_NOME);

        restTipoMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoMensagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoMensagem))
            )
            .andExpect(status().isOk());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
        TipoMensagem testTipoMensagem = tipoMensagemList.get(tipoMensagemList.size() - 1);
        assertThat(testTipoMensagem.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoMensagem.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoMensagem.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateTipoMensagemWithPatch() throws Exception {
        // Initialize the database
        tipoMensagemRepository.saveAndFlush(tipoMensagem);

        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();

        // Update the tipoMensagem using partial update
        TipoMensagem partialUpdatedTipoMensagem = new TipoMensagem();
        partialUpdatedTipoMensagem.setId(tipoMensagem.getId());

        partialUpdatedTipoMensagem.codigo(UPDATED_CODIGO).nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restTipoMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoMensagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoMensagem))
            )
            .andExpect(status().isOk());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
        TipoMensagem testTipoMensagem = tipoMensagemList.get(tipoMensagemList.size() - 1);
        assertThat(testTipoMensagem.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoMensagem.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoMensagem.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoMensagem() throws Exception {
        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();
        tipoMensagem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoMensagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoMensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoMensagem() throws Exception {
        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();
        tipoMensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoMensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoMensagem() throws Exception {
        int databaseSizeBeforeUpdate = tipoMensagemRepository.findAll().size();
        tipoMensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoMensagem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoMensagem in the database
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoMensagem() throws Exception {
        // Initialize the database
        tipoMensagemRepository.saveAndFlush(tipoMensagem);

        int databaseSizeBeforeDelete = tipoMensagemRepository.findAll().size();

        // Delete the tipoMensagem
        restTipoMensagemMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoMensagem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoMensagem> tipoMensagemList = tipoMensagemRepository.findAll();
        assertThat(tipoMensagemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
