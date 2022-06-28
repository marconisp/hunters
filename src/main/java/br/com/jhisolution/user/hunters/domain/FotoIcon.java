package br.com.jhisolution.user.hunters.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FotoIcon.
 */
@Entity
@Table(name = "foto_icon")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FotoIcon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "conteudo", nullable = false)
    private byte[] conteudo;

    @NotNull
    @Column(name = "conteudo_content_type", nullable = false)
    private String conteudoContentType;

    public FotoIcon() {}

    public FotoIcon(Long id, byte[] conteudo, String conteudoContentType) {
        super();
        this.id = id;
        this.conteudo = conteudo;
        this.conteudoContentType = conteudoContentType;
    }

    public static FotoIcon getInstance() {
        return new FotoIcon();
    }

    public static FotoIcon getInstance(Long id, byte[] conteudo, String conteudoContentType) {
        return new FotoIcon(id, conteudo, conteudoContentType);
    }

    public static FotoIcon getInstance(byte[] conteudo, String conteudoContentType) {
        return new FotoIcon(null, conteudo, conteudoContentType);
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FotoIcon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getConteudo() {
        return this.conteudo;
    }

    public FotoIcon conteudo(byte[] conteudo) {
        this.setConteudo(conteudo);
        return this;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudoContentType() {
        return this.conteudoContentType;
    }

    public FotoIcon conteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
        return this;
    }

    public void setConteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FotoIcon)) {
            return false;
        }
        return id != null && id.equals(((FotoIcon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FotoIcon{" +
            "id=" + getId() +
            ", conteudo='" + getConteudo() + "'" +
            ", conteudoContentType='" + getConteudoContentType() + "'" +
            "}";
    }
}
