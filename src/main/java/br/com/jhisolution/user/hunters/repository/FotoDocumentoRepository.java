package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.FotoDocumento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FotoDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoDocumentoRepository extends JpaRepository<FotoDocumento, Long> {}
