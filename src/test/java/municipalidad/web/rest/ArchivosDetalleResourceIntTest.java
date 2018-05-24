package municipalidad.web.rest;

import municipalidad.EscribanosApp;

import municipalidad.domain.ArchivosDetalle;
import municipalidad.repository.ArchivosDetalleRepository;
import municipalidad.service.ArchivosDetalleService;
import municipalidad.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static municipalidad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import municipalidad.domain.enumeration.EstadoPresentacion;
/**
 * Test class for the ArchivosDetalleResource REST controller.
 *
 * @see ArchivosDetalleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscribanosApp.class)
public class ArchivosDetalleResourceIntTest {

    private static final EstadoPresentacion DEFAULT_ESTADO = EstadoPresentacion.ENTREGADO;
    private static final EstadoPresentacion UPDATED_ESTADO = EstadoPresentacion.REVISION;

    @Autowired
    private ArchivosDetalleRepository archivosDetalleRepository;

    @Autowired
    private ArchivosDetalleService archivosDetalleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArchivosDetalleMockMvc;

    private ArchivosDetalle archivosDetalle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArchivosDetalleResource archivosDetalleResource = new ArchivosDetalleResource(archivosDetalleService);
        this.restArchivosDetalleMockMvc = MockMvcBuilders.standaloneSetup(archivosDetalleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArchivosDetalle createEntity(EntityManager em) {
        ArchivosDetalle archivosDetalle = new ArchivosDetalle()
            .estado(DEFAULT_ESTADO);
        return archivosDetalle;
    }

    @Before
    public void initTest() {
        archivosDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createArchivosDetalle() throws Exception {
        int databaseSizeBeforeCreate = archivosDetalleRepository.findAll().size();

        // Create the ArchivosDetalle
        restArchivosDetalleMockMvc.perform(post("/api/archivos-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivosDetalle)))
            .andExpect(status().isCreated());

        // Validate the ArchivosDetalle in the database
        List<ArchivosDetalle> archivosDetalleList = archivosDetalleRepository.findAll();
        assertThat(archivosDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        ArchivosDetalle testArchivosDetalle = archivosDetalleList.get(archivosDetalleList.size() - 1);
        assertThat(testArchivosDetalle.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createArchivosDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = archivosDetalleRepository.findAll().size();

        // Create the ArchivosDetalle with an existing ID
        archivosDetalle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchivosDetalleMockMvc.perform(post("/api/archivos-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivosDetalle)))
            .andExpect(status().isBadRequest());

        // Validate the ArchivosDetalle in the database
        List<ArchivosDetalle> archivosDetalleList = archivosDetalleRepository.findAll();
        assertThat(archivosDetalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArchivosDetalles() throws Exception {
        // Initialize the database
        archivosDetalleRepository.saveAndFlush(archivosDetalle);

        // Get all the archivosDetalleList
        restArchivosDetalleMockMvc.perform(get("/api/archivos-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivosDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getArchivosDetalle() throws Exception {
        // Initialize the database
        archivosDetalleRepository.saveAndFlush(archivosDetalle);

        // Get the archivosDetalle
        restArchivosDetalleMockMvc.perform(get("/api/archivos-detalles/{id}", archivosDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(archivosDetalle.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArchivosDetalle() throws Exception {
        // Get the archivosDetalle
        restArchivosDetalleMockMvc.perform(get("/api/archivos-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArchivosDetalle() throws Exception {
        // Initialize the database
        archivosDetalleService.save(archivosDetalle);

        int databaseSizeBeforeUpdate = archivosDetalleRepository.findAll().size();

        // Update the archivosDetalle
        ArchivosDetalle updatedArchivosDetalle = archivosDetalleRepository.findOne(archivosDetalle.getId());
        // Disconnect from session so that the updates on updatedArchivosDetalle are not directly saved in db
        em.detach(updatedArchivosDetalle);
        updatedArchivosDetalle
            .estado(UPDATED_ESTADO);

        restArchivosDetalleMockMvc.perform(put("/api/archivos-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArchivosDetalle)))
            .andExpect(status().isOk());

        // Validate the ArchivosDetalle in the database
        List<ArchivosDetalle> archivosDetalleList = archivosDetalleRepository.findAll();
        assertThat(archivosDetalleList).hasSize(databaseSizeBeforeUpdate);
        ArchivosDetalle testArchivosDetalle = archivosDetalleList.get(archivosDetalleList.size() - 1);
        assertThat(testArchivosDetalle.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingArchivosDetalle() throws Exception {
        int databaseSizeBeforeUpdate = archivosDetalleRepository.findAll().size();

        // Create the ArchivosDetalle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArchivosDetalleMockMvc.perform(put("/api/archivos-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivosDetalle)))
            .andExpect(status().isCreated());

        // Validate the ArchivosDetalle in the database
        List<ArchivosDetalle> archivosDetalleList = archivosDetalleRepository.findAll();
        assertThat(archivosDetalleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArchivosDetalle() throws Exception {
        // Initialize the database
        archivosDetalleService.save(archivosDetalle);

        int databaseSizeBeforeDelete = archivosDetalleRepository.findAll().size();

        // Get the archivosDetalle
        restArchivosDetalleMockMvc.perform(delete("/api/archivos-detalles/{id}", archivosDetalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArchivosDetalle> archivosDetalleList = archivosDetalleRepository.findAll();
        assertThat(archivosDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArchivosDetalle.class);
        ArchivosDetalle archivosDetalle1 = new ArchivosDetalle();
        archivosDetalle1.setId(1L);
        ArchivosDetalle archivosDetalle2 = new ArchivosDetalle();
        archivosDetalle2.setId(archivosDetalle1.getId());
        assertThat(archivosDetalle1).isEqualTo(archivosDetalle2);
        archivosDetalle2.setId(2L);
        assertThat(archivosDetalle1).isNotEqualTo(archivosDetalle2);
        archivosDetalle1.setId(null);
        assertThat(archivosDetalle1).isNotEqualTo(archivosDetalle2);
    }
}
