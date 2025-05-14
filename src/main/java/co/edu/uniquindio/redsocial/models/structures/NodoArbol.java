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
    private K clave;
    private V valor;
    private NodoArbol<K, V> izquierdo;
    private NodoArbol<K, V> derecho;

    public NodoArbol(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y Setters
    public K getClave() { return clave; }
    public void setClave(K clave) { this.clave = clave; }

    public V getValor() { return valor; }
    public void setValor(V valor) { this.valor = valor; }

    public NodoArbol<K, V> getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoArbol<K, V> izquierdo) { this.izquierdo = izquierdo; }

    public NodoArbol<K, V> getDerecho() { return derecho; }
    public void setDerecho(NodoArbol<K, V> derecho) { this.derecho = derecho; }
    /**
     * Determina si el nodo es una hoja (no tiene hijos).
     *
     * @return true si el nodo no tiene hijos, false en caso contrario.
     */
    public boolean isLeaf() {
        return izquierdo == null && derecho == null;
    }
    /**
     * Verifica si el nodo tiene exactamente un hijo.
     *
     * @return true si tiene un solo hijo, false si tiene ambos o ninguno.
     */
    public boolean hasOnlyOneChild() {
        return (izquierdo == null && derecho != null) || (izquierdo != null && derecho == null);
    }
    /**
     * Devuelve el único hijo del nodo si tiene uno.
     *
     * @return Nodo hijo si tiene solo uno; null si tiene ambos o ninguno.
     */
    public NodoArbol<K, V> getOnlyChild() {
        if (izquierdo != null && derecho == null) return izquierdo;
        if (derecho != null && izquierdo == null) return derecho;
        return null;
    }
    @Override
    public String toString() {
        return "NodoArbol{" +
                "clave=" + clave +
                ", valor=" + valor +
                '}';
    }
}
