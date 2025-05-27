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
 * Implementación del gestor de red social.
 * Administra las operaciones relacionadas con la red de estudiantes como visualización de grafo,
 * detección de comunidades, caminos más cortos y participación.
 */
public class GestorRedSocial implements IGestorRedSocial {

    private final GrafoImpl<Estudiante> grafoEstudiantes;

    /**
     * Constructor que inicializa el grafo no dirigido.
     */
    public GestorRedSocial() {
        this.grafoEstudiantes = new GrafoImpl<>(false); // No dirigido
    }

    /**
     * Detecta comunidades (componentes conexas) usando DFS.
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

    // DFS auxiliar que visita nodos y agrega sus datos a la comunidad
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
     * Visualiza el grafo imprimiendo cada nodo y sus vecinos.
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
     * Obtiene la lista de estudiantes con mayor número de conexiones (grado).
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
     * Calcula el camino más corto entre dos estudiantes usando el método de grafo.
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


    // Busca nodo en el grafo por nombre del estudiante
    private NodoGrafo<Estudiante> buscarNodoPorNombre(String nombre) {
        for (NodoGrafo<Estudiante> nodo : grafoEstudiantes.obtenerNodos()) {
            if (nodo.getDato().getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null;
    }

    /**
     * Obtiene los niveles de participación basados en la cantidad de conexiones.
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
