package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoDocumento;
import br.com.jhisolution.user.hunters.repository.FotoDocumentoRepository;
import br.com.jhisolution.user.hunters.service.FotoDocumentoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoDocumento}.
 */
@Service
@Transactional
public class FotoDocumentoServiceImpl implements FotoDocumentoService {

    private final Logger log = LoggerFactory.getLogger(FotoDocumentoServiceImpl.class);

    private final FotoDocumentoRepository fotoDocumentoRepository;

    public FotoDocumentoServiceImpl(FotoDocumentoRepository fotoDocumentoRepository) {
        this.fotoDocumentoRepository = fotoDocumentoRepository;
    }

    @Override
    public FotoDocumento save(FotoDocumento fotoDocumento) {
        log.debug("Request to save FotoDocumento : {}", fotoDocumento);
        return fotoDocumentoRepository.save(fotoDocumento);
    }

    @Override
    public FotoDocumento update(FotoDocumento fotoDocumento) {
        log.debug("Request to save FotoDocumento : {}", fotoDocumento);
        return fotoDocumentoRepository.save(fotoDocumento);
    }

    @Override
    public Optional<FotoDocumento> partialUpdate(FotoDocumento fotoDocumento) {
        log.debug("Request to partially update FotoDocumento : {}", fotoDocumento);

        return fotoDocumentoRepository
            .findById(fotoDocumento.getId())
            .map(existingFotoDocumento -> {
                if (fotoDocumento.getConteudo() != null) {
                    existingFotoDocumento.setConteudo(fotoDocumento.getConteudo());
                }
                if (fotoDocumento.getConteudoContentType() != null) {
                    existingFotoDocumento.setConteudoContentType(fotoDocumento.getConteudoContentType());
                }

                return existingFotoDocumento;
            })
            .map(fotoDocumentoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoDocumento> findAll(Pageable pageable) {
        log.debug("Request to get all FotoDocumentos");
        return fotoDocumentoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoDocumento> findOne(Long id) {
        log.debug("Request to get FotoDocumento : {}", id);
        return fotoDocumentoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoDocumento : {}", id);
        fotoDocumentoRepository.deleteById(id);
    }
}
