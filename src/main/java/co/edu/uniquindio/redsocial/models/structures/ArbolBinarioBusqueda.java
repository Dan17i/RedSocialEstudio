package co.edu.uniquindio.redsocial.models.structures;

import co.edu.uniquindio.redsocial.models.services.interf.Tematico;

/**
 * Clase que representa un Árbol Binario de Búsqueda (ABB) genérico,
 * utilizado para almacenar par clave-valor donde la clave es un String
 * y el valor implementa la interfaz Tematico.
 *
 * @param <T> Tipo de valor almacenado, debe implementar la interfaz Tematico.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 */
public class ArbolBinarioBusqueda<T extends Tematico> {

    private NodoABB<T> raiz;

    /**
     * Constructor con nodo raíz especificado.
     *
     * @param raiz Nodo raíz del árbol.
     */
    public ArbolBinarioBusqueda(NodoABB<T> raiz) {
        this.raiz = raiz;
    }

    /**
     * Constructor por defecto que inicializa el árbol vacío.
     */
    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    /**
     * Inserta un nuevo nodo con la clave y el valor dados en el árbol.
     * Si la clave ya existe, se actualiza el valor asociado.
     *
     * @param clave Clave del nodo a insertar.
     * @param valor Valor asociado a la clave.
     */
    public void insertar(String clave, T valor) {
        raiz = insertarRecursivo(raiz, clave, valor);
    }

    private NodoABB<T> insertarRecursivo(NodoABB<T> nodo, String clave, T valor) {
        if (nodo == null) {
            return new NodoABB<>(clave, valor);
        }

        int comparacion = clave.compareTo(nodo.getClave());

        if (comparacion < 0) {
            nodo.setIzquierda(insertarRecursivo(nodo.getIzquierda(), clave, valor));
        } else if (comparacion > 0) {
            nodo.setDerecha(insertarRecursivo(nodo.getDerecha(), clave, valor));
        } else {
            nodo.setValor(valor); // Actualiza el valor si la clave ya existe
        }

        return nodo;
    }

    /**
     * Busca el valor asociado a una clave en el árbol.
     *
     * @param clave Clave a buscar.
     * @return Valor asociado a la clave o null si no se encuentra.
     */
    public T buscar(String clave) {
        return buscarRecursivo(raiz, clave);
    }

    private T buscarRecursivo(NodoABB<T> nodo, String clave) {
        if (nodo == null) {
            return null;
        }

        int comparacion = clave.compareTo(nodo.getClave());

        if (comparacion < 0) {
            return buscarRecursivo(nodo.getIzquierda(), clave);
        } else if (comparacion > 0) {
            return buscarRecursivo(nodo.getDerecha(), clave);
        } else {
            return nodo.getValor();
        }
    }

    /**
     * Retorna una lista con todos los valores almacenados en el árbol
     * ordenados ascendentemente según su clave.
     *
     * @return ListaEnlazada de valores en orden ascendente.
     */
    public ListaEnlazada<T> listarTodos() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        listarEnOrden(raiz, lista);
        return lista;
    }

    private void listarEnOrden(NodoABB<T> nodo, ListaEnlazada<T> lista) {
        if (nodo != null) {
            listarEnOrden(nodo.getIzquierda(), lista);
            lista.agregar(nodo.getValor());
            listarEnOrden(nodo.getDerecha(), lista);
        }
    }

    /**
     * Lista todos los contenidos cuyo tema coincide con el tema dado.
     *
     * @param tema Tema a buscar.
     * @return Lista de contenidos que coinciden con el tema especificado.
     */
    public ListaEnlazada<T> listarContenidosPorTema(String tema) {
        ListaEnlazada<T> resultados = new ListaEnlazada<>();
        listarPorTemaRecursivo(raiz, tema, resultados);
        return resultados;
    }

    private void listarPorTemaRecursivo(NodoABB<T> nodo, String tema, ListaEnlazada<T> resultados) {
        if (nodo != null) {
            listarPorTemaRecursivo(nodo.getIzquierda(), tema, resultados);
            if (tema.equals(nodo.getValor().getTema())) {
                resultados.agregar(nodo.getValor());
            }
            listarPorTemaRecursivo(nodo.getDerecha(), tema, resultados);
        }
    }

    /**
     * Elimina un nodo con la clave especificada del árbol.
     *
     * @param clave Clave del nodo a eliminar.
     */
    public void eliminar(String clave) {
        raiz = eliminarRecursivo(raiz, clave);
    }

    private NodoABB<T> eliminarRecursivo(NodoABB<T> nodo, String clave) {
        if (nodo == null) {
            return null;
        }

        int comparacion = clave.compareTo(nodo.getClave());

        if (comparacion < 0) {
            nodo.setIzquierda(eliminarRecursivo(nodo.getIzquierda(), clave));
        } else if (comparacion > 0) {
            nodo.setDerecha(eliminarRecursivo(nodo.getDerecha(), clave));
        } else {
            if (nodo.getIzquierda() == null) {
                return nodo.getDerecha();
            } else if (nodo.getDerecha() == null) {
                return nodo.getIzquierda();
            }

            NodoABB<T> sucesor = encontrarMinimo(nodo.getDerecha());
            nodo.setClave(sucesor.getClave());
            nodo.setValor(sucesor.getValor());
            nodo.setDerecha(eliminarRecursivo(nodo.getDerecha(), sucesor.getClave()));
        }

        return nodo;
    }

    private NodoABB<T> encontrarMinimo(NodoABB<T> nodo) {
        while (nodo.getIzquierda() != null) {
            nodo = nodo.getIzquierda();
        }
        return nodo;
    }

    /**
     * Obtiene la raíz del árbol.
     *
     * @return Nodo raíz.
     */
    public NodoABB<T> getRaiz() {
        return raiz;
    }

    /**
     * Establece la raíz del árbol.
     *
     * @param raiz Nodo a establecer como raíz.
     */
    public void setRaiz(NodoABB<T> raiz) {
        this.raiz = raiz;
    }
}
