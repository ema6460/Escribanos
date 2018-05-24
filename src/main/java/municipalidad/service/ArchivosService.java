package municipalidad.service;

import municipalidad.domain.Archivos;
import java.util.List;

/**
 * Service Interface for managing Archivos.
 */
public interface ArchivosService {

    /**
     * Save a archivos.
     *
     * @param archivos the entity to save
     * @return the persisted entity
     */
    Archivos save(Archivos archivos);

    /**
     * Get all the archivos.
     *
     * @return the list of entities
     */
    List<Archivos> findAll();

    /**
     * Get the "id" archivos.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Archivos findOne(Long id);

    /**
     * Delete the "id" archivos.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
