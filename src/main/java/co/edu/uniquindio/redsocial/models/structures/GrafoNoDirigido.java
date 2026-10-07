package co.edu.uniquindio.redsocial.models.structures;

/**
 * Representa un grafo no dirigido genérico.
 * Reutiliza toda la lógica de {@link GrafoImpl} (nodos, aristas, ruta corta y comunidades),
 * fijando que las aristas sean simétricas, y añade operaciones de conveniencia
 * (creación automática de nodos al conectar y consulta de vecinos).
 *
 * @author Daniel Jurado Sebasstian Torres y Juan Soto
 * @param <T> Tipo del nodo en el grafo.
 */
public class GrafoNoDirigido<T> extends GrafoImpl<T> {

    /**
     * Constructor que inicializa un grafo no dirigido vacío.
     */
    public GrafoNoDirigido() {
        super(false);
    }

    /**
     * Agrega una arista no dirigida entre dos nodos. Si los nodos no existen, los crea.
     *
     * @param nodo1 Primer nodo.
     * @param nodo2 Segundo nodo.
     */
    @Override
    public void agregarArista(T nodo1, T nodo2) {
        agregarArista(nodo1, nodo2, PESO_POR_DEFECTO);
    }

    /**
     * Agrega una arista no dirigida con peso entre dos nodos. Si los nodos no existen, los crea.
     *
     * @param nodo1 Primer nodo.
     * @param nodo2 Segundo nodo.
     * @param peso  Peso de la arista (no negativo).
     */
    @Override
    public void agregarArista(T nodo1, T nodo2, double peso) {
        if (nodo1 == null || nodo2 == null) throw new IllegalArgumentException("Nodos no pueden ser null");
        agregarNodo(nodo1);
        agregarNodo(nodo2);
        super.agregarArista(nodo1, nodo2, peso);
    }

    /**
     * Obtiene los vecinos adyacentes de un nodo.
     *
     * @param nodo Nodo cuyos vecinos se buscan.
     * @return Lista de vecinos, o lista vacía si el nodo no existe.
     */
    public ListaEnlazada<T> obtenerVecinos(T nodo) {
        ListaEnlazada<T> vecinos = new ListaEnlazada<>();
        NodoGrafo<T> nodoGrafo = obtenerNodo(nodo);
        if (nodoGrafo == null) return vecinos;

        for (NodoGrafo<T> vecino : nodoGrafo.getAdyacentes().claves()) {
            vecinos.agregar(vecino.getDato());
        }
        return vecinos;
    }
}
