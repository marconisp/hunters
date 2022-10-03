package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.PeriodoDuracao;
import br.com.jhisolution.user.hunters.repository.PeriodoDuracaoRepository;
import br.com.jhisolution.user.hunters.service.PeriodoDuracaoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PeriodoDuracao}.
 */
@Service
@Transactional
public class PeriodoDuracaoServiceImpl implements PeriodoDuracaoService {

    private final Logger log = LoggerFactory.getLogger(PeriodoDuracaoServiceImpl.class);

    private final PeriodoDuracaoRepository periodoDuracaoRepository;

    public PeriodoDuracaoServiceImpl(PeriodoDuracaoRepository periodoDuracaoRepository) {
        this.periodoDuracaoRepository = periodoDuracaoRepository;
    }

    @Override
    public PeriodoDuracao save(PeriodoDuracao periodoDuracao) {
        log.debug("Request to save PeriodoDuracao : {}", periodoDuracao);
        return periodoDuracaoRepository.save(periodoDuracao);
    }

    @Override
    public PeriodoDuracao update(PeriodoDuracao periodoDuracao) {
        log.debug("Request to save PeriodoDuracao : {}", periodoDuracao);
        return periodoDuracaoRepository.save(periodoDuracao);
    }

    @Override
    public Optional<PeriodoDuracao> partialUpdate(PeriodoDuracao periodoDuracao) {
        log.debug("Request to partially update PeriodoDuracao : {}", periodoDuracao);

        return periodoDuracaoRepository
            .findById(periodoDuracao.getId())
            .map(existingPeriodoDuracao -> {
                if (periodoDuracao.getNome() != null) {
                    existingPeriodoDuracao.setNome(periodoDuracao.getNome());
                }
                if (periodoDuracao.getDataInicio() != null) {
                    existingPeriodoDuracao.setDataInicio(periodoDuracao.getDataInicio());
                }
                if (periodoDuracao.getDataFim() != null) {
                    existingPeriodoDuracao.setDataFim(periodoDuracao.getDataFim());
                }
                if (periodoDuracao.getHoraInicio() != null) {
                    existingPeriodoDuracao.setHoraInicio(periodoDuracao.getHoraInicio());
                }
                if (periodoDuracao.getHoraFim() != null) {
                    existingPeriodoDuracao.setHoraFim(periodoDuracao.getHoraFim());
                }
                if (periodoDuracao.getObs() != null) {
                    existingPeriodoDuracao.setObs(periodoDuracao.getObs());
                }

                return existingPeriodoDuracao;
            })
            .map(periodoDuracaoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PeriodoDuracao> findAll(Pageable pageable) {
        log.debug("Request to get all PeriodoDuracaos");
        return periodoDuracaoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodoDuracao> findOne(Long id) {
        log.debug("Request to get PeriodoDuracao : {}", id);
        return periodoDuracaoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PeriodoDuracao : {}", id);
        periodoDuracaoRepository.deleteById(id);
    }
}
