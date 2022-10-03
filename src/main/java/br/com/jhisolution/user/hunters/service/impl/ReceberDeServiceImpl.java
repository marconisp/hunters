package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.ReceberDe;
import br.com.jhisolution.user.hunters.repository.ReceberDeRepository;
import br.com.jhisolution.user.hunters.service.ReceberDeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReceberDe}.
 */
@Service
@Transactional
public class ReceberDeServiceImpl implements ReceberDeService {

    private final Logger log = LoggerFactory.getLogger(ReceberDeServiceImpl.class);

    private final ReceberDeRepository receberDeRepository;

    public ReceberDeServiceImpl(ReceberDeRepository receberDeRepository) {
        this.receberDeRepository = receberDeRepository;
    }

    @Override
    public ReceberDe save(ReceberDe receberDe) {
        log.debug("Request to save ReceberDe : {}", receberDe);
        return receberDeRepository.save(receberDe);
    }

    @Override
    public ReceberDe update(ReceberDe receberDe) {
        log.debug("Request to save ReceberDe : {}", receberDe);
        return receberDeRepository.save(receberDe);
    }

    @Override
    public Optional<ReceberDe> partialUpdate(ReceberDe receberDe) {
        log.debug("Request to partially update ReceberDe : {}", receberDe);

        return receberDeRepository
            .findById(receberDe.getId())
            .map(existingReceberDe -> {
                if (receberDe.getNome() != null) {
                    existingReceberDe.setNome(receberDe.getNome());
                }
                if (receberDe.getDescricao() != null) {
                    existingReceberDe.setDescricao(receberDe.getDescricao());
                }
                if (receberDe.getCnpj() != null) {
                    existingReceberDe.setCnpj(receberDe.getCnpj());
                }
                if (receberDe.getDocumento() != null) {
                    existingReceberDe.setDocumento(receberDe.getDocumento());
                }

                return existingReceberDe;
            })
            .map(receberDeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReceberDe> findAll(Pageable pageable) {
        log.debug("Request to get all ReceberDes");
        return receberDeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReceberDe> findOne(Long id) {
        log.debug("Request to get ReceberDe : {}", id);
        return receberDeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReceberDe : {}", id);
        receberDeRepository.deleteById(id);
    }
}
