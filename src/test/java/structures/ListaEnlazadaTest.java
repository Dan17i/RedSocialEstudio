package structures;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListaEnlazadaTest {

    private ListaEnlazada<Integer> lista(int... valores) {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        for (int v : valores) l.agregar(v);
        return l;
    }

    @Test
    void lista_nueva_esta_vacia() {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        assertTrue(l.isEmpty());
        assertEquals(0, l.getTamanio());
    }

    @Test
    void agregar_mantiene_el_orden_de_insercion() {
        ListaEnlazada<Integer> l = lista(1, 2, 3);
        assertEquals(3, l.getTamanio());
        assertEquals(1, l.obtener(0));
        assertEquals(3, l.obtener(2));
    }

    @Test
    void agregarInicio_inserta_como_primer_elemento() {
        ListaEnlazada<Integer> l = lista(2, 3);
        l.agregarInicio(1);
        assertEquals(1, l.obtener(0));
        assertEquals(3, l.getTamanio());
    }

    @Test
    void insertarEn_permite_insertar_al_inicio_en_medio_y_al_final() {
        ListaEnlazada<Integer> l = lista(2, 4);
        l.insertarEn(0, 1);
        l.insertarEn(2, 3);
        l.insertarEn(4, 5);
        for (int i = 0; i < 5; i++) assertEquals(i + 1, l.obtener(i));
        assertThrows(IndexOutOfBoundsException.class, () -> l.insertarEn(7, 9));
    }

    @Test
    void obtener_con_indice_invalido_lanza_excepcion() {
        ListaEnlazada<Integer> l = lista(1);
        assertThrows(IndexOutOfBoundsException.class, () -> l.obtener(1));
        assertThrows(IndexOutOfBoundsException.class, () -> l.obtener(-1));
    }

    @Test
    void eliminar_por_posicion_y_por_dato() {
        ListaEnlazada<Integer> l = lista(1, 2, 3, 4);
        l.eliminar(0);
        assertTrue(l.eliminar(Integer.valueOf(3)));
        assertFalse(l.eliminar(Integer.valueOf(99)));
        assertEquals(2, l.getTamanio());
        assertEquals(2, l.obtener(0));
        assertEquals(4, l.obtener(1));
    }

    @Test
    void eliminarEn_devuelve_el_elemento_quitado() {
        ListaEnlazada<Integer> l = lista(10, 20, 30);
        assertEquals(20, l.eliminarEn(1));
        assertEquals(30, l.eliminarEn(1));
        assertEquals(10, l.eliminarEn(0));
        assertTrue(l.isEmpty());
    }

    @Test
    void contiene_y_obtenerIndice() {
        ListaEnlazada<Integer> l = lista(5, 6, 7);
        assertTrue(l.contiene(6));
        assertFalse(l.contiene(8));
        assertEquals(2, l.obtenerIndice(7));
        assertEquals(-1, l.obtenerIndice(8));
    }

    @Test
    void invertir_da_vuelta_el_orden() {
        ListaEnlazada<Integer> l = lista(1, 2, 3);
        l.invertir();
        assertEquals(3, l.obtener(0));
        assertEquals(1, l.obtener(2));
        assertEquals(3, l.getTamanio());
    }

    @Test
    void ordenar_usa_el_comparador() {
        ListaEnlazada<Integer> l = lista(3, 1, 4, 1, 5, 9, 2, 6);
        l.ordenar(Integer::compare);
        int[] esperado = {1, 1, 2, 3, 4, 5, 6, 9};
        for (int i = 0; i < esperado.length; i++) assertEquals(esperado[i], l.obtener(i));
    }

    @Test
    void clonar_genera_una_lista_independiente() {
        ListaEnlazada<Integer> l = lista(1, 2);
        ListaEnlazada<Integer> clon = l.clonar();
        clon.agregar(3);
        assertEquals(2, l.getTamanio());
        assertEquals(3, clon.getTamanio());
    }

    @Test
    void sublista_devuelve_el_rango_inclusivo() {
        ListaEnlazada<Integer> sub = lista(1, 2, 3, 4, 5).sublista(1, 3);
        assertEquals(3, sub.getTamanio());
        assertEquals(2, sub.obtener(0));
        assertEquals(4, sub.obtener(2));
        assertThrows(IndexOutOfBoundsException.class, () -> lista(1, 2).sublista(0, 5));
    }

    @Test
    void iterador_recorre_todos_los_elementos() {
        int suma = 0;
        for (int v : lista(1, 2, 3)) suma += v;
        assertEquals(6, suma);
    }

    @Test
    void hayInterseccion_detecta_elementos_comunes() {
        assertTrue(lista(1, 2, 3).hayInterseccion(lista(3, 4)));
        assertFalse(lista(1, 2).hayInterseccion(lista(3, 4)));
    }
}
