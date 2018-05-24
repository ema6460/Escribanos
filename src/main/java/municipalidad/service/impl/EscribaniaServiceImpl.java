package municipalidad.service.impl;

import municipalidad.service.EscribaniaService;
import municipalidad.domain.Escribania;
import municipalidad.repository.EscribaniaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Escribania.
 */
@Service
@Transactional
public class EscribaniaServiceImpl implements EscribaniaService {

    private final Logger log = LoggerFactory.getLogger(EscribaniaServiceImpl.class);

    private final EscribaniaRepository escribaniaRepository;

    public EscribaniaServiceImpl(EscribaniaRepository escribaniaRepository) {
        this.escribaniaRepository = escribaniaRepository;
    }

   

    /**
     * Save a escribania.
     *
     * @param escribania the entity to save
     * @return the persisted entity
     */
    @Override
    public Escribania save(Escribania escribania) {
        log.debug("Request to save Escribania : {}", escribania);
        return escribaniaRepository.save(escribania);
    }

    /**
     * Get all the escribanias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Escribania> findAll() {
        log.debug("Request to get all Escribanias");
        return escribaniaRepository.findAll();
    }

    /**
     * Get one escribania by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Escribania findOne(Long id) {
        log.debug("Request to get Escribania : {}", id);
        return escribaniaRepository.findOne(id);
    }

    /**
     * Delete the escribania by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Escribania : {}", id);
        escribaniaRepository.delete(id);
    }
}
