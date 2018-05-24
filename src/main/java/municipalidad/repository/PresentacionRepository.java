package municipalidad.repository;

import municipalidad.domain.Presentacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Presentacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresentacionRepository extends JpaRepository<Presentacion, Long> {

}
