package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Mensagem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Mensagem entity.
 */
@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    default Optional<Mensagem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Mensagem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Mensagem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    default Page<Mensagem> findAllByDadosPessoaisId(Long id, Pageable pageable) {
        return this.findAllByPessoalId(id, pageable);
    }

    @Query(
        "select DISTINCT dadosPessoais.mensagems from DadosPessoais dadosPessoais join dadosPessoais.mensagems where dadosPessoais.id =:id"
    )
    Page<Mensagem> findAllByPessoalId(@Param("id") Long id, Pageable pageable);

    @Query(
        value = "select distinct mensagem from Mensagem mensagem left join fetch mensagem.tipo",
        countQuery = "select count(distinct mensagem) from Mensagem mensagem"
    )
    Page<Mensagem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct mensagem from Mensagem mensagem left join fetch mensagem.tipo")
    List<Mensagem> findAllWithToOneRelationships();

    @Query("select mensagem from Mensagem mensagem left join fetch mensagem.tipo where mensagem.id =:id")
    Optional<Mensagem> findOneWithToOneRelationships(@Param("id") Long id);
}
