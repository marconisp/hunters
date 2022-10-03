package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Alergia;
import br.com.jhisolution.user.hunters.repository.AlergiaRepository;
import br.com.jhisolution.user.hunters.service.AlergiaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Alergia}.
 */
@Service
@Transactional
public class AlergiaServiceImpl implements AlergiaService {

    private final Logger log = LoggerFactory.getLogger(AlergiaServiceImpl.class);

    private final AlergiaRepository alergiaRepository;

    public AlergiaServiceImpl(AlergiaRepository alergiaRepository) {
        this.alergiaRepository = alergiaRepository;
    }

    @Override
    public Alergia save(Alergia alergia) {
        log.debug("Request to save Alergia : {}", alergia);
        return alergiaRepository.save(alergia);
    }

    @Override
    public Alergia update(Alergia alergia) {
        log.debug("Request to save Alergia : {}", alergia);
        return alergiaRepository.save(alergia);
    }

    @Override
    public Optional<Alergia> partialUpdate(Alergia alergia) {
        log.debug("Request to partially update Alergia : {}", alergia);

        return alergiaRepository
            .findById(alergia.getId())
            .map(existingAlergia -> {
                if (alergia.getNome() != null) {
                    existingAlergia.setNome(alergia.getNome());
                }
                if (alergia.getSintoma() != null) {
                    existingAlergia.setSintoma(alergia.getSintoma());
                }
                if (alergia.getPrecaucoes() != null) {
                    existingAlergia.setPrecaucoes(alergia.getPrecaucoes());
                }
                if (alergia.getSocorro() != null) {
                    existingAlergia.setSocorro(alergia.getSocorro());
                }
                if (alergia.getObs() != null) {
                    existingAlergia.setObs(alergia.getObs());
                }

                return existingAlergia;
            })
            .map(alergiaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Alergia> findAll(Pageable pageable) {
        log.debug("Request to get all Alergias");
        return alergiaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alergia> findOne(Long id) {
        log.debug("Request to get Alergia : {}", id);
        return alergiaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alergia : {}", id);
        alergiaRepository.deleteById(id);
    }
}
