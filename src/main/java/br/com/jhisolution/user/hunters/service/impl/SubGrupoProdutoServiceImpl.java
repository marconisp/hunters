package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.SubGrupoProduto;
import br.com.jhisolution.user.hunters.repository.SubGrupoProdutoRepository;
import br.com.jhisolution.user.hunters.service.SubGrupoProdutoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubGrupoProduto}.
 */
@Service
@Transactional
public class SubGrupoProdutoServiceImpl implements SubGrupoProdutoService {

    private final Logger log = LoggerFactory.getLogger(SubGrupoProdutoServiceImpl.class);

    private final SubGrupoProdutoRepository subGrupoProdutoRepository;

    public SubGrupoProdutoServiceImpl(SubGrupoProdutoRepository subGrupoProdutoRepository) {
        this.subGrupoProdutoRepository = subGrupoProdutoRepository;
    }

    @Override
    public SubGrupoProduto save(SubGrupoProduto subGrupoProduto) {
        log.debug("Request to save SubGrupoProduto : {}", subGrupoProduto);
        return subGrupoProdutoRepository.save(subGrupoProduto);
    }

    @Override
    public SubGrupoProduto update(SubGrupoProduto subGrupoProduto) {
        log.debug("Request to save SubGrupoProduto : {}", subGrupoProduto);
        return subGrupoProdutoRepository.save(subGrupoProduto);
    }

    @Override
    public Optional<SubGrupoProduto> partialUpdate(SubGrupoProduto subGrupoProduto) {
        log.debug("Request to partially update SubGrupoProduto : {}", subGrupoProduto);

        return subGrupoProdutoRepository
            .findById(subGrupoProduto.getId())
            .map(existingSubGrupoProduto -> {
                if (subGrupoProduto.getNome() != null) {
                    existingSubGrupoProduto.setNome(subGrupoProduto.getNome());
                }
                if (subGrupoProduto.getObs() != null) {
                    existingSubGrupoProduto.setObs(subGrupoProduto.getObs());
                }

                return existingSubGrupoProduto;
            })
            .map(subGrupoProdutoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubGrupoProduto> findAll(Pageable pageable) {
        log.debug("Request to get all SubGrupoProdutos");
        return subGrupoProdutoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubGrupoProduto> findOne(Long id) {
        log.debug("Request to get SubGrupoProduto : {}", id);
        return subGrupoProdutoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubGrupoProduto : {}", id);
        subGrupoProdutoRepository.deleteById(id);
    }
}
