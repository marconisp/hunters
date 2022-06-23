package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Endereco.
 */
@Entity
@Table(name = "endereco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Endereco implements Serializable {

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
    @Size(min = 1, max = 50)
    @Column(name = "logradouro", length = 50, nullable = false)
    private String logradouro;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "complemento_1", length = 50, nullable = false)
    private String complemento1;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "complemento_2", length = 50, nullable = false)
    private String complemento2;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero", length = 10, nullable = false)
    private String numero;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "bairro", length = 50, nullable = false)
    private String bairro;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "localidade", length = 50, nullable = false)
    private String localidade;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "uf", length = 50, nullable = false)
    private String uf;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "unidade", length = 20, nullable = false)
    private String unidade;

    @NotNull
    @Size(min = 10, max = 20)
    @Column(name = "ibge", length = 20, nullable = false)
    private String ibge;

    @NotNull
    @Size(min = 10, max = 20)
    @Column(name = "gia", length = 20, nullable = false)
    private String gia;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "estadoCivil", "raca", "religiao", "foto", "fotoAvatar", "fotoIcon", "mensagems", "avisos", "documentos", "enderecos", "user",
        },
        allowSetters = true
    )
    private DadosPessoais dadosPessoais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Endereco id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return this.cep;
    }

    public Endereco cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public Endereco logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento1() {
        return this.complemento1;
    }

    public Endereco complemento1(String complemento1) {
        this.setComplemento1(complemento1);
        return this;
    }

    public void setComplemento1(String complemento1) {
        this.complemento1 = complemento1;
    }

    public String getComplemento2() {
        return this.complemento2;
    }

    public Endereco complemento2(String complemento2) {
        this.setComplemento2(complemento2);
        return this;
    }

    public void setComplemento2(String complemento2) {
        this.complemento2 = complemento2;
    }

    public String getNumero() {
        return this.numero;
    }

    public Endereco numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public Endereco bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return this.localidade;
    }

    public Endereco localidade(String localidade) {
        this.setLocalidade(localidade);
        return this;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return this.uf;
    }

    public Endereco uf(String uf) {
        this.setUf(uf);
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getUnidade() {
        return this.unidade;
    }

    public Endereco unidade(String unidade) {
        this.setUnidade(unidade);
        return this;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getIbge() {
        return this.ibge;
    }

    public Endereco ibge(String ibge) {
        this.setIbge(ibge);
        return this;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return this.gia;
    }

    public Endereco gia(String gia) {
        this.setGia(gia);
        return this;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public Endereco latitude(Float latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Endereco longitude(Float longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Endereco dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Endereco)) {
            return false;
        }
        return id != null && id.equals(((Endereco) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Endereco{" +
            "id=" + getId() +
            ", cep='" + getCep() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", complemento1='" + getComplemento1() + "'" +
            ", complemento2='" + getComplemento2() + "'" +
            ", numero='" + getNumero() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", localidade='" + getLocalidade() + "'" +
            ", uf='" + getUf() + "'" +
            ", unidade='" + getUnidade() + "'" +
            ", ibge='" + getIbge() + "'" +
            ", gia='" + getGia() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
