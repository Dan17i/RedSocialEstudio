package models;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.Estudiante;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private Estudiante estudiante;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> historialContenidos;
    private ListaEnlazada<Valoracion> valoraciones;
    private ColaPrioridad<ColaPrioridad.SolicitudAyuda> solicitudesAyuda;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;

    @BeforeEach
    public void setUp() {
        intereses = new ListaEnlazada<>();
        historialContenidos = new ListaEnlazada<>();
        valoraciones = new ListaEnlazada<>();
        solicitudesAyuda = new ColaPrioridad<>();
        gruposEstudio = new ListaEnlazada<>();

        estudiante = new Estudiante(
                "001",
                "Juan Pérez",
                "juan.perez@uniquindio.edu.co",
                "1234",
                intereses,
                historialContenidos,
                valoraciones,
                solicitudesAyuda,
                gruposEstudio
        );
    }

    @Test
    public void testValorarContenido() {
        ListaEnlazada<Valoracion> valoracionesContenido = new ListaEnlazada<>();
        Contenido contenido = new Contenido("101", "Matemáticas", "Juan Pérez", "Video", valoracionesContenido);

        estudiante.valorarContenido(contenido, 5, "Excelente contenido");

        // Verificar que la valoración fue añadida al contenido
        assertEquals(1, contenido.getValoraciones().getTamanio());

        // Verificar que la valoración fue añadida al estudiante
        assertEquals(1, estudiante.getValoraciones().getTamanio());

        Valoracion valEstudiante = estudiante.getValoraciones().obtener(0);
        Valoracion valContenido = contenido.getValoraciones().obtener(0);

        assertEquals(valEstudiante, valContenido);
        assertEquals(5, valEstudiante.getPuntuacion());
        assertEquals("Excelente contenido", valEstudiante.getComentario());
        assertEquals(estudiante, valEstudiante.getEstudiante());
    }

    @Test
    public void testAgregarMiembroAlGrupo() {
        GrupoEstudio grupo = new GrupoEstudio("001", "Matemáticas Avanzadas");
        int miembrosAntes = grupo.getMiembros().getTamanio(); // debería ser 0 inicialmente

        // Agregar el estudiante al grupo mediante el método del grupo
        grupo.agregarMiembro(estudiante);

        // Verificar que el grupo tiene un miembro más
        assertEquals(miembrosAntes + 1, grupo.getMiembros().getTamanio());

        // Verificar que el grupo contiene al estudiante
        assertTrue(grupo.getMiembros().contiene(estudiante));

        // Verificar que el estudiante tiene asociado el grupo
        assertEquals(1, estudiante.getGruposEstudio().getTamanio());
        assertEquals(grupo, estudiante.getGruposEstudio().obtener(0));
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
    public void testPublicarContenido() {
        ListaEnlazada<Valoracion> valoracionesVacias = new ListaEnlazada<>();
        Contenido contenido = new Contenido("103", "Física", "Ana López", "Video", valoracionesVacias);

        estudiante.publicarContenido(contenido);

        // Verificar que se agregó al historial del estudiante
        assertEquals(1, estudiante.getHistorialContenidos().getTamanio());
        assertEquals(contenido, estudiante.getHistorialContenidos().obtener(0));

    }
}
