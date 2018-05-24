package municipalidad.service.impl;

import municipalidad.service.ArchivosService;
import municipalidad.domain.Archivos;
import municipalidad.repository.ArchivosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Archivos.
 */
@Service
@Transactional
public class ArchivosServiceImpl implements ArchivosService {

    private final Logger log = LoggerFactory.getLogger(ArchivosServiceImpl.class);

    private final ArchivosRepository archivosRepository;

    public ArchivosServiceImpl(ArchivosRepository archivosRepository) {
        this.archivosRepository = archivosRepository;
    }

    /**
     * Save a archivos.
     *
     * @param archivos the entity to save
     * @return the persisted entity
     */
    @Override
    public Archivos save(Archivos archivos) {
        log.debug("Request to save Archivos : {}", archivos);
        return archivosRepository.save(archivos);
    }

    /**
     * Get all the archivos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Archivos> findAll() {
        log.debug("Request to get all Archivos");
        return archivosRepository.findAll();
    }

    /**
     * Get one archivos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Archivos findOne(Long id) {
        log.debug("Request to get Archivos : {}", id);
        return archivosRepository.findOne(id);
    }

    /**
     * Delete the archivos by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Archivos : {}", id);
        archivosRepository.delete(id);
    }
}
