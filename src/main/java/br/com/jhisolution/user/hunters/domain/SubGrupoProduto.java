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
 * A SubGrupoProduto.
 */
@Entity
@Table(name = "sub_grupo_produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubGrupoProduto implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "nomes" }, allowSetters = true)
    private GrupoProduto grupoProduto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubGrupoProduto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public SubGrupoProduto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObs() {
        return this.obs;
    }

    public SubGrupoProduto obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public GrupoProduto getGrupoProduto() {
        return this.grupoProduto;
    }

    public void setGrupoProduto(GrupoProduto grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public SubGrupoProduto grupoProduto(GrupoProduto grupoProduto) {
        this.setGrupoProduto(grupoProduto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubGrupoProduto)) {
            return false;
        }
        return id != null && id.equals(((SubGrupoProduto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubGrupoProduto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
