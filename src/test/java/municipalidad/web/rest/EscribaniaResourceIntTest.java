package municipalidad.web.rest;

import municipalidad.EscribanosApp;

import municipalidad.domain.Escribania;
import municipalidad.repository.EscribaniaRepository;
import municipalidad.service.EscribaniaService;
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

/**
 * Test class for the EscribaniaResource REST controller.
 *
 * @see EscribaniaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscribanosApp.class)
public class EscribaniaResourceIntTest {

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    @Autowired
    private EscribaniaRepository escribaniaRepository;

    @Autowired
    private EscribaniaService escribaniaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEscribaniaMockMvc;

    private Escribania escribania;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscribaniaResource escribaniaResource = new EscribaniaResource(escribaniaService);
        this.restEscribaniaMockMvc = MockMvcBuilders.standaloneSetup(escribaniaResource)
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
    public static Escribania createEntity(EntityManager em) {
        Escribania escribania = new Escribania()
            .domicilio(DEFAULT_DOMICILIO);
        return escribania;
    }

    @Before
    public void initTest() {
        escribania = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscribania() throws Exception {
        int databaseSizeBeforeCreate = escribaniaRepository.findAll().size();

        // Create the Escribania
        restEscribaniaMockMvc.perform(post("/api/escribanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escribania)))
            .andExpect(status().isCreated());

        // Validate the Escribania in the database
        List<Escribania> escribaniaList = escribaniaRepository.findAll();
        assertThat(escribaniaList).hasSize(databaseSizeBeforeCreate + 1);
        Escribania testEscribania = escribaniaList.get(escribaniaList.size() - 1);
        assertThat(testEscribania.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
    }

    @Test
    @Transactional
    public void createEscribaniaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escribaniaRepository.findAll().size();

        // Create the Escribania with an existing ID
        escribania.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscribaniaMockMvc.perform(post("/api/escribanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escribania)))
            .andExpect(status().isBadRequest());

        // Validate the Escribania in the database
        List<Escribania> escribaniaList = escribaniaRepository.findAll();
        assertThat(escribaniaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEscribanias() throws Exception {
        // Initialize the database
        escribaniaRepository.saveAndFlush(escribania);

        // Get all the escribaniaList
        restEscribaniaMockMvc.perform(get("/api/escribanias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escribania.getId().intValue())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())));
    }

    @Test
    @Transactional
    public void getEscribania() throws Exception {
        // Initialize the database
        escribaniaRepository.saveAndFlush(escribania);

        // Get the escribania
        restEscribaniaMockMvc.perform(get("/api/escribanias/{id}", escribania.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escribania.getId().intValue()))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEscribania() throws Exception {
        // Get the escribania
        restEscribaniaMockMvc.perform(get("/api/escribanias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscribania() throws Exception {
        // Initialize the database
        escribaniaService.save(escribania);

        int databaseSizeBeforeUpdate = escribaniaRepository.findAll().size();

        // Update the escribania
        Escribania updatedEscribania = escribaniaRepository.findOne(escribania.getId());
        // Disconnect from session so that the updates on updatedEscribania are not directly saved in db
        em.detach(updatedEscribania);
        updatedEscribania
            .domicilio(UPDATED_DOMICILIO);

        restEscribaniaMockMvc.perform(put("/api/escribanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEscribania)))
            .andExpect(status().isOk());

        // Validate the Escribania in the database
        List<Escribania> escribaniaList = escribaniaRepository.findAll();
        assertThat(escribaniaList).hasSize(databaseSizeBeforeUpdate);
        Escribania testEscribania = escribaniaList.get(escribaniaList.size() - 1);
        assertThat(testEscribania.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void updateNonExistingEscribania() throws Exception {
        int databaseSizeBeforeUpdate = escribaniaRepository.findAll().size();

        // Create the Escribania

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEscribaniaMockMvc.perform(put("/api/escribanias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escribania)))
            .andExpect(status().isCreated());

        // Validate the Escribania in the database
        List<Escribania> escribaniaList = escribaniaRepository.findAll();
        assertThat(escribaniaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEscribania() throws Exception {
        // Initialize the database
        escribaniaService.save(escribania);

        int databaseSizeBeforeDelete = escribaniaRepository.findAll().size();

        // Get the escribania
        restEscribaniaMockMvc.perform(delete("/api/escribanias/{id}", escribania.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Escribania> escribaniaList = escribaniaRepository.findAll();
        assertThat(escribaniaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escribania.class);
        Escribania escribania1 = new Escribania();
        escribania1.setId(1L);
        Escribania escribania2 = new Escribania();
        escribania2.setId(escribania1.getId());
        assertThat(escribania1).isEqualTo(escribania2);
        escribania2.setId(2L);
        assertThat(escribania1).isNotEqualTo(escribania2);
        escribania1.setId(null);
        assertThat(escribania1).isNotEqualTo(escribania2);
    }
}
