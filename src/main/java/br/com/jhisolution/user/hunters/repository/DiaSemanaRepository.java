package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DiaSemana entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Long> {}
