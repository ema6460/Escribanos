package municipalidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import municipalidad.domain.enumeration.EstadoPresentacion;

/**
 * A ArchivosDetalle.
 */
@Entity
@Table(name = "archivos_detalle")
public class ArchivosDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPresentacion estado;

    @OneToMany(mappedBy = "archivosDetalle")
    @JsonIgnore
    private Set<Tramite> tramites = new HashSet<>();

    @ManyToOne
    private Presentacion presentacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoPresentacion getEstado() {
        return estado;
    }

    public ArchivosDetalle estado(EstadoPresentacion estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoPresentacion estado) {
        this.estado = estado;
    }

    public Set<Tramite> getTramites() {
        return tramites;
    }

    public ArchivosDetalle tramites(Set<Tramite> tramites) {
        this.tramites = tramites;
        return this;
    }

    public ArchivosDetalle addTramites(Tramite tramite) {
        this.tramites.add(tramite);
        tramite.setArchivosDetalle(this);
        return this;
    }

    public ArchivosDetalle removeTramites(Tramite tramite) {
        this.tramites.remove(tramite);
        tramite.setArchivosDetalle(null);
        return this;
    }

    public void setTramites(Set<Tramite> tramites) {
        this.tramites = tramites;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public ArchivosDetalle presentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
        return this;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
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
        ArchivosDetalle archivosDetalle = (ArchivosDetalle) o;
        if (archivosDetalle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), archivosDetalle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArchivosDetalle{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
