package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Estoque;
import br.com.jhisolution.user.hunters.repository.EstoqueRepository;
import br.com.jhisolution.user.hunters.service.EstoqueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Estoque}.
 */
@Service
@Transactional
public class EstoqueServiceImpl implements EstoqueService {

    private final Logger log = LoggerFactory.getLogger(EstoqueServiceImpl.class);

    private final EstoqueRepository estoqueRepository;

    public EstoqueServiceImpl(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public Estoque save(Estoque estoque) {
        log.debug("Request to save Estoque : {}", estoque);
        return estoqueRepository.save(estoque);
    }

    @Override
    public Estoque update(Estoque estoque) {
        log.debug("Request to save Estoque : {}", estoque);
        return estoqueRepository.save(estoque);
    }

    @Override
    public Optional<Estoque> partialUpdate(Estoque estoque) {
        log.debug("Request to partially update Estoque : {}", estoque);

        return estoqueRepository
            .findById(estoque.getId())
            .map(existingEstoque -> {
                if (estoque.getData() != null) {
                    existingEstoque.setData(estoque.getData());
                }
                if (estoque.getQtde() != null) {
                    existingEstoque.setQtde(estoque.getQtde());
                }
                if (estoque.getValorUnitario() != null) {
                    existingEstoque.setValorUnitario(estoque.getValorUnitario());
                }
                if (estoque.getValorTotal() != null) {
                    existingEstoque.setValorTotal(estoque.getValorTotal());
                }

                return existingEstoque;
            })
            .map(estoqueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Estoque> findAll(Pageable pageable) {
        log.debug("Request to get all Estoques");
        return estoqueRepository.findAll(pageable);
    }

    public Page<Estoque> findAllWithEagerRelationships(Pageable pageable) {
        return estoqueRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estoque> findOne(Long id) {
        log.debug("Request to get Estoque : {}", id);
        return estoqueRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estoque : {}", id);
        estoqueRepository.deleteById(id);
    }
}
