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
 * A DiaSemana.
 */
@Entity
@Table(name = "dia_semana")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DiaSemana implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nome", length = 20, nullable = false)
    private String nome;

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @ManyToOne
    @JsonIgnoreProperties(value = { "diaSemanas" }, allowSetters = true)
    private PeriodoDuracao periodoDuracao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DiaSemana id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public DiaSemana nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObs() {
        return this.obs;
    }

    public DiaSemana obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public PeriodoDuracao getPeriodoDuracao() {
        return this.periodoDuracao;
    }

    public void setPeriodoDuracao(PeriodoDuracao periodoDuracao) {
        this.periodoDuracao = periodoDuracao;
    }

    public DiaSemana periodoDuracao(PeriodoDuracao periodoDuracao) {
        this.setPeriodoDuracao(periodoDuracao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiaSemana)) {
            return false;
        }
        return id != null && id.equals(((DiaSemana) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiaSemana{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
