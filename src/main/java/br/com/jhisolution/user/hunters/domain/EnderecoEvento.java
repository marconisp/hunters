package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EnderecoEvento.
 */
@Entity
@Table(name = "endereco_evento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EnderecoEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 8, max = 10)
    @Column(name = "cep", length = 10, nullable = false)
    private String cep;

    @NotNull
    @Size(max = 50)
    @Column(name = "logradouro", length = 50, nullable = false)
    private String logradouro;

    @Size(max = 50)
    @Column(name = "complemento", length = 50)
    private String complemento;

    @NotNull
    @Size(max = 10)
    @Column(name = "numero", length = 10, nullable = false)
    private String numero;

    @NotNull
    @Size(max = 50)
    @Column(name = "bairro", length = 50, nullable = false)
    private String bairro;

    @NotNull
    @Size(max = 50)
    @Column(name = "cidade", length = 50, nullable = false)
    private String cidade;

    @NotNull
    @Size(max = 2)
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @ManyToOne
    @JsonIgnoreProperties(value = { "periodoDuracao", "enderecos", "dadosPessoais1" }, allowSetters = true)
    private Evento evento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EnderecoEvento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return this.cep;
    }

    public EnderecoEvento cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public EnderecoEvento logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public EnderecoEvento complemento(String complemento) {
        this.setComplemento(complemento);
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return this.numero;
    }

    public EnderecoEvento numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public EnderecoEvento bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public EnderecoEvento cidade(String cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public EnderecoEvento uf(String uf) {
        this.setUf(uf);
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Evento getEvento() {
        return this.evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public EnderecoEvento evento(Evento evento) {
        this.setEvento(evento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnderecoEvento)) {
            return false;
        }
        return id != null && id.equals(((EnderecoEvento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoEvento{" +
            "id=" + getId() +
            ", cep='" + getCep() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", numero='" + getNumero() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", uf='" + getUf() + "'" +
            "}";
    }
}
