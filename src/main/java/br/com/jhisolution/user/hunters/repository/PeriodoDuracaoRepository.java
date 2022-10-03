package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.PeriodoDuracao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PeriodoDuracao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoDuracaoRepository extends JpaRepository<PeriodoDuracao, Long> {}
