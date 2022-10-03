package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoProduto;
import br.com.jhisolution.user.hunters.repository.FotoProdutoRepository;
import br.com.jhisolution.user.hunters.service.FotoProdutoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoProduto}.
 */
@Service
@Transactional
public class FotoProdutoServiceImpl implements FotoProdutoService {

    private final Logger log = LoggerFactory.getLogger(FotoProdutoServiceImpl.class);

    private final FotoProdutoRepository fotoProdutoRepository;

    public FotoProdutoServiceImpl(FotoProdutoRepository fotoProdutoRepository) {
        this.fotoProdutoRepository = fotoProdutoRepository;
    }

    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        log.debug("Request to save FotoProduto : {}", fotoProduto);
        return fotoProdutoRepository.save(fotoProduto);
    }

    @Override
    public FotoProduto update(FotoProduto fotoProduto) {
        log.debug("Request to save FotoProduto : {}", fotoProduto);
        return fotoProdutoRepository.save(fotoProduto);
    }

    @Override
    public Optional<FotoProduto> partialUpdate(FotoProduto fotoProduto) {
        log.debug("Request to partially update FotoProduto : {}", fotoProduto);

        return fotoProdutoRepository
            .findById(fotoProduto.getId())
            .map(existingFotoProduto -> {
                if (fotoProduto.getConteudo() != null) {
                    existingFotoProduto.setConteudo(fotoProduto.getConteudo());
                }
                if (fotoProduto.getConteudoContentType() != null) {
                    existingFotoProduto.setConteudoContentType(fotoProduto.getConteudoContentType());
                }

                return existingFotoProduto;
            })
            .map(fotoProdutoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoProduto> findAll(Pageable pageable) {
        log.debug("Request to get all FotoProdutos");
        return fotoProdutoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoProduto> findOne(Long id) {
        log.debug("Request to get FotoProduto : {}", id);
        return fotoProdutoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoProduto : {}", id);
        fotoProdutoRepository.deleteById(id);
    }
}
