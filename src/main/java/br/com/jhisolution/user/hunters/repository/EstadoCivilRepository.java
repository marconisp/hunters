package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.EstadoCivil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EstadoCivil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {}
