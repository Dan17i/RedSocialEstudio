package models;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de prueba para la clase {@link Estudiante}.
 * Contiene pruebas unitarias para validar las funcionalidades relacionadas con
 * valorar contenidos, unirse a grupos de estudio y publicar contenidos.
 *
 * @author Daniel Juraro, Sebastian Torres y Juan Soto
 * @version 1.0
 */
public class UsuarioTest {

    private Estudiante estudiante;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> historialContenidos;
    private ListaEnlazada<Valoracion> valoraciones;
    private ColaPrioridad<SolicitudAyuda> solicitudesAyuda;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;
    private ListaEnlazada<Mensaje> bandejaEntrada;
    private ArchivoMultimedia archivo;
    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        intereses = new ListaEnlazada<>();
        historialContenidos = new ListaEnlazada<>();
        valoraciones = new ListaEnlazada<>();
        solicitudesAyuda = new ColaPrioridad<>();
        gruposEstudio = new ListaEnlazada<>();
        bandejaEntrada = new ListaEnlazada<>();

        estudiante = new Estudiante(
                "001",
                "Juan Pérez",
                "juan.perez@uniquindio.edu.co",
                "1234",
                intereses,
                historialContenidos,
                valoraciones,
                solicitudesAyuda,
                gruposEstudio,
                bandejaEntrada

        );
    }
    /**
     * Verifica que un estudiante pueda valorar correctamente un contenido.
     * Se valida que la valoración se agregue tanto al contenido como al estudiante.
     */
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
                new ListaEnlazada<>(),
                new ListaEnlazada<>()
        );

        Contenido contenido = new Contenido(
                "101",
                "Matemáticas",
                "sobre Matemáticas",
                juan,
                TipoContenido.TEXTO,
                LocalDateTime.now(),
                valoracionesContenido,
                archivo
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
    /**
     * Verifica que un estudiante pueda ser agregado a un grupo de estudio correctamente.
     * También válida que el grupo se agregue a la lista de grupos del estudiante.
     */
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
    /**
     * Verifica que un estudiante pueda publicar contenido correctamente,
     * agregándolo a su historial de contenidos.
     */
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
                new ListaEnlazada<>(),
                new ListaEnlazada<>()
        );

        ListaEnlazada<Valoracion> valoracionesVacias = new ListaEnlazada<>();

        Contenido contenido = new Contenido(
                "103",
                "Física",
                "Temas de física",
                ana,
                TipoContenido.VIDEO,
                LocalDateTime.now(),
                valoracionesVacias,
                archivo
        );
        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();
        ListaEnlazada<Contenido> contenidoDestacado = new ListaEnlazada<>();
        GestorContenidos gestorContenidos = new GestorContenidos(arbolContenidos, contenidoDestacado);
        estudiante.publicarContenido(contenido,gestorContenidos);

        assertEquals(1, estudiante.getHistorialContenidos().getTamanio());
        assertEquals(contenido, estudiante.getHistorialContenidos().obtener(0));
    }

}