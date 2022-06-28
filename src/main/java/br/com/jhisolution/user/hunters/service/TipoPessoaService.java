package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.TipoPessoa;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoPessoa}.
 */
public interface TipoPessoaService {
    /**
     * Save a tipoPessoa.
     *
     * @param tipoPessoa the entity to save.
     * @return the persisted entity.
     */
    TipoPessoa save(TipoPessoa tipoPessoa);

    /**
     * Updates a tipoPessoa.
     *
     * @param tipoPessoa the entity to update.
     * @return the persisted entity.
     */
    TipoPessoa update(TipoPessoa tipoPessoa);

    /**
     * Partially updates a tipoPessoa.
     *
     * @param tipoPessoa the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoPessoa> partialUpdate(TipoPessoa tipoPessoa);

    /**
     * Get all the tipoPessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoPessoa> findAll(Pageable pageable);

    /**
     * Get the "id" tipoPessoa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoPessoa> findOne(Long id);

    /**
     * Delete the "id" tipoPessoa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
