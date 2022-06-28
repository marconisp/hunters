package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.User1;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link User1}.
 */
public interface User1Service {
    /**
     * Save a user1.
     *
     * @param user1 the entity to save.
     * @return the persisted entity.
     */
    User1 save(User1 user1);

    /**
     * Updates a user1.
     *
     * @param user1 the entity to update.
     * @return the persisted entity.
     */
    User1 update(User1 user1);

    /**
     * Partially updates a user1.
     *
     * @param user1 the entity to update partially.
     * @return the persisted entity.
     */
    Optional<User1> partialUpdate(User1 user1);

    /**
     * Get all the user1s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<User1> findAll(Pageable pageable);

    /**
     * Get the "id" user1.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<User1> findOne(Long id);

    /**
     * Delete the "id" user1.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
