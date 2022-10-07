package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Documento.
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", length = 50, nullable = false)
    private String descricao;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoDocumento tipoDocumento;

    @OneToMany(mappedBy = "documento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documento" }, allowSetters = true)
    private Set<FotoDocumento> fotos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "tipoPessoa",
            "estadoCivil",
            "raca",
            "religiao",
            "foto",
            "fotoAvatar",
            "fotoIcon",
            "mensagems",
            "avisos",
            "documentos",
            "enderecos",
            "user",
        },
        allowSetters = true
    )
    private DadosPessoais dadosPessoais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Documento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Documento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Documento tipoDocumento(TipoDocumento tipoDocumento) {
        this.setTipoDocumento(tipoDocumento);
        return this;
    }

    public Set<FotoDocumento> getFotos() {
        return this.fotos;
    }

    public void setFotos(Set<FotoDocumento> fotoDocumentos) {
        if (this.fotos != null) {
            this.fotos.forEach(i -> i.setDocumento(null));
        }
        if (fotoDocumentos != null) {
            fotoDocumentos.forEach(i -> i.setDocumento(this));
        }
        this.fotos = fotoDocumentos;
    }

    public Documento fotos(Set<FotoDocumento> fotoDocumentos) {
        this.setFotos(fotoDocumentos);
        return this;
    }

    public Documento addFoto(FotoDocumento fotoDocumento) {
        this.fotos.add(fotoDocumento);
        fotoDocumento.setDocumento(this);
        return this;
    }

    public Documento removeFoto(FotoDocumento fotoDocumento) {
        this.fotos.remove(fotoDocumento);
        fotoDocumento.setDocumento(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Documento dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documento)) {
            return false;
        }
        return id != null && id.equals(((Documento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
