package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.FotoIcon;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FotoIcon}.
 */
public interface FotoIconService {
    /**
     * Save a fotoIcon.
     *
     * @param fotoIcon the entity to save.
     * @return the persisted entity.
     */
    FotoIcon save(FotoIcon fotoIcon);

    /**
     * Updates a fotoIcon.
     *
     * @param fotoIcon the entity to update.
     * @return the persisted entity.
     */
    FotoIcon update(FotoIcon fotoIcon);

    /**
     * Partially updates a fotoIcon.
     *
     * @param fotoIcon the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoIcon> partialUpdate(FotoIcon fotoIcon);

    /**
     * Get all the fotoIcons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoIcon> findAll(Pageable pageable);

    /**
     * Get the "id" fotoIcon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoIcon> findOne(Long id);

    /**
     * Delete the "id" fotoIcon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
