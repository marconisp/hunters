package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Materia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {}
