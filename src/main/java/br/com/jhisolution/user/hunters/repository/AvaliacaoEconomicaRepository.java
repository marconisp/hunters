package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.AvaliacaoEconomica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AvaliacaoEconomica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvaliacaoEconomicaRepository extends JpaRepository<AvaliacaoEconomica, Long> {}
