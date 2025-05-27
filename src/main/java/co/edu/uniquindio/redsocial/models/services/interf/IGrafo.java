package co.edu.uniquindio.redsocial.models.services.interf;

import java.util.List;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoGrafo;

/**
 * Interfaz que define las operaciones básicas para un grafo genérico.
 * Un grafo es una estructura compuesta por nodos y aristas que los unen.
 *
 * @param <T> Tipo genérico de los nodos del grafo.
 *
 * @author Daniel Jurado
 * @since 2025-05-24
 */
public interface IGrafo<T> {

    /**
     * Agrega un nodo al grafo.
     * @param dato El dato del nodo
     * @throws IllegalArgumentException si el dato es null.
     */
    void agregarNodo(T dato);

    /**
     * Agrega una arista con un peso específico entre dos nodos.
     * @param nodo1 Nodo de origen.
     * @param nodo2 Nodo de destino.
     * @param peso Peso de la arista (debe ser >= 0).
     * @throws IllegalArgumentException si nodos no existen o el peso es inválido.
     */
    void agregarArista(T nodo1, T nodo2, double peso);

    /**
     * Agrega una arista sin peso (peso por defecto 0.0).
     * @param nodo1 Nodo de origen.
     * @param nodo2 Nodo de destino.
     */
    void agregarArista(T nodo1, T nodo2);

    /**
     * Elimina un nodo y todas sus conexiones.
     * @param dato Dato del nodo.
     * @return true si fue eliminado, false si no existe.
     */
    boolean eliminarNodo(T dato);

    /**
     * Elimina una arista entre dos nodos.
     * @param nodo1 Nodo origen.
     * @param nodo2 Nodo destino.
     * @return true si la arista fue eliminada, false si no existía.
     */
    boolean eliminarArista(T nodo1, T nodo2);

    /**
     * Obtiene la ruta más corta entre dos nodos usando el algoritmo de Dijkstra.
     * @param origen Nodo inicial.
     * @param destino Nodo final.
     * @return Lista con la ruta más corta o null si no existe.
     * @throws IllegalArgumentException si los nodos no existen o son null.
     */
    ListaEnlazada<T> buscarRutaCorta(T origen, T destino);

    /**
     * Obtiene el nodo asociado a un dato.
     * @param dato Dato del nodo.
     * @return Nodo correspondiente o null si no existe.
     */
    NodoGrafo<T> obtenerNodo(T dato);

    /**
     * Obtiene todos los nodos del grafo.
     * @return Lista enlazada con todos los nodos.
     */
    ListaEnlazada<NodoGrafo<T>> obtenerNodos();

    /**
     * Indica si el grafo es dirigido.
     * @return true si es dirigido, false si es no dirigido.
     */
    boolean esDirigido();

    /**
     * Obtiene el número de nodos del grafo.
     * @return Tamaño del grafo.
     */
    int tamano();

    /**
     * Detecta comunidades (componentes conexas) dentro del grafo.
     * @return Lista de comunidades, cada una representada como lista de nodos.
     */
    ListaEnlazada<ListaEnlazada<T>> detectarComunidades();
}

