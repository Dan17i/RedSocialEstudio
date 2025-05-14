package co.edu.uniquindio.redsocial.models.structures;

import co.edu.uniquindio.redsocial.models.NodoGrafo;
import co.edu.uniquindio.redsocial.models.NodoLista;

/**
 * Clase que representa un Grafo genérico. Un grafo es una estructura de datos compuesta por nodos
 * (también llamados vértices) y aristas (también llamadas conexiones o enlaces) que los unen.
 * Este grafo permite realizar operaciones básicas como agregar nodos, agregar aristas, buscar rutas
 * cortas, detectar comunidades y actualizar conexiones con pesos.
 *
 * @param <T> Tipo genérico de los nodos del grafo. Puede ser cualquier objeto.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
class Grafo<T> {

    /**
     * Lista de nodos que forman el grafo.
     */
    private ListaEnlazada<NodoGrafo<T>> nodos = new ListaEnlazada<>();

    /**
     * Constructor que inicializa el grafo con una lista de nodos.
     *
     * @param nodos Lista de nodos inicial para el grafo.
     */
    public Grafo(ListaEnlazada<NodoGrafo<T>> nodos) {
        this.nodos = nodos;
    }

    /**
     * Agrega un nuevo nodo al grafo.
     *
     * @param dato El dato que representa el nodo a agregar.
     */
    public void agregarNodo(T dato) {
        nodos.agregar(new NodoGrafo<>(dato));
    }

    /**
     * Agrega una arista entre dos nodos del grafo. Si los nodos no existen, primero los agrega.
     * Este método debe ser mejorado para implementar correctamente la lógica de la arista.
     *
     * @param nodo1 El primer nodo de la arista.
     * @param nodo2 El segundo nodo de la arista.
     */
    public void agregarArista(T nodo1, T nodo2) {
        // Lógica para agregar la arista entre nodo1 y nodo2
        // Asegurarse de que ambos nodos existan y conectar sus respectivos enlaces
        NodoGrafo<T> n1 = buscarNodo(nodo1);
        NodoGrafo<T> n2 = buscarNodo(nodo2);

        if (n1 != null && n2 != null) {
            n1.agregarAdyacente(n2, 1.0); // Asignar un peso por defecto de 1.0, puedes cambiarlo si es necesario
            n2.agregarAdyacente(n1, 1.0); // Si el grafo es no dirigido
        }
    }

    /**
     * Busca la ruta más corta entre dos nodos usando el algoritmo de búsqueda deseado (por ejemplo, Dijkstra).
     *
     * @param origen Nodo de inicio.
     * @param destino Nodo de destino.
     * @return Lista de nodos que representan la ruta más corta.
     */
    public ListaEnlazada<T> buscarRutaCorta(T origen, T destino) {
        // Implementar algoritmo de ruta más corta (por ejemplo, Dijkstra)
        return new ListaEnlazada<>(); // Retorna una lista vacía por ahora
    }

    /**
     * Detecta comunidades dentro del grafo. Este método puede usar algún algoritmo de detección de comunidades
     * como el algoritmo de Louvain.
     *
     * @return Lista de comunidades, cada una representada por una lista de nodos.
     */
    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() {
        return new ListaEnlazada<>(); // Lista vacía por ahora, implementar lógica más adelante
    }

    /**
     * Actualiza las conexiones entre dos nodos, asignando un peso a la arista entre ellos.
     * El peso podría representar distancia, tiempo, o cualquier otro factor relevante.
     *
     * @param nodo1 El primer nodo de la conexión.
     * @param nodo2 El segundo nodo de la conexión.
     * @param peso El peso de la arista entre los dos nodos.
     */
    public void actualizarConexiones(T nodo1, T nodo2, double peso) {
        // Actualizar pesos entre nodo1 y nodo2, asegurarse de que los nodos existan
        NodoGrafo<T> n1 = buscarNodo(nodo1);
        NodoGrafo<T> n2 = buscarNodo(nodo2);

        if (n1 != null && n2 != null) {
            // Actualizamos los pesos entre los nodos
            n1.actualizarPeso(n2, peso);
            n2.actualizarPeso(n1, peso); // Si el grafo es no dirigido
        }
    }

    /**
     * Busca un nodo específico en el grafo.
     *
     * @param dato El dato del nodo a buscar.
     * @return El nodo encontrado, o null si no se encuentra.
     */
    public NodoGrafo<T> buscarNodo(T dato) {
        NodoLista<NodoGrafo<T>> nodoActual = nodos.getCabeza();  // Obtén la cabeza de la lista
        while (nodoActual != null) {
            if (nodoActual.getDato().equals(dato)) {
                return nodoActual.getDato();   // Cast al tipo correcto
            }
            nodoActual = nodoActual.getSiguiente();  // Usar getSiguiente() para avanzar
        }
        return null;
    }


    // Getters y Setters
    public ListaEnlazada<NodoGrafo<T>> getNodos() {
        return nodos;
    }

    public void setNodos(ListaEnlazada<NodoGrafo<T>> nodos) {
        this.nodos = nodos;
    }
}
