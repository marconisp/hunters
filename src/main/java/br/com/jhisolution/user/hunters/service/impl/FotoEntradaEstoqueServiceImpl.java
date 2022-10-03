package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoEntradaEstoque;
import br.com.jhisolution.user.hunters.repository.FotoEntradaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.FotoEntradaEstoqueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoEntradaEstoque}.
 */
@Service
@Transactional
public class FotoEntradaEstoqueServiceImpl implements FotoEntradaEstoqueService {

    private final Logger log = LoggerFactory.getLogger(FotoEntradaEstoqueServiceImpl.class);

    private final FotoEntradaEstoqueRepository fotoEntradaEstoqueRepository;

    public FotoEntradaEstoqueServiceImpl(FotoEntradaEstoqueRepository fotoEntradaEstoqueRepository) {
        this.fotoEntradaEstoqueRepository = fotoEntradaEstoqueRepository;
    }

    @Override
    public FotoEntradaEstoque save(FotoEntradaEstoque fotoEntradaEstoque) {
        log.debug("Request to save FotoEntradaEstoque : {}", fotoEntradaEstoque);
        return fotoEntradaEstoqueRepository.save(fotoEntradaEstoque);
    }

    @Override
    public FotoEntradaEstoque update(FotoEntradaEstoque fotoEntradaEstoque) {
        log.debug("Request to save FotoEntradaEstoque : {}", fotoEntradaEstoque);
        return fotoEntradaEstoqueRepository.save(fotoEntradaEstoque);
    }

    @Override
    public Optional<FotoEntradaEstoque> partialUpdate(FotoEntradaEstoque fotoEntradaEstoque) {
        log.debug("Request to partially update FotoEntradaEstoque : {}", fotoEntradaEstoque);

        return fotoEntradaEstoqueRepository
            .findById(fotoEntradaEstoque.getId())
            .map(existingFotoEntradaEstoque -> {
                if (fotoEntradaEstoque.getConteudo() != null) {
                    existingFotoEntradaEstoque.setConteudo(fotoEntradaEstoque.getConteudo());
                }
                if (fotoEntradaEstoque.getConteudoContentType() != null) {
                    existingFotoEntradaEstoque.setConteudoContentType(fotoEntradaEstoque.getConteudoContentType());
                }

                return existingFotoEntradaEstoque;
            })
            .map(fotoEntradaEstoqueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoEntradaEstoque> findAll(Pageable pageable) {
        log.debug("Request to get all FotoEntradaEstoques");
        return fotoEntradaEstoqueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoEntradaEstoque> findOne(Long id) {
        log.debug("Request to get FotoEntradaEstoque : {}", id);
        return fotoEntradaEstoqueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoEntradaEstoque : {}", id);
        fotoEntradaEstoqueRepository.deleteById(id);
    }
}
