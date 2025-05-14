package co.edu.uniquindio.redsocial.models.structures;

/**
 * Clase que representa un nodo en una lista enlazada.
 * Cada nodo contiene un dato de tipo T y una referencia al siguiente nodo de la lista.
 *
 * @param <T> Tipo de dato que almacena el nodo.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
class NodoLista<T> {
    private T dato;                // Valor almacenado en el nodo
    private NodoLista<T> siguiente; // Referencia al siguiente nodo de la lista

    /**
     * Constructor para crear un nodo con un dato y una referencia al siguiente nodo.
     *
     * @param dato     Valor almacenado en el nodo.
     * @param siguiente Nodo al que apunta el nodo actual.
     */
    public NodoLista(T dato, NodoLista<T> siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }

    /**
     * Constructor para crear un nodo con un dato, con el siguiente nodo como null.
     * Llama al constructor principal con null como siguiente.
     *
     * @param dato Valor almacenado en el nodo.
     */
    public NodoLista(T dato) {
        this(dato, null);  // Llama al constructor principal con null como siguiente
    }

    /**
     * Verifica si el nodo es el último de la lista.
     * Un nodo es el último si su siguiente es null.
     *
     * @return true si el nodo es el último, false si no lo es.
     */
    public boolean esUltimo() {
        return siguiente == null;
    }

    /**
     * Devuelve una representación en cadena del nodo y su dato.
     *
     * @return Cadena con la representación del nodo.
     */
    @Override
    public String toString() {
        return "Nodo{" + "dato=" + dato + '}';
    }

    // Getters y Setters
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoLista<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoLista<T> siguiente) {
        this.siguiente = siguiente;
    }
}
