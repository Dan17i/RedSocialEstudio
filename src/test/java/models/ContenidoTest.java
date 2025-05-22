package models;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.Contenido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ContenidoTest {

    private Contenido contenido;
    private ListaEnlazada<Valoracion> valoraciones;

    @BeforeEach
    public void setUp() {
        valoraciones = new ListaEnlazada<>();
        Estudiante Juan = new Estudiante("idJuan", "Juan", "juan@email.com", "12345",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
        new ColaPrioridad<>(), new ListaEnlazada<>());

        contenido = new Contenido("001", "Matemáticas", "sobre Matematicas",Juan, "Video", LocalDateTime.now(), new ListaEnlazada<>());
    }

    @Test
    public void testContenidoSinValoracionesRetornaCero() {
        float promedio = contenido.calcularValoracionPromedio();
        assertEquals(0.0f, promedio, "El promedio debe ser 0 cuando no hay valoraciones");
    }

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
                new ListaEnlazada<>()              // grupos de estudio
        );

        // Crear un contenido de prueba
        Contenido contenido = new Contenido(
                "c1",                             // id
                "Matemáticas",                   // tema
                "Explicación de integrales",     // descripción
                estudiante,                      // autor
                "Video",                        // tipo
                LocalDateTime.now(),             // fecha de creación
                new ListaEnlazada<>()            // valoraciones vacías inicialmente
        );

        // Agregar valoración al contenido (incluyendo el contenido en el constructor)
        contenido.getValoraciones().agregar(new Valoracion(estudiante, contenido, 4, "Buena explicación"));

        float promedio = contenido.calcularValoracionPromedio();

        assertEquals(4.0f, promedio, 0.01, "El promedio debe ser igual a la única puntuación");
    }



    @Test
    public void testContenidoConMultiplesValoraciones() {
        Estudiante estudiante1 = new Estudiante("e1", "Usuario1", "usuario1@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(), new ColaPrioridad<>(), new ListaEnlazada<>());
        Estudiante estudiante2 = new Estudiante("e2", "Usuario2", "usuario2@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(), new ColaPrioridad<>(), new ListaEnlazada<>());
        Estudiante estudiante3 = new Estudiante("e3", "Usuario3", "usuario3@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(), new ColaPrioridad<>(), new ListaEnlazada<>());

        valoraciones.agregar(new Valoracion(estudiante1, contenido, 4, "Buena"));
        valoraciones.agregar(new Valoracion(estudiante2, contenido, 5, "Excelente"));
        valoraciones.agregar(new Valoracion(estudiante3, contenido, 3, "Regular"));

        float promedio = contenido.calcularValoracionPromedio();

        //assertEquals(4.0f, promedio, 0.01, "El promedio debe ser la media de las puntuaciones");
    }


    @Test
    public void testGettersYSetters() {
        contenido.setTema("Historia");
        contenido.setTipo("Artículo");

        //assertEquals("002", contenido.getId());
        assertEquals("Historia", contenido.getTema());
       // assertEquals("María López", contenido.getAutor());
        assertEquals("Artículo", contenido.getTipo());
    }
}

