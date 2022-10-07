package br.com.jhisolution.user.hunters.domain;

import br.com.jhisolution.user.hunters.domain.enumeration.Coracao;
import br.com.jhisolution.user.hunters.domain.enumeration.Pressao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DadosMedico.
 */
@Entity
@Table(name = "dados_medico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DadosMedico implements Serializable {

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
    @Column(name = "peso", nullable = false)
    private Integer peso;

    @NotNull
    @Column(name = "altura", nullable = false)
    private Integer altura;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pressao", nullable = false)
    private Pressao pressao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "coracao", nullable = false)
    private Coracao coracao;

    @Size(max = 100)
    @Column(name = "medicacao", length = 100)
    private String medicacao;

    @Size(max = 50)
    @Column(name = "obs", length = 50)
    private String obs;

    @OneToOne
    @JoinColumn(unique = true)
    private Vacina vacina;

    @OneToOne
    @JoinColumn(unique = true)
    private Alergia alergia;

    @OneToOne
    @JoinColumn(unique = true)
    private Doenca doenca;

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

    public DadosMedico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public DadosMedico data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getPeso() {
        return this.peso;
    }

    public DadosMedico peso(Integer peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getAltura() {
        return this.altura;
    }

    public DadosMedico altura(Integer altura) {
        this.setAltura(altura);
        return this;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Pressao getPressao() {
        return this.pressao;
    }

    public DadosMedico pressao(Pressao pressao) {
        this.setPressao(pressao);
        return this;
    }

    public void setPressao(Pressao pressao) {
        this.pressao = pressao;
    }

    public Coracao getCoracao() {
        return this.coracao;
    }

    public DadosMedico coracao(Coracao coracao) {
        this.setCoracao(coracao);
        return this;
    }

    public void setCoracao(Coracao coracao) {
        this.coracao = coracao;
    }

    public String getMedicacao() {
        return this.medicacao;
    }

    public DadosMedico medicacao(String medicacao) {
        this.setMedicacao(medicacao);
        return this;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getObs() {
        return this.obs;
    }

    public DadosMedico obs(String obs) {
        this.setObs(obs);
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Vacina getVacina() {
        return this.vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public DadosMedico vacina(Vacina vacina) {
        this.setVacina(vacina);
        return this;
    }

    public Alergia getAlergia() {
        return this.alergia;
    }

    public void setAlergia(Alergia alergia) {
        this.alergia = alergia;
    }

    public DadosMedico alergia(Alergia alergia) {
        this.setAlergia(alergia);
        return this;
    }

    public Doenca getDoenca() {
        return this.doenca;
    }

    public void setDoenca(Doenca doenca) {
        this.doenca = doenca;
    }

    public DadosMedico doenca(Doenca doenca) {
        this.setDoenca(doenca);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public DadosMedico dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DadosMedico)) {
            return false;
        }
        return id != null && id.equals(((DadosMedico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DadosMedico{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", peso=" + getPeso() +
            ", altura=" + getAltura() +
            ", pressao='" + getPressao() + "'" +
            ", coracao='" + getCoracao() + "'" +
            ", medicacao='" + getMedicacao() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
