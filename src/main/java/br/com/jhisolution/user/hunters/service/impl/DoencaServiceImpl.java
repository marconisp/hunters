package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Doenca;
import br.com.jhisolution.user.hunters.repository.DoencaRepository;
import br.com.jhisolution.user.hunters.service.DoencaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Doenca}.
 */
@Service
@Transactional
public class DoencaServiceImpl implements DoencaService {

    private final Logger log = LoggerFactory.getLogger(DoencaServiceImpl.class);

    private final DoencaRepository doencaRepository;

    public DoencaServiceImpl(DoencaRepository doencaRepository) {
        this.doencaRepository = doencaRepository;
    }

    @Override
    public Doenca save(Doenca doenca) {
        log.debug("Request to save Doenca : {}", doenca);
        return doencaRepository.save(doenca);
    }

    @Override
    public Doenca update(Doenca doenca) {
        log.debug("Request to save Doenca : {}", doenca);
        return doencaRepository.save(doenca);
    }

    @Override
    public Optional<Doenca> partialUpdate(Doenca doenca) {
        log.debug("Request to partially update Doenca : {}", doenca);

        return doencaRepository
            .findById(doenca.getId())
            .map(existingDoenca -> {
                if (doenca.getNome() != null) {
                    existingDoenca.setNome(doenca.getNome());
                }
                if (doenca.getSintoma() != null) {
                    existingDoenca.setSintoma(doenca.getSintoma());
                }
                if (doenca.getPrecaucoes() != null) {
                    existingDoenca.setPrecaucoes(doenca.getPrecaucoes());
                }
                if (doenca.getSocorro() != null) {
                    existingDoenca.setSocorro(doenca.getSocorro());
                }
                if (doenca.getObs() != null) {
                    existingDoenca.setObs(doenca.getObs());
                }

                return existingDoenca;
            })
            .map(doencaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Doenca> findAll(Pageable pageable) {
        log.debug("Request to get all Doencas");
        return doencaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Doenca> findOne(Long id) {
        log.debug("Request to get Doenca : {}", id);
        return doencaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Doenca : {}", id);
        doencaRepository.deleteById(id);
    }
}
