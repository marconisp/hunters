package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Materia;
import br.com.jhisolution.user.hunters.repository.MateriaRepository;
import br.com.jhisolution.user.hunters.service.MateriaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Materia}.
 */
@Service
@Transactional
public class MateriaServiceImpl implements MateriaService {

    private final Logger log = LoggerFactory.getLogger(MateriaServiceImpl.class);

    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Materia save(Materia materia) {
        log.debug("Request to save Materia : {}", materia);
        return materiaRepository.save(materia);
    }

    @Override
    public Materia update(Materia materia) {
        log.debug("Request to save Materia : {}", materia);
        return materiaRepository.save(materia);
    }

    @Override
    public Optional<Materia> partialUpdate(Materia materia) {
        log.debug("Request to partially update Materia : {}", materia);

        return materiaRepository
            .findById(materia.getId())
            .map(existingMateria -> {
                if (materia.getNome() != null) {
                    existingMateria.setNome(materia.getNome());
                }

                return existingMateria;
            })
            .map(materiaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Materia> findAll(Pageable pageable) {
        log.debug("Request to get all Materias");
        return materiaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Materia> findOne(Long id) {
        log.debug("Request to get Materia : {}", id);
        return materiaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materia : {}", id);
        materiaRepository.deleteById(id);
    }
}
