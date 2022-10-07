package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.AgendaColaborador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AgendaColaborador entity.
 */
@Repository
public interface AgendaColaboradorRepository extends JpaRepository<AgendaColaborador, Long> {
    default Optional<AgendaColaborador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AgendaColaborador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AgendaColaborador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct agendaColaborador from AgendaColaborador agendaColaborador left join fetch agendaColaborador.periodoDuracao",
        countQuery = "select count(distinct agendaColaborador) from AgendaColaborador agendaColaborador"
    )
    Page<AgendaColaborador> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct agendaColaborador from AgendaColaborador agendaColaborador left join fetch agendaColaborador.periodoDuracao")
    List<AgendaColaborador> findAllWithToOneRelationships();

    @Query(
        "select agendaColaborador from AgendaColaborador agendaColaborador left join fetch agendaColaborador.periodoDuracao where agendaColaborador.id =:id"
    )
    Optional<AgendaColaborador> findOneWithToOneRelationships(@Param("id") Long id);
}
