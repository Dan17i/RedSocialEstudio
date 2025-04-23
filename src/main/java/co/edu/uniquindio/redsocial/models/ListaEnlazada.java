package co.edu.uniquindio.redsocial.models;

public class ListaEnlazada<T> {

    NodoLista<T> cabeza;
    int tamanio;

    public ListaEnlazada() {
        cabeza = null;
        tamanio = 0;
    }

    public void agregar(T elemento) {

    }

    public void eliminar(int posicion) {

    }

    public boolean buscar(T elemento) {
        return false;
    }

    public T obtener(int posicion){

        return null;
    }

    public boolean estaVacia() {

        return cabeza == null;
    }

    public NodoLista<T> getCabeza() {
        return cabeza;
    }

    public void setCabeza(NodoLista<T> cabeza) {
        this.cabeza = cabeza;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
}
