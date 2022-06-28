package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Aviso;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Aviso}.
 */
public interface AvisoService {
    /**
     * Save a aviso.
     *
     * @param aviso the entity to save.
     * @return the persisted entity.
     */
    Aviso save(Aviso aviso);

    /**
     * Updates a aviso.
     *
     * @param aviso the entity to update.
     * @return the persisted entity.
     */
    Aviso update(Aviso aviso);

    /**
     * Partially updates a aviso.
     *
     * @param aviso the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Aviso> partialUpdate(Aviso aviso);

    /**
     * Get all the avisos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Aviso> findAll(Pageable pageable);
    Page<Aviso> findAllByDadosPessoaisId(Long id, Pageable pageable);

    /**
     * Get the "id" aviso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Aviso> findOne(Long id);

    /**
     * Delete the "id" aviso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
