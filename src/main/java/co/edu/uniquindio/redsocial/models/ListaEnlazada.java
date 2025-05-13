package co.edu.uniquindio.redsocial.models;

class ListaEnlazada<T> {

    private NodoLista<T> cabeza;
    private int tamanio;

    public ListaEnlazada() {
        cabeza = null;
        tamanio = 0;
    }

    public ListaEnlazada(NodoLista<T> cabeza, int tamanio) {
        this.cabeza = cabeza;
        this.tamanio = tamanio;
    }

    public void agregar(T elemento) {
        NodoLista<T> nuevo = new NodoLista<>(elemento, null);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoLista<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public void eliminar(int posicion) {
        if (posicion < 0 || posicion >= tamanio) return;
        if (posicion == 0) {
            cabeza = cabeza.getSiguiente();
        } else {
            NodoLista<T> actual = cabeza;
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
        }
        tamanio--;
    }

    public boolean buscar(T elemento) {
        NodoLista<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(elemento)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) return null;
        NodoLista<T> actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public boolean estaVacia() { return tamanio == 0; }

    // Getters y Setters (Torres)
    public NodoLista<T> getCabeza() { return cabeza; }
    public void setCabeza(NodoLista<T> cabeza) { this.cabeza = cabeza; }
    public int getTamanio() { return tamanio; }
    public void setTamanio(int tamanio) { this.tamanio = tamanio; }

    public boolean contiene(T estudiante) {

    }
}