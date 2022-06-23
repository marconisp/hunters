package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Documento;
import br.com.jhisolution.user.hunters.repository.DocumentoRepository;
import br.com.jhisolution.user.hunters.service.DocumentoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Documento}.
 */
@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService {

    private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private final DocumentoRepository documentoRepository;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    @Override
    public Documento save(Documento documento) {
        log.debug("Request to save Documento : {}", documento);
        return documentoRepository.save(documento);
    }

    @Override
    public Documento update(Documento documento) {
        log.debug("Request to save Documento : {}", documento);
        return documentoRepository.save(documento);
    }

    @Override
    public Optional<Documento> partialUpdate(Documento documento) {
        log.debug("Request to partially update Documento : {}", documento);

        return documentoRepository
            .findById(documento.getId())
            .map(existingDocumento -> {
                if (documento.getDescricao() != null) {
                    existingDocumento.setDescricao(documento.getDescricao());
                }

                return existingDocumento;
            })
            .map(documentoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Documento> findAll(Pageable pageable) {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Documento> findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Documento : {}", id);
        documentoRepository.deleteById(id);
    }
}
