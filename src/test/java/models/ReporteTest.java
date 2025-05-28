package models;


import co.edu.uniquindio.redsocial.models.Reporte;
import co.edu.uniquindio.redsocial.models.Enums.TipoReporte;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoArbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de pruebas unitarias para {@link Reporte}.
 * <p>
 * Esta clase valida el comportamiento del modelo {@link Reporte}, asegurando que:
 * <ul>
 *     <li>Se pueda crear un reporte con datos válidos.</li>
 *     <li>Se puedan agregar datos al reporte correctamente.</li>
 *     <li>La generación del contenido textual sea coherente con los datos incluidos.</li>
 *     <li>El reporte pueda exportarse a formatos válidos como TXT y CSV.</li>
 *     <li>Se manejen apropiadamente errores al utilizar formatos inválidos o valores nulos.</li>
 * </ul>
 * <p>
 * Utiliza nodos del tipo {@link NodoArbol NodoArbol&lt;String, Integer&gt;} como datos del reporte,
 * y estructuras personalizadas como {@link ListaEnlazada}.
 *
 * @author Daneil Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 * @since 2025-05-27
 */
public class ReporteTest {
    /**
     * Reporte de prueba que contiene nodos con identificadores y valores enteros.
     */
    private Reporte<NodoArbol<String, Integer>> reporte;
    /**
     * Inicializa un reporte antes de cada prueba con dos elementos de prueba.
     */
    @BeforeEach
    void setUp() {
        ListaEnlazada<NodoArbol<String, Integer>> datos = new ListaEnlazada<>();
        datos.agregar(new NodoArbol<>("Contenido1", 10));
        datos.agregar(new NodoArbol<>("Contenido2", 8));

        reporte = new Reporte<>("R001", TipoReporte.CONTENIDOS_VALORADOS, LocalDateTime.now(), datos);
    }
    /**
     * Verifica que un reporte se cree correctamente con los valores iniciales esperados.
     */
    @Test
    void testCrearReporteExitosamente() {
        assertEquals("R001", reporte.getId());
        assertEquals(TipoReporte.CONTENIDOS_VALORADOS, reporte.getTipo());
        assertEquals(2, reporte.getDatos().getTamanio());
    }
    /**
     * Verifica que se pueda agregar un nuevo dato al reporte y que el tamaño se actualice.
     */
    @Test
    void testAgregarDato() {
        NodoArbol<String, Integer> nuevo = new NodoArbol<>("Contenido3", 5);
        reporte.agregarDato(nuevo);
        assertEquals(3, reporte.getDatos().getTamanio());
    }
    /**
     * Verifica que el contenido generado por el reporte incluya correctamente
     * los datos insertados.
     */
    @Test
    void testGenerarContenidoNoEsVacio() {
        String contenido = reporte.generarContenido();
        assertTrue(contenido.contains("Contenido1"));
        assertTrue(contenido.contains("Contenido2"));
    }
    /**
     * Verifica que el reporte pueda exportarse correctamente en formato TXT.
     * <p>
     * También comprueba que el archivo se haya creado y contiene los datos esperados.
     */
    @Test
    void testExportarTXT() throws IOException {
        String ruta = "reporte_test.txt";
        reporte.exportar("TXT", ruta);

        File archivo = new File(ruta);
        assertTrue(archivo.exists());

        String contenido = Files.readString(archivo.toPath());
        assertTrue(contenido.contains("Contenido1"));

        archivo.delete();
    }
    /**
     * Verifica que el reporte pueda exportarse correctamente en formato CSV.
     * <p>
     * También comprueba que el archivo se haya creado y contiene los datos esperados.
     */
    @Test
    void testExportarCSV() throws IOException {
        String ruta = "reporte_test.csv";
        reporte.exportar("CSV", ruta);

        File archivo = new File(ruta);
        assertTrue(archivo.exists());

        String contenido = Files.readString(archivo.toPath());
        assertTrue(contenido.contains("Contenido1"));

        archivo.delete();
    }
    /**
     * Verifica que al intentar exportar el reporte a un formato no soportado,
     * se lance una excepción de tipo {@link IllegalArgumentException}.
     */
    @Test
    void testExportarFormatoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> reporte.exportar("PDF", "reporte.pdf"));
    }
    /**
     * Verifica que al establecer un ID inválido (vacío o null) se lance una excepción.
     */
    @Test
    void testSetIdInvalido() {
        assertThrows(IllegalArgumentException.class, () -> reporte.setId(""));
        assertThrows(IllegalArgumentException.class, () -> reporte.setId(null));
    }
    /**
     * Verifica que al establecer un tipo de reporte null se lance una excepción.
     */
    @Test
    void testSetTipoNull() {
        assertThrows(IllegalArgumentException.class, () -> reporte.setTipo(null));
    }
    /**
     * Verifica que al construir un reporte con datos nulos o fecha nula
     * se lance una excepción apropiada.
     */
    @Test
    void testConstructorConDatosNulosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reporte<>("R002", TipoReporte.CONTENIDOS_VALORADOS, LocalDateTime.now(), null)
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Reporte<>("R003", TipoReporte.CONTENIDOS_VALORADOS, null, new ListaEnlazada<>())
        );
    }
    /**
     * Verifica que al intentar agregar un dato nulo al reporte se lance una excepción.
     */
    @Test
    void testAgregarDatoNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> reporte.agregarDato(null));
    }
}

