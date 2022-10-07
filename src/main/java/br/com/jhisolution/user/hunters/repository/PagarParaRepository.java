package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.PagarPara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PagarPara entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagarParaRepository extends JpaRepository<PagarPara, Long> {}
