package br.com.jhisolution.user.hunters.repository;

import br.com.jhisolution.user.hunters.domain.TipoMensagem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoMensagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoMensagemRepository extends JpaRepository<TipoMensagem, Long> {}
