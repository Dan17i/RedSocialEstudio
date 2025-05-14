package co.edu.uniquindio.redsocial.models.structures;

import java.util.Objects;
/**
 * Clase que representa un nodo en una estructura de prioridad.
 * Cada nodo contiene un dato genérico, una prioridad numérica y una
 * referencia al siguiente nodo en la estructura.
 *
 * @param <T> Tipo del dato almacenado en el nodo.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class NodoPrioridad<T> implements Comparable<NodoPrioridad<T>> {

    private T dato;                        // Dato almacenado
    private int prioridad;                 // Prioridad del nodo
    private NodoPrioridad<T> siguiente;    // Enlace al siguiente nodo
    /**
     * Crea un nodo con el dato y la prioridad proporcionados.
     * La referencia al siguiente nodo se inicializa en null.
     *
     * @param dato      El valor que almacena el nodo (no puede ser null).
     * @param prioridad La prioridad asociada al nodo.
     * @throws IllegalArgumentException si el dato es null.
     */
    public NodoPrioridad(T dato, int prioridad) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser null");
        }
        this.dato = dato;
        this.prioridad = prioridad;
        this.siguiente = null;
    }
    /**
     * Verifica si el nodo es el último de la lista (no tiene siguiente).
     *
     * @return true si no hay siguiente nodo; false en caso contrario.
     */
    public boolean esUltimo() {
        return siguiente == null;
    }
    /**
     * Devuelve una representación textual del nodo.
     *
     * @return Cadena con el dato y su prioridad.
     */
    @Override
    public String toString() {
        return "NodoPrioridad{" + "dato=" + dato + ", prioridad=" + prioridad + '}';
    }
    /**
     * Compara este nodo con otro según la prioridad.
     * Una menor prioridad numérica indica mayor prioridad lógica.
     *
     * @param otro El nodo a comparar.
     * @return Un valor negativo si este nodo tiene mayor prioridad,
     *         cero si son iguales,
     *         positivo si este nodo tiene menor prioridad.
     */
    @Override
    public int compareTo(NodoPrioridad<T> otro) {
        return Integer.compare(this.prioridad, otro.prioridad);
    }

    // Getters y Setters

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser null");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NodoPrioridad)) return false;
        NodoPrioridad<?> otro = (NodoPrioridad<?>) obj;
        return prioridad == otro.prioridad && Objects.equals(dato, otro.dato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dato, prioridad);
    }
}
