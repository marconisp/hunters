package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.DadosMedico;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DadosMedico}.
 */
public interface DadosMedicoService {
    /**
     * Save a dadosMedico.
     *
     * @param dadosMedico the entity to save.
     * @return the persisted entity.
     */
    DadosMedico save(DadosMedico dadosMedico);

    /**
     * Updates a dadosMedico.
     *
     * @param dadosMedico the entity to update.
     * @return the persisted entity.
     */
    DadosMedico update(DadosMedico dadosMedico);

    /**
     * Partially updates a dadosMedico.
     *
     * @param dadosMedico the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DadosMedico> partialUpdate(DadosMedico dadosMedico);

    /**
     * Get all the dadosMedicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DadosMedico> findAll(Pageable pageable);

    /**
     * Get all the dadosMedicos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DadosMedico> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dadosMedico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DadosMedico> findOne(Long id);

    /**
     * Delete the "id" dadosMedico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
