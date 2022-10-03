package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.SaidaEstoque;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SaidaEstoque entity.
 */
@Repository
public interface SaidaEstoqueRepository extends JpaRepository<SaidaEstoque, Long> {
    default Optional<SaidaEstoque> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SaidaEstoque> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SaidaEstoque> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct saidaEstoque from SaidaEstoque saidaEstoque left join fetch saidaEstoque.produto",
        countQuery = "select count(distinct saidaEstoque) from SaidaEstoque saidaEstoque"
    )
    Page<SaidaEstoque> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct saidaEstoque from SaidaEstoque saidaEstoque left join fetch saidaEstoque.produto")
    List<SaidaEstoque> findAllWithToOneRelationships();

    @Query("select saidaEstoque from SaidaEstoque saidaEstoque left join fetch saidaEstoque.produto where saidaEstoque.id =:id")
    Optional<SaidaEstoque> findOneWithToOneRelationships(@Param("id") Long id);
}
