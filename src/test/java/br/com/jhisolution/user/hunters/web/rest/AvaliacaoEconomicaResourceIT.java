package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.AvaliacaoEconomica;
import br.com.jhisolution.user.hunters.domain.enumeration.AssitenciaMedica;
import br.com.jhisolution.user.hunters.domain.enumeration.Escola;
import br.com.jhisolution.user.hunters.domain.enumeration.Escola;
import br.com.jhisolution.user.hunters.domain.enumeration.FamiliaExiste;
import br.com.jhisolution.user.hunters.domain.enumeration.Moradia;
import br.com.jhisolution.user.hunters.domain.enumeration.Pais;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SituacaoMoradia;
import br.com.jhisolution.user.hunters.domain.enumeration.TipoMoradia;
import br.com.jhisolution.user.hunters.repository.AvaliacaoEconomicaRepository;
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
 * Integration tests for the {@link AvaliacaoEconomicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvaliacaoEconomicaResourceIT {

    private static final SimNao DEFAULT_TRABALHO_OU_ESTAGIO = SimNao.SIM;
    private static final SimNao UPDATED_TRABALHO_OU_ESTAGIO = SimNao.NAO;

    private static final SimNao DEFAULT_VINCULO_EMPREGATICIO = SimNao.SIM;
    private static final SimNao UPDATED_VINCULO_EMPREGATICIO = SimNao.NAO;

    private static final String DEFAULT_CARGO_FUNCAO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO_FUNCAO = "BBBBBBBBBB";

    private static final SimNao DEFAULT_CONTRIBUI_RENDA_FAMILIAR = SimNao.SIM;
    private static final SimNao UPDATED_CONTRIBUI_RENDA_FAMILIAR = SimNao.NAO;

    private static final SimNao DEFAULT_APOIO_FINANCEIRO_FAMILIAR = SimNao.SIM;
    private static final SimNao UPDATED_APOIO_FINANCEIRO_FAMILIAR = SimNao.NAO;

    private static final SimNao DEFAULT_ESTUDA_ATUALMENTE = SimNao.SIM;
    private static final SimNao UPDATED_ESTUDA_ATUALMENTE = SimNao.NAO;

    private static final Escola DEFAULT_ESCOLA_ATUAL = Escola.PUBLICA;
    private static final Escola UPDATED_ESCOLA_ATUAL = Escola.PARTICULAR;

    private static final SimNao DEFAULT_ESTUDOU_ANTERIORMENTE = SimNao.SIM;
    private static final SimNao UPDATED_ESTUDOU_ANTERIORMENTE = SimNao.NAO;

    private static final Escola DEFAULT_ESCOLA_ANTERIOR = Escola.PUBLICA;
    private static final Escola UPDATED_ESCOLA_ANTERIOR = Escola.PARTICULAR;

    private static final SimNao DEFAULT_CONCLUI_ANO_ESCOLAR_ANTERIOR = SimNao.SIM;
    private static final SimNao UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR = SimNao.NAO;

    private static final SimNao DEFAULT_REPETENTE = SimNao.SIM;
    private static final SimNao UPDATED_REPETENTE = SimNao.NAO;

    private static final SimNao DEFAULT_DIFICULDADE_APRENDIZADO = SimNao.SIM;
    private static final SimNao UPDATED_DIFICULDADE_APRENDIZADO = SimNao.NAO;

    private static final String DEFAULT_DIFICULDADE_DISCIPLINA = "AAAAAAAAAA";
    private static final String UPDATED_DIFICULDADE_DISCIPLINA = "BBBBBBBBBB";

    private static final Moradia DEFAULT_MORA_COM = Moradia.PAI;
    private static final Moradia UPDATED_MORA_COM = Moradia.MAE;

    private static final Pais DEFAULT_PAIS = Pais.CASADOS;
    private static final Pais UPDATED_PAIS = Pais.SEPARADOS;

    private static final SituacaoMoradia DEFAULT_SITUACAO_MORADIA = SituacaoMoradia.PROPRIO;
    private static final SituacaoMoradia UPDATED_SITUACAO_MORADIA = SituacaoMoradia.FINANCIADO;

    private static final TipoMoradia DEFAULT_TIPO_MORADIA = TipoMoradia.CASA;
    private static final TipoMoradia UPDATED_TIPO_MORADIA = TipoMoradia.APARTAMENTO;

    private static final SimNao DEFAULT_RECEBE_BENEFICIO_GOVERNO = SimNao.SIM;
    private static final SimNao UPDATED_RECEBE_BENEFICIO_GOVERNO = SimNao.NAO;

    private static final String DEFAULT_TIPO_BENEFICIO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_BENEFICIO = "BBBBBBBBBB";

    private static final FamiliaExiste DEFAULT_FAMILIA_EXISTE = FamiliaExiste.ALCOOLISMO;
    private static final FamiliaExiste UPDATED_FAMILIA_EXISTE = FamiliaExiste.DROGADICAO;

    private static final AssitenciaMedica DEFAULT_ASSITENCIA_MEDICA = AssitenciaMedica.PUBLICA;
    private static final AssitenciaMedica UPDATED_ASSITENCIA_MEDICA = AssitenciaMedica.PRIVADA;

    private static final SimNao DEFAULT_TEM_ALERGIA = SimNao.SIM;
    private static final SimNao UPDATED_TEM_ALERGIA = SimNao.NAO;

    private static final SimNao DEFAULT_TEM_PROBLEMA_SAUDE = SimNao.SIM;
    private static final SimNao UPDATED_TEM_PROBLEMA_SAUDE = SimNao.NAO;

    private static final SimNao DEFAULT_TOMA_MEDICAMENTO = SimNao.SIM;
    private static final SimNao UPDATED_TOMA_MEDICAMENTO = SimNao.NAO;

    private static final SimNao DEFAULT_TEVE_FRATURA = SimNao.SIM;
    private static final SimNao UPDATED_TEVE_FRATURA = SimNao.NAO;

    private static final SimNao DEFAULT_TEVE_CIRURGIA = SimNao.SIM;
    private static final SimNao UPDATED_TEVE_CIRURGIA = SimNao.NAO;

    private static final SimNao DEFAULT_TEM_DEFICIENCIA = SimNao.SIM;
    private static final SimNao UPDATED_TEM_DEFICIENCIA = SimNao.NAO;

    private static final SimNao DEFAULT_TEM_ACOMPANHAMENTO_MEDICO = SimNao.SIM;
    private static final SimNao UPDATED_TEM_ACOMPANHAMENTO_MEDICO = SimNao.NAO;

    private static final String ENTITY_API_URL = "/api/avaliacao-economicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvaliacaoEconomicaRepository avaliacaoEconomicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvaliacaoEconomicaMockMvc;

    private AvaliacaoEconomica avaliacaoEconomica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvaliacaoEconomica createEntity(EntityManager em) {
        AvaliacaoEconomica avaliacaoEconomica = new AvaliacaoEconomica()
            .trabalhoOuEstagio(DEFAULT_TRABALHO_OU_ESTAGIO)
            .vinculoEmpregaticio(DEFAULT_VINCULO_EMPREGATICIO)
            .cargoFuncao(DEFAULT_CARGO_FUNCAO)
            .contribuiRendaFamiliar(DEFAULT_CONTRIBUI_RENDA_FAMILIAR)
            .apoioFinanceiroFamiliar(DEFAULT_APOIO_FINANCEIRO_FAMILIAR)
            .estudaAtualmente(DEFAULT_ESTUDA_ATUALMENTE)
            .escolaAtual(DEFAULT_ESCOLA_ATUAL)
            .estudouAnteriormente(DEFAULT_ESTUDOU_ANTERIORMENTE)
            .escolaAnterior(DEFAULT_ESCOLA_ANTERIOR)
            .concluiAnoEscolarAnterior(DEFAULT_CONCLUI_ANO_ESCOLAR_ANTERIOR)
            .repetente(DEFAULT_REPETENTE)
            .dificuldadeAprendizado(DEFAULT_DIFICULDADE_APRENDIZADO)
            .dificuldadeDisciplina(DEFAULT_DIFICULDADE_DISCIPLINA)
            .moraCom(DEFAULT_MORA_COM)
            .pais(DEFAULT_PAIS)
            .situacaoMoradia(DEFAULT_SITUACAO_MORADIA)
            .tipoMoradia(DEFAULT_TIPO_MORADIA)
            .recebeBeneficioGoverno(DEFAULT_RECEBE_BENEFICIO_GOVERNO)
            .tipoBeneficio(DEFAULT_TIPO_BENEFICIO)
            .familiaExiste(DEFAULT_FAMILIA_EXISTE)
            .assitenciaMedica(DEFAULT_ASSITENCIA_MEDICA)
            .temAlergia(DEFAULT_TEM_ALERGIA)
            .temProblemaSaude(DEFAULT_TEM_PROBLEMA_SAUDE)
            .tomaMedicamento(DEFAULT_TOMA_MEDICAMENTO)
            .teveFratura(DEFAULT_TEVE_FRATURA)
            .teveCirurgia(DEFAULT_TEVE_CIRURGIA)
            .temDeficiencia(DEFAULT_TEM_DEFICIENCIA)
            .temAcompanhamentoMedico(DEFAULT_TEM_ACOMPANHAMENTO_MEDICO);
        return avaliacaoEconomica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvaliacaoEconomica createUpdatedEntity(EntityManager em) {
        AvaliacaoEconomica avaliacaoEconomica = new AvaliacaoEconomica()
            .trabalhoOuEstagio(UPDATED_TRABALHO_OU_ESTAGIO)
            .vinculoEmpregaticio(UPDATED_VINCULO_EMPREGATICIO)
            .cargoFuncao(UPDATED_CARGO_FUNCAO)
            .contribuiRendaFamiliar(UPDATED_CONTRIBUI_RENDA_FAMILIAR)
            .apoioFinanceiroFamiliar(UPDATED_APOIO_FINANCEIRO_FAMILIAR)
            .estudaAtualmente(UPDATED_ESTUDA_ATUALMENTE)
            .escolaAtual(UPDATED_ESCOLA_ATUAL)
            .estudouAnteriormente(UPDATED_ESTUDOU_ANTERIORMENTE)
            .escolaAnterior(UPDATED_ESCOLA_ANTERIOR)
            .concluiAnoEscolarAnterior(UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR)
            .repetente(UPDATED_REPETENTE)
            .dificuldadeAprendizado(UPDATED_DIFICULDADE_APRENDIZADO)
            .dificuldadeDisciplina(UPDATED_DIFICULDADE_DISCIPLINA)
            .moraCom(UPDATED_MORA_COM)
            .pais(UPDATED_PAIS)
            .situacaoMoradia(UPDATED_SITUACAO_MORADIA)
            .tipoMoradia(UPDATED_TIPO_MORADIA)
            .recebeBeneficioGoverno(UPDATED_RECEBE_BENEFICIO_GOVERNO)
            .tipoBeneficio(UPDATED_TIPO_BENEFICIO)
            .familiaExiste(UPDATED_FAMILIA_EXISTE)
            .assitenciaMedica(UPDATED_ASSITENCIA_MEDICA)
            .temAlergia(UPDATED_TEM_ALERGIA)
            .temProblemaSaude(UPDATED_TEM_PROBLEMA_SAUDE)
            .tomaMedicamento(UPDATED_TOMA_MEDICAMENTO)
            .teveFratura(UPDATED_TEVE_FRATURA)
            .teveCirurgia(UPDATED_TEVE_CIRURGIA)
            .temDeficiencia(UPDATED_TEM_DEFICIENCIA)
            .temAcompanhamentoMedico(UPDATED_TEM_ACOMPANHAMENTO_MEDICO);
        return avaliacaoEconomica;
    }

    @BeforeEach
    public void initTest() {
        avaliacaoEconomica = createEntity(em);
    }

    @Test
    @Transactional
    void createAvaliacaoEconomica() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoEconomicaRepository.findAll().size();
        // Create the AvaliacaoEconomica
        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isCreated());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeCreate + 1);
        AvaliacaoEconomica testAvaliacaoEconomica = avaliacaoEconomicaList.get(avaliacaoEconomicaList.size() - 1);
        assertThat(testAvaliacaoEconomica.getTrabalhoOuEstagio()).isEqualTo(DEFAULT_TRABALHO_OU_ESTAGIO);
        assertThat(testAvaliacaoEconomica.getVinculoEmpregaticio()).isEqualTo(DEFAULT_VINCULO_EMPREGATICIO);
        assertThat(testAvaliacaoEconomica.getCargoFuncao()).isEqualTo(DEFAULT_CARGO_FUNCAO);
        assertThat(testAvaliacaoEconomica.getContribuiRendaFamiliar()).isEqualTo(DEFAULT_CONTRIBUI_RENDA_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getApoioFinanceiroFamiliar()).isEqualTo(DEFAULT_APOIO_FINANCEIRO_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getEstudaAtualmente()).isEqualTo(DEFAULT_ESTUDA_ATUALMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAtual()).isEqualTo(DEFAULT_ESCOLA_ATUAL);
        assertThat(testAvaliacaoEconomica.getEstudouAnteriormente()).isEqualTo(DEFAULT_ESTUDOU_ANTERIORMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAnterior()).isEqualTo(DEFAULT_ESCOLA_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getConcluiAnoEscolarAnterior()).isEqualTo(DEFAULT_CONCLUI_ANO_ESCOLAR_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getRepetente()).isEqualTo(DEFAULT_REPETENTE);
        assertThat(testAvaliacaoEconomica.getDificuldadeAprendizado()).isEqualTo(DEFAULT_DIFICULDADE_APRENDIZADO);
        assertThat(testAvaliacaoEconomica.getDificuldadeDisciplina()).isEqualTo(DEFAULT_DIFICULDADE_DISCIPLINA);
        assertThat(testAvaliacaoEconomica.getMoraCom()).isEqualTo(DEFAULT_MORA_COM);
        assertThat(testAvaliacaoEconomica.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testAvaliacaoEconomica.getSituacaoMoradia()).isEqualTo(DEFAULT_SITUACAO_MORADIA);
        assertThat(testAvaliacaoEconomica.getTipoMoradia()).isEqualTo(DEFAULT_TIPO_MORADIA);
        assertThat(testAvaliacaoEconomica.getRecebeBeneficioGoverno()).isEqualTo(DEFAULT_RECEBE_BENEFICIO_GOVERNO);
        assertThat(testAvaliacaoEconomica.getTipoBeneficio()).isEqualTo(DEFAULT_TIPO_BENEFICIO);
        assertThat(testAvaliacaoEconomica.getFamiliaExiste()).isEqualTo(DEFAULT_FAMILIA_EXISTE);
        assertThat(testAvaliacaoEconomica.getAssitenciaMedica()).isEqualTo(DEFAULT_ASSITENCIA_MEDICA);
        assertThat(testAvaliacaoEconomica.getTemAlergia()).isEqualTo(DEFAULT_TEM_ALERGIA);
        assertThat(testAvaliacaoEconomica.getTemProblemaSaude()).isEqualTo(DEFAULT_TEM_PROBLEMA_SAUDE);
        assertThat(testAvaliacaoEconomica.getTomaMedicamento()).isEqualTo(DEFAULT_TOMA_MEDICAMENTO);
        assertThat(testAvaliacaoEconomica.getTeveFratura()).isEqualTo(DEFAULT_TEVE_FRATURA);
        assertThat(testAvaliacaoEconomica.getTeveCirurgia()).isEqualTo(DEFAULT_TEVE_CIRURGIA);
        assertThat(testAvaliacaoEconomica.getTemDeficiencia()).isEqualTo(DEFAULT_TEM_DEFICIENCIA);
        assertThat(testAvaliacaoEconomica.getTemAcompanhamentoMedico()).isEqualTo(DEFAULT_TEM_ACOMPANHAMENTO_MEDICO);
    }

    @Test
    @Transactional
    void createAvaliacaoEconomicaWithExistingId() throws Exception {
        // Create the AvaliacaoEconomica with an existing ID
        avaliacaoEconomica.setId(1L);

        int databaseSizeBeforeCreate = avaliacaoEconomicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrabalhoOuEstagioIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTrabalhoOuEstagio(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContribuiRendaFamiliarIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setContribuiRendaFamiliar(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApoioFinanceiroFamiliarIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setApoioFinanceiroFamiliar(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstudaAtualmenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setEstudaAtualmente(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstudouAnteriormenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setEstudouAnteriormente(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConcluiAnoEscolarAnteriorIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setConcluiAnoEscolarAnterior(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoraComIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setMoraCom(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setPais(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSituacaoMoradiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setSituacaoMoradia(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoMoradiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTipoMoradia(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRecebeBeneficioGovernoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setRecebeBeneficioGoverno(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFamiliaExisteIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setFamiliaExiste(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssitenciaMedicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setAssitenciaMedica(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTemAlergiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTemAlergia(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTemProblemaSaudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTemProblemaSaude(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTomaMedicamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTomaMedicamento(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTeveFraturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTeveFratura(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTeveCirurgiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTeveCirurgia(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTemDeficienciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTemDeficiencia(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTemAcompanhamentoMedicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoEconomicaRepository.findAll().size();
        // set the field null
        avaliacaoEconomica.setTemAcompanhamentoMedico(null);

        // Create the AvaliacaoEconomica, which fails.

        restAvaliacaoEconomicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAvaliacaoEconomicas() throws Exception {
        // Initialize the database
        avaliacaoEconomicaRepository.saveAndFlush(avaliacaoEconomica);

        // Get all the avaliacaoEconomicaList
        restAvaliacaoEconomicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacaoEconomica.getId().intValue())))
            .andExpect(jsonPath("$.[*].trabalhoOuEstagio").value(hasItem(DEFAULT_TRABALHO_OU_ESTAGIO.toString())))
            .andExpect(jsonPath("$.[*].vinculoEmpregaticio").value(hasItem(DEFAULT_VINCULO_EMPREGATICIO.toString())))
            .andExpect(jsonPath("$.[*].cargoFuncao").value(hasItem(DEFAULT_CARGO_FUNCAO)))
            .andExpect(jsonPath("$.[*].contribuiRendaFamiliar").value(hasItem(DEFAULT_CONTRIBUI_RENDA_FAMILIAR.toString())))
            .andExpect(jsonPath("$.[*].apoioFinanceiroFamiliar").value(hasItem(DEFAULT_APOIO_FINANCEIRO_FAMILIAR.toString())))
            .andExpect(jsonPath("$.[*].estudaAtualmente").value(hasItem(DEFAULT_ESTUDA_ATUALMENTE.toString())))
            .andExpect(jsonPath("$.[*].escolaAtual").value(hasItem(DEFAULT_ESCOLA_ATUAL.toString())))
            .andExpect(jsonPath("$.[*].estudouAnteriormente").value(hasItem(DEFAULT_ESTUDOU_ANTERIORMENTE.toString())))
            .andExpect(jsonPath("$.[*].escolaAnterior").value(hasItem(DEFAULT_ESCOLA_ANTERIOR.toString())))
            .andExpect(jsonPath("$.[*].concluiAnoEscolarAnterior").value(hasItem(DEFAULT_CONCLUI_ANO_ESCOLAR_ANTERIOR.toString())))
            .andExpect(jsonPath("$.[*].repetente").value(hasItem(DEFAULT_REPETENTE.toString())))
            .andExpect(jsonPath("$.[*].dificuldadeAprendizado").value(hasItem(DEFAULT_DIFICULDADE_APRENDIZADO.toString())))
            .andExpect(jsonPath("$.[*].dificuldadeDisciplina").value(hasItem(DEFAULT_DIFICULDADE_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].moraCom").value(hasItem(DEFAULT_MORA_COM.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].situacaoMoradia").value(hasItem(DEFAULT_SITUACAO_MORADIA.toString())))
            .andExpect(jsonPath("$.[*].tipoMoradia").value(hasItem(DEFAULT_TIPO_MORADIA.toString())))
            .andExpect(jsonPath("$.[*].recebeBeneficioGoverno").value(hasItem(DEFAULT_RECEBE_BENEFICIO_GOVERNO.toString())))
            .andExpect(jsonPath("$.[*].tipoBeneficio").value(hasItem(DEFAULT_TIPO_BENEFICIO)))
            .andExpect(jsonPath("$.[*].familiaExiste").value(hasItem(DEFAULT_FAMILIA_EXISTE.toString())))
            .andExpect(jsonPath("$.[*].assitenciaMedica").value(hasItem(DEFAULT_ASSITENCIA_MEDICA.toString())))
            .andExpect(jsonPath("$.[*].temAlergia").value(hasItem(DEFAULT_TEM_ALERGIA.toString())))
            .andExpect(jsonPath("$.[*].temProblemaSaude").value(hasItem(DEFAULT_TEM_PROBLEMA_SAUDE.toString())))
            .andExpect(jsonPath("$.[*].tomaMedicamento").value(hasItem(DEFAULT_TOMA_MEDICAMENTO.toString())))
            .andExpect(jsonPath("$.[*].teveFratura").value(hasItem(DEFAULT_TEVE_FRATURA.toString())))
            .andExpect(jsonPath("$.[*].teveCirurgia").value(hasItem(DEFAULT_TEVE_CIRURGIA.toString())))
            .andExpect(jsonPath("$.[*].temDeficiencia").value(hasItem(DEFAULT_TEM_DEFICIENCIA.toString())))
            .andExpect(jsonPath("$.[*].temAcompanhamentoMedico").value(hasItem(DEFAULT_TEM_ACOMPANHAMENTO_MEDICO.toString())));
    }

    @Test
    @Transactional
    void getAvaliacaoEconomica() throws Exception {
        // Initialize the database
        avaliacaoEconomicaRepository.saveAndFlush(avaliacaoEconomica);

        // Get the avaliacaoEconomica
        restAvaliacaoEconomicaMockMvc
            .perform(get(ENTITY_API_URL_ID, avaliacaoEconomica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacaoEconomica.getId().intValue()))
            .andExpect(jsonPath("$.trabalhoOuEstagio").value(DEFAULT_TRABALHO_OU_ESTAGIO.toString()))
            .andExpect(jsonPath("$.vinculoEmpregaticio").value(DEFAULT_VINCULO_EMPREGATICIO.toString()))
            .andExpect(jsonPath("$.cargoFuncao").value(DEFAULT_CARGO_FUNCAO))
            .andExpect(jsonPath("$.contribuiRendaFamiliar").value(DEFAULT_CONTRIBUI_RENDA_FAMILIAR.toString()))
            .andExpect(jsonPath("$.apoioFinanceiroFamiliar").value(DEFAULT_APOIO_FINANCEIRO_FAMILIAR.toString()))
            .andExpect(jsonPath("$.estudaAtualmente").value(DEFAULT_ESTUDA_ATUALMENTE.toString()))
            .andExpect(jsonPath("$.escolaAtual").value(DEFAULT_ESCOLA_ATUAL.toString()))
            .andExpect(jsonPath("$.estudouAnteriormente").value(DEFAULT_ESTUDOU_ANTERIORMENTE.toString()))
            .andExpect(jsonPath("$.escolaAnterior").value(DEFAULT_ESCOLA_ANTERIOR.toString()))
            .andExpect(jsonPath("$.concluiAnoEscolarAnterior").value(DEFAULT_CONCLUI_ANO_ESCOLAR_ANTERIOR.toString()))
            .andExpect(jsonPath("$.repetente").value(DEFAULT_REPETENTE.toString()))
            .andExpect(jsonPath("$.dificuldadeAprendizado").value(DEFAULT_DIFICULDADE_APRENDIZADO.toString()))
            .andExpect(jsonPath("$.dificuldadeDisciplina").value(DEFAULT_DIFICULDADE_DISCIPLINA))
            .andExpect(jsonPath("$.moraCom").value(DEFAULT_MORA_COM.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.situacaoMoradia").value(DEFAULT_SITUACAO_MORADIA.toString()))
            .andExpect(jsonPath("$.tipoMoradia").value(DEFAULT_TIPO_MORADIA.toString()))
            .andExpect(jsonPath("$.recebeBeneficioGoverno").value(DEFAULT_RECEBE_BENEFICIO_GOVERNO.toString()))
            .andExpect(jsonPath("$.tipoBeneficio").value(DEFAULT_TIPO_BENEFICIO))
            .andExpect(jsonPath("$.familiaExiste").value(DEFAULT_FAMILIA_EXISTE.toString()))
            .andExpect(jsonPath("$.assitenciaMedica").value(DEFAULT_ASSITENCIA_MEDICA.toString()))
            .andExpect(jsonPath("$.temAlergia").value(DEFAULT_TEM_ALERGIA.toString()))
            .andExpect(jsonPath("$.temProblemaSaude").value(DEFAULT_TEM_PROBLEMA_SAUDE.toString()))
            .andExpect(jsonPath("$.tomaMedicamento").value(DEFAULT_TOMA_MEDICAMENTO.toString()))
            .andExpect(jsonPath("$.teveFratura").value(DEFAULT_TEVE_FRATURA.toString()))
            .andExpect(jsonPath("$.teveCirurgia").value(DEFAULT_TEVE_CIRURGIA.toString()))
            .andExpect(jsonPath("$.temDeficiencia").value(DEFAULT_TEM_DEFICIENCIA.toString()))
            .andExpect(jsonPath("$.temAcompanhamentoMedico").value(DEFAULT_TEM_ACOMPANHAMENTO_MEDICO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAvaliacaoEconomica() throws Exception {
        // Get the avaliacaoEconomica
        restAvaliacaoEconomicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAvaliacaoEconomica() throws Exception {
        // Initialize the database
        avaliacaoEconomicaRepository.saveAndFlush(avaliacaoEconomica);

        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();

        // Update the avaliacaoEconomica
        AvaliacaoEconomica updatedAvaliacaoEconomica = avaliacaoEconomicaRepository.findById(avaliacaoEconomica.getId()).get();
        // Disconnect from session so that the updates on updatedAvaliacaoEconomica are not directly saved in db
        em.detach(updatedAvaliacaoEconomica);
        updatedAvaliacaoEconomica
            .trabalhoOuEstagio(UPDATED_TRABALHO_OU_ESTAGIO)
            .vinculoEmpregaticio(UPDATED_VINCULO_EMPREGATICIO)
            .cargoFuncao(UPDATED_CARGO_FUNCAO)
            .contribuiRendaFamiliar(UPDATED_CONTRIBUI_RENDA_FAMILIAR)
            .apoioFinanceiroFamiliar(UPDATED_APOIO_FINANCEIRO_FAMILIAR)
            .estudaAtualmente(UPDATED_ESTUDA_ATUALMENTE)
            .escolaAtual(UPDATED_ESCOLA_ATUAL)
            .estudouAnteriormente(UPDATED_ESTUDOU_ANTERIORMENTE)
            .escolaAnterior(UPDATED_ESCOLA_ANTERIOR)
            .concluiAnoEscolarAnterior(UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR)
            .repetente(UPDATED_REPETENTE)
            .dificuldadeAprendizado(UPDATED_DIFICULDADE_APRENDIZADO)
            .dificuldadeDisciplina(UPDATED_DIFICULDADE_DISCIPLINA)
            .moraCom(UPDATED_MORA_COM)
            .pais(UPDATED_PAIS)
            .situacaoMoradia(UPDATED_SITUACAO_MORADIA)
            .tipoMoradia(UPDATED_TIPO_MORADIA)
            .recebeBeneficioGoverno(UPDATED_RECEBE_BENEFICIO_GOVERNO)
            .tipoBeneficio(UPDATED_TIPO_BENEFICIO)
            .familiaExiste(UPDATED_FAMILIA_EXISTE)
            .assitenciaMedica(UPDATED_ASSITENCIA_MEDICA)
            .temAlergia(UPDATED_TEM_ALERGIA)
            .temProblemaSaude(UPDATED_TEM_PROBLEMA_SAUDE)
            .tomaMedicamento(UPDATED_TOMA_MEDICAMENTO)
            .teveFratura(UPDATED_TEVE_FRATURA)
            .teveCirurgia(UPDATED_TEVE_CIRURGIA)
            .temDeficiencia(UPDATED_TEM_DEFICIENCIA)
            .temAcompanhamentoMedico(UPDATED_TEM_ACOMPANHAMENTO_MEDICO);

        restAvaliacaoEconomicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAvaliacaoEconomica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAvaliacaoEconomica))
            )
            .andExpect(status().isOk());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
        AvaliacaoEconomica testAvaliacaoEconomica = avaliacaoEconomicaList.get(avaliacaoEconomicaList.size() - 1);
        assertThat(testAvaliacaoEconomica.getTrabalhoOuEstagio()).isEqualTo(UPDATED_TRABALHO_OU_ESTAGIO);
        assertThat(testAvaliacaoEconomica.getVinculoEmpregaticio()).isEqualTo(UPDATED_VINCULO_EMPREGATICIO);
        assertThat(testAvaliacaoEconomica.getCargoFuncao()).isEqualTo(UPDATED_CARGO_FUNCAO);
        assertThat(testAvaliacaoEconomica.getContribuiRendaFamiliar()).isEqualTo(UPDATED_CONTRIBUI_RENDA_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getApoioFinanceiroFamiliar()).isEqualTo(UPDATED_APOIO_FINANCEIRO_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getEstudaAtualmente()).isEqualTo(UPDATED_ESTUDA_ATUALMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAtual()).isEqualTo(UPDATED_ESCOLA_ATUAL);
        assertThat(testAvaliacaoEconomica.getEstudouAnteriormente()).isEqualTo(UPDATED_ESTUDOU_ANTERIORMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAnterior()).isEqualTo(UPDATED_ESCOLA_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getConcluiAnoEscolarAnterior()).isEqualTo(UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getRepetente()).isEqualTo(UPDATED_REPETENTE);
        assertThat(testAvaliacaoEconomica.getDificuldadeAprendizado()).isEqualTo(UPDATED_DIFICULDADE_APRENDIZADO);
        assertThat(testAvaliacaoEconomica.getDificuldadeDisciplina()).isEqualTo(UPDATED_DIFICULDADE_DISCIPLINA);
        assertThat(testAvaliacaoEconomica.getMoraCom()).isEqualTo(UPDATED_MORA_COM);
        assertThat(testAvaliacaoEconomica.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testAvaliacaoEconomica.getSituacaoMoradia()).isEqualTo(UPDATED_SITUACAO_MORADIA);
        assertThat(testAvaliacaoEconomica.getTipoMoradia()).isEqualTo(UPDATED_TIPO_MORADIA);
        assertThat(testAvaliacaoEconomica.getRecebeBeneficioGoverno()).isEqualTo(UPDATED_RECEBE_BENEFICIO_GOVERNO);
        assertThat(testAvaliacaoEconomica.getTipoBeneficio()).isEqualTo(UPDATED_TIPO_BENEFICIO);
        assertThat(testAvaliacaoEconomica.getFamiliaExiste()).isEqualTo(UPDATED_FAMILIA_EXISTE);
        assertThat(testAvaliacaoEconomica.getAssitenciaMedica()).isEqualTo(UPDATED_ASSITENCIA_MEDICA);
        assertThat(testAvaliacaoEconomica.getTemAlergia()).isEqualTo(UPDATED_TEM_ALERGIA);
        assertThat(testAvaliacaoEconomica.getTemProblemaSaude()).isEqualTo(UPDATED_TEM_PROBLEMA_SAUDE);
        assertThat(testAvaliacaoEconomica.getTomaMedicamento()).isEqualTo(UPDATED_TOMA_MEDICAMENTO);
        assertThat(testAvaliacaoEconomica.getTeveFratura()).isEqualTo(UPDATED_TEVE_FRATURA);
        assertThat(testAvaliacaoEconomica.getTeveCirurgia()).isEqualTo(UPDATED_TEVE_CIRURGIA);
        assertThat(testAvaliacaoEconomica.getTemDeficiencia()).isEqualTo(UPDATED_TEM_DEFICIENCIA);
        assertThat(testAvaliacaoEconomica.getTemAcompanhamentoMedico()).isEqualTo(UPDATED_TEM_ACOMPANHAMENTO_MEDICO);
    }

    @Test
    @Transactional
    void putNonExistingAvaliacaoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();
        avaliacaoEconomica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoEconomicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avaliacaoEconomica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvaliacaoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();
        avaliacaoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoEconomicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvaliacaoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();
        avaliacaoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoEconomicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvaliacaoEconomicaWithPatch() throws Exception {
        // Initialize the database
        avaliacaoEconomicaRepository.saveAndFlush(avaliacaoEconomica);

        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();

        // Update the avaliacaoEconomica using partial update
        AvaliacaoEconomica partialUpdatedAvaliacaoEconomica = new AvaliacaoEconomica();
        partialUpdatedAvaliacaoEconomica.setId(avaliacaoEconomica.getId());

        partialUpdatedAvaliacaoEconomica
            .trabalhoOuEstagio(UPDATED_TRABALHO_OU_ESTAGIO)
            .vinculoEmpregaticio(UPDATED_VINCULO_EMPREGATICIO)
            .contribuiRendaFamiliar(UPDATED_CONTRIBUI_RENDA_FAMILIAR)
            .escolaAnterior(UPDATED_ESCOLA_ANTERIOR)
            .concluiAnoEscolarAnterior(UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR)
            .dificuldadeAprendizado(UPDATED_DIFICULDADE_APRENDIZADO)
            .moraCom(UPDATED_MORA_COM)
            .tipoMoradia(UPDATED_TIPO_MORADIA)
            .temAlergia(UPDATED_TEM_ALERGIA)
            .temProblemaSaude(UPDATED_TEM_PROBLEMA_SAUDE)
            .teveFratura(UPDATED_TEVE_FRATURA)
            .temAcompanhamentoMedico(UPDATED_TEM_ACOMPANHAMENTO_MEDICO);

        restAvaliacaoEconomicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacaoEconomica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvaliacaoEconomica))
            )
            .andExpect(status().isOk());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
        AvaliacaoEconomica testAvaliacaoEconomica = avaliacaoEconomicaList.get(avaliacaoEconomicaList.size() - 1);
        assertThat(testAvaliacaoEconomica.getTrabalhoOuEstagio()).isEqualTo(UPDATED_TRABALHO_OU_ESTAGIO);
        assertThat(testAvaliacaoEconomica.getVinculoEmpregaticio()).isEqualTo(UPDATED_VINCULO_EMPREGATICIO);
        assertThat(testAvaliacaoEconomica.getCargoFuncao()).isEqualTo(DEFAULT_CARGO_FUNCAO);
        assertThat(testAvaliacaoEconomica.getContribuiRendaFamiliar()).isEqualTo(UPDATED_CONTRIBUI_RENDA_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getApoioFinanceiroFamiliar()).isEqualTo(DEFAULT_APOIO_FINANCEIRO_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getEstudaAtualmente()).isEqualTo(DEFAULT_ESTUDA_ATUALMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAtual()).isEqualTo(DEFAULT_ESCOLA_ATUAL);
        assertThat(testAvaliacaoEconomica.getEstudouAnteriormente()).isEqualTo(DEFAULT_ESTUDOU_ANTERIORMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAnterior()).isEqualTo(UPDATED_ESCOLA_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getConcluiAnoEscolarAnterior()).isEqualTo(UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getRepetente()).isEqualTo(DEFAULT_REPETENTE);
        assertThat(testAvaliacaoEconomica.getDificuldadeAprendizado()).isEqualTo(UPDATED_DIFICULDADE_APRENDIZADO);
        assertThat(testAvaliacaoEconomica.getDificuldadeDisciplina()).isEqualTo(DEFAULT_DIFICULDADE_DISCIPLINA);
        assertThat(testAvaliacaoEconomica.getMoraCom()).isEqualTo(UPDATED_MORA_COM);
        assertThat(testAvaliacaoEconomica.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testAvaliacaoEconomica.getSituacaoMoradia()).isEqualTo(DEFAULT_SITUACAO_MORADIA);
        assertThat(testAvaliacaoEconomica.getTipoMoradia()).isEqualTo(UPDATED_TIPO_MORADIA);
        assertThat(testAvaliacaoEconomica.getRecebeBeneficioGoverno()).isEqualTo(DEFAULT_RECEBE_BENEFICIO_GOVERNO);
        assertThat(testAvaliacaoEconomica.getTipoBeneficio()).isEqualTo(DEFAULT_TIPO_BENEFICIO);
        assertThat(testAvaliacaoEconomica.getFamiliaExiste()).isEqualTo(DEFAULT_FAMILIA_EXISTE);
        assertThat(testAvaliacaoEconomica.getAssitenciaMedica()).isEqualTo(DEFAULT_ASSITENCIA_MEDICA);
        assertThat(testAvaliacaoEconomica.getTemAlergia()).isEqualTo(UPDATED_TEM_ALERGIA);
        assertThat(testAvaliacaoEconomica.getTemProblemaSaude()).isEqualTo(UPDATED_TEM_PROBLEMA_SAUDE);
        assertThat(testAvaliacaoEconomica.getTomaMedicamento()).isEqualTo(DEFAULT_TOMA_MEDICAMENTO);
        assertThat(testAvaliacaoEconomica.getTeveFratura()).isEqualTo(UPDATED_TEVE_FRATURA);
        assertThat(testAvaliacaoEconomica.getTeveCirurgia()).isEqualTo(DEFAULT_TEVE_CIRURGIA);
        assertThat(testAvaliacaoEconomica.getTemDeficiencia()).isEqualTo(DEFAULT_TEM_DEFICIENCIA);
        assertThat(testAvaliacaoEconomica.getTemAcompanhamentoMedico()).isEqualTo(UPDATED_TEM_ACOMPANHAMENTO_MEDICO);
    }

    @Test
    @Transactional
    void fullUpdateAvaliacaoEconomicaWithPatch() throws Exception {
        // Initialize the database
        avaliacaoEconomicaRepository.saveAndFlush(avaliacaoEconomica);

        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();

        // Update the avaliacaoEconomica using partial update
        AvaliacaoEconomica partialUpdatedAvaliacaoEconomica = new AvaliacaoEconomica();
        partialUpdatedAvaliacaoEconomica.setId(avaliacaoEconomica.getId());

        partialUpdatedAvaliacaoEconomica
            .trabalhoOuEstagio(UPDATED_TRABALHO_OU_ESTAGIO)
            .vinculoEmpregaticio(UPDATED_VINCULO_EMPREGATICIO)
            .cargoFuncao(UPDATED_CARGO_FUNCAO)
            .contribuiRendaFamiliar(UPDATED_CONTRIBUI_RENDA_FAMILIAR)
            .apoioFinanceiroFamiliar(UPDATED_APOIO_FINANCEIRO_FAMILIAR)
            .estudaAtualmente(UPDATED_ESTUDA_ATUALMENTE)
            .escolaAtual(UPDATED_ESCOLA_ATUAL)
            .estudouAnteriormente(UPDATED_ESTUDOU_ANTERIORMENTE)
            .escolaAnterior(UPDATED_ESCOLA_ANTERIOR)
            .concluiAnoEscolarAnterior(UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR)
            .repetente(UPDATED_REPETENTE)
            .dificuldadeAprendizado(UPDATED_DIFICULDADE_APRENDIZADO)
            .dificuldadeDisciplina(UPDATED_DIFICULDADE_DISCIPLINA)
            .moraCom(UPDATED_MORA_COM)
            .pais(UPDATED_PAIS)
            .situacaoMoradia(UPDATED_SITUACAO_MORADIA)
            .tipoMoradia(UPDATED_TIPO_MORADIA)
            .recebeBeneficioGoverno(UPDATED_RECEBE_BENEFICIO_GOVERNO)
            .tipoBeneficio(UPDATED_TIPO_BENEFICIO)
            .familiaExiste(UPDATED_FAMILIA_EXISTE)
            .assitenciaMedica(UPDATED_ASSITENCIA_MEDICA)
            .temAlergia(UPDATED_TEM_ALERGIA)
            .temProblemaSaude(UPDATED_TEM_PROBLEMA_SAUDE)
            .tomaMedicamento(UPDATED_TOMA_MEDICAMENTO)
            .teveFratura(UPDATED_TEVE_FRATURA)
            .teveCirurgia(UPDATED_TEVE_CIRURGIA)
            .temDeficiencia(UPDATED_TEM_DEFICIENCIA)
            .temAcompanhamentoMedico(UPDATED_TEM_ACOMPANHAMENTO_MEDICO);

        restAvaliacaoEconomicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacaoEconomica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvaliacaoEconomica))
            )
            .andExpect(status().isOk());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
        AvaliacaoEconomica testAvaliacaoEconomica = avaliacaoEconomicaList.get(avaliacaoEconomicaList.size() - 1);
        assertThat(testAvaliacaoEconomica.getTrabalhoOuEstagio()).isEqualTo(UPDATED_TRABALHO_OU_ESTAGIO);
        assertThat(testAvaliacaoEconomica.getVinculoEmpregaticio()).isEqualTo(UPDATED_VINCULO_EMPREGATICIO);
        assertThat(testAvaliacaoEconomica.getCargoFuncao()).isEqualTo(UPDATED_CARGO_FUNCAO);
        assertThat(testAvaliacaoEconomica.getContribuiRendaFamiliar()).isEqualTo(UPDATED_CONTRIBUI_RENDA_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getApoioFinanceiroFamiliar()).isEqualTo(UPDATED_APOIO_FINANCEIRO_FAMILIAR);
        assertThat(testAvaliacaoEconomica.getEstudaAtualmente()).isEqualTo(UPDATED_ESTUDA_ATUALMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAtual()).isEqualTo(UPDATED_ESCOLA_ATUAL);
        assertThat(testAvaliacaoEconomica.getEstudouAnteriormente()).isEqualTo(UPDATED_ESTUDOU_ANTERIORMENTE);
        assertThat(testAvaliacaoEconomica.getEscolaAnterior()).isEqualTo(UPDATED_ESCOLA_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getConcluiAnoEscolarAnterior()).isEqualTo(UPDATED_CONCLUI_ANO_ESCOLAR_ANTERIOR);
        assertThat(testAvaliacaoEconomica.getRepetente()).isEqualTo(UPDATED_REPETENTE);
        assertThat(testAvaliacaoEconomica.getDificuldadeAprendizado()).isEqualTo(UPDATED_DIFICULDADE_APRENDIZADO);
        assertThat(testAvaliacaoEconomica.getDificuldadeDisciplina()).isEqualTo(UPDATED_DIFICULDADE_DISCIPLINA);
        assertThat(testAvaliacaoEconomica.getMoraCom()).isEqualTo(UPDATED_MORA_COM);
        assertThat(testAvaliacaoEconomica.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testAvaliacaoEconomica.getSituacaoMoradia()).isEqualTo(UPDATED_SITUACAO_MORADIA);
        assertThat(testAvaliacaoEconomica.getTipoMoradia()).isEqualTo(UPDATED_TIPO_MORADIA);
        assertThat(testAvaliacaoEconomica.getRecebeBeneficioGoverno()).isEqualTo(UPDATED_RECEBE_BENEFICIO_GOVERNO);
        assertThat(testAvaliacaoEconomica.getTipoBeneficio()).isEqualTo(UPDATED_TIPO_BENEFICIO);
        assertThat(testAvaliacaoEconomica.getFamiliaExiste()).isEqualTo(UPDATED_FAMILIA_EXISTE);
        assertThat(testAvaliacaoEconomica.getAssitenciaMedica()).isEqualTo(UPDATED_ASSITENCIA_MEDICA);
        assertThat(testAvaliacaoEconomica.getTemAlergia()).isEqualTo(UPDATED_TEM_ALERGIA);
        assertThat(testAvaliacaoEconomica.getTemProblemaSaude()).isEqualTo(UPDATED_TEM_PROBLEMA_SAUDE);
        assertThat(testAvaliacaoEconomica.getTomaMedicamento()).isEqualTo(UPDATED_TOMA_MEDICAMENTO);
        assertThat(testAvaliacaoEconomica.getTeveFratura()).isEqualTo(UPDATED_TEVE_FRATURA);
        assertThat(testAvaliacaoEconomica.getTeveCirurgia()).isEqualTo(UPDATED_TEVE_CIRURGIA);
        assertThat(testAvaliacaoEconomica.getTemDeficiencia()).isEqualTo(UPDATED_TEM_DEFICIENCIA);
        assertThat(testAvaliacaoEconomica.getTemAcompanhamentoMedico()).isEqualTo(UPDATED_TEM_ACOMPANHAMENTO_MEDICO);
    }

    @Test
    @Transactional
    void patchNonExistingAvaliacaoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();
        avaliacaoEconomica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoEconomicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avaliacaoEconomica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvaliacaoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();
        avaliacaoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoEconomicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvaliacaoEconomica() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoEconomicaRepository.findAll().size();
        avaliacaoEconomica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoEconomicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoEconomica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AvaliacaoEconomica in the database
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvaliacaoEconomica() throws Exception {
        // Initialize the database
        avaliacaoEconomicaRepository.saveAndFlush(avaliacaoEconomica);

        int databaseSizeBeforeDelete = avaliacaoEconomicaRepository.findAll().size();

        // Delete the avaliacaoEconomica
        restAvaliacaoEconomicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, avaliacaoEconomica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvaliacaoEconomica> avaliacaoEconomicaList = avaliacaoEconomicaRepository.findAll();
        assertThat(avaliacaoEconomicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
