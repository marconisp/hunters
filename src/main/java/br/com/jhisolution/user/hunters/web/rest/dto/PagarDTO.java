package br.com.jhisolution.user.hunters.web.rest.dto;

public class PagarDTO {

    private Long id;
    private String data;
    private Float valor;
    private String status;
    private String tipoPagar;
    private String pagarPara;
    private String pessoaPagar;
    private String tipoTransacao;
    private Boolean tratada;

    public PagarDTO() {
        super();
    }

    public PagarDTO(
        Long id,
        String data,
        Float valor,
        String status,
        String tipoPagar,
        String pagarPara,
        String pessoaPagar,
        String tipoTransacao,
        Boolean tratada
    ) {
        super();
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.status = status;
        this.tipoPagar = tipoPagar;
        this.pagarPara = pagarPara;
        this.pessoaPagar = pessoaPagar;
        this.tipoTransacao = tipoTransacao;
        this.tratada = tratada;
    }

    public static PagarDTO getInstance(
        Long id,
        String data,
        Float valor,
        String status,
        String tipoPagar,
        String pagarPara,
        String pessoaPagar,
        String tipoTransacao,
        Boolean tratada
    ) {
        return new PagarDTO(id, data, valor, status, tipoPagar, pagarPara, pessoaPagar, tipoTransacao, tratada);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoPagar() {
        return tipoPagar;
    }

    public void setTipoPagar(String tipoPagar) {
        this.tipoPagar = tipoPagar;
    }

    public String getPagarPara() {
        return pagarPara;
    }

    public void setPagarPara(String pagarPara) {
        this.pagarPara = pagarPara;
    }

    public String getPessoaPagar() {
        return pessoaPagar;
    }

    public void setPessoaPagar(String pessoaPagar) {
        this.pessoaPagar = pessoaPagar;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Boolean getTratada() {
        return tratada;
    }

    public void setTratada(Boolean tratada) {
        this.tratada = tratada;
    }
}
