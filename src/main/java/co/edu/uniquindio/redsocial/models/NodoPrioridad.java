package co.edu.uniquindio.redsocial.models;

public class NodoPrioridad<T> {
    private T dato;
    private int prioridad;
    private NodoPrioridad<T> siguiente;

    // Constructor
    public NodoPrioridad(T dato, int prioridad) {
        this.dato = dato;
        this.prioridad = prioridad;
        this.siguiente = null;
    }

    // Getters y setters
    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }
    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }
    public NodoPrioridad<T> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoPrioridad<T> siguiente) { this.siguiente = siguiente; }
}
