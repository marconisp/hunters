package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoTransacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {}
