package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoExameMedico;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoExameMedico}.
 */
public interface FotoExameMedicoService {
    /**
     * Save a fotoExameMedico.
     *
     * @param fotoExameMedico the entity to save.
     * @return the persisted entity.
     */
    FotoExameMedico save(FotoExameMedico fotoExameMedico);

    /**
     * Updates a fotoExameMedico.
     *
     * @param fotoExameMedico the entity to update.
     * @return the persisted entity.
     */
    FotoExameMedico update(FotoExameMedico fotoExameMedico);

    /**
     * Partially updates a fotoExameMedico.
     *
     * @param fotoExameMedico the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoExameMedico> partialUpdate(FotoExameMedico fotoExameMedico);

    /**
     * Get all the fotoExameMedicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoExameMedico> findAll(Pageable pageable);

    /**
     * Get the "id" fotoExameMedico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoExameMedico> findOne(Long id);

    /**
     * Delete the "id" fotoExameMedico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
