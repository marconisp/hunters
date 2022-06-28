package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Aviso;
import br.com.jhisolution.user.hunters.repository.AvisoRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AvisoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvisoResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/avisos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvisoRepository avisoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisoMockMvc;

    private Aviso aviso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aviso createEntity(EntityManager em) {
        Aviso aviso = new Aviso().data(DEFAULT_DATA).titulo(DEFAULT_TITULO).conteudo(DEFAULT_CONTEUDO);
        return aviso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aviso createUpdatedEntity(EntityManager em) {
        Aviso aviso = new Aviso().data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);
        return aviso;
    }

    @BeforeEach
    public void initTest() {
        aviso = createEntity(em);
    }

    @Test
    @Transactional
    void createAviso() throws Exception {
        int databaseSizeBeforeCreate = avisoRepository.findAll().size();
        // Create the Aviso
        restAvisoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aviso)))
            .andExpect(status().isCreated());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeCreate + 1);
        Aviso testAviso = avisoList.get(avisoList.size() - 1);
        assertThat(testAviso.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAviso.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAviso.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
    }

    @Test
    @Transactional
    void createAvisoWithExistingId() throws Exception {
        // Create the Aviso with an existing ID
        aviso.setId(1L);

        int databaseSizeBeforeCreate = avisoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aviso)))
            .andExpect(status().isBadRequest());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoRepository.findAll().size();
        // set the field null
        aviso.setData(null);

        // Create the Aviso, which fails.

        restAvisoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aviso)))
            .andExpect(status().isBadRequest());

        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoRepository.findAll().size();
        // set the field null
        aviso.setTitulo(null);

        // Create the Aviso, which fails.

        restAvisoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aviso)))
            .andExpect(status().isBadRequest());

        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConteudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoRepository.findAll().size();
        // set the field null
        aviso.setConteudo(null);

        // Create the Aviso, which fails.

        restAvisoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aviso)))
            .andExpect(status().isBadRequest());

        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAvisos() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        // Get all the avisoList
        restAvisoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aviso.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getAviso() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        // Get the aviso
        restAvisoMockMvc
            .perform(get(ENTITY_API_URL_ID, aviso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aviso.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO));
    }

    @Test
    @Transactional
    void getNonExistingAviso() throws Exception {
        // Get the aviso
        restAvisoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAviso() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();

        // Update the aviso
        Aviso updatedAviso = avisoRepository.findById(aviso.getId()).get();
        // Disconnect from session so that the updates on updatedAviso are not directly saved in db
        em.detach(updatedAviso);
        updatedAviso.data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);

        restAvisoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAviso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAviso))
            )
            .andExpect(status().isOk());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
        Aviso testAviso = avisoList.get(avisoList.size() - 1);
        assertThat(testAviso.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAviso.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAviso.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
    }

    @Test
    @Transactional
    void putNonExistingAviso() throws Exception {
        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();
        aviso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aviso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aviso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAviso() throws Exception {
        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();
        aviso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aviso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAviso() throws Exception {
        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();
        aviso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aviso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvisoWithPatch() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();

        // Update the aviso using partial update
        Aviso partialUpdatedAviso = new Aviso();
        partialUpdatedAviso.setId(aviso.getId());

        restAvisoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAviso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAviso))
            )
            .andExpect(status().isOk());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
        Aviso testAviso = avisoList.get(avisoList.size() - 1);
        assertThat(testAviso.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAviso.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAviso.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
    }

    @Test
    @Transactional
    void fullUpdateAvisoWithPatch() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();

        // Update the aviso using partial update
        Aviso partialUpdatedAviso = new Aviso();
        partialUpdatedAviso.setId(aviso.getId());

        partialUpdatedAviso.data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);

        restAvisoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAviso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAviso))
            )
            .andExpect(status().isOk());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
        Aviso testAviso = avisoList.get(avisoList.size() - 1);
        assertThat(testAviso.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAviso.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAviso.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
    }

    @Test
    @Transactional
    void patchNonExistingAviso() throws Exception {
        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();
        aviso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aviso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aviso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAviso() throws Exception {
        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();
        aviso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aviso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAviso() throws Exception {
        int databaseSizeBeforeUpdate = avisoRepository.findAll().size();
        aviso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvisoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aviso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aviso in the database
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAviso() throws Exception {
        // Initialize the database
        avisoRepository.saveAndFlush(aviso);

        int databaseSizeBeforeDelete = avisoRepository.findAll().size();

        // Delete the aviso
        restAvisoMockMvc
            .perform(delete(ENTITY_API_URL_ID, aviso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aviso> avisoList = avisoRepository.findAll();
        assertThat(avisoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
