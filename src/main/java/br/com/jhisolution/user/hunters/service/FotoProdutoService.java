package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoProduto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoProduto}.
 */
public interface FotoProdutoService {
    /**
     * Save a fotoProduto.
     *
     * @param fotoProduto the entity to save.
     * @return the persisted entity.
     */
    FotoProduto save(FotoProduto fotoProduto);

    /**
     * Updates a fotoProduto.
     *
     * @param fotoProduto the entity to update.
     * @return the persisted entity.
     */
    FotoProduto update(FotoProduto fotoProduto);

    /**
     * Partially updates a fotoProduto.
     *
     * @param fotoProduto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoProduto> partialUpdate(FotoProduto fotoProduto);

    /**
     * Get all the fotoProdutos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoProduto> findAll(Pageable pageable);

    /**
     * Get the "id" fotoProduto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoProduto> findOne(Long id);

    /**
     * Delete the "id" fotoProduto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
