package br.com.jhisolution.user.hunters.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReceberDe.
 */
@Entity
@Table(name = "receber_de")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReceberDe implements Serializable {

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
    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "cnpj")
    private Boolean cnpj;

    @Size(max = 20)
    @Column(name = "documento", length = 20)
    private String documento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReceberDe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public ReceberDe nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ReceberDe descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getCnpj() {
        return this.cnpj;
    }

    public ReceberDe cnpj(Boolean cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(Boolean cnpj) {
        this.cnpj = cnpj;
    }

    public String getDocumento() {
        return this.documento;
    }

    public ReceberDe documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceberDe)) {
            return false;
        }
        return id != null && id.equals(((ReceberDe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceberDe{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", documento='" + getDocumento() + "'" +
            "}";
    }
}
