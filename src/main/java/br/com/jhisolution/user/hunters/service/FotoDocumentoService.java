package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoDocumento;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoDocumento}.
 */
public interface FotoDocumentoService {
    /**
     * Save a fotoDocumento.
     *
     * @param fotoDocumento the entity to save.
     * @return the persisted entity.
     */
    FotoDocumento save(FotoDocumento fotoDocumento);

    /**
     * Updates a fotoDocumento.
     *
     * @param fotoDocumento the entity to update.
     * @return the persisted entity.
     */
    FotoDocumento update(FotoDocumento fotoDocumento);

    /**
     * Partially updates a fotoDocumento.
     *
     * @param fotoDocumento the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoDocumento> partialUpdate(FotoDocumento fotoDocumento);

    /**
     * Get all the fotoDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoDocumento> findAll(Pageable pageable);
    Page<FotoDocumento> findAllFotoDocumentosByDocumentoId(Long id, Pageable pageable);

    /**
     * Get the "id" fotoDocumento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoDocumento> findOne(Long id);

    /**
     * Delete the "id" fotoDocumento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
