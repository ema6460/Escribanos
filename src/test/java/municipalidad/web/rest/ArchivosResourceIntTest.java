package municipalidad.web.rest;

import municipalidad.EscribanosApp;

import municipalidad.domain.Archivos;
import municipalidad.repository.ArchivosRepository;
import municipalidad.service.ArchivosService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static municipalidad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArchivosResource REST controller.
 *
 * @see ArchivosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscribanosApp.class)
public class ArchivosResourceIntTest {

    private static final byte[] DEFAULT_ARCHIVO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHIVO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ARCHIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHIVO_CONTENT_TYPE = "image/png";

    @Autowired
    private ArchivosRepository archivosRepository;

    @Autowired
    private ArchivosService archivosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArchivosMockMvc;

    private Archivos archivos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArchivosResource archivosResource = new ArchivosResource(archivosService);
        this.restArchivosMockMvc = MockMvcBuilders.standaloneSetup(archivosResource)
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
    public static Archivos createEntity(EntityManager em) {
        Archivos archivos = new Archivos()
            .archivo(DEFAULT_ARCHIVO)
            .archivoContentType(DEFAULT_ARCHIVO_CONTENT_TYPE);
        return archivos;
    }

    @Before
    public void initTest() {
        archivos = createEntity(em);
    }

    @Test
    @Transactional
    public void createArchivos() throws Exception {
        int databaseSizeBeforeCreate = archivosRepository.findAll().size();

        // Create the Archivos
        restArchivosMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivos)))
            .andExpect(status().isCreated());

        // Validate the Archivos in the database
        List<Archivos> archivosList = archivosRepository.findAll();
        assertThat(archivosList).hasSize(databaseSizeBeforeCreate + 1);
        Archivos testArchivos = archivosList.get(archivosList.size() - 1);
        assertThat(testArchivos.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testArchivos.getArchivoContentType()).isEqualTo(DEFAULT_ARCHIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createArchivosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = archivosRepository.findAll().size();

        // Create the Archivos with an existing ID
        archivos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchivosMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivos)))
            .andExpect(status().isBadRequest());

        // Validate the Archivos in the database
        List<Archivos> archivosList = archivosRepository.findAll();
        assertThat(archivosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArchivos() throws Exception {
        // Initialize the database
        archivosRepository.saveAndFlush(archivos);

        // Get all the archivosList
        restArchivosMockMvc.perform(get("/api/archivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivoContentType").value(hasItem(DEFAULT_ARCHIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHIVO))));
    }

    @Test
    @Transactional
    public void getArchivos() throws Exception {
        // Initialize the database
        archivosRepository.saveAndFlush(archivos);

        // Get the archivos
        restArchivosMockMvc.perform(get("/api/archivos/{id}", archivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(archivos.getId().intValue()))
            .andExpect(jsonPath("$.archivoContentType").value(DEFAULT_ARCHIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.archivo").value(Base64Utils.encodeToString(DEFAULT_ARCHIVO)));
    }

    @Test
    @Transactional
    public void getNonExistingArchivos() throws Exception {
        // Get the archivos
        restArchivosMockMvc.perform(get("/api/archivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArchivos() throws Exception {
        // Initialize the database
        archivosService.save(archivos);

        int databaseSizeBeforeUpdate = archivosRepository.findAll().size();

        // Update the archivos
        Archivos updatedArchivos = archivosRepository.findOne(archivos.getId());
        // Disconnect from session so that the updates on updatedArchivos are not directly saved in db
        em.detach(updatedArchivos);
        updatedArchivos
            .archivo(UPDATED_ARCHIVO)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE);

        restArchivosMockMvc.perform(put("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArchivos)))
            .andExpect(status().isOk());

        // Validate the Archivos in the database
        List<Archivos> archivosList = archivosRepository.findAll();
        assertThat(archivosList).hasSize(databaseSizeBeforeUpdate);
        Archivos testArchivos = archivosList.get(archivosList.size() - 1);
        assertThat(testArchivos.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testArchivos.getArchivoContentType()).isEqualTo(UPDATED_ARCHIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingArchivos() throws Exception {
        int databaseSizeBeforeUpdate = archivosRepository.findAll().size();

        // Create the Archivos

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArchivosMockMvc.perform(put("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivos)))
            .andExpect(status().isCreated());

        // Validate the Archivos in the database
        List<Archivos> archivosList = archivosRepository.findAll();
        assertThat(archivosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArchivos() throws Exception {
        // Initialize the database
        archivosService.save(archivos);

        int databaseSizeBeforeDelete = archivosRepository.findAll().size();

        // Get the archivos
        restArchivosMockMvc.perform(delete("/api/archivos/{id}", archivos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Archivos> archivosList = archivosRepository.findAll();
        assertThat(archivosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archivos.class);
        Archivos archivos1 = new Archivos();
        archivos1.setId(1L);
        Archivos archivos2 = new Archivos();
        archivos2.setId(archivos1.getId());
        assertThat(archivos1).isEqualTo(archivos2);
        archivos2.setId(2L);
        assertThat(archivos1).isNotEqualTo(archivos2);
        archivos1.setId(null);
        assertThat(archivos1).isNotEqualTo(archivos2);
    }
}
