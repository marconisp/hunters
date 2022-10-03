package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.TipoContratacao;
import br.com.jhisolution.user.hunters.repository.TipoContratacaoRepository;
import br.com.jhisolution.user.hunters.service.TipoContratacaoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoContratacao}.
 */
@Service
@Transactional
public class TipoContratacaoServiceImpl implements TipoContratacaoService {

    private final Logger log = LoggerFactory.getLogger(TipoContratacaoServiceImpl.class);

    private final TipoContratacaoRepository tipoContratacaoRepository;

    public TipoContratacaoServiceImpl(TipoContratacaoRepository tipoContratacaoRepository) {
        this.tipoContratacaoRepository = tipoContratacaoRepository;
    }

    @Override
    public TipoContratacao save(TipoContratacao tipoContratacao) {
        log.debug("Request to save TipoContratacao : {}", tipoContratacao);
        return tipoContratacaoRepository.save(tipoContratacao);
    }

    @Override
    public TipoContratacao update(TipoContratacao tipoContratacao) {
        log.debug("Request to save TipoContratacao : {}", tipoContratacao);
        return tipoContratacaoRepository.save(tipoContratacao);
    }

    @Override
    public Optional<TipoContratacao> partialUpdate(TipoContratacao tipoContratacao) {
        log.debug("Request to partially update TipoContratacao : {}", tipoContratacao);

        return tipoContratacaoRepository
            .findById(tipoContratacao.getId())
            .map(existingTipoContratacao -> {
                if (tipoContratacao.getNome() != null) {
                    existingTipoContratacao.setNome(tipoContratacao.getNome());
                }

                return existingTipoContratacao;
            })
            .map(tipoContratacaoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoContratacao> findAll(Pageable pageable) {
        log.debug("Request to get all TipoContratacaos");
        return tipoContratacaoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoContratacao> findOne(Long id) {
        log.debug("Request to get TipoContratacao : {}", id);
        return tipoContratacaoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoContratacao : {}", id);
        tipoContratacaoRepository.deleteById(id);
    }
}
