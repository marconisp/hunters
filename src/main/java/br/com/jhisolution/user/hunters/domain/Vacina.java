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
 * A Vacina.
 */
@Entity
@Table(name = "vacina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vacina implements Serializable {

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

    @Size(max = 5)
    @Column(name = "idade", length = 5)
    private String idade;

    @Size(max = 50)
    @Column(name = "obs", length = 50)
    private String obs;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vacina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Vacina nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return this.idade;
    }

    public Vacina idade(String idade) {
        this.setIdade(idade);
        return this;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getObs() {
        return this.obs;
    }

    public Vacina obs(String obs) {
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
        if (!(o instanceof Vacina)) {
            return false;
        }
        return id != null && id.equals(((Vacina) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vacina{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", idade='" + getIdade() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
