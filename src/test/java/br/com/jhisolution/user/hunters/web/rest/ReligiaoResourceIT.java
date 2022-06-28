package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.Religiao;
import br.com.jhisolution.user.hunters.repository.ReligiaoRepository;
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
 * Integration tests for the {@link ReligiaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReligiaoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/religiaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReligiaoRepository religiaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReligiaoMockMvc;

    private Religiao religiao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Religiao createEntity(EntityManager em) {
        Religiao religiao = new Religiao().codigo(DEFAULT_CODIGO).descricao(DEFAULT_DESCRICAO);
        return religiao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Religiao createUpdatedEntity(EntityManager em) {
        Religiao religiao = new Religiao().codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        return religiao;
    }

    @BeforeEach
    public void initTest() {
        religiao = createEntity(em);
    }

    @Test
    @Transactional
    void createReligiao() throws Exception {
        int databaseSizeBeforeCreate = religiaoRepository.findAll().size();
        // Create the Religiao
        restReligiaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(religiao)))
            .andExpect(status().isCreated());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeCreate + 1);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testReligiao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createReligiaoWithExistingId() throws Exception {
        // Create the Religiao with an existing ID
        religiao.setId(1L);

        int databaseSizeBeforeCreate = religiaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReligiaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(religiao)))
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = religiaoRepository.findAll().size();
        // set the field null
        religiao.setCodigo(null);

        // Create the Religiao, which fails.

        restReligiaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(religiao)))
            .andExpect(status().isBadRequest());

        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = religiaoRepository.findAll().size();
        // set the field null
        religiao.setDescricao(null);

        // Create the Religiao, which fails.

        restReligiaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(religiao)))
            .andExpect(status().isBadRequest());

        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReligiaos() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        // Get all the religiaoList
        restReligiaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(religiao.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        // Get the religiao
        restReligiaoMockMvc
            .perform(get(ENTITY_API_URL_ID, religiao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(religiao.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingReligiao() throws Exception {
        // Get the religiao
        restReligiaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();

        // Update the religiao
        Religiao updatedReligiao = religiaoRepository.findById(religiao.getId()).get();
        // Disconnect from session so that the updates on updatedReligiao are not directly saved in db
        em.detach(updatedReligiao);
        updatedReligiao.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restReligiaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReligiao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReligiao))
            )
            .andExpect(status().isOk());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testReligiao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();
        religiao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReligiaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, religiao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religiao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();
        religiao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligiaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(religiao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();
        religiao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligiaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(religiao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReligiaoWithPatch() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();

        // Update the religiao using partial update
        Religiao partialUpdatedReligiao = new Religiao();
        partialUpdatedReligiao.setId(religiao.getId());

        partialUpdatedReligiao.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restReligiaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReligiao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReligiao))
            )
            .andExpect(status().isOk());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testReligiao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateReligiaoWithPatch() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();

        // Update the religiao using partial update
        Religiao partialUpdatedReligiao = new Religiao();
        partialUpdatedReligiao.setId(religiao.getId());

        partialUpdatedReligiao.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restReligiaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReligiao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReligiao))
            )
            .andExpect(status().isOk());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testReligiao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();
        religiao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReligiaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, religiao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(religiao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();
        religiao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligiaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(religiao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();
        religiao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReligiaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(religiao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        int databaseSizeBeforeDelete = religiaoRepository.findAll().size();

        // Delete the religiao
        restReligiaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, religiao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
