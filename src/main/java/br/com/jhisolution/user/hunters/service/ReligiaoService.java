package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Religiao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Religiao}.
 */
public interface ReligiaoService {
    /**
     * Save a religiao.
     *
     * @param religiao the entity to save.
     * @return the persisted entity.
     */
    Religiao save(Religiao religiao);

    /**
     * Updates a religiao.
     *
     * @param religiao the entity to update.
     * @return the persisted entity.
     */
    Religiao update(Religiao religiao);

    /**
     * Partially updates a religiao.
     *
     * @param religiao the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Religiao> partialUpdate(Religiao religiao);

    /**
     * Get all the religiaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Religiao> findAll(Pageable pageable);

    /**
     * Get the "id" religiao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Religiao> findOne(Long id);

    /**
     * Delete the "id" religiao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
