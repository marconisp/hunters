package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Estoque;
import br.com.jhisolution.user.hunters.repository.EstoqueRepository;
import br.com.jhisolution.user.hunters.service.EstoqueService;
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
 * Integration tests for the {@link EstoqueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EstoqueResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QTDE = 1;
    private static final Integer UPDATED_QTDE = 2;

    private static final Float DEFAULT_VALOR_UNITARIO = 1F;
    private static final Float UPDATED_VALOR_UNITARIO = 2F;

    private static final Float DEFAULT_VALOR_TOTAL = 1F;
    private static final Float UPDATED_VALOR_TOTAL = 2F;

    private static final String ENTITY_API_URL = "/api/estoques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Mock
    private EstoqueRepository estoqueRepositoryMock;

    @Mock
    private EstoqueService estoqueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstoqueMockMvc;

    private Estoque estoque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estoque createEntity(EntityManager em) {
        Estoque estoque = new Estoque()
            .data(DEFAULT_DATA)
            .qtde(DEFAULT_QTDE)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .valorTotal(DEFAULT_VALOR_TOTAL);
        return estoque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estoque createUpdatedEntity(EntityManager em) {
        Estoque estoque = new Estoque()
            .data(UPDATED_DATA)
            .qtde(UPDATED_QTDE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valorTotal(UPDATED_VALOR_TOTAL);
        return estoque;
    }

    @BeforeEach
    public void initTest() {
        estoque = createEntity(em);
    }

    @Test
    @Transactional
    void createEstoque() throws Exception {
        int databaseSizeBeforeCreate = estoqueRepository.findAll().size();
        // Create the Estoque
        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isCreated());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeCreate + 1);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testEstoque.getQtde()).isEqualTo(DEFAULT_QTDE);
        assertThat(testEstoque.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testEstoque.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void createEstoqueWithExistingId() throws Exception {
        // Create the Estoque with an existing ID
        estoque.setId(1L);

        int databaseSizeBeforeCreate = estoqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueRepository.findAll().size();
        // set the field null
        estoque.setData(null);

        // Create the Estoque, which fails.

        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isBadRequest());

        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQtdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueRepository.findAll().size();
        // set the field null
        estoque.setQtde(null);

        // Create the Estoque, which fails.

        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isBadRequest());

        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueRepository.findAll().size();
        // set the field null
        estoque.setValorUnitario(null);

        // Create the Estoque, which fails.

        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isBadRequest());

        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueRepository.findAll().size();
        // set the field null
        estoque.setValorTotal(null);

        // Create the Estoque, which fails.

        restEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isBadRequest());

        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstoques() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].qtde").value(hasItem(DEFAULT_QTDE)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstoquesWithEagerRelationshipsIsEnabled() throws Exception {
        when(estoqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstoqueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(estoqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstoquesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(estoqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstoqueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(estoqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEstoque() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get the estoque
        restEstoqueMockMvc
            .perform(get(ENTITY_API_URL_ID, estoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estoque.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.qtde").value(DEFAULT_QTDE))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO.doubleValue()))
            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingEstoque() throws Exception {
        // Get the estoque
        restEstoqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstoque() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Update the estoque
        Estoque updatedEstoque = estoqueRepository.findById(estoque.getId()).get();
        // Disconnect from session so that the updates on updatedEstoque are not directly saved in db
        em.detach(updatedEstoque);
        updatedEstoque.data(UPDATED_DATA).qtde(UPDATED_QTDE).valorUnitario(UPDATED_VALOR_UNITARIO).valorTotal(UPDATED_VALOR_TOTAL);

        restEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstoque))
            )
            .andExpect(status().isOk());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEstoque.getQtde()).isEqualTo(UPDATED_QTDE);
        assertThat(testEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testEstoque.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstoqueWithPatch() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Update the estoque using partial update
        Estoque partialUpdatedEstoque = new Estoque();
        partialUpdatedEstoque.setId(estoque.getId());

        partialUpdatedEstoque.data(UPDATED_DATA).valorUnitario(UPDATED_VALOR_UNITARIO).valorTotal(UPDATED_VALOR_TOTAL);

        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstoque))
            )
            .andExpect(status().isOk());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEstoque.getQtde()).isEqualTo(DEFAULT_QTDE);
        assertThat(testEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testEstoque.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateEstoqueWithPatch() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Update the estoque using partial update
        Estoque partialUpdatedEstoque = new Estoque();
        partialUpdatedEstoque.setId(estoque.getId());

        partialUpdatedEstoque.data(UPDATED_DATA).qtde(UPDATED_QTDE).valorUnitario(UPDATED_VALOR_UNITARIO).valorTotal(UPDATED_VALOR_TOTAL);

        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstoque))
            )
            .andExpect(status().isOk());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testEstoque.getQtde()).isEqualTo(UPDATED_QTDE);
        assertThat(testEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testEstoque.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();
        estoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstoqueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstoque() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        int databaseSizeBeforeDelete = estoqueRepository.findAll().size();

        // Delete the estoque
        restEstoqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, estoque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
