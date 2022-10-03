package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.ReceberDe;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ReceberDe}.
 */
public interface ReceberDeService {
    /**
     * Save a receberDe.
     *
     * @param receberDe the entity to save.
     * @return the persisted entity.
     */
    ReceberDe save(ReceberDe receberDe);

    /**
     * Updates a receberDe.
     *
     * @param receberDe the entity to update.
     * @return the persisted entity.
     */
    ReceberDe update(ReceberDe receberDe);

    /**
     * Partially updates a receberDe.
     *
     * @param receberDe the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReceberDe> partialUpdate(ReceberDe receberDe);

    /**
     * Get all the receberDes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReceberDe> findAll(Pageable pageable);

    /**
     * Get the "id" receberDe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReceberDe> findOne(Long id);

    /**
     * Delete the "id" receberDe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
