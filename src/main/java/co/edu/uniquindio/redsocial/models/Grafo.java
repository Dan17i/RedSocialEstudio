package co.edu.uniquindio.redsocial.models;

public class Grafo<T> {

    ListaEnlazada<NodoGrafo<T>> nodos;

    public Grafo(ListaEnlazada<NodoGrafo<T>> nodos) {
        this.nodos = nodos;
    }

    public void agregarArista(T nodo1, T nodo2) {

    }

    public ListaEnlazada<T> buscarRutaCorta(T origen, T destino) {

        return null;
    }

    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() {
        return null;
    }

    public void agregarNodo(T dato){

    }

    public ListaEnlazada<NodoGrafo<T>> getNodos() {
        return nodos;
    }

    public void setNodos(ListaEnlazada<NodoGrafo<T>> nodos) {
        this.nodos = nodos;
    }
}
