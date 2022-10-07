package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoIcon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoIcon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoIconRepository extends JpaRepository<FotoIcon, Long> {}
