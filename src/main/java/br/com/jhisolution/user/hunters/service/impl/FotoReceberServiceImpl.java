package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoReceber;
import br.com.jhisolution.user.hunters.repository.FotoReceberRepository;
import br.com.jhisolution.user.hunters.service.FotoReceberService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoReceber}.
 */
@Service
@Transactional
public class FotoReceberServiceImpl implements FotoReceberService {

    private final Logger log = LoggerFactory.getLogger(FotoReceberServiceImpl.class);

    private final FotoReceberRepository fotoReceberRepository;

    public FotoReceberServiceImpl(FotoReceberRepository fotoReceberRepository) {
        this.fotoReceberRepository = fotoReceberRepository;
    }

    @Override
    public FotoReceber save(FotoReceber fotoReceber) {
        log.debug("Request to save FotoReceber : {}", fotoReceber);
        return fotoReceberRepository.save(fotoReceber);
    }

    @Override
    public FotoReceber update(FotoReceber fotoReceber) {
        log.debug("Request to save FotoReceber : {}", fotoReceber);
        return fotoReceberRepository.save(fotoReceber);
    }

    @Override
    public Optional<FotoReceber> partialUpdate(FotoReceber fotoReceber) {
        log.debug("Request to partially update FotoReceber : {}", fotoReceber);

        return fotoReceberRepository
            .findById(fotoReceber.getId())
            .map(existingFotoReceber -> {
                if (fotoReceber.getConteudo() != null) {
                    existingFotoReceber.setConteudo(fotoReceber.getConteudo());
                }
                if (fotoReceber.getConteudoContentType() != null) {
                    existingFotoReceber.setConteudoContentType(fotoReceber.getConteudoContentType());
                }

                return existingFotoReceber;
            })
            .map(fotoReceberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoReceber> findAll(Pageable pageable) {
        log.debug("Request to get all FotoRecebers");
        return fotoReceberRepository.findAll(pageable);
    }

    @Override
    public Page<FotoReceber> findAllByReceberId(Long id, Pageable pageable) {
        log.debug("Request to get all FotoRecebers");
        return fotoReceberRepository.findAllFotoReceberByReceberId(id, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoReceber> findOne(Long id) {
        log.debug("Request to get FotoReceber : {}", id);
        return fotoReceberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoReceber : {}", id);
        fotoReceberRepository.deleteById(id);
    }
}
