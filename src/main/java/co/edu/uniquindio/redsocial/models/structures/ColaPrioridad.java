package co.edu.uniquindio.redsocial.models.structures;

import co.edu.uniquindio.redsocial.models.Estudiante;

/**
 * Cola de prioridad basada en lista enlazada.
 * Los elementos se almacenan como NodoPrioridad<T> y se insertan
 * ordenadamente de acuerdo a su prioridad (menor valor = mayor prioridad).
 *
 * @param <T> Tipo de dato almacenado en la cola.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class ColaPrioridad<T> {

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
     * @param dato      Dato a encolar.
     * @param prioridad Prioridad del dato (a menor número, mayor prioridad).
     */
    public void encolar(T dato, int prioridad) {
        NodoPrioridad<T> nuevoNodo = new NodoPrioridad<>(dato, prioridad);
        int indice = 0;

        // Se inserta en la posición que mantiene el orden por prioridad ascendente.
        while (indice < elementos.getTamanio() &&
                elementos.obtener(indice).getPrioridad() <= prioridad) {
            indice++;
        }

        elementos.insertarEn(indice, nuevoNodo);
    }

    /**
     * Desencola y devuelve el dato con mayor prioridad.
     *
     * @return Dato del nodo con mayor prioridad o null si la cola está vacía.
     */
    public T desencolar() {
        if (elementos.isEmpty()) {
            return null;
        }
        NodoPrioridad<T> nodo = elementos.eliminarEn(0);  // Necesita método eliminarEn en ListaEnlazada
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
     * @return Tamaño de la lista enlazada.
     */
    public int tamanio() {
        return elementos.getTamanio();
    }

    /**
     * Retorna la lista completa de nodos con prioridad.
     *
     * @return Lista enlazada de NodoPrioridad<T>.
     */
    public ListaEnlazada<NodoPrioridad<T>> getElementos() {
        return elementos;
    }

    public void setElementos(ListaEnlazada<NodoPrioridad<T>> elementos) {
        this.elementos = elementos;
    }

}
