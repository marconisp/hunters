package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.PagarPara;
import br.com.jhisolution.user.hunters.repository.PagarParaRepository;
import br.com.jhisolution.user.hunters.service.PagarParaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PagarPara}.
 */
@Service
@Transactional
public class PagarParaServiceImpl implements PagarParaService {

    private final Logger log = LoggerFactory.getLogger(PagarParaServiceImpl.class);

    private final PagarParaRepository pagarParaRepository;

    public PagarParaServiceImpl(PagarParaRepository pagarParaRepository) {
        this.pagarParaRepository = pagarParaRepository;
    }

    @Override
    public PagarPara save(PagarPara pagarPara) {
        log.debug("Request to save PagarPara : {}", pagarPara);
        return pagarParaRepository.save(pagarPara);
    }

    @Override
    public PagarPara update(PagarPara pagarPara) {
        log.debug("Request to save PagarPara : {}", pagarPara);
        return pagarParaRepository.save(pagarPara);
    }

    @Override
    public Optional<PagarPara> partialUpdate(PagarPara pagarPara) {
        log.debug("Request to partially update PagarPara : {}", pagarPara);

        return pagarParaRepository
            .findById(pagarPara.getId())
            .map(existingPagarPara -> {
                if (pagarPara.getNome() != null) {
                    existingPagarPara.setNome(pagarPara.getNome());
                }
                if (pagarPara.getDescricao() != null) {
                    existingPagarPara.setDescricao(pagarPara.getDescricao());
                }
                if (pagarPara.getCnpj() != null) {
                    existingPagarPara.setCnpj(pagarPara.getCnpj());
                }
                if (pagarPara.getDocumento() != null) {
                    existingPagarPara.setDocumento(pagarPara.getDocumento());
                }
                if (pagarPara.getBanco() != null) {
                    existingPagarPara.setBanco(pagarPara.getBanco());
                }
                if (pagarPara.getAgencia() != null) {
                    existingPagarPara.setAgencia(pagarPara.getAgencia());
                }
                if (pagarPara.getConta() != null) {
                    existingPagarPara.setConta(pagarPara.getConta());
                }
                if (pagarPara.getPix() != null) {
                    existingPagarPara.setPix(pagarPara.getPix());
                }

                return existingPagarPara;
            })
            .map(pagarParaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PagarPara> findAll(Pageable pageable) {
        log.debug("Request to get all PagarParas");
        return pagarParaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PagarPara> findOne(Long id) {
        log.debug("Request to get PagarPara : {}", id);
        return pagarParaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PagarPara : {}", id);
        pagarParaRepository.deleteById(id);
    }
}
