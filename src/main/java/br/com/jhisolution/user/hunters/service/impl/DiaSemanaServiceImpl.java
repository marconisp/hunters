package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.DiaSemana;
import br.com.jhisolution.user.hunters.repository.DiaSemanaRepository;
import br.com.jhisolution.user.hunters.service.DiaSemanaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DiaSemana}.
 */
@Service
@Transactional
public class DiaSemanaServiceImpl implements DiaSemanaService {

    private final Logger log = LoggerFactory.getLogger(DiaSemanaServiceImpl.class);

    private final DiaSemanaRepository diaSemanaRepository;

    public DiaSemanaServiceImpl(DiaSemanaRepository diaSemanaRepository) {
        this.diaSemanaRepository = diaSemanaRepository;
    }

    @Override
    public DiaSemana save(DiaSemana diaSemana) {
        log.debug("Request to save DiaSemana : {}", diaSemana);
        return diaSemanaRepository.save(diaSemana);
    }

    @Override
    public DiaSemana update(DiaSemana diaSemana) {
        log.debug("Request to save DiaSemana : {}", diaSemana);
        return diaSemanaRepository.save(diaSemana);
    }

    @Override
    public Optional<DiaSemana> partialUpdate(DiaSemana diaSemana) {
        log.debug("Request to partially update DiaSemana : {}", diaSemana);

        return diaSemanaRepository
            .findById(diaSemana.getId())
            .map(existingDiaSemana -> {
                if (diaSemana.getNome() != null) {
                    existingDiaSemana.setNome(diaSemana.getNome());
                }
                if (diaSemana.getObs() != null) {
                    existingDiaSemana.setObs(diaSemana.getObs());
                }

                return existingDiaSemana;
            })
            .map(diaSemanaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiaSemana> findAll(Pageable pageable) {
        log.debug("Request to get all DiaSemanas");
        return diaSemanaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiaSemana> findOne(Long id) {
        log.debug("Request to get DiaSemana : {}", id);
        return diaSemanaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiaSemana : {}", id);
        diaSemanaRepository.deleteById(id);
    }
}
