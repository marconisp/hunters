package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.AvaliacaoEconomica;
import br.com.jhisolution.user.hunters.repository.AvaliacaoEconomicaRepository;
import br.com.jhisolution.user.hunters.service.AvaliacaoEconomicaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AvaliacaoEconomica}.
 */
@Service
@Transactional
public class AvaliacaoEconomicaServiceImpl implements AvaliacaoEconomicaService {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoEconomicaServiceImpl.class);

    private final AvaliacaoEconomicaRepository avaliacaoEconomicaRepository;

    public AvaliacaoEconomicaServiceImpl(AvaliacaoEconomicaRepository avaliacaoEconomicaRepository) {
        this.avaliacaoEconomicaRepository = avaliacaoEconomicaRepository;
    }

    @Override
    public AvaliacaoEconomica save(AvaliacaoEconomica avaliacaoEconomica) {
        log.debug("Request to save AvaliacaoEconomica : {}", avaliacaoEconomica);
        return avaliacaoEconomicaRepository.save(avaliacaoEconomica);
    }

    @Override
    public AvaliacaoEconomica update(AvaliacaoEconomica avaliacaoEconomica) {
        log.debug("Request to save AvaliacaoEconomica : {}", avaliacaoEconomica);
        return avaliacaoEconomicaRepository.save(avaliacaoEconomica);
    }

    @Override
    public Optional<AvaliacaoEconomica> partialUpdate(AvaliacaoEconomica avaliacaoEconomica) {
        log.debug("Request to partially update AvaliacaoEconomica : {}", avaliacaoEconomica);

        return avaliacaoEconomicaRepository
            .findById(avaliacaoEconomica.getId())
            .map(existingAvaliacaoEconomica -> {
                if (avaliacaoEconomica.getTrabalhoOuEstagio() != null) {
                    existingAvaliacaoEconomica.setTrabalhoOuEstagio(avaliacaoEconomica.getTrabalhoOuEstagio());
                }
                if (avaliacaoEconomica.getVinculoEmpregaticio() != null) {
                    existingAvaliacaoEconomica.setVinculoEmpregaticio(avaliacaoEconomica.getVinculoEmpregaticio());
                }
                if (avaliacaoEconomica.getCargoFuncao() != null) {
                    existingAvaliacaoEconomica.setCargoFuncao(avaliacaoEconomica.getCargoFuncao());
                }
                if (avaliacaoEconomica.getContribuiRendaFamiliar() != null) {
                    existingAvaliacaoEconomica.setContribuiRendaFamiliar(avaliacaoEconomica.getContribuiRendaFamiliar());
                }
                if (avaliacaoEconomica.getApoioFinanceiroFamiliar() != null) {
                    existingAvaliacaoEconomica.setApoioFinanceiroFamiliar(avaliacaoEconomica.getApoioFinanceiroFamiliar());
                }
                if (avaliacaoEconomica.getEstudaAtualmente() != null) {
                    existingAvaliacaoEconomica.setEstudaAtualmente(avaliacaoEconomica.getEstudaAtualmente());
                }
                if (avaliacaoEconomica.getEscolaAtual() != null) {
                    existingAvaliacaoEconomica.setEscolaAtual(avaliacaoEconomica.getEscolaAtual());
                }
                if (avaliacaoEconomica.getEstudouAnteriormente() != null) {
                    existingAvaliacaoEconomica.setEstudouAnteriormente(avaliacaoEconomica.getEstudouAnteriormente());
                }
                if (avaliacaoEconomica.getEscolaAnterior() != null) {
                    existingAvaliacaoEconomica.setEscolaAnterior(avaliacaoEconomica.getEscolaAnterior());
                }
                if (avaliacaoEconomica.getConcluiAnoEscolarAnterior() != null) {
                    existingAvaliacaoEconomica.setConcluiAnoEscolarAnterior(avaliacaoEconomica.getConcluiAnoEscolarAnterior());
                }
                if (avaliacaoEconomica.getRepetente() != null) {
                    existingAvaliacaoEconomica.setRepetente(avaliacaoEconomica.getRepetente());
                }
                if (avaliacaoEconomica.getDificuldadeAprendizado() != null) {
                    existingAvaliacaoEconomica.setDificuldadeAprendizado(avaliacaoEconomica.getDificuldadeAprendizado());
                }
                if (avaliacaoEconomica.getDificuldadeDisciplina() != null) {
                    existingAvaliacaoEconomica.setDificuldadeDisciplina(avaliacaoEconomica.getDificuldadeDisciplina());
                }
                if (avaliacaoEconomica.getMoraCom() != null) {
                    existingAvaliacaoEconomica.setMoraCom(avaliacaoEconomica.getMoraCom());
                }
                if (avaliacaoEconomica.getPais() != null) {
                    existingAvaliacaoEconomica.setPais(avaliacaoEconomica.getPais());
                }
                if (avaliacaoEconomica.getSituacaoMoradia() != null) {
                    existingAvaliacaoEconomica.setSituacaoMoradia(avaliacaoEconomica.getSituacaoMoradia());
                }
                if (avaliacaoEconomica.getTipoMoradia() != null) {
                    existingAvaliacaoEconomica.setTipoMoradia(avaliacaoEconomica.getTipoMoradia());
                }
                if (avaliacaoEconomica.getRecebeBeneficioGoverno() != null) {
                    existingAvaliacaoEconomica.setRecebeBeneficioGoverno(avaliacaoEconomica.getRecebeBeneficioGoverno());
                }
                if (avaliacaoEconomica.getTipoBeneficio() != null) {
                    existingAvaliacaoEconomica.setTipoBeneficio(avaliacaoEconomica.getTipoBeneficio());
                }
                if (avaliacaoEconomica.getFamiliaExiste() != null) {
                    existingAvaliacaoEconomica.setFamiliaExiste(avaliacaoEconomica.getFamiliaExiste());
                }
                if (avaliacaoEconomica.getAssitenciaMedica() != null) {
                    existingAvaliacaoEconomica.setAssitenciaMedica(avaliacaoEconomica.getAssitenciaMedica());
                }
                if (avaliacaoEconomica.getTemAlergia() != null) {
                    existingAvaliacaoEconomica.setTemAlergia(avaliacaoEconomica.getTemAlergia());
                }
                if (avaliacaoEconomica.getTemProblemaSaude() != null) {
                    existingAvaliacaoEconomica.setTemProblemaSaude(avaliacaoEconomica.getTemProblemaSaude());
                }
                if (avaliacaoEconomica.getTomaMedicamento() != null) {
                    existingAvaliacaoEconomica.setTomaMedicamento(avaliacaoEconomica.getTomaMedicamento());
                }
                if (avaliacaoEconomica.getTeveFratura() != null) {
                    existingAvaliacaoEconomica.setTeveFratura(avaliacaoEconomica.getTeveFratura());
                }
                if (avaliacaoEconomica.getTeveCirurgia() != null) {
                    existingAvaliacaoEconomica.setTeveCirurgia(avaliacaoEconomica.getTeveCirurgia());
                }
                if (avaliacaoEconomica.getTemDeficiencia() != null) {
                    existingAvaliacaoEconomica.setTemDeficiencia(avaliacaoEconomica.getTemDeficiencia());
                }
                if (avaliacaoEconomica.getTemAcompanhamentoMedico() != null) {
                    existingAvaliacaoEconomica.setTemAcompanhamentoMedico(avaliacaoEconomica.getTemAcompanhamentoMedico());
                }

                return existingAvaliacaoEconomica;
            })
            .map(avaliacaoEconomicaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvaliacaoEconomica> findAll(Pageable pageable) {
        log.debug("Request to get all AvaliacaoEconomicas");
        return avaliacaoEconomicaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvaliacaoEconomica> findOne(Long id) {
        log.debug("Request to get AvaliacaoEconomica : {}", id);
        return avaliacaoEconomicaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvaliacaoEconomica : {}", id);
        avaliacaoEconomicaRepository.deleteById(id);
    }
}
