package co.edu.uniquindio.redsocial.models.structures;
/**
 * Clase que representa un nodo genérico en una lista enlazada.
 * Un nodo contiene un dato de tipo genérico y una referencia al siguiente nodo en la lista.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 * @param <T> El tipo de dato que almacena el nodo. Este tipo es genérico, lo que significa que
 *            puede ser cualquier tipo de objeto.
 *
 **/
public class Nodo<T> {

    // Atributos
    private T dato;              // Dato almacenado en el nodo
    private Nodo<T> siguiente;    // Referencia al siguiente nodo en la lista
    /**
     * Constructor que inicializa un nodo con el dato proporcionado y la referencia siguiente a null.
     *
     * @param dato El dato a almacenar en el nodo.
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
    /**
     * Obtiene el dato almacenado en el nodo.
     *
     * @return El dato almacenado en el nodo.
     */
    public T getDato() {
        return dato;
    }
    /**
     * Establece un nuevo dato para el nodo.
     *
     * @param dato El nuevo dato a almacenar en el nodo.
     */
    public void setDato(T dato) {
        this.dato = dato;
    }
    /**
     * Obtiene la referencia al siguiente nodo en la lista.
     *
     * @return El siguiente nodo en la lista, o null si este nodo es el último.
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }
    /**
     * Establece el siguiente nodo en la lista.
     *
     * @param siguiente El siguiente nodo en la lista.
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}

