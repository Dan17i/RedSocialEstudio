package structures;

import co.edu.uniquindio.redsocial.models.structures.GrafoNoDirigido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrafoNoDirigidoTest {

    @Test
    void agregarArista_crea_los_nodos_y_es_simetrica() {
        GrafoNoDirigido<String> g = new GrafoNoDirigido<>();
        g.agregarArista("A", "B");
        assertEquals(2, g.tamano());
        assertTrue(g.obtenerVecinos("A").contiene("B"));
        assertTrue(g.obtenerVecinos("B").contiene("A"));
    }

    @Test
    void arista_repetida_no_duplica_vecinos() {
        GrafoNoDirigido<String> g = new GrafoNoDirigido<>();
        g.agregarArista("A", "B");
        g.agregarArista("A", "B");
        assertEquals(1, g.obtenerVecinos("A").getTamanio());
    }

    @Test
    void obtenerVecinos_de_nodo_inexistente_devuelve_lista_vacia() {
        assertTrue(new GrafoNoDirigido<String>().obtenerVecinos("X").isEmpty());
    }

    @Test
    void detectarComunidades_separa_componentes_conexos() {
        GrafoNoDirigido<String> g = new GrafoNoDirigido<>();
        g.agregarArista("A", "B");
        g.agregarArista("B", "C");
        g.agregarArista("D", "E");
        g.agregarNodo("F");
        ListaEnlazada<ListaEnlazada<String>> comunidades = g.detectarComunidades();
        assertEquals(3, comunidades.getTamanio());
    }

    @Test
    void buscarRutaCorta_prefiere_el_camino_de_menor_peso() {
        GrafoNoDirigido<String> g = new GrafoNoDirigido<>();
        g.agregarArista("A", "B", 1);
        g.agregarArista("B", "C", 1);
        g.agregarArista("A", "C", 5);
        ListaEnlazada<String> ruta = g.buscarRutaCorta("A", "C");
        assertEquals(3, ruta.getTamanio());
        assertEquals("A", ruta.obtener(0));
        assertEquals("B", ruta.obtener(1));
        assertEquals("C", ruta.obtener(2));
    }

    @Test
    void buscarRutaCorta_sin_camino_devuelve_null() {
        GrafoNoDirigido<String> g = new GrafoNoDirigido<>();
        g.agregarNodo("A");
        g.agregarNodo("B");
        assertNull(g.buscarRutaCorta("A", "B"));
    }

    @Test
    void eliminarNodo_quita_las_aristas_hacia_el() {
        GrafoNoDirigido<String> g = new GrafoNoDirigido<>();
        g.agregarArista("A", "B");
        assertTrue(g.eliminarNodo("B"));
        assertTrue(g.obtenerVecinos("A").isEmpty());
        assertEquals(1, g.tamano());
    }

    @Test
    void buscarRutaCorta_sin_pesos_minimiza_el_numero_de_saltos() {
        GrafoNoDirigido<String> g = new GrafoNoDirigido<>();
        // Camino largo A-B-C-D y atajo A-E-D (declarado después).
        g.agregarArista("A", "B");
        g.agregarArista("B", "C");
        g.agregarArista("C", "D");
        g.agregarArista("A", "E");
        g.agregarArista("E", "D");
        ListaEnlazada<String> ruta = g.buscarRutaCorta("A", "D");
        assertEquals(3, ruta.getTamanio());
        assertEquals("E", ruta.obtener(1));
    }
}
