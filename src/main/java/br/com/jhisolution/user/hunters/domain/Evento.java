package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Evento implements Serializable {

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

    @Column(name = "ativo")
    private Boolean ativo;

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @JsonIgnoreProperties(value = { "diaSemanas" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PeriodoDuracao periodoDuracao;

    @OneToMany(mappedBy = "evento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "evento" }, allowSetters = true)
    private Set<EnderecoEvento> enderecos = new HashSet<>();

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

    public Evento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Evento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Evento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public Evento ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getObs() {
        return this.obs;
    }

    public Evento obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public PeriodoDuracao getPeriodoDuracao() {
        return this.periodoDuracao;
    }

    public void setPeriodoDuracao(PeriodoDuracao periodoDuracao) {
        this.periodoDuracao = periodoDuracao;
    }

    public Evento periodoDuracao(PeriodoDuracao periodoDuracao) {
        this.setPeriodoDuracao(periodoDuracao);
        return this;
    }

    public Set<EnderecoEvento> getEnderecos() {
        return this.enderecos;
    }

    public void setEnderecos(Set<EnderecoEvento> enderecoEventos) {
        if (this.enderecos != null) {
            this.enderecos.forEach(i -> i.setEvento(null));
        }
        if (enderecoEventos != null) {
            enderecoEventos.forEach(i -> i.setEvento(this));
        }
        this.enderecos = enderecoEventos;
    }

    public Evento enderecos(Set<EnderecoEvento> enderecoEventos) {
        this.setEnderecos(enderecoEventos);
        return this;
    }

    public Evento addEndereco(EnderecoEvento enderecoEvento) {
        this.enderecos.add(enderecoEvento);
        enderecoEvento.setEvento(this);
        return this;
    }

    public Evento removeEndereco(EnderecoEvento enderecoEvento) {
        this.enderecos.remove(enderecoEvento);
        enderecoEvento.setEvento(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Evento dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evento)) {
            return false;
        }
        return id != null && id.equals(((Evento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + getAtivo() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
