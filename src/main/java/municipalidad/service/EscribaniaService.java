package municipalidad.service;

import municipalidad.domain.Escribania;
import java.util.List;

/**
 * Service Interface for managing Escribania.
 */
public interface EscribaniaService {

    /**
     * Save a escribania.
     *
     * @param escribania the entity to save
     * @return the persisted entity
     */
    Escribania save(Escribania escribania);

    /**
     * Get all the escribanias.
     *
     * @return the list of entities
     */
    List<Escribania> findAll();

    /**
     * Get the "id" escribania.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Escribania findOne(Long id);

    /**
     * Delete the "id" escribania.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
