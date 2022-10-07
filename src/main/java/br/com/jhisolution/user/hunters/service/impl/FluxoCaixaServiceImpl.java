package br.com.jhisolution.user.hunters.service.impl;

import br.com.jhisolution.user.hunters.service.FluxoCaixaService;
import br.com.jhisolution.user.hunters.service.PagarService;
import br.com.jhisolution.user.hunters.service.ReceberService;
import br.com.jhisolution.user.hunters.service.dto.PeriodoDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroPagarDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.FiltroReceberDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.FluxoCaixaDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.PagarDTO;
import br.com.jhisolution.user.hunters.web.rest.dto.ReceberDTO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FluxoCaixaServiceImpl implements FluxoCaixaService {

    private final Logger log = LoggerFactory.getLogger(FluxoCaixaServiceImpl.class);
    private PagarService pagarService;
    private ReceberService receberService;
    private Float saldoFinal = 0F;

    public FluxoCaixaServiceImpl(PagarService pagarService, ReceberService receberService) {
        this.pagarService = pagarService;
        this.receberService = receberService;
    }

    @Override
    public List<FluxoCaixaDTO> findAllByDataInicialAndDataFinal(PeriodoDTO filtro) {
        log.debug("Request to get Fluxo Caixa by data início:{} e data fim:{}", filtro.getDataInicio(), filtro.getDataFim());

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYY");

        FiltroPagarDTO filtroPagar = FiltroPagarDTO.gitInstance(filtro.getDataInicio(), filtro.getDataFim());
        List<PagarDTO> listaPagar = pagarService.findAllByDataInicialAndDataFinal(filtroPagar);

        FiltroReceberDTO filtroReceber = FiltroReceberDTO.getInstance(filtro.getDataInicio(), filtro.getDataFim());
        List<ReceberDTO> listaReceber = receberService.findAllByDataInicialAndDataFinal(filtroReceber);

        List<FluxoCaixaDTO> listaFluxo = new ArrayList<>();

        saldoFinal = 0F;

        log.debug("findAllByDataInicialAndDataFinal -> Data início:{} e data fim:{}", filtro.getDataInicio(), filtro.getDataFim());

        if (listaPagar.size() >= listaReceber.size()) {
            listaPagar.forEach(pagar -> {
                FluxoCaixaDTO fluxo = FluxoCaixaDTO.getInstance(pagar.getData(), null, null, pagar.getValor(), pagar.getStatus(), 0F, 0F);
                for (ReceberDTO receber : listaReceber) {
                    if (pagar.getData().equals(receber.getData()) && receber.getTratada() == false) {
                        if (pagar.getData().equals(receber.getData())) {
                            fluxo.setReceber(receber.getValor());
                            fluxo.setStatusReceber(receber.getStatus());
                            receber.setTratada(true);
                            break;
                        }
                    }
                }
                listaFluxo.add(fluxo);
            });

            listaReceber
                .stream()
                .forEach(receber -> {
                    if (receber.getTratada() == false) {
                        listaFluxo.add(
                            FluxoCaixaDTO.getInstance(receber.getData(), receber.getValor(), receber.getStatus(), null, null, 0F, 0F)
                        );
                        receber.setTratada(true);
                    }
                });
        } else {
            listaReceber.forEach(receber -> {
                FluxoCaixaDTO fluxo = FluxoCaixaDTO.getInstance(
                    receber.getData(),
                    receber.getValor(),
                    receber.getStatus(),
                    null,
                    null,
                    null,
                    null
                );
                for (PagarDTO pagar : listaPagar) {
                    if (pagar.getData().equals(receber.getData()) && pagar.getTratada() == false) {
                        fluxo.setPagar(pagar.getValor());
                        fluxo.setStatusPagar(pagar.getStatus());
                        pagar.setTratada(true);
                    }
                    break;
                }
                listaFluxo.add(fluxo);
            });
            listaPagar
                .stream()
                .forEach(pagar -> {
                    if (pagar.getTratada() == false) {
                        listaFluxo.add(FluxoCaixaDTO.getInstance(pagar.getData(), pagar.getValor(), pagar.getStatus(), null, null, 0F, 0F));
                        pagar.setTratada(true);
                    }
                });
        }

        List<FluxoCaixaDTO> lista = listaFluxo.stream().sorted(Comparator.comparing(FluxoCaixaDTO::getData)).collect(Collectors.toList());

        lista.forEach(fluxo -> {
            float receber = Objects.nonNull(fluxo.getReceber()) ? fluxo.getReceber() : 0F;
            float pagar = Objects.nonNull(fluxo.getPagar()) ? fluxo.getPagar() : 0F;
            float saldo = receber - pagar;
            saldoFinal += saldo;
            fluxo.setSaldo(saldo);
            fluxo.setSaldoFinal(saldoFinal);
        });

        return lista;
    }

    @Override
    public Resource findAllByDataInicialAndDataFinalJasper(PeriodoDTO filtro) {
        // TODO Auto-generated method stub
        return null;
    }
}
