package municipalidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Presentacion.
 */
@Entity
@Table(name = "presentacion")
public class Presentacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuit_escribano")
    private String cuitEscribano;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @OneToMany(mappedBy = "presentacion")
    @JsonIgnore
    private Set<ArchivosDetalle> archivosDetalles = new HashSet<>();

    @ManyToOne
    private Escribania escribania;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuitEscribano() {
        return cuitEscribano;
    }

    public Presentacion cuitEscribano(String cuitEscribano) {
        this.cuitEscribano = cuitEscribano;
        return this;
    }

    public void setCuitEscribano(String cuitEscribano) {
        this.cuitEscribano = cuitEscribano;
    }

    public String getNombre() {
        return nombre;
    }

    public Presentacion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Presentacion apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Set<ArchivosDetalle> getArchivosDetalles() {
        return archivosDetalles;
    }

    public Presentacion archivosDetalles(Set<ArchivosDetalle> archivosDetalles) {
        this.archivosDetalles = archivosDetalles;
        return this;
    }

    public Presentacion addArchivosDetalle(ArchivosDetalle archivosDetalle) {
        this.archivosDetalles.add(archivosDetalle);
        archivosDetalle.setPresentacion(this);
        return this;
    }

    public Presentacion removeArchivosDetalle(ArchivosDetalle archivosDetalle) {
        this.archivosDetalles.remove(archivosDetalle);
        archivosDetalle.setPresentacion(null);
        return this;
    }

    public void setArchivosDetalles(Set<ArchivosDetalle> archivosDetalles) {
        this.archivosDetalles = archivosDetalles;
    }

    public Escribania getEscribania() {
        return escribania;
    }

    public Presentacion escribania(Escribania escribania) {
        this.escribania = escribania;
        return this;
    }

    public void setEscribania(Escribania escribania) {
        this.escribania = escribania;
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
        Presentacion presentacion = (Presentacion) o;
        if (presentacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), presentacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Presentacion{" +
            "id=" + getId() +
            ", cuitEscribano='" + getCuitEscribano() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            "}";
    }
}
