package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Vacina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Vacina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {}
