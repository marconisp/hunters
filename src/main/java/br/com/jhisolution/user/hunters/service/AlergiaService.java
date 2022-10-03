package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Alergia;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Alergia}.
 */
public interface AlergiaService {
    /**
     * Save a alergia.
     *
     * @param alergia the entity to save.
     * @return the persisted entity.
     */
    Alergia save(Alergia alergia);

    /**
     * Updates a alergia.
     *
     * @param alergia the entity to update.
     * @return the persisted entity.
     */
    Alergia update(Alergia alergia);

    /**
     * Partially updates a alergia.
     *
     * @param alergia the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Alergia> partialUpdate(Alergia alergia);

    /**
     * Get all the alergias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Alergia> findAll(Pageable pageable);

    /**
     * Get the "id" alergia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Alergia> findOne(Long id);

    /**
     * Delete the "id" alergia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
