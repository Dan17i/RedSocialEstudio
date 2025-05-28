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
    /**
     * Inserta recursivamente un nodo con la clave y valor especificados en el árbol binario de búsqueda.
     * Si la clave ya existe, actualiza el valor asociado a esa clave.
     *
     * @param nodo El nodo actual desde donde se inicia o continúa la inserción.
     *             Si es null, se crea un nuevo nodo con la clave y valor dados.
     * @param clave La clave que identifica al nodo a insertar o actualizar.
     * @param valor El valor asociado a la clave que se va a insertar o actualizar.
     *
     * @return El nodo actualizado después de la inserción o actualización.
     */
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
    /**
     * Busca recursivamente un valor en el árbol binario de búsqueda basado en la clave proporcionada.
     *
     * @param nodo El nodo actual desde donde se inicia o continúa la búsqueda. Si es null, significa que
     *             el valor no se encontró en el árbol y se retorna null.
     * @param clave La clave que se busca en el árbol. Se utiliza para comparar con las claves de los nodos.
     *
     * @return El valor asociado a la clave si se encuentra en el árbol; de lo contrario, retorna null.
     */
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
    /**
     * Realiza un recorrido en orden (inorden) del árbol binario de búsqueda
     * comenzando desde el nodo especificado, y agrega los valores de los nodos
     * visitados a la lista proporcionada.
     *
     * @param nodo El nodo actual desde donde se inicia o continúa el recorrido en orden.
     *             Si es null, la función no realiza ninguna acción.
     * @param lista La lista enlazada donde se almacenan los valores de los nodos
     *              en orden ascendente.
     */
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
    /**
     * Realiza un recorrido en orden del árbol binario de búsqueda y agrega a la lista
     * los nodos cuyo valor coincida con el tema especificado.
     *
     * @param nodo El nodo actual desde donde se inicia o continúa el recorrido.
     *             Si es null, no se realiza ninguna acción.
     * @param tema El tema que se utiliza para filtrar los valores de los nodos.
     * @param resultados La lista enlazada donde se almacenan los valores que coinciden con el tema.
     */
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
    /**
     * Elimina recursivamente un nodo del árbol binario de búsqueda que coincide con la clave dada.
     *
     * @param nodo El nodo actual desde donde se inicia o continúa la eliminación.
     *             Si es null, significa que no se encontró el nodo a eliminar y retorna null.
     * @param clave La clave del nodo que se desea eliminar del árbol.
     *
     * @return El nodo actualizado después de realizar la eliminación, que puede ser un nuevo subárbol
     *         o null si el nodo fue eliminado y no tenía hijos.
     */
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
    /**
     * Busca el nodo con la clave mínima en el subárbol dado.
     *
     * @param nodo El nodo raíz del subárbol en el cual se buscará el nodo con la clave mínima.
     *             Se asume que el nodo no es null.
     *
     * @return El nodo que contiene la clave mínima dentro del subárbol.
     */
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
