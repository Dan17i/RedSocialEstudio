package co.edu.uniquindio.redsocial.models.structures;

/**
 * Clase que representa un nodo en un Árbol Binario de Búsqueda (ABB).
 * Cada nodo contiene una clave de tipo String, un valor de tipo genérico T,
 * y dos referencias a sus nodos izquierdo y derecho.
 *
 * @param <T> Tipo de datos que almacena el valor del nodo.
 */
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

    public NodoABB(String clave, T valor) {
        this(clave, valor, null, null);
    }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public T getValor() { return valor; }
    public void setValor(T valor) { this.valor = valor; }

    public NodoABB<T> getIzquierda() { return izquierda; }
    public void setIzquierda(NodoABB<T> izquierda) { this.izquierda = izquierda; }

    public NodoABB<T> getDerecha() { return derecha; }
    public void setDerecha(NodoABB<T> derecha) { this.derecha = derecha; }
    /**
     * Verifica si el nodo no tiene hijos.
     *
     * @return true si el nodo es una hoja, false en caso contrario.
     */
    public boolean isLeaf() {
        return izquierda == null && derecha == null;
    }
    /**
     * Verifica si el nodo tiene exactamente un hijo.
     *
     * @return true si tiene solo un hijo, false si tiene ambos o ninguno.
     */
    public boolean hasOnlyOneChild() {
        return (izquierda == null && derecha != null) || (izquierda != null && derecha == null);
    }
    /**
     * Retorna el único hijo del nodo si tiene uno.
     *
     * @return Referencia al único hijo o null si tiene ambos o ninguno.
     */
    public NodoABB<T> getOnlyChild() {
        if (izquierda != null && derecha == null) return izquierda;
        if (derecha != null && izquierda == null) return derecha;
        return null;
    }
    @Override
    public String toString() {
        return "NodoABB{" +
                "clave='" + clave + '\'' +
                ", valor=" + valor +
                '}';
    }
}
