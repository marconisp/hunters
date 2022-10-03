package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.SaidaEstoque;
import br.com.jhisolution.user.hunters.repository.SaidaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.SaidaEstoqueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SaidaEstoque}.
 */
@Service
@Transactional
public class SaidaEstoqueServiceImpl implements SaidaEstoqueService {

    private final Logger log = LoggerFactory.getLogger(SaidaEstoqueServiceImpl.class);

    private final SaidaEstoqueRepository saidaEstoqueRepository;

    public SaidaEstoqueServiceImpl(SaidaEstoqueRepository saidaEstoqueRepository) {
        this.saidaEstoqueRepository = saidaEstoqueRepository;
    }

    @Override
    public SaidaEstoque save(SaidaEstoque saidaEstoque) {
        log.debug("Request to save SaidaEstoque : {}", saidaEstoque);
        return saidaEstoqueRepository.save(saidaEstoque);
    }

    @Override
    public SaidaEstoque update(SaidaEstoque saidaEstoque) {
        log.debug("Request to save SaidaEstoque : {}", saidaEstoque);
        return saidaEstoqueRepository.save(saidaEstoque);
    }

    @Override
    public Optional<SaidaEstoque> partialUpdate(SaidaEstoque saidaEstoque) {
        log.debug("Request to partially update SaidaEstoque : {}", saidaEstoque);

        return saidaEstoqueRepository
            .findById(saidaEstoque.getId())
            .map(existingSaidaEstoque -> {
                if (saidaEstoque.getData() != null) {
                    existingSaidaEstoque.setData(saidaEstoque.getData());
                }
                if (saidaEstoque.getQtde() != null) {
                    existingSaidaEstoque.setQtde(saidaEstoque.getQtde());
                }
                if (saidaEstoque.getValorUnitario() != null) {
                    existingSaidaEstoque.setValorUnitario(saidaEstoque.getValorUnitario());
                }
                if (saidaEstoque.getObs() != null) {
                    existingSaidaEstoque.setObs(saidaEstoque.getObs());
                }

                return existingSaidaEstoque;
            })
            .map(saidaEstoqueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaidaEstoque> findAll(Pageable pageable) {
        log.debug("Request to get all SaidaEstoques");
        return saidaEstoqueRepository.findAll(pageable);
    }

    public Page<SaidaEstoque> findAllWithEagerRelationships(Pageable pageable) {
        return saidaEstoqueRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SaidaEstoque> findOne(Long id) {
        log.debug("Request to get SaidaEstoque : {}", id);
        return saidaEstoqueRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SaidaEstoque : {}", id);
        saidaEstoqueRepository.deleteById(id);
    }
}
