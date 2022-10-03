package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Turma.
 */
@Entity
@Table(name = "turma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Turma implements Serializable {

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
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @JsonIgnoreProperties(value = { "diaSemanas" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PeriodoDuracao periodoDuracao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Turma id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Turma nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAno() {
        return this.ano;
    }

    public Turma ano(Integer ano) {
        this.setAno(ano);
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public PeriodoDuracao getPeriodoDuracao() {
        return this.periodoDuracao;
    }

    public void setPeriodoDuracao(PeriodoDuracao periodoDuracao) {
        this.periodoDuracao = periodoDuracao;
    }

    public Turma periodoDuracao(PeriodoDuracao periodoDuracao) {
        this.setPeriodoDuracao(periodoDuracao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turma)) {
            return false;
        }
        return id != null && id.equals(((Turma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Turma{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ano=" + getAno() +
            "}";
    }
}
