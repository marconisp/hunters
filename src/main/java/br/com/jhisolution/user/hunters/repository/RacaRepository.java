package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Raca;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Raca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {}
