package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Receber;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Receber entity.
 */
@Repository
public interface ReceberRepository extends JpaRepository<Receber, Long> {
    default Optional<Receber> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Receber> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Receber> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct receber from Receber receber left join fetch receber.tipoReceber left join fetch receber.receberDe left join fetch receber.tipoTransacao",
        countQuery = "select count(distinct receber) from Receber receber"
    )
    Page<Receber> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct receber from Receber receber left join fetch receber.tipoReceber left join fetch receber.receberDe left join fetch receber.tipoTransacao"
    )
    List<Receber> findAllWithToOneRelationships();

    @Query(
        "select receber from Receber receber left join fetch receber.tipoReceber left join fetch receber.receberDe left join fetch receber.tipoTransacao where receber.id =:id"
    )
    Optional<Receber> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select receber from Receber receber where receber.data >= :dataInicial and receber.data <= :dataFinal")
    List<Receber> findAllByDataInicialAndDataFinal(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);
}
