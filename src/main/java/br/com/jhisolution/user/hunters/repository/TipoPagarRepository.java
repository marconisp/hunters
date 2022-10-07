package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.TipoPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoPagar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPagarRepository extends JpaRepository<TipoPagar, Long> {}
