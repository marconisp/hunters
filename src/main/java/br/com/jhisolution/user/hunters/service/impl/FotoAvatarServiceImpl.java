package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoAvatar;
import br.com.jhisolution.user.hunters.repository.FotoAvatarRepository;
import br.com.jhisolution.user.hunters.service.FotoAvatarService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoAvatar}.
 */
@Service
@Transactional
public class FotoAvatarServiceImpl implements FotoAvatarService {

    private final Logger log = LoggerFactory.getLogger(FotoAvatarServiceImpl.class);

    private final FotoAvatarRepository fotoAvatarRepository;

    public FotoAvatarServiceImpl(FotoAvatarRepository fotoAvatarRepository) {
        this.fotoAvatarRepository = fotoAvatarRepository;
    }

    @Override
    public FotoAvatar save(FotoAvatar fotoAvatar) {
        log.debug("Request to save FotoAvatar : {}", fotoAvatar);
        return fotoAvatarRepository.save(fotoAvatar);
    }

    @Override
    public FotoAvatar update(FotoAvatar fotoAvatar) {
        log.debug("Request to save FotoAvatar : {}", fotoAvatar);
        return fotoAvatarRepository.save(fotoAvatar);
    }

    @Override
    public Optional<FotoAvatar> partialUpdate(FotoAvatar fotoAvatar) {
        log.debug("Request to partially update FotoAvatar : {}", fotoAvatar);

        return fotoAvatarRepository
            .findById(fotoAvatar.getId())
            .map(existingFotoAvatar -> {
                if (fotoAvatar.getConteudo() != null) {
                    existingFotoAvatar.setConteudo(fotoAvatar.getConteudo());
                }
                if (fotoAvatar.getConteudoContentType() != null) {
                    existingFotoAvatar.setConteudoContentType(fotoAvatar.getConteudoContentType());
                }

                return existingFotoAvatar;
            })
            .map(fotoAvatarRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoAvatar> findAll(Pageable pageable) {
        log.debug("Request to get all FotoAvatars");
        return fotoAvatarRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoAvatar> findOne(Long id) {
        log.debug("Request to get FotoAvatar : {}", id);
        return fotoAvatarRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoAvatar : {}", id);
        fotoAvatarRepository.deleteById(id);
    }
}
