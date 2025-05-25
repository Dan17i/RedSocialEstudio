package models;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba unitaria para {@link Mensaje}.
 * Verifica la funcionalidad del envío de mensajes a estudiantes y grupos,
 * así como la validación de parámetros, método toString, equals y hashCode.
 * <p>
 * Estas pruebas están diseñadas para garantizar que la clase Mensaje cumpla
 * correctamente con sus responsabilidades de encapsular y enviar información
 * entre estudiantes y grupos de estudio.
 * </p>
 *
 * @author Daniel Jurado
 * @since 2025-05-21
 */
public class MensajeTest {

    private Estudiante emisor;
    private Estudiante receptor;
    private GrupoEstudio grupo;
    private LocalDateTime fecha;

    /**
     * Configura los objetos necesarios antes de cada prueba:
     * estudiantes, grupo de estudio y fecha del mensaje.
     */
    @BeforeEach
    public void setUp() {
        emisor = new Estudiante(
                "E1", "Juan", "juan@mail.com", "1234",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>()
        );

        receptor = new Estudiante(
                "E2", "Ana", "ana@mail.com", "1234",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>()
        );

        grupo = new GrupoEstudio("G1", "Grupo Álgebra");

        grupo.agregarMiembro(new Estudiante(
                "E3", "Luis", "luis@mail.com", "1234",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>()
        ));

        grupo.agregarMiembro(new Estudiante(
                "E4", "Sofía", "sofia@mail.com", "1234",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>()
        ));

        fecha = LocalDateTime.now();
    }

    /**
     * Verifica que el método {@code enviar()} funcione correctamente
     * cuando el receptor es un estudiante individual.
     */
    @Test
    public void testEnviarMensajeAEstudiante() {
        // Preparar el emisor y receptor con nombre "Ana"
        Estudiante emisor = new Estudiante(
                "E3", "Carlos", "carlos@mail.com", "1234",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>());
        Estudiante receptor = new Estudiante(
                "E3", "Ana", "ana@mail.com", "12345",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>());
        LocalDateTime fecha = LocalDateTime.now();

        Mensaje mensaje = new Mensaje(emisor, receptor, "Hola Ana", fecha);

        // Capturar salida por consola
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            mensaje.enviar();
        } finally {
            System.setOut(originalOut);  // Restaurar salida estándar
        }

        String output = outContent.toString().trim();
        // Verifica que la salida contiene la frase esperada
        assertTrue(output.contains("Mensaje recibido por Ana: Hola Ana"),
                "La salida no contiene el mensaje esperado. Salida: " + output);
    }



    /**
     * Verifica que el método {@code enviar()} funcione correctamente
     * cuando el receptor es un grupo de estudio.
     */
    @Test
    public void testEnviarMensajeAGrupo() {
        Mensaje mensaje = new Mensaje(emisor, grupo, "Reunión mañana", fecha);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        mensaje.enviar();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Mensaje recibido por Luis: Reunión mañana"));
        assertTrue(output.contains("Mensaje recibido por Sofía: Reunión mañana"));

    }

    /**
     * Verifica que el constructor de {@code Mensaje} arroje excepciones
     * al recibir parámetros inválidos (nulos o mensaje vacío).
     */
    @Test
    public void testConstructorParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Mensaje(null, receptor, "Hola", fecha));
        assertThrows(IllegalArgumentException.class, () -> new Mensaje(emisor, null, "Hola", fecha));
        assertThrows(IllegalArgumentException.class, () -> new Mensaje(emisor, receptor, "", fecha));
    }

    /**
     * Verifica que el método {@code toString()} incluya los datos relevantes del mensaje.
     */
    @Test
    public void testToString() {
        Mensaje mensaje = new Mensaje(emisor, receptor, "Hola", fecha);
        String result = mensaje.toString();
        assertTrue(result.contains("De: Juan"));
        assertTrue(result.contains("Para: Ana"));
        assertTrue(result.contains("Mensaje: Hola"));
    }

    /**
     * Verifica el comportamiento de los métodos {@code equals()} y {@code hashCode()}.
     */
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
