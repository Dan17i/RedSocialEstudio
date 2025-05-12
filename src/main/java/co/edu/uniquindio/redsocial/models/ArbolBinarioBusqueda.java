package co.edu.uniquindio.redsocial.models;

class ArbolBinarioBusqueda<Contenido> {
    private NodoABB<Contenido> raiz;

    public ArbolBinarioBusqueda(NodoABB<Contenido> raiz) {
        this.raiz = raiz;
    }

    public void insertar(String clave, Contenido valor) { /* Lógica de inserción */ }
    public ListaEnlazada<Contenido> buscar(String clave) { return new ListaEnlazada<>(); }
    public ListaEnlazada<Contenido> listarContenidosPorTema(String tema) { return buscar(tema); }

    // Getters y Setters
    public NodoABB<Contenido> getRaiz() { return raiz; }
    public void setRaiz(NodoABB<Contenido> raiz) { this.raiz = raiz; }
}
