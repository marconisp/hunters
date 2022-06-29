package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.EstadoCivil;
import br.com.jhisolution.user.hunters.domain.Raca;
import br.com.jhisolution.user.hunters.domain.Religiao;
import br.com.jhisolution.user.hunters.domain.TipoPessoa;
import br.com.jhisolution.user.hunters.repository.DadosPessoaisRepository;
import br.com.jhisolution.user.hunters.service.DadosPessoaisService;
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
 * Integration tests for the {@link DadosPessoaisResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DadosPessoaisResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRE_NOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRE_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_PAI = "AAAAAAAAAA";
    private static final String UPDATED_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_MAE = "AAAAAAAAAA";
    private static final String UPDATED_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_WHATSAPP = "AAAAAAAAAA";
    private static final String UPDATED_WHATSAPP = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dados-pessoais";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DadosPessoaisRepository dadosPessoaisRepository;

    @Mock
    private DadosPessoaisRepository dadosPessoaisRepositoryMock;

    @Mock
    private DadosPessoaisService dadosPessoaisServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDadosPessoaisMockMvc;

    private DadosPessoais dadosPessoais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosPessoais createEntity(EntityManager em) {
        DadosPessoais dadosPessoais = new DadosPessoais()
            .nome(DEFAULT_NOME)
            .sobreNome(DEFAULT_SOBRE_NOME)
            .pai(DEFAULT_PAI)
            .mae(DEFAULT_MAE)
            .telefone(DEFAULT_TELEFONE)
            .celular(DEFAULT_CELULAR)
            .whatsapp(DEFAULT_WHATSAPP)
            .email(DEFAULT_EMAIL);
        // Add required entity
        TipoPessoa tipoPessoa;
        if (TestUtil.findAll(em, TipoPessoa.class).isEmpty()) {
            tipoPessoa = TipoPessoaResourceIT.createEntity(em);
            em.persist(tipoPessoa);
            em.flush();
        } else {
            tipoPessoa = TestUtil.findAll(em, TipoPessoa.class).get(0);
        }
        dadosPessoais.setTipoPessoa(tipoPessoa);
        // Add required entity
        EstadoCivil estadoCivil;
        if (TestUtil.findAll(em, EstadoCivil.class).isEmpty()) {
            estadoCivil = EstadoCivilResourceIT.createEntity(em);
            em.persist(estadoCivil);
            em.flush();
        } else {
            estadoCivil = TestUtil.findAll(em, EstadoCivil.class).get(0);
        }
        dadosPessoais.setEstadoCivil(estadoCivil);
        // Add required entity
        Raca raca;
        if (TestUtil.findAll(em, Raca.class).isEmpty()) {
            raca = RacaResourceIT.createEntity(em);
            em.persist(raca);
            em.flush();
        } else {
            raca = TestUtil.findAll(em, Raca.class).get(0);
        }
        dadosPessoais.setRaca(raca);
        // Add required entity
        Religiao religiao;
        if (TestUtil.findAll(em, Religiao.class).isEmpty()) {
            religiao = ReligiaoResourceIT.createEntity(em);
            em.persist(religiao);
            em.flush();
        } else {
            religiao = TestUtil.findAll(em, Religiao.class).get(0);
        }
        dadosPessoais.setReligiao(religiao);
        return dadosPessoais;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosPessoais createUpdatedEntity(EntityManager em) {
        DadosPessoais dadosPessoais = new DadosPessoais()
            .nome(UPDATED_NOME)
            .sobreNome(UPDATED_SOBRE_NOME)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .telefone(UPDATED_TELEFONE)
            .celular(UPDATED_CELULAR)
            .whatsapp(UPDATED_WHATSAPP)
            .email(UPDATED_EMAIL);
        // Add required entity
        TipoPessoa tipoPessoa;
        if (TestUtil.findAll(em, TipoPessoa.class).isEmpty()) {
            tipoPessoa = TipoPessoaResourceIT.createUpdatedEntity(em);
            em.persist(tipoPessoa);
            em.flush();
        } else {
            tipoPessoa = TestUtil.findAll(em, TipoPessoa.class).get(0);
        }
        dadosPessoais.setTipoPessoa(tipoPessoa);
        // Add required entity
        EstadoCivil estadoCivil;
        if (TestUtil.findAll(em, EstadoCivil.class).isEmpty()) {
            estadoCivil = EstadoCivilResourceIT.createUpdatedEntity(em);
            em.persist(estadoCivil);
            em.flush();
        } else {
            estadoCivil = TestUtil.findAll(em, EstadoCivil.class).get(0);
        }
        dadosPessoais.setEstadoCivil(estadoCivil);
        // Add required entity
        Raca raca;
        if (TestUtil.findAll(em, Raca.class).isEmpty()) {
            raca = RacaResourceIT.createUpdatedEntity(em);
            em.persist(raca);
            em.flush();
        } else {
            raca = TestUtil.findAll(em, Raca.class).get(0);
        }
        dadosPessoais.setRaca(raca);
        // Add required entity
        Religiao religiao;
        if (TestUtil.findAll(em, Religiao.class).isEmpty()) {
            religiao = ReligiaoResourceIT.createUpdatedEntity(em);
            em.persist(religiao);
            em.flush();
        } else {
            religiao = TestUtil.findAll(em, Religiao.class).get(0);
        }
        dadosPessoais.setReligiao(religiao);
        return dadosPessoais;
    }

    @BeforeEach
    public void initTest() {
        dadosPessoais = createEntity(em);
    }

    @Test
    @Transactional
    void createDadosPessoais() throws Exception {
        int databaseSizeBeforeCreate = dadosPessoaisRepository.findAll().size();
        // Create the DadosPessoais
        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isCreated());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeCreate + 1);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDadosPessoais.getSobreNome()).isEqualTo(DEFAULT_SOBRE_NOME);
        assertThat(testDadosPessoais.getPai()).isEqualTo(DEFAULT_PAI);
        assertThat(testDadosPessoais.getMae()).isEqualTo(DEFAULT_MAE);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testDadosPessoais.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testDadosPessoais.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createDadosPessoaisWithExistingId() throws Exception {
        // Create the DadosPessoais with an existing ID
        dadosPessoais.setId(1L);

        int databaseSizeBeforeCreate = dadosPessoaisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosPessoaisRepository.findAll().size();
        // set the field null
        dadosPessoais.setNome(null);

        // Create the DadosPessoais, which fails.

        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isBadRequest());

        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSobreNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosPessoaisRepository.findAll().size();
        // set the field null
        dadosPessoais.setSobreNome(null);

        // Create the DadosPessoais, which fails.

        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isBadRequest());

        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosPessoaisRepository.findAll().size();
        // set the field null
        dadosPessoais.setMae(null);

        // Create the DadosPessoais, which fails.

        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isBadRequest());

        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosPessoaisRepository.findAll().size();
        // set the field null
        dadosPessoais.setCelular(null);

        // Create the DadosPessoais, which fails.

        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isBadRequest());

        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = dadosPessoaisRepository.findAll().size();
        // set the field null
        dadosPessoais.setEmail(null);

        // Create the DadosPessoais, which fails.

        restDadosPessoaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isBadRequest());

        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    //@Transactional
    void getAllDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        // Get all the dadosPessoaisList
        restDadosPessoaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dadosPessoais.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobreNome").value(hasItem(DEFAULT_SOBRE_NOME)))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDadosPessoaisWithEagerRelationshipsIsEnabled() throws Exception {
        when(dadosPessoaisServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDadosPessoaisMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dadosPessoaisServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDadosPessoaisWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dadosPessoaisServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDadosPessoaisMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dadosPessoaisServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        // Get the dadosPessoais
        restDadosPessoaisMockMvc
            .perform(get(ENTITY_API_URL_ID, dadosPessoais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dadosPessoais.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sobreNome").value(DEFAULT_SOBRE_NOME))
            .andExpect(jsonPath("$.pai").value(DEFAULT_PAI))
            .andExpect(jsonPath("$.mae").value(DEFAULT_MAE))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.whatsapp").value(DEFAULT_WHATSAPP))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingDadosPessoais() throws Exception {
        // Get the dadosPessoais
        restDadosPessoaisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();

        // Update the dadosPessoais
        DadosPessoais updatedDadosPessoais = dadosPessoaisRepository.findById(dadosPessoais.getId()).get();
        // Disconnect from session so that the updates on updatedDadosPessoais are not directly saved in db
        em.detach(updatedDadosPessoais);
        updatedDadosPessoais
            .nome(UPDATED_NOME)
            .sobreNome(UPDATED_SOBRE_NOME)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .telefone(UPDATED_TELEFONE)
            .celular(UPDATED_CELULAR)
            .whatsapp(UPDATED_WHATSAPP)
            .email(UPDATED_EMAIL);

        restDadosPessoaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDadosPessoais.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDadosPessoais))
            )
            .andExpect(status().isOk());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDadosPessoais.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testDadosPessoais.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDadosPessoais.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testDadosPessoais.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testDadosPessoais.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dadosPessoais.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadosPessoais)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDadosPessoaisWithPatch() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();

        // Update the dadosPessoais using partial update
        DadosPessoais partialUpdatedDadosPessoais = new DadosPessoais();
        partialUpdatedDadosPessoais.setId(dadosPessoais.getId());

        partialUpdatedDadosPessoais.nome(UPDATED_NOME).mae(UPDATED_MAE).celular(UPDATED_CELULAR);

        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadosPessoais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadosPessoais))
            )
            .andExpect(status().isOk());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDadosPessoais.getSobreNome()).isEqualTo(DEFAULT_SOBRE_NOME);
        assertThat(testDadosPessoais.getPai()).isEqualTo(DEFAULT_PAI);
        assertThat(testDadosPessoais.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testDadosPessoais.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testDadosPessoais.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateDadosPessoaisWithPatch() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();

        // Update the dadosPessoais using partial update
        DadosPessoais partialUpdatedDadosPessoais = new DadosPessoais();
        partialUpdatedDadosPessoais.setId(dadosPessoais.getId());

        partialUpdatedDadosPessoais
            .nome(UPDATED_NOME)
            .sobreNome(UPDATED_SOBRE_NOME)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .telefone(UPDATED_TELEFONE)
            .celular(UPDATED_CELULAR)
            .whatsapp(UPDATED_WHATSAPP)
            .email(UPDATED_EMAIL);

        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadosPessoais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadosPessoais))
            )
            .andExpect(status().isOk());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
        DadosPessoais testDadosPessoais = dadosPessoaisList.get(dadosPessoaisList.size() - 1);
        assertThat(testDadosPessoais.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDadosPessoais.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testDadosPessoais.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDadosPessoais.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDadosPessoais.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testDadosPessoais.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testDadosPessoais.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testDadosPessoais.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dadosPessoais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDadosPessoais() throws Exception {
        int databaseSizeBeforeUpdate = dadosPessoaisRepository.findAll().size();
        dadosPessoais.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadosPessoaisMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dadosPessoais))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadosPessoais in the database
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDadosPessoais() throws Exception {
        // Initialize the database
        dadosPessoaisRepository.saveAndFlush(dadosPessoais);

        int databaseSizeBeforeDelete = dadosPessoaisRepository.findAll().size();

        // Delete the dadosPessoais
        restDadosPessoaisMockMvc
            .perform(delete(ENTITY_API_URL_ID, dadosPessoais.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DadosPessoais> dadosPessoaisList = dadosPessoaisRepository.findAll();
        assertThat(dadosPessoaisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
