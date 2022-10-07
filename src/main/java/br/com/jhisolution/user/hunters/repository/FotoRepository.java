package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Foto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {}
