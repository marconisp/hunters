package br.com.jhisolution.user.hunters.service;

import br.com.jhisolution.user.hunters.service.dto.PeriodoDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.FluxoCaixaDTO;
import java.util.List;
import org.springframework.core.io.Resource;

public interface FluxoCaixaService {
    List<FluxoCaixaDTO> findAllByDataInicialAndDataFinal(PeriodoDTO filtro);
    Resource findAllByDataInicialAndDataFinalJasper(PeriodoDTO filtro);
}
