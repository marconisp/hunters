package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {}
