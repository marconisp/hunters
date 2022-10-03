package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Vacina;
import br.com.jhisolution.user.hunters.repository.VacinaRepository;
import br.com.jhisolution.user.hunters.service.VacinaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vacina}.
 */
@Service
@Transactional
public class VacinaServiceImpl implements VacinaService {

    private final Logger log = LoggerFactory.getLogger(VacinaServiceImpl.class);

    private final VacinaRepository vacinaRepository;

    public VacinaServiceImpl(VacinaRepository vacinaRepository) {
        this.vacinaRepository = vacinaRepository;
    }

    @Override
    public Vacina save(Vacina vacina) {
        log.debug("Request to save Vacina : {}", vacina);
        return vacinaRepository.save(vacina);
    }

    @Override
    public Vacina update(Vacina vacina) {
        log.debug("Request to save Vacina : {}", vacina);
        return vacinaRepository.save(vacina);
    }

    @Override
    public Optional<Vacina> partialUpdate(Vacina vacina) {
        log.debug("Request to partially update Vacina : {}", vacina);

        return vacinaRepository
            .findById(vacina.getId())
            .map(existingVacina -> {
                if (vacina.getNome() != null) {
                    existingVacina.setNome(vacina.getNome());
                }
                if (vacina.getIdade() != null) {
                    existingVacina.setIdade(vacina.getIdade());
                }
                if (vacina.getObs() != null) {
                    existingVacina.setObs(vacina.getObs());
                }

                return existingVacina;
            })
            .map(vacinaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vacina> findAll(Pageable pageable) {
        log.debug("Request to get all Vacinas");
        return vacinaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vacina> findOne(Long id) {
        log.debug("Request to get Vacina : {}", id);
        return vacinaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vacina : {}", id);
        vacinaRepository.deleteById(id);
    }
}
