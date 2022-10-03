package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.EntradaEstoque;
import br.com.jhisolution.user.hunters.repository.EntradaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.EntradaEstoqueService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EntradaEstoqueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EntradaEstoqueResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QTDE = 1;
    private static final Integer UPDATED_QTDE = 2;

    private static final Float DEFAULT_VALOR_UNITARIO = 1F;
    private static final Float UPDATED_VALOR_UNITARIO = 2F;

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entrada-estoques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntradaEstoqueRepository entradaEstoqueRepository;

    @Mock
    private EntradaEstoqueRepository entradaEstoqueRepositoryMock;

    @Mock
    private EntradaEstoqueService entradaEstoqueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntradaEstoqueMockMvc;

    private EntradaEstoque entradaEstoque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntradaEstoque createEntity(EntityManager em) {
        EntradaEstoque entradaEstoque = new EntradaEstoque()
            .data(DEFAULT_DATA)
            .qtde(DEFAULT_QTDE)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .obs(DEFAULT_OBS);
        return entradaEstoque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntradaEstoque createUpdatedEntity(EntityManager em) {
        EntradaEstoque entradaEstoque = new EntradaEstoque()
            .data(UPDATED_DATA)
            .qtde(UPDATED_QTDE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .obs(UPDATED_OBS);
        return entradaEstoque;
    }

    @BeforeEach
    public void initTest() {
        entradaEstoque = createEntity(em);
    }

    @Test
    @Transactional
    void createEntradaEstoque() throws Exception {
        int databaseSizeBeforeCreate = entradaEstoqueRepository.findAll().size();
        // Create the EntradaEstoque
        restEntradaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isCreated());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeCreate + 1);
        EntradaEstoque testEntradaEstoque = entradaEstoqueList.get(entradaEstoqueList.size() - 1);
        assertThat(testEntradaEstoque.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testEntradaEstoque.getQtde()).isEqualTo(DEFAULT_QTDE);
        assertThat(testEntradaEstoque.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testEntradaEstoque.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createEntradaEstoqueWithExistingId() throws Exception {
        // Create the EntradaEstoque with an existing ID
        entradaEstoque.setId(1L);

        int databaseSizeBeforeCreate = entradaEstoqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntradaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = entradaEstoqueRepository.findAll().size();
        // set the field null
        entradaEstoque.setData(null);

        // Create the EntradaEstoque, which fails.

        restEntradaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQtdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = entradaEstoqueRepository.findAll().size();
        // set the field null
        entradaEstoque.setQtde(null);

        // Create the EntradaEstoque, which fails.

        restEntradaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = entradaEstoqueRepository.findAll().size();
        // set the field null
        entradaEstoque.setValorUnitario(null);

        // Create the EntradaEstoque, which fails.

        restEntradaEstoqueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntradaEstoques() throws Exception {
        // Initialize the database
        entradaEstoqueRepository.saveAndFlush(entradaEstoque);

        // Get all the entradaEstoqueList
        restEntradaEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entradaEstoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].qtde").value(hasItem(DEFAULT_QTDE)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntradaEstoquesWithEagerRelationshipsIsEnabled() throws Exception {
        when(entradaEstoqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEntradaEstoqueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(entradaEstoqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntradaEstoquesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(entradaEstoqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEntradaEstoqueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(entradaEstoqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEntradaEstoque() throws Exception {
        // Initialize the database
        entradaEstoqueRepository.saveAndFlush(entradaEstoque);

        // Get the entradaEstoque
        restEntradaEstoqueMockMvc
            .perform(get(ENTITY_API_URL_ID, entradaEstoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entradaEstoque.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.qtde").value(DEFAULT_QTDE))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO.doubleValue()))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingEntradaEstoque() throws Exception {
        // Get the entradaEstoque
        restEntradaEstoqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntradaEstoque() throws Exception {
        // Initialize the database
        entradaEstoqueRepository.saveAndFlush(entradaEstoque);

        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();

        // Update the entradaEstoque
        EntradaEstoque updatedEntradaEstoque = entradaEstoqueRepository.findById(entradaEstoque.getId()).get();
        // Disconnect from session so that the updates on updatedEntradaEstoque are not directly saved in db
        em.detach(updatedEntradaEstoque);
        updatedEntradaEstoque.data(UPDATED_DATA).qtde(UPDATED_QTDE).valorUnitario(UPDATED_VALOR_UNITARIO).obs(UPDATED_OBS);

        restEntradaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntradaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntradaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        EntradaEstoque testEntradaEstoque = entradaEstoqueList.get(entradaEstoqueList.size() - 1);
        assertThat(testEntradaEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEntradaEstoque.getQtde()).isEqualTo(UPDATED_QTDE);
        assertThat(testEntradaEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testEntradaEstoque.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();
        entradaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntradaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entradaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();
        entradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();
        entradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaEstoqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entradaEstoque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntradaEstoqueWithPatch() throws Exception {
        // Initialize the database
        entradaEstoqueRepository.saveAndFlush(entradaEstoque);

        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();

        // Update the entradaEstoque using partial update
        EntradaEstoque partialUpdatedEntradaEstoque = new EntradaEstoque();
        partialUpdatedEntradaEstoque.setId(entradaEstoque.getId());

        partialUpdatedEntradaEstoque.qtde(UPDATED_QTDE).valorUnitario(UPDATED_VALOR_UNITARIO).obs(UPDATED_OBS);

        restEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntradaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntradaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        EntradaEstoque testEntradaEstoque = entradaEstoqueList.get(entradaEstoqueList.size() - 1);
        assertThat(testEntradaEstoque.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testEntradaEstoque.getQtde()).isEqualTo(UPDATED_QTDE);
        assertThat(testEntradaEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testEntradaEstoque.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdateEntradaEstoqueWithPatch() throws Exception {
        // Initialize the database
        entradaEstoqueRepository.saveAndFlush(entradaEstoque);

        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();

        // Update the entradaEstoque using partial update
        EntradaEstoque partialUpdatedEntradaEstoque = new EntradaEstoque();
        partialUpdatedEntradaEstoque.setId(entradaEstoque.getId());

        partialUpdatedEntradaEstoque.data(UPDATED_DATA).qtde(UPDATED_QTDE).valorUnitario(UPDATED_VALOR_UNITARIO).obs(UPDATED_OBS);

        restEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntradaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntradaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        EntradaEstoque testEntradaEstoque = entradaEstoqueList.get(entradaEstoqueList.size() - 1);
        assertThat(testEntradaEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEntradaEstoque.getQtde()).isEqualTo(UPDATED_QTDE);
        assertThat(testEntradaEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testEntradaEstoque.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();
        entradaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entradaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();
        entradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntradaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = entradaEstoqueRepository.findAll().size();
        entradaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntradaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entradaEstoque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntradaEstoque in the database
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntradaEstoque() throws Exception {
        // Initialize the database
        entradaEstoqueRepository.saveAndFlush(entradaEstoque);

        int databaseSizeBeforeDelete = entradaEstoqueRepository.findAll().size();

        // Delete the entradaEstoque
        restEntradaEstoqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, entradaEstoque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntradaEstoque> entradaEstoqueList = entradaEstoqueRepository.findAll();
        assertThat(entradaEstoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
