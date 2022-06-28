package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Raca;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Raca}.
 */
public interface RacaService {
    /**
     * Save a raca.
     *
     * @param raca the entity to save.
     * @return the persisted entity.
     */
    Raca save(Raca raca);

    /**
     * Updates a raca.
     *
     * @param raca the entity to update.
     * @return the persisted entity.
     */
    Raca update(Raca raca);

    /**
     * Partially updates a raca.
     *
     * @param raca the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Raca> partialUpdate(Raca raca);

    /**
     * Get all the racas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Raca> findAll(Pageable pageable);

    /**
     * Get the "id" raca.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Raca> findOne(Long id);

    /**
     * Delete the "id" raca.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
