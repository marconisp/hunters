package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoIcon;
import br.com.jhisolution.user.hunters.repository.FotoIconRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FotoIconResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoIconResourceIT {

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-icons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoIconRepository fotoIconRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoIconMockMvc;

    private FotoIcon fotoIcon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoIcon createEntity(EntityManager em) {
        FotoIcon fotoIcon = new FotoIcon().conteudo(DEFAULT_CONTEUDO).conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return fotoIcon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoIcon createUpdatedEntity(EntityManager em) {
        FotoIcon fotoIcon = new FotoIcon().conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return fotoIcon;
    }

    @BeforeEach
    public void initTest() {
        fotoIcon = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoIcon() throws Exception {
        int databaseSizeBeforeCreate = fotoIconRepository.findAll().size();
        // Create the FotoIcon
        restFotoIconMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoIcon)))
            .andExpect(status().isCreated());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeCreate + 1);
        FotoIcon testFotoIcon = fotoIconList.get(fotoIconList.size() - 1);
        assertThat(testFotoIcon.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoIcon.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoIconWithExistingId() throws Exception {
        // Create the FotoIcon with an existing ID
        fotoIcon.setId(1L);

        int databaseSizeBeforeCreate = fotoIconRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoIconMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoIcon)))
            .andExpect(status().isBadRequest());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoIcons() throws Exception {
        // Initialize the database
        fotoIconRepository.saveAndFlush(fotoIcon);

        // Get all the fotoIconList
        restFotoIconMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoIcon.getId().intValue())))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }

    @Test
    @Transactional
    void getFotoIcon() throws Exception {
        // Initialize the database
        fotoIconRepository.saveAndFlush(fotoIcon);

        // Get the fotoIcon
        restFotoIconMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoIcon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoIcon.getId().intValue()))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoIcon() throws Exception {
        // Get the fotoIcon
        restFotoIconMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoIcon() throws Exception {
        // Initialize the database
        fotoIconRepository.saveAndFlush(fotoIcon);

        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();

        // Update the fotoIcon
        FotoIcon updatedFotoIcon = fotoIconRepository.findById(fotoIcon.getId()).get();
        // Disconnect from session so that the updates on updatedFotoIcon are not directly saved in db
        em.detach(updatedFotoIcon);
        updatedFotoIcon.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoIconMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoIcon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoIcon))
            )
            .andExpect(status().isOk());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
        FotoIcon testFotoIcon = fotoIconList.get(fotoIconList.size() - 1);
        assertThat(testFotoIcon.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoIcon.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoIcon() throws Exception {
        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();
        fotoIcon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoIconMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoIcon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoIcon))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoIcon() throws Exception {
        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();
        fotoIcon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoIconMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoIcon))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoIcon() throws Exception {
        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();
        fotoIcon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoIconMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoIcon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoIconWithPatch() throws Exception {
        // Initialize the database
        fotoIconRepository.saveAndFlush(fotoIcon);

        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();

        // Update the fotoIcon using partial update
        FotoIcon partialUpdatedFotoIcon = new FotoIcon();
        partialUpdatedFotoIcon.setId(fotoIcon.getId());

        partialUpdatedFotoIcon.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoIconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoIcon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoIcon))
            )
            .andExpect(status().isOk());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
        FotoIcon testFotoIcon = fotoIconList.get(fotoIconList.size() - 1);
        assertThat(testFotoIcon.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoIcon.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoIconWithPatch() throws Exception {
        // Initialize the database
        fotoIconRepository.saveAndFlush(fotoIcon);

        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();

        // Update the fotoIcon using partial update
        FotoIcon partialUpdatedFotoIcon = new FotoIcon();
        partialUpdatedFotoIcon.setId(fotoIcon.getId());

        partialUpdatedFotoIcon.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoIconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoIcon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoIcon))
            )
            .andExpect(status().isOk());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
        FotoIcon testFotoIcon = fotoIconList.get(fotoIconList.size() - 1);
        assertThat(testFotoIcon.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoIcon.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoIcon() throws Exception {
        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();
        fotoIcon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoIconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoIcon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoIcon))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoIcon() throws Exception {
        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();
        fotoIcon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoIconMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoIcon))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoIcon() throws Exception {
        int databaseSizeBeforeUpdate = fotoIconRepository.findAll().size();
        fotoIcon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoIconMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fotoIcon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoIcon in the database
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoIcon() throws Exception {
        // Initialize the database
        fotoIconRepository.saveAndFlush(fotoIcon);

        int databaseSizeBeforeDelete = fotoIconRepository.findAll().size();

        // Delete the fotoIcon
        restFotoIconMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoIcon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoIcon> fotoIconList = fotoIconRepository.findAll();
        assertThat(fotoIconList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
