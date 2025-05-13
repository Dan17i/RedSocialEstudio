package co.edu.uniquindio.redsocial.models;

import java.util.Iterator;

/**
 * Clase que representa una lista enlazada simple.
 *
 * @param <T> tipo de dato que contiene la lista
 */
public class ListaEnlazada<T> implements Iterable<T>{

    private NodoLista<T> cabeza;
    private int tamanio;

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public ListaEnlazada(NodoLista<T> cabeza, int tamanio) {
        this.cabeza = cabeza;
        this.tamanio = tamanio;
    }

    /**
     * Retorna el índice de un elemento si está presente.
     *
     * @param elemento el elemento a buscar
     * @return índice del elemento o -1 si no está
     */
    public int obtenerIndice(T elemento) {
        NodoLista<T> actual = cabeza;
        int indice = 0;
        while (actual != null) {
            if (actual.getDato().equals(elemento)) {
                return indice;
            }
            actual = actual.getSiguiente();
            indice++;
        }
        return -1;
    }

    /**
     * Reemplaza el dato en el índice especificado.
     *
     * @param indice posición a reemplazar
     * @param nuevoDato nuevo valor a establecer
     */
    public void set(int indice, T nuevoDato) {
        validarIndice(indice);
        NodoLista<T> actual = obtenerNodo(indice);
        actual.setDato(nuevoDato);
    }

    /**
     * Agrega un nuevo elemento al final de la lista.
     *
     * @param elemento dato a agregar
     */
    public void agregar(T elemento) {
        NodoLista<T> nuevo = new NodoLista<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoLista<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    /**
     * Elimina el nodo en la posición especificada.
     *
     * @param posicion índice del nodo a eliminar
     */
    public void eliminar(int posicion) {
        validarIndice(posicion);
        if (posicion == 0) {
            cabeza = cabeza.getSiguiente();
        } else {
            NodoLista<T> anterior = obtenerNodo(posicion - 1);
            anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
        }
        tamanio--;
    }

    /**
     * Verifica si un elemento está en la lista.
     *
     * @param elemento elemento a buscar
     * @return true si está, false si no
     */
    public boolean buscar(T elemento) {
        return contiene(elemento);
    }

    /**
     * Retorna el dato en una posición específica.
     *
     * @param posicion índice del dato
     * @return el dato, o null si el índice no es válido
     */
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) return null;
        return obtenerNodo(posicion).getDato();
    }

    /**
     * Verifica si la lista está vacía.
     *
     * @return true si está vacía
     */
    public boolean estaVacia() {
        return tamanio == 0;
    }

    /**
     * Verifica si la lista contiene un elemento.
     *
     * @param elemento el elemento a buscar
     * @return true si lo contiene
     */
    public boolean contiene(T elemento) {
        NodoLista<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * Retorna el nodo en una posición específica.
     *
     * @param index índice del nodo
     * @return nodo correspondiente
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    public NodoLista<T> obtenerNodo(int index) {
        validarIndice(index);
        NodoLista<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }

    /**
     * Inserta un dato en una posición específica de la lista.
     *
     * @param index posición de inserción
     * @param dato elemento a insertar
     */
    public void insertarEn(int index, T dato) {
        if (index < 0 || index > tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        NodoLista<T> nuevoNodo = new NodoLista<>(dato);

        if (index == 0) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
        } else {
            NodoLista<T> anterior = obtenerNodo(index - 1);
            nuevoNodo.setSiguiente(anterior.getSiguiente());
            anterior.setSiguiente(nuevoNodo);
        }

        tamanio++;
    }

    /**
     * Valida que el índice esté en el rango de la lista.
     *
     * @param indice índice a validar
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }

    /**
     * Invierte el orden de los elementos en la lista.
     * El nodo cabeza apunta ahora al último nodo original.
     */
    public void invertir() {
        NodoLista<T> anterior = null;
        NodoLista<T> actual = cabeza;
        NodoLista<T> siguiente;

        while (actual != null) {
            siguiente = actual.getSiguiente();     // Guardar el siguiente
            actual.setSiguiente(anterior);         // Invertir el puntero
            anterior = actual;                     // Mover anterior hacia adelante
            actual = siguiente;                    // Mover actual hacia adelante
        }

        cabeza = anterior;  // Actualizar la nueva cabeza
    }

    /**
     * Crea una copia profunda (clon) de la lista enlazada.
     *
     * @return Una nueva ListaEnlazada con los mismos elementos en el mismo orden.
     */
    public ListaEnlazada<T> clonar() {
        ListaEnlazada<T> clon = new ListaEnlazada<>();
        NodoLista<T> actual = cabeza;

        while (actual != null) {
            clon.agregar(actual.getDato());
            actual = actual.getSiguiente();
        }

        return clon;
    }

    /**
     * Retorna una sublista con los elementos desde el índice 'desde' (inclusive)
     * hasta el índice 'hasta' (exclusivo).
     *
     * @param desde Índice de inicio (inclusive)
     * @param hasta Índice de fin (exclusivo)
     * @return Sublista con los elementos del rango especificado
     * @throws IndexOutOfBoundsException si los índices no son válidos
     */
    public ListaEnlazada<T> sublista(int desde, int hasta) {
        if (desde < 0 || hasta > tamanio || desde >= hasta) {
            throw new IndexOutOfBoundsException("Rango inválido: desde=" + desde + ", hasta=" + hasta);
        }

        ListaEnlazada<T> sublista = new ListaEnlazada<>();
        NodoLista<T> actual = cabeza;
        int indice = 0;

        while (actual != null && indice < hasta) {
            if (indice >= desde) {
                sublista.agregar(actual.getDato());
            }
            actual = actual.getSiguiente();
            indice++;
        }

        return sublista;
    }
    /**
     * Crea un iterador para recorrer los elementos de la lista enlazada de manera secuencial.
     * Este método implementa la interfaz {@link Iterable} y devuelve un iterador que permite recorrer
     * los elementos de la lista, uno por uno, sin necesidad de acceder directamente a los nodos.
     *
     * @return Un iterador {@link Iterator} que permite recorrer los elementos de la lista enlazada.
     * El iterador devuelve cada elemento en orden desde el inicio hasta el final de la lista.
     *
     * @throws IllegalStateException Si se intenta llamar al método {@link Iterator#next()} cuando no hay más elementos
     * en la lista (es decir, si {@link Iterator#hasNext()} retorna {@code false}).
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NodoLista<T> actual = cabeza;  // NodoLista<T> es el tipo de nodo que contiene los elementos de la lista

            /**
             * Verifica si hay más elementos en la lista para iterar.
             * Este método devuelve {@code true} si el iterador puede avanzar al siguiente elemento,
             * es decir, si aún hay elementos en la lista.
             *
             * @return {@code true} si hay más elementos en la lista; {@code false} si se ha alcanzado el final de la lista.
             */
            @Override
            public boolean hasNext() {
                return actual != null;  // Si 'actual' es null, significa que hemos llegado al final de la lista
            }

            /**
             * Devuelve el siguiente elemento de la lista y avanza el iterador.
             * Este método obtiene el valor del nodo actual y mueve el iterador al siguiente nodo.
             *
             * @return El siguiente valor en la lista.
             *
             * @throws IllegalStateException Si se llama a este método cuando no hay más elementos en la lista.
             */
            @Override
            public T next() {
                if (hasNext()) {
                    T valor = actual.getDato();  // Se obtiene el valor del nodo actual
                    actual = actual.getSiguiente();  // Se mueve al siguiente nodo
                    return valor;
                }
                throw new IllegalStateException("No more elements");  // Si no hay más elementos, se lanza una excepción
            }
        };
    }



    // Getters y Setters
    public NodoLista<T> getCabeza() {
        return cabeza;
    }

    public void setCabeza(NodoLista<T> cabeza) {
        this.cabeza = cabeza;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public boolean isEmpty() {
        return cabeza == null;
    }
}
