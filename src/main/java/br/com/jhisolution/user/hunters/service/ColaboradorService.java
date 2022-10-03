package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Colaborador;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Colaborador}.
 */
public interface ColaboradorService {
    /**
     * Save a colaborador.
     *
     * @param colaborador the entity to save.
     * @return the persisted entity.
     */
    Colaborador save(Colaborador colaborador);

    /**
     * Updates a colaborador.
     *
     * @param colaborador the entity to update.
     * @return the persisted entity.
     */
    Colaborador update(Colaborador colaborador);

    /**
     * Partially updates a colaborador.
     *
     * @param colaborador the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Colaborador> partialUpdate(Colaborador colaborador);

    /**
     * Get all the colaboradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Colaborador> findAll(Pageable pageable);

    /**
     * Get the "id" colaborador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Colaborador> findOne(Long id);

    /**
     * Delete the "id" colaborador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
