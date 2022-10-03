package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EntradaEstoque.
 */
@Entity
@Table(name = "entrada_estoque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntradaEstoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "qtde", nullable = false)
    private Integer qtde;

    @NotNull
    @Column(name = "valor_unitario", nullable = false)
    private Float valorUnitario;

    @Size(max = 50)
    @Column(name = "obs", length = 50)
    private String obs;

    @JsonIgnoreProperties(value = { "fotoProdutos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Produto produto;

    @OneToMany(mappedBy = "entradaEstoque")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entradaEstoque" }, allowSetters = true)
    private Set<FotoEntradaEstoque> fotoEntradaEstoques = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "matriculas",
            "dadosMedicos",
            "avaliacaoEconomicas",
            "acompanhamentoAlunos",
            "colaboradors",
            "caracteristicasPsiquicas",
            "exameMedicos",
            "eventos",
            "pagars",
            "recebers",
            "entradaEstoques",
            "saidaEstoques",
        },
        allowSetters = true
    )
    private DadosPessoais dadosPessoais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EntradaEstoque id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public EntradaEstoque data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getQtde() {
        return this.qtde;
    }

    public EntradaEstoque qtde(Integer qtde) {
        this.setQtde(qtde);
        return this;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Float getValorUnitario() {
        return this.valorUnitario;
    }

    public EntradaEstoque valorUnitario(Float valorUnitario) {
        this.setValorUnitario(valorUnitario);
        return this;
    }

    public void setValorUnitario(Float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getObs() {
        return this.obs;
    }

    public EntradaEstoque obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public EntradaEstoque produto(Produto produto) {
        this.setProduto(produto);
        return this;
    }

    public Set<FotoEntradaEstoque> getFotoEntradaEstoques() {
        return this.fotoEntradaEstoques;
    }

    public void setFotoEntradaEstoques(Set<FotoEntradaEstoque> fotoEntradaEstoques) {
        if (this.fotoEntradaEstoques != null) {
            this.fotoEntradaEstoques.forEach(i -> i.setEntradaEstoque(null));
        }
        if (fotoEntradaEstoques != null) {
            fotoEntradaEstoques.forEach(i -> i.setEntradaEstoque(this));
        }
        this.fotoEntradaEstoques = fotoEntradaEstoques;
    }

    public EntradaEstoque fotoEntradaEstoques(Set<FotoEntradaEstoque> fotoEntradaEstoques) {
        this.setFotoEntradaEstoques(fotoEntradaEstoques);
        return this;
    }

    public EntradaEstoque addFotoEntradaEstoque(FotoEntradaEstoque fotoEntradaEstoque) {
        this.fotoEntradaEstoques.add(fotoEntradaEstoque);
        fotoEntradaEstoque.setEntradaEstoque(this);
        return this;
    }

    public EntradaEstoque removeFotoEntradaEstoque(FotoEntradaEstoque fotoEntradaEstoque) {
        this.fotoEntradaEstoques.remove(fotoEntradaEstoque);
        fotoEntradaEstoque.setEntradaEstoque(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public EntradaEstoque dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntradaEstoque)) {
            return false;
        }
        return id != null && id.equals(((EntradaEstoque) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntradaEstoque{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", qtde=" + getQtde() +
            ", valorUnitario=" + getValorUnitario() +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
