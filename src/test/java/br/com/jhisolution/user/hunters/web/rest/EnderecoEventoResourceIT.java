package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.EnderecoEvento;
import br.com.jhisolution.user.hunters.repository.EnderecoEventoRepository;
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
 * Integration tests for the {@link EnderecoEventoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnderecoEventoResourceIT {

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AA";
    private static final String UPDATED_UF = "BB";

    private static final String ENTITY_API_URL = "/api/endereco-eventos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnderecoEventoRepository enderecoEventoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoEventoMockMvc;

    private EnderecoEvento enderecoEvento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoEvento createEntity(EntityManager em) {
        EnderecoEvento enderecoEvento = new EnderecoEvento()
            .cep(DEFAULT_CEP)
            .logradouro(DEFAULT_LOGRADOURO)
            .complemento(DEFAULT_COMPLEMENTO)
            .numero(DEFAULT_NUMERO)
            .bairro(DEFAULT_BAIRRO)
            .cidade(DEFAULT_CIDADE)
            .uf(DEFAULT_UF);
        return enderecoEvento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoEvento createUpdatedEntity(EntityManager em) {
        EnderecoEvento enderecoEvento = new EnderecoEvento()
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF);
        return enderecoEvento;
    }

    @BeforeEach
    public void initTest() {
        enderecoEvento = createEntity(em);
    }

    @Test
    @Transactional
    void createEnderecoEvento() throws Exception {
        int databaseSizeBeforeCreate = enderecoEventoRepository.findAll().size();
        // Create the EnderecoEvento
        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isCreated());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeCreate + 1);
        EnderecoEvento testEnderecoEvento = enderecoEventoList.get(enderecoEventoList.size() - 1);
        assertThat(testEnderecoEvento.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEnderecoEvento.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testEnderecoEvento.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEnderecoEvento.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEnderecoEvento.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEnderecoEvento.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEnderecoEvento.getUf()).isEqualTo(DEFAULT_UF);
    }

    @Test
    @Transactional
    void createEnderecoEventoWithExistingId() throws Exception {
        // Create the EnderecoEvento with an existing ID
        enderecoEvento.setId(1L);

        int databaseSizeBeforeCreate = enderecoEventoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = enderecoEventoRepository.findAll().size();
        // set the field null
        enderecoEvento.setCep(null);

        // Create the EnderecoEvento, which fails.

        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = enderecoEventoRepository.findAll().size();
        // set the field null
        enderecoEvento.setLogradouro(null);

        // Create the EnderecoEvento, which fails.

        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = enderecoEventoRepository.findAll().size();
        // set the field null
        enderecoEvento.setNumero(null);

        // Create the EnderecoEvento, which fails.

        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = enderecoEventoRepository.findAll().size();
        // set the field null
        enderecoEvento.setBairro(null);

        // Create the EnderecoEvento, which fails.

        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = enderecoEventoRepository.findAll().size();
        // set the field null
        enderecoEvento.setCidade(null);

        // Create the EnderecoEvento, which fails.

        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUfIsRequired() throws Exception {
        int databaseSizeBeforeTest = enderecoEventoRepository.findAll().size();
        // set the field null
        enderecoEvento.setUf(null);

        // Create the EnderecoEvento, which fails.

        restEnderecoEventoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEnderecoEventos() throws Exception {
        // Initialize the database
        enderecoEventoRepository.saveAndFlush(enderecoEvento);

        // Get all the enderecoEventoList
        restEnderecoEventoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoEvento.getId().intValue())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)));
    }

    @Test
    @Transactional
    void getEnderecoEvento() throws Exception {
        // Initialize the database
        enderecoEventoRepository.saveAndFlush(enderecoEvento);

        // Get the enderecoEvento
        restEnderecoEventoMockMvc
            .perform(get(ENTITY_API_URL_ID, enderecoEvento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enderecoEvento.getId().intValue()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF));
    }

    @Test
    @Transactional
    void getNonExistingEnderecoEvento() throws Exception {
        // Get the enderecoEvento
        restEnderecoEventoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnderecoEvento() throws Exception {
        // Initialize the database
        enderecoEventoRepository.saveAndFlush(enderecoEvento);

        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();

        // Update the enderecoEvento
        EnderecoEvento updatedEnderecoEvento = enderecoEventoRepository.findById(enderecoEvento.getId()).get();
        // Disconnect from session so that the updates on updatedEnderecoEvento are not directly saved in db
        em.detach(updatedEnderecoEvento);
        updatedEnderecoEvento
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF);

        restEnderecoEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnderecoEvento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnderecoEvento))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
        EnderecoEvento testEnderecoEvento = enderecoEventoList.get(enderecoEventoList.size() - 1);
        assertThat(testEnderecoEvento.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEnderecoEvento.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEnderecoEvento.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEnderecoEvento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEnderecoEvento.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEnderecoEvento.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEnderecoEvento.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    void putNonExistingEnderecoEvento() throws Exception {
        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();
        enderecoEvento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoEvento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnderecoEvento() throws Exception {
        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();
        enderecoEvento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnderecoEvento() throws Exception {
        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();
        enderecoEvento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEventoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enderecoEvento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnderecoEventoWithPatch() throws Exception {
        // Initialize the database
        enderecoEventoRepository.saveAndFlush(enderecoEvento);

        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();

        // Update the enderecoEvento using partial update
        EnderecoEvento partialUpdatedEnderecoEvento = new EnderecoEvento();
        partialUpdatedEnderecoEvento.setId(enderecoEvento.getId());

        partialUpdatedEnderecoEvento.logradouro(UPDATED_LOGRADOURO).bairro(UPDATED_BAIRRO).uf(UPDATED_UF);

        restEnderecoEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnderecoEvento))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
        EnderecoEvento testEnderecoEvento = enderecoEventoList.get(enderecoEventoList.size() - 1);
        assertThat(testEnderecoEvento.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEnderecoEvento.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEnderecoEvento.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEnderecoEvento.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEnderecoEvento.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEnderecoEvento.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEnderecoEvento.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    void fullUpdateEnderecoEventoWithPatch() throws Exception {
        // Initialize the database
        enderecoEventoRepository.saveAndFlush(enderecoEvento);

        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();

        // Update the enderecoEvento using partial update
        EnderecoEvento partialUpdatedEnderecoEvento = new EnderecoEvento();
        partialUpdatedEnderecoEvento.setId(enderecoEvento.getId());

        partialUpdatedEnderecoEvento
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF);

        restEnderecoEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnderecoEvento))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
        EnderecoEvento testEnderecoEvento = enderecoEventoList.get(enderecoEventoList.size() - 1);
        assertThat(testEnderecoEvento.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEnderecoEvento.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testEnderecoEvento.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEnderecoEvento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEnderecoEvento.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEnderecoEvento.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEnderecoEvento.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    void patchNonExistingEnderecoEvento() throws Exception {
        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();
        enderecoEvento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enderecoEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnderecoEvento() throws Exception {
        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();
        enderecoEvento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnderecoEvento() throws Exception {
        int databaseSizeBeforeUpdate = enderecoEventoRepository.findAll().size();
        enderecoEvento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEventoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(enderecoEvento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoEvento in the database
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnderecoEvento() throws Exception {
        // Initialize the database
        enderecoEventoRepository.saveAndFlush(enderecoEvento);

        int databaseSizeBeforeDelete = enderecoEventoRepository.findAll().size();

        // Delete the enderecoEvento
        restEnderecoEventoMockMvc
            .perform(delete(ENTITY_API_URL_ID, enderecoEvento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnderecoEvento> enderecoEventoList = enderecoEventoRepository.findAll();
        assertThat(enderecoEventoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
