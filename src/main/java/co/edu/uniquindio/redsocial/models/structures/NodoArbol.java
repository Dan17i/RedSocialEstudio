package co.edu.uniquindio.redsocial.models.structures;

/**
 * Nodo utilizado en el árbol binario de búsqueda.
 * Representa un nodo con una clave de tipo <K> y un valor de tipo <V>.
 * Los nodos se conectan con referencias a sus nodos izquierdo y derecho.
 *
 * @param <K> Tipo de clave, debe ser comparable.
 * @param <V> Tipo de valor.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class NodoArbol<K extends Comparable<K>, V> {
    private K clave;                   // Clave única asociada al nodo
    private V valor;                   // Valor asociado al nodo
    private NodoArbol<K, V> izquierdo;  // Nodo izquierdo del árbol
    private NodoArbol<K, V> derecho;    // Nodo derecho del árbol

    /**
     * Constructor que crea un nodo con la clave y el valor. Los nodos izquierdo y derecho se inicializan como null.
     *
     * @param clave Clave única del nodo.
     * @param valor Valor almacenado en el nodo.
     */
    public NodoArbol(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y Setters
    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public NodoArbol<K, V> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol<K, V> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbol<K, V> getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol<K, V> derecho) {
        this.derecho = derecho;
    }

    /**
     * Método que devuelve una representación en cadena del nodo.
     *
     * @return Cadena con la clave y el valor del nodo.
     */
    @Override
    public String toString() {
        return "NodoArbol{" +
                "clave=" + clave +
                ", valor=" + valor +
                '}';
    }

    /**
     * Método que determina si el nodo es una hoja (es decir, no tiene hijos).
     *
     * @return true si el nodo es una hoja, false si tiene al menos un hijo.
     */
    public boolean isLeaf() {
        return izquierdo == null && derecho == null;
    }
}
