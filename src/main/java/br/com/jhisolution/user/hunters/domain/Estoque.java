package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estoque.
 */
@Entity
@Table(name = "estoque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Estoque implements Serializable {

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

    @NotNull
    @Column(name = "valor_total", nullable = false)
    private Float valorTotal;

    @JsonIgnoreProperties(value = { "fotoProdutos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Produto produto;

    @JsonIgnoreProperties(value = { "nomes" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private GrupoProduto grupoProduto;

    @JsonIgnoreProperties(value = { "grupoProduto" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SubGrupoProduto subGrupoProduto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estoque id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Estoque data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getQtde() {
        return this.qtde;
    }

    public Estoque qtde(Integer qtde) {
        this.setQtde(qtde);
        return this;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Float getValorUnitario() {
        return this.valorUnitario;
    }

    public Estoque valorUnitario(Float valorUnitario) {
        this.setValorUnitario(valorUnitario);
        return this;
    }

    public void setValorUnitario(Float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Float getValorTotal() {
        return this.valorTotal;
    }

    public Estoque valorTotal(Float valorTotal) {
        this.setValorTotal(valorTotal);
        return this;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Estoque produto(Produto produto) {
        this.setProduto(produto);
        return this;
    }

    public GrupoProduto getGrupoProduto() {
        return this.grupoProduto;
    }

    public void setGrupoProduto(GrupoProduto grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public Estoque grupoProduto(GrupoProduto grupoProduto) {
        this.setGrupoProduto(grupoProduto);
        return this;
    }

    public SubGrupoProduto getSubGrupoProduto() {
        return this.subGrupoProduto;
    }

    public void setSubGrupoProduto(SubGrupoProduto subGrupoProduto) {
        this.subGrupoProduto = subGrupoProduto;
    }

    public Estoque subGrupoProduto(SubGrupoProduto subGrupoProduto) {
        this.setSubGrupoProduto(subGrupoProduto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estoque)) {
            return false;
        }
        return id != null && id.equals(((Estoque) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estoque{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", qtde=" + getQtde() +
            ", valorUnitario=" + getValorUnitario() +
            ", valorTotal=" + getValorTotal() +
            "}";
    }
}
