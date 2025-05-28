package models;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la clase SolicitudAyuda.
 * Se prueban los constructores, getters, setters y métodos importantes.
 */
public class SolicitudAyudaTest {

    private Estudiante estudiante;
    private SolicitudAyuda solicitud;

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

    @Test
    public void testConstructorTemaNuloOEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda(null, 5, estudiante, "Descripción válida", EstadoSolicitud.RESUELTA));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("", 5, estudiante, "Descripción válida", EstadoSolicitud.RESUELTA));
    }

    @Test
    public void testConstructorUrgenciaFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 0, estudiante, "Descripción válida", EstadoSolicitud.PENDIENTE));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 11, estudiante, "Descripción válida", EstadoSolicitud.PENDIENTE));
    }

    @Test
    public void testConstructorEstudianteNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, null, "Descripción válida", EstadoSolicitud.RESUELTA));
    }

    @Test
    public void testConstructorDescripcionNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, estudiante, null, EstadoSolicitud.RESUELTA));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, estudiante, "", EstadoSolicitud.EN_PROGRESO));
    }

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

    @Test
    public void testSetEstadoValido() {
        solicitud.setEstado(EstadoSolicitud.EN_PROGRESO);
        assertEquals(EstadoSolicitud.EN_PROGRESO, solicitud.getEstado());

        solicitud.setEstado(EstadoSolicitud.RESUELTA);
        assertEquals(EstadoSolicitud.RESUELTA, solicitud.getEstado());
    }

    @Test
    public void testSetEstadoNulo() {
        assertThrows(IllegalArgumentException.class, () -> solicitud.setEstado(null));
    }

    @Test
    public void testCompareTo() {
        SolicitudAyuda menosUrgente = new SolicitudAyuda("Física", 3, estudiante, "Consulta física.", EstadoSolicitud.PENDIENTE);
        SolicitudAyuda masUrgente = new SolicitudAyuda("Química", 9, estudiante, "Consulta química.", EstadoSolicitud.PENDIENTE);
        SolicitudAyuda solicitud = new SolicitudAyuda("Matemáticas", 7, estudiante, "Consulta matemáticas.", EstadoSolicitud.PENDIENTE);

        assertTrue(solicitud.compareTo(masUrgente) > 0, "Solicitud menos urgente debe ser menor que más urgente");
        assertTrue(masUrgente.compareTo(menosUrgente) < 0, "Solicitud más urgente debe ser mayor que menos urgente");
        assertEquals(0, solicitud.compareTo(solicitud), "Solicitud debe ser igual a sí misma");
    }

    @Test
    public void testToString() {
        String texto = solicitud.toString();
        assertTrue(texto.contains("Matemáticas"));
        assertTrue(texto.contains("7"));
        assertTrue(texto.contains("Juan Pérez"));
        assertTrue(texto.contains("EN_PROGRESO")); // Corregido
        assertTrue(texto.contains("Necesito ayuda con integrales."));
    }

    @Test
    public void testEqualsYHashCode() {
        SolicitudAyuda otraSolicitud = new SolicitudAyuda("Tema2", 5, estudiante, "Otra descripción", EstadoSolicitud.EN_PROGRESO);

        assertNotEquals(solicitud, otraSolicitud);
        assertNotEquals(solicitud.hashCode(), otraSolicitud.hashCode());

        assertEquals(solicitud, solicitud);
        assertEquals(solicitud.hashCode(), solicitud.hashCode());
    }
}
