package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.ExameMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExameMedico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExameMedicoRepository extends JpaRepository<ExameMedico, Long> {}
