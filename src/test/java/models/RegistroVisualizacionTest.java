package models;
import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.TipoContenido;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.RegistroVisualizacion;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistroVisualizacionTest {

    private Estudiante usuario;  // Cambié el tipo aquí para evitar castings innecesarios
    private Contenido contenido;
    private LocalDateTime fechaVisualizacion;
    private ArchivoMultimedia archivo;

    /**
     * Configura el entorno antes de cada prueba.
     * <p>
     * Inicializa un objeto {@link Estudiante} con listas vacías para intereses,
     * historial de contenidos y valoraciones. También crea un objeto {@link Contenido}
     * asociado a este estudiante como autor, con una lista vacía de valoraciones.
     * Además, establece una fecha fija para la visualización.
     */
    @BeforeEach
    public void setUp() {
        // Crear listas vacías para los argumentos del constructor
        ListaEnlazada<String> intereses = new ListaEnlazada<>();
        ListaEnlazada<Contenido> historialContenidos = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoracionesUsuario = new ListaEnlazada<>();

        // Crear Estudiante directamente
        usuario = new Estudiante(
                "123",
                "Ana Pérez",
                "ana@email.com",
                "1234",
                intereses,
                historialContenidos,
                valoracionesUsuario,
                new ColaPrioridad<>(), // solicitudesAyuda vacía
                new ListaEnlazada<>(),  // gruposEstudio vacía
                new ListaEnlazada<>()
        );

        // Crear valoraciones para el contenido
        ListaEnlazada<Valoracion> valoracionesContenido = new ListaEnlazada<>();

        // Crear contenido con autor Estudiante
        contenido = new Contenido(
                "001",
                "Título de prueba",
                "Descripción de prueba",
                usuario,           // usuario ya es Estudiante, no se necesita cast
                TipoContenido.TEXTO,
                LocalDateTime.now(),
                valoracionesContenido,
                archivo
        );

        // Definir fecha de visualización
        fechaVisualizacion = LocalDateTime.of(2025, 5, 21, 15, 30);
    }

    /**
     * Verifica que el constructor de {@link RegistroVisualizacion} inicialice
     * correctamente sus atributos y que los métodos getters devuelvan los valores esperados.
     * <p>
     * Comprueba que:
     * <ul>
     *   <li>El usuario asignado es el esperado.</li>
     *   <li>El contenido asignado es el esperado.</li>
     *   <li>La fecha de visualización asignada es la esperada.</li>
     * </ul>
     */
    @Test
    public void testConstructorYGetters() {
        RegistroVisualizacion registro = new RegistroVisualizacion(usuario, contenido, fechaVisualizacion);

        assertEquals(usuario, registro.getUsuario(), "El usuario no coincide");
        assertEquals(contenido, registro.getContenido(), "El contenido no coincide");
        assertEquals(fechaVisualizacion, registro.getFechaVisualizacion(), "La fecha de visualización no coincide");
    }
}


