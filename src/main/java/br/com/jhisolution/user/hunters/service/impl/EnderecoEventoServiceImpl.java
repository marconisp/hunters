package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.EnderecoEvento;
import br.com.jhisolution.user.hunters.repository.EnderecoEventoRepository;
import br.com.jhisolution.user.hunters.service.EnderecoEventoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EnderecoEvento}.
 */
@Service
@Transactional
public class EnderecoEventoServiceImpl implements EnderecoEventoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoEventoServiceImpl.class);

    private final EnderecoEventoRepository enderecoEventoRepository;

    public EnderecoEventoServiceImpl(EnderecoEventoRepository enderecoEventoRepository) {
        this.enderecoEventoRepository = enderecoEventoRepository;
    }

    @Override
    public EnderecoEvento save(EnderecoEvento enderecoEvento) {
        log.debug("Request to save EnderecoEvento : {}", enderecoEvento);
        return enderecoEventoRepository.save(enderecoEvento);
    }

    @Override
    public EnderecoEvento update(EnderecoEvento enderecoEvento) {
        log.debug("Request to save EnderecoEvento : {}", enderecoEvento);
        return enderecoEventoRepository.save(enderecoEvento);
    }

    @Override
    public Optional<EnderecoEvento> partialUpdate(EnderecoEvento enderecoEvento) {
        log.debug("Request to partially update EnderecoEvento : {}", enderecoEvento);

        return enderecoEventoRepository
            .findById(enderecoEvento.getId())
            .map(existingEnderecoEvento -> {
                if (enderecoEvento.getCep() != null) {
                    existingEnderecoEvento.setCep(enderecoEvento.getCep());
                }
                if (enderecoEvento.getLogradouro() != null) {
                    existingEnderecoEvento.setLogradouro(enderecoEvento.getLogradouro());
                }
                if (enderecoEvento.getComplemento() != null) {
                    existingEnderecoEvento.setComplemento(enderecoEvento.getComplemento());
                }
                if (enderecoEvento.getNumero() != null) {
                    existingEnderecoEvento.setNumero(enderecoEvento.getNumero());
                }
                if (enderecoEvento.getBairro() != null) {
                    existingEnderecoEvento.setBairro(enderecoEvento.getBairro());
                }
                if (enderecoEvento.getCidade() != null) {
                    existingEnderecoEvento.setCidade(enderecoEvento.getCidade());
                }
                if (enderecoEvento.getUf() != null) {
                    existingEnderecoEvento.setUf(enderecoEvento.getUf());
                }

                return existingEnderecoEvento;
            })
            .map(enderecoEventoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoEvento> findAll(Pageable pageable) {
        log.debug("Request to get all EnderecoEventos");
        return enderecoEventoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnderecoEvento> findOne(Long id) {
        log.debug("Request to get EnderecoEvento : {}", id);
        return enderecoEventoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EnderecoEvento : {}", id);
        enderecoEventoRepository.deleteById(id);
    }
}
