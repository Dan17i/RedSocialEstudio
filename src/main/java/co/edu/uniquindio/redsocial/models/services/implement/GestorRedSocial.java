package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorRedSocial;
import co.edu.uniquindio.redsocial.models.structures.GrafoImpl;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoGrafo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Clase que implementa la interfaz IGestorRedSocial y actúa como gestor principal de la red social educativa.
 * Utiliza una estructura de grafo no dirigido para representar relaciones entre estudiantes.
 * Funcionalidades:
 * - Detección de comunidades mediante DFS.
 * - Visualización del grafo.
 * - Determinación de los estudiantes más conectados.
 * - Cálculo de caminos más cortos entre estudiantes.
 * - Clasificación del nivel de participación de cada estudiante según sus conexiones.
 * Esta implementación utiliza una estructura de grafo personalizada (GrafoImpl),
 * y estructuras de datos como listas enlazadas y nodos de grafo.
 *
 * @author
 *   Daniel Jurado, Sebastián Torres y Juan Soto
 * @since
 *   2025-05-20
 */
public class GestorRedSocial implements IGestorRedSocial {

    private final GrafoImpl<Estudiante> grafoEstudiantes;

    /**
     * Constructor que inicializa el gestor de red social creando una instancia
     * de un grafo no dirigido que almacenará los estudiantes y sus relaciones.
     */
    public GestorRedSocial() {
        this.grafoEstudiantes = new GrafoImpl<>(false); // No dirigido
    }

    /**
     * Detecta comunidades dentro de la red social de estudiantes.
     * Una comunidad se define como un conjunto de estudiantes conectados entre sí
     * (componente conexa del grafo no dirigido). Utiliza un recorrido en profundidad (DFS)
     * para explorar todas las comunidades presentes en el grafo.
     *
     * @return Lista de comunidades, donde cada comunidad es una lista enlazada de estudiantes conectados.
     */

    @Override
    public ListaEnlazada<ListaEnlazada<Estudiante>> detectarComunidades() {
        ListaEnlazada<ListaEnlazada<Estudiante>> comunidades = new ListaEnlazada<>();
        Set<NodoGrafo<Estudiante>> visitados = new HashSet<>();

        for (NodoGrafo<Estudiante> nodo : grafoEstudiantes.obtenerNodos()) {
            if (!visitados.contains(nodo)) {
                ListaEnlazada<Estudiante> comunidad = new ListaEnlazada<>();
                dfs(nodo, visitados, comunidad);
                comunidades.agregar(comunidad);
            }
        }

        return comunidades;
    }
    /**
     * Método auxiliar para realizar un recorrido en profundidad (DFS) sobre el grafo.
     * Visita todos los nodos conectados al nodo actual y los agrega a la comunidad correspondiente.
     *
     * @param actual Nodo actual que se está explorando.
     * @param visitados Conjunto de nodos ya visitados para evitar ciclos.
     * @param comunidad Lista que agrupa los estudiantes pertenecientes a la misma comunidad.
     */

    private void dfs(NodoGrafo<Estudiante> actual, Set<NodoGrafo<Estudiante>> visitados, ListaEnlazada<Estudiante> comunidad) {
        visitados.add(actual);
        comunidad.agregar(actual.getDato());

        Map<NodoGrafo<Estudiante>, Double> adyacentes = actual.getAdyacentes();
        for (NodoGrafo<Estudiante> vecino : adyacentes.keySet()) {
            if (!visitados.contains(vecino)) {
                dfs(vecino, visitados, comunidad);
            }
        }
    }

    /**
     * Imprime una representación del grafo en consola.
     * Para cada estudiante (nodo), muestra los nombres de los estudiantes con los que está conectado (adyacentes).
     * Este método es útil para depurar o visualizar de forma básica la estructura del grafo.
     */
    @Override
    public void visualizarGrafoUsuarios() {
        for (NodoGrafo<Estudiante> nodo : grafoEstudiantes.obtenerNodos()) {
            System.out.print(nodo.getDato().getNombre() + " -> ");
            Map<NodoGrafo<Estudiante>, Double> adyacentes = nodo.getAdyacentes();
            for (NodoGrafo<Estudiante> vecino : adyacentes.keySet()) {
                System.out.print(vecino.getDato().getNombre() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Identifica y retorna la lista de estudiantes con mayor número de conexiones (mayor grado en el grafo).
     * Si varios estudiantes tienen el mismo número máximo de conexiones, todos son incluidos en la lista.
     *
     * @return Lista enlazada con los estudiantes más conectados de la red social.
     */
    @Override
    public ListaEnlazada<Estudiante> obtenerEstudiantesMasConectados() {
        ListaEnlazada<Estudiante> masConectados = new ListaEnlazada<>();
        int maxConexiones = 0;

        for (NodoGrafo<Estudiante> nodo : grafoEstudiantes.obtenerNodos()) {
            int conexiones = nodo.getAdyacentes().size();

            if (conexiones > maxConexiones) {
                masConectados = new ListaEnlazada<>();
                masConectados.agregar(nodo.getDato());
                maxConexiones = conexiones;
            } else if (conexiones == maxConexiones) {
                masConectados.agregar(nodo.getDato());
            }
        }

        return masConectados;
    }
    /**
     * Calcula el camino más corto entre dos estudiantes en el grafo, dados sus nombres.
     * Utiliza el método buscarRutaCorta del grafo para obtener la ruta más corta basada en el número de conexiones.
     *
     * @param inicio Nombre del estudiante de origen.
     * @param destino Nombre del estudiante destino.
     * @return Lista enlazada con los nombres de los estudiantes que componen el camino más corto.
     *         Si no existe una ruta o alguno de los estudiantes no está en el grafo, retorna una lista vacía.
     */
    @Override
    public ListaEnlazada<String> calcularCaminosMasCortos(String inicio, String destino) {
        NodoGrafo<Estudiante> nodoInicio = buscarNodoPorNombre(inicio);
        NodoGrafo<Estudiante> nodoDestino = buscarNodoPorNombre(destino);

        ListaEnlazada<String> caminoNombres = new ListaEnlazada<>();

        if (nodoInicio == null || nodoDestino == null) {
            // Si no existen los nodos, retorna lista vacía
            return caminoNombres;
        }

        // Obtén los datos Estudiante de los nodos
        Estudiante estudianteInicio = nodoInicio.getDato();
        Estudiante estudianteDestino = nodoDestino.getDato();

        // Llama al método buscarRutaCorta con los datos Estudiante
        ListaEnlazada<Estudiante> ruta = grafoEstudiantes.buscarRutaCorta(estudianteInicio, estudianteDestino);

        if (ruta == null) {
            System.out.println("No hay ruta");
            return caminoNombres; // vacía
        }

        // Convierte la ruta de Estudiante a ruta de nombres (String)
        for (Estudiante est : ruta) {
            caminoNombres.agregar(est.getNombre());
        }

        return caminoNombres;
    }

    /**
     * Busca un nodo dentro del grafo a partir del nombre del estudiante.
     *
     * @param nombre Nombre del estudiante a buscar.
     * @return Nodo del grafo que contiene al estudiante, o null si no se encuentra.
     */

    private NodoGrafo<Estudiante> buscarNodoPorNombre(String nombre) {
        for (NodoGrafo<Estudiante> nodo : grafoEstudiantes.obtenerNodos()) {
            if (nodo.getDato().getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null;
    }

    /**
     * Evalúa el nivel de participación de cada estudiante en la red con base en su número de conexiones.
     * Clasifica el nivel como:
     * - Alta: 5 o más conexiones.
     * - Media: Entre 2 y 4 conexiones.
     * - Baja: Menos de 2 conexiones.
     *
     * @return Lista enlazada de cadenas que describen el nivel de participación de cada estudiante.
     */
    @Override
    public ListaEnlazada<String> obtenerNivelesParticipacion() {
        ListaEnlazada<String> niveles = new ListaEnlazada<>();

        for (NodoGrafo<Estudiante> nodo : grafoEstudiantes.obtenerNodos()) {
            int conexiones = nodo.getAdyacentes().size();
            String nivel;

            if (conexiones >= 5) {
                nivel = "Alta";
            } else if (conexiones >= 2) {
                nivel = "Media";
            } else {
                nivel = "Baja";
            }

            niveles.agregar(nodo.getDato().getNombre() + ": Participación " + nivel);
        }

        return niveles;
    }
}
