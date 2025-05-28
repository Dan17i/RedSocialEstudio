package co.edu.uniquindio.redsocial.models.structures;

import co.edu.uniquindio.redsocial.models.services.interf.IGrafo;

import java.util.*;

/**
 * Implementación concreta de la interfaz {@link IGrafo} que representa un grafo genérico.
 * Permite grafos dirigidos o no dirigidos, con operaciones de agregado, eliminación, búsqueda
 * de ruta más corta (Dijkstra) y detección de comunidades.
 *
 * @param <T> Tipo genérico de los nodos del grafo.
 *
 * @author Daniel Jurado
 * @since 2025-05-24
 */
public class GrafoImpl<T> implements IGrafo<T> {

    private ListaEnlazada<NodoGrafo<T>> nodos;
    private final Map<T, NodoGrafo<T>> mapaDeNodos;
    private final boolean esDirigido;

    /**
     * Crea un grafo no dirigido vacío.
     */
    public GrafoImpl() {
        this(false);
    }

    /**
     * Crea un grafo, indicando si es dirigido o no.
     * @param esDirigido true si es dirigido, false si es no dirigido.
     */
    public GrafoImpl(boolean esDirigido) {
        this.nodos = new ListaEnlazada<>();
        this.mapaDeNodos = new HashMap<>();
        this.esDirigido = esDirigido;
    }
    /**
     * Agrega un nodo al grafo si no existe previamente.
     * @param dato El dato del nodo
     * @throws IllegalArgumentException si el dato es null
     */
    @Override
    public void agregarNodo(T dato) {
        if (dato == null) throw new IllegalArgumentException("El nodo no puede ser null");
        if (!mapaDeNodos.containsKey(dato)) {
            NodoGrafo<T> nuevo = new NodoGrafo<>(dato);
            nodos.agregar(nuevo);
            mapaDeNodos.put(dato, nuevo);
        }
    }
    /**
     * Agrega una arista con un peso específico.
     * @param nodo1 Nodo de origen
     * @param nodo2 Nodo de destino
     * @param peso Peso de la arista (debe ser >= 0)
     * @throws IllegalArgumentException si nodos no existen o el peso es inválido
     */

    public void agregarArista(T nodo1, T nodo2, double peso) {
        if (nodo1 == null || nodo2 == null) throw new IllegalArgumentException("Nodos no pueden ser null");
        if (nodo1.equals(nodo2)) throw new IllegalArgumentException("No se permiten lazos (nodo igual a sí mismo)");
        if (peso < 0) throw new IllegalArgumentException("Peso no puede ser negativo");

        NodoGrafo<T> n1 = mapaDeNodos.get(nodo1);
        NodoGrafo<T> n2 = mapaDeNodos.get(nodo2);
        if (n1 == null || n2 == null) throw new IllegalArgumentException("Uno o ambos nodos no existen");

        n1.agregarAdyacente(n2, peso);
        if (!esDirigido) n2.agregarAdyacente(n1, peso);
    }
    /**
     * Agrega una arista sin peso (valor por defecto 0.0).
     */
    @Override
    public void agregarArista(T nodo1, T nodo2) {
        agregarArista(nodo1, nodo2, 0.0);
    }
    /**
     * Elimina un nodo y todas sus conexiones.
     * @param dato Dato del nodo
     * @return true si fue eliminado, false si no existe
     */
    @Override
    public boolean eliminarNodo(T dato) {
        if (dato == null) return false;
        NodoGrafo<T> nodo = mapaDeNodos.remove(dato);
        if (nodo == null) return false;
        nodos.eliminar(nodo);
        for (NodoGrafo<T> otro : nodos) {
            otro.eliminarAdyacente(nodo);
        }
        return true;
    }
    /**
     * Elimina una arista entre dos nodos.
     * @param nodo1 Origen
     * @param nodo2 Destino
     * @return true si fue eliminada, false si no existía
     */
    @Override
    public boolean eliminarArista(T nodo1, T nodo2) {
        NodoGrafo<T> n1 = mapaDeNodos.get(nodo1);
        NodoGrafo<T> n2 = mapaDeNodos.get(nodo2);
        if (n1 == null || n2 == null) return false;

        boolean eliminado = n1.eliminarAdyacente(n2);
        if (!esDirigido) n2.eliminarAdyacente(n1);
        return eliminado;
    }
    /**
     * Devuelve la ruta más corta entre dos nodos usando Dijkstra.
     * @param origen Nodo inicial
     * @param destino Nodo final
     * @return Lista con la ruta más corta o null si no existe
     */
    @Override
    public ListaEnlazada<T> buscarRutaCorta(T origen, T destino) {
        if (origen == null || destino == null)
            throw new IllegalArgumentException("Nodos no pueden ser null");

        NodoGrafo<T> nodoOrigen = mapaDeNodos.get(origen);
        NodoGrafo<T> nodoDestino = mapaDeNodos.get(destino);

        if (nodoOrigen == null || nodoDestino == null)
            throw new IllegalArgumentException("Uno o ambos nodos no existen");

        if (origen.equals(destino)) {
            ListaEnlazada<T> ruta = new ListaEnlazada<>();
            ruta.agregar(origen);
            return ruta;
        }

        Map<NodoGrafo<T>, Double> distancias = new HashMap<>();
        Map<NodoGrafo<T>, NodoGrafo<T>> predecesores = new HashMap<>();
        PriorityQueue<NodoGrafo<T>> cola = new PriorityQueue<>(Comparator.comparingDouble(n -> distancias.getOrDefault(n, Double.MAX_VALUE)));

        for (NodoGrafo<T> nodo : nodos) {
            distancias.put(nodo, Double.MAX_VALUE);
        }

        distancias.put(nodoOrigen, 0.0);
        cola.add(nodoOrigen);

        while (!cola.isEmpty()) {
            NodoGrafo<T> actual = cola.poll();

            if (actual.equals(nodoDestino)) return reconstruirRuta(nodoDestino, predecesores);

            for (Map.Entry<NodoGrafo<T>, Double> entrada : actual.getAdyacentes().entrySet()) {
                NodoGrafo<T> vecino = entrada.getKey();
                double peso = entrada.getValue();
                double nuevaDistancia = distancias.get(actual) + peso;

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, actual);
                    cola.remove(vecino);
                    cola.add(vecino);
                }
            }
        }

        return null;
    }

    /**
     * Reconstruye una ruta desde un mapa de predecesores.
     * @param destino Nodo destino.
     * @param predecesores Mapa de predecesores.
     * @return Ruta reconstruida.
     */
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
     * Retorna el nodo asociado a un dato.
     */
    @Override
    public NodoGrafo<T> obtenerNodo(T dato) {
        return mapaDeNodos.get(dato);
    }
    /**
     * Obtiene todos los nodos del grafo.
     */
    @Override
    public ListaEnlazada<NodoGrafo<T>> obtenerNodos() {
        return nodos;
    }
    /**
     * Indica si el grafo es dirigido.
     */
    @Override
    public boolean esDirigido() {
        return esDirigido;
    }
    /**
     * Retorna el número de nodos en el grafo.
     */
    @Override
    public int tamano() {
        return mapaDeNodos.size();
    }

    /**
     * Agrega un nodo al grafo si no existe previamente.
     * Método auxiliar para asegurarse que los nodos existen antes de agregar aristas.
     * @param dato El dato del nodo
     */
    public void agregarVertice(T dato) {
        mapaDeNodos.putIfAbsent(dato, new NodoGrafo<>(dato));
    }

    public Map<T, Double> obtenerAdyacentes(T dato) {
        NodoGrafo<T> nodo = mapaDeNodos.get(dato);
        if (nodo == null) return Collections.emptyMap();

        Map<T, Double> adyacentes = new HashMap<>();
        for (Map.Entry<NodoGrafo<T>, Double> entry : nodo.getAdyacentes().entrySet()) {
            adyacentes.put(entry.getKey().getDato(), entry.getValue());
        }
        return adyacentes;
    }
    /**
     * Verifica si el grafo contiene un nodo con el dato especificado.
     *
     * @param dato El dato del nodo que se desea verificar.
     *
     * @return {@code true} si el grafo contiene un nodo con el dato dado; {@code false} en caso contrario.
     */
    public boolean contieneNodo(T dato) {
        return mapaDeNodos.containsKey(dato);
    }

    public Set<T> obtenerTodosLosDatos() {
        return mapaDeNodos.keySet();
    }
    /**
     * Busca un nodo en la lista de nodos del grafo que contenga el dato especificado.
     *
     * @param dato El dato que se desea buscar en los nodos del grafo.
     *
     * @return El nodo que contiene el dato si se encuentra en la lista de nodos;
     *         de lo contrario, retorna null.
     */
    public NodoGrafo<T> buscarNodo(T dato) {
        for (int i = 0; i < nodos.getTamanio(); i++) {
            NodoGrafo<T> nodo = nodos.obtener(i);
            if (nodo.getDato().equals(dato)) {
                return nodo;
            }
        }
        return null;
    }

    public ListaEnlazada<NodoGrafo<T>> getNodos() {
        return nodos;
    }

    /**
     * Detecta comunidades dentro del grafo utilizando componentes conexas.
     * Cada comunidad es una lista de nodos conectados entre sí.
     *
     * @return Lista de comunidades (cada una representada como una lista de nodos).
     */
    @Override
    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() {
        ListaEnlazada<ListaEnlazada<T>> comunidades = new ListaEnlazada<>();
        Set<T> visitados = new HashSet<>();

        for (T vertice : mapaDeNodos.keySet()) {
            if (!visitados.contains(vertice)) {
                ListaEnlazada<T> comunidad = new ListaEnlazada<>();
                dfs(vertice, visitados, comunidad);
                comunidades.agregar(comunidad);
            }
        }

        return comunidades;
    }

    /**
     * Algoritmo DFS para detectar comunidades (componentes conexas).
     * @param actual Nodo actual.
     * @param visitados Conjunto de nodos visitados.
     * @param comunidad Lista para almacenar nodos de la comunidad actual.
     */
    private void dfs(T actual, Set<T> visitados, ListaEnlazada<T> comunidad) {
        visitados.add(actual);
        comunidad.agregar(actual);
        NodoGrafo<T> nodoActual = mapaDeNodos.get(actual);
        if (nodoActual == null) return;

        for (NodoGrafo<T> vecinoNodo : nodoActual.getAdyacentes().keySet()) {
            T vecino = vecinoNodo.getDato();
            if (!visitados.contains(vecino)) {
                dfs(vecino, visitados, comunidad);
            }
        }
    }


}

