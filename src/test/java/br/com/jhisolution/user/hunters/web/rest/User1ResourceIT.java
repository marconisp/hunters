package br.com.jhisolution.user.hunters.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jhisolution.user.hunters.IntegrationTest;
import br.com.jhisolution.user.hunters.domain.User1;
import br.com.jhisolution.user.hunters.repository.User1Repository;
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
 * Integration tests for the {@link User1Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class User1ResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private User1Repository user1Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUser1MockMvc;

    private User1 user1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User1 createEntity(EntityManager em) {
        User1 user1 = new User1().firstName(DEFAULT_FIRST_NAME).lastName(DEFAULT_LAST_NAME).email(DEFAULT_EMAIL);
        return user1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User1 createUpdatedEntity(EntityManager em) {
        User1 user1 = new User1().firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);
        return user1;
    }

    @BeforeEach
    public void initTest() {
        user1 = createEntity(em);
    }

    @Test
    @Transactional
    void createUser1() throws Exception {
        int databaseSizeBeforeCreate = user1Repository.findAll().size();
        // Create the User1
        restUser1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user1)))
            .andExpect(status().isCreated());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeCreate + 1);
        User1 testUser1 = user1List.get(user1List.size() - 1);
        assertThat(testUser1.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUser1.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUser1.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createUser1WithExistingId() throws Exception {
        // Create the User1 with an existing ID
        user1.setId(1L);

        int databaseSizeBeforeCreate = user1Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user1)))
            .andExpect(status().isBadRequest());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = user1Repository.findAll().size();
        // set the field null
        user1.setFirstName(null);

        // Create the User1, which fails.

        restUser1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user1)))
            .andExpect(status().isBadRequest());

        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = user1Repository.findAll().size();
        // set the field null
        user1.setLastName(null);

        // Create the User1, which fails.

        restUser1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user1)))
            .andExpect(status().isBadRequest());

        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = user1Repository.findAll().size();
        // set the field null
        user1.setEmail(null);

        // Create the User1, which fails.

        restUser1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user1)))
            .andExpect(status().isBadRequest());

        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUser1s() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        // Get all the user1List
        restUser1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user1.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getUser1() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        // Get the user1
        restUser1MockMvc
            .perform(get(ENTITY_API_URL_ID, user1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(user1.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingUser1() throws Exception {
        // Get the user1
        restUser1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUser1() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        int databaseSizeBeforeUpdate = user1Repository.findAll().size();

        // Update the user1
        User1 updatedUser1 = user1Repository.findById(user1.getId()).get();
        // Disconnect from session so that the updates on updatedUser1 are not directly saved in db
        em.detach(updatedUser1);
        updatedUser1.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);

        restUser1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUser1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUser1))
            )
            .andExpect(status().isOk());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
        User1 testUser1 = user1List.get(user1List.size() - 1);
        assertThat(testUser1.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUser1.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUser1.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingUser1() throws Exception {
        int databaseSizeBeforeUpdate = user1Repository.findAll().size();
        user1.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUser1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, user1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(user1))
            )
            .andExpect(status().isBadRequest());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUser1() throws Exception {
        int databaseSizeBeforeUpdate = user1Repository.findAll().size();
        user1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(user1))
            )
            .andExpect(status().isBadRequest());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUser1() throws Exception {
        int databaseSizeBeforeUpdate = user1Repository.findAll().size();
        user1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser1MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUser1WithPatch() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        int databaseSizeBeforeUpdate = user1Repository.findAll().size();

        // Update the user1 using partial update
        User1 partialUpdatedUser1 = new User1();
        partialUpdatedUser1.setId(user1.getId());

        partialUpdatedUser1.email(UPDATED_EMAIL);

        restUser1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUser1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUser1))
            )
            .andExpect(status().isOk());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
        User1 testUser1 = user1List.get(user1List.size() - 1);
        assertThat(testUser1.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUser1.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUser1.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateUser1WithPatch() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        int databaseSizeBeforeUpdate = user1Repository.findAll().size();

        // Update the user1 using partial update
        User1 partialUpdatedUser1 = new User1();
        partialUpdatedUser1.setId(user1.getId());

        partialUpdatedUser1.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);

        restUser1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUser1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUser1))
            )
            .andExpect(status().isOk());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
        User1 testUser1 = user1List.get(user1List.size() - 1);
        assertThat(testUser1.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUser1.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUser1.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingUser1() throws Exception {
        int databaseSizeBeforeUpdate = user1Repository.findAll().size();
        user1.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUser1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, user1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(user1))
            )
            .andExpect(status().isBadRequest());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUser1() throws Exception {
        int databaseSizeBeforeUpdate = user1Repository.findAll().size();
        user1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(user1))
            )
            .andExpect(status().isBadRequest());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUser1() throws Exception {
        int databaseSizeBeforeUpdate = user1Repository.findAll().size();
        user1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser1MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(user1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUser1() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        int databaseSizeBeforeDelete = user1Repository.findAll().size();

        // Delete the user1
        restUser1MockMvc
            .perform(delete(ENTITY_API_URL_ID, user1.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
