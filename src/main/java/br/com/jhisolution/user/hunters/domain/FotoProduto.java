package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FotoProduto.
 */
@Entity
@Table(name = "foto_produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FotoProduto implements Serializable {

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
    @JsonIgnoreProperties(value = { "fotoProdutos" }, allowSetters = true)
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FotoProduto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getConteudo() {
        return this.conteudo;
    }

    public FotoProduto conteudo(byte[] conteudo) {
        this.setConteudo(conteudo);
        return this;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudoContentType() {
        return this.conteudoContentType;
    }

    public FotoProduto conteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
        return this;
    }

    public void setConteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public FotoProduto produto(Produto produto) {
        this.setProduto(produto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FotoProduto)) {
            return false;
        }
        return id != null && id.equals(((FotoProduto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FotoProduto{" +
            "id=" + getId() +
            ", conteudo='" + getConteudo() + "'" +
            ", conteudoContentType='" + getConteudoContentType() + "'" +
            "}";
    }
}
