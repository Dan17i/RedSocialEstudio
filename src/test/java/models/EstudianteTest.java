package models;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Estudiante.
 */
public class EstudianteTest {

    private Estudiante estudiante;
    private Contenido contenidoEjemplo;

    /**
     * Configuración inicial antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        ListaEnlazada<String> intereses = new ListaEnlazada<>();
        intereses.agregar("Matemáticas");

        ListaEnlazada<Contenido> historial = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoraciones = new ListaEnlazada<>();
        ColaPrioridad<ColaPrioridad.SolicitudAyuda> solicitudesAyuda = new ColaPrioridad<>();
        ListaEnlazada<GrupoEstudio> grupos = new ListaEnlazada<>();

        estudiante = new Estudiante(
                "001", "Juan Pérez", "juan@correo.com", "clave123",
                intereses, historial, valoraciones, solicitudesAyuda, grupos
        );

        ListaEnlazada<Valoracion> listaVacia = new ListaEnlazada<>();
        contenidoEjemplo = new Contenido("001", "Matemáticas", "Juan Pérez", "Video", listaVacia);
    }

    /**
     * Verifica que el contenido publicado se agrega al historial del estudiante.
     */
    @Test
    public void testPublicarContenido() {
        estudiante.publicarContenido(contenidoEjemplo);
        assertTrue(estudiante.getHistorialContenidos().buscar(contenidoEjemplo));
    }

    /**
     * Verifica que una valoración se registra tanto en el estudiante como en el contenido.
     */
    @Test
    public void testValorarContenido() {
        estudiante.valorarContenido(contenidoEjemplo, 4, "Muy útil");
        assertEquals(1, contenidoEjemplo.getValoraciones().getTamanio());
        assertEquals(1, estudiante.getValoraciones().getTamanio());
    }

    /**
     * Verifica que el estudiante pueda unirse a un grupo y que se actualicen ambos lados.
     */
    @Test
    public void testUnirseAGrupo() {
        GrupoEstudio grupo = new GrupoEstudio("G01", "Grupo de Álgebra");

        int gruposAntes = estudiante.getGrupos().getTamanio();
        int miembrosAntes = grupo.getMiembros().getTamanio();

        estudiante.unirseAGrupo(grupo);

        assertEquals(gruposAntes + 1, estudiante.getGrupos().getTamanio(), "El estudiante no agregó el grupo");
        assertTrue(estudiante.getGrupos().buscar(grupo), "El grupo no fue encontrado en los grupos del estudiante");

        assertEquals(miembrosAntes + 1, grupo.getMiembros().getTamanio(), "El grupo no tiene un miembro más");
        assertTrue(grupo.getMiembros().buscar(estudiante), "El estudiante no fue encontrado en los miembros del grupo");
    }

    /**
     * Verifica que el estudiante pueda buscar contenido en su historial según criterios.
     */
    @Test
    public void testBuscarContenido() {
        ListaEnlazada<Valoracion> valoracionesVacias = new ListaEnlazada<>();

        Contenido c1 = new Contenido("101", "Matemáticas", "Juan Pérez", "Video", valoracionesVacias);
        Contenido c2 = new Contenido("102", "Historia", "Ana López", "Artículo", valoracionesVacias);

        estudiante.getHistorialContenidos().agregar(c1);
        estudiante.getHistorialContenidos().agregar(c2);

        ListaEnlazada<Contenido> resultados = estudiante.buscarContenido("Matemáticas", "Juan Pérez", "Video");

        assertEquals(1, resultados.getTamanio());
        assertEquals(c1, resultados.obtener(0));
    }

    /**
     * Verifica que dos estudiantes con la misma cédula sean considerados iguales.
     */
    @Test
    public void testEqualsYHashCode() {
        Estudiante otro = new Estudiante(
                "001", "Juan Pérez", "otro@correo.com", "clave999",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>()
        );

        assertEquals(estudiante, otro);
        assertEquals(estudiante.hashCode(), otro.hashCode());
    }
}
