package co.edu.uniquindio.redsocial.models.structures;

import co.edu.uniquindio.redsocial.models.services.interf.IGrafo;

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

    /** Peso asignado a las aristas creadas sin peso explícito. */
    protected static final double PESO_POR_DEFECTO = 1.0;

    private ListaEnlazada<NodoGrafo<T>> nodos;
    private final TablaHash<T, NodoGrafo<T>> mapaDeNodos;
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
        this.mapaDeNodos = new TablaHash<>();
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
        if (!mapaDeNodos.contieneClave(dato)) {
            NodoGrafo<T> nuevo = new NodoGrafo<>(dato);
            nodos.agregar(nuevo);
            mapaDeNodos.poner(dato, nuevo);
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

        NodoGrafo<T> n1 = mapaDeNodos.obtener(nodo1);
        NodoGrafo<T> n2 = mapaDeNodos.obtener(nodo2);
        if (n1 == null || n2 == null) throw new IllegalArgumentException("Uno o ambos nodos no existen");

        n1.agregarAdyacente(n2, peso);
        if (!esDirigido) n2.agregarAdyacente(n1, peso);
    }
    /**
     * Agrega una arista con peso por defecto 1.0, de modo que la ruta más corta
     * sea la de menor número de conexiones (saltos).
     */
    @Override
    public void agregarArista(T nodo1, T nodo2) {
        agregarArista(nodo1, nodo2, PESO_POR_DEFECTO);
    }
    /**
     * Elimina un nodo y todas sus conexiones.
     * @param dato Dato del nodo
     * @return true si fue eliminado, false si no existe
     */
    @Override
    public boolean eliminarNodo(T dato) {
        if (dato == null) return false;
        NodoGrafo<T> nodo = mapaDeNodos.eliminar(dato);
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
        NodoGrafo<T> n1 = mapaDeNodos.obtener(nodo1);
        NodoGrafo<T> n2 = mapaDeNodos.obtener(nodo2);
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

        NodoGrafo<T> nodoOrigen = mapaDeNodos.obtener(origen);
        NodoGrafo<T> nodoDestino = mapaDeNodos.obtener(destino);

        if (nodoOrigen == null || nodoDestino == null)
            throw new IllegalArgumentException("Uno o ambos nodos no existen");

        if (origen.equals(destino)) {
            ListaEnlazada<T> ruta = new ListaEnlazada<>();
            ruta.agregar(origen);
            return ruta;
        }

        TablaHash<NodoGrafo<T>, Double> distancias = new TablaHash<>();
        TablaHash<NodoGrafo<T>, NodoGrafo<T>> predecesores = new TablaHash<>();
        // Nodos alcanzados y aún no procesados; se extrae siempre el de menor distancia.
        ListaEnlazada<NodoGrafo<T>> pendientes = new ListaEnlazada<>();

        for (NodoGrafo<T> nodo : nodos) {
            distancias.poner(nodo, Double.MAX_VALUE);
        }

        distancias.poner(nodoOrigen, 0.0);
        pendientes.agregar(nodoOrigen);

        while (!pendientes.isEmpty()) {
            int indiceMenor = 0;
            for (int i = 1; i < pendientes.getTamanio(); i++) {
                if (distancias.obtener(pendientes.obtener(i)) < distancias.obtener(pendientes.obtener(indiceMenor))) {
                    indiceMenor = i;
                }
            }
            NodoGrafo<T> actual = pendientes.eliminarEn(indiceMenor);

            if (actual.equals(nodoDestino)) return reconstruirRuta(nodoDestino, predecesores);

            for (TablaHash.Entrada<NodoGrafo<T>, Double> entrada : actual.getAdyacentes().entradas()) {
                NodoGrafo<T> vecino = entrada.getClave();
                double peso = entrada.getValor();
                double nuevaDistancia = distancias.obtener(actual) + peso;

                if (nuevaDistancia < distancias.obtener(vecino)) {
                    distancias.poner(vecino, nuevaDistancia);
                    predecesores.poner(vecino, actual);
                    if (!pendientes.contiene(vecino)) {
                        pendientes.agregar(vecino);
                    }
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
    private ListaEnlazada<T> reconstruirRuta(NodoGrafo<T> destino, TablaHash<NodoGrafo<T>, NodoGrafo<T>> predecesores) {
        ListaEnlazada<T> ruta = new ListaEnlazada<>();
        NodoGrafo<T> actual = destino;

        while (actual != null) {
            ruta.agregarInicio(actual.getDato());
            actual = predecesores.obtener(actual);
        }

        return ruta;
    }
    /**
     * Retorna el nodo asociado a un dato.
     */
    @Override
    public NodoGrafo<T> obtenerNodo(T dato) {
        return mapaDeNodos.obtener(dato);
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
        return mapaDeNodos.tamanio();
    }

    /**
     * Agrega un nodo al grafo si no existe previamente.
     * Método auxiliar para asegurarse que los nodos existen antes de agregar aristas.
     * @param dato El dato del nodo
     */
    public void agregarVertice(T dato) {
        agregarNodo(dato);
    }

    public TablaHash<T, Double> obtenerAdyacentes(T dato) {
        TablaHash<T, Double> adyacentes = new TablaHash<>();
        NodoGrafo<T> nodo = mapaDeNodos.obtener(dato);
        if (nodo == null) return adyacentes;

        for (TablaHash.Entrada<NodoGrafo<T>, Double> entry : nodo.getAdyacentes().entradas()) {
            adyacentes.poner(entry.getClave().getDato(), entry.getValor());
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
        return mapaDeNodos.contieneClave(dato);
    }

    public ListaEnlazada<T> obtenerTodosLosDatos() {
        return mapaDeNodos.claves();
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
        ConjuntoHash<T> visitados = new ConjuntoHash<>();

        for (T vertice : mapaDeNodos.claves()) {
            if (!visitados.contiene(vertice)) {
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
    private void dfs(T actual, ConjuntoHash<T> visitados, ListaEnlazada<T> comunidad) {
        visitados.agregar(actual);
        comunidad.agregar(actual);
        NodoGrafo<T> nodoActual = mapaDeNodos.obtener(actual);
        if (nodoActual == null) return;

        for (NodoGrafo<T> vecinoNodo : nodoActual.getAdyacentes().claves()) {
            T vecino = vecinoNodo.getDato();
            if (!visitados.contiene(vecino)) {
                dfs(vecino, visitados, comunidad);
            }
        }
    }


}

