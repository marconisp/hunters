package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.CaracteristicasPsiquicas;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CaracteristicasPsiquicas}.
 */
public interface CaracteristicasPsiquicasService {
    /**
     * Save a caracteristicasPsiquicas.
     *
     * @param caracteristicasPsiquicas the entity to save.
     * @return the persisted entity.
     */
    CaracteristicasPsiquicas save(CaracteristicasPsiquicas caracteristicasPsiquicas);

    /**
     * Updates a caracteristicasPsiquicas.
     *
     * @param caracteristicasPsiquicas the entity to update.
     * @return the persisted entity.
     */
    CaracteristicasPsiquicas update(CaracteristicasPsiquicas caracteristicasPsiquicas);

    /**
     * Partially updates a caracteristicasPsiquicas.
     *
     * @param caracteristicasPsiquicas the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CaracteristicasPsiquicas> partialUpdate(CaracteristicasPsiquicas caracteristicasPsiquicas);

    /**
     * Get all the caracteristicasPsiquicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CaracteristicasPsiquicas> findAll(Pageable pageable);

    /**
     * Get the "id" caracteristicasPsiquicas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CaracteristicasPsiquicas> findOne(Long id);

    /**
     * Delete the "id" caracteristicasPsiquicas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
