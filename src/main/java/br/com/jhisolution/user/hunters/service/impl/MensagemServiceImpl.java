package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.Mensagem;
import br.com.jhisolution.user.hunters.repository.DadosPessoaisRepository;
import br.com.jhisolution.user.hunters.repository.MensagemRepository;
import br.com.jhisolution.user.hunters.service.MensagemService;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Mensagem}.
 */
@Service
@Transactional
public class MensagemServiceImpl implements MensagemService {

    private final Logger log = LoggerFactory.getLogger(MensagemServiceImpl.class);

    private final MensagemRepository mensagemRepository;
    private final DadosPessoaisRepository dadosPessoaisRepository;

    public MensagemServiceImpl(MensagemRepository mensagemRepository, DadosPessoaisRepository dadosPessoaisRepository) {
        this.mensagemRepository = mensagemRepository;
        this.dadosPessoaisRepository = dadosPessoaisRepository;
    }

    @Override
    public Mensagem save(Mensagem mensagem) {
        log.debug("Request to save Mensagem : {}", mensagem);

        if (Objects.nonNull(mensagem.getDadosPessoais()) && Objects.nonNull(mensagem.getDadosPessoais().getId())) {
            DadosPessoais dadosPessoais = dadosPessoaisRepository.findById(mensagem.getDadosPessoais().getId()).get();
            mensagem.setDadosPessoais(dadosPessoais);
        }

        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem update(Mensagem mensagem) {
        log.debug("Request to save Mensagem : {}", mensagem);
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Optional<Mensagem> partialUpdate(Mensagem mensagem) {
        log.debug("Request to partially update Mensagem : {}", mensagem);

        return mensagemRepository
            .findById(mensagem.getId())
            .map(existingMensagem -> {
                if (mensagem.getData() != null) {
                    existingMensagem.setData(mensagem.getData());
                }
                if (mensagem.getTitulo() != null) {
                    existingMensagem.setTitulo(mensagem.getTitulo());
                }
                if (mensagem.getConteudo() != null) {
                    existingMensagem.setConteudo(mensagem.getConteudo());
                }

                return existingMensagem;
            })
            .map(mensagemRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mensagem> findAll(Pageable pageable) {
        log.debug("Request to get all Mensagems");
        return mensagemRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mensagem> findAllByDadosPessoaisId(Long id, Pageable pageable) {
        log.debug("Request to get all Enderecos by DadoPessoal id");
        return mensagemRepository.findAllByDadosPessoaisId(id, pageable);
    }

    public Page<Mensagem> findAllWithEagerRelationships(Pageable pageable) {
        return mensagemRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Mensagem> findOne(Long id) {
        log.debug("Request to get Mensagem : {}", id);
        return mensagemRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mensagem : {}", id);
        mensagemRepository.deleteById(id);
    }
}
