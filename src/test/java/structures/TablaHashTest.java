package structures;

import co.edu.uniquindio.redsocial.models.structures.ConjuntoHash;
import co.edu.uniquindio.redsocial.models.structures.TablaHash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TablaHashTest {

    @Test
    void poner_y_obtener_devuelven_el_valor_asociado() {
        TablaHash<String, Integer> tabla = new TablaHash<>();
        assertNull(tabla.poner("a", 1));
        assertEquals(1, tabla.obtener("a"));
        assertNull(tabla.obtener("z"));
        assertEquals(1, tabla.tamanio());
    }

    @Test
    void poner_con_clave_existente_reemplaza_y_devuelve_el_anterior() {
        TablaHash<String, Integer> tabla = new TablaHash<>();
        tabla.poner("a", 1);
        assertEquals(1, tabla.poner("a", 2));
        assertEquals(2, tabla.obtener("a"));
        assertEquals(1, tabla.tamanio());
    }

    @Test
    void ponerSiAusente_no_sobrescribe() {
        TablaHash<String, Integer> tabla = new TablaHash<>();
        assertTrue(tabla.ponerSiAusente("a", 1));
        assertFalse(tabla.ponerSiAusente("a", 2));
        assertEquals(1, tabla.obtener("a"));
    }

    @Test
    void eliminar_quita_la_clave_y_ajusta_el_tamanio() {
        TablaHash<String, Integer> tabla = new TablaHash<>();
        tabla.poner("a", 1);
        tabla.poner("b", 2);
        assertEquals(1, tabla.eliminar("a"));
        assertNull(tabla.eliminar("a"));
        assertFalse(tabla.contieneClave("a"));
        assertEquals(1, tabla.tamanio());
    }

    @Test
    void soporta_muchos_elementos_tras_redimensionar() {
        TablaHash<Integer, Integer> tabla = new TablaHash<>();
        for (int i = 0; i < 1000; i++) tabla.poner(i, i * 2);
        assertEquals(1000, tabla.tamanio());
        for (int i = 0; i < 1000; i++) assertEquals(i * 2, tabla.obtener(i));
        assertEquals(1000, tabla.claves().getTamanio());
        assertEquals(1000, tabla.valores().getTamanio());
    }

    @Test
    void obtenerOPredeterminado_y_clave_nula() {
        TablaHash<String, Integer> tabla = new TablaHash<>();
        assertEquals(7, tabla.obtenerOPredeterminado("x", 7));
        tabla.poner(null, 5);
        assertEquals(5, tabla.obtener(null));
    }

    @Test
    void conjunto_no_permite_repetidos() {
        ConjuntoHash<String> conjunto = new ConjuntoHash<>();
        assertTrue(conjunto.agregar("a"));
        assertFalse(conjunto.agregar("a"));
        assertTrue(conjunto.contiene("a"));
        assertEquals(1, conjunto.tamanio());
        assertTrue(conjunto.eliminar("a"));
        assertFalse(conjunto.eliminar("a"));
        assertTrue(conjunto.estaVacio());
    }
}
