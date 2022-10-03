package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.PagarPara;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PagarPara}.
 */
public interface PagarParaService {
    /**
     * Save a pagarPara.
     *
     * @param pagarPara the entity to save.
     * @return the persisted entity.
     */
    PagarPara save(PagarPara pagarPara);

    /**
     * Updates a pagarPara.
     *
     * @param pagarPara the entity to update.
     * @return the persisted entity.
     */
    PagarPara update(PagarPara pagarPara);

    /**
     * Partially updates a pagarPara.
     *
     * @param pagarPara the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PagarPara> partialUpdate(PagarPara pagarPara);

    /**
     * Get all the pagarParas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PagarPara> findAll(Pageable pageable);

    /**
     * Get the "id" pagarPara.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PagarPara> findOne(Long id);

    /**
     * Delete the "id" pagarPara.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
