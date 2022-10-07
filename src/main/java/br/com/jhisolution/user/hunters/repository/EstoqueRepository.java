package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Estoque;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Estoque entity.
 */
@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    default Optional<Estoque> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Estoque> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Estoque> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct estoque from Estoque estoque left join fetch estoque.produto left join fetch estoque.grupoProduto left join fetch estoque.subGrupoProduto",
        countQuery = "select count(distinct estoque) from Estoque estoque"
    )
    Page<Estoque> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct estoque from Estoque estoque left join fetch estoque.produto left join fetch estoque.grupoProduto left join fetch estoque.subGrupoProduto"
    )
    List<Estoque> findAllWithToOneRelationships();

    @Query(
        "select estoque from Estoque estoque left join fetch estoque.produto left join fetch estoque.grupoProduto left join fetch estoque.subGrupoProduto where estoque.id =:id"
    )
    Optional<Estoque> findOneWithToOneRelationships(@Param("id") Long id);
}
