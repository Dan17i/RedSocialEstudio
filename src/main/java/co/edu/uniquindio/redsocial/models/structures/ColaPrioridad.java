package co.edu.uniquindio.redsocial.models.structures;


/**
 * Cola de prioridad basada en lista enlazada.
 * Los elementos se insertan ordenadamente según su prioridad (menor valor = mayor prioridad).
 * Se preserva el orden de inserción para prioridades iguales (estabilidad).
 *
 * <p><b>Nota:</b> Esta implementación tiene un rendimiento O(n) en inserción y eliminación,
 * debido al uso de una lista enlazada. Para cargas elevadas, podría considerarse una estructura
 * más eficiente como un heap binario (O(log n)).</p>
 *
 * @param <T> Tipo de dato almacenado en la cola.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class ColaPrioridad<T> {

    /**
     * Lista enlazada de nodos con prioridad.
     * Se asume que ListaEnlazada implementa métodos eficientes y correctos.
     */
    private ListaEnlazada<NodoPrioridad<T>> elementos = new ListaEnlazada<>();

    /**
     * Constructor por defecto.
     */
    public ColaPrioridad() {
    }

    /**
     * Constructor que permite iniciar la cola con una lista enlazada existente.
     *
     * @param elementos Lista enlazada de nodos de prioridad.
     */
    public ColaPrioridad(ListaEnlazada<NodoPrioridad<T>> elementos) {
        this.elementos = elementos;
    }

    /**
     * Encola un nuevo dato según su nivel de prioridad.
     *
     * <p>Complejidad: O(n) en el peor caso debido al recorrido secuencial
     * de la lista para encontrar la posición correcta de inserción.</p>
     *
     * <p>Los elementos con igual prioridad se colocan después de los previamente insertados
     * con esa misma prioridad, manteniendo la estabilidad.</p>
     *
     * @param dato      Dato a encolar.
     * @param prioridad Prioridad del dato (a menor número, mayor prioridad).
     */
    public void encolar(T dato, int prioridad) {
        NodoPrioridad<T> nuevoNodo = new NodoPrioridad<>(dato, prioridad);
        int indice = 0;

        // Se encuentra la posición correcta manteniendo el orden por prioridad ascendente.
        while (indice < elementos.getTamanio() &&
                elementos.obtener(indice).getPrioridad() <= prioridad) {
            indice++;
        }

        // Inserta en la posición encontrada.
        elementos.insertarEn(indice, nuevoNodo);
    }

    /**
     * Desencola y devuelve el dato con mayor prioridad.
     *
     * <p>Complejidad: O(1) si eliminarEn(0) es constante. O(n) si implica recorrido.</p>
     *
     * @return Dato del nodo con mayor prioridad o null si la cola está vacía.
     */
    public T desencolar() {
        if (elementos.isEmpty()) {
            return null;
        }
        NodoPrioridad<T> nodo = elementos.eliminarEn(0);
        return nodo.getDato();
    }

    /**
     * Verifica si la cola está vacía.
     *
     * @return true si no hay elementos, false si hay al menos uno.
     */
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    /**
     * Retorna el número de elementos en la cola.
     *
     * @return Tamaño actual de la cola.
     */
    public int tamanio() {
        return elementos.getTamanio();
    }

    /**
     * Elimina de la cola el primer elemento igual (equals) al indicado, sin importar su posición.
     *
     * @param dato Elemento a quitar.
     * @return true si se encontró y se eliminó; false si no estaba en la cola.
     */
    public boolean eliminar(T dato) {
        for (int i = 0; i < elementos.getTamanio(); i++) {
            if (elementos.obtener(i).getDato().equals(dato)) {
                elementos.eliminarEn(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve los elementos en el orden en que serían atendidos (mayor prioridad primero),
     * sin modificar la cola.
     *
     * @return Nueva lista con los datos de la cola.
     */
    public ListaEnlazada<T> aLista() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        for (NodoPrioridad<T> nodo : elementos) {
            lista.agregar(nodo.getDato());
        }
        return lista;
    }

    /**
     * Retorna la lista enlazada interna de nodos con prioridad.
     *
     * @return Lista enlazada de NodoPrioridad<T>.
     */
    public ListaEnlazada<NodoPrioridad<T>> getElementos() {
        return elementos;
    }

    /**
     * Establece una nueva lista enlazada como base de la cola.
     *
     * @param elementos Lista enlazada a establecer.
     */
    public void setElementos(ListaEnlazada<NodoPrioridad<T>> elementos) {
        this.elementos = elementos;
    }
}
