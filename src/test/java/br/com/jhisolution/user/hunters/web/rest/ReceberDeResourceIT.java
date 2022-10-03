package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.ReceberDe;
import br.com.jhisolution.user.hunters.repository.ReceberDeRepository;
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
 * Integration tests for the {@link ReceberDeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReceberDeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CNPJ = false;
    private static final Boolean UPDATED_CNPJ = true;

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/receber-des";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReceberDeRepository receberDeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceberDeMockMvc;

    private ReceberDe receberDe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceberDe createEntity(EntityManager em) {
        ReceberDe receberDe = new ReceberDe()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .cnpj(DEFAULT_CNPJ)
            .documento(DEFAULT_DOCUMENTO);
        return receberDe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceberDe createUpdatedEntity(EntityManager em) {
        ReceberDe receberDe = new ReceberDe()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .cnpj(UPDATED_CNPJ)
            .documento(UPDATED_DOCUMENTO);
        return receberDe;
    }

    @BeforeEach
    public void initTest() {
        receberDe = createEntity(em);
    }

    @Test
    @Transactional
    void createReceberDe() throws Exception {
        int databaseSizeBeforeCreate = receberDeRepository.findAll().size();
        // Create the ReceberDe
        restReceberDeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receberDe)))
            .andExpect(status().isCreated());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeCreate + 1);
        ReceberDe testReceberDe = receberDeList.get(receberDeList.size() - 1);
        assertThat(testReceberDe.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testReceberDe.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testReceberDe.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testReceberDe.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
    }

    @Test
    @Transactional
    void createReceberDeWithExistingId() throws Exception {
        // Create the ReceberDe with an existing ID
        receberDe.setId(1L);

        int databaseSizeBeforeCreate = receberDeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceberDeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receberDe)))
            .andExpect(status().isBadRequest());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = receberDeRepository.findAll().size();
        // set the field null
        receberDe.setNome(null);

        // Create the ReceberDe, which fails.

        restReceberDeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receberDe)))
            .andExpect(status().isBadRequest());

        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReceberDes() throws Exception {
        // Initialize the database
        receberDeRepository.saveAndFlush(receberDe);

        // Get all the receberDeList
        restReceberDeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receberDe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.booleanValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)));
    }

    @Test
    @Transactional
    void getReceberDe() throws Exception {
        // Initialize the database
        receberDeRepository.saveAndFlush(receberDe);

        // Get the receberDe
        restReceberDeMockMvc
            .perform(get(ENTITY_API_URL_ID, receberDe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receberDe.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.booleanValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO));
    }

    @Test
    @Transactional
    void getNonExistingReceberDe() throws Exception {
        // Get the receberDe
        restReceberDeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReceberDe() throws Exception {
        // Initialize the database
        receberDeRepository.saveAndFlush(receberDe);

        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();

        // Update the receberDe
        ReceberDe updatedReceberDe = receberDeRepository.findById(receberDe.getId()).get();
        // Disconnect from session so that the updates on updatedReceberDe are not directly saved in db
        em.detach(updatedReceberDe);
        updatedReceberDe.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).cnpj(UPDATED_CNPJ).documento(UPDATED_DOCUMENTO);

        restReceberDeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceberDe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReceberDe))
            )
            .andExpect(status().isOk());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
        ReceberDe testReceberDe = receberDeList.get(receberDeList.size() - 1);
        assertThat(testReceberDe.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testReceberDe.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testReceberDe.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testReceberDe.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void putNonExistingReceberDe() throws Exception {
        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();
        receberDe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceberDeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receberDe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receberDe))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceberDe() throws Exception {
        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();
        receberDe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberDeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receberDe))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceberDe() throws Exception {
        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();
        receberDe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberDeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receberDe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceberDeWithPatch() throws Exception {
        // Initialize the database
        receberDeRepository.saveAndFlush(receberDe);

        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();

        // Update the receberDe using partial update
        ReceberDe partialUpdatedReceberDe = new ReceberDe();
        partialUpdatedReceberDe.setId(receberDe.getId());

        partialUpdatedReceberDe.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).documento(UPDATED_DOCUMENTO);

        restReceberDeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceberDe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceberDe))
            )
            .andExpect(status().isOk());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
        ReceberDe testReceberDe = receberDeList.get(receberDeList.size() - 1);
        assertThat(testReceberDe.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testReceberDe.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testReceberDe.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testReceberDe.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void fullUpdateReceberDeWithPatch() throws Exception {
        // Initialize the database
        receberDeRepository.saveAndFlush(receberDe);

        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();

        // Update the receberDe using partial update
        ReceberDe partialUpdatedReceberDe = new ReceberDe();
        partialUpdatedReceberDe.setId(receberDe.getId());

        partialUpdatedReceberDe.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).cnpj(UPDATED_CNPJ).documento(UPDATED_DOCUMENTO);

        restReceberDeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceberDe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceberDe))
            )
            .andExpect(status().isOk());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
        ReceberDe testReceberDe = receberDeList.get(receberDeList.size() - 1);
        assertThat(testReceberDe.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testReceberDe.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testReceberDe.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testReceberDe.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingReceberDe() throws Exception {
        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();
        receberDe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceberDeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receberDe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receberDe))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceberDe() throws Exception {
        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();
        receberDe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberDeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receberDe))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceberDe() throws Exception {
        int databaseSizeBeforeUpdate = receberDeRepository.findAll().size();
        receberDe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberDeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(receberDe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceberDe in the database
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceberDe() throws Exception {
        // Initialize the database
        receberDeRepository.saveAndFlush(receberDe);

        int databaseSizeBeforeDelete = receberDeRepository.findAll().size();

        // Delete the receberDe
        restReceberDeMockMvc
            .perform(delete(ENTITY_API_URL_ID, receberDe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReceberDe> receberDeList = receberDeRepository.findAll();
        assertThat(receberDeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
