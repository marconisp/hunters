package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.TipoDocumento;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoDocumento}.
 */
public interface TipoDocumentoService {
    /**
     * Save a tipoDocumento.
     *
     * @param tipoDocumento the entity to save.
     * @return the persisted entity.
     */
    TipoDocumento save(TipoDocumento tipoDocumento);

    /**
     * Updates a tipoDocumento.
     *
     * @param tipoDocumento the entity to update.
     * @return the persisted entity.
     */
    TipoDocumento update(TipoDocumento tipoDocumento);

    /**
     * Partially updates a tipoDocumento.
     *
     * @param tipoDocumento the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoDocumento> partialUpdate(TipoDocumento tipoDocumento);

    /**
     * Get all the tipoDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoDocumento> findAll(Pageable pageable);

    /**
     * Get the "id" tipoDocumento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoDocumento> findOne(Long id);

    /**
     * Delete the "id" tipoDocumento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
