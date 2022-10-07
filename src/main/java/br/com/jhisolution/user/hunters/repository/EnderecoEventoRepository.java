package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.EnderecoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EnderecoEvento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnderecoEventoRepository extends JpaRepository<EnderecoEvento, Long> {}
