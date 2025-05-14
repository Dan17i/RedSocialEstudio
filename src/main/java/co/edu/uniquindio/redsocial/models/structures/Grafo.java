package co.edu.uniquindio.redsocial.models.structures;

import java.util.*;

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
public class Grafo<T> {

    private ListaEnlazada<NodoGrafo<T>> nodos;
    private boolean esDirigido;

    /**
     * Constructor que inicializa un grafo no dirigido vacío.
     */
    public Grafo() {
        this(false);
    }

    /**
     * Constructor que permite definir si el grafo es dirigido.
     *
     * @param esDirigido true si el grafo es dirigido, false si no
     */
    public Grafo(boolean esDirigido) {
        this.nodos = new ListaEnlazada<>();
        this.esDirigido = esDirigido;
    }

    /**
     * Agrega un nuevo nodo al grafo si no existe previamente.
     *
     * @param dato El dato que representa el nodo a agregar.
     */
    public void agregarNodo(T dato) {
        if (buscarNodo(dato) == null) {
            nodos.agregar(new NodoGrafo<>(dato));
        }
    }

    /**
     * Agrega una arista entre dos nodos del grafo con peso 1.0 por defecto.
     * Si los nodos no existen, no se hace nada.
     * Para grafos no dirigidos, la conexión es bidireccional.
     *
     * @param nodo1 El primer nodo de la arista.
     * @param nodo2 El segundo nodo de la arista.
     */
    public void agregarArista(T nodo1, T nodo2) {
        NodoGrafo<T> n1 = buscarNodo(nodo1);
        NodoGrafo<T> n2 = buscarNodo(nodo2);

        if (n1 == null || n2 == null) return;

        if (!n1.esAdyacente(n2)) {
            n1.agregarAdyacente(n2, 1.0);
            if (!esDirigido) {
                n2.agregarAdyacente(n1, 1.0);
            }
        }
    }
    /**
     * Elimina un nodo del grafo junto con todas las conexiones hacia él desde otros nodos.
     *
     * @param dato El dato del nodo a eliminar.
     */
    public void eliminarNodo(T dato) {
        NodoGrafo<T> nodoAEliminar = buscarNodo(dato);
        if (nodoAEliminar == null) return;

        // Eliminar el nodo de la lista de nodos
        nodos.eliminar(nodoAEliminar);

        // Eliminar referencias en otros nodos
        for (NodoGrafo<T> nodo : nodos) {
            nodo.eliminarAdyacente(nodoAEliminar);
        }
    }
    /**
     * Elimina una arista entre dos nodos del grafo.
     *
     * @param nodo1 El dato del primer nodo.
     * @param nodo2 El dato del segundo nodo.
     */
    public void eliminarArista(T nodo1, T nodo2) {
        NodoGrafo<T> n1 = buscarNodo(nodo1);
        NodoGrafo<T> n2 = buscarNodo(nodo2);
        if (n1 == null || n2 == null) return;

        n1.eliminarAdyacente(n2);
        if (!esDirigido) {
            n2.eliminarAdyacente(n1);
        }
    }

    /**
     * Busca la ruta más corta entre dos nodos usando el algoritmo de Dijkstra.
     *
     * @param origen Nodo de inicio.
     * @param destino Nodo de destino.
     * @return Lista de nodos que representan la ruta más corta o null si no hay conexión.
     */
    public ListaEnlazada<T> buscarRutaCorta(T origen, T destino) {
        NodoGrafo<T> nodoOrigen = buscarNodo(origen);
        NodoGrafo<T> nodoDestino = buscarNodo(destino);

        if (nodoOrigen == null || nodoDestino == null) {
            return null;
        }

        Map<NodoGrafo<T>, Double> distancias = new HashMap<>();
        Map<NodoGrafo<T>, NodoGrafo<T>> predecesores = new HashMap<>();
        Set<NodoGrafo<T>> visitados = new HashSet<>();

        for (NodoGrafo<T> nodo : nodos) {
            distancias.put(nodo, Double.MAX_VALUE);
        }
        distancias.put(nodoOrigen, 0.0);

        while (visitados.size() < nodos.getTamanio()) {
            NodoGrafo<T> actual = obtenerNodoDistanciaMinima(distancias, visitados);
            if (actual == null) break;

            visitados.add(actual);

            // Recorrer los adyacentes del nodo actual
            for (Map.Entry<NodoGrafo<T>, Double> vecino : actual.getAdyacentes().entrySet()) {
                NodoGrafo<T> adyacente = vecino.getKey();
                double peso = vecino.getValue();  // Obtener el peso directamente del Map

                if (!visitados.contains(adyacente)) {
                    double nuevaDistancia = distancias.get(actual) + peso;
                    if (nuevaDistancia < distancias.get(adyacente)) {
                        distancias.put(adyacente, nuevaDistancia);
                        predecesores.put(adyacente, actual);
                    }
                }
            }
        }


        if (distancias.get(nodoDestino) == Double.MAX_VALUE) {
            return null;
        }

        return reconstruirRuta(nodoDestino, predecesores);
    }

    private NodoGrafo<T> obtenerNodoDistanciaMinima(Map<NodoGrafo<T>, Double> distancias, Set<NodoGrafo<T>> visitados) {
        NodoGrafo<T> minimo = null;
        double menorDistancia = Double.MAX_VALUE;

        for (NodoGrafo<T> nodo : distancias.keySet()) {
            if (!visitados.contains(nodo) && distancias.get(nodo) < menorDistancia) {
                menorDistancia = distancias.get(nodo);
                minimo = nodo;
            }
        }
        return minimo;
    }

    private ListaEnlazada<T> reconstruirRuta(NodoGrafo<T> destino, Map<NodoGrafo<T>, NodoGrafo<T>> predecesores) {
        ListaEnlazada<T> ruta = new ListaEnlazada<>();
        NodoGrafo<T> actual = destino;

        while (actual != null) {
            ruta.agregarInicio(actual.getDato());
            actual = predecesores.get(actual);
        }

        return ruta;
    }

    /**
     * Detecta comunidades dentro del grafo. Método de ejemplo a implementar.
     *
     * @return Lista vacía por ahora.
     */
    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() {
        return new ListaEnlazada<>();
    }

    /**
     * Actualiza o agrega una conexión entre dos nodos con un peso dado.
     * Para grafos no dirigidos, la conexión es bidireccional.
     *
     * @param nodo1 Nodo origen.
     * @param nodo2 Nodo destino.
     * @param peso Peso de la arista.
     */
    public void actualizarConexiones(T nodo1, T nodo2, double peso) {
        NodoGrafo<T> n1 = buscarNodo(nodo1);
        NodoGrafo<T> n2 = buscarNodo(nodo2);

        if (n1 == null || n2 == null) return;

        n1.getAdyacentes().put(n2, peso);
        if (!esDirigido) {
            n2.getAdyacentes().put(n1, peso);
        }
    }

    /**
     * Busca un nodo específico en el grafo.
     *
     * @param dato El dato del nodo a buscar.
     * @return El nodo encontrado, o null si no se encuentra.
     */
    public NodoGrafo<T> buscarNodo(T dato) {
        NodoLista<NodoGrafo<T>> actual = nodos.getCabeza();
        while (actual != null) {
            if (actual.getDato().getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
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

    public boolean isDirigido() {
        return esDirigido;
    }

    public void setEsDirigido(boolean esDirigido) {
        this.esDirigido = esDirigido;
    }
}