package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.SaidaEstoque;
import br.com.jhisolution.user.hunters.repository.SaidaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.SaidaEstoqueService;
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
 * Integration tests for the {@link SaidaEstoqueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SaidaEstoqueResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QTDE = 1;
    private static final Integer UPDATED_QTDE = 2;

    private static final Float DEFAULT_VALOR_UNITARIO = 1F;
    private static final Float UPDATED_VALOR_UNITARIO = 2F;

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/saida-estoques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaidaEstoqueRepository saidaEstoqueRepository;

    @Mock
    private SaidaEstoqueRepository saidaEstoqueRepositoryMock;

    @Mock
    private SaidaEstoqueService saidaEstoqueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaidaEstoqueMockMvc;

    private SaidaEstoque saidaEstoque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaidaEstoque createEntity(EntityManager em) {
        SaidaEstoque saidaEstoque = new SaidaEstoque()
            .data(DEFAULT_DATA)
            .qtde(DEFAULT_QTDE)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .obs(DEFAULT_OBS);
        return saidaEstoque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaidaEstoque createUpdatedEntity(EntityManager em) {
        SaidaEstoque saidaEstoque = new SaidaEstoque()
            .data(UPDATED_DATA)
            .qtde(UPDATED_QTDE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .obs(UPDATED_OBS);
        return saidaEstoque;
    }

    @BeforeEach
    public void initTest() {
        saidaEstoque = createEntity(em);
    }

    @Test
    @Transactional
    void createSaidaEstoque() throws Exception {
        int databaseSizeBeforeCreate = saidaEstoqueRepository.findAll().size();
        // Create the SaidaEstoque
        restSaidaEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaEstoque)))
            .andExpect(status().isCreated());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeCreate + 1);
        SaidaEstoque testSaidaEstoque = saidaEstoqueList.get(saidaEstoqueList.size() - 1);
        assertThat(testSaidaEstoque.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testSaidaEstoque.getQtde()).isEqualTo(DEFAULT_QTDE);
        assertThat(testSaidaEstoque.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testSaidaEstoque.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createSaidaEstoqueWithExistingId() throws Exception {
        // Create the SaidaEstoque with an existing ID
        saidaEstoque.setId(1L);

        int databaseSizeBeforeCreate = saidaEstoqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaidaEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaEstoque)))
            .andExpect(status().isBadRequest());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = saidaEstoqueRepository.findAll().size();
        // set the field null
        saidaEstoque.setData(null);

        // Create the SaidaEstoque, which fails.

        restSaidaEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaEstoque)))
            .andExpect(status().isBadRequest());

        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQtdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = saidaEstoqueRepository.findAll().size();
        // set the field null
        saidaEstoque.setQtde(null);

        // Create the SaidaEstoque, which fails.

        restSaidaEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaEstoque)))
            .andExpect(status().isBadRequest());

        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = saidaEstoqueRepository.findAll().size();
        // set the field null
        saidaEstoque.setValorUnitario(null);

        // Create the SaidaEstoque, which fails.

        restSaidaEstoqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaEstoque)))
            .andExpect(status().isBadRequest());

        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSaidaEstoques() throws Exception {
        // Initialize the database
        saidaEstoqueRepository.saveAndFlush(saidaEstoque);

        // Get all the saidaEstoqueList
        restSaidaEstoqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saidaEstoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].qtde").value(hasItem(DEFAULT_QTDE)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSaidaEstoquesWithEagerRelationshipsIsEnabled() throws Exception {
        when(saidaEstoqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSaidaEstoqueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(saidaEstoqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSaidaEstoquesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(saidaEstoqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSaidaEstoqueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(saidaEstoqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSaidaEstoque() throws Exception {
        // Initialize the database
        saidaEstoqueRepository.saveAndFlush(saidaEstoque);

        // Get the saidaEstoque
        restSaidaEstoqueMockMvc
            .perform(get(ENTITY_API_URL_ID, saidaEstoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saidaEstoque.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.qtde").value(DEFAULT_QTDE))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO.doubleValue()))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingSaidaEstoque() throws Exception {
        // Get the saidaEstoque
        restSaidaEstoqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSaidaEstoque() throws Exception {
        // Initialize the database
        saidaEstoqueRepository.saveAndFlush(saidaEstoque);

        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();

        // Update the saidaEstoque
        SaidaEstoque updatedSaidaEstoque = saidaEstoqueRepository.findById(saidaEstoque.getId()).get();
        // Disconnect from session so that the updates on updatedSaidaEstoque are not directly saved in db
        em.detach(updatedSaidaEstoque);
        updatedSaidaEstoque.data(UPDATED_DATA).qtde(UPDATED_QTDE).valorUnitario(UPDATED_VALOR_UNITARIO).obs(UPDATED_OBS);

        restSaidaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSaidaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSaidaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        SaidaEstoque testSaidaEstoque = saidaEstoqueList.get(saidaEstoqueList.size() - 1);
        assertThat(testSaidaEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSaidaEstoque.getQtde()).isEqualTo(UPDATED_QTDE);
        assertThat(testSaidaEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testSaidaEstoque.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();
        saidaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaidaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saidaEstoque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();
        saidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaEstoqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();
        saidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaEstoqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saidaEstoque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaidaEstoqueWithPatch() throws Exception {
        // Initialize the database
        saidaEstoqueRepository.saveAndFlush(saidaEstoque);

        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();

        // Update the saidaEstoque using partial update
        SaidaEstoque partialUpdatedSaidaEstoque = new SaidaEstoque();
        partialUpdatedSaidaEstoque.setId(saidaEstoque.getId());

        partialUpdatedSaidaEstoque.data(UPDATED_DATA).valorUnitario(UPDATED_VALOR_UNITARIO);

        restSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaidaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaidaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        SaidaEstoque testSaidaEstoque = saidaEstoqueList.get(saidaEstoqueList.size() - 1);
        assertThat(testSaidaEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSaidaEstoque.getQtde()).isEqualTo(DEFAULT_QTDE);
        assertThat(testSaidaEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testSaidaEstoque.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void fullUpdateSaidaEstoqueWithPatch() throws Exception {
        // Initialize the database
        saidaEstoqueRepository.saveAndFlush(saidaEstoque);

        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();

        // Update the saidaEstoque using partial update
        SaidaEstoque partialUpdatedSaidaEstoque = new SaidaEstoque();
        partialUpdatedSaidaEstoque.setId(saidaEstoque.getId());

        partialUpdatedSaidaEstoque.data(UPDATED_DATA).qtde(UPDATED_QTDE).valorUnitario(UPDATED_VALOR_UNITARIO).obs(UPDATED_OBS);

        restSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaidaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaidaEstoque))
            )
            .andExpect(status().isOk());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
        SaidaEstoque testSaidaEstoque = saidaEstoqueList.get(saidaEstoqueList.size() - 1);
        assertThat(testSaidaEstoque.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSaidaEstoque.getQtde()).isEqualTo(UPDATED_QTDE);
        assertThat(testSaidaEstoque.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testSaidaEstoque.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();
        saidaEstoque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saidaEstoque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();
        saidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saidaEstoque))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaidaEstoque() throws Exception {
        int databaseSizeBeforeUpdate = saidaEstoqueRepository.findAll().size();
        saidaEstoque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaidaEstoqueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(saidaEstoque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaidaEstoque in the database
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaidaEstoque() throws Exception {
        // Initialize the database
        saidaEstoqueRepository.saveAndFlush(saidaEstoque);

        int databaseSizeBeforeDelete = saidaEstoqueRepository.findAll().size();

        // Delete the saidaEstoque
        restSaidaEstoqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, saidaEstoque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SaidaEstoque> saidaEstoqueList = saidaEstoqueRepository.findAll();
        assertThat(saidaEstoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
