package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.AcompanhamentoAluno;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AcompanhamentoAluno}.
 */
public interface AcompanhamentoAlunoService {
    /**
     * Save a acompanhamentoAluno.
     *
     * @param acompanhamentoAluno the entity to save.
     * @return the persisted entity.
     */
    AcompanhamentoAluno save(AcompanhamentoAluno acompanhamentoAluno);

    /**
     * Updates a acompanhamentoAluno.
     *
     * @param acompanhamentoAluno the entity to update.
     * @return the persisted entity.
     */
    AcompanhamentoAluno update(AcompanhamentoAluno acompanhamentoAluno);

    /**
     * Partially updates a acompanhamentoAluno.
     *
     * @param acompanhamentoAluno the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AcompanhamentoAluno> partialUpdate(AcompanhamentoAluno acompanhamentoAluno);

    /**
     * Get all the acompanhamentoAlunos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcompanhamentoAluno> findAll(Pageable pageable);

    /**
     * Get the "id" acompanhamentoAluno.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcompanhamentoAluno> findOne(Long id);

    /**
     * Delete the "id" acompanhamentoAluno.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
