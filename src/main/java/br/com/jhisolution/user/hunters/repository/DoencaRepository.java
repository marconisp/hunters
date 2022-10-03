package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Doenca;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Doenca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoencaRepository extends JpaRepository<Doenca, Long> {}
