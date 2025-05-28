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
    /**
     * Constructor que crea un nodo ABB con la clave y valor especificados,
     * sin hijos izquierdo ni derecho.
     *
     * @param clave La clave asociada al nodo.
     * @param valor El valor almacenado en el nodo.
     */
    public NodoABB(String clave, T valor) {
        this(clave, valor, null, null);
    }
    /**
     * Obtiene la clave asociada a este nodo.
     *
     * @return La clave del nodo.
     */
    public String getClave() { return clave; }
    /**
     * Establece la clave asociada a este nodo.
     *
     * @param clave La nueva clave a asignar al nodo.
     */
    public void setClave(String clave) { this.clave = clave; }
    /**
     * Obtiene el valor almacenado en este nodo.
     *
     * @return El valor del nodo.
     */
    public T getValor() { return valor; }
    /**
     * Establece el valor almacenado en este nodo.
     *
     * @param valor El nuevo valor a asignar al nodo.
     */
    public void setValor(T valor) { this.valor = valor; }
    /**
     * Obtiene el nodo hijo izquierdo de este nodo.
     *
     * @return El nodo hijo izquierdo, o null si no existe.
     */
    public NodoABB<T> getIzquierda() { return izquierda; }
    /**
     * Establece el nodo hijo izquierdo de este nodo.
     *
     * @param izquierda El nuevo nodo hijo izquierdo.
     */
    public void setIzquierda(NodoABB<T> izquierda) { this.izquierda = izquierda; }
    /**
     * Obtiene el nodo hijo derecho de este nodo.
     *
     * @return El nodo hijo derecho, o null si no existe.
     */
    public NodoABB<T> getDerecha() { return derecha; }
    /**
     * Establece el nodo hijo derecho de este nodo.
     *
     * @param derecha El nuevo nodo hijo derecho.
     */
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
