package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Alergia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlergiaRepository extends JpaRepository<Alergia, Long> {}
