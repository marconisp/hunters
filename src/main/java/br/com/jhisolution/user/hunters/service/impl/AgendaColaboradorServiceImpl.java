package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.AgendaColaborador;
import br.com.jhisolution.user.hunters.repository.AgendaColaboradorRepository;
import br.com.jhisolution.user.hunters.service.AgendaColaboradorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AgendaColaborador}.
 */
@Service
@Transactional
public class AgendaColaboradorServiceImpl implements AgendaColaboradorService {

    private final Logger log = LoggerFactory.getLogger(AgendaColaboradorServiceImpl.class);

    private final AgendaColaboradorRepository agendaColaboradorRepository;

    public AgendaColaboradorServiceImpl(AgendaColaboradorRepository agendaColaboradorRepository) {
        this.agendaColaboradorRepository = agendaColaboradorRepository;
    }

    @Override
    public AgendaColaborador save(AgendaColaborador agendaColaborador) {
        log.debug("Request to save AgendaColaborador : {}", agendaColaborador);
        return agendaColaboradorRepository.save(agendaColaborador);
    }

    @Override
    public AgendaColaborador update(AgendaColaborador agendaColaborador) {
        log.debug("Request to save AgendaColaborador : {}", agendaColaborador);
        return agendaColaboradorRepository.save(agendaColaborador);
    }

    @Override
    public Optional<AgendaColaborador> partialUpdate(AgendaColaborador agendaColaborador) {
        log.debug("Request to partially update AgendaColaborador : {}", agendaColaborador);

        return agendaColaboradorRepository
            .findById(agendaColaborador.getId())
            .map(existingAgendaColaborador -> {
                if (agendaColaborador.getNome() != null) {
                    existingAgendaColaborador.setNome(agendaColaborador.getNome());
                }
                if (agendaColaborador.getObs() != null) {
                    existingAgendaColaborador.setObs(agendaColaborador.getObs());
                }

                return existingAgendaColaborador;
            })
            .map(agendaColaboradorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendaColaborador> findAll(Pageable pageable) {
        log.debug("Request to get all AgendaColaboradors");
        return agendaColaboradorRepository.findAll(pageable);
    }

    public Page<AgendaColaborador> findAllWithEagerRelationships(Pageable pageable) {
        return agendaColaboradorRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgendaColaborador> findOne(Long id) {
        log.debug("Request to get AgendaColaborador : {}", id);
        return agendaColaboradorRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgendaColaborador : {}", id);
        agendaColaboradorRepository.deleteById(id);
    }
}
