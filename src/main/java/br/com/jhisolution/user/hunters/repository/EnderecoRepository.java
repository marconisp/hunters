package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Endereco entity.
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    default Page<Endereco> findAllByDadosPessoaisId(Long id, Pageable pageable) {
        return this.findAllByPessoalId(id, pageable);
    }

    @Query(
        "select DISTINCT dadosPessoais.enderecos from DadosPessoais dadosPessoais join dadosPessoais.enderecos where dadosPessoais.id =:id"
    )
    Page<Endereco> findAllByPessoalId(@Param("id") Long id, Pageable pageable);
}
