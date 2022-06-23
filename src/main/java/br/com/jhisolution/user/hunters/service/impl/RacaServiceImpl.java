package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Raca;
import br.com.jhisolution.user.hunters.repository.RacaRepository;
import br.com.jhisolution.user.hunters.service.RacaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Raca}.
 */
@Service
@Transactional
public class RacaServiceImpl implements RacaService {

    private final Logger log = LoggerFactory.getLogger(RacaServiceImpl.class);

    private final RacaRepository racaRepository;

    public RacaServiceImpl(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    @Override
    public Raca save(Raca raca) {
        log.debug("Request to save Raca : {}", raca);
        return racaRepository.save(raca);
    }

    @Override
    public Raca update(Raca raca) {
        log.debug("Request to save Raca : {}", raca);
        return racaRepository.save(raca);
    }

    @Override
    public Optional<Raca> partialUpdate(Raca raca) {
        log.debug("Request to partially update Raca : {}", raca);

        return racaRepository
            .findById(raca.getId())
            .map(existingRaca -> {
                if (raca.getCodigo() != null) {
                    existingRaca.setCodigo(raca.getCodigo());
                }
                if (raca.getDescricao() != null) {
                    existingRaca.setDescricao(raca.getDescricao());
                }

                return existingRaca;
            })
            .map(racaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Raca> findAll(Pageable pageable) {
        log.debug("Request to get all Racas");
        return racaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Raca> findOne(Long id) {
        log.debug("Request to get Raca : {}", id);
        return racaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Raca : {}", id);
        racaRepository.deleteById(id);
    }
}
