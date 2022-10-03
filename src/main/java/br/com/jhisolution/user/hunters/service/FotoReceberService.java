package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoReceber;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoReceber}.
 */
public interface FotoReceberService {
    /**
     * Save a fotoReceber.
     *
     * @param fotoReceber the entity to save.
     * @return the persisted entity.
     */
    FotoReceber save(FotoReceber fotoReceber);

    /**
     * Updates a fotoReceber.
     *
     * @param fotoReceber the entity to update.
     * @return the persisted entity.
     */
    FotoReceber update(FotoReceber fotoReceber);

    /**
     * Partially updates a fotoReceber.
     *
     * @param fotoReceber the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoReceber> partialUpdate(FotoReceber fotoReceber);

    /**
     * Get all the fotoRecebers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoReceber> findAll(Pageable pageable);
    Page<FotoReceber> findAllByReceberId(Long id, Pageable pageable);

    /**
     * Get the "id" fotoReceber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoReceber> findOne(Long id);

    /**
     * Delete the "id" fotoReceber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
