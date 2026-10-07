package structures;

import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * En la cola de prioridad el número MENOR es el de MAYOR prioridad
 * (coherente con la interfaz: "Urgencia 1 alta - 10 baja").
 */
class ColaPrioridadTest {

    @Test
    void cola_nueva_esta_vacia_y_desencolar_devuelve_null() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        assertTrue(cola.estaVacia());
        assertNull(cola.desencolar());
    }

    @Test
    void desencolar_entrega_primero_el_de_menor_numero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.encolar("baja", 9);
        cola.encolar("urgente", 1);
        cola.encolar("media", 5);

        assertEquals("urgente", cola.desencolar());
        assertEquals("media", cola.desencolar());
        assertEquals("baja", cola.desencolar());
        assertTrue(cola.estaVacia());
    }

    @Test
    void con_igual_prioridad_se_respeta_el_orden_de_llegada() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.encolar("primero", 3);
        cola.encolar("segundo", 3);
        cola.encolar("tercero", 3);

        assertEquals("primero", cola.desencolar());
        assertEquals("segundo", cola.desencolar());
        assertEquals("tercero", cola.desencolar());
    }

    @Test
    void tamanio_se_actualiza_al_encolar_y_desencolar() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();
        cola.encolar(1, 2);
        cola.encolar(2, 1);
        assertEquals(2, cola.tamanio());
        cola.desencolar();
        assertEquals(1, cola.tamanio());
    }

    @Test
    void eliminar_quita_un_elemento_de_cualquier_posicion() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.encolar("a", 1);
        cola.encolar("b", 2);
        cola.encolar("c", 3);

        assertTrue(cola.eliminar("b"));
        assertFalse(cola.eliminar("z"));
        assertEquals(2, cola.tamanio());
        assertEquals("a", cola.desencolar());
        assertEquals("c", cola.desencolar());
    }

    @Test
    void aLista_devuelve_el_orden_de_atencion_sin_vaciar_la_cola() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.encolar("baja", 8);
        cola.encolar("alta", 1);

        assertEquals("alta", cola.aLista().obtener(0));
        assertEquals("baja", cola.aLista().obtener(1));
        assertEquals(2, cola.tamanio());
    }
}
