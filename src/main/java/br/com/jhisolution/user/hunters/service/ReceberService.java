package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Receber;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroReceberDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.ReceberDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Receber}.
 */
public interface ReceberService {
    /**
     * Save a receber.
     *
     * @param receber the entity to save.
     * @return the persisted entity.
     */
    Receber save(Receber receber);

    /**
     * Updates a receber.
     *
     * @param receber the entity to update.
     * @return the persisted entity.
     */
    Receber update(Receber receber);

    /**
     * Partially updates a receber.
     *
     * @param receber the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Receber> partialUpdate(Receber receber);

    /**
     * Get all the recebers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Receber> findAll(Pageable pageable);

    /**
     * Get all the recebers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Receber> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" receber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Receber> findOne(Long id);

    /**
     * Delete the "id" receber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ReceberDTO> findAllByDataInicialAndDataFinal(FiltroReceberDTO filtro);
    Resource findAllByDataInicialAndDataFinalJasper(FiltroReceberDTO filtro);
}
