package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoExameMedico;
import br.com.jhisolution.user.hunters.repository.FotoExameMedicoRepository;
import br.com.jhisolution.user.hunters.service.FotoExameMedicoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoExameMedico}.
 */
@Service
@Transactional
public class FotoExameMedicoServiceImpl implements FotoExameMedicoService {

    private final Logger log = LoggerFactory.getLogger(FotoExameMedicoServiceImpl.class);

    private final FotoExameMedicoRepository fotoExameMedicoRepository;

    public FotoExameMedicoServiceImpl(FotoExameMedicoRepository fotoExameMedicoRepository) {
        this.fotoExameMedicoRepository = fotoExameMedicoRepository;
    }

    @Override
    public FotoExameMedico save(FotoExameMedico fotoExameMedico) {
        log.debug("Request to save FotoExameMedico : {}", fotoExameMedico);
        return fotoExameMedicoRepository.save(fotoExameMedico);
    }

    @Override
    public FotoExameMedico update(FotoExameMedico fotoExameMedico) {
        log.debug("Request to save FotoExameMedico : {}", fotoExameMedico);
        return fotoExameMedicoRepository.save(fotoExameMedico);
    }

    @Override
    public Optional<FotoExameMedico> partialUpdate(FotoExameMedico fotoExameMedico) {
        log.debug("Request to partially update FotoExameMedico : {}", fotoExameMedico);

        return fotoExameMedicoRepository
            .findById(fotoExameMedico.getId())
            .map(existingFotoExameMedico -> {
                if (fotoExameMedico.getFoto() != null) {
                    existingFotoExameMedico.setFoto(fotoExameMedico.getFoto());
                }
                if (fotoExameMedico.getFotoContentType() != null) {
                    existingFotoExameMedico.setFotoContentType(fotoExameMedico.getFotoContentType());
                }

                return existingFotoExameMedico;
            })
            .map(fotoExameMedicoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoExameMedico> findAll(Pageable pageable) {
        log.debug("Request to get all FotoExameMedicos");
        return fotoExameMedicoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoExameMedico> findOne(Long id) {
        log.debug("Request to get FotoExameMedico : {}", id);
        return fotoExameMedicoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoExameMedico : {}", id);
        fotoExameMedicoRepository.deleteById(id);
    }
}
