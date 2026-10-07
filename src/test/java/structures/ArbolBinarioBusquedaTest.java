package structures;

import co.edu.uniquindio.redsocial.models.services.interf.Tematico;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArbolBinarioBusquedaTest {

    /** Elemento mínimo para probar el árbol. */
    private static class Item implements Tematico {
        private final String tema;

        Item(String tema) {
            this.tema = tema;
        }

        @Override
        public String getTema() {
            return tema;
        }
    }

    private ArbolBinarioBusqueda<Item> arbolConTemas(String... temas) {
        ArbolBinarioBusqueda<Item> arbol = new ArbolBinarioBusqueda<>();
        for (String t : temas) arbol.insertar(t, new Item(t));
        return arbol;
    }

    @Test
    void arbol_vacio_no_encuentra_nada() {
        ArbolBinarioBusqueda<Item> arbol = new ArbolBinarioBusqueda<>();
        assertNull(arbol.buscar("x"));
        assertTrue(arbol.listarTodos().isEmpty());
    }

    @Test
    void insertar_y_buscar_por_clave() {
        ArbolBinarioBusqueda<Item> arbol = arbolConTemas("m", "c", "x");
        assertEquals("c", arbol.buscar("c").getTema());
        assertNull(arbol.buscar("z"));
    }

    @Test
    void listarTodos_devuelve_los_elementos_ordenados_por_clave() {
        ListaEnlazada<Item> lista = arbolConTemas("m", "c", "x", "a", "p").listarTodos();
        String[] esperado = {"a", "c", "m", "p", "x"};
        assertEquals(esperado.length, lista.getTamanio());
        for (int i = 0; i < esperado.length; i++) assertEquals(esperado[i], lista.obtener(i).getTema());
    }

    @Test
    void eliminar_hoja_nodo_con_un_hijo_y_nodo_con_dos_hijos() {
        ArbolBinarioBusqueda<Item> arbol = arbolConTemas("m", "c", "x", "a", "d", "p");
        arbol.eliminar("a");   // hoja
        arbol.eliminar("x");   // un hijo (p)
        arbol.eliminar("c");   // un hijo (d)
        arbol.eliminar("m");   // raíz con dos hijos

        assertNull(arbol.buscar("m"));
        ListaEnlazada<Item> resto = arbol.listarTodos();
        assertEquals(2, resto.getTamanio());
        assertEquals("d", resto.obtener(0).getTema());
        assertEquals("p", resto.obtener(1).getTema());
    }

    @Test
    void eliminar_clave_inexistente_no_cambia_el_arbol() {
        ArbolBinarioBusqueda<Item> arbol = arbolConTemas("a", "b");
        arbol.eliminar("z");
        assertEquals(2, arbol.listarTodos().getTamanio());
    }

    @Test
    void listarContenidosPorTema_filtra_por_el_tema_del_elemento() {
        ArbolBinarioBusqueda<Item> arbol = new ArbolBinarioBusqueda<>();
        arbol.insertar("k1", new Item("java"));
        arbol.insertar("k2", new Item("python"));
        arbol.insertar("k3", new Item("java"));
        assertEquals(2, arbol.listarContenidosPorTema("java").getTamanio());
    }

    @Test
    void varios_elementos_con_el_mismo_tema_se_conservan_todos() {
        ArbolBinarioBusqueda<Item> arbol = new ArbolBinarioBusqueda<>();
        arbol.insertar("java", new Item("java"));
        arbol.insertar("java", new Item("java"));
        arbol.insertar("java", new Item("java"));

        assertEquals(3, arbol.listarTodos().getTamanio());
        assertEquals(3, arbol.listarContenidosPorTema("java").getTamanio());
    }

    @Test
    void eliminarValor_quita_solo_el_elemento_indicado() {
        ArbolBinarioBusqueda<Item> arbol = new ArbolBinarioBusqueda<>();
        Item uno = new Item("java");
        Item dos = new Item("java");
        Item tres = new Item("java");
        arbol.insertar("java", uno);
        arbol.insertar("java", dos);
        arbol.insertar("java", tres);

        arbol.eliminarValor("java", dos);

        ListaEnlazada<Item> resto = arbol.listarTodos();
        assertEquals(2, resto.getTamanio());
        assertSame(uno, resto.obtener(0));
        assertSame(tres, resto.obtener(1));
    }
}
