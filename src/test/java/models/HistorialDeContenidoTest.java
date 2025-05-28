package models;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.HistorialDeContenido;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * Clase de prueba unitaria para la clase {@link HistorialDeContenido}.
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos de la clase
 * {@code HistorialDeContenido}, incluyendo operaciones de adición, filtrado,
 * inversión y clonación del historial de contenidos accedidos por un estudiante.
 * </p>
 * <p>
 * Se utiliza JUnit 5 para realizar las pruebas automatizadas.
 * </p>
 *
 * @author Tú
 * @version 1.0
 * @since 2025-05-20
 */

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba unitaria para la clase {@link HistorialDeContenido}.
 * Verifica el correcto funcionamiento de los métodos relacionados con el historial de acceso a contenidos.
 *
 * @author Tú
 * @since 2025-05-20
 */
public class HistorialDeContenidoTest {
    /**
     * Instancia del historial a ser utilizada en las pruebas.
     */
    private HistorialDeContenido historial;
    /**
     * Contenido de ejemplo para pruebas.
     */
    private Contenido contenido1;
    /**
     * Segundo contenido de ejemplo para pruebas.
     */
    private Contenido contenido2;
    /**
     * Tercer contenido de ejemplo para pruebas.
     */
    private Contenido contenido3;
    /**
     * Fecha y hora usada como referencia de acceso.
     */
    private LocalDateTime ahora;
    /**
     * Estudiante que actúa como autor de los contenidos.
     */
    private Estudiante autor;
    /**
     * Archivo multimedia asociado a los contenidos.
     */
    private ArchivoMultimedia archivo;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        autor = new Estudiante("e1", "Juan", "juan@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(), new ListaEnlazada<>());

        // Supongamos que 'autor' ya está definido antes
        contenido1 = new Contenido(
                "c1",
                "Integrales",
                "Descripción sobre integrales", // descripción, puede ser breve o vacía si quieres
                autor,
                TipoContenido.TEXTO,
                LocalDateTime.now(),  // fecha actual para creación
                new ListaEnlazada<>(),
                archivo
        );

        contenido2 = new Contenido(
                "c2",
                "Derivadas",
                "Descripción sobre derivadas",
                autor,
                TipoContenido.TEXTO,
                LocalDateTime.now(),
                new ListaEnlazada<>(),
                archivo
        );

        contenido3 = new Contenido(
                "c3",
                "Límites",
                "Ejercicios de límites",
                autor,
                TipoContenido.TEXTO,
                LocalDateTime.now(),
                new ListaEnlazada<>(),
                archivo
        );



        ahora = LocalDateTime.now();

        historial = new HistorialDeContenido("e1", new ListaEnlazada<>(), new ListaEnlazada<>());
    }
    /**
     * Verifica que el método {@link HistorialDeContenido#agregarContenido(Contenido, LocalDateTime)}
     * agregue correctamente un contenido al historial junto con su fecha de acceso.
     */
    @Test
    public void testAgregarContenido() {
        historial.agregarContenido(contenido1, ahora);
        assertEquals(1, historial.getContenidos().getTamanio());
        assertEquals(contenido1, historial.getContenidos().obtener(0));
        assertEquals(ahora, historial.getFechasAcceso().obtener(0));
    }
    /**
     * Verifica que el método {@link HistorialDeContenido#filtrarPorTema(String)}
     * retorne una lista de contenidos que coincidan con el tema especificado.
     */
    @Test
    public void testFiltrarPorTema() {
        historial.agregarContenido(contenido1, ahora);
        historial.agregarContenido(contenido2, ahora);
        ListaEnlazada<Contenido> filtrados = historial.filtrarPorTema("Integrales");
        assertEquals(1, filtrados.getTamanio());
        assertEquals("Integrales", filtrados.obtener(0).getTema());
    }
    /**
     * Verifica que el método {@link HistorialDeContenido#obtenerUltimos(int)}
     * retorne los últimos N contenidos accedidos en orden correcto.
     */
    @Test
    public void testObtenerUltimos() {
        historial.agregarContenido(contenido1, ahora);
        historial.agregarContenido(contenido2, ahora);
        historial.agregarContenido(contenido3, ahora);
        ListaEnlazada<Contenido> ultimos = historial.obtenerUltimos(2);
        assertEquals(2, ultimos.getTamanio());
        assertEquals(contenido2, ultimos.obtener(0));
        assertEquals(contenido3, ultimos.obtener(1));
    }

    /**
     * Verifica que el método {@link HistorialDeContenido#obtenerUltimos(int)}
     * retorne los últimos N contenidos accedidos en orden correcto.
     */
    @Test
    public void testInvertirHistorial() {
        historial.agregarContenido(contenido1, ahora.minusDays(2));
        historial.agregarContenido(contenido2, ahora.minusDays(1));
        historial.invertirHistorial();
        assertEquals(contenido2, historial.getContenidos().obtener(0));
        assertEquals(contenido1, historial.getContenidos().obtener(1));
    }
    /**
     * Verifica que el método {@link HistorialDeContenido#clonarHistorial()}
     * realice una copia independiente del historial actual.
     */
    @Test
    public void testClonarHistorial() {
        historial.agregarContenido(contenido1, ahora);
        HistorialDeContenido clon = historial.clonarHistorial();
        assertNotSame(historial, clon);
        assertEquals(historial.getContenidos().getTamanio(), clon.getContenidos().getTamanio());
        assertEquals(historial.getFechasAcceso().obtener(0), clon.getFechasAcceso().obtener(0));
    }
    /**
     * Verifica que el método {@link HistorialDeContenido#obtenerContenidosRecientes(int)}
     * retorne una sublista con los últimos contenidos hasta un número máximo especificado.
     */
    @Test
    public void testObtenerContenidosRecientes() {

        // Crear lista de contenidos
        ListaEnlazada<Contenido> contenidos = new ListaEnlazada<>();
        Contenido contenido1 = new Contenido("C1", "Tema 1", "Descripción X", autor, TipoContenido.TEXTO,
                LocalDateTime.now(), new ListaEnlazada<>(),archivo);

        Contenido contenido2 = new Contenido("C2", "Tema 2", "Descripción X", autor, TipoContenido.TEXTO,
                LocalDateTime.now(), new ListaEnlazada<>(),archivo);

        Contenido contenido3 = new Contenido("C3", "Tema 3", "Descripción X", autor, TipoContenido.TEXTO,
                LocalDateTime.now(), new ListaEnlazada<>(), archivo);

        Contenido contenido4 = new Contenido("C4", "Tema 4", "Descripción X", autor, TipoContenido.TEXTO,
                LocalDateTime.now(), new ListaEnlazada<>(),archivo);


        contenidos.agregar(contenido1);
        contenidos.agregar(contenido2);
        contenidos.agregar(contenido3);
        contenidos.agregar(contenido4);

        // Crear lista de fechas de acceso
        ListaEnlazada<LocalDateTime> fechas = new ListaEnlazada<>();
        fechas.agregar(LocalDateTime.now());
        fechas.agregar(LocalDateTime.now());
        fechas.agregar(LocalDateTime.now());
        fechas.agregar(LocalDateTime.now());

        // Crear el historial con datos válidos
        HistorialDeContenido historial = new HistorialDeContenido("usuario1", contenidos, fechas);

        // Probar método: índice 1 a 3 (debería incluir los elementos en posición 1 y 2)
        ListaEnlazada<Contenido> sublista = historial.obtenerContenidosRecientes( 3);
        assertEquals(3, sublista.getTamanio());
        assertEquals(contenido2, sublista.obtener(0));
        assertEquals(contenido3, sublista.obtener(1));
        assertEquals(contenido4, sublista.obtener(2));
    }

}

