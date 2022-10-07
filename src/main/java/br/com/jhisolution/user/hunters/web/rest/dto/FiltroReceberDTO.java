package br.com.jhisolution.user.hunters.web.rest.dto;

import br.com.jhisolution.user.hunters.domain.DadosPessoais;
import br.com.jhisolution.user.hunters.domain.ReceberDe;
import br.com.jhisolution.user.hunters.domain.TipoReceber;
import br.com.jhisolution.user.hunters.domain.TipoTransacao;
import br.com.jhisolution.user.hunters.domain.enumeration.StatusContaReceber;
import java.time.LocalDate;

public class FiltroReceberDTO {

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String tipo;
    private TipoTransacao transacao;
    private TipoReceber tipoReceber;
    private ReceberDe receberDe;
    private StatusContaReceber status;
    private DadosPessoais dadosPessoais;

    public FiltroReceberDTO() {
        super();
    }

    public FiltroReceberDTO(LocalDate dataInicio, LocalDate dataFim) {
        super();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public FiltroReceberDTO(
        LocalDate dataInicio,
        LocalDate dataFim,
        String tipo,
        TipoTransacao transacao,
        TipoReceber tipoReceber,
        ReceberDe receberDe,
        StatusContaReceber status,
        DadosPessoais dadosPessoais
    ) {
        super();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo = tipo;
        this.transacao = transacao;
        this.tipoReceber = tipoReceber;
        this.receberDe = receberDe;
        this.status = status;
        this.dadosPessoais = dadosPessoais;
    }

    public static FiltroReceberDTO getInstance(LocalDate dataInicio, LocalDate dataFim) {
        return new FiltroReceberDTO(dataInicio, dataFim);
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

    public TipoReceber getTipoReceber() {
        return tipoReceber;
    }

    public void setTipoReceber(TipoReceber tipoReceber) {
        this.tipoReceber = tipoReceber;
    }

    public ReceberDe getReceberDe() {
        return receberDe;
    }

    public void setReceberDe(ReceberDe receberDe) {
        this.receberDe = receberDe;
    }

    public StatusContaReceber getStatus() {
        return status;
    }

    public void setStatus(StatusContaReceber status) {
        this.status = status;
    }

    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }
}
