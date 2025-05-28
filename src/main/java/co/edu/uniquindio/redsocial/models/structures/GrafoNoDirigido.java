package co.edu.uniquindio.redsocial.models.structures;

import java.util.*;

/**
 * Representa un grafo no dirigido genérico.
 * @author Daniel Jurado Sebasstian Torres y Juan Soto
 * @param <T> Tipo del nodo en el grafo.
 */
public class GrafoNoDirigido<T> extends GrafoImpl<T> {

    /**
     * Mapa que asocia cada nodo con su lista de vecinos adyacentes.
     */
    private final Map<T, List<T>> listaAdyacencia;

    /**
     * Constructor que inicializa un grafo no dirigido vacío.
     */
    public GrafoNoDirigido() {
        this.listaAdyacencia = new HashMap<>();
    }

    /**
     * Agrega un nodo al grafo. Si ya existe, no hace nada.
     *
     * @param nodo Nodo a agregar.
     */
    public void agregarNodo(T nodo) {
        listaAdyacencia.putIfAbsent(nodo, new ArrayList<>());
    }

    /**
     * Agrega una arista no dirigida entre dos nodos.
     * Si los nodos no existen, los crea.
     *
     * @param nodo1 Primer nodo.
     * @param nodo2 Segundo nodo.
     */
    public void agregarArista(T nodo1, T nodo2) {
        agregarNodo(nodo1);
        agregarNodo(nodo2);
        listaAdyacencia.get(nodo1).add(nodo2);
        listaAdyacencia.get(nodo2).add(nodo1);
    }

    /**
     * Obtiene los vecinos adyacentes de un nodo.
     *
     * @param nodo Nodo cuyo vecinos se buscan.
     * @return Lista de vecinos, o lista vacía si el nodo no existe.
     */
    public List<T> obtenerVecinos(T nodo) {
        return listaAdyacencia.getOrDefault(nodo, Collections.emptyList());
    }

    /**
     * Detecta las comunidades o componentes conexos del grafo no dirigido.
     * Cada comunidad es una lista de nodos conectados.
     *
     * @return Lista de comunidades (listas de nodos).
     */
    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() {
        ListaEnlazada<ListaEnlazada<T>> comunidades = new ListaEnlazada<>();
        Set<T> visitados = new HashSet<>();

        for (T nodo : listaAdyacencia.keySet()) {
            if (!visitados.contains(nodo)) {
                ListaEnlazada<T> comunidad = new ListaEnlazada<>();
                dfs(nodo, visitados, comunidad);
                comunidades.agregar(comunidad);
            }
        }

        return comunidades;
    }

    /**
     * DFS auxiliar para recorrer el grafo y encontrar una comunidad.
     *
     * @param nodo     Nodo actual.
     * @param visitados Conjunto de nodos visitados.
     * @param comunidad Lista donde se acumulan nodos de la comunidad.
     */
    private void dfs(T nodo, Set<T> visitados, ListaEnlazada<T> comunidad) {
        visitados.add(nodo);
        comunidad.agregar(nodo);
        for (T vecino : listaAdyacencia.getOrDefault(nodo, Collections.emptyList())) {
            if (!visitados.contains(vecino)) {
                dfs(vecino, visitados, comunidad);
            }
        }
    }

    // Métodos adicionales típicos podrían incluir eliminar nodo/arista,
    // verificar existencia de nodo/arista, obtener grado, etc.
}

