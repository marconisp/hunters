package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;

    @Size(max = 20)
    @Column(name = "unidade", length = 20)
    private String unidade;

    @Size(max = 20)
    @Column(name = "peso", length = 20)
    private String peso;

    @OneToMany(mappedBy = "produto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produto" }, allowSetters = true)
    private Set<FotoProduto> fotoProdutos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Produto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Produto descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return this.unidade;
    }

    public Produto unidade(String unidade) {
        this.setUnidade(unidade);
        return this;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getPeso() {
        return this.peso;
    }

    public Produto peso(String peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Set<FotoProduto> getFotoProdutos() {
        return this.fotoProdutos;
    }

    public void setFotoProdutos(Set<FotoProduto> fotoProdutos) {
        if (this.fotoProdutos != null) {
            this.fotoProdutos.forEach(i -> i.setProduto(null));
        }
        if (fotoProdutos != null) {
            fotoProdutos.forEach(i -> i.setProduto(this));
        }
        this.fotoProdutos = fotoProdutos;
    }

    public Produto fotoProdutos(Set<FotoProduto> fotoProdutos) {
        this.setFotoProdutos(fotoProdutos);
        return this;
    }

    public Produto addFotoProduto(FotoProduto fotoProduto) {
        this.fotoProdutos.add(fotoProduto);
        fotoProduto.setProduto(this);
        return this;
    }

    public Produto removeFotoProduto(FotoProduto fotoProduto) {
        this.fotoProdutos.remove(fotoProduto);
        fotoProduto.setProduto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", unidade='" + getUnidade() + "'" +
            ", peso='" + getPeso() + "'" +
            "}";
    }
}
