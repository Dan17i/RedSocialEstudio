package co.edu.uniquindio.redsocial.models.structures;

import co.edu.uniquindio.redsocial.models.services.interf.Tematico;

/**
 * Clase que representa un Árbol Binario de Búsqueda (ABB) genérico,
 * utilizado para almacenar pares clave-valor donde la clave es un String
 * y el valor es de tipo genérico Contenido.
 *
 * @param <T> Tipo de valor.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @param <T> El tipo de los valores almacenados en el árbol.
 */
public class ArbolBinarioBusqueda<T extends Tematico> {

    private NodoABB<T> raiz;

    public ArbolBinarioBusqueda(NodoABB<T> raiz) {
        this.raiz = raiz;
    }

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    /**
     * Inserta un nuevo nodo en el árbol con la clave y el valor dados.
     * Si la clave ya existe, se actualiza el valor.
     *
     * @param clave Clave del nodo a insertar.
     * @param valor Valor asociado a la clave.
     */
    public void insertar(String clave, T valor) {
        raiz = insertarRecursivo(raiz, clave, valor);
    }

    private NodoABB<T> insertarRecursivo(NodoABB<T> nodo, String clave, T valor) {
        if (nodo == null) {
            return new NodoABB<>(clave, valor); // Crear nuevo nodo si llegamos a una hoja nula
        }

        int comparacion = clave.compareTo(nodo.getClave());

        if (comparacion < 0) {
            nodo.setIzquierda(insertarRecursivo(nodo.getIzquierda(), clave, valor));
        } else if (comparacion > 0) {
            nodo.setDerecha(insertarRecursivo(nodo.getDerecha(), clave, valor));
        } else {
            // Si la clave ya existe, se actualiza el valor (opcional)
            nodo.setValor(valor);
        }

        return nodo;
    }

    /**
     * Busca el valor asociado a la clave dada.
     * Si la clave existe, retorna el valor; de lo contrario, retorna null.
     *
     * @param clave Clave a buscar.
     * @return Valor asociado a la clave, o null si no se encuentra.
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
            return nodo.getValor(); // Clave encontrada, retornar el valor
        }
    }

    /**
     * Listar todos los valores del árbol en orden ascendente de claves.
     *
     * @return Lista de valores almacenados en el árbol.
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
     * Busca todos los contenidos que tienen el tema especificado y los devuelve en una lista enlazada.
     * Este método recurre al método `listarPorTemaRecursivo` para recorrer el árbol binario
     * y agregar los contenidos cuyo tema coincida con el proporcionado.
     *
     * @param tema El tema a buscar en los contenidos.
     * @return Lista enlazada de contenidos que coinciden con el tema dado. Si no se encuentran
     *         contenidos con ese tema, se devuelve una lista vacía.
     */
    public ListaEnlazada<T> listarContenidosPorTema(String tema) {
        ListaEnlazada<T> resultados = new ListaEnlazada<>();
        listarPorTemaRecursivo(raiz, tema, resultados);  // Llama al método recursivo
        return resultados;
    }

    /**
     * Método recursivo que recorre el árbol binario de búsqueda en orden, y agrega los contenidos
     * cuyo tema coincide con el tema proporcionado en la lista de resultados.
     *
     * @param nodo El nodo actual del árbol a procesar.
     * @param tema El tema a buscar en los contenidos.
     * @param resultados La lista donde se agregan los contenidos cuyo tema coincide con el dado.
     */
    private void listarPorTemaRecursivo(NodoABB<T> nodo, String tema, ListaEnlazada<T> resultados) {
        if (nodo != null) {
            if (nodo.getValor().getTema().equals(tema)) {
                resultados.agregar(nodo.getValor());
            }
            listarPorTemaRecursivo(nodo.getIzquierda(), tema, resultados);
            listarPorTemaRecursivo(nodo.getDerecha(), tema, resultados);
        }
    }


    /**
     * Elimina un nodo con la clave dada del árbol.
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
            // Nodo encontrado
            if (nodo.getIzquierda() == null) {
                return nodo.getDerecha();
            } else if (nodo.getDerecha() == null) {
                return nodo.getIzquierda();
            }

            // Nodo con dos hijos
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

    public NodoABB<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoABB<T> raiz) {
        this.raiz = raiz;
    }
}
