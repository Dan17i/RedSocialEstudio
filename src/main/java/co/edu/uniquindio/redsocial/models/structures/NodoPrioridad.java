package co.edu.uniquindio.redsocial.models.structures;

/**
 * Clase que representa un nodo en una lista de prioridad.
 * Cada nodo contiene un dato de tipo T, una prioridad de tipo entero,
 * y una referencia al siguiente nodo de la lista.
 *
 * @param <T> Tipo de dato que almacena el nodo.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class NodoPrioridad<T> {
    private T dato;           // Valor almacenado en el nodo
    private int prioridad;    // Prioridad asociada al nodo
    private NodoPrioridad<T> siguiente; // Referencia al siguiente nodo en la lista

    /**
     * Constructor para crear un nodo con un dato, una prioridad,
     * y un siguiente nodo inicializado como null.
     *
     * @param dato     Valor almacenado en el nodo.
     * @param prioridad Prioridad asociada al nodo.
     */
    public NodoPrioridad(T dato, int prioridad) {
        this.dato = dato;
        this.prioridad = prioridad;
        this.siguiente = null;  // El siguiente nodo es null por defecto
    }

    /**
     * Verifica si el nodo es el último en la lista.
     * Un nodo es el último si su referencia 'siguiente' es null.
     *
     * @return true si el nodo es el último, false en caso contrario.
     */
    public boolean esUltimo() {
        return siguiente == null;
    }

    /**
     * Devuelve una representación en cadena del nodo y su prioridad.
     *
     * @return Cadena con la representación del nodo.
     */
    @Override
    public String toString() {
        return "NodoPrioridad{" + "dato=" + dato + ", prioridad=" + prioridad + '}';
    }

    // Getters y Setters
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public NodoPrioridad<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPrioridad<T> siguiente) {
        this.siguiente = siguiente;
    }
}
