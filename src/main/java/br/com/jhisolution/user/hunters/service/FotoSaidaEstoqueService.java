package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoSaidaEstoque;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoSaidaEstoque}.
 */
public interface FotoSaidaEstoqueService {
    /**
     * Save a fotoSaidaEstoque.
     *
     * @param fotoSaidaEstoque the entity to save.
     * @return the persisted entity.
     */
    FotoSaidaEstoque save(FotoSaidaEstoque fotoSaidaEstoque);

    /**
     * Updates a fotoSaidaEstoque.
     *
     * @param fotoSaidaEstoque the entity to update.
     * @return the persisted entity.
     */
    FotoSaidaEstoque update(FotoSaidaEstoque fotoSaidaEstoque);

    /**
     * Partially updates a fotoSaidaEstoque.
     *
     * @param fotoSaidaEstoque the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoSaidaEstoque> partialUpdate(FotoSaidaEstoque fotoSaidaEstoque);

    /**
     * Get all the fotoSaidaEstoques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoSaidaEstoque> findAll(Pageable pageable);

    /**
     * Get the "id" fotoSaidaEstoque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoSaidaEstoque> findOne(Long id);

    /**
     * Delete the "id" fotoSaidaEstoque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
