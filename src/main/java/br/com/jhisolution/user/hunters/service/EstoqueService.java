package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Estoque;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Estoque}.
 */
public interface EstoqueService {
    /**
     * Save a estoque.
     *
     * @param estoque the entity to save.
     * @return the persisted entity.
     */
    Estoque save(Estoque estoque);

    /**
     * Updates a estoque.
     *
     * @param estoque the entity to update.
     * @return the persisted entity.
     */
    Estoque update(Estoque estoque);

    /**
     * Partially updates a estoque.
     *
     * @param estoque the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Estoque> partialUpdate(Estoque estoque);

    /**
     * Get all the estoques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Estoque> findAll(Pageable pageable);

    /**
     * Get all the estoques with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Estoque> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" estoque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Estoque> findOne(Long id);

    /**
     * Delete the "id" estoque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
