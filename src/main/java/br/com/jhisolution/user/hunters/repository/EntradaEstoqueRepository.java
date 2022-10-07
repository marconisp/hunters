package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.EntradaEstoque;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EntradaEstoque entity.
 */
@Repository
public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {
    default Optional<EntradaEstoque> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EntradaEstoque> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EntradaEstoque> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct entradaEstoque from EntradaEstoque entradaEstoque left join fetch entradaEstoque.produto",
        countQuery = "select count(distinct entradaEstoque) from EntradaEstoque entradaEstoque"
    )
    Page<EntradaEstoque> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct entradaEstoque from EntradaEstoque entradaEstoque left join fetch entradaEstoque.produto")
    List<EntradaEstoque> findAllWithToOneRelationships();

    @Query("select entradaEstoque from EntradaEstoque entradaEstoque left join fetch entradaEstoque.produto where entradaEstoque.id =:id")
    Optional<EntradaEstoque> findOneWithToOneRelationships(@Param("id") Long id);
}
