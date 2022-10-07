package br.com.jhisolution.user.hunters.web.rest.dto;

public class ReceberDTO {

    private Long id;
    private String data;
    private Float valor;
    private String status;
    private String tipoReceber;
    private String receberDe;
    private String pessoaReceber;
    private String tipoTransacao;
    private Boolean tratada;

    public ReceberDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ReceberDTO(
        Long id,
        String data,
        Float valor,
        String status,
        String tipoReceber,
        String receberDe,
        String pessoaReceber,
        String tipoTransacao,
        Boolean tratada
    ) {
        super();
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.status = status;
        this.tipoReceber = tipoReceber;
        this.receberDe = receberDe;
        this.pessoaReceber = pessoaReceber;
        this.tipoTransacao = tipoTransacao;
        this.tratada = tratada;
    }

    public static ReceberDTO getInstance(
        Long id,
        String data,
        Float valor,
        String status,
        String tipoReceber,
        String receberDe,
        String pessoaReceber,
        String tipoTransacao,
        Boolean tratada
    ) {
        return new ReceberDTO(id, data, valor, status, tipoReceber, receberDe, pessoaReceber, tipoTransacao, tratada);
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

    public String getPessoaReceber() {
        return pessoaReceber;
    }

    public void setPessoaReceber(String pessoaReceber) {
        this.pessoaReceber = pessoaReceber;
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
