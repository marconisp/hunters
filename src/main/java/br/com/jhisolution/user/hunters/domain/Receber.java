package br.com.jhisolution.user.hunters.domain;

import br.com.jhisolution.user.hunters.domain.enumeration.StatusContaReceber;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Receber.
 */
@Entity
@Table(name = "receber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Receber implements Serializable {

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
    @Column(name = "valor", nullable = false)
    private Float valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusContaReceber status;

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @OneToOne
    private TipoReceber tipoReceber;

    @OneToOne
    private ReceberDe receberDe;

    @OneToOne
    private TipoTransacao tipoTransacao;

    @OneToMany(mappedBy = "receber")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "receber" }, allowSetters = true)
    private Set<FotoReceber> fotoRecebers = new HashSet<>();

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

    public Receber id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Receber data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getValor() {
        return this.valor;
    }

    public Receber valor(Float valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public StatusContaReceber getStatus() {
        return this.status;
    }

    public Receber status(StatusContaReceber status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusContaReceber status) {
        this.status = status;
    }

    public String getObs() {
        return this.obs;
    }

    public Receber obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public TipoReceber getTipoReceber() {
        return this.tipoReceber;
    }

    public void setTipoReceber(TipoReceber tipoReceber) {
        this.tipoReceber = tipoReceber;
    }

    public Receber tipoReceber(TipoReceber tipoReceber) {
        this.setTipoReceber(tipoReceber);
        return this;
    }

    public ReceberDe getReceberDe() {
        return this.receberDe;
    }

    public void setReceberDe(ReceberDe receberDe) {
        this.receberDe = receberDe;
    }

    public Receber receberDe(ReceberDe receberDe) {
        this.setReceberDe(receberDe);
        return this;
    }

    public TipoTransacao getTipoTransacao() {
        return this.tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Receber tipoTransacao(TipoTransacao tipoTransacao) {
        this.setTipoTransacao(tipoTransacao);
        return this;
    }

    public Set<FotoReceber> getFotoRecebers() {
        return this.fotoRecebers;
    }

    public void setFotoRecebers(Set<FotoReceber> fotoRecebers) {
        if (this.fotoRecebers != null) {
            this.fotoRecebers.forEach(i -> i.setReceber(null));
        }
        if (fotoRecebers != null) {
            fotoRecebers.forEach(i -> i.setReceber(this));
        }
        this.fotoRecebers = fotoRecebers;
    }

    public Receber fotoRecebers(Set<FotoReceber> fotoRecebers) {
        this.setFotoRecebers(fotoRecebers);
        return this;
    }

    public Receber addFotoReceber(FotoReceber fotoReceber) {
        this.fotoRecebers.add(fotoReceber);
        fotoReceber.setReceber(this);
        return this;
    }

    public Receber removeFotoReceber(FotoReceber fotoReceber) {
        this.fotoRecebers.remove(fotoReceber);
        fotoReceber.setReceber(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Receber dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receber)) {
            return false;
        }
        return id != null && id.equals(((Receber) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Receber{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            ", status='" + getStatus() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
