package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.SubGrupoProduto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SubGrupoProduto}.
 */
public interface SubGrupoProdutoService {
    /**
     * Save a subGrupoProduto.
     *
     * @param subGrupoProduto the entity to save.
     * @return the persisted entity.
     */
    SubGrupoProduto save(SubGrupoProduto subGrupoProduto);

    /**
     * Updates a subGrupoProduto.
     *
     * @param subGrupoProduto the entity to update.
     * @return the persisted entity.
     */
    SubGrupoProduto update(SubGrupoProduto subGrupoProduto);

    /**
     * Partially updates a subGrupoProduto.
     *
     * @param subGrupoProduto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubGrupoProduto> partialUpdate(SubGrupoProduto subGrupoProduto);

    /**
     * Get all the subGrupoProdutos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubGrupoProduto> findAll(Pageable pageable);

    /**
     * Get the "id" subGrupoProduto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubGrupoProduto> findOne(Long id);

    /**
     * Delete the "id" subGrupoProduto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
