package co.edu.uniquindio.redsocial.models;

class NodoLista<T> {
    private T dato;
    private NodoLista<T> siguiente;

    public NodoLista(T dato, NodoLista<T> siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }

    // Getters y Setters
    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }
    public NodoLista<T> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoLista<T> siguiente) { this.siguiente = siguiente; }
}
