package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AgendaColaborador.
 */
@Entity
@Table(name = "agenda_colaborador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AgendaColaborador implements Serializable {

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
    @Column(name = "obs", length = 100)
    private String obs;

    @JsonIgnoreProperties(value = { "diaSemanas" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PeriodoDuracao periodoDuracao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "agendaColaboradors", "tipoContratacaos", "dadosPessoais1" }, allowSetters = true)
    private Colaborador colaborador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AgendaColaborador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public AgendaColaborador nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObs() {
        return this.obs;
    }

    public AgendaColaborador obs(String obs) {
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

    public AgendaColaborador periodoDuracao(PeriodoDuracao periodoDuracao) {
        this.setPeriodoDuracao(periodoDuracao);
        return this;
    }

    public Colaborador getColaborador() {
        return this.colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public AgendaColaborador colaborador(Colaborador colaborador) {
        this.setColaborador(colaborador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaColaborador)) {
            return false;
        }
        return id != null && id.equals(((AgendaColaborador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendaColaborador{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
