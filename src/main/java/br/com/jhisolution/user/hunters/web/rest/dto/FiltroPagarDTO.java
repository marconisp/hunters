package br.com.jhisolution.user.hunters.web.rest.dto;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.PagarPara;
import br.com.jhisolution.user.hunters.domain.TipoPagar;
import br.com.jhisolution.user.hunters.domain.TipoTransacao;
import br.com.jhisolution.user.hunters.domain.enumeration.StatusContaPagar;
import java.time.LocalDate;

public class FiltroPagarDTO {

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String tipo;
    private TipoTransacao transacao;
    private TipoPagar tipoPagar;
    private PagarPara pagarPara;
    private StatusContaPagar status;
    private DadosPessoais dadosPessoais;

    public FiltroPagarDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FiltroPagarDTO(LocalDate dataInicio, LocalDate dataFim) {
        super();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public FiltroPagarDTO(
        LocalDate dataInicio,
        LocalDate dataFim,
        String tipo,
        TipoTransacao transacao,
        TipoPagar tipoPagar,
        PagarPara pagarPara,
        StatusContaPagar status,
        DadosPessoais dadosPessoais
    ) {
        super();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo = tipo;
        this.transacao = transacao;
        this.tipoPagar = tipoPagar;
        this.pagarPara = pagarPara;
        this.status = status;
        this.dadosPessoais = dadosPessoais;
    }

    public static FiltroPagarDTO gitInstance(LocalDate dataInicio, LocalDate dataFim) {
        return new FiltroPagarDTO(dataInicio, dataFim);
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public TipoTransacao getTransacao() {
        return transacao;
    }

    public void setTransacao(TipoTransacao transacao) {
        this.transacao = transacao;
    }

    public TipoPagar getTipoPagar() {
        return tipoPagar;
    }

    public void setTipoPagar(TipoPagar tipoPagar) {
        this.tipoPagar = tipoPagar;
    }

    public PagarPara getPagarPara() {
        return pagarPara;
    }

    public void setPagarPara(PagarPara pagarPara) {
        this.pagarPara = pagarPara;
    }

    public StatusContaPagar getStatus() {
        return status;
    }

    public void setStatus(StatusContaPagar status) {
        this.status = status;
    }

    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }
}
