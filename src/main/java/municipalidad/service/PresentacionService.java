package municipalidad.service;

import municipalidad.domain.Presentacion;
import java.util.List;
import municipalidad.service.dto.PresentacionDTO;

/**
 * Service Interface for managing Presentacion.
 */
public interface PresentacionService {

    /**
     * Save a presentacion.
     *
     * @param presentacion the entity to save
     * @return the persisted entity
     */
    Presentacion save(Presentacion presentacion);

    /**
     * Get all the presentacions.
     *
     * @return the list of entities
     */
    List<PresentacionDTO> findAll();

    /**
     * Get the "id" presentacion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Presentacion findOne(Long id);

    /**
     * Delete the "id" presentacion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    
    Presentacion saveDTO(PresentacionDTO presentacion);
}
