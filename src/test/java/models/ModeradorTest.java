package models;

import co.edu.uniquindio.redsocial.models.*;
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

        moderador = new Moderador("1", "Carlos", "carlos@correo.com", "clave123",
                intereses, historial, valoraciones, true, areas);
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

        assertEquals(TipoReporte.CONTENIDOS_VALORADOS, // Cambiar si usas otro tipo para caminos cortos
                moderador.generarReporteCaminosMasCortos("A", "B").getTipo());

        assertEquals(TipoReporte.INFORME,
                moderador.generarReporteParticipacion().getTipo());
    }

    /**
     * Verifica que los métodos de gestión de usuarios se ejecutan sin errores.
     */
    @Test
    void testMetodosGestionUsuarios() {
        Usuario usuario = new Usuario("2", "Ana", "ana@correo.com", "pass",
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>());

        assertDoesNotThrow(() -> {
            moderador.darAltaUsuario(usuario);
            moderador.darBajaUsuario(usuario);
            moderador.modificarUsuario(usuario, "Ana Actualizada");
        });
    }
}
