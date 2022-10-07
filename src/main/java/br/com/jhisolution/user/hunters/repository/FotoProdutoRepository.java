package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoProdutoRepository extends JpaRepository<FotoProduto, Long> {}
