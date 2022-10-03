package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FotoExameMedico.
 */
@Entity
@Table(name = "foto_exame_medico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FotoExameMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "foto", nullable = false)
    private byte[] foto;

    @NotNull
    @Column(name = "foto_content_type", nullable = false)
    private String fotoContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fotoExameMedicos", "dadosPessoais1" }, allowSetters = true)
    private ExameMedico exameMedico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FotoExameMedico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public FotoExameMedico foto(byte[] foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return this.fotoContentType;
    }

    public FotoExameMedico fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public ExameMedico getExameMedico() {
        return this.exameMedico;
    }

    public void setExameMedico(ExameMedico exameMedico) {
        this.exameMedico = exameMedico;
    }

    public FotoExameMedico exameMedico(ExameMedico exameMedico) {
        this.setExameMedico(exameMedico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FotoExameMedico)) {
            return false;
        }
        return id != null && id.equals(((FotoExameMedico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FotoExameMedico{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            "}";
    }
}
