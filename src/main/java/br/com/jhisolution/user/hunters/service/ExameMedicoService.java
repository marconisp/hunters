package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.ExameMedico;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ExameMedico}.
 */
public interface ExameMedicoService {
    /**
     * Save a exameMedico.
     *
     * @param exameMedico the entity to save.
     * @return the persisted entity.
     */
    ExameMedico save(ExameMedico exameMedico);

    /**
     * Updates a exameMedico.
     *
     * @param exameMedico the entity to update.
     * @return the persisted entity.
     */
    ExameMedico update(ExameMedico exameMedico);

    /**
     * Partially updates a exameMedico.
     *
     * @param exameMedico the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExameMedico> partialUpdate(ExameMedico exameMedico);

    /**
     * Get all the exameMedicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExameMedico> findAll(Pageable pageable);

    /**
     * Get the "id" exameMedico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExameMedico> findOne(Long id);

    /**
     * Delete the "id" exameMedico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
