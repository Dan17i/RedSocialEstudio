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

    public void agregarAdyacente(NodoGrafo<T> nodo, Double peso) {
        adyacentes.agregar(nodo);
        pesos.agregar(peso);
    }

    // Getters y Setters
    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }
    public ListaEnlazada<NodoGrafo<T>> getAdyacentes() { return adyacentes; }
    public void setAdyacentes(ListaEnlazada<NodoGrafo<T>> adyacentes) { this.adyacentes = adyacentes; }
    public ListaEnlazada<Double> getPesos() { return pesos; }
    public void setPesos(ListaEnlazada<Double> pesos) { this.pesos = pesos; }
}
