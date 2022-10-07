package br.com.jhisolution.user.hunters.repository.dao;

import br.com.jhisolution.user.hunters.domain.Pagar;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroPagarDTO;
import java.util.List;

public interface PagarDAO {
    List<Pagar> findAllByCriteria(FiltroPagarDTO filtro);
}
