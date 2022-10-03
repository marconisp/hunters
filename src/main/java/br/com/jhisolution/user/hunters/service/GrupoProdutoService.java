package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.GrupoProduto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link GrupoProduto}.
 */
public interface GrupoProdutoService {
    /**
     * Save a grupoProduto.
     *
     * @param grupoProduto the entity to save.
     * @return the persisted entity.
     */
    GrupoProduto save(GrupoProduto grupoProduto);

    /**
     * Updates a grupoProduto.
     *
     * @param grupoProduto the entity to update.
     * @return the persisted entity.
     */
    GrupoProduto update(GrupoProduto grupoProduto);

    /**
     * Partially updates a grupoProduto.
     *
     * @param grupoProduto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GrupoProduto> partialUpdate(GrupoProduto grupoProduto);

    /**
     * Get all the grupoProdutos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GrupoProduto> findAll(Pageable pageable);

    /**
     * Get the "id" grupoProduto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GrupoProduto> findOne(Long id);

    /**
     * Delete the "id" grupoProduto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
