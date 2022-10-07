package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Colaborador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {}
