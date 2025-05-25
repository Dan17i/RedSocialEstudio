package models;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.HistorialDeContenido;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba unitaria para la clase {@link HistorialDeContenido}.
 * Verifica el correcto funcionamiento de los métodos relacionados con el historial de acceso a contenidos.
 *
 * @author Tú
 * @since 2025-05-20
 */
public class HistorialDeContenidoTest {

    private HistorialDeContenido historial;
    private Contenido contenido1;
    private Contenido contenido2;
    private Contenido contenido3;
    private LocalDateTime ahora;
    private Estudiante autor;

    /**
     * Inicializa los datos antes de cada prueba.
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
                "Teoría",
                LocalDateTime.now(),  // fecha actual para creación
                new ListaEnlazada<>()
        );

        contenido2 = new Contenido(
                "c2",
                "Derivadas",
                "Descripción sobre derivadas",
                autor,
                "Teoría",
                LocalDateTime.now(),
                new ListaEnlazada<>()
        );

        contenido3 = new Contenido(
                "c3",
                "Límites",
                "Ejercicios de límites",
                autor,
                "Ejercicio",
                LocalDateTime.now(),
                new ListaEnlazada<>()
        );



        ahora = LocalDateTime.now();

        historial = new HistorialDeContenido("e1", new ListaEnlazada<>(), new ListaEnlazada<>());
    }

    /**
     * Verifica que agregar contenido y fecha al historial sea exitoso.
     */
    @Test
    public void testAgregarContenido() {
        historial.agregarContenido(contenido1, ahora);
        assertEquals(1, historial.getContenidos().getTamanio());
        assertEquals(contenido1, historial.getContenidos().obtener(0));
        assertEquals(ahora, historial.getFechasAcceso().obtener(0));
    }

    /**
     * Verifica que se filtren correctamente los contenidos por tema.
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
     * Verifica que se obtengan los últimos N contenidos correctamente.
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
     * Verifica que se invierta correctamente el historial de contenidos y fechas.
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
     * Verifica que se clone correctamente el historial.
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
     * Verifica que se obtenga una sublista válida del historial de contenidos.
     */
    @Test
    public void testObtenerContenidosRecientes() {

        // Crear lista de contenidos
        ListaEnlazada<Contenido> contenidos = new ListaEnlazada<>();
        Contenido contenido1 = new Contenido("C1", "Tema 1", "Descripción X", autor, "Texto",
                LocalDateTime.now(), new ListaEnlazada<>());

        Contenido contenido2 = new Contenido("C2", "Tema 2", "Descripción X", autor, "Texto",
                LocalDateTime.now(), new ListaEnlazada<>());

        Contenido contenido3 = new Contenido("C3", "Tema 3", "Descripción X", autor, "Texto",
                LocalDateTime.now(), new ListaEnlazada<>());

        Contenido contenido4 = new Contenido("C4", "Tema 4", "Descripción X", autor, "Texto",
                LocalDateTime.now(), new ListaEnlazada<>());


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

