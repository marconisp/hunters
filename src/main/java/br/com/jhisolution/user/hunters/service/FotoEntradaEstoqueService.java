package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoEntradaEstoque;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoEntradaEstoque}.
 */
public interface FotoEntradaEstoqueService {
    /**
     * Save a fotoEntradaEstoque.
     *
     * @param fotoEntradaEstoque the entity to save.
     * @return the persisted entity.
     */
    FotoEntradaEstoque save(FotoEntradaEstoque fotoEntradaEstoque);

    /**
     * Updates a fotoEntradaEstoque.
     *
     * @param fotoEntradaEstoque the entity to update.
     * @return the persisted entity.
     */
    FotoEntradaEstoque update(FotoEntradaEstoque fotoEntradaEstoque);

    /**
     * Partially updates a fotoEntradaEstoque.
     *
     * @param fotoEntradaEstoque the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoEntradaEstoque> partialUpdate(FotoEntradaEstoque fotoEntradaEstoque);

    /**
     * Get all the fotoEntradaEstoques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoEntradaEstoque> findAll(Pageable pageable);

    /**
     * Get the "id" fotoEntradaEstoque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoEntradaEstoque> findOne(Long id);

    /**
     * Delete the "id" fotoEntradaEstoque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
