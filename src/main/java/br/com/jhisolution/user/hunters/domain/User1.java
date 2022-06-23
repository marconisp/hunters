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
 * A User1.
 */
@Entity
@Table(name = "user_1")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "email", length = 60, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "estadoCivil", "raca", "religiao", "foto", "fotoAvatar", "fotoIcon", "mensagems", "avisos", "documentos", "enderecos", "user",
        },
        allowSetters = true
    )
    private Set<DadosPessoais> dadosPessoais = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public User1 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public User1 firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public User1 lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public User1 email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<DadosPessoais> getDadosPessoais() {
        return this.dadosPessoais;
    }

    public void setDadosPessoais(Set<DadosPessoais> dadosPessoais) {
        if (this.dadosPessoais != null) {
            this.dadosPessoais.forEach(i -> i.setUser(null));
        }
        if (dadosPessoais != null) {
            dadosPessoais.forEach(i -> i.setUser(this));
        }
        this.dadosPessoais = dadosPessoais;
    }

    public User1 dadosPessoais(Set<DadosPessoais> dadosPessoais) {
        this.setDadosPessoais(dadosPessoais);
        return this;
    }

    public User1 addDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais.add(dadosPessoais);
        dadosPessoais.setUser(this);
        return this;
    }

    public User1 removeDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais.remove(dadosPessoais);
        dadosPessoais.setUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User1)) {
            return false;
        }
        return id != null && id.equals(((User1) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User1{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
