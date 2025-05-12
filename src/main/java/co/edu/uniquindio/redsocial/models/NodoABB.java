package co.edu.uniquindio.redsocial.models;

// Clase NodoABB
class NodoABB<T> {
    private String clave;
    private T valor;
    private NodoABB<T> izquierda;
    private NodoABB<T> derecha;

    public NodoABB(String clave, T valor, NodoABB<T> izquierda, NodoABB<T> derecha) {
        this.clave = clave;
        this.valor = valor;
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    // Getters y Setters
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public T getValor() { return valor; }
    public void setValor(T valor) { this.valor = valor; }
    public NodoABB<T> getIzquierda() { return izquierda; }
    public void setIzquierda(NodoABB<T> izquierda) { this.izquierda = izquierda; }
    public NodoABB<T> getDerecha() { return derecha; }
    public void setDerecha(NodoABB<T> derecha) { this.derecha = derecha; }
}