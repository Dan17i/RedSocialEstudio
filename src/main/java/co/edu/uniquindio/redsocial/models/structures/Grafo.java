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
    private final Map<T, NodoGrafo<T>> mapaDeNodos; // Para búsqueda optimizada O(1)
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
        this.mapaDeNodos = new HashMap<>();
        this.esDirigido = esDirigido;
    }

    /**
     * Agrega un nuevo nodo al grafo si no existe previamente.
     *
     * @param dato El dato que representa el nodo a agregar.
     * @throws IllegalArgumentException Si el dato es null.
     */
    public void agregarNodo(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede agregar un nodo con dato nulo");
        }

        if (!mapaDeNodos.containsKey(dato)) {
            NodoGrafo<T> nuevoNodo = new NodoGrafo<>(dato);
            nodos.agregar(nuevoNodo);
            mapaDeNodos.put(dato, nuevoNodo);
        }
    }

    /**
     * Agrega una arista entre dos nodos del grafo con un peso especificado.
     * Si los nodos no existen, no se hace nada.
     * Para grafos no dirigidos, la conexión es bidireccional.
     *
     * @param nodo1 El primer nodo de la arista.
     * @param nodo2 El segundo nodo de la arista.
     * @param peso El peso de la arista.
     * @throws IllegalArgumentException Si los nodos son iguales o no existen, o si el peso es negativo.
     */
    public void agregarArista(T nodo1, T nodo2, double peso) {
        if (nodo1 == null || nodo2 == null) {
            throw new IllegalArgumentException("Los nodos no pueden ser nulos");
        }

        if (nodo1.equals(nodo2)) {
            throw new IllegalArgumentException("No se permiten aristas entre el mismo nodo");
        }

        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }

        NodoGrafo<T> n1 = mapaDeNodos.get(nodo1);
        NodoGrafo<T> n2 = mapaDeNodos.get(nodo2);

        if (n1 == null || n2 == null) {
            throw new IllegalArgumentException("Uno o ambos nodos no existen en el grafo");
        }

        n1.agregarAdyacente(n2, peso);
        if (!esDirigido) {
            n2.agregarAdyacente(n1, peso);
        }
    }

    /**
     * Agrega una arista entre dos nodos del grafo con peso por defecto de 0.0.
     * Este valor representa que inicialmente no hay afinidad.
     *
     * @param nodo1 El primer nodo de la arista.
     * @param nodo2 El segundo nodo de la arista.
     * @throws IllegalArgumentException Si los nodos son iguales o no existen.
     */
    public void agregarArista(T nodo1, T nodo2) {
        agregarArista(nodo1, nodo2, 0.0); // Inicializa con afinidad cero
    }

    /**
     * Elimina un nodo del grafo junto con todas las conexiones hacia él desde otros nodos.
     *
     * @param dato El dato del nodo a eliminar.
     * @return true si el nodo fue eliminado, false si no existía.
     */
    public boolean eliminarNodo(T dato) {
        if (dato == null) {
            return false;
        }

        NodoGrafo<T> nodoAEliminar = mapaDeNodos.get(dato);
        if (nodoAEliminar == null) {
            return false;
        }

        // Eliminar el nodo de la lista de nodos y del mapa
        nodos.eliminar(nodoAEliminar);
        mapaDeNodos.remove(dato);

        // Eliminar referencias en otros nodos
        for (NodoGrafo<T> nodo : nodos) {
            nodo.eliminarAdyacente(nodoAEliminar);
        }

        return true;
    }

    /**
     * Elimina una arista entre dos nodos del grafo.
     *
     * @param nodo1 El dato del primer nodo.
     * @param nodo2 El dato del segundo nodo.
     * @return true si la arista fue eliminada, false si alguno de los nodos no existía.
     */
    public boolean eliminarArista(T nodo1, T nodo2) {
        if (nodo1 == null || nodo2 == null) return false;

        NodoGrafo<T> n1 = mapaDeNodos.get(nodo1);
        NodoGrafo<T> n2 = mapaDeNodos.get(nodo2);
        if (n1 == null || n2 == null) return false;

        boolean resultado = n1.eliminarAdyacente(n2);
        if (!esDirigido) {
            n2.eliminarAdyacente(n1);
        }
        return resultado;
    }


    /**
     * Busca la ruta más corta entre dos nodos usando el algoritmo de Dijkstra.
     *
     * @param origen Nodo de inicio.
     * @param destino Nodo de destino.
     * @return Lista de nodos que representan la ruta más corta o null si no hay conexión.
     * @throws IllegalArgumentException Si alguno de los nodos no existe en el grafo.
     */
    public ListaEnlazada<T> buscarRutaCorta(T origen, T destino) {
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Los nodos origen y destino no pueden ser nulos");
        }

        NodoGrafo<T> nodoOrigen = mapaDeNodos.get(origen);
        NodoGrafo<T> nodoDestino = mapaDeNodos.get(destino);

        if (nodoOrigen == null || nodoDestino == null) {
            throw new IllegalArgumentException("Uno o ambos nodos no existen en el grafo");
        }

        // Si origen y destino son iguales, devolver una lista con un solo elemento
        if (origen.equals(destino)) {
            ListaEnlazada<T> rutaSimple = new ListaEnlazada<>();
            rutaSimple.agregar(origen);
            return rutaSimple;
        }

        Map<NodoGrafo<T>, Double> distancias = new HashMap<>();
        Map<NodoGrafo<T>, NodoGrafo<T>> predecesores = new HashMap<>();

        // Usamos PriorityQueue para mejorar la eficiencia al obtener el nodo con distancia mínima
        PriorityQueue<NodoGrafo<T>> cola = new PriorityQueue<>(
                Comparator.comparingDouble(nodo -> distancias.getOrDefault(nodo, Double.MAX_VALUE))
        );

        // Inicializar distancias
        for (NodoGrafo<T> nodo : nodos) {
            distancias.put(nodo, Double.MAX_VALUE);
        }
        distancias.put(nodoOrigen, 0.0);
        cola.add(nodoOrigen);

        while (!cola.isEmpty()) {
            NodoGrafo<T> actual = cola.poll();

            // Si llegamos al destino, reconstruir la ruta
            if (actual.equals(nodoDestino)) {
                return reconstruirRuta(nodoDestino, predecesores);
            }

            // Si la distancia actual es infinito, no hay más caminos
            if (distancias.get(actual) == Double.MAX_VALUE) {
                break;
            }

            // Recorrer los adyacentes del nodo actual
            for (Map.Entry<NodoGrafo<T>, Double> vecino : actual.getAdyacentes().entrySet()) {
                NodoGrafo<T> adyacente = vecino.getKey();
                double peso = vecino.getValue();

                double nuevaDistancia = distancias.get(actual) + peso;
                if (nuevaDistancia < distancias.get(adyacente)) {
                    // Actualizar la distancia
                    distancias.put(adyacente, nuevaDistancia);
                    // Actualizar el predecesor
                    predecesores.put(adyacente, actual);
                    // Re-encolar para procesar con la nueva distancia
                    cola.remove(adyacente); // Eliminar si ya estaba en la cola
                    cola.add(adyacente);    // Añadir con la nueva prioridad
                }
            }
        }

        // Si llegamos aquí, no hay ruta
        return null;
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
     * Detecta comunidades dentro del grafo usando el algoritmo de Louvain.
     * Este algoritmo maximiza la modularidad del grafo para identificar comunidades.
     *
     * @return Lista de comunidades, donde cada comunidad es una lista de nodos.
     */
    public ListaEnlazada<ListaEnlazada<T>> detectarComunidades() {
        // Implementación del algoritmo de Louvain para detección de comunidades

        // Paso 1: Inicialización - cada nodo en su propia comunidad
        Map<NodoGrafo<T>, Integer> comunidades = new HashMap<>();
        int comunidadId = 0;
        for (NodoGrafo<T> nodo : nodos) {
            comunidades.put(nodo, comunidadId++);
        }

        boolean huboCambios;
        double modularidadAnterior = calcularModularidad(comunidades);

        do {
            huboCambios = false;

            // Paso 2: Primera fase - reasignar nodos a comunidades para maximizar modularidad
            for (NodoGrafo<T> nodo : nodos) {
                int comunidadOriginal = comunidades.get(nodo);
                double mejorGanancia = 0;
                int mejorComunidad = comunidadOriginal;

                // Conjunto para evitar evaluar la misma comunidad varias veces
                Set<Integer> comunidadesVecinas = new HashSet<>();
                comunidadesVecinas.add(comunidadOriginal);

                // Añadir comunidades de los vecinos
                for (NodoGrafo<T> vecino : nodo.getAdyacentes().keySet()) {
                    comunidadesVecinas.add(comunidades.get(vecino));
                }

                // Evaluar ganancia de modularidad para cada comunidad vecina
                for (int comunidadCandidata : comunidadesVecinas) {
                    // Mover temporalmente el nodo a la comunidad candidata
                    comunidades.put(nodo, comunidadCandidata);

                    // Calcular la ganancia de modularidad
                    double nuevaModularidad = calcularModularidad(comunidades);
                    double ganancia = nuevaModularidad - modularidadAnterior;

                    if (ganancia > mejorGanancia) {
                        mejorGanancia = ganancia;
                        mejorComunidad = comunidadCandidata;
                    }
                }

                // Restaurar a la comunidad original para evaluar otros nodos
                comunidades.put(nodo, comunidadOriginal);

                // Si encontramos una mejor comunidad, mover el nodo
                if (mejorComunidad != comunidadOriginal) {
                    comunidades.put(nodo, mejorComunidad);
                    huboCambios = true;
                    modularidadAnterior += mejorGanancia;
                }
            }

            // Si no hay más cambios que mejoren la modularidad, terminamos
            if (!huboCambios) {
                break;
            }

            // Paso 3: Segunda fase - agrupar los nodos de la misma comunidad
            // En una implementación completa, aquí se crearía un nuevo grafo con las comunidades como nodos
            // Pero para simplificar, omitimos este paso y seguimos iterando sobre el grafo original

        } while (huboCambios);

        // Convertir el mapa de comunidades a la estructura de retorno
        Map<Integer, ListaEnlazada<T>> comunidadesFinales = new HashMap<>();
        for (Map.Entry<NodoGrafo<T>, Integer> entry : comunidades.entrySet()) {
            int idComunidad = entry.getValue();
            if (!comunidadesFinales.containsKey(idComunidad)) {
                comunidadesFinales.put(idComunidad, new ListaEnlazada<>());
            }
            comunidadesFinales.get(idComunidad).agregar(entry.getKey().getDato());
        }

        // Crear la lista de comunidades
        ListaEnlazada<ListaEnlazada<T>> resultado = new ListaEnlazada<>();
        for (ListaEnlazada<T> comunidad : comunidadesFinales.values()) {
            resultado.agregar(comunidad);
        }

        return resultado;
    }

    /**
     * Calcula la modularidad de la partición actual del grafo.
     * La modularidad es una medida de la calidad de una partición de un grafo en comunidades.
     *
     * @param comunidades Mapa que asigna cada nodo a su comunidad.
     * @return Valor de modularidad (entre -0.5 y 1).
     */
    private double calcularModularidad(Map<NodoGrafo<T>, Integer> comunidades) {
        double m = calcularTotalAristas();
        if (m == 0) return 0;

        double q = 0;
        for (NodoGrafo<T> i : nodos) {
            for (NodoGrafo<T> j : nodos) {
                if (comunidades.get(i).equals(comunidades.get(j))) {
                    double aij = (i.esAdyacente(j))
                            ? i.getPesoArista(j)
                            : 0;
                    double ki = i.getGrado();
                    double kj = j.getGrado();
                    q += aij - (ki * kj / (2 * m));
                }
            }
        }
        return q / (2 * m);
    }


    /**
     * Calcula el total de aristas en el grafo, considerando sus pesos.
     *
     * @return Suma de los pesos de todas las aristas.
     */
    private double calcularTotalAristas() {
        double total = 0;

        for (NodoGrafo<T> nodo : nodos) {
            for (Double peso : nodo.getAdyacentes().values()) {
                total += peso;
            }
        }

        // Si es no dirigido, dividir por 2 para no contar las aristas dos veces
        return esDirigido ? total : total / 2;
    }

    /**
     * Actualiza o agrega una conexión entre dos nodos con un peso dado.
     * Para grafos no dirigidos, la conexión es bidireccional.
     *
     * @param nodo1 Nodo origen.
     * @param nodo2 Nodo destino.
     * @param peso Peso de la arista.
     * @throws IllegalArgumentException Si alguno de los nodos no existe, o si los nodos son iguales.
     */
    public void actualizarConexiones(T nodo1, T nodo2, double peso) {
        if (nodo1 == null || nodo2 == null) {
            throw new IllegalArgumentException("Los nodos no pueden ser nulos");
        }

        if (nodo1.equals(nodo2)) {
            throw new IllegalArgumentException("No se permiten aristas entre el mismo nodo");
        }

        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }

        NodoGrafo<T> n1 = mapaDeNodos.get(nodo1);
        NodoGrafo<T> n2 = mapaDeNodos.get(nodo2);

        if (n1 == null || n2 == null) {
            throw new IllegalArgumentException("Uno o ambos nodos no existen en el grafo");
        }

        n1.getAdyacentes().put(n2, peso);
        if (!esDirigido) {
            n2.getAdyacentes().put(n1, peso);
        }
    }

    /**
     * Busca un nodo específico en el grafo.
     * Complejidad O(1) gracias al mapa de nodos.
     *
     * @param dato El dato del nodo a buscar.
     * @return El nodo encontrado, o null si no se encuentra.
     */
    public NodoGrafo<T> buscarNodo(T dato) {
        return mapaDeNodos.get(dato);
    }

    /**
     * Obtiene los datos necesarios para la visualización del grafo.
     *
     * @return Mapa con la información para visualizar el grafo.
     */
    public Map<String, Object> getDatosVisualizacion() {
        Map<String, Object> datos = new HashMap<>();

        // Lista de nodos para visualización
        List<Map<String, Object>> nodosVisualizacion = new ArrayList<>();
        for (NodoGrafo<T> nodo : nodos) {
            Map<String, Object> nodoInfo = new HashMap<>();
            nodoInfo.put("id", nodo.getDato().toString());
            nodoInfo.put("dato", nodo.getDato());
            nodoInfo.put("grado", nodo.getGrado());
            nodosVisualizacion.add(nodoInfo);
        }

        // Lista de aristas para visualización
        List<Map<String, Object>> aristasVisualizacion = new ArrayList<>();
        for (NodoGrafo<T> origen : nodos) {
            for (Map.Entry<NodoGrafo<T>, Double> arista : origen.getAdyacentes().entrySet()) {
                NodoGrafo<T> destino = arista.getKey();
                Double peso = arista.getValue();

                // Para grafos no dirigidos, solo añadir la arista una vez
                if (esDirigido || origen.getDato().toString().compareTo(destino.getDato().toString()) <= 0) {
                    Map<String, Object> aristaInfo = new HashMap<>();
                    aristaInfo.put("origen", origen.getDato().toString());
                    aristaInfo.put("destino", destino.getDato().toString());
                    aristaInfo.put("peso", peso);
                    aristasVisualizacion.add(aristaInfo);
                }
            }
        }

        datos.put("nodos", nodosVisualizacion);
        datos.put("aristas", aristasVisualizacion);
        datos.put("esDirigido", esDirigido);

        return datos;
    }

    /**
     * Verifica si el grafo contiene ciclos negativos.
     * Útil para validar si el algoritmo de Dijkstra puede funcionar correctamente.
     *
     * @return true si hay ciclos negativos, false en caso contrario.
     */
    public boolean tieneCiclosNegativos() {
        // Implementamos algoritmo de Bellman-Ford para detectar ciclos negativos
        if (nodos.getTamanio() == 0) {
            return false;
        }

        NodoGrafo<T> origen = nodos.getCabeza().getDato();
        Map<NodoGrafo<T>, Double> distancias = new HashMap<>();

        // Inicializar distancias
        for (NodoGrafo<T> nodo : nodos) {
            distancias.put(nodo, Double.MAX_VALUE);
        }
        distancias.put(origen, 0.0);

        // Relajar aristas |V| - 1 veces
        int numNodos = nodos.getTamanio();
        for (int i = 0; i < numNodos - 1; i++) {
            for (NodoGrafo<T> u : nodos) {
                for (Map.Entry<NodoGrafo<T>, Double> arista : u.getAdyacentes().entrySet()) {
                    NodoGrafo<T> v = arista.getKey();
                    Double peso = arista.getValue();

                    if (distancias.get(u) != Double.MAX_VALUE &&
                            distancias.get(u) + peso < distancias.get(v)) {
                        distancias.put(v, distancias.get(u) + peso);
                    }
                }
            }
        }

        // Verificar ciclos negativos - Si podemos relajar más, hay ciclo negativo
        for (NodoGrafo<T> u : nodos) {
            for (Map.Entry<NodoGrafo<T>, Double> arista : u.getAdyacentes().entrySet()) {
                NodoGrafo<T> v = arista.getKey();
                Double peso = arista.getValue();

                if (distancias.get(u) != Double.MAX_VALUE &&
                        distancias.get(u) + peso < distancias.get(v)) {
                    return true; // Hay ciclo negativo
                }
            }
        }

        return false;
    }

    // Getters y Setters

    /**
     * Obtiene la lista de nodos del grafo.
     *
     * @return Lista enlazada con los nodos del grafo.
     */
    public ListaEnlazada<NodoGrafo<T>> getNodos() {
        return nodos;
    }

    /**
     * Establece la lista de nodos del grafo.
     *
     * @param nodos Nueva lista de nodos.
     */
    public void setNodos(ListaEnlazada<NodoGrafo<T>> nodos) {
        this.nodos = nodos;

        // Actualizar el mapa de nodos para mantener la consistencia
        this.mapaDeNodos.clear();
        for (NodoGrafo<T> nodo : nodos) {
            this.mapaDeNodos.put(nodo.getDato(), nodo);
        }
    }

    /**
     * Verifica si el grafo es dirigido.
     *
     * @return true si el grafo es dirigido, false en caso contrario.
     */
    public boolean isDirigido() {
        return esDirigido;
    }

    /**
     * Establece si el grafo es dirigido o no.
     * ¡Cuidado! Cambiar esta propiedad después de haber agregado aristas
     * puede generar inconsistencias en las conexiones.
     *
     * @param esDirigido true para hacer el grafo dirigido, false para no dirigido.
     */
    public void setEsDirigido(boolean esDirigido) {
        this.esDirigido = esDirigido;
    }

    /**
     * Obtiene el número de nodos en el grafo.
     *
     * @return Cantidad de nodos.
     */
    public int getNumeroDeNodos() {
        return nodos.getTamanio();
    }

    /**
     * Obtiene el número de aristas en el grafo.
     *
     * @return Cantidad de aristas.
     */
    public int getNumeroDeAristas() {
        int contador = 0;
        for (NodoGrafo<T> nodo : nodos) {
            contador += nodo.getAdyacentes().size();
        }

        // Si el grafo no es dirigido, cada arista se cuenta dos veces
        return esDirigido ? contador : contador / 2;
    }

    /**
     * Limpia el grafo, eliminando todos los nodos y aristas.
     */
    public void limpiar() {
        nodos = new ListaEnlazada<>();
        mapaDeNodos.clear();
    }
}