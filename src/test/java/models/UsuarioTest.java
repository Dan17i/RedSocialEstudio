package models;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private Estudiante estudiante;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> historialContenidos;
    private ListaEnlazada<Valoracion> valoraciones;
    private ColaPrioridad<SolicitudAyuda> solicitudesAyuda;
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
        Estudiante juan = new Estudiante(
                "idJuan",
                "Juan Pérez",
                "juan@email.com",
                "1234",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>()
        );

        Contenido contenido = new Contenido(
                "101",
                "Matemáticas",
                "sobre Matemáticas",
                juan,
                "Teoría",
                LocalDateTime.now(),
                valoracionesContenido
        );

        estudiante.valorarContenido(contenido, 5, "Excelente contenido");

        assertEquals(1, contenido.getValoraciones().getTamanio());
        assertEquals(1, estudiante.getValoraciones().getTamanio());

        Valoracion valEstudiante = estudiante.getValoraciones().obtener(0);
        Valoracion valContenido  = contenido.getValoraciones().obtener(0);

        assertEquals(valEstudiante, valContenido);
        assertEquals(5, valEstudiante.getPuntuacion());
        assertEquals("Excelente contenido", valEstudiante.getComentario());
        assertEquals(estudiante, valEstudiante.getEstudiante());
    }

    @Test
    public void testAgregarMiembroAlGrupo() {
        GrupoEstudio grupo = new GrupoEstudio("001", "Matemáticas Avanzadas");
        int miembrosAntes = grupo.getMiembros().getTamanio();

        grupo.agregarMiembro(estudiante);

        assertEquals(miembrosAntes + 1, grupo.getMiembros().getTamanio());
        assertTrue(grupo.getMiembros().contiene(estudiante));
        assertEquals(1, estudiante.getGruposEstudio().getTamanio());
        assertEquals(grupo, estudiante.getGruposEstudio().obtener(0));
    }

    @Test
    public void testPublicarContenido() {
        Estudiante ana = new Estudiante(
                "idAna",
                "Ana",
                "ana@email.com",
                "12345",
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ListaEnlazada<>(),
                new ColaPrioridad<>(),
                new ListaEnlazada<>()
        );

        ListaEnlazada<Valoracion> valoracionesVacias = new ListaEnlazada<>();

        Contenido contenido = new Contenido(
                "103",
                "Física",
                "Temas de física",
                ana,
                "Video",
                LocalDateTime.now(),
                valoracionesVacias
        );

        estudiante.publicarContenido(contenido);

        assertEquals(1, estudiante.getHistorialContenidos().getTamanio());
        assertEquals(contenido, estudiante.getHistorialContenidos().obtener(0));
    }

}