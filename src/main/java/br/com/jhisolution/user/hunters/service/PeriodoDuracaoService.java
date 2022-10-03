package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.PeriodoDuracao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PeriodoDuracao}.
 */
public interface PeriodoDuracaoService {
    /**
     * Save a periodoDuracao.
     *
     * @param periodoDuracao the entity to save.
     * @return the persisted entity.
     */
    PeriodoDuracao save(PeriodoDuracao periodoDuracao);

    /**
     * Updates a periodoDuracao.
     *
     * @param periodoDuracao the entity to update.
     * @return the persisted entity.
     */
    PeriodoDuracao update(PeriodoDuracao periodoDuracao);

    /**
     * Partially updates a periodoDuracao.
     *
     * @param periodoDuracao the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PeriodoDuracao> partialUpdate(PeriodoDuracao periodoDuracao);

    /**
     * Get all the periodoDuracaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeriodoDuracao> findAll(Pageable pageable);

    /**
     * Get the "id" periodoDuracao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeriodoDuracao> findOne(Long id);

    /**
     * Delete the "id" periodoDuracao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
