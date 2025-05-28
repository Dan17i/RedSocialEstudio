package models;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas unitarias para la clase {@link GrupoEstudio}.
 * Verifica el correcto funcionamiento de los métodos relacionados con miembros,
 * publicaciones y subgrupos.
 *
 */
public class GrupoEstudioTest {
    /** Instancia del grupo de estudio que se va a probar. */
    private GrupoEstudio grupo;
    /** Primer estudiante de prueba. */
    private Estudiante estudiante1;
    /** Segundo estudiante de prueba. */
    private Estudiante estudiante2;
    /** Contenido de prueba. */
    private Contenido contenido;
    /** Archivo multimedia de prueba. */
    private ArchivoMultimedia archivo;
    /**
     * Inicializa los objetos necesarios para las pruebas antes de cada ejecución.
     */
    @BeforeEach
    public void setUp() {
        grupo = new GrupoEstudio("g1", "Matemáticas");

        estudiante1 = new Estudiante("e1", "Juan", "juan@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(), new ListaEnlazada<>());

        estudiante2 = new Estudiante("e2", "Ana", "ana@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(),new ListaEnlazada<>());

        contenido = new Contenido("c1", "Integrales","sobre integrales", estudiante1, TipoContenido.TEXTO, LocalDateTime.now(),new ListaEnlazada<>(),archivo);
    }
    /**
     * Verifica que el constructor de {@link GrupoEstudio} inicializa correctamente los valores válidos.
     */
    @Test
    public void testConstructorValido() {
        assertEquals("g1", grupo.getId());
        assertEquals("Matemáticas", grupo.getTema());
        assertEquals(0, grupo.getMiembros().getTamanio());
    }
    /**
     * Verifica que el constructor lanza una excepción al recibir parámetros inválidos.
     */
    @Test
    public void testConstructorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new GrupoEstudio(null, "Tema"));
        assertThrows(IllegalArgumentException.class, () -> new GrupoEstudio("id", ""));
    }
    /**
     * Verifica que se pueda agregar un miembro correctamente al grupo de estudio.
     */
    @Test
    public void testAgregarMiembro() {
        grupo.agregarMiembro(estudiante1);
        assertEquals(1, grupo.getMiembros().getTamanio());
        assertTrue(estudiante1.getGruposEstudio().contiene(grupo));
    }
    /**
     * Verifica que agregar un miembro nulo lanza una excepción.
     */
    @Test
    public void testAgregarMiembroNulo() {
        assertThrows(IllegalArgumentException.class, () -> grupo.agregarMiembro(null));
    }
    /**
     * Verifica que se puede obtener correctamente un subgrupo dado un rango válido.
     */
    @Test
    public void testObtenerSubgrupo() {
        grupo.agregarMiembro(estudiante1);
        grupo.agregarMiembro(estudiante2);

        ListaEnlazada<Estudiante> subgrupo = grupo.obtenerSubgrupo(0, 1);
        assertEquals(2, subgrupo.getTamanio());
        assertEquals("Juan", subgrupo.obtener(0).getNombre());
        assertEquals("Ana", subgrupo.obtener(1).getNombre());
    }
    /**
     * Verifica que se lanza una excepción al intentar obtener un subgrupo fuera de rango.
     */
    @Test
    public void testObtenerSubgrupoFueraDeRango() {
        grupo.agregarMiembro(estudiante1);
        assertThrows(IndexOutOfBoundsException.class, () -> grupo.obtenerSubgrupo(0, 5));
    }
    /**
     * Verifica que se pueda publicar un contenido correctamente en el grupo.
     */
    @Test
    public void testPublicarContenidoGrupo() {
        grupo.publicarContenidoGrupo(contenido);
        ListaEnlazada<Contenido> publicaciones = grupo.getPublicaciones();
        assertEquals(1, publicaciones.getTamanio());
        assertEquals("Integrales", publicaciones.obtener(0).getTema());
    }
    /**
     * Verifica que se lanza una excepción al intentar publicar un contenido nulo.
     */
    @Test
    public void testPublicarContenidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> grupo.publicarContenidoGrupo(null));
    }
    /**
     * Verifica que se puede cambiar el tema del grupo a un valor válido.
     */
    @Test
    public void testSetTemaValido() {
        grupo.setTema("Física");
        assertEquals("Física", grupo.getTema());
    }
    /**
     * Verifica que cambiar el tema a un valor inválido lanza una excepción.
     */
    @Test
    public void testSetTemaInvalido() {
        assertThrows(IllegalArgumentException.class, () -> grupo.setTema(""));
    }
    /**
     * Verifica que el método {@code toString} devuelve una representación válida del grupo.
     */
    @Test
    public void testToString() {
        grupo.agregarMiembro(estudiante1);
        String salida = grupo.toString();
        assertTrue(salida.contains("g1"));
        assertTrue(salida.contains("Matemáticas"));
        assertTrue(salida.contains("miembros=1"));
    }
}

