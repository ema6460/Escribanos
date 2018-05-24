package municipalidad.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.domain.Escribania;
import municipalidad.service.EscribaniaService;
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
 * REST controller for managing Escribania.
 */
@RestController
@RequestMapping("/api")
public class EscribaniaResource {

    private final Logger log = LoggerFactory.getLogger(EscribaniaResource.class);

    private static final String ENTITY_NAME = "escribania";

    private final EscribaniaService escribaniaService;

    public EscribaniaResource(EscribaniaService escribaniaService) {
        this.escribaniaService = escribaniaService;
    }

    /**
     * POST  /escribanias : Create a new escribania.
     *
     * @param escribania the escribania to create
     * @return the ResponseEntity with status 201 (Created) and with body the new escribania, or with status 400 (Bad Request) if the escribania has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/escribanias")
    @Timed
    public ResponseEntity<Escribania> createEscribania(@RequestBody Escribania escribania) throws URISyntaxException {
        log.debug("REST request to save Escribania : {}", escribania);
        if (escribania.getId() != null) {
            throw new BadRequestAlertException("A new escribania cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Escribania result = escribaniaService.save(escribania);
        return ResponseEntity.created(new URI("/api/escribanias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /escribanias : Updates an existing escribania.
     *
     * @param escribania the escribania to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated escribania,
     * or with status 400 (Bad Request) if the escribania is not valid,
     * or with status 500 (Internal Server Error) if the escribania couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/escribanias")
    @Timed
    public ResponseEntity<Escribania> updateEscribania(@RequestBody Escribania escribania) throws URISyntaxException {
        log.debug("REST request to update Escribania : {}", escribania);
        if (escribania.getId() == null) {
            return createEscribania(escribania);
        }
        Escribania result = escribaniaService.save(escribania);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, escribania.getId().toString()))
            .body(result);
    }

    /**
     * GET  /escribanias : get all the escribanias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of escribanias in body
     */
    @GetMapping("/escribanias")
    @Timed
    public List<Escribania> getAllEscribanias() {
        log.debug("REST request to get all Escribanias");
        return escribaniaService.findAll();
        }

    /**
     * GET  /escribanias/:id : get the "id" escribania.
     *
     * @param id the id of the escribania to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the escribania, or with status 404 (Not Found)
     */
    @GetMapping("/escribanias/{id}")
    @Timed
    public ResponseEntity<Escribania> getEscribania(@PathVariable Long id) {
        log.debug("REST request to get Escribania : {}", id);
        Escribania escribania = escribaniaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(escribania));
    }

    /**
     * DELETE  /escribanias/:id : delete the "id" escribania.
     *
     * @param id the id of the escribania to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/escribanias/{id}")
    @Timed
    public ResponseEntity<Void> deleteEscribania(@PathVariable Long id) {
        log.debug("REST request to delete Escribania : {}", id);
        escribaniaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
