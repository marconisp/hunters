package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.TipoMensagem;
import br.com.jhisolution.user.hunters.repository.TipoMensagemRepository;
import br.com.jhisolution.user.hunters.service.TipoMensagemService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoMensagem}.
 */
@Service
@Transactional
public class TipoMensagemServiceImpl implements TipoMensagemService {

    private final Logger log = LoggerFactory.getLogger(TipoMensagemServiceImpl.class);

    private final TipoMensagemRepository tipoMensagemRepository;

    public TipoMensagemServiceImpl(TipoMensagemRepository tipoMensagemRepository) {
        this.tipoMensagemRepository = tipoMensagemRepository;
    }

    @Override
    public TipoMensagem save(TipoMensagem tipoMensagem) {
        log.debug("Request to save TipoMensagem : {}", tipoMensagem);
        return tipoMensagemRepository.save(tipoMensagem);
    }

    @Override
    public TipoMensagem update(TipoMensagem tipoMensagem) {
        log.debug("Request to save TipoMensagem : {}", tipoMensagem);
        return tipoMensagemRepository.save(tipoMensagem);
    }

    @Override
    public Optional<TipoMensagem> partialUpdate(TipoMensagem tipoMensagem) {
        log.debug("Request to partially update TipoMensagem : {}", tipoMensagem);

        return tipoMensagemRepository
            .findById(tipoMensagem.getId())
            .map(existingTipoMensagem -> {
                if (tipoMensagem.getCodigo() != null) {
                    existingTipoMensagem.setCodigo(tipoMensagem.getCodigo());
                }
                if (tipoMensagem.getNome() != null) {
                    existingTipoMensagem.setNome(tipoMensagem.getNome());
                }
                if (tipoMensagem.getDescricao() != null) {
                    existingTipoMensagem.setDescricao(tipoMensagem.getDescricao());
                }

                return existingTipoMensagem;
            })
            .map(tipoMensagemRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoMensagem> findAll(Pageable pageable) {
        log.debug("Request to get all TipoMensagems");
        return tipoMensagemRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoMensagem> findOne(Long id) {
        log.debug("Request to get TipoMensagem : {}", id);
        return tipoMensagemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoMensagem : {}", id);
        tipoMensagemRepository.deleteById(id);
    }
}
