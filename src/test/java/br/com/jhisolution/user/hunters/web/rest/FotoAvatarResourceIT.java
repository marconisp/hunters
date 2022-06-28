package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.FotoAvatar;
import br.com.jhisolution.user.hunters.repository.FotoAvatarRepository;
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
 * Integration tests for the {@link FotoAvatarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FotoAvatarResourceIT {

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/foto-avatars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoAvatarRepository fotoAvatarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoAvatarMockMvc;

    private FotoAvatar fotoAvatar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoAvatar createEntity(EntityManager em) {
        FotoAvatar fotoAvatar = new FotoAvatar().conteudo(DEFAULT_CONTEUDO).conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return fotoAvatar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoAvatar createUpdatedEntity(EntityManager em) {
        FotoAvatar fotoAvatar = new FotoAvatar().conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return fotoAvatar;
    }

    @BeforeEach
    public void initTest() {
        fotoAvatar = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoAvatar() throws Exception {
        int databaseSizeBeforeCreate = fotoAvatarRepository.findAll().size();
        // Create the FotoAvatar
        restFotoAvatarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoAvatar)))
            .andExpect(status().isCreated());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeCreate + 1);
        FotoAvatar testFotoAvatar = fotoAvatarList.get(fotoAvatarList.size() - 1);
        assertThat(testFotoAvatar.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoAvatar.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFotoAvatarWithExistingId() throws Exception {
        // Create the FotoAvatar with an existing ID
        fotoAvatar.setId(1L);

        int databaseSizeBeforeCreate = fotoAvatarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoAvatarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoAvatar)))
            .andExpect(status().isBadRequest());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFotoAvatars() throws Exception {
        // Initialize the database
        fotoAvatarRepository.saveAndFlush(fotoAvatar);

        // Get all the fotoAvatarList
        restFotoAvatarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoAvatar.getId().intValue())))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }

    @Test
    @Transactional
    void getFotoAvatar() throws Exception {
        // Initialize the database
        fotoAvatarRepository.saveAndFlush(fotoAvatar);

        // Get the fotoAvatar
        restFotoAvatarMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoAvatar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoAvatar.getId().intValue()))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getNonExistingFotoAvatar() throws Exception {
        // Get the fotoAvatar
        restFotoAvatarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFotoAvatar() throws Exception {
        // Initialize the database
        fotoAvatarRepository.saveAndFlush(fotoAvatar);

        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();

        // Update the fotoAvatar
        FotoAvatar updatedFotoAvatar = fotoAvatarRepository.findById(fotoAvatar.getId()).get();
        // Disconnect from session so that the updates on updatedFotoAvatar are not directly saved in db
        em.detach(updatedFotoAvatar);
        updatedFotoAvatar.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoAvatarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFotoAvatar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFotoAvatar))
            )
            .andExpect(status().isOk());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
        FotoAvatar testFotoAvatar = fotoAvatarList.get(fotoAvatarList.size() - 1);
        assertThat(testFotoAvatar.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoAvatar.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFotoAvatar() throws Exception {
        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();
        fotoAvatar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoAvatarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoAvatar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoAvatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoAvatar() throws Exception {
        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();
        fotoAvatar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoAvatarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoAvatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoAvatar() throws Exception {
        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();
        fotoAvatar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoAvatarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoAvatar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoAvatarWithPatch() throws Exception {
        // Initialize the database
        fotoAvatarRepository.saveAndFlush(fotoAvatar);

        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();

        // Update the fotoAvatar using partial update
        FotoAvatar partialUpdatedFotoAvatar = new FotoAvatar();
        partialUpdatedFotoAvatar.setId(fotoAvatar.getId());

        restFotoAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoAvatar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoAvatar))
            )
            .andExpect(status().isOk());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
        FotoAvatar testFotoAvatar = fotoAvatarList.get(fotoAvatarList.size() - 1);
        assertThat(testFotoAvatar.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testFotoAvatar.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFotoAvatarWithPatch() throws Exception {
        // Initialize the database
        fotoAvatarRepository.saveAndFlush(fotoAvatar);

        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();

        // Update the fotoAvatar using partial update
        FotoAvatar partialUpdatedFotoAvatar = new FotoAvatar();
        partialUpdatedFotoAvatar.setId(fotoAvatar.getId());

        partialUpdatedFotoAvatar.conteudo(UPDATED_CONTEUDO).conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restFotoAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoAvatar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoAvatar))
            )
            .andExpect(status().isOk());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
        FotoAvatar testFotoAvatar = fotoAvatarList.get(fotoAvatarList.size() - 1);
        assertThat(testFotoAvatar.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testFotoAvatar.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFotoAvatar() throws Exception {
        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();
        fotoAvatar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoAvatar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoAvatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoAvatar() throws Exception {
        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();
        fotoAvatar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoAvatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoAvatar() throws Exception {
        int databaseSizeBeforeUpdate = fotoAvatarRepository.findAll().size();
        fotoAvatar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fotoAvatar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoAvatar in the database
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoAvatar() throws Exception {
        // Initialize the database
        fotoAvatarRepository.saveAndFlush(fotoAvatar);

        int databaseSizeBeforeDelete = fotoAvatarRepository.findAll().size();

        // Delete the fotoAvatar
        restFotoAvatarMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoAvatar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoAvatar> fotoAvatarList = fotoAvatarRepository.findAll();
        assertThat(fotoAvatarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
