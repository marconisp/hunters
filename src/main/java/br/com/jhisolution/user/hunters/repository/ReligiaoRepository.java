package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Religiao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Religiao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReligiaoRepository extends JpaRepository<Religiao, Long> {}
