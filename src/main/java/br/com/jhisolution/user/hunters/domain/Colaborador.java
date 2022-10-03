package br.com.jhisolution.user.hunters.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Colaborador.
 */
@Entity
@Table(name = "colaborador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Colaborador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "data_recisao")
    private LocalDate dataRecisao;

    @Column(name = "salario", precision = 21, scale = 2)
    private BigDecimal salario;

    @Column(name = "ativo")
    private Boolean ativo;

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @OneToMany(mappedBy = "colaborador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "periodoDuracao", "colaborador" }, allowSetters = true)
    private Set<AgendaColaborador> agendaColaboradors = new HashSet<>();

    @OneToMany(mappedBy = "colaborador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "colaborador" }, allowSetters = true)
    private Set<TipoContratacao> tipoContratacaos = new HashSet<>();

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

    public Colaborador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public Colaborador dataCadastro(LocalDate dataCadastro) {
        this.setDataCadastro(dataCadastro);
        return this;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataAdmissao() {
        return this.dataAdmissao;
    }

    public Colaborador dataAdmissao(LocalDate dataAdmissao) {
        this.setDataAdmissao(dataAdmissao);
        return this;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataRecisao() {
        return this.dataRecisao;
    }

    public Colaborador dataRecisao(LocalDate dataRecisao) {
        this.setDataRecisao(dataRecisao);
        return this;
    }

    public void setDataRecisao(LocalDate dataRecisao) {
        this.dataRecisao = dataRecisao;
    }

    public BigDecimal getSalario() {
        return this.salario;
    }

    public Colaborador salario(BigDecimal salario) {
        this.setSalario(salario);
        return this;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public Colaborador ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getObs() {
        return this.obs;
    }

    public Colaborador obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Set<AgendaColaborador> getAgendaColaboradors() {
        return this.agendaColaboradors;
    }

    public void setAgendaColaboradors(Set<AgendaColaborador> agendaColaboradors) {
        if (this.agendaColaboradors != null) {
            this.agendaColaboradors.forEach(i -> i.setColaborador(null));
        }
        if (agendaColaboradors != null) {
            agendaColaboradors.forEach(i -> i.setColaborador(this));
        }
        this.agendaColaboradors = agendaColaboradors;
    }

    public Colaborador agendaColaboradors(Set<AgendaColaborador> agendaColaboradors) {
        this.setAgendaColaboradors(agendaColaboradors);
        return this;
    }

    public Colaborador addAgendaColaborador(AgendaColaborador agendaColaborador) {
        this.agendaColaboradors.add(agendaColaborador);
        agendaColaborador.setColaborador(this);
        return this;
    }

    public Colaborador removeAgendaColaborador(AgendaColaborador agendaColaborador) {
        this.agendaColaboradors.remove(agendaColaborador);
        agendaColaborador.setColaborador(null);
        return this;
    }

    public Set<TipoContratacao> getTipoContratacaos() {
        return this.tipoContratacaos;
    }

    public void setTipoContratacaos(Set<TipoContratacao> tipoContratacaos) {
        if (this.tipoContratacaos != null) {
            this.tipoContratacaos.forEach(i -> i.setColaborador(null));
        }
        if (tipoContratacaos != null) {
            tipoContratacaos.forEach(i -> i.setColaborador(this));
        }
        this.tipoContratacaos = tipoContratacaos;
    }

    public Colaborador tipoContratacaos(Set<TipoContratacao> tipoContratacaos) {
        this.setTipoContratacaos(tipoContratacaos);
        return this;
    }

    public Colaborador addTipoContratacao(TipoContratacao tipoContratacao) {
        this.tipoContratacaos.add(tipoContratacao);
        tipoContratacao.setColaborador(this);
        return this;
    }

    public Colaborador removeTipoContratacao(TipoContratacao tipoContratacao) {
        this.tipoContratacaos.remove(tipoContratacao);
        tipoContratacao.setColaborador(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Colaborador dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colaborador)) {
            return false;
        }
        return id != null && id.equals(((Colaborador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Colaborador{" +
            "id=" + getId() +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", dataAdmissao='" + getDataAdmissao() + "'" +
            ", dataRecisao='" + getDataRecisao() + "'" +
            ", salario=" + getSalario() +
            ", ativo='" + getAtivo() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
