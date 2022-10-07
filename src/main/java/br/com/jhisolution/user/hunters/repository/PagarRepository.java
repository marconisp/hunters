package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.repository.dao.PagarDAO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pagar entity.
 */
@Repository
public interface PagarRepository extends JpaRepository<Pagar, Long>, PagarDAO {
    default Optional<Pagar> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Pagar> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Pagar> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct pagar from Pagar pagar left join fetch pagar.tipoPagar left join fetch pagar.pagarPara left join fetch pagar.tipoTransacao",
        countQuery = "select count(distinct pagar) from Pagar pagar"
    )
    Page<Pagar> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct pagar from Pagar pagar left join fetch pagar.tipoPagar left join fetch pagar.pagarPara left join fetch pagar.tipoTransacao"
    )
    List<Pagar> findAllWithToOneRelationships();

    @Query(
        "select pagar from Pagar pagar left join fetch pagar.tipoPagar left join fetch pagar.pagarPara left join fetch pagar.tipoTransacao where pagar.id =:id"
    )
    Optional<Pagar> findOneWithToOneRelationships(@Param("id") Long id);
}
