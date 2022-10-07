package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.TipoContratacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoContratacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoContratacaoRepository extends JpaRepository<TipoContratacao, Long> {}
