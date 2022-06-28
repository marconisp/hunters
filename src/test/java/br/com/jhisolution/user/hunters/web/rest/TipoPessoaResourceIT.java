package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.TipoPessoa;
import br.com.jhisolution.user.hunters.repository.TipoPessoaRepository;
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
 * Integration tests for the {@link TipoPessoaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoPessoaResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoPessoaMockMvc;

    private TipoPessoa tipoPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPessoa createEntity(EntityManager em) {
        TipoPessoa tipoPessoa = new TipoPessoa().codigo(DEFAULT_CODIGO).nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return tipoPessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPessoa createUpdatedEntity(EntityManager em) {
        TipoPessoa tipoPessoa = new TipoPessoa().codigo(UPDATED_CODIGO).nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return tipoPessoa;
    }

    @BeforeEach
    public void initTest() {
        tipoPessoa = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoPessoa() throws Exception {
        int databaseSizeBeforeCreate = tipoPessoaRepository.findAll().size();
        // Create the TipoPessoa
        restTipoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPessoa)))
            .andExpect(status().isCreated());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPessoa testTipoPessoa = tipoPessoaList.get(tipoPessoaList.size() - 1);
        assertThat(testTipoPessoa.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoPessoa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createTipoPessoaWithExistingId() throws Exception {
        // Create the TipoPessoa with an existing ID
        tipoPessoa.setId(1L);

        int databaseSizeBeforeCreate = tipoPessoaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPessoa)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPessoaRepository.findAll().size();
        // set the field null
        tipoPessoa.setCodigo(null);

        // Create the TipoPessoa, which fails.

        restTipoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPessoa)))
            .andExpect(status().isBadRequest());

        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPessoaRepository.findAll().size();
        // set the field null
        tipoPessoa.setNome(null);

        // Create the TipoPessoa, which fails.

        restTipoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPessoa)))
            .andExpect(status().isBadRequest());

        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoPessoas() throws Exception {
        // Initialize the database
        tipoPessoaRepository.saveAndFlush(tipoPessoa);

        // Get all the tipoPessoaList
        restTipoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getTipoPessoa() throws Exception {
        // Initialize the database
        tipoPessoaRepository.saveAndFlush(tipoPessoa);

        // Get the tipoPessoa
        restTipoPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingTipoPessoa() throws Exception {
        // Get the tipoPessoa
        restTipoPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoPessoa() throws Exception {
        // Initialize the database
        tipoPessoaRepository.saveAndFlush(tipoPessoa);

        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();

        // Update the tipoPessoa
        TipoPessoa updatedTipoPessoa = tipoPessoaRepository.findById(tipoPessoa.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPessoa are not directly saved in db
        em.detach(updatedTipoPessoa);
        updatedTipoPessoa.codigo(UPDATED_CODIGO).nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restTipoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
        TipoPessoa testTipoPessoa = tipoPessoaList.get(tipoPessoaList.size() - 1);
        assertThat(testTipoPessoa.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoPessoa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingTipoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();
        tipoPessoa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();
        tipoPessoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();
        tipoPessoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoPessoaWithPatch() throws Exception {
        // Initialize the database
        tipoPessoaRepository.saveAndFlush(tipoPessoa);

        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();

        // Update the tipoPessoa using partial update
        TipoPessoa partialUpdatedTipoPessoa = new TipoPessoa();
        partialUpdatedTipoPessoa.setId(tipoPessoa.getId());

        partialUpdatedTipoPessoa.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restTipoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
        TipoPessoa testTipoPessoa = tipoPessoaList.get(tipoPessoaList.size() - 1);
        assertThat(testTipoPessoa.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoPessoa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateTipoPessoaWithPatch() throws Exception {
        // Initialize the database
        tipoPessoaRepository.saveAndFlush(tipoPessoa);

        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();

        // Update the tipoPessoa using partial update
        TipoPessoa partialUpdatedTipoPessoa = new TipoPessoa();
        partialUpdatedTipoPessoa.setId(tipoPessoa.getId());

        partialUpdatedTipoPessoa.codigo(UPDATED_CODIGO).nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restTipoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
        TipoPessoa testTipoPessoa = tipoPessoaList.get(tipoPessoaList.size() - 1);
        assertThat(testTipoPessoa.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoPessoa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();
        tipoPessoa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();
        tipoPessoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = tipoPessoaRepository.findAll().size();
        tipoPessoa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoPessoa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoPessoa in the database
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoPessoa() throws Exception {
        // Initialize the database
        tipoPessoaRepository.saveAndFlush(tipoPessoa);

        int databaseSizeBeforeDelete = tipoPessoaRepository.findAll().size();

        // Delete the tipoPessoa
        restTipoPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoPessoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoPessoa> tipoPessoaList = tipoPessoaRepository.findAll();
        assertThat(tipoPessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
