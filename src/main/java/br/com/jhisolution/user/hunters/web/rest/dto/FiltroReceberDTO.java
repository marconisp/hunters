package br.com.jhisolution.user.hunters.web.rest.dto;

import java.time.LocalDate;

public class FiltroReceberDTO {

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String tipo;
    private String transacao;
    private String tipoReceber;
    private String receberDe;
    private String status;

    public FiltroReceberDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FiltroReceberDTO(
        LocalDate dataInicio,
        LocalDate dataFim,
        String tipo,
        String transacao,
        String tipoReceber,
        String receberDe,
        String status
    ) {
        super();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo = tipo;
        this.transacao = transacao;
        this.tipoReceber = tipoReceber;
        this.receberDe = receberDe;
        this.status = status;
    }

    public static FiltroReceberDTO getInstance(
        LocalDate dataInicio,
        LocalDate dataFim,
        String tipo,
        String transacao,
        String tipoReceber,
        String receberDe,
        String status
    ) {
        return new FiltroReceberDTO(dataInicio, dataFim, tipo, transacao, tipoReceber, receberDe, status);
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

    public String getTransacao() {
        return transacao;
    }

    public void setTransacao(String transacao) {
        this.transacao = transacao;
    }

    public String getTipoReceber() {
        return tipoReceber;
    }

    public void setTipoReceber(String tipoReceber) {
        this.tipoReceber = tipoReceber;
    }

    public String getReceberDe() {
        return receberDe;
    }

    public void setReceberDe(String receberDe) {
        this.receberDe = receberDe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
