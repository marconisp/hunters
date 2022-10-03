package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.AgendaColaborador;
import br.com.jhisolution.user.hunters.repository.AgendaColaboradorRepository;
import br.com.jhisolution.user.hunters.service.AgendaColaboradorService;
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
 * Integration tests for the {@link AgendaColaboradorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AgendaColaboradorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agenda-colaboradors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgendaColaboradorRepository agendaColaboradorRepository;

    @Mock
    private AgendaColaboradorRepository agendaColaboradorRepositoryMock;

    @Mock
    private AgendaColaboradorService agendaColaboradorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendaColaboradorMockMvc;

    private AgendaColaborador agendaColaborador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaColaborador createEntity(EntityManager em) {
        AgendaColaborador agendaColaborador = new AgendaColaborador().nome(DEFAULT_NOME).obs(DEFAULT_OBS);
        return agendaColaborador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaColaborador createUpdatedEntity(EntityManager em) {
        AgendaColaborador agendaColaborador = new AgendaColaborador().nome(UPDATED_NOME).obs(UPDATED_OBS);
        return agendaColaborador;
    }

    @BeforeEach
    public void initTest() {
        agendaColaborador = createEntity(em);
    }

    @Test
    @Transactional
    void createAgendaColaborador() throws Exception {
        int databaseSizeBeforeCreate = agendaColaboradorRepository.findAll().size();
        // Create the AgendaColaborador
        restAgendaColaboradorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isCreated());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeCreate + 1);
        AgendaColaborador testAgendaColaborador = agendaColaboradorList.get(agendaColaboradorList.size() - 1);
        assertThat(testAgendaColaborador.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAgendaColaborador.getObs()).isEqualTo(DEFAULT_OBS);
    }

    @Test
    @Transactional
    void createAgendaColaboradorWithExistingId() throws Exception {
        // Create the AgendaColaborador with an existing ID
        agendaColaborador.setId(1L);

        int databaseSizeBeforeCreate = agendaColaboradorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaColaboradorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaColaboradorRepository.findAll().size();
        // set the field null
        agendaColaborador.setNome(null);

        // Create the AgendaColaborador, which fails.

        restAgendaColaboradorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isBadRequest());

        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAgendaColaboradors() throws Exception {
        // Initialize the database
        agendaColaboradorRepository.saveAndFlush(agendaColaborador);

        // Get all the agendaColaboradorList
        restAgendaColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaColaborador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaColaboradorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(agendaColaboradorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaColaboradorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(agendaColaboradorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaColaboradorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(agendaColaboradorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaColaboradorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(agendaColaboradorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAgendaColaborador() throws Exception {
        // Initialize the database
        agendaColaboradorRepository.saveAndFlush(agendaColaborador);

        // Get the agendaColaborador
        restAgendaColaboradorMockMvc
            .perform(get(ENTITY_API_URL_ID, agendaColaborador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendaColaborador.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS));
    }

    @Test
    @Transactional
    void getNonExistingAgendaColaborador() throws Exception {
        // Get the agendaColaborador
        restAgendaColaboradorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAgendaColaborador() throws Exception {
        // Initialize the database
        agendaColaboradorRepository.saveAndFlush(agendaColaborador);

        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();

        // Update the agendaColaborador
        AgendaColaborador updatedAgendaColaborador = agendaColaboradorRepository.findById(agendaColaborador.getId()).get();
        // Disconnect from session so that the updates on updatedAgendaColaborador are not directly saved in db
        em.detach(updatedAgendaColaborador);
        updatedAgendaColaborador.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restAgendaColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgendaColaborador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAgendaColaborador))
            )
            .andExpect(status().isOk());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
        AgendaColaborador testAgendaColaborador = agendaColaboradorList.get(agendaColaboradorList.size() - 1);
        assertThat(testAgendaColaborador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAgendaColaborador.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void putNonExistingAgendaColaborador() throws Exception {
        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();
        agendaColaborador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendaColaborador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendaColaborador() throws Exception {
        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();
        agendaColaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendaColaborador() throws Exception {
        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();
        agendaColaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendaColaboradorWithPatch() throws Exception {
        // Initialize the database
        agendaColaboradorRepository.saveAndFlush(agendaColaborador);

        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();

        // Update the agendaColaborador using partial update
        AgendaColaborador partialUpdatedAgendaColaborador = new AgendaColaborador();
        partialUpdatedAgendaColaborador.setId(agendaColaborador.getId());

        partialUpdatedAgendaColaborador.obs(UPDATED_OBS);

        restAgendaColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgendaColaborador))
            )
            .andExpect(status().isOk());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
        AgendaColaborador testAgendaColaborador = agendaColaboradorList.get(agendaColaboradorList.size() - 1);
        assertThat(testAgendaColaborador.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAgendaColaborador.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void fullUpdateAgendaColaboradorWithPatch() throws Exception {
        // Initialize the database
        agendaColaboradorRepository.saveAndFlush(agendaColaborador);

        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();

        // Update the agendaColaborador using partial update
        AgendaColaborador partialUpdatedAgendaColaborador = new AgendaColaborador();
        partialUpdatedAgendaColaborador.setId(agendaColaborador.getId());

        partialUpdatedAgendaColaborador.nome(UPDATED_NOME).obs(UPDATED_OBS);

        restAgendaColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgendaColaborador))
            )
            .andExpect(status().isOk());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
        AgendaColaborador testAgendaColaborador = agendaColaboradorList.get(agendaColaboradorList.size() - 1);
        assertThat(testAgendaColaborador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAgendaColaborador.getObs()).isEqualTo(UPDATED_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingAgendaColaborador() throws Exception {
        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();
        agendaColaborador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendaColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendaColaborador() throws Exception {
        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();
        agendaColaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendaColaborador() throws Exception {
        int databaseSizeBeforeUpdate = agendaColaboradorRepository.findAll().size();
        agendaColaborador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agendaColaborador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaColaborador in the database
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendaColaborador() throws Exception {
        // Initialize the database
        agendaColaboradorRepository.saveAndFlush(agendaColaborador);

        int databaseSizeBeforeDelete = agendaColaboradorRepository.findAll().size();

        // Delete the agendaColaborador
        restAgendaColaboradorMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendaColaborador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgendaColaborador> agendaColaboradorList = agendaColaboradorRepository.findAll();
        assertThat(agendaColaboradorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
