package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.DiaSemana;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DiaSemana}.
 */
public interface DiaSemanaService {
    /**
     * Save a diaSemana.
     *
     * @param diaSemana the entity to save.
     * @return the persisted entity.
     */
    DiaSemana save(DiaSemana diaSemana);

    /**
     * Updates a diaSemana.
     *
     * @param diaSemana the entity to update.
     * @return the persisted entity.
     */
    DiaSemana update(DiaSemana diaSemana);

    /**
     * Partially updates a diaSemana.
     *
     * @param diaSemana the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiaSemana> partialUpdate(DiaSemana diaSemana);

    /**
     * Get all the diaSemanas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiaSemana> findAll(Pageable pageable);

    /**
     * Get the "id" diaSemana.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiaSemana> findOne(Long id);

    /**
     * Delete the "id" diaSemana.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
