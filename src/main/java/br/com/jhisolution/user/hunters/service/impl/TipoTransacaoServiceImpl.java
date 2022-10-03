package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.TipoTransacao;
import br.com.jhisolution.user.hunters.repository.TipoTransacaoRepository;
import br.com.jhisolution.user.hunters.service.TipoTransacaoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoTransacao}.
 */
@Service
@Transactional
public class TipoTransacaoServiceImpl implements TipoTransacaoService {

    private final Logger log = LoggerFactory.getLogger(TipoTransacaoServiceImpl.class);

    private final TipoTransacaoRepository tipoTransacaoRepository;

    public TipoTransacaoServiceImpl(TipoTransacaoRepository tipoTransacaoRepository) {
        this.tipoTransacaoRepository = tipoTransacaoRepository;
    }

    @Override
    public TipoTransacao save(TipoTransacao tipoTransacao) {
        log.debug("Request to save TipoTransacao : {}", tipoTransacao);
        return tipoTransacaoRepository.save(tipoTransacao);
    }

    @Override
    public TipoTransacao update(TipoTransacao tipoTransacao) {
        log.debug("Request to save TipoTransacao : {}", tipoTransacao);
        return tipoTransacaoRepository.save(tipoTransacao);
    }

    @Override
    public Optional<TipoTransacao> partialUpdate(TipoTransacao tipoTransacao) {
        log.debug("Request to partially update TipoTransacao : {}", tipoTransacao);

        return tipoTransacaoRepository
            .findById(tipoTransacao.getId())
            .map(existingTipoTransacao -> {
                if (tipoTransacao.getNome() != null) {
                    existingTipoTransacao.setNome(tipoTransacao.getNome());
                }

                return existingTipoTransacao;
            })
            .map(tipoTransacaoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoTransacao> findAll(Pageable pageable) {
        log.debug("Request to get all TipoTransacaos");
        return tipoTransacaoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoTransacao> findOne(Long id) {
        log.debug("Request to get TipoTransacao : {}", id);
        return tipoTransacaoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoTransacao : {}", id);
        tipoTransacaoRepository.deleteById(id);
    }
}
