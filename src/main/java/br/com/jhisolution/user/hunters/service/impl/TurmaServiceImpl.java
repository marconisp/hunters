package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Turma;
import br.com.jhisolution.user.hunters.repository.TurmaRepository;
import br.com.jhisolution.user.hunters.service.TurmaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Turma}.
 */
@Service
@Transactional
public class TurmaServiceImpl implements TurmaService {

    private final Logger log = LoggerFactory.getLogger(TurmaServiceImpl.class);

    private final TurmaRepository turmaRepository;

    public TurmaServiceImpl(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    @Override
    public Turma save(Turma turma) {
        log.debug("Request to save Turma : {}", turma);
        return turmaRepository.save(turma);
    }

    @Override
    public Turma update(Turma turma) {
        log.debug("Request to save Turma : {}", turma);
        return turmaRepository.save(turma);
    }

    @Override
    public Optional<Turma> partialUpdate(Turma turma) {
        log.debug("Request to partially update Turma : {}", turma);

        return turmaRepository
            .findById(turma.getId())
            .map(existingTurma -> {
                if (turma.getNome() != null) {
                    existingTurma.setNome(turma.getNome());
                }
                if (turma.getAno() != null) {
                    existingTurma.setAno(turma.getAno());
                }

                return existingTurma;
            })
            .map(turmaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Turma> findAll(Pageable pageable) {
        log.debug("Request to get all Turmas");
        return turmaRepository.findAll(pageable);
    }

    public Page<Turma> findAllWithEagerRelationships(Pageable pageable) {
        return turmaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Turma> findOne(Long id) {
        log.debug("Request to get Turma : {}", id);
        return turmaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Turma : {}", id);
        turmaRepository.deleteById(id);
    }
}
