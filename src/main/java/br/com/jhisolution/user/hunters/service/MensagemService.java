package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Mensagem;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Mensagem}.
 */
public interface MensagemService {
    /**
     * Save a mensagem.
     *
     * @param mensagem the entity to save.
     * @return the persisted entity.
     */
    Mensagem save(Mensagem mensagem);

    /**
     * Updates a mensagem.
     *
     * @param mensagem the entity to update.
     * @return the persisted entity.
     */
    Mensagem update(Mensagem mensagem);

    /**
     * Partially updates a mensagem.
     *
     * @param mensagem the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Mensagem> partialUpdate(Mensagem mensagem);

    /**
     * Get all the mensagems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Mensagem> findAll(Pageable pageable);

    /**
     * Get all the mensagems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Mensagem> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" mensagem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mensagem> findOne(Long id);

    /**
     * Delete the "id" mensagem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
