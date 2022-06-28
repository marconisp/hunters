package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.domain.TipoPessoa;
import br.com.jhisolution.user.hunters.repository.TipoPessoaRepository;
import br.com.jhisolution.user.hunters.service.TipoPessoaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoPessoa}.
 */
@Service
@Transactional
public class TipoPessoaServiceImpl implements TipoPessoaService {

    private final Logger log = LoggerFactory.getLogger(TipoPessoaServiceImpl.class);

    private final TipoPessoaRepository tipoPessoaRepository;

    public TipoPessoaServiceImpl(TipoPessoaRepository tipoPessoaRepository) {
        this.tipoPessoaRepository = tipoPessoaRepository;
    }

    @Override
    public TipoPessoa save(TipoPessoa tipoPessoa) {
        log.debug("Request to save TipoPessoa : {}", tipoPessoa);
        return tipoPessoaRepository.save(tipoPessoa);
    }

    @Override
    public TipoPessoa update(TipoPessoa tipoPessoa) {
        log.debug("Request to save TipoPessoa : {}", tipoPessoa);
        return tipoPessoaRepository.save(tipoPessoa);
    }

    @Override
    public Optional<TipoPessoa> partialUpdate(TipoPessoa tipoPessoa) {
        log.debug("Request to partially update TipoPessoa : {}", tipoPessoa);

        return tipoPessoaRepository
            .findById(tipoPessoa.getId())
            .map(existingTipoPessoa -> {
                if (tipoPessoa.getCodigo() != null) {
                    existingTipoPessoa.setCodigo(tipoPessoa.getCodigo());
                }
                if (tipoPessoa.getNome() != null) {
                    existingTipoPessoa.setNome(tipoPessoa.getNome());
                }
                if (tipoPessoa.getDescricao() != null) {
                    existingTipoPessoa.setDescricao(tipoPessoa.getDescricao());
                }

                return existingTipoPessoa;
            })
            .map(tipoPessoaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoPessoa> findAll(Pageable pageable) {
        log.debug("Request to get all TipoPessoas");
        return tipoPessoaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPessoa> findOne(Long id) {
        log.debug("Request to get TipoPessoa : {}", id);
        return tipoPessoaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPessoa : {}", id);
        tipoPessoaRepository.deleteById(id);
    }
}
