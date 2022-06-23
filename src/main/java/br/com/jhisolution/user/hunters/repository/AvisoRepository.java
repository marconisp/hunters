package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Aviso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Aviso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {}
