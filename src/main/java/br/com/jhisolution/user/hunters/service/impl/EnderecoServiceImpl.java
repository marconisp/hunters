package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.Endereco;
import br.com.jhisolution.user.hunters.repository.DadosPessoaisRepository;
import br.com.jhisolution.user.hunters.repository.EnderecoRepository;
import br.com.jhisolution.user.hunters.service.EnderecoService;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Endereco}.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;
    private final DadosPessoaisRepository dadosPessoaisRepository;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, DadosPessoaisRepository dadosPessoaisRepository) {
        this.enderecoRepository = enderecoRepository;
        this.dadosPessoaisRepository = dadosPessoaisRepository;
    }

    @Override
    public Endereco save(Endereco endereco) {
        log.debug("Request to save Endereco : {}", endereco);

        if (Objects.nonNull(endereco.getDadosPessoais()) && Objects.nonNull(endereco.getDadosPessoais().getId())) {
            DadosPessoais dadosPessoais = dadosPessoaisRepository.findById(endereco.getDadosPessoais().getId()).get();
            endereco.setDadosPessoais(dadosPessoais);
        }

        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco update(Endereco endereco) {
        log.debug("Request to save Endereco : {}", endereco);
        return enderecoRepository.save(endereco);
    }

    @Override
    public Optional<Endereco> partialUpdate(Endereco endereco) {
        log.debug("Request to partially update Endereco : {}", endereco);

        return enderecoRepository
            .findById(endereco.getId())
            .map(existingEndereco -> {
                if (endereco.getCep() != null) {
                    existingEndereco.setCep(endereco.getCep());
                }
                if (endereco.getLogradouro() != null) {
                    existingEndereco.setLogradouro(endereco.getLogradouro());
                }
                if (endereco.getComplemento1() != null) {
                    existingEndereco.setComplemento1(endereco.getComplemento1());
                }
                if (endereco.getComplemento2() != null) {
                    existingEndereco.setComplemento2(endereco.getComplemento2());
                }
                if (endereco.getNumero() != null) {
                    existingEndereco.setNumero(endereco.getNumero());
                }
                if (endereco.getBairro() != null) {
                    existingEndereco.setBairro(endereco.getBairro());
                }
                if (endereco.getLocalidade() != null) {
                    existingEndereco.setLocalidade(endereco.getLocalidade());
                }
                if (endereco.getUf() != null) {
                    existingEndereco.setUf(endereco.getUf());
                }
                if (endereco.getUnidade() != null) {
                    existingEndereco.setUnidade(endereco.getUnidade());
                }
                if (endereco.getIbge() != null) {
                    existingEndereco.setIbge(endereco.getIbge());
                }
                if (endereco.getGia() != null) {
                    existingEndereco.setGia(endereco.getGia());
                }
                if (endereco.getLatitude() != null) {
                    existingEndereco.setLatitude(endereco.getLatitude());
                }
                if (endereco.getLongitude() != null) {
                    existingEndereco.setLongitude(endereco.getLongitude());
                }

                return existingEndereco;
            })
            .map(enderecoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Endereco> findAll(Pageable pageable) {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Endereco> findAllByDadosPessoaisId(Long id, Pageable pageable) {
        log.debug("Request to get all Enderecos by DadoPessoal id");
        return enderecoRepository.findAllByDadosPessoaisId(id, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Endereco> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
}
