package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.SaidaEstoque;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SaidaEstoque}.
 */
public interface SaidaEstoqueService {
    /**
     * Save a saidaEstoque.
     *
     * @param saidaEstoque the entity to save.
     * @return the persisted entity.
     */
    SaidaEstoque save(SaidaEstoque saidaEstoque);

    /**
     * Updates a saidaEstoque.
     *
     * @param saidaEstoque the entity to update.
     * @return the persisted entity.
     */
    SaidaEstoque update(SaidaEstoque saidaEstoque);

    /**
     * Partially updates a saidaEstoque.
     *
     * @param saidaEstoque the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaidaEstoque> partialUpdate(SaidaEstoque saidaEstoque);

    /**
     * Get all the saidaEstoques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SaidaEstoque> findAll(Pageable pageable);

    /**
     * Get all the saidaEstoques with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SaidaEstoque> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" saidaEstoque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaidaEstoque> findOne(Long id);

    /**
     * Delete the "id" saidaEstoque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
