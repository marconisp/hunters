package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Receber;
import br.com.jhisolution.user.hunters.domain.enumeration.StatusContaReceber;
import br.com.jhisolution.user.hunters.repository.ReceberRepository;
import br.com.jhisolution.user.hunters.service.ReceberService;
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
 * Integration tests for the {@link ReceberResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReceberResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final StatusContaReceber DEFAULT_STATUS = StatusContaReceber.VENCIDA;
    private static final StatusContaReceber UPDATED_STATUS = StatusContaReceber.RECEBIDA;

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recebers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReceberRepository receberRepository;

    @Mock
    private ReceberRepository receberRepositoryMock;

    @Mock
    private ReceberService receberServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceberMockMvc;

    private Receber receber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receber createEntity(EntityManager em) {
        Receber receber = new Receber().data(DEFAULT_DATA).valor(DEFAULT_VALOR).status(DEFAULT_STATUS).obs(DEFAULT_OBS);
        return receber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receber createUpdatedEntity(EntityManager em) {
        Receber receber = new Receber().data(UPDATED_DATA).valor(UPDATED_VALOR).status(UPDATED_STATUS).obs(UPDATED_OBS);
        return receber;
    }

    @BeforeEach
    public void initTest() {
        receber = createEntity(em);
    }

    @Test
    @Transactional
    void createReceber() throws Exception {
        int databaseSizeBeforeCreate = receberRepository.findAll().size();
        // Create the Receber
        restReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receber)))
            .andExpect(status().isCreated());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeCreate + 1);
        Receber testReceber = receberList.get(receberList.size() - 1);
        assertThat(testReceber.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testReceber.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testReceber.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReceber.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createReceberWithExistingId() throws Exception {
        // Create the Receber with an existing ID
        receber.setId(1L);

        int databaseSizeBeforeCreate = receberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receber)))
            .andExpect(status().isBadRequest());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = receberRepository.findAll().size();
        // set the field null
        receber.setData(null);

        // Create the Receber, which fails.

        restReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receber)))
            .andExpect(status().isBadRequest());

        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = receberRepository.findAll().size();
        // set the field null
        receber.setValor(null);

        // Create the Receber, which fails.

        restReceberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receber)))
            .andExpect(status().isBadRequest());

        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecebers() throws Exception {
        // Initialize the database
        receberRepository.saveAndFlush(receber);

        // Get all the receberList
        restReceberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receber.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecebersWithEagerRelationshipsIsEnabled() throws Exception {
        when(receberServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReceberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(receberServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecebersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(receberServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReceberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(receberServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getReceber() throws Exception {
        // Initialize the database
        receberRepository.saveAndFlush(receber);

        // Get the receber
        restReceberMockMvc
            .perform(get(ENTITY_API_URL_ID, receber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receber.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingReceber() throws Exception {
        // Get the receber
        restReceberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReceber() throws Exception {
        // Initialize the database
        receberRepository.saveAndFlush(receber);

        int databaseSizeBeforeUpdate = receberRepository.findAll().size();

        // Update the receber
        Receber updatedReceber = receberRepository.findById(receber.getId()).get();
        // Disconnect from session so that the updates on updatedReceber are not directly saved in db
        em.detach(updatedReceber);
        updatedReceber.data(UPDATED_DATA).valor(UPDATED_VALOR).status(UPDATED_STATUS).obs(UPDATED_OBS);

        restReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReceber))
            )
            .andExpect(status().isOk());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
        Receber testReceber = receberList.get(receberList.size() - 1);
        assertThat(testReceber.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testReceber.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testReceber.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReceber.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingReceber() throws Exception {
        int databaseSizeBeforeUpdate = receberRepository.findAll().size();
        receber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receber))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceber() throws Exception {
        int databaseSizeBeforeUpdate = receberRepository.findAll().size();
        receber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receber))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceber() throws Exception {
        int databaseSizeBeforeUpdate = receberRepository.findAll().size();
        receber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receber)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceberWithPatch() throws Exception {
        // Initialize the database
        receberRepository.saveAndFlush(receber);

        int databaseSizeBeforeUpdate = receberRepository.findAll().size();

        // Update the receber using partial update
        Receber partialUpdatedReceber = new Receber();
        partialUpdatedReceber.setId(receber.getId());

        partialUpdatedReceber.valor(UPDATED_VALOR);

        restReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceber))
            )
            .andExpect(status().isOk());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
        Receber testReceber = receberList.get(receberList.size() - 1);
        assertThat(testReceber.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testReceber.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testReceber.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReceber.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void fullUpdateReceberWithPatch() throws Exception {
        // Initialize the database
        receberRepository.saveAndFlush(receber);

        int databaseSizeBeforeUpdate = receberRepository.findAll().size();

        // Update the receber using partial update
        Receber partialUpdatedReceber = new Receber();
        partialUpdatedReceber.setId(receber.getId());

        partialUpdatedReceber.data(UPDATED_DATA).valor(UPDATED_VALOR).status(UPDATED_STATUS).obs(UPDATED_OBS);

        restReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceber))
            )
            .andExpect(status().isOk());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
        Receber testReceber = receberList.get(receberList.size() - 1);
        assertThat(testReceber.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testReceber.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testReceber.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReceber.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingReceber() throws Exception {
        int databaseSizeBeforeUpdate = receberRepository.findAll().size();
        receber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receber))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceber() throws Exception {
        int databaseSizeBeforeUpdate = receberRepository.findAll().size();
        receber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receber))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceber() throws Exception {
        int databaseSizeBeforeUpdate = receberRepository.findAll().size();
        receber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceberMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(receber)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receber in the database
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceber() throws Exception {
        // Initialize the database
        receberRepository.saveAndFlush(receber);

        int databaseSizeBeforeDelete = receberRepository.findAll().size();

        // Delete the receber
        restReceberMockMvc
            .perform(delete(ENTITY_API_URL_ID, receber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receber> receberList = receberRepository.findAll();
        assertThat(receberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
