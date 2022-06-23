package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.repository.DadosPessoaisRepository;
import br.com.jhisolution.user.hunters.service.DadosPessoaisService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DadosPessoais}.
 */
@Service
@Transactional
public class DadosPessoaisServiceImpl implements DadosPessoaisService {

    private final Logger log = LoggerFactory.getLogger(DadosPessoaisServiceImpl.class);

    private final DadosPessoaisRepository dadosPessoaisRepository;

    public DadosPessoaisServiceImpl(DadosPessoaisRepository dadosPessoaisRepository) {
        this.dadosPessoaisRepository = dadosPessoaisRepository;
    }

    @Override
    public DadosPessoais save(DadosPessoais dadosPessoais) {
        log.debug("Request to save DadosPessoais : {}", dadosPessoais);
        return dadosPessoaisRepository.save(dadosPessoais);
    }

    @Override
    public DadosPessoais update(DadosPessoais dadosPessoais) {
        log.debug("Request to save DadosPessoais : {}", dadosPessoais);
        return dadosPessoaisRepository.save(dadosPessoais);
    }

    @Override
    public Optional<DadosPessoais> partialUpdate(DadosPessoais dadosPessoais) {
        log.debug("Request to partially update DadosPessoais : {}", dadosPessoais);

        return dadosPessoaisRepository
            .findById(dadosPessoais.getId())
            .map(existingDadosPessoais -> {
                if (dadosPessoais.getNome() != null) {
                    existingDadosPessoais.setNome(dadosPessoais.getNome());
                }
                if (dadosPessoais.getSobreNome() != null) {
                    existingDadosPessoais.setSobreNome(dadosPessoais.getSobreNome());
                }
                if (dadosPessoais.getPai() != null) {
                    existingDadosPessoais.setPai(dadosPessoais.getPai());
                }
                if (dadosPessoais.getMae() != null) {
                    existingDadosPessoais.setMae(dadosPessoais.getMae());
                }
                if (dadosPessoais.getTelefone() != null) {
                    existingDadosPessoais.setTelefone(dadosPessoais.getTelefone());
                }
                if (dadosPessoais.getCelular() != null) {
                    existingDadosPessoais.setCelular(dadosPessoais.getCelular());
                }
                if (dadosPessoais.getWhatsapp() != null) {
                    existingDadosPessoais.setWhatsapp(dadosPessoais.getWhatsapp());
                }
                if (dadosPessoais.getEmail() != null) {
                    existingDadosPessoais.setEmail(dadosPessoais.getEmail());
                }

                return existingDadosPessoais;
            })
            .map(dadosPessoaisRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosPessoais> findAll(Pageable pageable) {
        log.debug("Request to get all DadosPessoais");
        return dadosPessoaisRepository.findAll(pageable);
    }

    public Page<DadosPessoais> findAllWithEagerRelationships(Pageable pageable) {
        return dadosPessoaisRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DadosPessoais> findOne(Long id) {
        log.debug("Request to get DadosPessoais : {}", id);
        return dadosPessoaisRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DadosPessoais : {}", id);
        dadosPessoaisRepository.deleteById(id);
    }
}
