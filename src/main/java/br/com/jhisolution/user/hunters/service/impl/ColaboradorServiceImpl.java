package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.Colaborador;
import br.com.jhisolution.user.hunters.repository.ColaboradorRepository;
import br.com.jhisolution.user.hunters.service.ColaboradorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Colaborador}.
 */
@Service
@Transactional
public class ColaboradorServiceImpl implements ColaboradorService {

    private final Logger log = LoggerFactory.getLogger(ColaboradorServiceImpl.class);

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorServiceImpl(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public Colaborador save(Colaborador colaborador) {
        log.debug("Request to save Colaborador : {}", colaborador);
        return colaboradorRepository.save(colaborador);
    }

    @Override
    public Colaborador update(Colaborador colaborador) {
        log.debug("Request to save Colaborador : {}", colaborador);
        return colaboradorRepository.save(colaborador);
    }

    @Override
    public Optional<Colaborador> partialUpdate(Colaborador colaborador) {
        log.debug("Request to partially update Colaborador : {}", colaborador);

        return colaboradorRepository
            .findById(colaborador.getId())
            .map(existingColaborador -> {
                if (colaborador.getDataCadastro() != null) {
                    existingColaborador.setDataCadastro(colaborador.getDataCadastro());
                }
                if (colaborador.getDataAdmissao() != null) {
                    existingColaborador.setDataAdmissao(colaborador.getDataAdmissao());
                }
                if (colaborador.getDataRecisao() != null) {
                    existingColaborador.setDataRecisao(colaborador.getDataRecisao());
                }
                if (colaborador.getSalario() != null) {
                    existingColaborador.setSalario(colaborador.getSalario());
                }
                if (colaborador.getAtivo() != null) {
                    existingColaborador.setAtivo(colaborador.getAtivo());
                }
                if (colaborador.getObs() != null) {
                    existingColaborador.setObs(colaborador.getObs());
                }

                return existingColaborador;
            })
            .map(colaboradorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Colaborador> findAll(Pageable pageable) {
        log.debug("Request to get all Colaboradors");
        return colaboradorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Colaborador> findOne(Long id) {
        log.debug("Request to get Colaborador : {}", id);
        return colaboradorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Colaborador : {}", id);
        colaboradorRepository.deleteById(id);
    }
}
