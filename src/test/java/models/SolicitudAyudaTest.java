package models;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
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

    /**
     * Inicializa objetos comunes para las pruebas.
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

        solicitud = new SolicitudAyuda("Matemáticas", 7, estudiante, "Necesito ayuda con integrales.");
    }

    /**
     * Prueba que el constructor lanza IllegalArgumentException
     * cuando el tema es nulo o vacío.
     */
    @Test
    public void testConstructorTemaNuloOEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda(null, 5, estudiante, "Descripción válida"));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("", 5, estudiante, "Descripción válida"));
    }

    /**
     * Prueba que el constructor lanza IllegalArgumentException
     * cuando la urgencia está fuera del rango 1-10.
     */
    @Test
    public void testConstructorUrgenciaFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 0, estudiante, "Descripción válida"));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 11, estudiante, "Descripción válida"));
    }

    /**
     * Prueba que el constructor lanza IllegalArgumentException
     * cuando el estudiante es nulo.
     */
    @Test
    public void testConstructorEstudianteNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, null, "Descripción válida"));
    }

    /**
     * Prueba que el constructor lanza IllegalArgumentException
     * cuando la descripción es nula o vacía.
     */
    @Test
    public void testConstructorDescripcionNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, estudiante, null));
        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudAyuda("Tema", 5, estudiante, ""));
    }

    /**
     * Prueba que los getters devuelven los valores esperados
     * después de crear una solicitud válida.
     */
    @Test
    public void testGetters() {
        assertEquals("Matemáticas", solicitud.getTema());
        assertEquals(7, solicitud.getUrgencia());
        assertEquals(estudiante, solicitud.getEstudiante());
        assertEquals("Necesito ayuda con integrales.", solicitud.getDescripcion());
        assertNotNull(solicitud.getFechaSolicitud());
        assertEquals(SolicitudAyuda.EstadoSolicitud.PENDIENTE, solicitud.getEstado());
        assertNotNull(solicitud.getId());
    }

    /**
     * Prueba que el método setEstado actualiza correctamente
     * el estado cuando se le asigna un valor válido.
     */
    @Test
    public void testSetEstadoValido() {
        solicitud.setEstado(SolicitudAyuda.EstadoSolicitud.EN_PROGRESO);
        assertEquals(SolicitudAyuda.EstadoSolicitud.EN_PROGRESO, solicitud.getEstado());

        solicitud.setEstado(SolicitudAyuda.EstadoSolicitud.RESUELTA);
        assertEquals(SolicitudAyuda.EstadoSolicitud.RESUELTA, solicitud.getEstado());
    }

    /**
     * Prueba que el método setEstado lanza IllegalArgumentException
     * cuando se le asigna un valor nulo.
     */
    @Test
    public void testSetEstadoNulo() {
        assertThrows(IllegalArgumentException.class, () -> solicitud.setEstado(null));
    }

    /**
     * Prueba que el método compareTo ordena correctamente
     * las solicitudes por nivel de urgencia de forma descendente.
     */
    @Test
    public void testCompareTo() {
        SolicitudAyuda menosUrgente = new SolicitudAyuda("Física", 3, estudiante, "Consulta física.");
        SolicitudAyuda masUrgente = new SolicitudAyuda("Química", 9, estudiante, "Consulta química.");

        assertTrue(solicitud.compareTo(masUrgente) > 0, "Solicitud menos urgente debe ser menor que más urgente");
        assertTrue(masUrgente.compareTo(menosUrgente) < 0, "Solicitud más urgente debe ser mayor que menos urgente");
        assertEquals(0, solicitud.compareTo(solicitud), "Solicitud debe ser igual a sí misma");
    }

    /**
     * Prueba el método toString para verificar que
     * incluya información relevante en la cadena.
     */
    @Test
    public void testToString() {
        String texto = solicitud.toString();
        assertTrue(texto.contains("Matemáticas"));
        assertTrue(texto.contains("7"));
        assertTrue(texto.contains("Juan Pérez"));
        assertTrue(texto.contains("PENDIENTE"));
        assertTrue(texto.contains("Necesito ayuda con integrales."));
    }

    /**
     * Prueba que dos solicitudes con mismo ID son iguales,
     * y que solicitudes diferentes no son iguales.
     */
    @Test
    public void testEqualsYHashCode() {
        SolicitudAyuda otraSolicitud = new SolicitudAyuda("Tema2", 5, estudiante, "Otra descripción");

        // Mismo ID simulado (usando reflexión o constructor modificado en test real)
        // Aquí se usa que son distintas porque se generan IDs distintos.
        assertNotEquals(solicitud, otraSolicitud);
        assertNotEquals(solicitud.hashCode(), otraSolicitud.hashCode());

        // Comparar solicitud consigo misma
        assertEquals(solicitud, solicitud);
        assertEquals(solicitud.hashCode(), solicitud.hashCode());
    }
}

