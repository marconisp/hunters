package br.com.jhisolution.user.hunters.web.rest;

import br.com.jhisolution.user.hunters.service.FluxoCaixaService;
import br.com.jhisolution.user.hunters.service.dto.PeriodoDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.FluxoCaixaDTO;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FluxoCaixaResource {

    private final Logger log = LoggerFactory.getLogger(ReceberResource.class);

    private FluxoCaixaService fluxoCaixaService;

    public FluxoCaixaResource(FluxoCaixaService fluxoCaixaService) {
        this.fluxoCaixaService = fluxoCaixaService;
    }

    @PostMapping("/fluxoCaixa/periodo")
    public ResponseEntity<List<FluxoCaixaDTO>> getFluxoCaixa(@Valid @RequestBody PeriodoDTO filtro) throws URISyntaxException {
        log.debug("REST request to get a list of Recebers period - initial:{} to final:{}", filtro.getDataInicio(), filtro.getDataFim());
        List<FluxoCaixaDTO> caixas = fluxoCaixaService.findAllByDataInicialAndDataFinal(filtro);
        return ResponseEntity.ok().body(caixas);
    }
}
