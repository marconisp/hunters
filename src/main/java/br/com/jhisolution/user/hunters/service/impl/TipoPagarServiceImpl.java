package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.TipoPagar;
import br.com.jhisolution.user.hunters.repository.TipoPagarRepository;
import br.com.jhisolution.user.hunters.service.TipoPagarService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoPagar}.
 */
@Service
@Transactional
public class TipoPagarServiceImpl implements TipoPagarService {

    private final Logger log = LoggerFactory.getLogger(TipoPagarServiceImpl.class);

    private final TipoPagarRepository tipoPagarRepository;

    public TipoPagarServiceImpl(TipoPagarRepository tipoPagarRepository) {
        this.tipoPagarRepository = tipoPagarRepository;
    }

    @Override
    public TipoPagar save(TipoPagar tipoPagar) {
        log.debug("Request to save TipoPagar : {}", tipoPagar);
        return tipoPagarRepository.save(tipoPagar);
    }

    @Override
    public TipoPagar update(TipoPagar tipoPagar) {
        log.debug("Request to save TipoPagar : {}", tipoPagar);
        return tipoPagarRepository.save(tipoPagar);
    }

    @Override
    public Optional<TipoPagar> partialUpdate(TipoPagar tipoPagar) {
        log.debug("Request to partially update TipoPagar : {}", tipoPagar);

        return tipoPagarRepository
            .findById(tipoPagar.getId())
            .map(existingTipoPagar -> {
                if (tipoPagar.getNome() != null) {
                    existingTipoPagar.setNome(tipoPagar.getNome());
                }

                return existingTipoPagar;
            })
            .map(tipoPagarRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoPagar> findAll(Pageable pageable) {
        log.debug("Request to get all TipoPagars");
        return tipoPagarRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPagar> findOne(Long id) {
        log.debug("Request to get TipoPagar : {}", id);
        return tipoPagarRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPagar : {}", id);
        tipoPagarRepository.deleteById(id);
    }
}
