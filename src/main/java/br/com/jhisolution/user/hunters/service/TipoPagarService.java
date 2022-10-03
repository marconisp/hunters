package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.TipoPagar;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoPagar}.
 */
public interface TipoPagarService {
    /**
     * Save a tipoPagar.
     *
     * @param tipoPagar the entity to save.
     * @return the persisted entity.
     */
    TipoPagar save(TipoPagar tipoPagar);

    /**
     * Updates a tipoPagar.
     *
     * @param tipoPagar the entity to update.
     * @return the persisted entity.
     */
    TipoPagar update(TipoPagar tipoPagar);

    /**
     * Partially updates a tipoPagar.
     *
     * @param tipoPagar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoPagar> partialUpdate(TipoPagar tipoPagar);

    /**
     * Get all the tipoPagars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoPagar> findAll(Pageable pageable);

    /**
     * Get the "id" tipoPagar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoPagar> findOne(Long id);

    /**
     * Delete the "id" tipoPagar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
