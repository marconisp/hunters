package br.com.jhisolution.user.hunters.web.rest.dto;

public class FluxoCaixaDTO {

    private String data;
    private Float receber;
    private String statusReceber;
    private Float pagar;
    private String statusPagar;
    private Float saldo;
    private Float saldoFinal;

    public FluxoCaixaDTO() {
        super();
    }

    public FluxoCaixaDTO(String data, Float receber, String statusReceber, Float pagar, String statusPagar, Float saldo, Float saldoFinal) {
        super();
        this.data = data;
        this.receber = receber;
        this.statusReceber = statusReceber;
        this.pagar = pagar;
        this.statusPagar = statusPagar;
        this.saldo = saldo;
        this.saldoFinal = saldoFinal;
    }

    public static FluxoCaixaDTO getInstance(
        String data,
        Float receber,
        String statusReceber,
        Float pagar,
        String statusPagar,
        Float saldo,
        Float saldoFinal
    ) {
        return new FluxoCaixaDTO(data, receber, statusReceber, pagar, statusPagar, saldo, saldoFinal);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Float getReceber() {
        return receber;
    }

    public void setReceber(Float receber) {
        this.receber = receber;
    }

    public String getStatusReceber() {
        return statusReceber;
    }

    public void setStatusReceber(String statusReceber) {
        this.statusReceber = statusReceber;
    }

    public Float getPagar() {
        return pagar;
    }

    public void setPagar(Float pagar) {
        this.pagar = pagar;
    }

    public String getStatusPagar() {
        return statusPagar;
    }

    public void setStatusPagar(String statusPagar) {
        this.statusPagar = statusPagar;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public Float getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Float saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
}
