package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoIcon;
import br.com.jhisolution.user.hunters.repository.FotoIconRepository;
import br.com.jhisolution.user.hunters.service.FotoIconService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoIcon}.
 */
@Service
@Transactional
public class FotoIconServiceImpl implements FotoIconService {

    private final Logger log = LoggerFactory.getLogger(FotoIconServiceImpl.class);

    private final FotoIconRepository fotoIconRepository;

    public FotoIconServiceImpl(FotoIconRepository fotoIconRepository) {
        this.fotoIconRepository = fotoIconRepository;
    }

    @Override
    public FotoIcon save(FotoIcon fotoIcon) {
        log.debug("Request to save FotoIcon : {}", fotoIcon);
        return fotoIconRepository.save(fotoIcon);
    }

    @Override
    public FotoIcon update(FotoIcon fotoIcon) {
        log.debug("Request to save FotoIcon : {}", fotoIcon);
        return fotoIconRepository.save(fotoIcon);
    }

    @Override
    public Optional<FotoIcon> partialUpdate(FotoIcon fotoIcon) {
        log.debug("Request to partially update FotoIcon : {}", fotoIcon);

        return fotoIconRepository
            .findById(fotoIcon.getId())
            .map(existingFotoIcon -> {
                if (fotoIcon.getConteudo() != null) {
                    existingFotoIcon.setConteudo(fotoIcon.getConteudo());
                }
                if (fotoIcon.getConteudoContentType() != null) {
                    existingFotoIcon.setConteudoContentType(fotoIcon.getConteudoContentType());
                }

                return existingFotoIcon;
            })
            .map(fotoIconRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoIcon> findAll(Pageable pageable) {
        log.debug("Request to get all FotoIcons");
        return fotoIconRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoIcon> findOne(Long id) {
        log.debug("Request to get FotoIcon : {}", id);
        return fotoIconRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoIcon : {}", id);
        fotoIconRepository.deleteById(id);
    }
}
