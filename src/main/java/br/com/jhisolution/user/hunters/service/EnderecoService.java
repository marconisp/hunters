package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.domain.Endereco;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Endereco}.
 */
public interface EnderecoService {
    /**
     * Save a endereco.
     *
     * @param endereco the entity to save.
     * @return the persisted entity.
     */
    Endereco save(Endereco endereco);

    /**
     * Updates a endereco.
     *
     * @param endereco the entity to update.
     * @return the persisted entity.
     */
    Endereco update(Endereco endereco);

    /**
     * Partially updates a endereco.
     *
     * @param endereco the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Endereco> partialUpdate(Endereco endereco);

    /**
     * Get all the enderecos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Endereco> findAll(Pageable pageable);
    Page<Endereco> findAllByDadosPessoaisId(Long id, Pageable pageable);
    /**
     * Get the "id" endereco.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Endereco> findOne(Long id);

    /**
     * Delete the "id" endereco.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
