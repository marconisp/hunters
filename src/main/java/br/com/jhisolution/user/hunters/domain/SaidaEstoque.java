package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SaidaEstoque.
 */
@Entity
@Table(name = "saida_estoque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SaidaEstoque implements Serializable {

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

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @JsonIgnoreProperties(value = { "fotoProdutos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Produto produto;

    @OneToMany(mappedBy = "saidaEstoque")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "saidaEstoque" }, allowSetters = true)
    private Set<FotoSaidaEstoque> fotoSaidaEstoques = new HashSet<>();

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

    public SaidaEstoque id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public SaidaEstoque data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getQtde() {
        return this.qtde;
    }

    public SaidaEstoque qtde(Integer qtde) {
        this.setQtde(qtde);
        return this;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Float getValorUnitario() {
        return this.valorUnitario;
    }

    public SaidaEstoque valorUnitario(Float valorUnitario) {
        this.setValorUnitario(valorUnitario);
        return this;
    }

    public void setValorUnitario(Float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getObs() {
        return this.obs;
    }

    public SaidaEstoque obs(String obs) {
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

    public SaidaEstoque produto(Produto produto) {
        this.setProduto(produto);
        return this;
    }

    public Set<FotoSaidaEstoque> getFotoSaidaEstoques() {
        return this.fotoSaidaEstoques;
    }

    public void setFotoSaidaEstoques(Set<FotoSaidaEstoque> fotoSaidaEstoques) {
        if (this.fotoSaidaEstoques != null) {
            this.fotoSaidaEstoques.forEach(i -> i.setSaidaEstoque(null));
        }
        if (fotoSaidaEstoques != null) {
            fotoSaidaEstoques.forEach(i -> i.setSaidaEstoque(this));
        }
        this.fotoSaidaEstoques = fotoSaidaEstoques;
    }

    public SaidaEstoque fotoSaidaEstoques(Set<FotoSaidaEstoque> fotoSaidaEstoques) {
        this.setFotoSaidaEstoques(fotoSaidaEstoques);
        return this;
    }

    public SaidaEstoque addFotoSaidaEstoque(FotoSaidaEstoque fotoSaidaEstoque) {
        this.fotoSaidaEstoques.add(fotoSaidaEstoque);
        fotoSaidaEstoque.setSaidaEstoque(this);
        return this;
    }

    public SaidaEstoque removeFotoSaidaEstoque(FotoSaidaEstoque fotoSaidaEstoque) {
        this.fotoSaidaEstoques.remove(fotoSaidaEstoque);
        fotoSaidaEstoque.setSaidaEstoque(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public SaidaEstoque dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaidaEstoque)) {
            return false;
        }
        return id != null && id.equals(((SaidaEstoque) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaidaEstoque{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", qtde=" + getQtde() +
            ", valorUnitario=" + getValorUnitario() +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
