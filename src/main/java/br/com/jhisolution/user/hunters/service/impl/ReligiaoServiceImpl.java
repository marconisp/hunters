package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Religiao;
import br.com.jhisolution.user.hunters.repository.ReligiaoRepository;
import br.com.jhisolution.user.hunters.service.ReligiaoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Religiao}.
 */
@Service
@Transactional
public class ReligiaoServiceImpl implements ReligiaoService {

    private final Logger log = LoggerFactory.getLogger(ReligiaoServiceImpl.class);

    private final ReligiaoRepository religiaoRepository;

    public ReligiaoServiceImpl(ReligiaoRepository religiaoRepository) {
        this.religiaoRepository = religiaoRepository;
    }

    @Override
    public Religiao save(Religiao religiao) {
        log.debug("Request to save Religiao : {}", religiao);
        return religiaoRepository.save(religiao);
    }

    @Override
    public Religiao update(Religiao religiao) {
        log.debug("Request to save Religiao : {}", religiao);
        return religiaoRepository.save(religiao);
    }

    @Override
    public Optional<Religiao> partialUpdate(Religiao religiao) {
        log.debug("Request to partially update Religiao : {}", religiao);

        return religiaoRepository
            .findById(religiao.getId())
            .map(existingReligiao -> {
                if (religiao.getCodigo() != null) {
                    existingReligiao.setCodigo(religiao.getCodigo());
                }
                if (religiao.getDescricao() != null) {
                    existingReligiao.setDescricao(religiao.getDescricao());
                }

                return existingReligiao;
            })
            .map(religiaoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Religiao> findAll(Pageable pageable) {
        log.debug("Request to get all Religiaos");
        return religiaoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Religiao> findOne(Long id) {
        log.debug("Request to get Religiao : {}", id);
        return religiaoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Religiao : {}", id);
        religiaoRepository.deleteById(id);
    }
}
