package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.TipoTransacao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoTransacao}.
 */
public interface TipoTransacaoService {
    /**
     * Save a tipoTransacao.
     *
     * @param tipoTransacao the entity to save.
     * @return the persisted entity.
     */
    TipoTransacao save(TipoTransacao tipoTransacao);

    /**
     * Updates a tipoTransacao.
     *
     * @param tipoTransacao the entity to update.
     * @return the persisted entity.
     */
    TipoTransacao update(TipoTransacao tipoTransacao);

    /**
     * Partially updates a tipoTransacao.
     *
     * @param tipoTransacao the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoTransacao> partialUpdate(TipoTransacao tipoTransacao);

    /**
     * Get all the tipoTransacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoTransacao> findAll(Pageable pageable);

    /**
     * Get the "id" tipoTransacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoTransacao> findOne(Long id);

    /**
     * Delete the "id" tipoTransacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
