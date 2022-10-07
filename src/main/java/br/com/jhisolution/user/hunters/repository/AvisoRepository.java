package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.Aviso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Aviso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {
    default Page<Aviso> findAllByDadosPessoaisId(Long id, Pageable pageable) {
        return this.findAllByPessoalId(id, pageable);
    }

    @Query("select DISTINCT dadosPessoais.avisos from DadosPessoais dadosPessoais join dadosPessoais.avisos where dadosPessoais.id =:id")
    Page<Aviso> findAllByPessoalId(@Param("id") Long id, Pageable pageable);
}
