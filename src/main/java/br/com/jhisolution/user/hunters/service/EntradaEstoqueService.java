package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.EntradaEstoque;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EntradaEstoque}.
 */
public interface EntradaEstoqueService {
    /**
     * Save a entradaEstoque.
     *
     * @param entradaEstoque the entity to save.
     * @return the persisted entity.
     */
    EntradaEstoque save(EntradaEstoque entradaEstoque);

    /**
     * Updates a entradaEstoque.
     *
     * @param entradaEstoque the entity to update.
     * @return the persisted entity.
     */
    EntradaEstoque update(EntradaEstoque entradaEstoque);

    /**
     * Partially updates a entradaEstoque.
     *
     * @param entradaEstoque the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntradaEstoque> partialUpdate(EntradaEstoque entradaEstoque);

    /**
     * Get all the entradaEstoques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntradaEstoque> findAll(Pageable pageable);

    /**
     * Get all the entradaEstoques with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntradaEstoque> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" entradaEstoque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntradaEstoque> findOne(Long id);

    /**
     * Delete the "id" entradaEstoque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
