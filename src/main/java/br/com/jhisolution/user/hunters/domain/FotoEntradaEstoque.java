package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FotoEntradaEstoque.
 */
@Entity
@Table(name = "foto_entrada_estoque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FotoEntradaEstoque implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "produto", "fotoEntradaEstoques", "dadosPessoais1" }, allowSetters = true)
    private EntradaEstoque entradaEstoque;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FotoEntradaEstoque id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getConteudo() {
        return this.conteudo;
    }

    public FotoEntradaEstoque conteudo(byte[] conteudo) {
        this.setConteudo(conteudo);
        return this;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudoContentType() {
        return this.conteudoContentType;
    }

    public FotoEntradaEstoque conteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
        return this;
    }

    public void setConteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
    }

    public EntradaEstoque getEntradaEstoque() {
        return this.entradaEstoque;
    }

    public void setEntradaEstoque(EntradaEstoque entradaEstoque) {
        this.entradaEstoque = entradaEstoque;
    }

    public FotoEntradaEstoque entradaEstoque(EntradaEstoque entradaEstoque) {
        this.setEntradaEstoque(entradaEstoque);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FotoEntradaEstoque)) {
            return false;
        }
        return id != null && id.equals(((FotoEntradaEstoque) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FotoEntradaEstoque{" +
            "id=" + getId() +
            ", conteudo='" + getConteudo() + "'" +
            ", conteudoContentType='" + getConteudoContentType() + "'" +
            "}";
    }
}
