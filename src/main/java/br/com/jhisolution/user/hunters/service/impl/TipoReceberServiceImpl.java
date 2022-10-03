package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.TipoReceber;
import br.com.jhisolution.user.hunters.repository.TipoReceberRepository;
import br.com.jhisolution.user.hunters.service.TipoReceberService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoReceber}.
 */
@Service
@Transactional
public class TipoReceberServiceImpl implements TipoReceberService {

    private final Logger log = LoggerFactory.getLogger(TipoReceberServiceImpl.class);

    private final TipoReceberRepository tipoReceberRepository;

    public TipoReceberServiceImpl(TipoReceberRepository tipoReceberRepository) {
        this.tipoReceberRepository = tipoReceberRepository;
    }

    @Override
    public TipoReceber save(TipoReceber tipoReceber) {
        log.debug("Request to save TipoReceber : {}", tipoReceber);
        return tipoReceberRepository.save(tipoReceber);
    }

    @Override
    public TipoReceber update(TipoReceber tipoReceber) {
        log.debug("Request to save TipoReceber : {}", tipoReceber);
        return tipoReceberRepository.save(tipoReceber);
    }

    @Override
    public Optional<TipoReceber> partialUpdate(TipoReceber tipoReceber) {
        log.debug("Request to partially update TipoReceber : {}", tipoReceber);

        return tipoReceberRepository
            .findById(tipoReceber.getId())
            .map(existingTipoReceber -> {
                if (tipoReceber.getNome() != null) {
                    existingTipoReceber.setNome(tipoReceber.getNome());
                }

                return existingTipoReceber;
            })
            .map(tipoReceberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoReceber> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRecebers");
        return tipoReceberRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoReceber> findOne(Long id) {
        log.debug("Request to get TipoReceber : {}", id);
        return tipoReceberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoReceber : {}", id);
        tipoReceberRepository.deleteById(id);
    }
}
