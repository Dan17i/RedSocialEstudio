package co.edu.uniquindio.redsocial.models;
/**
 * Clase que representa un nodo en un Árbol Binario de Búsqueda (ABB).
 * Cada nodo contiene una clave de tipo String, un valor de tipo genérico T,
 * y dos referencias a sus nodos izquierdo y derecho.
 *
 * @param <T> Tipo de datos que almacena el valor del nodo.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
class NodoABB<T> {
    private String clave;          // Clave única asociada al nodo
    private T valor;               // Valor asociado al nodo
    private NodoABB<T> izquierda;  // Nodo izquierdo del árbol
    private NodoABB<T> derecha;    // Nodo derecho del árbol

    /**
     * Constructor que crea un nodo con la clave, valor y referencias a los nodos izquierdo y derecho.
     *
     * @param clave     Clave única del nodo
     * @param valor     Valor almacenado en el nodo
     * @param izquierda Nodo izquierdo del árbol
     * @param derecha   Nodo derecho del árbol
     */
    public NodoABB(String clave, T valor, NodoABB<T> izquierda, NodoABB<T> derecha) {
        this.clave = clave;
        this.valor = valor;
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    /**
     * Constructor que crea un nodo con la clave y el valor, sin referencias a los nodos izquierdo y derecho.
     * Los nodos izquierdo y derecho se inicializan como null.
     *
     * @param clave     Clave única del nodo
     * @param valor     Valor almacenado en el nodo
     */
    public NodoABB(String clave, T valor) {
        this(clave, valor, null, null);  // Reutiliza el otro constructor
    }

    // Getters y Setters

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public NodoABB<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoABB<T> izquierda) {
        this.izquierda = izquierda;
    }

    public NodoABB<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoABB<T> derecha) {
        this.derecha = derecha;
    }

    /**
     * Método que devuelve una representación en cadena del nodo.
     *
     * @return Cadena con la clave y el valor del nodo.
     */
    @Override
    public String toString() {
        return "NodoABB{" +
                "clave='" + clave + '\'' +
                ", valor=" + valor +
                '}';
    }

    /**
     * Método que determina si el nodo es una hoja (no tiene hijos).
     *
     * @return true si el nodo es una hoja, false si tiene al menos un hijo.
     */
    public boolean isLeaf() {
        return izquierda == null && derecha == null;
    }
}
