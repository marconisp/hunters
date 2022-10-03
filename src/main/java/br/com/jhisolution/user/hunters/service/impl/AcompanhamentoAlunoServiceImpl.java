package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.AcompanhamentoAluno;
import br.com.jhisolution.user.hunters.repository.AcompanhamentoAlunoRepository;
import br.com.jhisolution.user.hunters.service.AcompanhamentoAlunoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AcompanhamentoAluno}.
 */
@Service
@Transactional
public class AcompanhamentoAlunoServiceImpl implements AcompanhamentoAlunoService {

    private final Logger log = LoggerFactory.getLogger(AcompanhamentoAlunoServiceImpl.class);

    private final AcompanhamentoAlunoRepository acompanhamentoAlunoRepository;

    public AcompanhamentoAlunoServiceImpl(AcompanhamentoAlunoRepository acompanhamentoAlunoRepository) {
        this.acompanhamentoAlunoRepository = acompanhamentoAlunoRepository;
    }

    @Override
    public AcompanhamentoAluno save(AcompanhamentoAluno acompanhamentoAluno) {
        log.debug("Request to save AcompanhamentoAluno : {}", acompanhamentoAluno);
        return acompanhamentoAlunoRepository.save(acompanhamentoAluno);
    }

    @Override
    public AcompanhamentoAluno update(AcompanhamentoAluno acompanhamentoAluno) {
        log.debug("Request to save AcompanhamentoAluno : {}", acompanhamentoAluno);
        return acompanhamentoAlunoRepository.save(acompanhamentoAluno);
    }

    @Override
    public Optional<AcompanhamentoAluno> partialUpdate(AcompanhamentoAluno acompanhamentoAluno) {
        log.debug("Request to partially update AcompanhamentoAluno : {}", acompanhamentoAluno);

        return acompanhamentoAlunoRepository
            .findById(acompanhamentoAluno.getId())
            .map(existingAcompanhamentoAluno -> {
                if (acompanhamentoAluno.getAno() != null) {
                    existingAcompanhamentoAluno.setAno(acompanhamentoAluno.getAno());
                }
                if (acompanhamentoAluno.getEnsino() != null) {
                    existingAcompanhamentoAluno.setEnsino(acompanhamentoAluno.getEnsino());
                }
                if (acompanhamentoAluno.getBimestre() != null) {
                    existingAcompanhamentoAluno.setBimestre(acompanhamentoAluno.getBimestre());
                }

                return existingAcompanhamentoAluno;
            })
            .map(acompanhamentoAlunoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AcompanhamentoAluno> findAll(Pageable pageable) {
        log.debug("Request to get all AcompanhamentoAlunos");
        return acompanhamentoAlunoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcompanhamentoAluno> findOne(Long id) {
        log.debug("Request to get AcompanhamentoAluno : {}", id);
        return acompanhamentoAlunoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcompanhamentoAluno : {}", id);
        acompanhamentoAlunoRepository.deleteById(id);
    }
}
