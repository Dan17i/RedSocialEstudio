package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.interf.IGrafo;
import co.edu.uniquindio.redsocial.models.structures.GrafoImpl;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.HashMap;
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
     * Registra un estudiante en la red si no existe.
     */
    @Override
    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null) throw new IllegalArgumentException("El estudiante no puede ser null");

        if (!grafoEstudiantes.contieneNodo(estudiante)) {
            grafoEstudiantes.agregarNodo(estudiante);
        }
    }

    /**
     * Crea una relación de amistad entre dos estudiantes.
     */
    @Override
    public void conectarEstudiantes(Estudiante e1, Estudiante e2, double afinidad) {
        if (e1 == null || e2 == null || e1.equals(e2)) {
            throw new IllegalArgumentException("Los estudiantes no pueden ser null ni iguales");
        }

        registrarEstudiante(e1);
        registrarEstudiante(e2);

        grafoEstudiantes.agregarArista(e1, e2, afinidad); // Afinidad como peso
    }

    /**
     * Retorna la ruta más corta de conexiones entre dos estudiantes (según Dijkstra).
     */
    @Override
    public ListaEnlazada<Estudiante> obtenerRutaEntre(Estudiante origen, Estudiante destino) {
        return grafoEstudiantes.buscarRutaCorta(origen, destino);
    }

    /**
     * Devuelve una lista de estudiantes recomendados para un estudiante dado,
     * basados en comunidades detectadas (conexiones indirectas).
     */
    //@Override
    //public ListaEnlazada<Estudiante> sugerirAmigos(Estudiante estudiante) {
        ListaEnlazada<ListaEnlazada<Estudiante>> comunidades = grafoEstudiantes.detectarComunidades();
        ListaEnlazada<Estudiante> sugerencias = new ListaEnlazada<>();

       // for (ListaEnlazada<Estudiante> comunidad : comunidades) {
           // if (comunidad.equals(estudiante)) {
               // for (Estudiante e : comunidad) {
                   // if (!e.equals(estudiante) && !grafoEstudiantes.obtenerAdyacentes(estudiante).containsKey(e)) {
                    //    sugerencias.agregar(e);
                  //  }
              //  }
              //  break;
          //  }
       // }

       // return sugerencias;
    //}

    /**
     * Elimina un estudiante de la red y sus relaciones.
     */
    @Override
    public boolean eliminarEstudiante(Estudiante estudiante) {
        return grafoEstudiantes.eliminarNodo(estudiante);
    }

    /**
     * Elimina una amistad entre dos estudiantes.
     */
    @Override
    public boolean eliminarConexion(Estudiante e1, Estudiante e2) {
        return grafoEstudiantes.eliminarArista(e1, e2);
    }

    /**
     * Lista todos los estudiantes registrados.
     */
    @Override
    public Set<Estudiante> listarEstudiantes() {
        return grafoEstudiantes.obtenerTodosLosDatos();
    }

    /**
     * Devuelve los amigos directos del estudiante.
     */
    @Override
    public Map<Estudiante, Double> obtenerAmigosDe(Estudiante estudiante) {
        return grafoEstudiantes.obtenerAdyacentes(estudiante);
    }
}
