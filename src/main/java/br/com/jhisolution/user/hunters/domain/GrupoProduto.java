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
 * A GrupoProduto.
 */
@Entity
@Table(name = "grupo_produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GrupoProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    @Size(max = 50)
    @Column(name = "obs", length = 50)
    private String obs;

    @OneToMany(mappedBy = "grupoProduto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grupoProduto" }, allowSetters = true)
    private Set<SubGrupoProduto> nomes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoProduto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public GrupoProduto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObs() {
        return this.obs;
    }

    public GrupoProduto obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Set<SubGrupoProduto> getNomes() {
        return this.nomes;
    }

    public void setNomes(Set<SubGrupoProduto> subGrupoProdutos) {
        if (this.nomes != null) {
            this.nomes.forEach(i -> i.setGrupoProduto(null));
        }
        if (subGrupoProdutos != null) {
            subGrupoProdutos.forEach(i -> i.setGrupoProduto(this));
        }
        this.nomes = subGrupoProdutos;
    }

    public GrupoProduto nomes(Set<SubGrupoProduto> subGrupoProdutos) {
        this.setNomes(subGrupoProdutos);
        return this;
    }

    public GrupoProduto addNome(SubGrupoProduto subGrupoProduto) {
        this.nomes.add(subGrupoProduto);
        subGrupoProduto.setGrupoProduto(this);
        return this;
    }

    public GrupoProduto removeNome(SubGrupoProduto subGrupoProduto) {
        this.nomes.remove(subGrupoProduto);
        subGrupoProduto.setGrupoProduto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoProduto)) {
            return false;
        }
        return id != null && id.equals(((GrupoProduto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoProduto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
