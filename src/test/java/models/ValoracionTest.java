package models;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.TipoContenido;
import co.edu.uniquindio.redsocial.models.*;

import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ValoracionTest {

    private Estudiante estudiante;
    private Contenido contenido;
    private Valoracion valoracion;
    private ArchivoMultimedia archivo;

    /**
     * Método ejecutado antes de cada prueba.
     * Inicializa objetos comunes necesarios para los tests.
     */

    @BeforeEach
    public void setUp() {
        // Crear listas vacías para inicializar parámetros que requieren listas
        ListaEnlazada<String> intereses = new ListaEnlazada<>();
        ListaEnlazada<Contenido> historialContenidos = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoracionesEstudiante = new ListaEnlazada<>();
        ColaPrioridad<SolicitudAyuda> solicitudesAyuda = new ColaPrioridad<>();
        ListaEnlazada<GrupoEstudio> gruposEstudio = new ListaEnlazada<>();
        ListaEnlazada<Mensaje> mensajes = new ListaEnlazada<>();

        // Crear el estudiante con todos sus parámetros
        estudiante = new Estudiante(
                "est123",
                "Juan Pérez",
                "juan.perez@example.com",
                "password123",
                intereses,
                historialContenidos,
                valoracionesEstudiante,
                solicitudesAyuda,
                gruposEstudio,
                mensajes
        );

        // Crear lista vacía de valoraciones para el contenido
        ListaEnlazada<Valoracion> valoracionesContenido = new ListaEnlazada<>();

        // Crear el contenido con todos sus parámetros
        contenido = new Contenido(
                "cont123",
                "Matemáticas",
                "Introducción a integrales",
                estudiante,               // autor
                TipoContenido.VIDEO,
                LocalDateTime.now(),      // fecha de creación actual
                valoracionesContenido,
                archivo
        );

        // Crear la valoración con estudiante, contenido, puntuación y comentario
        valoracion = new Valoracion(
                estudiante,
                contenido,
                4,
                "Buen contenido"
        );
    }


    /**
     * Verifica que el constructor crea correctamente una Valoracion con valores válidos.
     */
    @Test
    public void testConstructorValido() {
        assertEquals(estudiante, valoracion.getEstudiante());
        assertEquals(contenido, valoracion.getContenido());
        assertEquals(4, valoracion.getPuntuacion());
        assertEquals("Buen contenido", valoracion.getComentario());
        assertNotNull(valoracion.getFechaValoracion());
        assertNotNull(valoracion.getId());
    }

    /**
     * Verifica que el constructor lanza excepción si la puntuación está fuera del rango permitido.
     */
    @Test
    public void testConstructorPuntuacionInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Valoracion(estudiante, contenido, 0, "Muy malo");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Valoracion(estudiante, contenido, 6, "Excelente");
        });
    }

    /**
     * Verifica que el constructor lanza excepción si el estudiante es nulo.
     */
    @Test
    public void testConstructorEstudianteNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Valoracion(null, contenido, 3, "Comentario");
        });
    }

    /**
     * Verifica que el constructor lanza excepción si el contenido es nulo.
     */
    @Test
    public void testConstructorContenidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Valoracion(estudiante, null, 3, "Comentario");
        });
    }

    /**
     * Verifica que el método setComentario actualiza correctamente el comentario.
     */
    @Test
    public void testSetComentario() {
        valoracion.setComentario("Nuevo comentario");
        assertEquals("Nuevo comentario", valoracion.getComentario());

        valoracion.setComentario(null);
        assertEquals("Sin comentario", valoracion.getComentario());

        valoracion.setComentario("   ");
        assertEquals("Sin comentario", valoracion.getComentario());
    }

    /**
     * Verifica que los campos inmutables no cambian tras la creación.
     */
    @Test
    public void testCamposInmutables() {
        // No hay setters para estudiante, contenido, puntuacion, id y fechaValoracion,
        // por lo que verificamos que sus valores se mantienen.
        String idAntes = valoracion.getId();
        Estudiante estudianteAntes = valoracion.getEstudiante();
        Contenido contenidoAntes = valoracion.getContenido();
        int puntuacionAntes = valoracion.getPuntuacion();
        LocalDateTime fechaAntes = valoracion.getFechaValoracion();

        // No existe forma de modificar estos campos, sólo verificamos igualdad
        assertEquals(estudianteAntes, valoracion.getEstudiante());
        assertEquals(contenidoAntes, valoracion.getContenido());
        assertEquals(puntuacionAntes, valoracion.getPuntuacion());
        assertEquals(idAntes, valoracion.getId());
        assertEquals(fechaAntes, valoracion.getFechaValoracion());
    }

    /**
     * Verifica que el método toString contiene información relevante.
     */
    @Test
    public void testToString() {
        String texto = valoracion.toString();
        assertTrue(texto.contains(estudiante.getNombre()));
        assertTrue(texto.contains(contenido.getTema()));
        assertTrue(texto.contains("4"));
        assertTrue(texto.contains("Buen contenido"));
    }
}

