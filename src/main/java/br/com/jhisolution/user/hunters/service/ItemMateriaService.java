package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.ItemMateria;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ItemMateria}.
 */
public interface ItemMateriaService {
    /**
     * Save a itemMateria.
     *
     * @param itemMateria the entity to save.
     * @return the persisted entity.
     */
    ItemMateria save(ItemMateria itemMateria);

    /**
     * Updates a itemMateria.
     *
     * @param itemMateria the entity to update.
     * @return the persisted entity.
     */
    ItemMateria update(ItemMateria itemMateria);

    /**
     * Partially updates a itemMateria.
     *
     * @param itemMateria the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemMateria> partialUpdate(ItemMateria itemMateria);

    /**
     * Get all the itemMaterias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemMateria> findAll(Pageable pageable);

    /**
     * Get all the itemMaterias with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemMateria> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" itemMateria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemMateria> findOne(Long id);

    /**
     * Delete the "id" itemMateria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
