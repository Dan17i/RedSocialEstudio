package co.edu.uniquindio.redsocial.models;

public class NodoABB <T>{

    String clave;
    NodoABB<T> derecha;
    NodoABB<T> izquierda;
    T valor;

    public NodoABB(T valor, NodoABB<T> izquierda, NodoABB<T> derecha, String clave) {
        this.valor = valor;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public NodoABB<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoABB<T> derecha) {
        this.derecha = derecha;
    }

    public NodoABB<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoABB<T> izquierda) {
        this.izquierda = izquierda;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }
}
