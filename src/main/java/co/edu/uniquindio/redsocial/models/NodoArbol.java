package co.edu.uniquindio.redsocial.models;

/**
 * Nodo utilizado en el árbol binario de búsqueda.
 *
 * @param <K> Tipo de clave.
 * @param <V> Tipo de valor.
 */
public class NodoArbol<K extends Comparable<K>, V> {
    private K clave;
    private V valor;
    private NodoArbol<K, V> izquierdo;
    private NodoArbol<K, V> derecho;

    public NodoArbol(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() { return clave; }
    public void setClave(K clave) { this.clave = clave; }

    public V getValor() { return valor; }
    public void setValor(V valor) { this.valor = valor; }

    public NodoArbol<K, V> getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoArbol<K, V> izquierdo) { this.izquierdo = izquierdo; }

    public NodoArbol<K, V> getDerecho() { return derecho; }
    public void setDerecho(NodoArbol<K, V> derecho) { this.derecho = derecho; }
}

