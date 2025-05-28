package models;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas unitarias para la clase {@link Estudiante}.
 * <p>
 * Se realizan tests para verificar funcionalidades como publicar contenido,
 * valorar contenido, unirse a grupos, buscar contenido y verificar igualdad
 * entre objetos {@code Estudiante}.
 * </p>
 */
public class EstudianteTest {

    private Estudiante estudiante;
    private Contenido contenidoEjemplo;
    private ArchivoMultimedia archivo;
    /**
     * Configura el escenario inicial antes de cada prueba.
     * <p>
     * Crea un estudiante con intereses y otros atributos inicializados.
     * También crea un contenido de ejemplo asociado a otro estudiante.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        ListaEnlazada<String> intereses = new ListaEnlazada<>();
        intereses.agregar("Matemáticas");

        ListaEnlazada<Contenido> historial = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoracionesEstudiante = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoracionesContenido= new ListaEnlazada<>();
        ColaPrioridad<SolicitudAyuda> solicitudesAyuda = new ColaPrioridad<>();
        ListaEnlazada<GrupoEstudio> grupos = new ListaEnlazada<>();
        ListaEnlazada<Mensaje> mensajes = new ListaEnlazada<>();

        estudiante = new Estudiante(
                "001", "Juan Pérez", "juan@correo.com", "clave123",
                intereses, historial, valoracionesEstudiante, solicitudesAyuda, grupos,mensajes);

        Estudiante Juanes = new Estudiante("idJuan", "Juanes", "juanes@email.com", "12345",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(),new ListaEnlazada<>());

        contenidoEjemplo = new Contenido("001", "Matemáticas", "trata de matematicas", Juanes, TipoContenido.VIDEO, LocalDateTime.now(), valoracionesContenido, archivo);
    }
    /**
     * Verifica que al publicar un contenido, este se agregue al historial del estudiante.
     */
    @Test
    public void testPublicarContenido() {
        LocalDateTime ahora = LocalDateTime.now();
        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();
        ListaEnlazada<Contenido> contenidoDestacado = new ListaEnlazada<>();
        GestorContenidos gestorContenidos = new GestorContenidos(arbolContenidos,contenidoDestacado);
        estudiante.publicarContenido(contenidoEjemplo, gestorContenidos);
        assertTrue(estudiante.getHistorialContenidos().buscar(contenidoEjemplo));
    }
    /**
     * Verifica que al valorar un contenido, la valoración se registre
     * tanto en el estudiante como en el contenido.
     */
    @Test
    public void testValorarContenido() {
        estudiante.valorarContenido(contenidoEjemplo, 4, "Muy útil");
        assertEquals(1, contenidoEjemplo.getValoraciones().getTamanio());
        assertEquals(1, estudiante.getValoraciones().getTamanio());
    }
    /**
     * Verifica que el estudiante pueda unirse a un grupo de estudio,
     * y que el grupo refleje la adición del estudiante.
     */
    @Test
    public void testUnirseAGrupo() {
        GrupoEstudio grupo = new GrupoEstudio("G01", "Grupo de Álgebra");

        int gruposAntes = estudiante.getGruposEstudio().getTamanio();
        int miembrosAntes = grupo.getMiembros().getTamanio();

        estudiante.unirseAGrupo(grupo);

        assertEquals(gruposAntes + 1, estudiante.getGruposEstudio().getTamanio(), "El estudiante no agregó el grupo");
        assertTrue(estudiante.getGruposEstudio().buscar(grupo), "El grupo no fue encontrado en los grupos del estudiante");

        assertEquals(miembrosAntes + 1, grupo.getMiembros().getTamanio(), "El grupo no tiene un miembro más");
        assertTrue(grupo.getMiembros().buscar(estudiante), "El estudiante no fue encontrado en los miembros del grupo");
    }
    /**
     * Verifica que el estudiante pueda buscar contenido en su historial
     * aplicando filtros como tema, autor y tipo de contenido.
     */
    @Test
    public void testBuscarContenido() {
        ListaEnlazada<Valoracion> valoracionesVacias = new ListaEnlazada<>();

        Estudiante juan = new Estudiante("idJuan", "Juan Pérez", "juan@email.com", "1234",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(),new ListaEnlazada<>());

        Estudiante ana = new Estudiante("idAna", "Ana López", "ana@email.com", "5678",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(), new ListaEnlazada<>());

        Contenido c1 = new Contenido("101", "Matemáticas", "trata sobre matemáticas", juan, TipoContenido.VIDEO, LocalDateTime.now(), valoracionesVacias,archivo);
        Contenido c2 = new Contenido("102", "Historia", "Trata sobre historia", ana, TipoContenido.TEXTO, LocalDateTime.now(), valoracionesVacias,archivo);

        GestorContenidos gestor = GestorContenidos.getInstancia();

        // Insertar contenidos en el árbol para que la búsqueda funcione
        gestor.agregarContenido(c1);
        gestor.agregarContenido(c2);

        // También marcarlos como destacados si se quiere
        gestor.marcarComoDestacado(c1);
        gestor.marcarComoDestacado(c2);

        // Ahora buscar con el mismo objeto que tiene el método buscarContenido
        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();

        ListaEnlazada<Contenido> contenidoDestacado = new ListaEnlazada<>();
        ListaEnlazada<Contenido> resultados = juan.buscarContenido("Matemáticas", "Juan Pérez", "Video", gestor);

        // corregirlo @Daniel

        assertEquals(1, resultados.getTamanio());
        assertEquals(c1, resultados.obtener(0));
    }

    /**
     * Verifica que dos estudiantes con la misma cédula (ID) sean considerados iguales
     * y tengan el mismo código hash.
     */
    @Test
    public void testEqualsYHashCode() {
        Estudiante otro = new Estudiante(
                "001", "Juan Pérez", "otro@correo.com", "clave999",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ColaPrioridad<>(), new ListaEnlazada<>(),new ListaEnlazada<>());

        assertEquals(estudiante, otro);
        assertEquals(estudiante.hashCode(), otro.hashCode());
    }
}
