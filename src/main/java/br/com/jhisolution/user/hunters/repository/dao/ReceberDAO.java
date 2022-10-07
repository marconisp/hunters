package br.com.jhisolution.user.hunters.repository.dao;

import br.com.jhisolution.user.hunters.domain.Receber;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroReceberDTO;
import java.util.List;

public interface ReceberDAO {
    List<Receber> findAllByCriteria(FiltroReceberDTO filtro);
}
