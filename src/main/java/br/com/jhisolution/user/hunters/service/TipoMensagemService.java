package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.TipoMensagem;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoMensagem}.
 */
public interface TipoMensagemService {
    /**
     * Save a tipoMensagem.
     *
     * @param tipoMensagem the entity to save.
     * @return the persisted entity.
     */
    TipoMensagem save(TipoMensagem tipoMensagem);

    /**
     * Updates a tipoMensagem.
     *
     * @param tipoMensagem the entity to update.
     * @return the persisted entity.
     */
    TipoMensagem update(TipoMensagem tipoMensagem);

    /**
     * Partially updates a tipoMensagem.
     *
     * @param tipoMensagem the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoMensagem> partialUpdate(TipoMensagem tipoMensagem);

    /**
     * Get all the tipoMensagems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoMensagem> findAll(Pageable pageable);

    /**
     * Get the "id" tipoMensagem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoMensagem> findOne(Long id);

    /**
     * Delete the "id" tipoMensagem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
