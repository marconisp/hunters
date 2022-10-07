package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.ReceberDe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReceberDe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceberDeRepository extends JpaRepository<ReceberDe, Long> {}
