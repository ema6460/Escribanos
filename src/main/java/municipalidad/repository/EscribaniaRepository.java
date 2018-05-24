package municipalidad.repository;

import municipalidad.domain.Escribania;
import municipalidad.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Escribania entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscribaniaRepository extends JpaRepository<Escribania, Long> {
    
    @Query("select p from Escribania p where p.usuario = :p_usuario")
    Escribania findByUsuario(@Param("p_usuario")User p_usuario);

}
