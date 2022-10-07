package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoDocumento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoDocumentoRepository extends JpaRepository<FotoDocumento, Long> {
    default Page<FotoDocumento> findAllFotoDocumentosByDocumentoId(Long id, Pageable pageable) {
        return this.findAllFotoByDocumentoId(id, pageable);
    }

    @Query("select DISTINCT documento.fotos from Documento documento join documento.fotos where documento.id =:id")
    Page<FotoDocumento> findAllFotoByDocumentoId(@Param("id") Long id, Pageable pageable);
}
