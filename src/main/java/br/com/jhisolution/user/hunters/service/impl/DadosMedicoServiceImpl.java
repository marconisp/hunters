package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.DadosMedico;
import br.com.jhisolution.user.hunters.repository.DadosMedicoRepository;
import br.com.jhisolution.user.hunters.service.DadosMedicoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DadosMedico}.
 */
@Service
@Transactional
public class DadosMedicoServiceImpl implements DadosMedicoService {

    private final Logger log = LoggerFactory.getLogger(DadosMedicoServiceImpl.class);

    private final DadosMedicoRepository dadosMedicoRepository;

    public DadosMedicoServiceImpl(DadosMedicoRepository dadosMedicoRepository) {
        this.dadosMedicoRepository = dadosMedicoRepository;
    }

    @Override
    public DadosMedico save(DadosMedico dadosMedico) {
        log.debug("Request to save DadosMedico : {}", dadosMedico);
        return dadosMedicoRepository.save(dadosMedico);
    }

    @Override
    public DadosMedico update(DadosMedico dadosMedico) {
        log.debug("Request to save DadosMedico : {}", dadosMedico);
        return dadosMedicoRepository.save(dadosMedico);
    }

    @Override
    public Optional<DadosMedico> partialUpdate(DadosMedico dadosMedico) {
        log.debug("Request to partially update DadosMedico : {}", dadosMedico);

        return dadosMedicoRepository
            .findById(dadosMedico.getId())
            .map(existingDadosMedico -> {
                if (dadosMedico.getData() != null) {
                    existingDadosMedico.setData(dadosMedico.getData());
                }
                if (dadosMedico.getPeso() != null) {
                    existingDadosMedico.setPeso(dadosMedico.getPeso());
                }
                if (dadosMedico.getAltura() != null) {
                    existingDadosMedico.setAltura(dadosMedico.getAltura());
                }
                if (dadosMedico.getPressao() != null) {
                    existingDadosMedico.setPressao(dadosMedico.getPressao());
                }
                if (dadosMedico.getCoracao() != null) {
                    existingDadosMedico.setCoracao(dadosMedico.getCoracao());
                }
                if (dadosMedico.getMedicacao() != null) {
                    existingDadosMedico.setMedicacao(dadosMedico.getMedicacao());
                }
                if (dadosMedico.getObs() != null) {
                    existingDadosMedico.setObs(dadosMedico.getObs());
                }

                return existingDadosMedico;
            })
            .map(dadosMedicoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosMedico> findAll(Pageable pageable) {
        log.debug("Request to get all DadosMedicos");
        return dadosMedicoRepository.findAll(pageable);
    }

    public Page<DadosMedico> findAllWithEagerRelationships(Pageable pageable) {
        return dadosMedicoRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DadosMedico> findOne(Long id) {
        log.debug("Request to get DadosMedico : {}", id);
        return dadosMedicoRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DadosMedico : {}", id);
        dadosMedicoRepository.deleteById(id);
    }
}
