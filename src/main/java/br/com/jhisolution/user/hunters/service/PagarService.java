package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroPagarDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.PagarDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Pagar}.
 */
public interface PagarService {
    /**
     * Save a pagar.
     *
     * @param pagar the entity to save.
     * @return the persisted entity.
     */
    Pagar save(Pagar pagar);

    /**
     * Updates a pagar.
     *
     * @param pagar the entity to update.
     * @return the persisted entity.
     */
    Pagar update(Pagar pagar);

    /**
     * Partially updates a pagar.
     *
     * @param pagar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Pagar> partialUpdate(Pagar pagar);

    /**
     * Get all the pagars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pagar> findAll(Pageable pageable);

    /**
     * Get all the pagars with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pagar> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" pagar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pagar> findOne(Long id);

    /**
     * Delete the "id" pagar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<PagarDTO> findAllByDataInicialAndDataFinal(FiltroPagarDTO filtro);
    Resource findAllByDataInicialAndDataFinalJasper(FiltroPagarDTO filtro);
}
