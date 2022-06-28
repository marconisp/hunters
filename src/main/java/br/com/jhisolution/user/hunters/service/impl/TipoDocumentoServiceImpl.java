package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.TipoDocumento;
import br.com.jhisolution.user.hunters.repository.TipoDocumentoRepository;
import br.com.jhisolution.user.hunters.service.TipoDocumentoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoDocumento}.
 */
@Service
@Transactional
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoServiceImpl.class);

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoServiceImpl(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public TipoDocumento save(TipoDocumento tipoDocumento) {
        log.debug("Request to save TipoDocumento : {}", tipoDocumento);
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    @Override
    public TipoDocumento update(TipoDocumento tipoDocumento) {
        log.debug("Request to save TipoDocumento : {}", tipoDocumento);
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    @Override
    public Optional<TipoDocumento> partialUpdate(TipoDocumento tipoDocumento) {
        log.debug("Request to partially update TipoDocumento : {}", tipoDocumento);

        return tipoDocumentoRepository
            .findById(tipoDocumento.getId())
            .map(existingTipoDocumento -> {
                if (tipoDocumento.getCodigo() != null) {
                    existingTipoDocumento.setCodigo(tipoDocumento.getCodigo());
                }
                if (tipoDocumento.getNome() != null) {
                    existingTipoDocumento.setNome(tipoDocumento.getNome());
                }
                if (tipoDocumento.getDescricao() != null) {
                    existingTipoDocumento.setDescricao(tipoDocumento.getDescricao());
                }

                return existingTipoDocumento;
            })
            .map(tipoDocumentoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoDocumento> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDocumentos");
        return tipoDocumentoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDocumento> findOne(Long id) {
        log.debug("Request to get TipoDocumento : {}", id);
        return tipoDocumentoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDocumento : {}", id);
        tipoDocumentoRepository.deleteById(id);
    }
}
