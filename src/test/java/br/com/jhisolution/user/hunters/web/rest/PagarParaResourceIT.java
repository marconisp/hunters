package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.PagarPara;
import br.com.jhisolution.user.hunters.repository.PagarParaRepository;
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
 * Integration tests for the {@link PagarParaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PagarParaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CNPJ = false;
    private static final Boolean UPDATED_CNPJ = true;

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BANCO = "AAAAAAAAAA";
    private static final String UPDATED_BANCO = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_AGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CONTA = "AAAAAAAAAA";
    private static final String UPDATED_CONTA = "BBBBBBBBBB";

    private static final String DEFAULT_PIX = "AAAAAAAAAA";
    private static final String UPDATED_PIX = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pagar-paras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PagarParaRepository pagarParaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPagarParaMockMvc;

    private PagarPara pagarPara;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PagarPara createEntity(EntityManager em) {
        PagarPara pagarPara = new PagarPara()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .cnpj(DEFAULT_CNPJ)
            .documento(DEFAULT_DOCUMENTO)
            .banco(DEFAULT_BANCO)
            .agencia(DEFAULT_AGENCIA)
            .conta(DEFAULT_CONTA)
            .pix(DEFAULT_PIX);
        return pagarPara;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PagarPara createUpdatedEntity(EntityManager em) {
        PagarPara pagarPara = new PagarPara()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .cnpj(UPDATED_CNPJ)
            .documento(UPDATED_DOCUMENTO)
            .banco(UPDATED_BANCO)
            .agencia(UPDATED_AGENCIA)
            .conta(UPDATED_CONTA)
            .pix(UPDATED_PIX);
        return pagarPara;
    }

    @BeforeEach
    public void initTest() {
        pagarPara = createEntity(em);
    }

    @Test
    @Transactional
    void createPagarPara() throws Exception {
        int databaseSizeBeforeCreate = pagarParaRepository.findAll().size();
        // Create the PagarPara
        restPagarParaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagarPara)))
            .andExpect(status().isCreated());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeCreate + 1);
        PagarPara testPagarPara = pagarParaList.get(pagarParaList.size() - 1);
        assertThat(testPagarPara.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPagarPara.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPagarPara.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testPagarPara.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testPagarPara.getBanco()).isEqualTo(DEFAULT_BANCO);
        assertThat(testPagarPara.getAgencia()).isEqualTo(DEFAULT_AGENCIA);
        assertThat(testPagarPara.getConta()).isEqualTo(DEFAULT_CONTA);
        assertThat(testPagarPara.getPix()).isEqualTo(DEFAULT_PIX);
    }

    @Test
    @Transactional
    void createPagarParaWithExistingId() throws Exception {
        // Create the PagarPara with an existing ID
        pagarPara.setId(1L);

        int databaseSizeBeforeCreate = pagarParaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagarParaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagarPara)))
            .andExpect(status().isBadRequest());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagarParaRepository.findAll().size();
        // set the field null
        pagarPara.setNome(null);

        // Create the PagarPara, which fails.

        restPagarParaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagarPara)))
            .andExpect(status().isBadRequest());

        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPagarParas() throws Exception {
        // Initialize the database
        pagarParaRepository.saveAndFlush(pagarPara);

        // Get all the pagarParaList
        restPagarParaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagarPara.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.booleanValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO)))
            .andExpect(jsonPath("$.[*].agencia").value(hasItem(DEFAULT_AGENCIA)))
            .andExpect(jsonPath("$.[*].conta").value(hasItem(DEFAULT_CONTA)))
            .andExpect(jsonPath("$.[*].pix").value(hasItem(DEFAULT_PIX)));
    }

    @Test
    @Transactional
    void getPagarPara() throws Exception {
        // Initialize the database
        pagarParaRepository.saveAndFlush(pagarPara);

        // Get the pagarPara
        restPagarParaMockMvc
            .perform(get(ENTITY_API_URL_ID, pagarPara.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pagarPara.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.booleanValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.banco").value(DEFAULT_BANCO))
            .andExpect(jsonPath("$.agencia").value(DEFAULT_AGENCIA))
            .andExpect(jsonPath("$.conta").value(DEFAULT_CONTA))
            .andExpect(jsonPath("$.pix").value(DEFAULT_PIX));
    }

    @Test
    @Transactional
    void getNonExistingPagarPara() throws Exception {
        // Get the pagarPara
        restPagarParaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPagarPara() throws Exception {
        // Initialize the database
        pagarParaRepository.saveAndFlush(pagarPara);

        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();

        // Update the pagarPara
        PagarPara updatedPagarPara = pagarParaRepository.findById(pagarPara.getId()).get();
        // Disconnect from session so that the updates on updatedPagarPara are not directly saved in db
        em.detach(updatedPagarPara);
        updatedPagarPara
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .cnpj(UPDATED_CNPJ)
            .documento(UPDATED_DOCUMENTO)
            .banco(UPDATED_BANCO)
            .agencia(UPDATED_AGENCIA)
            .conta(UPDATED_CONTA)
            .pix(UPDATED_PIX);

        restPagarParaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPagarPara.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPagarPara))
            )
            .andExpect(status().isOk());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
        PagarPara testPagarPara = pagarParaList.get(pagarParaList.size() - 1);
        assertThat(testPagarPara.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPagarPara.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPagarPara.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testPagarPara.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testPagarPara.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testPagarPara.getAgencia()).isEqualTo(UPDATED_AGENCIA);
        assertThat(testPagarPara.getConta()).isEqualTo(UPDATED_CONTA);
        assertThat(testPagarPara.getPix()).isEqualTo(UPDATED_PIX);
    }

    @Test
    @Transactional
    void putNonExistingPagarPara() throws Exception {
        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();
        pagarPara.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagarParaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pagarPara.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagarPara))
            )
            .andExpect(status().isBadRequest());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPagarPara() throws Exception {
        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();
        pagarPara.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarParaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagarPara))
            )
            .andExpect(status().isBadRequest());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPagarPara() throws Exception {
        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();
        pagarPara.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarParaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagarPara)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePagarParaWithPatch() throws Exception {
        // Initialize the database
        pagarParaRepository.saveAndFlush(pagarPara);

        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();

        // Update the pagarPara using partial update
        PagarPara partialUpdatedPagarPara = new PagarPara();
        partialUpdatedPagarPara.setId(pagarPara.getId());

        partialUpdatedPagarPara.nome(UPDATED_NOME).cnpj(UPDATED_CNPJ).conta(UPDATED_CONTA).pix(UPDATED_PIX);

        restPagarParaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagarPara.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPagarPara))
            )
            .andExpect(status().isOk());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
        PagarPara testPagarPara = pagarParaList.get(pagarParaList.size() - 1);
        assertThat(testPagarPara.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPagarPara.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPagarPara.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testPagarPara.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testPagarPara.getBanco()).isEqualTo(DEFAULT_BANCO);
        assertThat(testPagarPara.getAgencia()).isEqualTo(DEFAULT_AGENCIA);
        assertThat(testPagarPara.getConta()).isEqualTo(UPDATED_CONTA);
        assertThat(testPagarPara.getPix()).isEqualTo(UPDATED_PIX);
    }

    @Test
    @Transactional
    void fullUpdatePagarParaWithPatch() throws Exception {
        // Initialize the database
        pagarParaRepository.saveAndFlush(pagarPara);

        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();

        // Update the pagarPara using partial update
        PagarPara partialUpdatedPagarPara = new PagarPara();
        partialUpdatedPagarPara.setId(pagarPara.getId());

        partialUpdatedPagarPara
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .cnpj(UPDATED_CNPJ)
            .documento(UPDATED_DOCUMENTO)
            .banco(UPDATED_BANCO)
            .agencia(UPDATED_AGENCIA)
            .conta(UPDATED_CONTA)
            .pix(UPDATED_PIX);

        restPagarParaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagarPara.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPagarPara))
            )
            .andExpect(status().isOk());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
        PagarPara testPagarPara = pagarParaList.get(pagarParaList.size() - 1);
        assertThat(testPagarPara.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPagarPara.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPagarPara.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testPagarPara.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testPagarPara.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testPagarPara.getAgencia()).isEqualTo(UPDATED_AGENCIA);
        assertThat(testPagarPara.getConta()).isEqualTo(UPDATED_CONTA);
        assertThat(testPagarPara.getPix()).isEqualTo(UPDATED_PIX);
    }

    @Test
    @Transactional
    void patchNonExistingPagarPara() throws Exception {
        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();
        pagarPara.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagarParaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pagarPara.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagarPara))
            )
            .andExpect(status().isBadRequest());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPagarPara() throws Exception {
        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();
        pagarPara.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarParaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagarPara))
            )
            .andExpect(status().isBadRequest());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPagarPara() throws Exception {
        int databaseSizeBeforeUpdate = pagarParaRepository.findAll().size();
        pagarPara.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarParaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pagarPara))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PagarPara in the database
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePagarPara() throws Exception {
        // Initialize the database
        pagarParaRepository.saveAndFlush(pagarPara);

        int databaseSizeBeforeDelete = pagarParaRepository.findAll().size();

        // Delete the pagarPara
        restPagarParaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pagarPara.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PagarPara> pagarParaList = pagarParaRepository.findAll();
        assertThat(pagarParaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
