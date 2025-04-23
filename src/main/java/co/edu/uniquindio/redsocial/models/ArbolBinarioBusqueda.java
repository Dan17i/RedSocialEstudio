package co.edu.uniquindio.redsocial.models;

public class ArbolBinarioBusqueda <T>{

    NodoABB<T> raiz;

    public ArbolBinarioBusqueda(NodoABB<T> raiz) {
        this.raiz = raiz;
    }

    public NodoABB<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoABB<T> raiz) {
        this.raiz = raiz;
    }

    public  void insertar(String clave, T valor){

    }

    public ListaEnlazada<T> buscar(String clave){

        return null;
    }

    public ListaEnlazada<Contenido> listarContenidosPorTema(String tema){
        return null;
    }
}
