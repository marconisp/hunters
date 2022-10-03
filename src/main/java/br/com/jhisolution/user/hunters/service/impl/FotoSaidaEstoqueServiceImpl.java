package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoSaidaEstoque;
import br.com.jhisolution.user.hunters.repository.FotoSaidaEstoqueRepository;
import br.com.jhisolution.user.hunters.service.FotoSaidaEstoqueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoSaidaEstoque}.
 */
@Service
@Transactional
public class FotoSaidaEstoqueServiceImpl implements FotoSaidaEstoqueService {

    private final Logger log = LoggerFactory.getLogger(FotoSaidaEstoqueServiceImpl.class);

    private final FotoSaidaEstoqueRepository fotoSaidaEstoqueRepository;

    public FotoSaidaEstoqueServiceImpl(FotoSaidaEstoqueRepository fotoSaidaEstoqueRepository) {
        this.fotoSaidaEstoqueRepository = fotoSaidaEstoqueRepository;
    }

    @Override
    public FotoSaidaEstoque save(FotoSaidaEstoque fotoSaidaEstoque) {
        log.debug("Request to save FotoSaidaEstoque : {}", fotoSaidaEstoque);
        return fotoSaidaEstoqueRepository.save(fotoSaidaEstoque);
    }

    @Override
    public FotoSaidaEstoque update(FotoSaidaEstoque fotoSaidaEstoque) {
        log.debug("Request to save FotoSaidaEstoque : {}", fotoSaidaEstoque);
        return fotoSaidaEstoqueRepository.save(fotoSaidaEstoque);
    }

    @Override
    public Optional<FotoSaidaEstoque> partialUpdate(FotoSaidaEstoque fotoSaidaEstoque) {
        log.debug("Request to partially update FotoSaidaEstoque : {}", fotoSaidaEstoque);

        return fotoSaidaEstoqueRepository
            .findById(fotoSaidaEstoque.getId())
            .map(existingFotoSaidaEstoque -> {
                if (fotoSaidaEstoque.getConteudo() != null) {
                    existingFotoSaidaEstoque.setConteudo(fotoSaidaEstoque.getConteudo());
                }
                if (fotoSaidaEstoque.getConteudoContentType() != null) {
                    existingFotoSaidaEstoque.setConteudoContentType(fotoSaidaEstoque.getConteudoContentType());
                }

                return existingFotoSaidaEstoque;
            })
            .map(fotoSaidaEstoqueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoSaidaEstoque> findAll(Pageable pageable) {
        log.debug("Request to get all FotoSaidaEstoques");
        return fotoSaidaEstoqueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoSaidaEstoque> findOne(Long id) {
        log.debug("Request to get FotoSaidaEstoque : {}", id);
        return fotoSaidaEstoqueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoSaidaEstoque : {}", id);
        fotoSaidaEstoqueRepository.deleteById(id);
    }
}
