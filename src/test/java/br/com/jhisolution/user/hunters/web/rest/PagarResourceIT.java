package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.domain.enumeration.StatusContaPagar;
import br.com.jhisolution.user.hunters.repository.PagarRepository;
import br.com.jhisolution.user.hunters.service.PagarService;
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
 * Integration tests for the {@link PagarResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PagarResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final StatusContaPagar DEFAULT_STATUS = StatusContaPagar.VENCIDA;
    private static final StatusContaPagar UPDATED_STATUS = StatusContaPagar.PAGA;

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pagars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PagarRepository pagarRepository;

    @Mock
    private PagarRepository pagarRepositoryMock;

    @Mock
    private PagarService pagarServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPagarMockMvc;

    private Pagar pagar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagar createEntity(EntityManager em) {
        Pagar pagar = new Pagar().data(DEFAULT_DATA).valor(DEFAULT_VALOR).status(DEFAULT_STATUS).obs(DEFAULT_OBS);
        return pagar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagar createUpdatedEntity(EntityManager em) {
        Pagar pagar = new Pagar().data(UPDATED_DATA).valor(UPDATED_VALOR).status(UPDATED_STATUS).obs(UPDATED_OBS);
        return pagar;
    }

    @BeforeEach
    public void initTest() {
        pagar = createEntity(em);
    }

    @Test
    @Transactional
    void createPagar() throws Exception {
        int databaseSizeBeforeCreate = pagarRepository.findAll().size();
        // Create the Pagar
        restPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagar)))
            .andExpect(status().isCreated());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeCreate + 1);
        Pagar testPagar = pagarList.get(pagarList.size() - 1);
        assertThat(testPagar.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPagar.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testPagar.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPagar.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createPagarWithExistingId() throws Exception {
        // Create the Pagar with an existing ID
        pagar.setId(1L);

        int databaseSizeBeforeCreate = pagarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagar)))
            .andExpect(status().isBadRequest());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagarRepository.findAll().size();
        // set the field null
        pagar.setData(null);

        // Create the Pagar, which fails.

        restPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagar)))
            .andExpect(status().isBadRequest());

        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagarRepository.findAll().size();
        // set the field null
        pagar.setValor(null);

        // Create the Pagar, which fails.

        restPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagar)))
            .andExpect(status().isBadRequest());

        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPagars() throws Exception {
        // Initialize the database
        pagarRepository.saveAndFlush(pagar);

        // Get all the pagarList
        restPagarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagar.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPagarsWithEagerRelationshipsIsEnabled() throws Exception {
        when(pagarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPagarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pagarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPagarsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pagarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPagarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pagarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPagar() throws Exception {
        // Initialize the database
        pagarRepository.saveAndFlush(pagar);

        // Get the pagar
        restPagarMockMvc
            .perform(get(ENTITY_API_URL_ID, pagar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pagar.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingPagar() throws Exception {
        // Get the pagar
        restPagarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPagar() throws Exception {
        // Initialize the database
        pagarRepository.saveAndFlush(pagar);

        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();

        // Update the pagar
        Pagar updatedPagar = pagarRepository.findById(pagar.getId()).get();
        // Disconnect from session so that the updates on updatedPagar are not directly saved in db
        em.detach(updatedPagar);
        updatedPagar.data(UPDATED_DATA).valor(UPDATED_VALOR).status(UPDATED_STATUS).obs(UPDATED_OBS);

        restPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPagar))
            )
            .andExpect(status().isOk());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
        Pagar testPagar = pagarList.get(pagarList.size() - 1);
        assertThat(testPagar.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPagar.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testPagar.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPagar.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingPagar() throws Exception {
        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();
        pagar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPagar() throws Exception {
        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();
        pagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPagar() throws Exception {
        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();
        pagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePagarWithPatch() throws Exception {
        // Initialize the database
        pagarRepository.saveAndFlush(pagar);

        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();

        // Update the pagar using partial update
        Pagar partialUpdatedPagar = new Pagar();
        partialUpdatedPagar.setId(pagar.getId());

        partialUpdatedPagar.data(UPDATED_DATA).valor(UPDATED_VALOR).obs(UPDATED_OBS);

        restPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPagar))
            )
            .andExpect(status().isOk());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
        Pagar testPagar = pagarList.get(pagarList.size() - 1);
        assertThat(testPagar.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPagar.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testPagar.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPagar.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdatePagarWithPatch() throws Exception {
        // Initialize the database
        pagarRepository.saveAndFlush(pagar);

        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();

        // Update the pagar using partial update
        Pagar partialUpdatedPagar = new Pagar();
        partialUpdatedPagar.setId(pagar.getId());

        partialUpdatedPagar.data(UPDATED_DATA).valor(UPDATED_VALOR).status(UPDATED_STATUS).obs(UPDATED_OBS);

        restPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPagar))
            )
            .andExpect(status().isOk());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
        Pagar testPagar = pagarList.get(pagarList.size() - 1);
        assertThat(testPagar.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPagar.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testPagar.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPagar.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingPagar() throws Exception {
        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();
        pagar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPagar() throws Exception {
        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();
        pagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPagar() throws Exception {
        int databaseSizeBeforeUpdate = pagarRepository.findAll().size();
        pagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pagar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pagar in the database
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePagar() throws Exception {
        // Initialize the database
        pagarRepository.saveAndFlush(pagar);

        int databaseSizeBeforeDelete = pagarRepository.findAll().size();

        // Delete the pagar
        restPagarMockMvc
            .perform(delete(ENTITY_API_URL_ID, pagar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pagar> pagarList = pagarRepository.findAll();
        assertThat(pagarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
