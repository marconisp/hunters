package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.ExameMedico;
import br.com.jhisolution.user.hunters.repository.ExameMedicoRepository;
import br.com.jhisolution.user.hunters.service.ExameMedicoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExameMedico}.
 */
@Service
@Transactional
public class ExameMedicoServiceImpl implements ExameMedicoService {

    private final Logger log = LoggerFactory.getLogger(ExameMedicoServiceImpl.class);

    private final ExameMedicoRepository exameMedicoRepository;

    public ExameMedicoServiceImpl(ExameMedicoRepository exameMedicoRepository) {
        this.exameMedicoRepository = exameMedicoRepository;
    }

    @Override
    public ExameMedico save(ExameMedico exameMedico) {
        log.debug("Request to save ExameMedico : {}", exameMedico);
        return exameMedicoRepository.save(exameMedico);
    }

    @Override
    public ExameMedico update(ExameMedico exameMedico) {
        log.debug("Request to save ExameMedico : {}", exameMedico);
        return exameMedicoRepository.save(exameMedico);
    }

    @Override
    public Optional<ExameMedico> partialUpdate(ExameMedico exameMedico) {
        log.debug("Request to partially update ExameMedico : {}", exameMedico);

        return exameMedicoRepository
            .findById(exameMedico.getId())
            .map(existingExameMedico -> {
                if (exameMedico.getData() != null) {
                    existingExameMedico.setData(exameMedico.getData());
                }
                if (exameMedico.getNomeMedico() != null) {
                    existingExameMedico.setNomeMedico(exameMedico.getNomeMedico());
                }
                if (exameMedico.getCrmMedico() != null) {
                    existingExameMedico.setCrmMedico(exameMedico.getCrmMedico());
                }
                if (exameMedico.getResumo() != null) {
                    existingExameMedico.setResumo(exameMedico.getResumo());
                }
                if (exameMedico.getObs() != null) {
                    existingExameMedico.setObs(exameMedico.getObs());
                }

                return existingExameMedico;
            })
            .map(exameMedicoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExameMedico> findAll(Pageable pageable) {
        log.debug("Request to get all ExameMedicos");
        return exameMedicoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExameMedico> findOne(Long id) {
        log.debug("Request to get ExameMedico : {}", id);
        return exameMedicoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExameMedico : {}", id);
        exameMedicoRepository.deleteById(id);
    }
}
