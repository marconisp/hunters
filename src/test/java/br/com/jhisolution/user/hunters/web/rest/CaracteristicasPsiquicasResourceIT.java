package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.CaracteristicasPsiquicas;
import br.com.jhisolution.user.hunters.repository.CaracteristicasPsiquicasRepository;
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
 * Integration tests for the {@link CaracteristicasPsiquicasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaracteristicasPsiquicasResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/caracteristicas-psiquicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CaracteristicasPsiquicasRepository caracteristicasPsiquicasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaracteristicasPsiquicasMockMvc;

    private CaracteristicasPsiquicas caracteristicasPsiquicas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaracteristicasPsiquicas createEntity(EntityManager em) {
        CaracteristicasPsiquicas caracteristicasPsiquicas = new CaracteristicasPsiquicas().nome(DEFAULT_NOME);
        return caracteristicasPsiquicas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaracteristicasPsiquicas createUpdatedEntity(EntityManager em) {
        CaracteristicasPsiquicas caracteristicasPsiquicas = new CaracteristicasPsiquicas().nome(UPDATED_NOME);
        return caracteristicasPsiquicas;
    }

    @BeforeEach
    public void initTest() {
        caracteristicasPsiquicas = createEntity(em);
    }

    @Test
    @Transactional
    void createCaracteristicasPsiquicas() throws Exception {
        int databaseSizeBeforeCreate = caracteristicasPsiquicasRepository.findAll().size();
        // Create the CaracteristicasPsiquicas
        restCaracteristicasPsiquicasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isCreated());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeCreate + 1);
        CaracteristicasPsiquicas testCaracteristicasPsiquicas = caracteristicasPsiquicasList.get(caracteristicasPsiquicasList.size() - 1);
        assertThat(testCaracteristicasPsiquicas.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createCaracteristicasPsiquicasWithExistingId() throws Exception {
        // Create the CaracteristicasPsiquicas with an existing ID
        caracteristicasPsiquicas.setId(1L);

        int databaseSizeBeforeCreate = caracteristicasPsiquicasRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristicasPsiquicasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicasPsiquicasRepository.findAll().size();
        // set the field null
        caracteristicasPsiquicas.setNome(null);

        // Create the CaracteristicasPsiquicas, which fails.

        restCaracteristicasPsiquicasMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isBadRequest());

        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCaracteristicasPsiquicas() throws Exception {
        // Initialize the database
        caracteristicasPsiquicasRepository.saveAndFlush(caracteristicasPsiquicas);

        // Get all the caracteristicasPsiquicasList
        restCaracteristicasPsiquicasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristicasPsiquicas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getCaracteristicasPsiquicas() throws Exception {
        // Initialize the database
        caracteristicasPsiquicasRepository.saveAndFlush(caracteristicasPsiquicas);

        // Get the caracteristicasPsiquicas
        restCaracteristicasPsiquicasMockMvc
            .perform(get(ENTITY_API_URL_ID, caracteristicasPsiquicas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristicasPsiquicas.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingCaracteristicasPsiquicas() throws Exception {
        // Get the caracteristicasPsiquicas
        restCaracteristicasPsiquicasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCaracteristicasPsiquicas() throws Exception {
        // Initialize the database
        caracteristicasPsiquicasRepository.saveAndFlush(caracteristicasPsiquicas);

        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();

        // Update the caracteristicasPsiquicas
        CaracteristicasPsiquicas updatedCaracteristicasPsiquicas = caracteristicasPsiquicasRepository
            .findById(caracteristicasPsiquicas.getId())
            .get();
        // Disconnect from session so that the updates on updatedCaracteristicasPsiquicas are not directly saved in db
        em.detach(updatedCaracteristicasPsiquicas);
        updatedCaracteristicasPsiquicas.nome(UPDATED_NOME);

        restCaracteristicasPsiquicasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaracteristicasPsiquicas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaracteristicasPsiquicas))
            )
            .andExpect(status().isOk());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
        CaracteristicasPsiquicas testCaracteristicasPsiquicas = caracteristicasPsiquicasList.get(caracteristicasPsiquicasList.size() - 1);
        assertThat(testCaracteristicasPsiquicas.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingCaracteristicasPsiquicas() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();
        caracteristicasPsiquicas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicasPsiquicasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caracteristicasPsiquicas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaracteristicasPsiquicas() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();
        caracteristicasPsiquicas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasPsiquicasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaracteristicasPsiquicas() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();
        caracteristicasPsiquicas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasPsiquicasMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaracteristicasPsiquicasWithPatch() throws Exception {
        // Initialize the database
        caracteristicasPsiquicasRepository.saveAndFlush(caracteristicasPsiquicas);

        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();

        // Update the caracteristicasPsiquicas using partial update
        CaracteristicasPsiquicas partialUpdatedCaracteristicasPsiquicas = new CaracteristicasPsiquicas();
        partialUpdatedCaracteristicasPsiquicas.setId(caracteristicasPsiquicas.getId());

        partialUpdatedCaracteristicasPsiquicas.nome(UPDATED_NOME);

        restCaracteristicasPsiquicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaracteristicasPsiquicas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaracteristicasPsiquicas))
            )
            .andExpect(status().isOk());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
        CaracteristicasPsiquicas testCaracteristicasPsiquicas = caracteristicasPsiquicasList.get(caracteristicasPsiquicasList.size() - 1);
        assertThat(testCaracteristicasPsiquicas.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void fullUpdateCaracteristicasPsiquicasWithPatch() throws Exception {
        // Initialize the database
        caracteristicasPsiquicasRepository.saveAndFlush(caracteristicasPsiquicas);

        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();

        // Update the caracteristicasPsiquicas using partial update
        CaracteristicasPsiquicas partialUpdatedCaracteristicasPsiquicas = new CaracteristicasPsiquicas();
        partialUpdatedCaracteristicasPsiquicas.setId(caracteristicasPsiquicas.getId());

        partialUpdatedCaracteristicasPsiquicas.nome(UPDATED_NOME);

        restCaracteristicasPsiquicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaracteristicasPsiquicas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaracteristicasPsiquicas))
            )
            .andExpect(status().isOk());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
        CaracteristicasPsiquicas testCaracteristicasPsiquicas = caracteristicasPsiquicasList.get(caracteristicasPsiquicasList.size() - 1);
        assertThat(testCaracteristicasPsiquicas.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingCaracteristicasPsiquicas() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();
        caracteristicasPsiquicas.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicasPsiquicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caracteristicasPsiquicas.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaracteristicasPsiquicas() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();
        caracteristicasPsiquicas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasPsiquicasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaracteristicasPsiquicas() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicasPsiquicasRepository.findAll().size();
        caracteristicasPsiquicas.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaracteristicasPsiquicasMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caracteristicasPsiquicas))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaracteristicasPsiquicas in the database
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaracteristicasPsiquicas() throws Exception {
        // Initialize the database
        caracteristicasPsiquicasRepository.saveAndFlush(caracteristicasPsiquicas);

        int databaseSizeBeforeDelete = caracteristicasPsiquicasRepository.findAll().size();

        // Delete the caracteristicasPsiquicas
        restCaracteristicasPsiquicasMockMvc
            .perform(delete(ENTITY_API_URL_ID, caracteristicasPsiquicas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaracteristicasPsiquicas> caracteristicasPsiquicasList = caracteristicasPsiquicasRepository.findAll();
        assertThat(caracteristicasPsiquicasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
