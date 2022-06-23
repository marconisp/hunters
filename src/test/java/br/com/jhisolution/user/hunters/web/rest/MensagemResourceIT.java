package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Mensagem;
import br.com.jhisolution.user.hunters.domain.TipoMensagem;
import br.com.jhisolution.user.hunters.repository.MensagemRepository;
import br.com.jhisolution.user.hunters.service.MensagemService;
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
 * Integration tests for the {@link MensagemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MensagemResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mensagems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MensagemRepository mensagemRepository;

    @Mock
    private MensagemRepository mensagemRepositoryMock;

    @Mock
    private MensagemService mensagemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMensagemMockMvc;

    private Mensagem mensagem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensagem createEntity(EntityManager em) {
        Mensagem mensagem = new Mensagem().data(DEFAULT_DATA).titulo(DEFAULT_TITULO).conteudo(DEFAULT_CONTEUDO);
        // Add required entity
        TipoMensagem tipoMensagem;
        if (TestUtil.findAll(em, TipoMensagem.class).isEmpty()) {
            tipoMensagem = TipoMensagemResourceIT.createEntity(em);
            em.persist(tipoMensagem);
            em.flush();
        } else {
            tipoMensagem = TestUtil.findAll(em, TipoMensagem.class).get(0);
        }
        mensagem.setTipo(tipoMensagem);
        return mensagem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensagem createUpdatedEntity(EntityManager em) {
        Mensagem mensagem = new Mensagem().data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);
        // Add required entity
        TipoMensagem tipoMensagem;
        if (TestUtil.findAll(em, TipoMensagem.class).isEmpty()) {
            tipoMensagem = TipoMensagemResourceIT.createUpdatedEntity(em);
            em.persist(tipoMensagem);
            em.flush();
        } else {
            tipoMensagem = TestUtil.findAll(em, TipoMensagem.class).get(0);
        }
        mensagem.setTipo(tipoMensagem);
        return mensagem;
    }

    @BeforeEach
    public void initTest() {
        mensagem = createEntity(em);
    }

    @Test
    @Transactional
    void createMensagem() throws Exception {
        int databaseSizeBeforeCreate = mensagemRepository.findAll().size();
        // Create the Mensagem
        restMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mensagem)))
            .andExpect(status().isCreated());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeCreate + 1);
        Mensagem testMensagem = mensagemList.get(mensagemList.size() - 1);
        assertThat(testMensagem.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testMensagem.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testMensagem.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
    }

    @Test
    @Transactional
    void createMensagemWithExistingId() throws Exception {
        // Create the Mensagem with an existing ID
        mensagem.setId(1L);

        int databaseSizeBeforeCreate = mensagemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mensagem)))
            .andExpect(status().isBadRequest());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensagemRepository.findAll().size();
        // set the field null
        mensagem.setData(null);

        // Create the Mensagem, which fails.

        restMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mensagem)))
            .andExpect(status().isBadRequest());

        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensagemRepository.findAll().size();
        // set the field null
        mensagem.setTitulo(null);

        // Create the Mensagem, which fails.

        restMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mensagem)))
            .andExpect(status().isBadRequest());

        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConteudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mensagemRepository.findAll().size();
        // set the field null
        mensagem.setConteudo(null);

        // Create the Mensagem, which fails.

        restMensagemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mensagem)))
            .andExpect(status().isBadRequest());

        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMensagems() throws Exception {
        // Initialize the database
        mensagemRepository.saveAndFlush(mensagem);

        // Get all the mensagemList
        restMensagemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMensagemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(mensagemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMensagemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mensagemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMensagemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mensagemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMensagemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mensagemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getMensagem() throws Exception {
        // Initialize the database
        mensagemRepository.saveAndFlush(mensagem);

        // Get the mensagem
        restMensagemMockMvc
            .perform(get(ENTITY_API_URL_ID, mensagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mensagem.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO));
    }

    @Test
    @Transactional
    void getNonExistingMensagem() throws Exception {
        // Get the mensagem
        restMensagemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMensagem() throws Exception {
        // Initialize the database
        mensagemRepository.saveAndFlush(mensagem);

        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();

        // Update the mensagem
        Mensagem updatedMensagem = mensagemRepository.findById(mensagem.getId()).get();
        // Disconnect from session so that the updates on updatedMensagem are not directly saved in db
        em.detach(updatedMensagem);
        updatedMensagem.data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);

        restMensagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMensagem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMensagem))
            )
            .andExpect(status().isOk());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
        Mensagem testMensagem = mensagemList.get(mensagemList.size() - 1);
        assertThat(testMensagem.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMensagem.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testMensagem.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
    }

    @Test
    @Transactional
    void putNonExistingMensagem() throws Exception {
        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
        mensagem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mensagem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMensagem() throws Exception {
        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
        mensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMensagem() throws Exception {
        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
        mensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mensagem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMensagemWithPatch() throws Exception {
        // Initialize the database
        mensagemRepository.saveAndFlush(mensagem);

        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();

        // Update the mensagem using partial update
        Mensagem partialUpdatedMensagem = new Mensagem();
        partialUpdatedMensagem.setId(mensagem.getId());

        partialUpdatedMensagem.data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);

        restMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMensagem))
            )
            .andExpect(status().isOk());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
        Mensagem testMensagem = mensagemList.get(mensagemList.size() - 1);
        assertThat(testMensagem.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMensagem.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testMensagem.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
    }

    @Test
    @Transactional
    void fullUpdateMensagemWithPatch() throws Exception {
        // Initialize the database
        mensagemRepository.saveAndFlush(mensagem);

        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();

        // Update the mensagem using partial update
        Mensagem partialUpdatedMensagem = new Mensagem();
        partialUpdatedMensagem.setId(mensagem.getId());

        partialUpdatedMensagem.data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);

        restMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMensagem))
            )
            .andExpect(status().isOk());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
        Mensagem testMensagem = mensagemList.get(mensagemList.size() - 1);
        assertThat(testMensagem.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMensagem.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testMensagem.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
    }

    @Test
    @Transactional
    void patchNonExistingMensagem() throws Exception {
        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
        mensagem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mensagem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMensagem() throws Exception {
        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
        mensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mensagem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMensagem() throws Exception {
        int databaseSizeBeforeUpdate = mensagemRepository.findAll().size();
        mensagem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mensagem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mensagem in the database
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMensagem() throws Exception {
        // Initialize the database
        mensagemRepository.saveAndFlush(mensagem);

        int databaseSizeBeforeDelete = mensagemRepository.findAll().size();

        // Delete the mensagem
        restMensagemMockMvc
            .perform(delete(ENTITY_API_URL_ID, mensagem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mensagem> mensagemList = mensagemRepository.findAll();
        assertThat(mensagemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
