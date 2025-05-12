package co.edu.uniquindio.redsocial.models;

class Grafo<T> {
    private ListaEnlazada<NodoGrafo<T>> nodos = new ListaEnlazada<>();

    public Grafo(ListaEnlazada<NodoGrafo<T>> nodos) {
        this.nodos = nodos;
    }

    public void agregarNodo(T dato) {
        nodos.agregar(new NodoGrafo<>(dato));
    }

    public void agregarArista(T nodo1, T nodo2) { /* Lógica para agregar arista  TODO TORRES*/ }
    public ListaEnlazada<T> buscarRutaCorta(T origen, T destino) { return new ListaEnlazada<>(); }
    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() { return new ListaEnlazada<>(); }
    public void actualizarConexiones(T nodo1, T nodo2, double peso) { /* Actualizar pesos */ }

    // Getters y Setters
    public ListaEnlazada<NodoGrafo<T>> getNodos() { return nodos; }
    public void setNodos(ListaEnlazada<NodoGrafo<T>> nodos) { this.nodos = nodos; }
}

