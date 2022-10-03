package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.TipoPagar;
import br.com.jhisolution.user.hunters.repository.TipoPagarRepository;
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
 * Integration tests for the {@link TipoPagarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoPagarResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-pagars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoPagarRepository tipoPagarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoPagarMockMvc;

    private TipoPagar tipoPagar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPagar createEntity(EntityManager em) {
        TipoPagar tipoPagar = new TipoPagar().nome(DEFAULT_NOME);
        return tipoPagar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPagar createUpdatedEntity(EntityManager em) {
        TipoPagar tipoPagar = new TipoPagar().nome(UPDATED_NOME);
        return tipoPagar;
    }

    @BeforeEach
    public void initTest() {
        tipoPagar = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoPagar() throws Exception {
        int databaseSizeBeforeCreate = tipoPagarRepository.findAll().size();
        // Create the TipoPagar
        restTipoPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPagar)))
            .andExpect(status().isCreated());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPagar testTipoPagar = tipoPagarList.get(tipoPagarList.size() - 1);
        assertThat(testTipoPagar.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createTipoPagarWithExistingId() throws Exception {
        // Create the TipoPagar with an existing ID
        tipoPagar.setId(1L);

        int databaseSizeBeforeCreate = tipoPagarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPagar)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPagarRepository.findAll().size();
        // set the field null
        tipoPagar.setNome(null);

        // Create the TipoPagar, which fails.

        restTipoPagarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPagar)))
            .andExpect(status().isBadRequest());

        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoPagars() throws Exception {
        // Initialize the database
        tipoPagarRepository.saveAndFlush(tipoPagar);

        // Get all the tipoPagarList
        restTipoPagarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPagar.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getTipoPagar() throws Exception {
        // Initialize the database
        tipoPagarRepository.saveAndFlush(tipoPagar);

        // Get the tipoPagar
        restTipoPagarMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoPagar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPagar.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingTipoPagar() throws Exception {
        // Get the tipoPagar
        restTipoPagarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoPagar() throws Exception {
        // Initialize the database
        tipoPagarRepository.saveAndFlush(tipoPagar);

        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();

        // Update the tipoPagar
        TipoPagar updatedTipoPagar = tipoPagarRepository.findById(tipoPagar.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPagar are not directly saved in db
        em.detach(updatedTipoPagar);
        updatedTipoPagar.nome(UPDATED_NOME);

        restTipoPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoPagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoPagar))
            )
            .andExpect(status().isOk());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
        TipoPagar testTipoPagar = tipoPagarList.get(tipoPagarList.size() - 1);
        assertThat(testTipoPagar.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingTipoPagar() throws Exception {
        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();
        tipoPagar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoPagar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoPagar() throws Exception {
        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();
        tipoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPagarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoPagar() throws Exception {
        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();
        tipoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPagarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoPagar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoPagarWithPatch() throws Exception {
        // Initialize the database
        tipoPagarRepository.saveAndFlush(tipoPagar);

        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();

        // Update the tipoPagar using partial update
        TipoPagar partialUpdatedTipoPagar = new TipoPagar();
        partialUpdatedTipoPagar.setId(tipoPagar.getId());

        partialUpdatedTipoPagar.nome(UPDATED_NOME);

        restTipoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoPagar))
            )
            .andExpect(status().isOk());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
        TipoPagar testTipoPagar = tipoPagarList.get(tipoPagarList.size() - 1);
        assertThat(testTipoPagar.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void fullUpdateTipoPagarWithPatch() throws Exception {
        // Initialize the database
        tipoPagarRepository.saveAndFlush(tipoPagar);

        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();

        // Update the tipoPagar using partial update
        TipoPagar partialUpdatedTipoPagar = new TipoPagar();
        partialUpdatedTipoPagar.setId(tipoPagar.getId());

        partialUpdatedTipoPagar.nome(UPDATED_NOME);

        restTipoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoPagar))
            )
            .andExpect(status().isOk());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
        TipoPagar testTipoPagar = tipoPagarList.get(tipoPagarList.size() - 1);
        assertThat(testTipoPagar.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingTipoPagar() throws Exception {
        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();
        tipoPagar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoPagar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoPagar() throws Exception {
        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();
        tipoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoPagar))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoPagar() throws Exception {
        int databaseSizeBeforeUpdate = tipoPagarRepository.findAll().size();
        tipoPagar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoPagarMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoPagar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoPagar in the database
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoPagar() throws Exception {
        // Initialize the database
        tipoPagarRepository.saveAndFlush(tipoPagar);

        int databaseSizeBeforeDelete = tipoPagarRepository.findAll().size();

        // Delete the tipoPagar
        restTipoPagarMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoPagar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoPagar> tipoPagarList = tipoPagarRepository.findAll();
        assertThat(tipoPagarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
