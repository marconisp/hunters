package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.GrupoProduto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GrupoProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoProdutoRepository extends JpaRepository<GrupoProduto, Long> {}
