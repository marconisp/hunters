package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.EnderecoEvento;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EnderecoEvento}.
 */
public interface EnderecoEventoService {
    /**
     * Save a enderecoEvento.
     *
     * @param enderecoEvento the entity to save.
     * @return the persisted entity.
     */
    EnderecoEvento save(EnderecoEvento enderecoEvento);

    /**
     * Updates a enderecoEvento.
     *
     * @param enderecoEvento the entity to update.
     * @return the persisted entity.
     */
    EnderecoEvento update(EnderecoEvento enderecoEvento);

    /**
     * Partially updates a enderecoEvento.
     *
     * @param enderecoEvento the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EnderecoEvento> partialUpdate(EnderecoEvento enderecoEvento);

    /**
     * Get all the enderecoEventos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnderecoEvento> findAll(Pageable pageable);

    /**
     * Get the "id" enderecoEvento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnderecoEvento> findOne(Long id);

    /**
     * Delete the "id" enderecoEvento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
