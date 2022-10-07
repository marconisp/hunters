package br.com.jhisolution.user.hunters.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PagarPara.
 */
@Entity
@Table(name = "pagar_para")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PagarPara implements Serializable {

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

    @Size(max = 50)
    @Column(name = "banco", length = 50)
    private String banco;

    @Size(max = 20)
    @Column(name = "agencia", length = 20)
    private String agencia;

    @Size(max = 20)
    @Column(name = "conta", length = 20)
    private String conta;

    @Size(max = 50)
    @Column(name = "pix", length = 50)
    private String pix;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PagarPara id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PagarPara nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public PagarPara descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getCnpj() {
        return this.cnpj;
    }

    public PagarPara cnpj(Boolean cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(Boolean cnpj) {
        this.cnpj = cnpj;
    }

    public String getDocumento() {
        return this.documento;
    }

    public PagarPara documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getBanco() {
        return this.banco;
    }

    public PagarPara banco(String banco) {
        this.setBanco(banco);
        return this;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return this.agencia;
    }

    public PagarPara agencia(String agencia) {
        this.setAgencia(agencia);
        return this;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return this.conta;
    }

    public PagarPara conta(String conta) {
        this.setConta(conta);
        return this;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getPix() {
        return this.pix;
    }

    public PagarPara pix(String pix) {
        this.setPix(pix);
        return this;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PagarPara)) {
            return false;
        }
        return id != null && id.equals(((PagarPara) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagarPara{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", banco='" + getBanco() + "'" +
            ", agencia='" + getAgencia() + "'" +
            ", conta='" + getConta() + "'" +
            ", pix='" + getPix() + "'" +
            "}";
    }
}
