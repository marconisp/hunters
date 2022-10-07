package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoReceber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoReceber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoReceberRepository extends JpaRepository<FotoReceber, Long> {
    default Page<FotoReceber> findAllFotoReceberByReceberId(Long id, Pageable pageable) {
        return this.findAllFotoByReceberId(id, pageable);
    }

    @Query("select DISTINCT receber.fotoRecebers from Receber receber join receber.fotoRecebers where receber.id =:id")
    Page<FotoReceber> findAllFotoByReceberId(@Param("id") Long id, Pageable pageable);
}
