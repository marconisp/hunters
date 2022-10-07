package br.com.jhisolution.user.hunters.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Alergia.
 */
@Entity
@Table(name = "alergia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Alergia implements Serializable {

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

    @Size(max = 150)
    @Column(name = "sintoma", length = 150)
    private String sintoma;

    @Size(max = 150)
    @Column(name = "precaucoes", length = 150)
    private String precaucoes;

    @Size(max = 150)
    @Column(name = "socorro", length = 150)
    private String socorro;

    @Size(max = 50)
    @Column(name = "obs", length = 50)
    private String obs;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Alergia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Alergia nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSintoma() {
        return this.sintoma;
    }

    public Alergia sintoma(String sintoma) {
        this.setSintoma(sintoma);
        return this;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getPrecaucoes() {
        return this.precaucoes;
    }

    public Alergia precaucoes(String precaucoes) {
        this.setPrecaucoes(precaucoes);
        return this;
    }

    public void setPrecaucoes(String precaucoes) {
        this.precaucoes = precaucoes;
    }

    public String getSocorro() {
        return this.socorro;
    }

    public Alergia socorro(String socorro) {
        this.setSocorro(socorro);
        return this;
    }

    public void setSocorro(String socorro) {
        this.socorro = socorro;
    }

    public String getObs() {
        return this.obs;
    }

    public Alergia obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alergia)) {
            return false;
        }
        return id != null && id.equals(((Alergia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alergia{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sintoma='" + getSintoma() + "'" +
            ", precaucoes='" + getPrecaucoes() + "'" +
            ", socorro='" + getSocorro() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
