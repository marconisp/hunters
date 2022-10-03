package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.EntradaEstoque;
import br.com.jhisolution.user.hunters.repository.EntradaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.EntradaEstoqueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EntradaEstoque}.
 */
@Service
@Transactional
public class EntradaEstoqueServiceImpl implements EntradaEstoqueService {

    private final Logger log = LoggerFactory.getLogger(EntradaEstoqueServiceImpl.class);

    private final EntradaEstoqueRepository entradaEstoqueRepository;

    public EntradaEstoqueServiceImpl(EntradaEstoqueRepository entradaEstoqueRepository) {
        this.entradaEstoqueRepository = entradaEstoqueRepository;
    }

    @Override
    public EntradaEstoque save(EntradaEstoque entradaEstoque) {
        log.debug("Request to save EntradaEstoque : {}", entradaEstoque);
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    @Override
    public EntradaEstoque update(EntradaEstoque entradaEstoque) {
        log.debug("Request to save EntradaEstoque : {}", entradaEstoque);
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    @Override
    public Optional<EntradaEstoque> partialUpdate(EntradaEstoque entradaEstoque) {
        log.debug("Request to partially update EntradaEstoque : {}", entradaEstoque);

        return entradaEstoqueRepository
            .findById(entradaEstoque.getId())
            .map(existingEntradaEstoque -> {
                if (entradaEstoque.getData() != null) {
                    existingEntradaEstoque.setData(entradaEstoque.getData());
                }
                if (entradaEstoque.getQtde() != null) {
                    existingEntradaEstoque.setQtde(entradaEstoque.getQtde());
                }
                if (entradaEstoque.getValorUnitario() != null) {
                    existingEntradaEstoque.setValorUnitario(entradaEstoque.getValorUnitario());
                }
                if (entradaEstoque.getObs() != null) {
                    existingEntradaEstoque.setObs(entradaEstoque.getObs());
                }

                return existingEntradaEstoque;
            })
            .map(entradaEstoqueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntradaEstoque> findAll(Pageable pageable) {
        log.debug("Request to get all EntradaEstoques");
        return entradaEstoqueRepository.findAll(pageable);
    }

    public Page<EntradaEstoque> findAllWithEagerRelationships(Pageable pageable) {
        return entradaEstoqueRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntradaEstoque> findOne(Long id) {
        log.debug("Request to get EntradaEstoque : {}", id);
        return entradaEstoqueRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntradaEstoque : {}", id);
        entradaEstoqueRepository.deleteById(id);
    }
}
