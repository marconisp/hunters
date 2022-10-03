package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.TipoContratacao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoContratacao}.
 */
public interface TipoContratacaoService {
    /**
     * Save a tipoContratacao.
     *
     * @param tipoContratacao the entity to save.
     * @return the persisted entity.
     */
    TipoContratacao save(TipoContratacao tipoContratacao);

    /**
     * Updates a tipoContratacao.
     *
     * @param tipoContratacao the entity to update.
     * @return the persisted entity.
     */
    TipoContratacao update(TipoContratacao tipoContratacao);

    /**
     * Partially updates a tipoContratacao.
     *
     * @param tipoContratacao the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoContratacao> partialUpdate(TipoContratacao tipoContratacao);

    /**
     * Get all the tipoContratacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoContratacao> findAll(Pageable pageable);

    /**
     * Get the "id" tipoContratacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoContratacao> findOne(Long id);

    /**
     * Delete the "id" tipoContratacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
