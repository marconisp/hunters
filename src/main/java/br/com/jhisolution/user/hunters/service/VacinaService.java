package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Vacina;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Vacina}.
 */
public interface VacinaService {
    /**
     * Save a vacina.
     *
     * @param vacina the entity to save.
     * @return the persisted entity.
     */
    Vacina save(Vacina vacina);

    /**
     * Updates a vacina.
     *
     * @param vacina the entity to update.
     * @return the persisted entity.
     */
    Vacina update(Vacina vacina);

    /**
     * Partially updates a vacina.
     *
     * @param vacina the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Vacina> partialUpdate(Vacina vacina);

    /**
     * Get all the vacinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Vacina> findAll(Pageable pageable);

    /**
     * Get the "id" vacina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vacina> findOne(Long id);

    /**
     * Delete the "id" vacina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
