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
 * A PeriodoDuracao.
 */
@Entity
@Table(name = "periodo_duracao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PeriodoDuracao implements Serializable {

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

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Size(max = 5)
    @Column(name = "hora_inicio", length = 5)
    private String horaInicio;

    @Size(max = 5)
    @Column(name = "hora_fim", length = 5)
    private String horaFim;

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @OneToMany(mappedBy = "periodoDuracao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "periodoDuracao" }, allowSetters = true)
    private Set<DiaSemana> diaSemanas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PeriodoDuracao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PeriodoDuracao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public PeriodoDuracao dataInicio(LocalDate dataInicio) {
        this.setDataInicio(dataInicio);
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return this.dataFim;
    }

    public PeriodoDuracao dataFim(LocalDate dataFim) {
        this.setDataFim(dataFim);
        return this;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getHoraInicio() {
        return this.horaInicio;
    }

    public PeriodoDuracao horaInicio(String horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return this.horaFim;
    }

    public PeriodoDuracao horaFim(String horaFim) {
        this.setHoraFim(horaFim);
        return this;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getObs() {
        return this.obs;
    }

    public PeriodoDuracao obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Set<DiaSemana> getDiaSemanas() {
        return this.diaSemanas;
    }

    public void setDiaSemanas(Set<DiaSemana> diaSemanas) {
        if (this.diaSemanas != null) {
            this.diaSemanas.forEach(i -> i.setPeriodoDuracao(null));
        }
        if (diaSemanas != null) {
            diaSemanas.forEach(i -> i.setPeriodoDuracao(this));
        }
        this.diaSemanas = diaSemanas;
    }

    public PeriodoDuracao diaSemanas(Set<DiaSemana> diaSemanas) {
        this.setDiaSemanas(diaSemanas);
        return this;
    }

    public PeriodoDuracao addDiaSemana(DiaSemana diaSemana) {
        this.diaSemanas.add(diaSemana);
        diaSemana.setPeriodoDuracao(this);
        return this;
    }

    public PeriodoDuracao removeDiaSemana(DiaSemana diaSemana) {
        this.diaSemanas.remove(diaSemana);
        diaSemana.setPeriodoDuracao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodoDuracao)) {
            return false;
        }
        return id != null && id.equals(((PeriodoDuracao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoDuracao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
