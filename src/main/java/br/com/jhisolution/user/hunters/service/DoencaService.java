package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Doenca;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Doenca}.
 */
public interface DoencaService {
    /**
     * Save a doenca.
     *
     * @param doenca the entity to save.
     * @return the persisted entity.
     */
    Doenca save(Doenca doenca);

    /**
     * Updates a doenca.
     *
     * @param doenca the entity to update.
     * @return the persisted entity.
     */
    Doenca update(Doenca doenca);

    /**
     * Partially updates a doenca.
     *
     * @param doenca the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Doenca> partialUpdate(Doenca doenca);

    /**
     * Get all the doencas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Doenca> findAll(Pageable pageable);

    /**
     * Get the "id" doenca.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Doenca> findOne(Long id);

    /**
     * Delete the "id" doenca.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
