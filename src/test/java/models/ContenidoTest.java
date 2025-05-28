package models;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.Contenido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas unitarias para la clase {@link Contenido}.
 * Contiene métodos para validar la correcta creación y funcionamiento
 * de objetos Contenido, especialmente el cálculo del promedio de valoraciones.
 */
public class ContenidoTest {

    private Contenido contenido;
    private Estudiante autor;
    private ArchivoMultimedia archivo;
    private ListaEnlazada<Valoracion> valoraciones;

    /**
     * Configura los objetos comunes a utilizar antes de cada prueba.
     * Inicializa un contenido con su autor, archivo multimedia y lista de valoraciones vacía.
     */
    @BeforeEach
    public void setUp() {


                valoraciones = new ListaEnlazada<>();
                archivo= new ArchivoMultimedia("video_prueba.mp4","/multimedia/video_prueba.mp4","video/mp4", 1024);
                autor = new Estudiante("idJuan", "Juan", "juan@email.com", "12345",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(),new ListaEnlazada<>());
                contenido= new Contenido("001",
                        "Matemáticas",
                        "Explicación múltiple",
                        autor,
                        TipoContenido.VIDEO,
                        LocalDateTime.now(),
                        valoraciones,
                        archivo);
    }
    /**
     * Verifica que un objeto Contenido se cree correctamente con todos sus atributos.
     */
    @Test
    public void testCrearContenido() {
        contenido = new Contenido(
                "001",
                "Matemáticas",
                "sobre Matematicas",
                autor,
                TipoContenido.VIDEO,
                LocalDateTime.now(),
                valoraciones,
                archivo);

        assertEquals("001", contenido.getId());
        assertEquals("Matemáticas", contenido.getTema());
        assertEquals("sobre Matematicas", contenido.getDescripcion());
        assertEquals(autor, contenido.getAutor());
        assertEquals(TipoContenido.VIDEO, contenido.getTipo());
        assertEquals(valoraciones, contenido.getValoraciones());
        assertEquals(archivo, contenido.getArchivoMultimedia());
    }
    /**
     * Prueba que el cálculo del promedio de valoraciones
     * retorne 0 cuando no existen valoraciones en el contenido.
     */
    @Test
    public void testContenidoSinValoracionesRetornaCero() {
        double promedio = contenido.promedioValoraciones();
        assertEquals(0.0f, promedio, "El promedio debe ser 0 cuando no hay valoraciones");
    }
    /**
     * Prueba que el cálculo del promedio de valoraciones funcione correctamente
     * cuando existe una única valoración para el contenido.
     */
    @Test
    public void testContenidoConUnaValoracion() {
        // Crear un estudiante de prueba
        Estudiante estudiante = new Estudiante(
                "e1",                              // id
                "Usuario1",                        // nombre
                "usuario1@correo.com",             // email
                "1234",                            // contraseña
                new ListaEnlazada<>(),             // intereses
                new ListaEnlazada<>(),             // historial de contenidos
                new ListaEnlazada<>(),             // valoraciones
                new ColaPrioridad<>(),             // solicitudes de ayuda
                new ListaEnlazada<>(),              // grupos de estudio
                new ListaEnlazada<>()
        );

        // Crear un contenido de prueba
        Contenido contenido = new Contenido(
                "c1",                             // id
                "Matemáticas",                   // tema
                "Explicación de integrales",     // descripción
                estudiante,                      // autor
                TipoContenido.VIDEO, // tipo
                LocalDateTime.now(), // fecha de creación
                valoraciones,// valoraciones vacías inicialmente
                archivo
        );

        // Agregar valoración al contenido (incluyendo el contenido en el constructor)
        contenido.getValoraciones().agregar(new Valoracion(estudiante, contenido, 4, "Buena explicación"));

        double promedio = contenido.promedioValoraciones();

        assertEquals(4.0f, promedio, 0.01, "El promedio debe ser igual a la única puntuación");
    }
    /**
     * Prueba que el cálculo del promedio de valoraciones funcione correctamente
     * cuando existen múltiples valoraciones para un contenido.
     * Se valida que el promedio sea la media aritmética de las puntuaciones.
     */
    @Test
    public void testContenidoConMultiplesValoraciones() {
        Estudiante estudiante1 = new Estudiante("e1", "Usuario1", "usuario1@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(), new ColaPrioridad<>(), new ListaEnlazada<>(),new ListaEnlazada<>());
        Estudiante estudiante2 = new Estudiante("e2", "Usuario2", "usuario2@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(), new ColaPrioridad<>(), new ListaEnlazada<>(), new ListaEnlazada<>());
        Estudiante estudiante3 = new Estudiante("e3", "Usuario3", "usuario3@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(), new ColaPrioridad<>(), new ListaEnlazada<>(), new ListaEnlazada<>());

        valoraciones.agregar(new Valoracion(estudiante1, contenido, 4, "Buena"));
        valoraciones.agregar(new Valoracion(estudiante2, contenido, 5, "Excelente"));
        valoraciones.agregar(new Valoracion(estudiante3, contenido, 3, "Regular"));

        double promedio = contenido.promedioValoraciones();

        assertEquals(4.0f, promedio, 0.01, "El promedio debe ser la media de las puntuaciones");
    }



}

