package municipalidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tramite.
 */
@Entity
@Table(name = "tramite")
public class Tramite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @Column(name = "fecha_fin")
    private ZonedDateTime fechaFin;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "tramite")
    @JsonIgnore
    private Set<Archivos> archivos = new HashSet<>();

    @ManyToOne
    private ArchivosDetalle archivosDetalle;

    @ManyToOne
    private Operador operador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Tramite fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public Tramite fechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Tramite observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Set<Archivos> getArchivos() {
        return archivos;
    }

    public Tramite archivos(Set<Archivos> archivos) {
        this.archivos = archivos;
        return this;
    }

    public Tramite addArchivos(Archivos archivos) {
        this.archivos.add(archivos);
        archivos.setTramite(this);
        return this;
    }

    public Tramite removeArchivos(Archivos archivos) {
        this.archivos.remove(archivos);
        archivos.setTramite(null);
        return this;
    }

    public void setArchivos(Set<Archivos> archivos) {
        this.archivos = archivos;
    }

    public ArchivosDetalle getArchivosDetalle() {
        return archivosDetalle;
    }

    public Tramite archivosDetalle(ArchivosDetalle archivosDetalle) {
        this.archivosDetalle = archivosDetalle;
        return this;
    }

    public void setArchivosDetalle(ArchivosDetalle archivosDetalle) {
        this.archivosDetalle = archivosDetalle;
    }

    public Operador getOperador() {
        return operador;
    }

    public Tramite operador(Operador operador) {
        this.operador = operador;
        return this;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
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
        Tramite tramite = (Tramite) o;
        if (tramite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tramite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tramite{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
