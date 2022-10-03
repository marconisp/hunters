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
 * A ExameMedico.
 */
@Entity
@Table(name = "exame_medico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExameMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Size(max = 50)
    @Column(name = "nome_medico", length = 50)
    private String nomeMedico;

    @Size(max = 20)
    @Column(name = "crm_medico", length = 20)
    private String crmMedico;

    @Size(max = 200)
    @Column(name = "resumo", length = 200)
    private String resumo;

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @OneToMany(mappedBy = "exameMedico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "exameMedico" }, allowSetters = true)
    private Set<FotoExameMedico> fotoExameMedicos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "matriculas",
            "dadosMedicos",
            "avaliacaoEconomicas",
            "acompanhamentoAlunos",
            "colaboradors",
            "caracteristicasPsiquicas",
            "exameMedicos",
            "eventos",
            "pagars",
            "recebers",
            "entradaEstoques",
            "saidaEstoques",
        },
        allowSetters = true
    )
    private DadosPessoais dadosPessoais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ExameMedico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public ExameMedico data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNomeMedico() {
        return this.nomeMedico;
    }

    public ExameMedico nomeMedico(String nomeMedico) {
        this.setNomeMedico(nomeMedico);
        return this;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getCrmMedico() {
        return this.crmMedico;
    }

    public ExameMedico crmMedico(String crmMedico) {
        this.setCrmMedico(crmMedico);
        return this;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public String getResumo() {
        return this.resumo;
    }

    public ExameMedico resumo(String resumo) {
        this.setResumo(resumo);
        return this;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getObs() {
        return this.obs;
    }

    public ExameMedico obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Set<FotoExameMedico> getFotoExameMedicos() {
        return this.fotoExameMedicos;
    }

    public void setFotoExameMedicos(Set<FotoExameMedico> fotoExameMedicos) {
        if (this.fotoExameMedicos != null) {
            this.fotoExameMedicos.forEach(i -> i.setExameMedico(null));
        }
        if (fotoExameMedicos != null) {
            fotoExameMedicos.forEach(i -> i.setExameMedico(this));
        }
        this.fotoExameMedicos = fotoExameMedicos;
    }

    public ExameMedico fotoExameMedicos(Set<FotoExameMedico> fotoExameMedicos) {
        this.setFotoExameMedicos(fotoExameMedicos);
        return this;
    }

    public ExameMedico addFotoExameMedico(FotoExameMedico fotoExameMedico) {
        this.fotoExameMedicos.add(fotoExameMedico);
        fotoExameMedico.setExameMedico(this);
        return this;
    }

    public ExameMedico removeFotoExameMedico(FotoExameMedico fotoExameMedico) {
        this.fotoExameMedicos.remove(fotoExameMedico);
        fotoExameMedico.setExameMedico(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public ExameMedico dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExameMedico)) {
            return false;
        }
        return id != null && id.equals(((ExameMedico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExameMedico{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", nomeMedico='" + getNomeMedico() + "'" +
            ", crmMedico='" + getCrmMedico() + "'" +
            ", resumo='" + getResumo() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
