package co.edu.uniquindio.redsocial.models;

class NodoLista<T> {
    private T dato;
    private NodoLista<T> siguiente;

    // Constructor para nodo con dato y siguiente
    public NodoLista(T dato, NodoLista<T> siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }

    // Constructor para nodo con solo dato (siguiente será null)
    public NodoLista(T dato) {
        this(dato, null);  // Llama al constructor principal con null como siguiente
    }

    // Getters y Setters
    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }
    public NodoLista<T> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoLista<T> siguiente) { this.siguiente = siguiente; }
}
