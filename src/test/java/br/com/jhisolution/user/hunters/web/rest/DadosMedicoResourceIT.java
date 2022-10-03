package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.DadosMedico;
import br.com.jhisolution.user.hunters.domain.enumeration.Coracao;
import br.com.jhisolution.user.hunters.domain.enumeration.Pressao;
import br.com.jhisolution.user.hunters.repository.DadosMedicoRepository;
import br.com.jhisolution.user.hunters.service.DadosMedicoService;
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
 * Integration tests for the {@link DadosMedicoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DadosMedicoResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PESO = 1;
    private static final Integer UPDATED_PESO = 2;

    private static final Integer DEFAULT_ALTURA = 1;
    private static final Integer UPDATED_ALTURA = 2;

    private static final Pressao DEFAULT_PRESSAO = Pressao.BAIXA;
    private static final Pressao UPDATED_PRESSAO = Pressao.NORMAL;

    private static final Coracao DEFAULT_CORACAO = Coracao.NORMAL;
    private static final Coracao UPDATED_CORACAO = Coracao.ARTERIOSCLEROSE;

    private static final String DEFAULT_MEDICACAO = "AAAAAAAAAA";
    private static final String UPDATED_MEDICACAO = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dados-medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DadosMedicoRepository dadosMedicoRepository;

    @Mock
    private DadosMedicoRepository dadosMedicoRepositoryMock;

    @Mock
    private DadosMedicoService dadosMedicoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDadosMedicoMockMvc;

    private DadosMedico dadosMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosMedico createEntity(EntityManager em) {
        DadosMedico dadosMedico = new DadosMedico()
            .data(DEFAULT_DATA)
            .peso(DEFAULT_PESO)
            .altura(DEFAULT_ALTURA)
            .pressao(DEFAULT_PRESSAO)
            .coracao(DEFAULT_CORACAO)
            .medicacao(DEFAULT_MEDICACAO)
            .obs(DEFAULT_OBS);
        return dadosMedico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosMedico createUpdatedEntity(EntityManager em) {
        DadosMedico dadosMedico = new DadosMedico()
            .data(UPDATED_DATA)
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA)
            .pressao(UPDATED_PRESSAO)
            .coracao(UPDATED_CORACAO)
            .medicacao(UPDATED_MEDICACAO)
            .obs(UPDATED_OBS);
        return dadosMedico;
    }

    @BeforeEach
    public void initTest() {
        dadosMedico = createEntity(em);
    }

    @Test
    @Transactional
    void createDadosMedico() throws Exception {
        int databaseSizeBeforeCreate = dadosMedicoRepository.findAll().size();
        // Create the DadosMedico
        restDadosMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isCreated());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeCreate + 1);
        DadosMedico testDadosMedico = dadosMedicoList.get(dadosMedicoList.size() - 1);
        assertThat(testDadosMedico.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDadosMedico.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testDadosMedico.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDadosMedico.getPressao()).isEqualTo(DEFAULT_PRESSAO);
        assertThat(testDadosMedico.getCoracao()).isEqualTo(DEFAULT_CORACAO);
        assertThat(testDadosMedico.getMedicacao()).isEqualTo(DEFAULT_MEDICACAO);
        assertThat(testDadosMedico.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createDadosMedicoWithExistingId() throws Exception {
        // Create the DadosMedico with an existing ID
        dadosMedico.setId(1L);

        int databaseSizeBeforeCreate = dadosMedicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDadosMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isBadRequest());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosMedicoRepository.findAll().size();
        // set the field null
        dadosMedico.setData(null);

        // Create the DadosMedico, which fails.

        restDadosMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isBadRequest());

        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosMedicoRepository.findAll().size();
        // set the field null
        dadosMedico.setPeso(null);

        // Create the DadosMedico, which fails.

        restDadosMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isBadRequest());

        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAlturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosMedicoRepository.findAll().size();
        // set the field null
        dadosMedico.setAltura(null);

        // Create the DadosMedico, which fails.

        restDadosMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isBadRequest());

        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPressaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosMedicoRepository.findAll().size();
        // set the field null
        dadosMedico.setPressao(null);

        // Create the DadosMedico, which fails.

        restDadosMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isBadRequest());

        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosMedicoRepository.findAll().size();
        // set the field null
        dadosMedico.setCoracao(null);

        // Create the DadosMedico, which fails.

        restDadosMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isBadRequest());

        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDadosMedicos() throws Exception {
        // Initialize the database
        dadosMedicoRepository.saveAndFlush(dadosMedico);

        // Get all the dadosMedicoList
        restDadosMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dadosMedico.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].pressao").value(hasItem(DEFAULT_PRESSAO.toString())))
            .andExpect(jsonPath("$.[*].coracao").value(hasItem(DEFAULT_CORACAO.toString())))
            .andExpect(jsonPath("$.[*].medicacao").value(hasItem(DEFAULT_MEDICACAO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDadosMedicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(dadosMedicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDadosMedicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dadosMedicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDadosMedicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dadosMedicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDadosMedicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dadosMedicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDadosMedico() throws Exception {
        // Initialize the database
        dadosMedicoRepository.saveAndFlush(dadosMedico);

        // Get the dadosMedico
        restDadosMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, dadosMedico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dadosMedico.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA))
            .andExpect(jsonPath("$.pressao").value(DEFAULT_PRESSAO.toString()))
            .andExpect(jsonPath("$.coracao").value(DEFAULT_CORACAO.toString()))
            .andExpect(jsonPath("$.medicacao").value(DEFAULT_MEDICACAO))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingDadosMedico() throws Exception {
        // Get the dadosMedico
        restDadosMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDadosMedico() throws Exception {
        // Initialize the database
        dadosMedicoRepository.saveAndFlush(dadosMedico);

        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();

        // Update the dadosMedico
        DadosMedico updatedDadosMedico = dadosMedicoRepository.findById(dadosMedico.getId()).get();
        // Disconnect from session so that the updates on updatedDadosMedico are not directly saved in db
        em.detach(updatedDadosMedico);
        updatedDadosMedico
            .data(UPDATED_DATA)
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA)
            .pressao(UPDATED_PRESSAO)
            .coracao(UPDATED_CORACAO)
            .medicacao(UPDATED_MEDICACAO)
            .obs(UPDATED_OBS);

        restDadosMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDadosMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDadosMedico))
            )
            .andExpect(status().isOk());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
        DadosMedico testDadosMedico = dadosMedicoList.get(dadosMedicoList.size() - 1);
        assertThat(testDadosMedico.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDadosMedico.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testDadosMedico.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDadosMedico.getPressao()).isEqualTo(UPDATED_PRESSAO);
        assertThat(testDadosMedico.getCoracao()).isEqualTo(UPDATED_CORACAO);
        assertThat(testDadosMedico.getMedicacao()).isEqualTo(UPDATED_MEDICACAO);
        assertThat(testDadosMedico.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingDadosMedico() throws Exception {
        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();
        dadosMedico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dadosMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadosMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDadosMedico() throws Exception {
        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();
        dadosMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadosMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDadosMedico() throws Exception {
        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();
        dadosMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosMedicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosMedico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDadosMedicoWithPatch() throws Exception {
        // Initialize the database
        dadosMedicoRepository.saveAndFlush(dadosMedico);

        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();

        // Update the dadosMedico using partial update
        DadosMedico partialUpdatedDadosMedico = new DadosMedico();
        partialUpdatedDadosMedico.setId(dadosMedico.getId());

        partialUpdatedDadosMedico.peso(UPDATED_PESO).coracao(UPDATED_CORACAO).medicacao(UPDATED_MEDICACAO).obs(UPDATED_OBS);

        restDadosMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadosMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadosMedico))
            )
            .andExpect(status().isOk());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
        DadosMedico testDadosMedico = dadosMedicoList.get(dadosMedicoList.size() - 1);
        assertThat(testDadosMedico.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDadosMedico.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testDadosMedico.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDadosMedico.getPressao()).isEqualTo(DEFAULT_PRESSAO);
        assertThat(testDadosMedico.getCoracao()).isEqualTo(UPDATED_CORACAO);
        assertThat(testDadosMedico.getMedicacao()).isEqualTo(UPDATED_MEDICACAO);
        assertThat(testDadosMedico.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdateDadosMedicoWithPatch() throws Exception {
        // Initialize the database
        dadosMedicoRepository.saveAndFlush(dadosMedico);

        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();

        // Update the dadosMedico using partial update
        DadosMedico partialUpdatedDadosMedico = new DadosMedico();
        partialUpdatedDadosMedico.setId(dadosMedico.getId());

        partialUpdatedDadosMedico
            .data(UPDATED_DATA)
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA)
            .pressao(UPDATED_PRESSAO)
            .coracao(UPDATED_CORACAO)
            .medicacao(UPDATED_MEDICACAO)
            .obs(UPDATED_OBS);

        restDadosMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadosMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadosMedico))
            )
            .andExpect(status().isOk());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
        DadosMedico testDadosMedico = dadosMedicoList.get(dadosMedicoList.size() - 1);
        assertThat(testDadosMedico.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDadosMedico.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testDadosMedico.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDadosMedico.getPressao()).isEqualTo(UPDATED_PRESSAO);
        assertThat(testDadosMedico.getCoracao()).isEqualTo(UPDATED_CORACAO);
        assertThat(testDadosMedico.getMedicacao()).isEqualTo(UPDATED_MEDICACAO);
        assertThat(testDadosMedico.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingDadosMedico() throws Exception {
        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();
        dadosMedico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dadosMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadosMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDadosMedico() throws Exception {
        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();
        dadosMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadosMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDadosMedico() throws Exception {
        int databaseSizeBeforeUpdate = dadosMedicoRepository.findAll().size();
        dadosMedico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dadosMedico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadosMedico in the database
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDadosMedico() throws Exception {
        // Initialize the database
        dadosMedicoRepository.saveAndFlush(dadosMedico);

        int databaseSizeBeforeDelete = dadosMedicoRepository.findAll().size();

        // Delete the dadosMedico
        restDadosMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, dadosMedico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DadosMedico> dadosMedicoList = dadosMedicoRepository.findAll();
        assertThat(dadosMedicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
