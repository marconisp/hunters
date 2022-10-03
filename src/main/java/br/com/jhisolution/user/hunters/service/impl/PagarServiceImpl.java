package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.repository.PagarRepository;
import br.com.jhisolution.user.hunters.service.PagarService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pagar}.
 */
@Service
@Transactional
public class PagarServiceImpl implements PagarService {

    private final Logger log = LoggerFactory.getLogger(PagarServiceImpl.class);

    private final PagarRepository pagarRepository;

    public PagarServiceImpl(PagarRepository pagarRepository) {
        this.pagarRepository = pagarRepository;
    }

    @Override
    public Pagar save(Pagar pagar) {
        log.debug("Request to save Pagar : {}", pagar);
        return pagarRepository.save(pagar);
    }

    @Override
    public Pagar update(Pagar pagar) {
        log.debug("Request to save Pagar : {}", pagar);
        return pagarRepository.save(pagar);
    }

    @Override
    public Optional<Pagar> partialUpdate(Pagar pagar) {
        log.debug("Request to partially update Pagar : {}", pagar);

        return pagarRepository
            .findById(pagar.getId())
            .map(existingPagar -> {
                if (pagar.getData() != null) {
                    existingPagar.setData(pagar.getData());
                }
                if (pagar.getValor() != null) {
                    existingPagar.setValor(pagar.getValor());
                }
                if (pagar.getStatus() != null) {
                    existingPagar.setStatus(pagar.getStatus());
                }
                if (pagar.getObs() != null) {
                    existingPagar.setObs(pagar.getObs());
                }

                return existingPagar;
            })
            .map(pagarRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pagar> findAll(Pageable pageable) {
        log.debug("Request to get all Pagars");
        return pagarRepository.findAll(pageable);
    }

    public Page<Pagar> findAllWithEagerRelationships(Pageable pageable) {
        return pagarRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pagar> findOne(Long id) {
        log.debug("Request to get Pagar : {}", id);
        return pagarRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pagar : {}", id);
        pagarRepository.deleteById(id);
    }
}
