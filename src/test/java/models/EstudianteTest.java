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
    /**
     * Verifica que un estudiante se cree correctamente y que sus atributos sean accesibles.
     */

    private Estudiante<?> estudiante;
    private Contenido contenidoEjemplo;

    @BeforeEach
    public void setUp() {
        ListaEnlazada<String> intereses = new ListaEnlazada<>();
        intereses.agregar("Matemáticas");

        ListaEnlazada<Contenido> historial = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoraciones = new ListaEnlazada<>();
        ColaPrioridad<ColaPrioridad.SolicitudAyuda> solicitudesAyuda = new ColaPrioridad<>();
        ListaEnlazada<GrupoEstudio> grupos = new ListaEnlazada<>();

        estudiante = new Estudiante<>(
                "001", "Juan Pérez", "juan@correo.com", "clave123",
                intereses, historial, valoraciones, solicitudesAyuda, grupos
        );
        ListaEnlazada<Valoracion> listaVacia = new ListaEnlazada<>();
        contenidoEjemplo = new Contenido("001", "Matemáticas", "Juan Pérez", "Video", listaVacia);
    }

    @Test
    public void testPublicarContenido() {
        estudiante.publicarContenido(contenidoEjemplo);
        assertTrue(estudiante.getHistorialContenidos().buscar(contenidoEjemplo));
    }

    @Test
    public void testValorarContenido() {
        estudiante.valorarContenido(contenidoEjemplo, 4, "Muy útil");
        assertEquals(1, contenidoEjemplo.getValoraciones().getTamanio());
        assertEquals(1, estudiante.getValoraciones().getTamanio());
    }

    @Test
    public void testUnirseAGrupo() {
        GrupoEstudio grupo = new GrupoEstudio("G01", "Grupo de Álgebra");
        estudiante.unirseAGrupo(grupo);

        assertTrue(estudiante.getGruposEstudio().buscar(grupo));
        assertTrue(grupo.getMiembros().buscar(estudiante));
    }

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

    @Test
    public void testEqualsYHashCode() {
        Estudiante<?> otro = new Estudiante<>(
                "001", "Juan Pérez", "otro@correo.com", "clave999",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>()
        );

        assertEquals(estudiante, otro);
        assertEquals(estudiante.hashCode(), otro.hashCode());
    }
}
