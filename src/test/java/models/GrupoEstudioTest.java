package models;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrupoEstudioTest {
 // metodos
    private GrupoEstudio grupo;
    private Estudiante estudiante1;
    private Estudiante estudiante2;
    private Contenido contenido;

    @BeforeEach
    public void setUp() {
        grupo = new GrupoEstudio("g1", "Matemáticas");

        estudiante1 = new Estudiante("e1", "Juan", "juan@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>());

        estudiante2 = new Estudiante("e2", "Ana", "ana@correo.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>());

        contenido = new Contenido("c1", "Integrales", estudiante1, "Texto", new ListaEnlazada<>());
    }


    @Test
    public void testConstructorValido() {
        assertEquals("g1", grupo.getId());
        assertEquals("Matemáticas", grupo.getTema());
        assertEquals(0, grupo.getMiembros().getTamanio());
    }

    @Test
    public void testConstructorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new GrupoEstudio(null, "Tema"));
        assertThrows(IllegalArgumentException.class, () -> new GrupoEstudio("id", ""));
    }

    @Test
    public void testAgregarMiembro() {
        grupo.agregarMiembro(estudiante1);
        assertEquals(1, grupo.getMiembros().getTamanio());
        assertTrue(estudiante1.getGrupos().contiene(grupo));
    }

    @Test
    public void testAgregarMiembroNulo() {
        assertThrows(IllegalArgumentException.class, () -> grupo.agregarMiembro(null));
    }

    @Test
    public void testObtenerSubgrupo() {
        grupo.agregarMiembro(estudiante1);
        grupo.agregarMiembro(estudiante2);

        ListaEnlazada<Estudiante> subgrupo = grupo.obtenerSubgrupo(0, 1);
        assertEquals(2, subgrupo.getTamanio());
        assertEquals("Juan", subgrupo.obtener(0).getNombre());
        assertEquals("Ana", subgrupo.obtener(1).getNombre());
    }

    @Test
    public void testObtenerSubgrupoFueraDeRango() {
        grupo.agregarMiembro(estudiante1);
        assertThrows(IndexOutOfBoundsException.class, () -> grupo.obtenerSubgrupo(0, 5));
    }

    @Test
    public void testPublicarContenidoGrupo() {
        grupo.publicarContenidoGrupo(contenido);
        ListaEnlazada<Contenido> publicaciones = grupo.getPublicaciones();
        assertEquals(1, publicaciones.getTamanio());
        assertEquals("Integrales", publicaciones.obtener(0).getTema());
    }

    @Test
    public void testPublicarContenidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> grupo.publicarContenidoGrupo(null));
    }

    @Test
    public void testSetTemaValido() {
        grupo.setTema("Física");
        assertEquals("Física", grupo.getTema());
    }

    @Test
    public void testSetTemaInvalido() {
        assertThrows(IllegalArgumentException.class, () -> grupo.setTema(""));
    }

    @Test
    public void testToString() {
        grupo.agregarMiembro(estudiante1);
        String salida = grupo.toString();
        assertTrue(salida.contains("g1"));
        assertTrue(salida.contains("Matemáticas"));
        assertTrue(salida.contains("miembros=1"));
    }
}

