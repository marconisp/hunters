package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.AvaliacaoEconomica;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AvaliacaoEconomica}.
 */
public interface AvaliacaoEconomicaService {
    /**
     * Save a avaliacaoEconomica.
     *
     * @param avaliacaoEconomica the entity to save.
     * @return the persisted entity.
     */
    AvaliacaoEconomica save(AvaliacaoEconomica avaliacaoEconomica);

    /**
     * Updates a avaliacaoEconomica.
     *
     * @param avaliacaoEconomica the entity to update.
     * @return the persisted entity.
     */
    AvaliacaoEconomica update(AvaliacaoEconomica avaliacaoEconomica);

    /**
     * Partially updates a avaliacaoEconomica.
     *
     * @param avaliacaoEconomica the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AvaliacaoEconomica> partialUpdate(AvaliacaoEconomica avaliacaoEconomica);

    /**
     * Get all the avaliacaoEconomicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvaliacaoEconomica> findAll(Pageable pageable);

    /**
     * Get the "id" avaliacaoEconomica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvaliacaoEconomica> findOne(Long id);

    /**
     * Delete the "id" avaliacaoEconomica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
