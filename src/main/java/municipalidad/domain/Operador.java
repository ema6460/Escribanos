package municipalidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Operador.
 */
@Entity
@Table(name = "operador")
public class Operador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToMany(mappedBy = "operador")
    @JsonIgnore
    private Set<Tramite> oficinas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Operador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public User getUsuario() {
        return usuario;
    }

    public Operador usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<Tramite> getOficinas() {
        return oficinas;
    }

    public Operador oficinas(Set<Tramite> tramites) {
        this.oficinas = tramites;
        return this;
    }

    public Operador addOficina(Tramite tramite) {
        this.oficinas.add(tramite);
        tramite.setOperador(this);
        return this;
    }

    public Operador removeOficina(Tramite tramite) {
        this.oficinas.remove(tramite);
        tramite.setOperador(null);
        return this;
    }

    public void setOficinas(Set<Tramite> tramites) {
        this.oficinas = tramites;
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
        Operador operador = (Operador) o;
        if (operador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operador{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
