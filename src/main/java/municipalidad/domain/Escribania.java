package municipalidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Escribania.
 */
@Entity
@Table(name = "escribania")
public class Escribania implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "domicilio")
    private String domicilio;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToMany(mappedBy = "escribania")
    @JsonIgnore
    private Set<Presentacion> presentacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Escribania domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public User getUsuario() {
        return usuario;
    }

    public Escribania usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<Presentacion> getPresentacions() {
        return presentacions;
    }

    public Escribania presentacions(Set<Presentacion> presentacions) {
        this.presentacions = presentacions;
        return this;
    }

    public Escribania addPresentacion(Presentacion presentacion) {
        this.presentacions.add(presentacion);
        presentacion.setEscribania(this);
        return this;
    }

    public Escribania removePresentacion(Presentacion presentacion) {
        this.presentacions.remove(presentacion);
        presentacion.setEscribania(null);
        return this;
    }

    public void setPresentacions(Set<Presentacion> presentacions) {
        this.presentacions = presentacions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Escribania escribania = (Escribania) o;
        if (escribania.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escribania.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Escribania{" +
            "id=" + getId() +
            ", domicilio='" + getDomicilio() + "'" +
            "}";
    }
}
