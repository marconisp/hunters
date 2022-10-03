package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.TipoReceber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoReceber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoReceberRepository extends JpaRepository<TipoReceber, Long> {}
