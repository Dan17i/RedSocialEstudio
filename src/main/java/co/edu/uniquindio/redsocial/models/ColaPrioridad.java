package co.edu.uniquindio.redsocial.models;

class ColaPrioridad<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();

    public ColaPrioridad(ListaEnlazada<T> elementos) {
        this.elementos = elementos;
    }

    public void encolar(T elemento, int prioridad) { /* Lógica de encolado con prioridad TODO TORRES */ }
    public T desencolar() { return elementos.obtener(0); } // Simplificado
    public ListaEnlazada<T> getElementos() { return elementos; }
    public void setElementos(ListaEnlazada<T> elementos) { this.elementos = elementos; }
}
