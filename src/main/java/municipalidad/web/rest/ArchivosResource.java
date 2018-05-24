package municipalidad.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.domain.Archivos;
import municipalidad.service.ArchivosService;
import municipalidad.web.rest.errors.BadRequestAlertException;
import municipalidad.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Archivos.
 */
@RestController
@RequestMapping("/api")
public class ArchivosResource {

    private final Logger log = LoggerFactory.getLogger(ArchivosResource.class);

    private static final String ENTITY_NAME = "archivos";

    private final ArchivosService archivosService;

    public ArchivosResource(ArchivosService archivosService) {
        this.archivosService = archivosService;
    }

    /**
     * POST  /archivos : Create a new archivos.
     *
     * @param archivos the archivos to create
     * @return the ResponseEntity with status 201 (Created) and with body the new archivos, or with status 400 (Bad Request) if the archivos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/archivos")
    @Timed
    public ResponseEntity<Archivos> createArchivos(@RequestBody Archivos archivos) throws URISyntaxException {
        log.debug("REST request to save Archivos : {}", archivos);
        if (archivos.getId() != null) {
            throw new BadRequestAlertException("A new archivos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Archivos result = archivosService.save(archivos);
        return ResponseEntity.created(new URI("/api/archivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /archivos : Updates an existing archivos.
     *
     * @param archivos the archivos to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated archivos,
     * or with status 400 (Bad Request) if the archivos is not valid,
     * or with status 500 (Internal Server Error) if the archivos couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/archivos")
    @Timed
    public ResponseEntity<Archivos> updateArchivos(@RequestBody Archivos archivos) throws URISyntaxException {
        log.debug("REST request to update Archivos : {}", archivos);
        if (archivos.getId() == null) {
            return createArchivos(archivos);
        }
        Archivos result = archivosService.save(archivos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, archivos.getId().toString()))
            .body(result);
    }

    /**
     * GET  /archivos : get all the archivos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of archivos in body
     */
    @GetMapping("/archivos")
    @Timed
    public List<Archivos> getAllArchivos() {
        log.debug("REST request to get all Archivos");
        return archivosService.findAll();
        }

    /**
     * GET  /archivos/:id : get the "id" archivos.
     *
     * @param id the id of the archivos to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the archivos, or with status 404 (Not Found)
     */
    @GetMapping("/archivos/{id}")
    @Timed
    public ResponseEntity<Archivos> getArchivos(@PathVariable Long id) {
        log.debug("REST request to get Archivos : {}", id);
        Archivos archivos = archivosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(archivos));
    }

    /**
     * DELETE  /archivos/:id : delete the "id" archivos.
     *
     * @param id the id of the archivos to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/archivos/{id}")
    @Timed
    public ResponseEntity<Void> deleteArchivos(@PathVariable Long id) {
        log.debug("REST request to delete Archivos : {}", id);
        archivosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
