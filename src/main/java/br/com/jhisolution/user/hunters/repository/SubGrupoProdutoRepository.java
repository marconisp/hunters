package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.SubGrupoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubGrupoProduto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubGrupoProdutoRepository extends JpaRepository<SubGrupoProduto, Long> {}
