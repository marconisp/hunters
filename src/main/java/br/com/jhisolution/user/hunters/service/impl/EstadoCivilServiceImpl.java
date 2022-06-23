package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.EstadoCivil;
import br.com.jhisolution.user.hunters.repository.EstadoCivilRepository;
import br.com.jhisolution.user.hunters.service.EstadoCivilService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstadoCivil}.
 */
@Service
@Transactional
public class EstadoCivilServiceImpl implements EstadoCivilService {

    private final Logger log = LoggerFactory.getLogger(EstadoCivilServiceImpl.class);

    private final EstadoCivilRepository estadoCivilRepository;

    public EstadoCivilServiceImpl(EstadoCivilRepository estadoCivilRepository) {
        this.estadoCivilRepository = estadoCivilRepository;
    }

    @Override
    public EstadoCivil save(EstadoCivil estadoCivil) {
        log.debug("Request to save EstadoCivil : {}", estadoCivil);
        return estadoCivilRepository.save(estadoCivil);
    }

    @Override
    public EstadoCivil update(EstadoCivil estadoCivil) {
        log.debug("Request to save EstadoCivil : {}", estadoCivil);
        return estadoCivilRepository.save(estadoCivil);
    }

    @Override
    public Optional<EstadoCivil> partialUpdate(EstadoCivil estadoCivil) {
        log.debug("Request to partially update EstadoCivil : {}", estadoCivil);

        return estadoCivilRepository
            .findById(estadoCivil.getId())
            .map(existingEstadoCivil -> {
                if (estadoCivil.getCodigo() != null) {
                    existingEstadoCivil.setCodigo(estadoCivil.getCodigo());
                }
                if (estadoCivil.getDescricao() != null) {
                    existingEstadoCivil.setDescricao(estadoCivil.getDescricao());
                }

                return existingEstadoCivil;
            })
            .map(estadoCivilRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoCivil> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoCivils");
        return estadoCivilRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoCivil> findOne(Long id) {
        log.debug("Request to get EstadoCivil : {}", id);
        return estadoCivilRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoCivil : {}", id);
        estadoCivilRepository.deleteById(id);
    }
}
