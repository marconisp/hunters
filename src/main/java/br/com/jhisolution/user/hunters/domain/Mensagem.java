package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mensagem.
 */
@Entity
@Table(name = "mensagem")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "titulo", length = 40, nullable = false)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "conteudo", length = 1000, nullable = false)
    private String conteudo;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private TipoMensagem tipo;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "estadoCivil", "raca", "religiao", "foto", "fotoAvatar", "fotoIcon", "mensagems", "avisos", "documentos", "enderecos", "user",
        },
        allowSetters = true
    )
    private DadosPessoais dadosPessoais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mensagem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Mensagem data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Mensagem titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public Mensagem conteudo(String conteudo) {
        this.setConteudo(conteudo);
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public TipoMensagem getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoMensagem tipoMensagem) {
        this.tipo = tipoMensagem;
    }

    public Mensagem tipo(TipoMensagem tipoMensagem) {
        this.setTipo(tipoMensagem);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Mensagem dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mensagem)) {
            return false;
        }
        return id != null && id.equals(((Mensagem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mensagem{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            "}";
    }
}
