package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoSaidaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoSaidaEstoque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoSaidaEstoqueRepository extends JpaRepository<FotoSaidaEstoque, Long> {}
