package co.edu.uniquindio.redsocial.models;

public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;

    // Constructor
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    // Getters y Setters
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}

