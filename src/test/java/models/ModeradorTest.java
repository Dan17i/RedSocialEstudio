package models;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.Enums.TipoReporte;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.GestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.implement.GestorUsuarios;

import co.edu.uniquindio.redsocial.models.services.interf.IGestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la clase {@link Moderador}.
 * Verifica el comportamiento de los métodos y atributos del moderador,
 * incluyendo el control de acceso, gestión de áreas de responsabilidad y generación de reportes.
 *
 * @author Daniel Jurado
 * @since 2025-05-21
 */
public class ModeradorTest {

    private Moderador moderador;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> historial;
    private ListaEnlazada<Valoracion> valoraciones;
    private ListaEnlazada<String> areas;
    private IGestorUsuarios gestorUsuarios;
    private GestorContenidos gestorContenidos;
    private IGestorRedSocial gestorRedSocial;


    /**
     * Inicializa un objeto {@code Moderador} con datos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        intereses = new ListaEnlazada<>();
        historial = new ListaEnlazada<>();
        valoraciones = new ListaEnlazada<>();
        areas = new ListaEnlazada<>();
        areas.agregar("Educación");
        areas.agregar("Tecnología");

        gestorUsuarios = new GestorUsuarios();
        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();
        ListaEnlazada<Contenido> contenidoDestacado = new ListaEnlazada<>();
        gestorContenidos = new GestorContenidos(arbolContenidos,contenidoDestacado);
        gestorRedSocial = new GestorRedSocial();

        moderador = new Moderador(
                "1", "Carlos", "carlos@correo.com", "clave123",
                intereses, historial, valoraciones, true, areas,
                gestorUsuarios, gestorContenidos, gestorRedSocial
        );
    }



    /**
     * Verifica que el constructor y los getters funcionen correctamente.
     */
    @Test
    void testConstructorYGetters() {
        assertEquals("Carlos", moderador.getNombre());
        assertEquals("carlos@correo.com", moderador.getEmail());
        assertTrue(moderador.tieneAccesoCompleto());
        assertEquals(2, moderador.getAreasResponsabilidad().getTamanio());
    }

    /**
     * Prueba la modificación del acceso completo mediante el setter.
     */
    @Test
    void testSetAccesoCompleto() {
        moderador.setAccesoCompleto(false);
        assertFalse(moderador.tieneAccesoCompleto());
    }

    /**
     * Verifica que se puedan asignar nuevas áreas de responsabilidad válidas.
     */
    @Test
    void testSetAreasResponsabilidadValida() {
        ListaEnlazada<String> nuevasAreas = new ListaEnlazada<>();
        nuevasAreas.agregar("Salud");
        moderador.setAreasResponsabilidad(nuevasAreas);
        assertEquals(1, moderador.getAreasResponsabilidad().getTamanio());
    }

    /**
     * Verifica que se lance una excepción al intentar establecer {@code null}
     * como áreas de responsabilidad.
     */
    @Test
    void testSetAreasResponsabilidadNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            moderador.setAreasResponsabilidad(null);
        });
    }

    /**
     * Verifica que se lance una excepción al establecer una lista vacía
     * como áreas de responsabilidad.
     */
    @Test
    void testSetAreasResponsabilidadVacia() {
        ListaEnlazada<String> vacia = new ListaEnlazada<>();
        assertThrows(IllegalArgumentException.class, () -> {
            moderador.setAreasResponsabilidad(vacia);
        });
    }

    /**
     * Verifica que el moderador pueda gestionar cualquier área si tiene acceso completo.
     */
    @Test
    void testPuedeGestionarAreaConAccesoCompleto() {
        assertTrue(moderador.puedeGestionarArea("OtraArea"));
    }

    /**
     * Verifica que el moderador solo pueda gestionar áreas permitidas si no tiene acceso completo.
     */
    @Test
    void testPuedeGestionarAreaSinAccesoCompleto() {
        moderador.setAccesoCompleto(false);
        assertTrue(moderador.puedeGestionarArea("Educación"));
        assertFalse(moderador.puedeGestionarArea("NoExiste"));
    }

    /**
     * Verifica la generación de reportes usando los métodos disponibles.
     */
    @Test
    void testGenerarReportes() {
        Reporte<?> reporteGeneral = moderador.generarReporte(TipoReporte.INFORME);
        assertNotNull(reporteGeneral);
        assertEquals(TipoReporte.INFORME, reporteGeneral.getTipo());

        assertEquals(TipoReporte.CONTENIDOS_VALORADOS,
                moderador.generarReporteContenidosMasValorados().getTipo());

        assertEquals(TipoReporte.ESTUDIANTES_CONECTADOS,
                moderador.generarReporteEstudiantesMasConectados().getTipo());

        assertEquals(TipoReporte.INFORME,
                moderador.generarReporteCaminosMasCortos("A", "B").getTipo());


        assertEquals(TipoReporte.INFORME,
                moderador.generarReporteParticipacion().getTipo());
    }
    /**
     * Verifica que el método {@code darAltaUsuario} registre correctamente un nuevo usuario sin lanzar excepciones.
     * Se asegura además de que el usuario quede almacenado en el sistema y sea accesible posteriormente.
     */
    @Test
    void testDarAltaUsuario() {
        Usuario usuario = new Usuario("2", "Ana", "ana@correo.com", "pass",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>());
        assertDoesNotThrow(() -> moderador.darAltaUsuario(usuario));
        // Verifica que usuario está registrado
        assertNotNull(gestorUsuarios.buscarUsuarioPorId("2"));
    }
    /**
     * Verifica que el método {@code darBajaUsuario} elimine correctamente a un usuario del sistema sin lanzar excepciones.
     * También comprueba que, tras la eliminación, el usuario ya no esté disponible en el sistema.
     */
    @Test
    void testDarBajaUsuario() {
        Usuario usuario = new Usuario("2", "Ana", "ana@correo.com", "pass",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>());
        moderador.darAltaUsuario(usuario);
        assertDoesNotThrow(() -> moderador.darBajaUsuario(usuario));
        // Verifica que usuario ya no existe
        assertNull(gestorUsuarios.buscarUsuarioPorId("2"));
    }
    /**
     * Verifica que el método {@code modificarUsuario} actualice correctamente los datos del usuario,
     * en este caso el nombre. Se asegura de que no se lancen excepciones y de que el cambio se refleje en el sistema.
     */
    @Test
    void testModificarUsuario() {
        Usuario usuario = new Usuario("2", "Ana", "ana@correo.com", "pass",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>());
        moderador.darAltaUsuario(usuario);
        assertDoesNotThrow(() -> moderador.modificarUsuario(usuario, "Ana Actualizada"));
        // Verifica que nombre fue actualizado
        Usuario modificado = gestorUsuarios.buscarUsuarioPorId("2");
        assertEquals("Ana Actualizada", modificado.getNombre());
    }

}
