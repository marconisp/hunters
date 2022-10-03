package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.FotoPagar;
import br.com.jhisolution.user.hunters.repository.FotoPagarRepository;
import br.com.jhisolution.user.hunters.service.FotoPagarService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoPagar}.
 */
@Service
@Transactional
public class FotoPagarServiceImpl implements FotoPagarService {

    private final Logger log = LoggerFactory.getLogger(FotoPagarServiceImpl.class);

    private final FotoPagarRepository fotoPagarRepository;

    public FotoPagarServiceImpl(FotoPagarRepository fotoPagarRepository) {
        this.fotoPagarRepository = fotoPagarRepository;
    }

    @Override
    public FotoPagar save(FotoPagar fotoPagar) {
        log.debug("Request to save FotoPagar : {}", fotoPagar);
        return fotoPagarRepository.save(fotoPagar);
    }

    @Override
    public FotoPagar update(FotoPagar fotoPagar) {
        log.debug("Request to save FotoPagar : {}", fotoPagar);
        return fotoPagarRepository.save(fotoPagar);
    }

    @Override
    public Optional<FotoPagar> partialUpdate(FotoPagar fotoPagar) {
        log.debug("Request to partially update FotoPagar : {}", fotoPagar);

        return fotoPagarRepository
            .findById(fotoPagar.getId())
            .map(existingFotoPagar -> {
                if (fotoPagar.getConteudo() != null) {
                    existingFotoPagar.setConteudo(fotoPagar.getConteudo());
                }
                if (fotoPagar.getConteudoContentType() != null) {
                    existingFotoPagar.setConteudoContentType(fotoPagar.getConteudoContentType());
                }

                return existingFotoPagar;
            })
            .map(fotoPagarRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoPagar> findAll(Pageable pageable) {
        log.debug("Request to get all FotoPagars");
        return fotoPagarRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoPagar> findAllByPagarId(Long id, Pageable pageable) {
        log.debug("Request to get all FotoPagars");
        return fotoPagarRepository.findAllFotoByPagarId(id, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoPagar> findOne(Long id) {
        log.debug("Request to get FotoPagar : {}", id);
        return fotoPagarRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoPagar : {}", id);
        fotoPagarRepository.deleteById(id);
    }
}
