package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.EstadoCivil;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EstadoCivil}.
 */
public interface EstadoCivilService {
    /**
     * Save a estadoCivil.
     *
     * @param estadoCivil the entity to save.
     * @return the persisted entity.
     */
    EstadoCivil save(EstadoCivil estadoCivil);

    /**
     * Updates a estadoCivil.
     *
     * @param estadoCivil the entity to update.
     * @return the persisted entity.
     */
    EstadoCivil update(EstadoCivil estadoCivil);

    /**
     * Partially updates a estadoCivil.
     *
     * @param estadoCivil the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstadoCivil> partialUpdate(EstadoCivil estadoCivil);

    /**
     * Get all the estadoCivils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoCivil> findAll(Pageable pageable);

    /**
     * Get the "id" estadoCivil.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoCivil> findOne(Long id);

    /**
     * Delete the "id" estadoCivil.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
