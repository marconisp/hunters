package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Documento;
import br.com.jhisolution.user.hunters.domain.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    default Page<Documento> findAllByDadosPessoaisId(Long id, Pageable pageable) {
        return this.findAllByPessoalId(id, pageable);
    }

    @Query(
        "select DISTINCT dadosPessoais.documentos from DadosPessoais dadosPessoais join dadosPessoais.documentos where dadosPessoais.id =:id"
    )
    Page<Documento> findAllByPessoalId(@Param("id") Long id, Pageable pageable);
}
