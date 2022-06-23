package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Foto;
import br.com.jhisolution.user.hunters.repository.FotoRepository;
import br.com.jhisolution.user.hunters.service.FotoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Foto}.
 */
@Service
@Transactional
public class FotoServiceImpl implements FotoService {

    private final Logger log = LoggerFactory.getLogger(FotoServiceImpl.class);

    private final FotoRepository fotoRepository;

    public FotoServiceImpl(FotoRepository fotoRepository) {
        this.fotoRepository = fotoRepository;
    }

    @Override
    public Foto save(Foto foto) {
        log.debug("Request to save Foto : {}", foto);
        return fotoRepository.save(foto);
    }

    @Override
    public Foto update(Foto foto) {
        log.debug("Request to save Foto : {}", foto);
        return fotoRepository.save(foto);
    }

    @Override
    public Optional<Foto> partialUpdate(Foto foto) {
        log.debug("Request to partially update Foto : {}", foto);

        return fotoRepository
            .findById(foto.getId())
            .map(existingFoto -> {
                if (foto.getConteudo() != null) {
                    existingFoto.setConteudo(foto.getConteudo());
                }
                if (foto.getConteudoContentType() != null) {
                    existingFoto.setConteudoContentType(foto.getConteudoContentType());
                }

                return existingFoto;
            })
            .map(fotoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Foto> findAll(Pageable pageable) {
        log.debug("Request to get all Fotos");
        return fotoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Foto> findOne(Long id) {
        log.debug("Request to get Foto : {}", id);
        return fotoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Foto : {}", id);
        fotoRepository.deleteById(id);
    }
}
