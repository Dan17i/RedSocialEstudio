package models;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase {@link SolicitudAyuda}.
 * Se valida el comportamiento del constructor, setters, getters, el método compareTo,
 * y se prueban los casos de error mediante lanzamientos de excepciones esperadas.
 *
 * Esta clase usa JUnit 5 como framework de pruebas.
 */
public class SolicitudAyudaTest {

    private Estudiante estudiante;
    private SolicitudAyuda solicitud;
    /**
     * Método de configuración inicial que se ejecuta antes de cada prueba.
     * Crea un objeto {@link Estudiante} con listas vacías y una solicitud de ayuda
     * con datos válidos.
     */
    @BeforeEach
    public void setUp() {
        ListaEnlazada<String> intereses = new ListaEnlazada<>();
        ListaEnlazada<Contenido> historialContenidos = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoraciones = new ListaEnlazada<>();
        ColaPrioridad<SolicitudAyuda> solicitudesAyuda = new ColaPrioridad<>();
        ListaEnlazada<GrupoEstudio> gruposEstudio = new ListaEnlazada<>();
        ListaEnlazada<Mensaje> mensajes = new ListaEnlazada<>();

        estudiante = new Estudiante(
                "12345",
                "Juan Pérez",
                "juan.perez@email.com",
                "contraseñaSegura123",
                intereses,
                historialContenidos,
                valoraciones,
                solicitudesAyuda,
                gruposEstudio,
                mensajes
        );

        solicitud = new SolicitudAyuda("Matemáticas", 7, estudiante, "Necesito ayuda con integrales.", EstadoSolicitud.EN_PROGRESO);
    }
    /**
     * Prueba que el constructor lance una excepción si el tema es null o vacío.
     */
    @Test
    public void testConstructorTemaNuloOEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda(null, 5, estudiante, "Descripción válida", EstadoSolicitud.RESUELTA));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("", 5, estudiante, "Descripción válida", EstadoSolicitud.RESUELTA));
    }
    /**
     * Prueba que el constructor lance una excepción si el nivel de urgencia está fuera del rango permitido (1-10).
     */
    @Test
    public void testConstructorUrgenciaFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 0, estudiante, "Descripción válida", EstadoSolicitud.PENDIENTE));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 11, estudiante, "Descripción válida", EstadoSolicitud.PENDIENTE));
    }
    /**
     * Prueba que el constructor lance una excepción si el estudiante es null.
     */
    @Test
    public void testConstructorEstudianteNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, null, "Descripción válida", EstadoSolicitud.RESUELTA));
    }
    /**
     * Prueba que el constructor lance una excepción si la descripción es null o vacía.
     */
    @Test
    public void testConstructorDescripcionNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, estudiante, null, EstadoSolicitud.RESUELTA));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, estudiante, "", EstadoSolicitud.EN_PROGRESO));
    }
    /**
     * Verifica que los getters devuelvan los valores esperados para una solicitud inicializada correctamente.
     */
    @Test
    public void testGetters() {
        assertEquals("Matemáticas", solicitud.getTema());
        assertEquals(7, solicitud.getUrgencia());
        assertEquals(estudiante, solicitud.getEstudiante());
        assertEquals("Necesito ayuda con integrales.", solicitud.getDescripcion());
        assertNotNull(solicitud.getFechaSolicitud());
        assertEquals(EstadoSolicitud.EN_PROGRESO, solicitud.getEstado()); // Corregido
        assertNotNull(solicitud.getId());
    }
    /**
     * Verifica que el método setEstado funcione correctamente con estados válidos.
     */
    @Test
    public void testSetEstadoValido() {
        solicitud.setEstado(EstadoSolicitud.EN_PROGRESO);
        assertEquals(EstadoSolicitud.EN_PROGRESO, solicitud.getEstado());

        solicitud.setEstado(EstadoSolicitud.RESUELTA);
        assertEquals(EstadoSolicitud.RESUELTA, solicitud.getEstado());
    }
    /**
     * Prueba que setEstado lance una excepción si se intenta asignar un estado null.
     */
    @Test
    public void testSetEstadoNulo() {
        assertThrows(IllegalArgumentException.class, () -> solicitud.setEstado(null));
    }
    /**
     * Verifica el método compareTo para diferentes niveles de urgencia.
     */
    @Test
    public void testCompareTo() {
        SolicitudAyuda menosUrgente = new SolicitudAyuda("Física", 3, estudiante, "Consulta física.", EstadoSolicitud.PENDIENTE);
        SolicitudAyuda masUrgente = new SolicitudAyuda("Química", 9, estudiante, "Consulta química.", EstadoSolicitud.PENDIENTE);
        SolicitudAyuda solicitud = new SolicitudAyuda("Matemáticas", 7, estudiante, "Consulta matemáticas.", EstadoSolicitud.PENDIENTE);

        assertTrue(solicitud.compareTo(masUrgente) > 0, "Solicitud menos urgente debe ser menor que más urgente");
        assertTrue(masUrgente.compareTo(menosUrgente) < 0, "Solicitud más urgente debe ser mayor que menos urgente");
        assertEquals(0, solicitud.compareTo(solicitud), "Solicitud debe ser igual a sí misma");
    }
    /**
     * Verifica que el método toString incluya la información esperada.
     */
    @Test
    public void testToString() {
        String texto = solicitud.toString();
        assertTrue(texto.contains("Matemáticas"));
        assertTrue(texto.contains("7"));
        assertTrue(texto.contains("Juan Pérez"));
        assertTrue(texto.contains("EN_PROGRESO")); // Corregido
        assertTrue(texto.contains("Necesito ayuda con integrales."));
    }
    /**
     * Prueba los métodos equals y hashCode para validar su comportamiento.
     */
    @Test
    public void testEqualsYHashCode() {
        SolicitudAyuda otraSolicitud = new SolicitudAyuda("Tema2", 5, estudiante, "Otra descripción", EstadoSolicitud.EN_PROGRESO);

        assertNotEquals(solicitud, otraSolicitud);
        assertNotEquals(solicitud.hashCode(), otraSolicitud.hashCode());

        assertEquals(solicitud, solicitud);
        assertEquals(solicitud.hashCode(), solicitud.hashCode());
    }
}
