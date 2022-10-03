package br.com.jhisolution.user.hunters.domain;

import br.com.jhisolution.user.hunters.domain.enumeration.Ensino;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AcompanhamentoAluno.
 */
@Entity
@Table(name = "acompanhamento_aluno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AcompanhamentoAluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ensino", nullable = false)
    private Ensino ensino;

    @NotNull
    @Column(name = "bimestre", nullable = false)
    private Integer bimestre;

    @OneToMany(mappedBy = "acompanhamentoAluno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia", "acompanhamentoAluno" }, allowSetters = true)
    private Set<ItemMateria> itemMaterias = new HashSet<>();

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

    public AcompanhamentoAluno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return this.ano;
    }

    public AcompanhamentoAluno ano(Integer ano) {
        this.setAno(ano);
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Ensino getEnsino() {
        return this.ensino;
    }

    public AcompanhamentoAluno ensino(Ensino ensino) {
        this.setEnsino(ensino);
        return this;
    }

    public void setEnsino(Ensino ensino) {
        this.ensino = ensino;
    }

    public Integer getBimestre() {
        return this.bimestre;
    }

    public AcompanhamentoAluno bimestre(Integer bimestre) {
        this.setBimestre(bimestre);
        return this;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public Set<ItemMateria> getItemMaterias() {
        return this.itemMaterias;
    }

    public void setItemMaterias(Set<ItemMateria> itemMaterias) {
        if (this.itemMaterias != null) {
            this.itemMaterias.forEach(i -> i.setAcompanhamentoAluno(null));
        }
        if (itemMaterias != null) {
            itemMaterias.forEach(i -> i.setAcompanhamentoAluno(this));
        }
        this.itemMaterias = itemMaterias;
    }

    public AcompanhamentoAluno itemMaterias(Set<ItemMateria> itemMaterias) {
        this.setItemMaterias(itemMaterias);
        return this;
    }

    public AcompanhamentoAluno addItemMateria(ItemMateria itemMateria) {
        this.itemMaterias.add(itemMateria);
        itemMateria.setAcompanhamentoAluno(this);
        return this;
    }

    public AcompanhamentoAluno removeItemMateria(ItemMateria itemMateria) {
        this.itemMaterias.remove(itemMateria);
        itemMateria.setAcompanhamentoAluno(null);
        return this;
    }

    public DadosPessoais getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public AcompanhamentoAluno dadosPessoais(DadosPessoais dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcompanhamentoAluno)) {
            return false;
        }
        return id != null && id.equals(((AcompanhamentoAluno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcompanhamentoAluno{" +
            "id=" + getId() +
            ", ano=" + getAno() +
            ", ensino='" + getEnsino() + "'" +
            ", bimestre=" + getBimestre() +
            "}";
    }
}
