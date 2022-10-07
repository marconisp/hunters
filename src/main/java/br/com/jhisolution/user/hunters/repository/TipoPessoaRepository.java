package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoPessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Long> {}
