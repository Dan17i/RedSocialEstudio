package co.edu.uniquindio.redsocial.models.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase que representa una lista enlazada simple genérica.
 * Permite operaciones como agregar, eliminar, buscar, obtener sublistas, clonar e iterar elementos.
 *
 * @param <T> Tipo de dato que contendrá la lista enlazada
 * @author Daniel
 * @author Sebastian
 * @author Juan
 * @since 2025-04-02
 */
public class ListaEnlazada<T> implements Iterable<T> {

    private NodoLista<T> cabeza;
    private int tamanio;

    /**
     * Constructor que inicializa una lista vacía.
     */
    public ListaEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    /**
     * Constructor que permite inicializar con una cabeza y tamaño específicos.
     * @param cabeza Nodo inicial de la lista
     * @param tamanio Número de elementos de la lista
     */
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
     * Reemplaza el elemento en la posición indicada con un nuevo dato.
     * Este método es equivalente al método set().
     *
     * @param index posición del nodo a actualizar.
     * @param dato nuevo dato para asignar.
     * @throws IndexOutOfBoundsException si el índice está fuera de rango.
     */
    public void put(int index, T dato) {
        set(index, dato);
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
     * Agrega un nuevo elemento al inicio de la lista enlazada.
     *
     * @param elemento el dato a insertar al inicio.
     */
    public void agregarInicio(T elemento) {
        NodoLista<T> nuevo = new NodoLista<>(elemento);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
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
     * Elimina la primera ocurrencia de un nodo que contenga el valor especificado.
     *
     * @param dato El dato a eliminar de la lista.
     * @return true si se eliminó correctamente; false si el dato no se encontró.
     */
    public boolean eliminar(T dato) {
        if (cabeza == null) return false;

        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
            return true;
        }

        NodoLista<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * Elimina el nodo en la posición especificada y retorna su dato.
     *
     * @param posicion índice del nodo a eliminar
     * @return Dato del nodo eliminado
     */
    public T eliminarEn(int posicion) {
        validarIndice(posicion);
        T dato;

        if (posicion == 0) {
            dato = cabeza.getDato();
            cabeza = cabeza.getSiguiente();
        } else {
            NodoLista<T> anterior = obtenerNodo(posicion - 1);
            NodoLista<T> nodoAEliminar = anterior.getSiguiente();
            dato = nodoAEliminar.getDato();
            anterior.setSiguiente(nodoAEliminar.getSiguiente());
        }

        tamanio--;
        return dato;
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
     * @return el dato
     * @throws IndexOutOfBoundsException si el índice no es válido
     */
    public T obtener(int posicion) {
        validarIndice(posicion);
        return obtenerNodo(posicion).getDato();
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
     * @throws IndexOutOfBoundsException si el índice es inválido
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
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    /**
     * Invierte el orden de los elementos en la lista.
     */
    public void invertir() {
        NodoLista<T> anterior = null;
        NodoLista<T> actual = cabeza;
        NodoLista<T> siguiente;

        while (actual != null) {
            siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }

        cabeza = anterior;
    }

    /**
     * Crea una copia profunda de la lista.
     *
     * @return Lista clonada
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
     * Retorna una sublista desde el índice 'desde' (inclusive) hasta 'hasta' (exclusivo).
     *
     * @param desde Índice de inicio
     * @param hasta Índice de fin (no incluido)
     * @return Sublista con los elementos deseados
     * @throws IndexOutOfBoundsException si los índices no son válidos
     */
    public ListaEnlazada<T> sublista(int desde, int hasta) {
        if (desde < 0 || hasta >= tamanio || desde > hasta) {
            throw new IndexOutOfBoundsException("Rango inválido: desde=" + desde + ", hasta=" + hasta);
        }

        ListaEnlazada<T> sublista = new ListaEnlazada<>();
        NodoLista<T> actual = cabeza;
        int indice = 0;

        while (indice < desde) {
            actual = actual.getSiguiente();
            indice++;
        }

        while (indice <= hasta && actual != null) {
            sublista.agregar(actual.getDato());
            actual = actual.getSiguiente();
            indice++;
        }

        return sublista;
    }

    /**
     * Retorna un iterador para recorrer secuencialmente los elementos de la lista.
     *
     * @return Iterador que implementa {@link Iterator}
     */
    @Override
    public Iterator<T> iterator() {
        return new ListaIterator();
    }

    /**
     * Clase privada que implementa un iterador interno para la lista enlazada.
     */
    private class ListaIterator implements Iterator<T> {

        private NodoLista<T> actual = cabeza;

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más elementos en la lista");
            }
            T dato = actual.getDato();
            actual = actual.getSiguiente();
            return dato;
        }
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

    /**
     * Verifica si la lista está vacía.
     *
     * @return true si no hay elementos
     */
    public boolean isEmpty() {
        return cabeza == null;
    }
}
