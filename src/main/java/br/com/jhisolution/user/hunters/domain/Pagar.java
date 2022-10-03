package br.com.jhisolution.user.hunters.domain;

import br.com.jhisolution.user.hunters.domain.enumeration.StatusContaPagar;
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
 * A Pagar.
 */
@Entity
@Table(name = "pagar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pagar implements Serializable {

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
    private StatusContaPagar status;

    @Size(max = 100)
    @Column(name = "obs", length = 100)
    private String obs;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoPagar tipoPagar;

    @OneToOne
    @JoinColumn(unique = true)
    private PagarPara pagarPara;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoTransacao tipoTransacao;

    @OneToMany(mappedBy = "pagar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pagar" }, allowSetters = true)
    private Set<FotoPagar> fotoPagars = new HashSet<>();

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

    public Pagar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public Pagar data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getValor() {
        return this.valor;
    }

    public Pagar valor(Float valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public StatusContaPagar getStatus() {
        return this.status;
    }

    public Pagar status(StatusContaPagar status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusContaPagar status) {
        this.status = status;
    }

    public String getObs() {
        return this.obs;
    }

    public Pagar obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public TipoPagar getTipoPagar() {
        return this.tipoPagar;
    }

    public void setTipoPagar(TipoPagar tipoPagar) {
        this.tipoPagar = tipoPagar;
    }

    public Pagar tipoPagar(TipoPagar tipoPagar) {
        this.setTipoPagar(tipoPagar);
        return this;
    }

    public PagarPara getPagarPara() {
        return this.pagarPara;
    }

    public void setPagarPara(PagarPara pagarPara) {
        this.pagarPara = pagarPara;
    }

    public Pagar pagarPara(PagarPara pagarPara) {
        this.setPagarPara(pagarPara);
        return this;
    }

    public TipoTransacao getTipoTransacao() {
        return this.tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Pagar tipoTransacao(TipoTransacao tipoTransacao) {
        this.setTipoTransacao(tipoTransacao);
        return this;
    }

    public Set<FotoPagar> getFotoPagars() {
        return this.fotoPagars;
    }

    public void setFotoPagars(Set<FotoPagar> fotoPagars) {
        if (this.fotoPagars != null) {
            this.fotoPagars.forEach(i -> i.setPagar(null));
        }
        if (fotoPagars != null) {
            fotoPagars.forEach(i -> i.setPagar(this));
        }
        this.fotoPagars = fotoPagars;
    }

    public Pagar fotoPagars(Set<FotoPagar> fotoPagars) {
        this.setFotoPagars(fotoPagars);
        return this;
    }

    public Pagar addFotoPagar(FotoPagar fotoPagar) {
        this.fotoPagars.add(fotoPagar);
        fotoPagar.setPagar(this);
        return this;
    }

    public Pagar removeFotoPagar(FotoPagar fotoPagar) {
        this.fotoPagars.remove(fotoPagar);
        fotoPagar.setPagar(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Pagar dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagar)) {
            return false;
        }
        return id != null && id.equals(((Pagar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pagar{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            ", status='" + getStatus() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
