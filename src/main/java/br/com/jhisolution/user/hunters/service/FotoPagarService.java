package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoPagar;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoPagar}.
 */
public interface FotoPagarService {
    /**
     * Save a fotoPagar.
     *
     * @param fotoPagar the entity to save.
     * @return the persisted entity.
     */
    FotoPagar save(FotoPagar fotoPagar);

    /**
     * Updates a fotoPagar.
     *
     * @param fotoPagar the entity to update.
     * @return the persisted entity.
     */
    FotoPagar update(FotoPagar fotoPagar);

    /**
     * Partially updates a fotoPagar.
     *
     * @param fotoPagar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoPagar> partialUpdate(FotoPagar fotoPagar);

    /**
     * Get all the fotoPagars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoPagar> findAll(Pageable pageable);

    Page<FotoPagar> findAllByPagarId(Long id, Pageable pageable);

    /**
     * Get the "id" fotoPagar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoPagar> findOne(Long id);

    /**
     * Delete the "id" fotoPagar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
