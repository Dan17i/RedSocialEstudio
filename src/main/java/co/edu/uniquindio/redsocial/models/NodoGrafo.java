package co.edu.uniquindio.redsocial.models;

class NodoGrafo<T> {
    private T dato;
    private ListaEnlazada<NodoGrafo<T>> adyacentes;
    private ListaEnlazada<Double> pesos;

    public NodoGrafo(T dato) {
        this.dato = dato;
        this.adyacentes = new ListaEnlazada<>();
        this.pesos = new ListaEnlazada<>();
    }

    // Método para agregar un nodo adyacente y su peso
    public void agregarAdyacente(NodoGrafo<T> nodo, Double peso) {
        adyacentes.agregar(nodo);
        pesos.agregar(peso);
    }

    /**
     * Actualiza el peso de la arista entre el nodo actual y el nodo adyacente.
     *
     * @param nodo Nodo adyacente al nodo actual.
     * @param peso Nuevo peso de la arista entre el nodo actual y el nodo adyacente.
     */
    public void actualizarPeso(NodoGrafo<T> nodo, double peso) {
        // Obtener el índice del nodo adyacente
        int indice = adyacentes.obtenerIndice(nodo);
        if (indice != -1) {  // Si el nodo adyacente existe en la lista
            pesos.set(indice, peso);  // Actualizamos el peso en la lista de pesos
        }
    }

    // Getters y Setters
    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }
    public ListaEnlazada<NodoGrafo<T>> getAdyacentes() { return adyacentes; }
    public void setAdyacentes(ListaEnlazada<NodoGrafo<T>> adyacentes) { this.adyacentes = adyacentes; }
    public ListaEnlazada<Double> getPesos() { return pesos; }
    public void setPesos(ListaEnlazada<Double> pesos) { this.pesos = pesos; }
}
