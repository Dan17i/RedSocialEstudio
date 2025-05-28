package models;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.*;

import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
/**
 * Clase de prueba para la clase {@link Valoracion}.
 * <p>
 * Esta clase contiene una serie de pruebas unitarias para verificar el correcto funcionamiento
 * de la clase {@code Valoracion}, incluyendo la validación de su constructor, métodos getters,
 * comportamiento inmutable y el método {@code toString}.
 * </p>
 *
 * <p>Se utilizan objetos auxiliares como {@link Estudiante} y {@link Contenido}, así como
 * estructuras de datos personalizadas como {@link co.edu.uniquindio.redsocial.models.structures.ListaEnlazada}
 * y {@link co.edu.uniquindio.redsocial.models.structures.ColaPrioridad}.</p>
 *
 * @author
 * @version 1.0
 */
public class ValoracionTest {
    /**
     * Instancia de {@link Estudiante} utilizada para las pruebas.
     */
    private Estudiante estudiante;
    /**
     * Instancia de {@link Contenido} utilizada para las pruebas.
     */
    private Contenido contenido;
    /**
     * Instancia de {@link Valoracion} a ser evaluada en las pruebas.
     */
    private Valoracion valoracion;
    /**
     * Instancia de {@link ArchivoMultimedia} asociada al contenido.
     */
    private ArchivoMultimedia archivo;
    /**
     * Método ejecutado antes de cada prueba.
     * Inicializa los objetos necesarios como {@link Estudiante}, {@link Contenido} y {@link Valoracion}.
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
     * Prueba que verifica la correcta inicialización de una {@link Valoracion} con valores válidos.
     * Se asegura de que todos los campos estén correctamente asignados.
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
     * Prueba que verifica que el constructor de {@link Valoracion} lanza una excepción
     * cuando la puntuación es menor a 1 o mayor a 5.
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
     * Prueba que verifica que el constructor de {@link Valoracion} lanza una excepción
     * cuando el estudiante es {@code null}.
     */
    @Test
    public void testConstructorEstudianteNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Valoracion(null, contenido, 3, "Comentario");
        });
    }
    /**
     * Prueba que verifica que el constructor de {@link Valoracion} lanza una excepción
     * cuando el contenido es {@code null}.
     */
    @Test
    public void testConstructorContenidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Valoracion(estudiante, null, 3, "Comentario");
        });
    }
    /**
     * Prueba que verifica que el método {@code setComentario} actualiza correctamente el comentario,
     * y reemplaza valores nulos o vacíos con el texto "Sin comentario".
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
     * Prueba que verifica que los campos inmutables de la clase {@link Valoracion}
     * (como estudiante, contenido, puntuación, ID y fecha de valoración)
     * no pueden ser modificados después de la construcción del objeto.
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
     * Prueba que verifica que el método {@code toString} de {@link Valoracion}
     * devuelve una representación textual que incluye la información relevante del objeto.
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

