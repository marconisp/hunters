package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoEntradaEstoque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoEntradaEstoque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoEntradaEstoqueRepository extends JpaRepository<FotoEntradaEstoque, Long> {}
