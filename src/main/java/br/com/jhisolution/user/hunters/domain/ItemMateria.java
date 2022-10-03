package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ItemMateria.
 */
@Entity
@Table(name = "item_materia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemMateria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nota", length = 20, nullable = false)
    private String nota;

    @Size(max = 50)
    @Column(name = "obs", length = 50)
    private String obs;

    @OneToOne
    @JoinColumn(unique = true)
    private Materia materia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "itemMaterias", "dadosPessoais1" }, allowSetters = true)
    private AcompanhamentoAluno acompanhamentoAluno;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemMateria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNota() {
        return this.nota;
    }

    public ItemMateria nota(String nota) {
        this.setNota(nota);
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getObs() {
        return this.obs;
    }

    public ItemMateria obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public ItemMateria materia(Materia materia) {
        this.setMateria(materia);
        return this;
    }

    public AcompanhamentoAluno getAcompanhamentoAluno() {
        return this.acompanhamentoAluno;
    }

    public void setAcompanhamentoAluno(AcompanhamentoAluno acompanhamentoAluno) {
        this.acompanhamentoAluno = acompanhamentoAluno;
    }

    public ItemMateria acompanhamentoAluno(AcompanhamentoAluno acompanhamentoAluno) {
        this.setAcompanhamentoAluno(acompanhamentoAluno);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemMateria)) {
            return false;
        }
        return id != null && id.equals(((ItemMateria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemMateria{" +
            "id=" + getId() +
            ", nota='" + getNota() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
