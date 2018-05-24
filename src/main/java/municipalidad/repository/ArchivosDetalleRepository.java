package municipalidad.repository;

import municipalidad.domain.ArchivosDetalle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArchivosDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchivosDetalleRepository extends JpaRepository<ArchivosDetalle, Long> {

}
