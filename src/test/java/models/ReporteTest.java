package models;


import co.edu.uniquindio.redsocial.models.Reporte;
import co.edu.uniquindio.redsocial.models.TipoReporte;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoArbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReporteTest {

    private Reporte<NodoArbol<String, Integer>> reporte;

    @BeforeEach
    void setUp() {
        ListaEnlazada<NodoArbol<String, Integer>> datos = new ListaEnlazada<>();
        datos.agregar(new NodoArbol<>("Contenido1", 10));
        datos.agregar(new NodoArbol<>("Contenido2", 8));

        reporte = new Reporte<>("R001", TipoReporte.CONTENIDOS_VALORADOS, LocalDateTime.now(), datos);
    }

    @Test
    void testCrearReporteExitosamente() {
        assertEquals("R001", reporte.getId());
        assertEquals(TipoReporte.CONTENIDOS_VALORADOS, reporte.getTipo());
        assertEquals(2, reporte.getDatos().getTamanio());
    }

    @Test
    void testAgregarDato() {
        NodoArbol<String, Integer> nuevo = new NodoArbol<>("Contenido3", 5);
        reporte.agregarDato(nuevo);
        assertEquals(3, reporte.getDatos().getTamanio());
    }

    @Test
    void testGenerarContenidoNoEsVacio() {
        String contenido = reporte.generarContenido();
        assertTrue(contenido.contains("Contenido1"));
        assertTrue(contenido.contains("Contenido2"));
    }

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

    @Test
    void testExportarFormatoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> reporte.exportar("PDF", "reporte.pdf"));
    }

    @Test
    void testSetIdInvalido() {
        assertThrows(IllegalArgumentException.class, () -> reporte.setId(""));
        assertThrows(IllegalArgumentException.class, () -> reporte.setId(null));
    }

    @Test
    void testSetTipoNull() {
        assertThrows(IllegalArgumentException.class, () -> reporte.setTipo(null));
    }

    @Test
    void testConstructorConDatosNulosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reporte<>("R002", TipoReporte.CONTENIDOS_VALORADOS, LocalDateTime.now(), null)
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Reporte<>("R003", TipoReporte.CONTENIDOS_VALORADOS, null, new ListaEnlazada<>())
        );
    }

    @Test
    void testAgregarDatoNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> reporte.agregarDato(null));
    }
}

