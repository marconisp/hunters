package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.GrupoProduto;
import br.com.jhisolution.user.hunters.repository.GrupoProdutoRepository;
import br.com.jhisolution.user.hunters.service.GrupoProdutoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GrupoProduto}.
 */
@Service
@Transactional
public class GrupoProdutoServiceImpl implements GrupoProdutoService {

    private final Logger log = LoggerFactory.getLogger(GrupoProdutoServiceImpl.class);

    private final GrupoProdutoRepository grupoProdutoRepository;

    public GrupoProdutoServiceImpl(GrupoProdutoRepository grupoProdutoRepository) {
        this.grupoProdutoRepository = grupoProdutoRepository;
    }

    @Override
    public GrupoProduto save(GrupoProduto grupoProduto) {
        log.debug("Request to save GrupoProduto : {}", grupoProduto);
        return grupoProdutoRepository.save(grupoProduto);
    }

    @Override
    public GrupoProduto update(GrupoProduto grupoProduto) {
        log.debug("Request to save GrupoProduto : {}", grupoProduto);
        return grupoProdutoRepository.save(grupoProduto);
    }

    @Override
    public Optional<GrupoProduto> partialUpdate(GrupoProduto grupoProduto) {
        log.debug("Request to partially update GrupoProduto : {}", grupoProduto);

        return grupoProdutoRepository
            .findById(grupoProduto.getId())
            .map(existingGrupoProduto -> {
                if (grupoProduto.getNome() != null) {
                    existingGrupoProduto.setNome(grupoProduto.getNome());
                }
                if (grupoProduto.getObs() != null) {
                    existingGrupoProduto.setObs(grupoProduto.getObs());
                }

                return existingGrupoProduto;
            })
            .map(grupoProdutoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GrupoProduto> findAll(Pageable pageable) {
        log.debug("Request to get all GrupoProdutos");
        return grupoProdutoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GrupoProduto> findOne(Long id) {
        log.debug("Request to get GrupoProduto : {}", id);
        return grupoProdutoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GrupoProduto : {}", id);
        grupoProdutoRepository.deleteById(id);
    }
}
