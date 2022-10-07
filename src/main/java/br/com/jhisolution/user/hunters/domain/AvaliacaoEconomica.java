package br.com.jhisolution.user.hunters.domain;

import br.com.jhisolution.user.hunters.domain.enumeration.AssitenciaMedica;
import br.com.jhisolution.user.hunters.domain.enumeration.Escola;
import br.com.jhisolution.user.hunters.domain.enumeration.FamiliaExiste;
import br.com.jhisolution.user.hunters.domain.enumeration.Moradia;
import br.com.jhisolution.user.hunters.domain.enumeration.Pais;
import br.com.jhisolution.user.hunters.domain.enumeration.SimNao;
import br.com.jhisolution.user.hunters.domain.enumeration.SituacaoMoradia;
import br.com.jhisolution.user.hunters.domain.enumeration.TipoMoradia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AvaliacaoEconomica.
 */
@Entity
@Table(name = "avaliacao_economica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvaliacaoEconomica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trabalho_ou_estagio", nullable = false)
    private SimNao trabalhoOuEstagio;

    @Enumerated(EnumType.STRING)
    @Column(name = "vinculo_empregaticio")
    private SimNao vinculoEmpregaticio;

    @Size(max = 50)
    @Column(name = "cargo_funcao", length = 50)
    private String cargoFuncao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contribui_renda_familiar", nullable = false)
    private SimNao contribuiRendaFamiliar;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "apoio_financeiro_familiar", nullable = false)
    private SimNao apoioFinanceiroFamiliar;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estuda_atualmente", nullable = false)
    private SimNao estudaAtualmente;

    @Enumerated(EnumType.STRING)
    @Column(name = "escola_atual")
    private Escola escolaAtual;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estudou_anteriormente", nullable = false)
    private SimNao estudouAnteriormente;

    @Enumerated(EnumType.STRING)
    @Column(name = "escola_anterior")
    private Escola escolaAnterior;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "conclui_ano_escolar_anterior", nullable = false)
    private SimNao concluiAnoEscolarAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "repetente")
    private SimNao repetente;

    @Enumerated(EnumType.STRING)
    @Column(name = "dificuldade_aprendizado")
    private SimNao dificuldadeAprendizado;

    @Size(max = 150)
    @Column(name = "dificuldade_disciplina", length = 150)
    private String dificuldadeDisciplina;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mora_com", nullable = false)
    private Moradia moraCom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pais", nullable = false)
    private Pais pais;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_moradia", nullable = false)
    private SituacaoMoradia situacaoMoradia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_moradia", nullable = false)
    private TipoMoradia tipoMoradia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recebe_beneficio_governo", nullable = false)
    private SimNao recebeBeneficioGoverno;

    @Size(max = 100)
    @Column(name = "tipo_beneficio", length = 100)
    private String tipoBeneficio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "familia_existe", nullable = false)
    private FamiliaExiste familiaExiste;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assitencia_medica", nullable = false)
    private AssitenciaMedica assitenciaMedica;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tem_alergia", nullable = false)
    private SimNao temAlergia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tem_problema_saude", nullable = false)
    private SimNao temProblemaSaude;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "toma_medicamento", nullable = false)
    private SimNao tomaMedicamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "teve_fratura", nullable = false)
    private SimNao teveFratura;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "teve_cirurgia", nullable = false)
    private SimNao teveCirurgia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tem_deficiencia", nullable = false)
    private SimNao temDeficiencia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tem_acompanhamento_medico", nullable = false)
    private SimNao temAcompanhamentoMedico;

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

    public AvaliacaoEconomica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimNao getTrabalhoOuEstagio() {
        return this.trabalhoOuEstagio;
    }

    public AvaliacaoEconomica trabalhoOuEstagio(SimNao trabalhoOuEstagio) {
        this.setTrabalhoOuEstagio(trabalhoOuEstagio);
        return this;
    }

    public void setTrabalhoOuEstagio(SimNao trabalhoOuEstagio) {
        this.trabalhoOuEstagio = trabalhoOuEstagio;
    }

    public SimNao getVinculoEmpregaticio() {
        return this.vinculoEmpregaticio;
    }

    public AvaliacaoEconomica vinculoEmpregaticio(SimNao vinculoEmpregaticio) {
        this.setVinculoEmpregaticio(vinculoEmpregaticio);
        return this;
    }

    public void setVinculoEmpregaticio(SimNao vinculoEmpregaticio) {
        this.vinculoEmpregaticio = vinculoEmpregaticio;
    }

    public String getCargoFuncao() {
        return this.cargoFuncao;
    }

    public AvaliacaoEconomica cargoFuncao(String cargoFuncao) {
        this.setCargoFuncao(cargoFuncao);
        return this;
    }

    public void setCargoFuncao(String cargoFuncao) {
        this.cargoFuncao = cargoFuncao;
    }

    public SimNao getContribuiRendaFamiliar() {
        return this.contribuiRendaFamiliar;
    }

    public AvaliacaoEconomica contribuiRendaFamiliar(SimNao contribuiRendaFamiliar) {
        this.setContribuiRendaFamiliar(contribuiRendaFamiliar);
        return this;
    }

    public void setContribuiRendaFamiliar(SimNao contribuiRendaFamiliar) {
        this.contribuiRendaFamiliar = contribuiRendaFamiliar;
    }

    public SimNao getApoioFinanceiroFamiliar() {
        return this.apoioFinanceiroFamiliar;
    }

    public AvaliacaoEconomica apoioFinanceiroFamiliar(SimNao apoioFinanceiroFamiliar) {
        this.setApoioFinanceiroFamiliar(apoioFinanceiroFamiliar);
        return this;
    }

    public void setApoioFinanceiroFamiliar(SimNao apoioFinanceiroFamiliar) {
        this.apoioFinanceiroFamiliar = apoioFinanceiroFamiliar;
    }

    public SimNao getEstudaAtualmente() {
        return this.estudaAtualmente;
    }

    public AvaliacaoEconomica estudaAtualmente(SimNao estudaAtualmente) {
        this.setEstudaAtualmente(estudaAtualmente);
        return this;
    }

    public void setEstudaAtualmente(SimNao estudaAtualmente) {
        this.estudaAtualmente = estudaAtualmente;
    }

    public Escola getEscolaAtual() {
        return this.escolaAtual;
    }

    public AvaliacaoEconomica escolaAtual(Escola escolaAtual) {
        this.setEscolaAtual(escolaAtual);
        return this;
    }

    public void setEscolaAtual(Escola escolaAtual) {
        this.escolaAtual = escolaAtual;
    }

    public SimNao getEstudouAnteriormente() {
        return this.estudouAnteriormente;
    }

    public AvaliacaoEconomica estudouAnteriormente(SimNao estudouAnteriormente) {
        this.setEstudouAnteriormente(estudouAnteriormente);
        return this;
    }

    public void setEstudouAnteriormente(SimNao estudouAnteriormente) {
        this.estudouAnteriormente = estudouAnteriormente;
    }

    public Escola getEscolaAnterior() {
        return this.escolaAnterior;
    }

    public AvaliacaoEconomica escolaAnterior(Escola escolaAnterior) {
        this.setEscolaAnterior(escolaAnterior);
        return this;
    }

    public void setEscolaAnterior(Escola escolaAnterior) {
        this.escolaAnterior = escolaAnterior;
    }

    public SimNao getConcluiAnoEscolarAnterior() {
        return this.concluiAnoEscolarAnterior;
    }

    public AvaliacaoEconomica concluiAnoEscolarAnterior(SimNao concluiAnoEscolarAnterior) {
        this.setConcluiAnoEscolarAnterior(concluiAnoEscolarAnterior);
        return this;
    }

    public void setConcluiAnoEscolarAnterior(SimNao concluiAnoEscolarAnterior) {
        this.concluiAnoEscolarAnterior = concluiAnoEscolarAnterior;
    }

    public SimNao getRepetente() {
        return this.repetente;
    }

    public AvaliacaoEconomica repetente(SimNao repetente) {
        this.setRepetente(repetente);
        return this;
    }

    public void setRepetente(SimNao repetente) {
        this.repetente = repetente;
    }

    public SimNao getDificuldadeAprendizado() {
        return this.dificuldadeAprendizado;
    }

    public AvaliacaoEconomica dificuldadeAprendizado(SimNao dificuldadeAprendizado) {
        this.setDificuldadeAprendizado(dificuldadeAprendizado);
        return this;
    }

    public void setDificuldadeAprendizado(SimNao dificuldadeAprendizado) {
        this.dificuldadeAprendizado = dificuldadeAprendizado;
    }

    public String getDificuldadeDisciplina() {
        return this.dificuldadeDisciplina;
    }

    public AvaliacaoEconomica dificuldadeDisciplina(String dificuldadeDisciplina) {
        this.setDificuldadeDisciplina(dificuldadeDisciplina);
        return this;
    }

    public void setDificuldadeDisciplina(String dificuldadeDisciplina) {
        this.dificuldadeDisciplina = dificuldadeDisciplina;
    }

    public Moradia getMoraCom() {
        return this.moraCom;
    }

    public AvaliacaoEconomica moraCom(Moradia moraCom) {
        this.setMoraCom(moraCom);
        return this;
    }

    public void setMoraCom(Moradia moraCom) {
        this.moraCom = moraCom;
    }

    public Pais getPais() {
        return this.pais;
    }

    public AvaliacaoEconomica pais(Pais pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public SituacaoMoradia getSituacaoMoradia() {
        return this.situacaoMoradia;
    }

    public AvaliacaoEconomica situacaoMoradia(SituacaoMoradia situacaoMoradia) {
        this.setSituacaoMoradia(situacaoMoradia);
        return this;
    }

    public void setSituacaoMoradia(SituacaoMoradia situacaoMoradia) {
        this.situacaoMoradia = situacaoMoradia;
    }

    public TipoMoradia getTipoMoradia() {
        return this.tipoMoradia;
    }

    public AvaliacaoEconomica tipoMoradia(TipoMoradia tipoMoradia) {
        this.setTipoMoradia(tipoMoradia);
        return this;
    }

    public void setTipoMoradia(TipoMoradia tipoMoradia) {
        this.tipoMoradia = tipoMoradia;
    }

    public SimNao getRecebeBeneficioGoverno() {
        return this.recebeBeneficioGoverno;
    }

    public AvaliacaoEconomica recebeBeneficioGoverno(SimNao recebeBeneficioGoverno) {
        this.setRecebeBeneficioGoverno(recebeBeneficioGoverno);
        return this;
    }

    public void setRecebeBeneficioGoverno(SimNao recebeBeneficioGoverno) {
        this.recebeBeneficioGoverno = recebeBeneficioGoverno;
    }

    public String getTipoBeneficio() {
        return this.tipoBeneficio;
    }

    public AvaliacaoEconomica tipoBeneficio(String tipoBeneficio) {
        this.setTipoBeneficio(tipoBeneficio);
        return this;
    }

    public void setTipoBeneficio(String tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public FamiliaExiste getFamiliaExiste() {
        return this.familiaExiste;
    }

    public AvaliacaoEconomica familiaExiste(FamiliaExiste familiaExiste) {
        this.setFamiliaExiste(familiaExiste);
        return this;
    }

    public void setFamiliaExiste(FamiliaExiste familiaExiste) {
        this.familiaExiste = familiaExiste;
    }

    public AssitenciaMedica getAssitenciaMedica() {
        return this.assitenciaMedica;
    }

    public AvaliacaoEconomica assitenciaMedica(AssitenciaMedica assitenciaMedica) {
        this.setAssitenciaMedica(assitenciaMedica);
        return this;
    }

    public void setAssitenciaMedica(AssitenciaMedica assitenciaMedica) {
        this.assitenciaMedica = assitenciaMedica;
    }

    public SimNao getTemAlergia() {
        return this.temAlergia;
    }

    public AvaliacaoEconomica temAlergia(SimNao temAlergia) {
        this.setTemAlergia(temAlergia);
        return this;
    }

    public void setTemAlergia(SimNao temAlergia) {
        this.temAlergia = temAlergia;
    }

    public SimNao getTemProblemaSaude() {
        return this.temProblemaSaude;
    }

    public AvaliacaoEconomica temProblemaSaude(SimNao temProblemaSaude) {
        this.setTemProblemaSaude(temProblemaSaude);
        return this;
    }

    public void setTemProblemaSaude(SimNao temProblemaSaude) {
        this.temProblemaSaude = temProblemaSaude;
    }

    public SimNao getTomaMedicamento() {
        return this.tomaMedicamento;
    }

    public AvaliacaoEconomica tomaMedicamento(SimNao tomaMedicamento) {
        this.setTomaMedicamento(tomaMedicamento);
        return this;
    }

    public void setTomaMedicamento(SimNao tomaMedicamento) {
        this.tomaMedicamento = tomaMedicamento;
    }

    public SimNao getTeveFratura() {
        return this.teveFratura;
    }

    public AvaliacaoEconomica teveFratura(SimNao teveFratura) {
        this.setTeveFratura(teveFratura);
        return this;
    }

    public void setTeveFratura(SimNao teveFratura) {
        this.teveFratura = teveFratura;
    }

    public SimNao getTeveCirurgia() {
        return this.teveCirurgia;
    }

    public AvaliacaoEconomica teveCirurgia(SimNao teveCirurgia) {
        this.setTeveCirurgia(teveCirurgia);
        return this;
    }

    public void setTeveCirurgia(SimNao teveCirurgia) {
        this.teveCirurgia = teveCirurgia;
    }

    public SimNao getTemDeficiencia() {
        return this.temDeficiencia;
    }

    public AvaliacaoEconomica temDeficiencia(SimNao temDeficiencia) {
        this.setTemDeficiencia(temDeficiencia);
        return this;
    }

    public void setTemDeficiencia(SimNao temDeficiencia) {
        this.temDeficiencia = temDeficiencia;
    }

    public SimNao getTemAcompanhamentoMedico() {
        return this.temAcompanhamentoMedico;
    }

    public AvaliacaoEconomica temAcompanhamentoMedico(SimNao temAcompanhamentoMedico) {
        this.setTemAcompanhamentoMedico(temAcompanhamentoMedico);
        return this;
    }

    public void setTemAcompanhamentoMedico(SimNao temAcompanhamentoMedico) {
        this.temAcompanhamentoMedico = temAcompanhamentoMedico;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public AvaliacaoEconomica dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvaliacaoEconomica)) {
            return false;
        }
        return id != null && id.equals(((AvaliacaoEconomica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvaliacaoEconomica{" +
            "id=" + getId() +
            ", trabalhoOuEstagio='" + getTrabalhoOuEstagio() + "'" +
            ", vinculoEmpregaticio='" + getVinculoEmpregaticio() + "'" +
            ", cargoFuncao='" + getCargoFuncao() + "'" +
            ", contribuiRendaFamiliar='" + getContribuiRendaFamiliar() + "'" +
            ", apoioFinanceiroFamiliar='" + getApoioFinanceiroFamiliar() + "'" +
            ", estudaAtualmente='" + getEstudaAtualmente() + "'" +
            ", escolaAtual='" + getEscolaAtual() + "'" +
            ", estudouAnteriormente='" + getEstudouAnteriormente() + "'" +
            ", escolaAnterior='" + getEscolaAnterior() + "'" +
            ", concluiAnoEscolarAnterior='" + getConcluiAnoEscolarAnterior() + "'" +
            ", repetente='" + getRepetente() + "'" +
            ", dificuldadeAprendizado='" + getDificuldadeAprendizado() + "'" +
            ", dificuldadeDisciplina='" + getDificuldadeDisciplina() + "'" +
            ", moraCom='" + getMoraCom() + "'" +
            ", pais='" + getPais() + "'" +
            ", situacaoMoradia='" + getSituacaoMoradia() + "'" +
            ", tipoMoradia='" + getTipoMoradia() + "'" +
            ", recebeBeneficioGoverno='" + getRecebeBeneficioGoverno() + "'" +
            ", tipoBeneficio='" + getTipoBeneficio() + "'" +
            ", familiaExiste='" + getFamiliaExiste() + "'" +
            ", assitenciaMedica='" + getAssitenciaMedica() + "'" +
            ", temAlergia='" + getTemAlergia() + "'" +
            ", temProblemaSaude='" + getTemProblemaSaude() + "'" +
            ", tomaMedicamento='" + getTomaMedicamento() + "'" +
            ", teveFratura='" + getTeveFratura() + "'" +
            ", teveCirurgia='" + getTeveCirurgia() + "'" +
            ", temDeficiencia='" + getTemDeficiencia() + "'" +
            ", temAcompanhamentoMedico='" + getTemAcompanhamentoMedico() + "'" +
            "}";
    }
}
