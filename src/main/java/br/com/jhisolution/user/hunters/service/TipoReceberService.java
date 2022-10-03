package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.TipoReceber;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoReceber}.
 */
public interface TipoReceberService {
    /**
     * Save a tipoReceber.
     *
     * @param tipoReceber the entity to save.
     * @return the persisted entity.
     */
    TipoReceber save(TipoReceber tipoReceber);

    /**
     * Updates a tipoReceber.
     *
     * @param tipoReceber the entity to update.
     * @return the persisted entity.
     */
    TipoReceber update(TipoReceber tipoReceber);

    /**
     * Partially updates a tipoReceber.
     *
     * @param tipoReceber the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoReceber> partialUpdate(TipoReceber tipoReceber);

    /**
     * Get all the tipoRecebers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoReceber> findAll(Pageable pageable);

    /**
     * Get the "id" tipoReceber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoReceber> findOne(Long id);

    /**
     * Delete the "id" tipoReceber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
