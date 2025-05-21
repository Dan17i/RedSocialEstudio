package models;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MensajeTest {

    private Estudiante emisor;
    private Estudiante receptor;
    private GrupoEstudio grupo;
    private LocalDateTime fecha;

    @BeforeEach
    public void setUp() {
        emisor = new Estudiante(
                "E1", "Juan", "juan@mail.com", "1234",
                new ListaEnlazada<>(),                 // intereses (String)
                new ListaEnlazada<>(),                 // historialContenidos (Contenido)
                new ListaEnlazada<>(),                 // valoraciones
                new ColaPrioridad<>(),                 // solicitudesAyuda
                new ListaEnlazada<>()                  // gruposEstudio
        );

        receptor = new Estudiante(
                "E2", "Ana", "ana@mail.com", "1234",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>()
        );


        Estudiante tutor = new Estudiante(
                "T1",                                  // id
                "Tutor",                               // nombre
                "tutor@mail.com",                      // email
                "1234",                                // contrasenia
                new ListaEnlazada<>(),                 // intereses (String)
                new ListaEnlazada<>(),                 // historialContenidos (Contenido)
                new ListaEnlazada<>(),                 // valoraciones
                new ColaPrioridad<>(),                 // solicitudesAyuda
                new ListaEnlazada<>()                  // gruposEstudio
        );


        grupo = new GrupoEstudio("G1", "Grupo Álgebra");

        grupo.agregarMiembro(new Estudiante(
                "E3", "Luis", "luis@mail.com", "1234",
                new ListaEnlazada<>(),                 // intereses (String)
                new ListaEnlazada<>(),                 // historialContenidos (Contenido)
                new ListaEnlazada<>(),                 // valoraciones
                new ColaPrioridad<>(),                 // solicitudesAyuda
                new ListaEnlazada<>()
        ));

        grupo.agregarMiembro(new Estudiante(
                "E4", "Sofía", "sofia@mail.com", "1234",
                new ListaEnlazada<>(),                 // intereses (String)
                new ListaEnlazada<>(),                 // historialContenidos (Contenido)
                new ListaEnlazada<>(),                 // valoraciones
                new ColaPrioridad<>(),                 // solicitudesAyuda
                new ListaEnlazada<>()
        ));

        fecha = LocalDateTime.now();
    }


    @Test
    public void testEnviarMensajeAEstudiante() {
        Mensaje mensaje = new Mensaje(emisor, receptor, "Hola Ana", fecha);

        // Capturar salida por consola
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        mensaje.enviar();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Mensaje enviado a Ana: Hola Ana"));
    }

    @Test
    public void testEnviarMensajeAGrupo() {
        Mensaje mensaje = new Mensaje(emisor, grupo, "Reunión mañana", fecha);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        mensaje.enviar();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Mensaje enviado a Luis: Reunión mañana"));
        assertTrue(output.contains("Mensaje enviado a Sofía: Reunión mañana"));
    }

    @Test
    public void testConstructorParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Mensaje(null, receptor, "Hola", fecha));
        assertThrows(IllegalArgumentException.class, () -> new Mensaje(emisor, null, "Hola", fecha));
        assertThrows(IllegalArgumentException.class, () -> new Mensaje(emisor, receptor, "", fecha));
    }

    @Test
    public void testToString() {
        Mensaje mensaje = new Mensaje(emisor, receptor, "Hola", fecha);
        String result = mensaje.toString();
        assertTrue(result.contains("De: Juan"));
        assertTrue(result.contains("Para: Ana"));
        assertTrue(result.contains("Mensaje: Hola"));
    }

    @Test
    public void testEqualsAndHashCode() {
        Mensaje m1 = new Mensaje(emisor, receptor, "Hola", fecha);
        Mensaje m2 = new Mensaje(emisor, receptor, "Hola", fecha);
        Mensaje m3 = new Mensaje(emisor, receptor, "Diferente", fecha);

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1, m3);
    }
}

