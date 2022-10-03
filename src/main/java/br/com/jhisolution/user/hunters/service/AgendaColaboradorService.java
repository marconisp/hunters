package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.AgendaColaborador;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AgendaColaborador}.
 */
public interface AgendaColaboradorService {
    /**
     * Save a agendaColaborador.
     *
     * @param agendaColaborador the entity to save.
     * @return the persisted entity.
     */
    AgendaColaborador save(AgendaColaborador agendaColaborador);

    /**
     * Updates a agendaColaborador.
     *
     * @param agendaColaborador the entity to update.
     * @return the persisted entity.
     */
    AgendaColaborador update(AgendaColaborador agendaColaborador);

    /**
     * Partially updates a agendaColaborador.
     *
     * @param agendaColaborador the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgendaColaborador> partialUpdate(AgendaColaborador agendaColaborador);

    /**
     * Get all the agendaColaboradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgendaColaborador> findAll(Pageable pageable);

    /**
     * Get all the agendaColaboradors with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgendaColaborador> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" agendaColaborador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgendaColaborador> findOne(Long id);

    /**
     * Delete the "id" agendaColaborador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
