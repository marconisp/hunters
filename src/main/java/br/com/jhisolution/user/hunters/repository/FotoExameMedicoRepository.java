package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoExameMedico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoExameMedico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoExameMedicoRepository extends JpaRepository<FotoExameMedico, Long> {}
