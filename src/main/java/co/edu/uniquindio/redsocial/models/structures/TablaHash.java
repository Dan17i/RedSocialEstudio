package co.edu.uniquindio.redsocial.models.structures;

import java.util.Objects;

/**
 * Tabla hash genérica propia (clave → valor) con resolución de colisiones por encadenamiento.
 * Reemplaza el uso de {@code java.util.HashMap} para respetar la restricción del proyecto
 * de utilizar únicamente estructuras de datos desarrolladas por los estudiantes.
 * Permite clave {@code null}. Redimensiona automáticamente al superar el factor de carga.
 *
 * @param <K> Tipo de la clave (debe implementar equals/hashCode de forma consistente).
 * @param <V> Tipo del valor.
 */
public class TablaHash<K, V> {

    private static final int CAPACIDAD_INICIAL = 16;
    private static final double FACTOR_CARGA = 0.75;

    /**
     * Par clave-valor almacenado en la tabla. También es un nodo de la cadena de colisiones.
     */
    public static class Entrada<K, V> {
        private final K clave;
        private V valor;
        private Entrada<K, V> siguiente;

        private Entrada(K clave, V valor, Entrada<K, V> siguiente) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = siguiente;
        }

        public K getClave() { return clave; }

        public V getValor() { return valor; }
    }

    private Entrada<K, V>[] tabla;
    private int tamanio;

    public TablaHash() {
        tabla = crearArreglo(CAPACIDAD_INICIAL);
        tamanio = 0;
    }

    @SuppressWarnings("unchecked")
    private Entrada<K, V>[] crearArreglo(int capacidad) {
        return (Entrada<K, V>[]) new Entrada[capacidad];
    }

    private int indice(Object clave, int capacidad) {
        return (Objects.hashCode(clave) & 0x7fffffff) % capacidad;
    }

    private Entrada<K, V> buscarEntrada(Object clave) {
        Entrada<K, V> actual = tabla[indice(clave, tabla.length)];
        while (actual != null) {
            if (Objects.equals(actual.clave, clave)) return actual;
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Asocia el valor con la clave. Si la clave ya existía, reemplaza su valor.
     *
     * @return El valor anterior asociado a la clave, o null si no existía.
     */
    public V poner(K clave, V valor) {
        Entrada<K, V> existente = buscarEntrada(clave);
        if (existente != null) {
            V anterior = existente.valor;
            existente.valor = valor;
            return anterior;
        }
        if (tamanio + 1 > tabla.length * FACTOR_CARGA) {
            redimensionar();
        }
        int i = indice(clave, tabla.length);
        tabla[i] = new Entrada<>(clave, valor, tabla[i]);
        tamanio++;
        return null;
    }

    /**
     * Asocia el valor con la clave solo si la clave no existe todavía.
     *
     * @return true si se insertó, false si la clave ya existía.
     */
    public boolean ponerSiAusente(K clave, V valor) {
        if (contieneClave(clave)) return false;
        poner(clave, valor);
        return true;
    }

    /** @return El valor asociado a la clave, o null si no existe. */
    public V obtener(K clave) {
        Entrada<K, V> entrada = buscarEntrada(clave);
        return entrada == null ? null : entrada.valor;
    }

    /** @return El valor asociado a la clave, o {@code predeterminado} si no existe. */
    public V obtenerOPredeterminado(K clave, V predeterminado) {
        Entrada<K, V> entrada = buscarEntrada(clave);
        return entrada == null ? predeterminado : entrada.valor;
    }

    public boolean contieneClave(K clave) {
        return buscarEntrada(clave) != null;
    }

    /**
     * Elimina la clave de la tabla.
     *
     * @return El valor que tenía asociado, o null si no existía.
     */
    public V eliminar(K clave) {
        int i = indice(clave, tabla.length);
        Entrada<K, V> anterior = null;
        Entrada<K, V> actual = tabla[i];
        while (actual != null) {
            if (Objects.equals(actual.clave, clave)) {
                if (anterior == null) tabla[i] = actual.siguiente;
                else anterior.siguiente = actual.siguiente;
                tamanio--;
                return actual.valor;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return null;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public void limpiar() {
        tabla = crearArreglo(CAPACIDAD_INICIAL);
        tamanio = 0;
    }

    /** @return Lista enlazada con todas las claves (sin orden garantizado). */
    public ListaEnlazada<K> claves() {
        ListaEnlazada<K> resultado = new ListaEnlazada<>();
        for (Entrada<K, V> entrada : entradas()) resultado.agregar(entrada.clave);
        return resultado;
    }

    /** @return Lista enlazada con todos los valores (sin orden garantizado). */
    public ListaEnlazada<V> valores() {
        ListaEnlazada<V> resultado = new ListaEnlazada<>();
        for (Entrada<K, V> entrada : entradas()) resultado.agregar(entrada.valor);
        return resultado;
    }

    /** @return Lista enlazada con todas las entradas clave-valor (sin orden garantizado). */
    public ListaEnlazada<Entrada<K, V>> entradas() {
        ListaEnlazada<Entrada<K, V>> resultado = new ListaEnlazada<>();
        for (Entrada<K, V> cabeza : tabla) {
            for (Entrada<K, V> actual = cabeza; actual != null; actual = actual.siguiente) {
                resultado.agregar(actual);
            }
        }
        return resultado;
    }

    private void redimensionar() {
        Entrada<K, V>[] vieja = tabla;
        tabla = crearArreglo(vieja.length * 2);
        for (Entrada<K, V> cabeza : vieja) {
            Entrada<K, V> actual = cabeza;
            while (actual != null) {
                Entrada<K, V> siguiente = actual.siguiente;
                int i = indice(actual.clave, tabla.length);
                actual.siguiente = tabla[i];
                tabla[i] = actual;
                actual = siguiente;
            }
        }
    }
}
