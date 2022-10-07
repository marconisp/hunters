package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.DadosMedico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DadosMedico entity.
 */
@Repository
public interface DadosMedicoRepository extends JpaRepository<DadosMedico, Long> {
    default Optional<DadosMedico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DadosMedico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DadosMedico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dadosMedico from DadosMedico dadosMedico left join fetch dadosMedico.vacina left join fetch dadosMedico.alergia left join fetch dadosMedico.doenca",
        countQuery = "select count(distinct dadosMedico) from DadosMedico dadosMedico"
    )
    Page<DadosMedico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dadosMedico from DadosMedico dadosMedico left join fetch dadosMedico.vacina left join fetch dadosMedico.alergia left join fetch dadosMedico.doenca"
    )
    List<DadosMedico> findAllWithToOneRelationships();

    @Query(
        "select dadosMedico from DadosMedico dadosMedico left join fetch dadosMedico.vacina left join fetch dadosMedico.alergia left join fetch dadosMedico.doenca where dadosMedico.id =:id"
    )
    Optional<DadosMedico> findOneWithToOneRelationships(@Param("id") Long id);
}
