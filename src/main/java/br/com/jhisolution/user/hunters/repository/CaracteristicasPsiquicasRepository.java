package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.CaracteristicasPsiquicas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CaracteristicasPsiquicas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaracteristicasPsiquicasRepository extends JpaRepository<CaracteristicasPsiquicas, Long> {}
