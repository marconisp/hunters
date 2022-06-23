package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DadosPessoais}.
 */
public interface DadosPessoaisService {
    /**
     * Save a dadosPessoais.
     *
     * @param dadosPessoais the entity to save.
     * @return the persisted entity.
     */
    DadosPessoais save(DadosPessoais dadosPessoais);

    /**
     * Updates a dadosPessoais.
     *
     * @param dadosPessoais the entity to update.
     * @return the persisted entity.
     */
    DadosPessoais update(DadosPessoais dadosPessoais);

    /**
     * Partially updates a dadosPessoais.
     *
     * @param dadosPessoais the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DadosPessoais> partialUpdate(DadosPessoais dadosPessoais);

    /**
     * Get all the dadosPessoais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DadosPessoais> findAll(Pageable pageable);

    /**
     * Get all the dadosPessoais with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DadosPessoais> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dadosPessoais.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DadosPessoais> findOne(Long id);

    /**
     * Delete the "id" dadosPessoais.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
