package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoDocumento;
import br.com.jhisolution.user.hunters.domain.FotoPagar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoPagar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoPagarRepository extends JpaRepository<FotoPagar, Long> {
    default Page<FotoPagar> findAllFotoPagarByPagarId(Long id, Pageable pageable) {
        return this.findAllFotoByPagarId(id, pageable);
    }

    @Query("select DISTINCT pagar.fotoPagars from Pagar pagar join pagar.fotoPagars where pagar.id =:id")
    Page<FotoPagar> findAllFotoByPagarId(@Param("id") Long id, Pageable pageable);
}
