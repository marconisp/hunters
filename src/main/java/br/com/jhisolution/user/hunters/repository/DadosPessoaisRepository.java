package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DadosPessoais entity.
 */
@Repository
public interface DadosPessoaisRepository extends JpaRepository<DadosPessoais, Long> {
    default Optional<DadosPessoais> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DadosPessoais> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DadosPessoais> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    Page<DadosPessoais> findAllByUser(Pageable pageable, User user);

    @Query(
        value = "select distinct dadosPessoais from DadosPessoais dadosPessoais left join fetch dadosPessoais.tipoPessoa left join fetch dadosPessoais.estadoCivil left join fetch dadosPessoais.raca left join fetch dadosPessoais.religiao",
        countQuery = "select count(distinct dadosPessoais) from DadosPessoais dadosPessoais"
    )
    Page<DadosPessoais> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dadosPessoais from DadosPessoais dadosPessoais left join fetch dadosPessoais.tipoPessoa left join fetch dadosPessoais.estadoCivil left join fetch dadosPessoais.raca left join fetch dadosPessoais.religiao"
    )
    List<DadosPessoais> findAllWithToOneRelationships();

    @Query(
        "select dadosPessoais from DadosPessoais dadosPessoais left join fetch dadosPessoais.tipoPessoa left join fetch dadosPessoais.estadoCivil left join fetch dadosPessoais.raca left join fetch dadosPessoais.religiao where dadosPessoais.id =:id"
    )
    Optional<DadosPessoais> findOneWithToOneRelationships(@Param("id") Long id);
}
